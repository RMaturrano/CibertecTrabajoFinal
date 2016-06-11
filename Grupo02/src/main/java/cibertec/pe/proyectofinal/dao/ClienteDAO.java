package cibertec.pe.proyectofinal.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import cibertec.pe.proyectofinal.entities.Cliente;

/**
 * Created by JOSE on 20/05/2016.
 */
public class ClienteDAO {

    public ArrayList<Cliente> listCliente() {
        Cursor cursor = DataBaseSingleton.getInstance().query("clientes", null, null, null, null, null, null);

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

    public Cliente getCliente(Cliente cliente) {
        Cursor cursor = null;
        try {
            cursor = DataBaseSingleton.getInstance().query("clientes", null, "idCliente = ?", new String[]{String.valueOf(cliente.getIdCliente())}, null, null, null, "1");

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

    public boolean insertCliente(Cliente cliente) {
        ContentValues cv = new ContentValues();
        //cv.put("idCliente", cliente.getIdCliente());
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

        long inserto = DataBaseSingleton.getInstance().insert("clientes", null, cv);
        return inserto != -1;
    }

    public boolean updateCliente(Cliente cliente) {
        ContentValues cv = new ContentValues();
        cv.put("idCliente", cliente.getIdCliente());
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

        int update = DataBaseSingleton.getInstance().update("clientes", cv, "idCliente = ?", new String[]{String.valueOf(cliente.getIdCliente())});
        return update > 0;
    }

    private Cliente transformCursorToCliente(Cursor cursor) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(cursor.getInt(cursor.getColumnIndex("idCliente")));
        cliente.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
        cliente.setApellido(cursor.getString(cursor.getColumnIndex("apellido")));
        cliente.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
        cliente.setCorreo(cursor.getString(cursor.getColumnIndex("correo")));
        cliente.setEmpresa(cursor.getString(cursor.getColumnIndex("empresa")));
        cliente.setDireccion(cursor.getString(cursor.getColumnIndex("direccion")));
        cliente.setDistrito(cursor.getString(cursor.getColumnIndex("distrito")));
        cliente.setReferencia(cursor.getString(cursor.getColumnIndex("referencia")));
        cliente.setLatitud(cursor.getDouble(cursor.getColumnIndex("latitud")));
        cliente.setLongitud(cursor.getDouble(cursor.getColumnIndex("longitud")));

        return cliente;
    }
}
