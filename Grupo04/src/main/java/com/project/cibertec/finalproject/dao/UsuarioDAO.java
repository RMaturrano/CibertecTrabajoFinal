package com.project.cibertec.finalproject.dao;

import android.database.Cursor;

import com.project.cibertec.finalproject.database.DataBaseSingleton;
import com.project.cibertec.finalproject.entities.Usuario;

/**
 * Created by thebe on 6/06/2016.
 */
public class UsuarioDAO {

    public Usuario getUsuari(Usuario usuario) {
        Cursor cursor = null;
        try {
            cursor = DataBaseSingleton.getInstance().query("TB_Usuario", null, "usuario = ?", new String[]{(usuario.getUsuario())}, null, null, null, "1");

            if (cursor.moveToFirst())
                usuario = transformCursorToUsuario(cursor);
            else
                usuario = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            usuario = null;
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            return usuario;
        }


    }

    private Usuario transformCursorToUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(cursor.getInt(cursor.getColumnIndex("usuarioId")));
        usuario.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
        usuario.setApellido(cursor.getString(cursor.getColumnIndex("apellido")));
        usuario.setCorreo(cursor.getString(cursor.getColumnIndex("correo")));
        usuario.setUsuario(cursor.getString(cursor.getColumnIndex("usuario")));
        usuario.setClave(cursor.getString(cursor.getColumnIndex("clave")));

        return usuario;
    }

}
