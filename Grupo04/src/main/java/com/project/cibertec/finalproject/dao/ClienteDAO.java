package com.project.cibertec.finalproject.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.project.cibertec.finalproject.database.DataBaseSingleton;
import com.project.cibertec.finalproject.entities.Cliente;

import java.util.ArrayList;

/**
 * Created by thebe on 5/06/2016.
 */
public class ClienteDAO {

    public ArrayList<Cliente> listCliente() {
        Cursor cursor = DataBaseSingleton.getInstance().query("TB_Cliente", null, null, null, null, null, null);

        ArrayList<Cliente> lstCliente = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                lstCliente.add(transformCursorToCliente(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstCliente;
    }

    private Cliente transformCursorToCliente(Cursor cursor) {
        Cliente cliente = new Cliente();
        cliente.setClienteId(cursor.getInt(cursor.getColumnIndex("clienteId")));
        cliente.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
        cliente.setApellido(cursor.getString(cursor.getColumnIndex("apellido")));
        cliente.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
        cliente.setCorreo(cursor.getString(cursor.getColumnIndex("correo")));
        cliente.setEmpresa(cursor.getString(cursor.getColumnIndex("empresa")));
        cliente.setDireccion(cursor.getString(cursor.getColumnIndex("direccion")));
        cliente.setDistrito(cursor.getString(cursor.getColumnIndex("distrito")));
        cliente.setReferencia(cursor.getString(cursor.getColumnIndex("referencia")));
        cliente.setLatitud(cursor.getString(cursor.getColumnIndex("latitud")));
        cliente.setLongitud(cursor.getString(cursor.getColumnIndex("longitud")));

        return cliente;
    }

    public boolean updateCliente(Cliente cliente) {
        ContentValues cv = new ContentValues();
        cv.put("nombre", cliente.getNombre());
        cv.put("apellido", cliente.getApellido());
        cv.put("telefono", cliente.getTelefono());
        cv.put("correo", cliente.getCorreo());
        cv.put("empresa", cliente.getEmpresa());
        cv.put("direccion", cliente.getDireccion());
        cv.put("distrito", cliente.getDistrito());
        cv.put("referencia", cliente.getReferencia());
        cv.put("latitud", cliente.getLatitud());
        cv.put("longitud", cliente.getLongitud());

        int update = DataBaseSingleton.getInstance().update("TB_Cliente", cv, "clienteId = ?", new String[]{String.valueOf(cliente.getClienteId())});
        return update > 0;
    }

    public boolean insertCliente(Cliente cliente) {
        ContentValues cv = new ContentValues();
        cv.put("nombre", cliente.getNombre());
        cv.put("apellido", cliente.getApellido());
        cv.put("telefono", cliente.getTelefono());
        cv.put("correo", cliente.getCorreo());
        cv.put("empresa", cliente.getEmpresa());
        cv.put("direccion", cliente.getDireccion());
        cv.put("distrito", cliente.getDistrito());
        cv.put("referencia", cliente.getReferencia());
        cv.put("latitud", cliente.getLatitud());
        cv.put("longitud", cliente.getLongitud());

        long inserto = DataBaseSingleton.getInstance().insert("TB_Cliente", null, cv);
        return inserto != -1;
    }
}
