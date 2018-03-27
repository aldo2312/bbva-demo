package com.areyes.demoproductsbbva.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.areyes.demoproductsbbva.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajrey on 25/03/2018.
 */

public class ProductoDAO extends SQLiteOpenHelper{

    private static final String DATABASE = "dataBbva";
    private static final int VERSION = 1;

    public ProductoDAO(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    public void guardar(List<Producto> listado){
        final int size = listado.size();
        for (int i = 0; i < size; i++)
        {
            Producto prod = listado.get(i);
            guardar(prod);
        }
    }

    public void guardar(Producto producto){
        ContentValues values = new ContentValues();
        if(producto.getCodigoProducto()!=-1)
            values.put("codigoProducto", producto.getCodigoProducto());
        values.put("nombreProducto", producto.getNombreProducto());
        values.put("descripcionProducto", producto.getDescripcionProducto());
        values.put("cantidad", producto.getCantidad());

        getWritableDatabase().insert("Productos", null, values);
    }

    public List<Producto> getLista(){

        String[] columns = {"codigoproducto", "nombreproducto", "descripcionproducto", "cantidad"};
        Cursor cursor = getWritableDatabase().query("Productos", columns, null,
                null, null, null, null);

        ArrayList<Producto> productos = new ArrayList<Producto>();
        while (cursor.moveToNext()) {
            Producto producto = new Producto();
            producto.setCodigoProducto(Integer.parseInt(cursor.getString(0)));
            producto.setNombreProducto(cursor.getString(1));
            producto.setDescripcionProducto(cursor.getString(2));
            producto.setCantidad(Integer.parseInt(cursor.getString(3)));
            productos.add(producto);
        }
        return productos;
    }

    public void eliminar(Producto producto){
        String[] args = { String.valueOf(producto.getCodigoProducto()) };
        getWritableDatabase().delete("Productos", "codigoproducto=?", args);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE Productos (codigoproducto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombreproducto TEXT," +
                "descripcionproducto TEXT," +
                "cantidad INTEGER);";
        db.execSQL(ddl);
     }
;
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ddl = "DROP TABLE IF EXISTS Productos";
        db.execSQL(ddl);

        this.onCreate(db);
    }

    public void modificar(Producto producto) {
        ContentValues values = new ContentValues();
        values.put("nombreProducto", producto.getNombreProducto());
        values.put("descripcionProducto", producto.getDescripcionProducto());
        values.put("cantidad", producto.getCantidad());

        String[] args = { String.valueOf(producto.getCodigoProducto()) };
        getWritableDatabase().update("Productos", values, "codigoproducto=?", args);
    }
}
