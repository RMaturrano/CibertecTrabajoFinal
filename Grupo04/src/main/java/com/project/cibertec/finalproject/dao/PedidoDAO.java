package com.project.cibertec.finalproject.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.project.cibertec.finalproject.database.DataBaseSingleton;
import com.project.cibertec.finalproject.entities.Cliente;
import com.project.cibertec.finalproject.entities.Pedido;
import com.project.cibertec.finalproject.entities.PedidoDetalle;

import java.util.ArrayList;

public class PedidoDAO {

    public ArrayList<Pedido> listPedidos() {
        Cursor cursor = null;
        cursor = DataBaseSingleton.getInstance().rawQuery
                ("SELECT  idPedido,"+
                            "c.empresa," +
                            "cantidadProductos," +
                            "total FROM TB_Pedido P join TB_Cliente C " +
                            "on P.idCliente = C.clienteId"
                , null);

        ArrayList<Pedido> lstPedido = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lstPedido.add(transformCursorToPedido(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstPedido;
    }

    public ArrayList<PedidoDetalle> listDetailPedido(int pedidoid) {
        Cursor cursor = null;
        cursor = DataBaseSingleton.getInstance().rawQuery
                ("SELECT " +
                        "PRO.nombre," +
                        "PRO.descripcion," +
                        "PED.cantidad," +
                        "PED.totalLinea " +
                        "FROM TB_Pedido PE join TB_Pedido_Detalle PED " +
                            "on PE.idPedido = PED.idPedido join TB_Producto PRO " +
                            "on PED.idProducto = PRO.productoId " +
                        "where PE.idPedido = '"+pedidoid+"' "
                        , null);

        ArrayList<PedidoDetalle> lstPedidoDetalle = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                lstPedidoDetalle.add(transformCursorToPedidoDetalle(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstPedidoDetalle;
    }


    public boolean insertPedido(Pedido pedido) {
        ContentValues cv = new ContentValues();
        cv.put("idCliente", pedido.getIdCliente());
        cv.put("cantidadProductos", pedido.getCantidadProductos());
        cv.put("total", pedido.getTotalPedido());

        long inserto = DataBaseSingleton.getInstance().insert("TB_Pedido", null, cv);

        if(inserto != -1){
            for (PedidoDetalle linea: pedido.getLineas()) {
                linea.setIdPedido(((int) inserto));
                insertPedidoLinea(linea);
            }
        }

        return inserto != -1;
    }

    public boolean insertPedidoLinea(PedidoDetalle detalle){
        ContentValues cv = new ContentValues();
        cv.put("idPedido", detalle.getIdPedido());
        cv.put("idProducto", detalle.getIdProducto());
        cv.put("cantidad", detalle.getCantidad());
        cv.put("totalLinea", detalle.getTotalLinea());

        long inserto = DataBaseSingleton.getInstance().insert("TB_Pedido_Detalle", null, cv);

        return inserto != -1;
    }

    public boolean deletePedido(Pedido pedido){
        return DataBaseSingleton.getInstance().delete("TB_Pedido","idPedido = ? ",
                                        new String[]{String.valueOf(pedido.getIdPedido())}) > 0;
    }

    private Pedido transformCursorToPedido(Cursor cursor) {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(cursor.getInt(cursor.getColumnIndex("idPedido")));
        pedido.setEmpresa(cursor.getString(cursor.getColumnIndex("empresa")));
        pedido.setCantidadProductos(cursor.getInt(cursor.getColumnIndex("cantidadProductos")));
        pedido.setTotalPedido(cursor.getDouble(cursor.getColumnIndex("total")));

        return pedido;
    }


    private PedidoDetalle transformCursorToPedidoDetalle(Cursor cursor) {
        PedidoDetalle PedidoDetalle = new PedidoDetalle();
        PedidoDetalle.setNombreProducto(cursor.getString(cursor.getColumnIndex("nombre")));
        PedidoDetalle.setDescripcionProducto(cursor.getString(cursor.getColumnIndex("descripcion")));
        PedidoDetalle.setCantidad(cursor.getInt(cursor.getColumnIndex("cantidad")));
        PedidoDetalle.setTotalLinea(cursor.getDouble(cursor.getColumnIndex("totalLinea")));

        return PedidoDetalle;
    }

}
