package dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import entities.Cliente;

/**
 * Created by carlosarmando on 12/05/2016.
 */
public class ClienteDAO {
    public ArrayList<Cliente> listCliente() {
        Cursor cursor = DataBaseSingleton.getInstance().query("EC_CLIENTES", null, null, null, null, null, null);

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
            cursor = DataBaseSingleton.getInstance().query("EC_CLIENTES", null, "ID_CLIENTE = ?", new String[]{String.valueOf(cliente.getEmpresaId())}, null, null, null, "1");

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
        cv.put("NO_NOMBRES", cliente.getContactoNombre());
        cv.put("NO_APELLIDOS", cliente.getContactoApellido());
        cv.put("TE_CELULAR", cliente.getContactoTelefono());
        cv.put("CO_CORREO", cliente.getContactoCorreo());
        cv.put("NO_E_EMPRESA", cliente.getEmpresaNombre());
        cv.put("DI_E_DIRECCION", cliente.getEmpresaDireccion());
        cv.put("DI_E_DISTRITO", cliente.getEmpresaDistrito());
        cv.put("UB_E_REFERENCIA", cliente.getEmpresaReferencia());
        cv.put("NU_LATITUD", cliente.getEmpresaLatitud());
        cv.put("NU_lONGITUD", cliente.getEmpresaLongitud());

        long inserto = DataBaseSingleton.getInstance().insert("EC_CLIENTES", null, cv);

        return inserto != -1;
    }

    public boolean updateCliente(Cliente cliente) {
        ContentValues cv = new ContentValues();
        cv.put("NO_NOMBRES", cliente.getContactoNombre());
        cv.put("NO_APELLIDOS", cliente.getContactoApellido());
        cv.put("TE_CELULAR", cliente.getContactoTelefono());
        cv.put("CO_CORREO", cliente.getContactoCorreo());
        cv.put("NO_E_EMPRESA", cliente.getEmpresaNombre());
        cv.put("DI_E_DIRECCION", cliente.getEmpresaDireccion());
        cv.put("DI_E_DISTRITO", cliente.getEmpresaDistrito());
        cv.put("UB_E_REFERENCIA", cliente.getEmpresaReferencia());
        cv.put("NU_LATITUD", cliente.getEmpresaLatitud());
        cv.put("NU_lONGITUD", cliente.getEmpresaLongitud());

        int update = DataBaseSingleton.getInstance().update("EC_CLIENTES", cv, "ID_CLIENTE = ?", new String[]{String.valueOf(cliente.getEmpresaId())});

        return update > 0;
    }

    public boolean deleteCliente(Cliente cliente) {
        int delete = DataBaseSingleton.getInstance().delete("EC_CLIENTES", "ID_CLIENTE = ?", new String[]{String.valueOf(cliente.getEmpresaId())});

        return delete > 0;
    }

    private Cliente transformCursorToCliente(Cursor cursor) {
        Cliente cliente = new Cliente();
        cliente.setEmpresaId(cursor.getInt(cursor.getColumnIndex("ID_CLIENTE")));
        cliente.setContactoNombre(cursor.getString(cursor.getColumnIndex("NO_NOMBRES")));
        cliente.setContactoApellido(cursor.getString(cursor.getColumnIndex("NO_APELLIDOS")));
        cliente.setContactoTelefono(cursor.getInt(cursor.getColumnIndex("TE_CELULAR")));
        cliente.setContactoCorreo(cursor.getString(cursor.getColumnIndex("CO_CORREO")));
        cliente.setEmpresaNombre(cursor.getString(cursor.getColumnIndex("NO_E_EMPRESA")));
        cliente.setEmpresaDireccion(cursor.getString(cursor.getColumnIndex("DI_E_DIRECCION")));
        cliente.setEmpresaDistrito(cursor.getString(cursor.getColumnIndex("DI_E_DISTRITO")));
        cliente.setEmpresaReferencia(cursor.getString(cursor.getColumnIndex("UB_E_REFERENCIA")));
        cliente.setEmpresaLatitud(cursor.getString(cursor.getColumnIndex("NU_LATITUD")));
        cliente.setEmpresaLongitud(cursor.getString(cursor.getColumnIndex("NU_lONGITUD")));

        return cliente;
    }
}
