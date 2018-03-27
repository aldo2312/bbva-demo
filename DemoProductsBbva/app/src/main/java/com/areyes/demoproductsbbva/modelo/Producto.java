package com.areyes.demoproductsbbva.modelo;

import java.io.Serializable;

/**
 * Created by ajrey on 25/03/2018.
 */

public class Producto implements Serializable {

    private static final long seriaVersionUID = 8222671891255181276L;
    private int codigoProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private int cantidad;

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return nombreProducto;
    }
}
