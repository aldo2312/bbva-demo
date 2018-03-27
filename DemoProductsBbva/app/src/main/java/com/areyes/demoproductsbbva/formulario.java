package com.areyes.demoproductsbbva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;

import com.areyes.demoproductsbbva.dao.ProductoDAO;
import com.areyes.demoproductsbbva.modelo.Producto;

/**
 * Created by ajrey on 25/03/2018.
 */

public class formulario extends Activity {

    private formularioHelper formularioHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);

        Intent intent = getIntent();
        final Producto productoModificar = (Producto) intent.getSerializableExtra("productoSeleccionado");

        formularioHelper = new formularioHelper(this);
        Button button = (Button) findViewById(R.id.bGrabar);

        if (productoModificar != null){
            button.setText("Modificar");
            formularioHelper.colocarProductoEnFormulario(productoModificar);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = formularioHelper.guardarProductoDeFormulario();
                ProductoDAO productoDAO = new ProductoDAO(formulario.this);

                if (productoModificar == null)
                {
                    productoDAO.guardar(producto);
                }else{
                    producto.setCodigoProducto(productoModificar.getCodigoProducto());
                    productoDAO.modificar(producto);
                }
                productoDAO.close();
                finish();
            }
        });


    }
}
