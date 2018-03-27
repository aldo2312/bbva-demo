package com.areyes.demoproductsbbva;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.areyes.demoproductsbbva.dao.ProductoDAO;
import com.areyes.demoproductsbbva.modelo.Producto;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SwipeMenuListView listView;
    EditText etBuscar;
    private List<Producto> list = new ArrayList<Producto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBuscar = (EditText)findViewById(R.id.txtBuscar);
        listView = (SwipeMenuListView) findViewById(R.id.list_item);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem editarItem = new SwipeMenuItem(getApplicationContext());
                editarItem.setBackground(new ColorDrawable(Color.rgb(0x00,0x66,0xFF)));
                editarItem.setWidth(170);
                editarItem.setIcon(R.drawable.ic_editar);

                menu.addMenuItem(editarItem);

                SwipeMenuItem eliminarItem = new SwipeMenuItem(getApplicationContext());
                eliminarItem.setBackground(new ColorDrawable(Color.rgb(0xF9,0x3F,0x25)));
                eliminarItem.setWidth(170);
                eliminarItem.setIcon(R.drawable.ic_eliminar);
                menu.addMenuItem(eliminarItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Producto productoSeleccionado = (Producto) listView.getItemAtPosition(position);
                switch (index){
                    case 0:
                        Log.d("MAIN_ACTIVITY", "onMenuItemClick clicked item"+ index);
                        Intent shFormulario = new Intent(MainActivity.this, formulario.class);
                        shFormulario.putExtra("productoSeleccionado", productoSeleccionado);
                        startActivity(shFormulario);

                        break;
                    case 1:
                        Log.d("MAIN_ACTIVITY", "onMenuItemClick clicked item"+ index);
                        ProductoDAO productoDAO = new ProductoDAO(MainActivity.this);
                        productoDAO.eliminar(productoSeleccionado);
                        productoDAO.close();
                        cargarLista("");
                        break;
                }
                return false;
            }
        });

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cargarLista(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //se consume el webservice y se hace el insert masivo
        cargarListaWSADispositivo();
    }

    private void  cargarLista(String filtro){
        ProductoDAO productoDAO = new ProductoDAO(this);
        List<Producto> lProductos = productoDAO.getLista();
        productoDAO.close();

        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<Producto> adapter = new ArrayAdapter<Producto>(this, layout, lProductos);

        listView.setAdapter(adapter);

        adapter.getFilter().filter(filtro);
    }

    private void cargarListaWSADispositivo(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://api.myjson.com/bins/12d94h",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray array = response.getJSONArray("listaprod");

                            // Loop through the array elements
                            for(int i=0;i<array.length();i++){
                                JSONObject objeto = array.getJSONObject(i);
                                Producto producto = new Producto();
                                producto.setCodigoProducto(objeto.getInt("codProd"));
                                producto.setNombreProducto(objeto.getString("NombreProd"));
                                producto.setDescripcionProducto(objeto.getString("Descripcion"));
                                producto.setCantidad(objeto.getInt("cantidad"));
                                list.add(producto);
                            }
                            new ProductoDAO(MainActivity.this).guardar(list);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("VOLLEY","ERROR");
                    }
                }
        );
        Controller.getPermission().addToRequestQueue(jsonObjectRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarLista("");
    }
}
