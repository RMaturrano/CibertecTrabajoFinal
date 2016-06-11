package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import entities.Usuarios;

/**
 * Created by gosoriot on 13/05/2016.
 */
public class UsuariosDAO {

    public ArrayList<Usuarios> listUsuarios(){
        Cursor cursor = DataBaseSingleton.getInstance().query("EC_TM_USU", null, "ES_ESTADO=1", null, null, null, null);

        ArrayList<Usuarios> listUsuarios= new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listUsuarios.add(transformCursorToUsuarios(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();
        return listUsuarios;
    }


    public Usuarios ValUsuario(Usuarios usu) {
        Cursor cursor = null;
        try {
            cursor = DataBaseSingleton.getInstance().query("EC_TM_USU", null, "USU_LOGIN = ? AND US_CLAVE = ?", new String[]{String.valueOf(usu.getUSusu()),String.valueOf(usu.getUSclave())}, null, null, null, "1");

            if (cursor.moveToFirst())
                usu = transformCursorToUsuarios(cursor);
            else
                usu = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            usu = null;
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            return usu;
        }
    }

    private Usuarios transformCursorToUsuarios(Cursor cursor) {
        Usuarios usu = new Usuarios();
        usu.setUSnombre(cursor.getString(cursor.getColumnIndex("NO_NOMBRES")));
        usu.setUSapellidos(cursor.getString(cursor.getColumnIndex("NO_APELLIDOS")));
        usu.setUSfoto(cursor.getString(cursor.getColumnIndex("NO_FOTO")));
       // usu.setUSusu(cursor.getString(cursor.getColumnIndex("NO_FOTO")));
      //  usu.setUSclave(cursor.getString(cursor.getColumnIndex("NO_FOTO")));
        usu.setUSarea(cursor.getInt(cursor.getColumnIndex("ID_ARE")));
        return usu;
    }

}
