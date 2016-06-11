package com.trabajo.capturapedido.capturapedido.datos;

import android.content.ContentValues;
import android.database.Cursor;

import com.trabajo.capturapedido.capturapedido.entities.BEPedido;

import java.util.ArrayList;

/**
 * Created by cechenique on 18/05/2016.
 */
public class DAPedido {

    public ArrayList<BEPedido> listaPedido() {
        Cursor cursor = DataBaseSingleton.getInstance().rawQuery("Select p.*, c.nomCliente||' '||c.apellCliente as nomCliente From Pedido p join Cliente c on p.idCliente=c.idCliente", null);

        ArrayList<BEPedido> lstPedido = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lstPedido.add(transformCursorToPedido(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstPedido;
    }

    public ArrayList<BEPedido> listaPedidoDet(int idPedido) {
        Cursor cursor = DataBaseSingleton.getInstance().rawQuery("Select pd.*, p.nomProducto,descProducto from PedidoDet pd join Producto p on pd.idProducto=p.idProducto Where idPedido = ?", new String[]{String.valueOf(idPedido)});

        ArrayList<BEPedido> lstPedidoDet = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lstPedidoDet.add(transformCursorToPedidoDet(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstPedidoDet;
    }


    /*public BEPedido getPedidoDet(BEPedido pedido) {
        Cursor cursor = null;
        try {
            cursor = DataBaseSingleton.getInstance().query("Pedido", null, "idPedido = ?", new String[]{String.valueOf(pedido.getIdPedido())}, null, null, null, "1");

            if (cursor.moveToFirst())
                pedido = transformCursorToPedidoDet(cursor);
            else
                pedido = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            pedido = null;
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            return pedido;
        }
    }*/

    public int insertPedido(BEPedido pedido) {
        ContentValues cv = new ContentValues();
        cv.put("idCliente", pedido.getIdClientePedido());
        cv.put("fecha", pedido.getFechaPedido());
        cv.put("cantidad", pedido.getCanttotPedido());
        cv.put("total", pedido.getTotalPedido());

        long inserto = DataBaseSingleton.getInstance().insert("Pedido", null, cv);

        return (int) inserto;
    }

    public int insertPedidoDet(BEPedido pedido) {
        ContentValues cv = new ContentValues();
        cv.put("idPedido", pedido.getIdPedido());
        cv.put("idProducto", pedido.getIdProdPedidoDet());
        cv.put("cantidad", pedido.getCantPedidoDet());
        cv.put("precio", pedido.getPrecioPedidoDet());
        cv.put("total", pedido.getTotalPedidoDet());

        long inserto = DataBaseSingleton.getInstance().insert("PedidoDet", null, cv);

        return (int) inserto;
    }

    /*public boolean updatePedido(BEPedido pedido) {
        ContentValues cv = new ContentValues();
        cv.put("situacion", pedido.getSituacionPedido());
        cv.put("total",pedido.getTotalPedido());

        int update = DataBaseSingleton.getInstance().update("Pedido", cv, "idPedido = ?", new String[]{String.valueOf(pedido.getIdPedido())});

        return update > 0;
    }*/

    public boolean deletePedido(BEPedido pedido) {

        int deleteDet = DataBaseSingleton.getInstance().delete("PedidoDet", "idPedido = ?", new String[]{String.valueOf(pedido.getIdPedido())});
        int delete = DataBaseSingleton.getInstance().delete("Pedido", "idPedido = ?", new String[]{String.valueOf(pedido.getIdPedido())});

        return delete > 0;
    }

    private BEPedido transformCursorToPedido(Cursor cursor) {
        BEPedido pedido = new BEPedido();
        pedido.setIdPedido(cursor.getInt(cursor.getColumnIndex("idPedido")));
        pedido.setIdClientePedido(cursor.getInt(cursor.getColumnIndex("idCliente")));
        pedido.setNomClientePedido(cursor.getString(cursor.getColumnIndex("nomCliente")));
        pedido.setFechaPedido(cursor.getString(cursor.getColumnIndex("fecha")));
        pedido.setCanttotPedido(cursor.getInt(cursor.getColumnIndex("cantidad")));
        pedido.setTotalPedido(cursor.getDouble(cursor.getColumnIndex("total")));

        return pedido;
    }

    private BEPedido transformCursorToPedidoDet(Cursor cursor) {
        BEPedido pedido = new BEPedido();
        pedido.setIdPedidoDet(cursor.getInt(cursor.getColumnIndex("idPedidoDet")));
        pedido.setIdPedido(cursor.getInt(cursor.getColumnIndex("idPedido")));
        pedido.setIdProdPedidoDet(cursor.getInt(cursor.getColumnIndex("idProducto")));
        pedido.setNomProdPedidoDet(cursor.getString(cursor.getColumnIndex("nomProducto")));
        pedido.setDescProdPedidoDet(cursor.getString(cursor.getColumnIndex("descProducto")));
        pedido.setCantPedidoDet(cursor.getInt(cursor.getColumnIndex("cantidad")));
        pedido.setPrecioPedidoDet(cursor.getDouble(cursor.getColumnIndex("precio")));
      //  pedido.setNomProdPedidoDet(cursor.getString(cursor.getColumnIndex("producto")));

        return pedido;
    }
}
