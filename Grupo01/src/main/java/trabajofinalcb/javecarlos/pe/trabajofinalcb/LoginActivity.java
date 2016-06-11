package trabajofinalcb.javecarlos.pe.trabajofinalcb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

import dao.DataBaseHelper;
import dao.DataBaseSingleton;
import dao.UsuariosDAO;
import entities.Usuarios;
import utils.Constantes;

/**
 * Created by carlosarmando on 03/05/2016.
 */
public class LoginActivity extends AppCompatActivity {
    EditText edt_usuario, edt_password;
    ImageButton btn_ingresar;
    TextView edt_error;

    String usuario, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_usuario = (EditText) findViewById(R.id.edt_usuario);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_error = (TextView) findViewById(R.id.edt_error);
        btn_ingresar = (ImageButton) findViewById(R.id.btn_ingresar);
        btn_ingresar.setOnClickListener(btnEntrarOnClickListener);

        edt_error.setVisibility(View.GONE);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(LoginActivity.this);
        try {
            dataBaseHelper.createDataBase();
            new DataBaseSingleton(LoginActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
//        if (PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).getBoolean("ingreso", false)) {
//            Usuarios usuarioActual = new Usuarios();
//            usuarioActual.setUSusu(sp.getString("usuario",""));
//            usuarioActual.setUSclave(sp.getString("clave",""));
//            usuarioActual.setUSnombre(sp.getString("nombre",""));
//            usuarioActual.setUSapellidos(sp.getString("apellido",""));
//            usuarioActual.setUSfoto(sp.getString("foto",""));
//            usuarioActual.setUSarea(1);
//
//            Ingresar(usuarioActual);
//        }d
    }

    View.OnClickListener btnEntrarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean isOK = true;
            usuario = edt_usuario.getText().toString();
            password = edt_password.getText().toString();

            if (usuario.trim().isEmpty()) {
                edt_usuario.setError(getResources().getString(R.string.login_texto_error_vacio_usuario));
                isOK = false;
            }

            if (password.trim().isEmpty()) {
                edt_password.setError(getResources().getString(R.string.login_texto_error_vacio_clave));
                isOK = false;
            }

            if (isOK) {//Validacion OK
                Usuarios usu = new Usuarios();
                usu.setUSusu(usuario.trim());
                usu.setUSclave(password.trim());
                Usuarios usu1 = new Usuarios();
                UsuariosDAO ud = new UsuariosDAO();
                usu1 = ud.ValUsuario(usu);
                if (usu1 != null) { //encontro Usuario
                    PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit()
                            .putString("usuario", usuario.trim())
                            .putString("clave", password.trim())
                            .putString("nombre", usu1.getUSnombre())
                            .putString("apellido", usu1.getUSapellidos())
                            .putString("foto", usu1.getUSfoto())
                            .putBoolean("ingreso", true)
                    .commit();

                    Ingresar(usu1);
                } else {
                    edt_error.setVisibility(View.VISIBLE);
                    edt_error.setText(getResources().getString(R.string.login_texto_error_credenciales_incorrectas));
                }
            }
        }

    };

    private void Ingresar(Usuarios usu1) {
        Intent intent = new Intent(LoginActivity.this, ClienteActivity.class);
        intent.putExtra(Constantes.ARG_USUARIO, usu1);
//        startActivityForResult(intent, 99);
        startActivity(intent);
        finish();
    }
}
