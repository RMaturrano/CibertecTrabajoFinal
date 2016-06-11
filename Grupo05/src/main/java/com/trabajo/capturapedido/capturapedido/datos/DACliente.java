package com.trabajo.capturapedido.capturapedido.datos;

import android.content.ContentValues;
import android.database.Cursor;

import com.trabajo.capturapedido.capturapedido.entities.BECliente;

import java.util.ArrayList;

/**
 * Created by cechenique on 18/05/2016.
 */
public class DACliente {

    public ArrayList<BECliente> listaCliente() {
        Cursor cursor = DataBaseSingleton.getInstance().query("Cliente", null, null, null, null, null, null);

        ArrayList<BECliente> lstCliente = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lstCliente.add(transformCursorToCliente(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstCliente;
    }

    public BECliente getCliente(BECliente cliente) {
        Cursor cursor = null;
        try {
            cursor = DataBaseSingleton.getInstance().query("Cliente", null, "idCliente = ?", new String[]{String.valueOf(cliente.getIdCliente())}, null, null, null, "1");

            if (cursor.moveToFirst())
                cliente = transformCursorToCliente(cursor);
            else
                cliente = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            cliente = null;
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            return cliente;
        }
    }

    public boolean insertCliente(BECliente cliente) {
        ContentValues cv = new ContentValues();
        cv.put("nomCliente", cliente.getNomCliente());
        cv.put("apellCliente", cliente.getApellCliente());
        cv.put("telfCliente", cliente.getTelfCliente());
        cv.put("correoCliente", cliente.getCorreoCliente());
        cv.put("ciaCliente", cliente.getCiaCliente());
        cv.put("direccCliente", cliente.getDireccCliente());
        cv.put("distCliente", cliente.getDistCliente());
        cv.put("refCliente", cliente.getRefCliente());
        cv.put("latitud", cliente.getLatitud());
        cv.put("longitud", cliente.getLongitud());

        long inserto = DataBaseSingleton.getInstance().insert("Cliente", null, cv);

        return inserto != -1;
    }

    public boolean updateCliente(BECliente cliente) {
        ContentValues cv = new ContentValues();
        cv.put("nomCliente", cliente.getNomCliente());
        cv.put("apellCliente", cliente.getApellCliente());
        cv.put("telfCliente", cliente.getTelfCliente());
        cv.put("correoCliente", cliente.getCorreoCliente());
        cv.put("ciaCliente", cliente.getCiaCliente());
        cv.put("direccCliente", cliente.getDireccCliente());
        cv.put("distCliente", cliente.getDistCliente());
        cv.put("refCliente", cliente.getRefCliente());
        cv.put("latitud", cliente.getLatitud());
        cv.put("longitud", cliente.getLongitud());

        int update = DataBaseSingleton.getInstance().update("Cliente", cv, "idCliente = ?", new String[]{String.valueOf(cliente.getIdCliente())});

        return update > 0;
    }

    public boolean deleteCliente(BECliente cliente) {

        int delete = DataBaseSingleton.getInstance().delete("Cliente", "idCliente = ?", new String[]{String.valueOf(cliente.getIdCliente())});

        return delete > 0;
    }

    private BECliente transformCursorToCliente(Cursor cursor) {
        BECliente cliente = new BECliente();
        cliente.setIdCliente(cursor.getInt(cursor.getColumnIndex("idCliente")));
        cliente.setNomCliente(cursor.getString(cursor.getColumnIndex("nomCliente")));
        cliente.setApellCliente(cursor.getString(cursor.getColumnIndex("apellCliente")));
        cliente.setTelfCliente(cursor.getString(cursor.getColumnIndex("telfCliente")));
        cliente.setCorreoCliente(cursor.getString(cursor.getColumnIndex("correoCliente")));
        cliente.setCiaCliente(cursor.getString(cursor.getColumnIndex("ciaCliente")));
        cliente.setDireccCliente(cursor.getString(cursor.getColumnIndex("direccCliente")));
        cliente.setDistCliente(cursor.getString(cursor.getColumnIndex("distCliente")));
        cliente.setRefCliente(cursor.getString(cursor.getColumnIndex("refCliente")));
        cliente.setLatitud(cursor.getDouble(cursor.getColumnIndex("latitud")));
        cliente.setLongitud(cursor.getDouble(cursor.getColumnIndex("longitud")));

        return cliente;
    }
}
