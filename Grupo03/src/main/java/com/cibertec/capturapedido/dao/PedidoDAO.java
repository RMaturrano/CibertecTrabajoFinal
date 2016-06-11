package com.cibertec.capturapedido.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.cibertec.capturapedido.entities.Pedido;
import com.cibertec.capturapedido.entities.PedidoDetalle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jorge on 26/05/2016.
 */
public class PedidoDAO {
    public ArrayList<Pedido> listPedido() {
        Cursor cursor = DataBaseSingleton.getInstance().rawQuery("SELECT p.pedidoId, p.clienteId, c.empresa Cliente, COUNT(pd.productoId) Productos, SUM(pd.cantidad * pd.precio) Total FROM Pedido p inner join Cliente c on p.clienteId = c.clienteId inner join PedidoDetalle pd on p.pedidoId = pd.pedidoId group by p.pedidoId, p.clienteId", null);

        ArrayList<Pedido> lstPedidos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Pedido pedido = new Pedido();
                pedido.setPedidoId(cursor.getInt(cursor.getColumnIndex("pedidoId")));
                pedido.setClienteId(cursor.getInt(cursor.getColumnIndex("clienteId")));
                pedido.setCliente(cursor.getString(cursor.getColumnIndex("Cliente")));
                pedido.setCant_productos(cursor.getInt(cursor.getColumnIndex("Productos")));
                pedido.setTotal(cursor.getDouble(cursor.getColumnIndex("Total")));
                lstPedidos.add(pedido);
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstPedidos;
    }

    public Pedido getPedido(int pedidoId) {
        Cursor cursor = null;
        Pedido pedido = null;
        try {
            cursor = DataBaseSingleton.getInstance().rawQuery("SELECT p.pedidoId, p.clienteId, c.empresa Cliente, p.fecha FROM Pedido p inner join Cliente c on p.clienteId = c.clienteId where p.pedidoId = ?", new String[]{String.valueOf(pedidoId)});

            if (cursor.moveToFirst()) {
                pedido = new Pedido();
                pedido.setPedidoId(cursor.getInt(cursor.getColumnIndex("pedidoId")));
                pedido.setClienteId(cursor.getInt(cursor.getColumnIndex("clienteId")));
                pedido.setCliente(cursor.getString(cursor.getColumnIndex("Cliente")));
                pedido.setFecha(cursor.getString(cursor.getColumnIndex("fecha")));

                //Llenamos el detalle del pedido
                pedido.setDetalle(getPedidoDetalle(pedidoId));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            pedido = null;
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return pedido;
    }

    public ArrayList<PedidoDetalle> getPedidoDetalle(int pedidoId) {
        ArrayList<PedidoDetalle> mLstDetalle = new ArrayList<>();

        Cursor cursor = DataBaseSingleton.getInstance().rawQuery("SELECT p.nombre nombre_producto, p.descripcion, pd.cantidad, pd.precio FROM PedidoDetalle pd inner join Producto p on pd.productoId = p.productoId where pd.pedidoId = ?", new String[]{String.valueOf(pedidoId)});

        if (cursor.moveToFirst()) {
            do {
                PedidoDetalle detalle = new PedidoDetalle();
                detalle.setPedidoId(pedidoId);
                detalle.setProductoNombre(cursor.getString(cursor.getColumnIndex("nombre_producto")));
                detalle.setProductoDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
                detalle.setCantidad(cursor.getDouble(cursor.getColumnIndex("cantidad")));
                detalle.setPrecio(cursor.getDouble(cursor.getColumnIndex("precio")));
                mLstDetalle.add(detalle);
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return mLstDetalle;
    }

    public int insertPedido(Pedido pedido) {
        ContentValues cv = new ContentValues();
        cv.put("clienteId", pedido.getClienteId());
        cv.put("fecha", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        long pedidoId = DataBaseSingleton.getInstance().insert("Pedido", null, cv);

        //Agregamos el detalle
        for(PedidoDetalle detalle: pedido.getDetalle()){
            cv = new ContentValues();
            cv.put("pedidoId", (int)pedidoId);
            cv.put("productoId", detalle.getProductoId());
            cv.put("cantidad", detalle.getCantidad());
            cv.put("precio", detalle.getPrecio());
            DataBaseSingleton.getInstance().insert("PedidoDetalle", null, cv);
        }

        return (int) pedidoId;
    }

    public boolean deletePedido(Pedido pedido) {
        DataBaseSingleton.getInstance().delete("PedidoDetalle", "pedidoId = ?", new String[]{String.valueOf(pedido.getPedidoId())});
        int delete = DataBaseSingleton.getInstance().delete("Pedido", "pedidoId = ?", new String[]{String.valueOf(pedido.getPedidoId())});

        return delete > 0;
    }

}
