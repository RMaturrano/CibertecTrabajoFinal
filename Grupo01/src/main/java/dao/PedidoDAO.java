package dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import entities.Pedido;
import entities.PedidoCab;
import entities.PedidoDet;

public class PedidoDAO {
    public ArrayList<PedidoCab> lstPedidoCab() {
        Cursor cursor = DataBaseSingleton.getInstance().rawQuery(
                "Select ID_PEDIDO, NO_E_EMPRESA As Cliente, " +
                        "       Count(ID_PRODUCTO) As CantProducto,Sum(NU_PRECIO * NU_CANTIDAD) As SubTotal " +
                        "From EC_PEDIDO ped " +
                        "  Inner Join EC_CLIENTES c ON ped.ID_CLIENTE= c.ID_CLIENTE " +
                        "Where ped.ES_ESTADO=1 " +
                        "Group By ID_PEDIDO, NO_E_EMPRESA",null);

        ArrayList<PedidoCab> lstPedido = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lstPedido.add(transformCursorToPedidoCab(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstPedido;
    }
    public ArrayList<PedidoDet> lstPedidoDet(PedidoCab pedidoCab) {
        Cursor cursor = DataBaseSingleton.getInstance().rawQuery(
                "SELECT p.ID_PRODUCTO, NO_NOMBRE, NU_CANTIDAD, NU_PRECIO * NU_CANTIDAD AS MONTO " +
                        "FROM EC_PEDIDO ped " +
                        "  INNER JOIN EC_PRODUCTOS p ON p.ID_PRODUCTO=ped.ID_PRODUCTO " +
                        "WHERE ID_PEDIDO=?",new String[]{String.valueOf(pedidoCab.getIdPedido())});

        ArrayList<PedidoDet> lstPedido = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                lstPedido.add(transformCursorToPedidoDet(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstPedido;
    }
    public PedidoCab getPedidoCab(PedidoCab pedidoCab) {
        Cursor cursor = null;
        try {
            cursor = DataBaseSingleton.getInstance().rawQuery(
                    "Select ID_PEDIDO, NO_E_EMPRESA As Cliente, " +
                            "       Count(ID_PRODUCTO) As CantProducto,Sum(NU_PRECIO * NU_CANTIDAD) As SubTotal " +
                            "From EC_PEDIDO ped " +
                            "  Inner Join EC_CLIENTES c ON ped.ID_CLIENTE= c.ID_CLIENTE " +
                            "Where ped.ES_ESTADO=1 " +
                            "Group By ID_PEDIDO, NO_E_EMPRESA " +
                            "Where ID_PEDIDO=?", new String[]{String.valueOf(pedidoCab.getIdPedido())});

            if (cursor.moveToFirst())
                pedidoCab = transformCursorToPedidoCab(cursor);
            else
                pedidoCab = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            pedidoCab = null;
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            return pedidoCab;
        }
    }
    public boolean insertPedido(Pedido pedido) {
        ContentValues cv = new ContentValues();
        cv.put("ID_PEDIDO", pedido.getIdPedido());
        cv.put("ID_CLIENTE", pedido.getIdCliente());
        cv.put("ID_PRODUCTO", pedido.getIdProducto());
        cv.put("NU_PRECIO", pedido.getNuPrecio());
        cv.put("NU_CANTIDAD", pedido.getNuCantidad());
        cv.put("ES_ESTADO","1");

        long inserto = DataBaseSingleton.getInstance().insert("EC_PEDIDO", null, cv);

        return inserto != -1;
    }
    public boolean deletePedido(PedidoCab pedidoCab) {
        int delete = DataBaseSingleton.getInstance().delete("EC_PEDIDO", "ID_PEDIDO = ?", new String[]{String.valueOf(pedidoCab.getIdPedido())});

        return delete > 0;
    }
    public int NewIdPedido(){
        int dev=0;
        Cursor cursor = null;
        try {
            cursor = DataBaseSingleton.getInstance().rawQuery(
                    "Select Max(ID_PEDIDO) As ULTIMO " +
                            "From EC_PEDIDO ",null);

            if (cursor.moveToFirst())
                dev = cursor.getInt(cursor.getColumnIndex("ULTIMO")) + 1;
            else
                dev = 1;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            return dev;
        }    }
    private PedidoCab transformCursorToPedidoCab(Cursor cursor) {
        PedidoCab pedido = new PedidoCab();
        pedido.setIdPedido(cursor.getInt(cursor.getColumnIndex("ID_PEDIDO")));
        pedido.setCliente(cursor.getString(cursor.getColumnIndex("Cliente")));
        pedido.setCantProducto(cursor.getInt(cursor.getColumnIndex("CantProducto")));
        pedido.setMontoTotal(cursor.getDouble(cursor.getColumnIndex("SubTotal")));

        return pedido;
    }
    private PedidoDet transformCursorToPedidoDet(Cursor cursor) {
        PedidoDet pedido = new PedidoDet();
        pedido.setNombreProd(cursor.getString(cursor.getColumnIndex("NO_NOMBRE")));
        pedido.setIdProducto(cursor.getInt(cursor.getColumnIndex("ID_PRODUCTO")));
        pedido.setCantidad(cursor.getDouble(cursor.getColumnIndex("NU_CANTIDAD")));
        pedido.setSubTotal(cursor.getDouble(cursor.getColumnIndex("MONTO")));
        return pedido;
    }
}