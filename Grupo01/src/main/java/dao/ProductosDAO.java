package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import entities.Productos;


/**
 * Created by gosoriot on 10/05/2016.
 */
public class ProductosDAO {

    public ArrayList<Productos> listProducto(){
        Cursor cursor = DataBaseSingleton.getInstance().query("EC_PRODUCTOS", null, "ES_ESTADO=1", null, null, null, null);

        ArrayList<Productos> listProducto = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listProducto.add(transformCursorToProdcutos(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return listProducto;
    }

    private Productos transformCursorToProdcutos(Cursor cursor) {
        Productos productos = new Productos();
        productos.setIdProducto(cursor.getInt(cursor.getColumnIndex("ID_PRODUCTO")));
        productos.setNombreP(cursor.getString(cursor.getColumnIndex("NO_NOMBRE")));
        productos.setStockP(cursor.getInt(cursor.getColumnIndex("CA_STOCK")));
        productos.setPrecioP(cursor.getDouble(cursor.getColumnIndex("PR_PRECIO")));
        return productos;
    }

    public boolean insertProducto(Productos productos) {
        ContentValues cv = new ContentValues();
        cv.put("NO_NOMBRE", productos.getNombreP());
        cv.put("PR_PRECIO", productos.getPrecioP());
        cv.put("CA_STOCK", productos.getStockP());
        cv.put("ES_ESTADO", 1);
        long inserto = DataBaseSingleton.getInstance().insert("EC_PRODUCTOS", null, cv);
        return inserto != -1;
    }

    public boolean updateProducto(Productos productos) {
        ContentValues cv = new ContentValues();
        cv.put("NO_NOMBRE", productos.getNombreP());
        cv.put("PR_PRECIO", productos.getPrecioP());
        cv.put("CA_STOCK", productos.getStockP());
        int update = DataBaseSingleton.getInstance().update("EC_PRODUCTOS", cv, "ID_PRODUCTO = ?", new String[]{String.valueOf(productos.getIdProducto())});
        return update > 0;
    }
}
