package com.cibertec.capturapedido.dao;

import android.database.Cursor;

import com.cibertec.capturapedido.entities.Usuario;

/**
 * Created by jtorres on 19/05/2016.
 */
public class UsuarioDAO {
    public Usuario getUsuario(String usuarioId){
        Cursor cursor = null;
        Usuario usuarioResult = null;
        try{
            cursor = DataBaseSingleton.getInstance().query("Usuario", null, "lower(usuarioId) = ?", new String[]{usuarioId.toLowerCase()}, null, null, null, null);
            //cursor = DataBaseSingleton.getInstance().rawQuery("select * from Usuario where usuarioId = ?", new String[]{usuarioId});
            if(cursor.moveToFirst()){
                usuarioResult = new Usuario();
                usuarioResult.setUsuarioId(usuarioId);
                usuarioResult.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                usuarioResult.setApellido(cursor.getString(cursor.getColumnIndex("apellido")));
                usuarioResult.setClave(cursor.getString(cursor.getColumnIndex("clave")));
                usuarioResult.setEdad(cursor.getInt(cursor.getColumnIndex("edad")));
                usuarioResult.setDni(cursor.getString(cursor.getColumnIndex("dni")));
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            return usuarioResult;
        }
    }
}
