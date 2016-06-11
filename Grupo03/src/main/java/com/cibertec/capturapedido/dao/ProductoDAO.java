package com.cibertec.capturapedido.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.cibertec.capturapedido.entities.Cliente;
import com.cibertec.capturapedido.entities.Producto;

import java.util.ArrayList;

/**
 * Created by bestrada on 25/05/2016.
 */
public class ProductoDAO {
    public ArrayList<Producto> listProduto(String searchText) {
        if (searchText == null) searchText = "";
        Cursor cursor = DataBaseSingleton.getInstance().query("Producto", null, "nombre like ? OR descripcion like ?", new String[]{"%" + searchText + "%", "%" + searchText + "%"}, null, null, null);

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

    public Producto getProducto(Producto producto) {
        Cursor cursor = null;
        try {
            cursor = DataBaseSingleton.getInstance().query("Producto", null, "productoId = ?", new String[]{String.valueOf(producto.getProductoId())}, null, null, null, "1");

            if (cursor.moveToFirst())
                producto = transformCursorToProducto(cursor);
            else
                producto = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            producto = null;
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            return producto;
        }
    }

    public boolean insertProducto(Producto producto) {
        ContentValues cv = new ContentValues();
        cv.put("nombre", producto.getNombre());
        cv.put("descripcion", producto.getDescripcion());
        cv.put("precio", producto.getPrecio());

        long productoId = DataBaseSingleton.getInstance().insert("Producto", null, cv);

        return productoId != -1;
    }

    public boolean updateProducto(Producto producto) {
        ContentValues cv = new ContentValues();
        cv.put("nombre", producto.getNombre());
        cv.put("descripcion", producto.getDescripcion());
        cv.put("precio", producto.getPrecio());

        int update = DataBaseSingleton.getInstance().update("Producto", cv, "productoId = ?", new String[]{String.valueOf(producto.getProductoId())});

        return update > 0;
    }

    public boolean deleteProducto(Producto producto) {

        int delete = DataBaseSingleton.getInstance().delete("Producto", "productoId = ?", new String[]{String.valueOf(producto.getProductoId())});

        return delete > 0;
    }

    private Producto transformCursorToProducto(Cursor cursor) {
        Producto producto = new Producto();
        producto.setProductoId(cursor.getInt(cursor.getColumnIndex("productoId")));
        producto.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
        producto.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
        producto.setPrecio(cursor.getDouble(cursor.getColumnIndex("precio")));

        return producto;
    }

}
