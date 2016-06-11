package com.project.cibertec.finalproject.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.project.cibertec.finalproject.database.DataBaseSingleton;
import com.project.cibertec.finalproject.entities.Cliente;
import com.project.cibertec.finalproject.entities.Producto;
import com.project.cibertec.finalproject.entities.Usuario;

import java.util.ArrayList;

/**
 * Created by thebe on 8/06/2016.
 */
public class ProductoDAO {

    public ArrayList<Producto> listProducto() {
        Cursor cursor = DataBaseSingleton.getInstance().query("TB_Producto", null, null, null, null, null, null);

        ArrayList<Producto> lstProducto = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lstProducto.add(transformCursorToProducto(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstProducto;
    }

    private Producto transformCursorToProducto(Cursor cursor) {
        Producto producto = new Producto();
        producto.setIdProducto(cursor.getInt(cursor.getColumnIndex("productoId")));
        producto.setNombreProducto(cursor.getString(cursor.getColumnIndex("nombre")));
        producto.setDescripcionProducto(cursor.getString(cursor.getColumnIndex("descripcion")));
        producto.setPrecioProducto(Double.parseDouble(cursor.getString(cursor.getColumnIndex("precio"))));
        return producto;
    }

    public boolean updateProducto(Producto producto) {
        ContentValues cv = new ContentValues();
        cv.put("nombre", producto.getNombreProducto());
        cv.put("descripcion", producto.getDescripcionProducto());
        cv.put("precio", producto.getPrecioProducto());

        int update = DataBaseSingleton.getInstance().update("TB_Producto", cv, "productoId = ?", new String[]{String.valueOf(producto.getIdProducto())});
        return update > 0;
    }

    public boolean insertProducto(Producto producto) {
        ContentValues cv = new ContentValues();
        cv.put("nombre", producto.getNombreProducto());
        cv.put("descripcion", producto.getDescripcionProducto());
        cv.put("precio", producto.getPrecioProducto());

        long inserto = DataBaseSingleton.getInstance().insert("TB_Producto", null, cv);
        return inserto != -1;
    }
}
