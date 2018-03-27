package com.areyes.demoproductsbbva;

import android.widget.EditText;

import com.areyes.demoproductsbbva.modelo.Producto;

/**
 * Created by ajrey on 25/03/2018.
 */

public class formularioHelper {
    private EditText editNombre;
    private EditText editDescripcion;
    private EditText editCantidad;

    public formularioHelper(formulario formulario) {
        editNombre = (EditText) formulario.findViewById(R.id.txtNombreProducto);
        editDescripcion = (EditText) formulario.findViewById(R.id.txtDescripcionProducto);
        editCantidad = (EditText) formulario.findViewById(R.id.txtCantidad);
    }

    public Producto guardarProductoDeFormulario(){
        Producto producto = new Producto();
        producto.setNombreProducto(editNombre.getText().toString());
        producto.setDescripcionProducto(editDescripcion.getText().toString());
        producto.setCantidad(Integer.parseInt(editCantidad.getText().toString()));
        return producto;
    }


    public void colocarProductoEnFormulario(Producto productoModificar){
        editNombre.setText(productoModificar.getNombreProducto());
        editDescripcion.setText(productoModificar.getDescripcionProducto());
        editCantidad.setText(String.valueOf(productoModificar.getCantidad()));
    }
}
