package com.trabajo.capturapedido.capturapedido.datos;


import android.content.ContentValues;
import android.database.Cursor;

import com.trabajo.capturapedido.capturapedido.entities.BEProducto;

import java.util.ArrayList;

/**
 * Created by Alejandra on 01/06/2016.
 */
public class DAProducto {

    public ArrayList<BEProducto> listaProducto() {
        Cursor cursor = DataBaseSingleton.getInstance().query("Producto", null, null, null, null, null, null);

        ArrayList<BEProducto> lstProducto = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lstProducto.add(transformCursorToProducto(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstProducto;
    }

    public ArrayList<BEProducto> FiltraProducto(String sNombre) {
        Cursor cursor = DataBaseSingleton.getInstance().rawQuery("Select * From Producto Where nomProducto like ?", new String[]{"%"+sNombre+"%"});

        ArrayList<BEProducto> lstProducto = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lstProducto.add(transformCursorToProducto(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstProducto;
    }

    public int contarRegistros(){
        Cursor cursor = DataBaseSingleton.getInstance().rawQuery("SELECT COUNT(*) FROM Producto", null);
        int cantidad = Integer.parseInt(String.valueOf(cursor));
        cantidad++;
        return cantidad;
    }

    public boolean insertProducto(BEProducto producto){
        ContentValues cv = new ContentValues();

        cv.put("nomProducto",producto.getNomProducto());
        cv.put("descProducto",producto.getDescProducto());
        cv.put("precProducto", producto.getPrecioProducto());

        long inserto = DataBaseSingleton.getInstance().insert("Producto",null,cv);

        return  inserto != 1;
    }

    public boolean updateProducto(BEProducto producto){
        ContentValues cv = new ContentValues();
        cv.put("nomProducto",producto.getNomProducto());
        cv.put("descProducto",producto.getDescProducto());
        cv.put("precProducto",producto.getPrecioProducto());

        int update = DataBaseSingleton.getInstance().update("Producto",cv,"idProducto = ?" , new String[]{String.valueOf(producto.getIdProducto())});

        return update > 0;
    }

    public boolean deleteProducto(BEProducto producto){
        int delete = DataBaseSingleton.getInstance().delete("Producto","idProducto = ?", new String[]{String.valueOf(producto.getIdProducto())});


        return delete > 0;
    }


    private BEProducto transformCursorToProducto(Cursor cursor) {
        BEProducto producto = new BEProducto();
        producto.setIdProducto(cursor.getInt(cursor.getColumnIndex("idProducto")));
        producto.setNomProducto(cursor.getString(cursor.getColumnIndex("nomProducto")));
        producto.setDescProducto(cursor.getString(cursor.getColumnIndex("descProducto")));
        producto.setPrecioProducto(cursor.getDouble(cursor.getColumnIndex("precProducto")));

        return producto;
    }
}

