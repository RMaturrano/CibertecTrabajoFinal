package com.cibertec.capturapedido;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cibertec.capturapedido.dao.DataBaseHelper;
import com.cibertec.capturapedido.dao.DataBaseSingleton;
import com.cibertec.capturapedido.dao.UsuarioDAO;
import com.cibertec.capturapedido.entities.Usuario;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginUsuario, etLoginClave;
    private Button btLoginIngresar;
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(LoginActivity.this);
        try {
            dataBaseHelper.createDataBase();
            new DataBaseSingleton(LoginActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        etLoginUsuario = (EditText) findViewById(R.id.etLoginUsuario);
        etLoginClave = (EditText) findViewById(R.id.etLoginClave);

        //etLoginUsuario.setOnKeyListener(enterKeyListener);
        etLoginClave.setOnKeyListener(enterKeyListener);

        btLoginIngresar = (Button) findViewById(R.id.btLoginIngresar);
        btLoginIngresar.setOnClickListener(btLoginIngresarOnClickListener);

        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        if (preferences.getBoolean("ingreso", false)) {
            Ingresar();
        }
    }

    View.OnKeyListener enterKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == 66){
                btLoginIngresarOnClickListener.onClick(v);
                return true;
            }
            return false;
        }
    };

    View.OnClickListener btLoginIngresarOnClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String oUsuario = etLoginUsuario.getText().toString().trim(),
                    oClave = etLoginClave.getText().toString().trim();

            if (oUsuario.isEmpty()) {
                setMessage("Ingrese su usuario");
                return;
            }

            if (oClave.isEmpty()) {
                setMessage("Ingrese su clave");
                return;
            }

            Usuario usuario = new UsuarioDAO().getUsuario(oUsuario);

            if(usuario != null){
                if(usuario.getClave().equals(oClave)){
                    preferences.edit()
                            .putString("nombre", usuario.getNombre())
                            .putString("apellido", usuario.getApellido())
                            .putBoolean("ingreso", true)
                            .commit();

                    Ingresar();
                }else{
                    setMessage("Su clave es incorrecta");
                }
            }else{
                setMessage("Usuario no existe");
            }
        }
    };

    private void Ingresar() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setMessage(String mesage) {
        new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.app_name).setMessage(mesage).setNegativeButton("Aceptar", null).show();
    }
}
