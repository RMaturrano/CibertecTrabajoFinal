package com.project.cibertec.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.cibertec.finalproject.cliente.ClientesListaActivity;
import com.project.cibertec.finalproject.dao.UsuarioDAO;
import com.project.cibertec.finalproject.entities.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLoginUsuario;
    private EditText edtLoginPassword;
    private Button btnLoginIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtLoginUsuario = (EditText) findViewById(R.id.edtLoginUsuario);
        edtLoginPassword = (EditText) findViewById(R.id.edtLoginPassword);
        btnLoginIngresar = (Button) findViewById(R.id.btnLoginIngresar);

        btnLoginIngresar.setOnClickListener(btnLoginIngresarOnClick);

        if (!PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).getBoolean("ingreso", false)) {
        } else
            Ingresar();

    }

    View.OnClickListener btnLoginIngresarOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (validarCampos()) {

                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setIndeterminate(true);
                pd.setCancelable(false);
                pd.setMessage("Validando, espere por favor...");
                pd.show();

                Thread timerThread = new Thread() {
                    public void run() {
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {

                            Usuario usuario = new Usuario();
                            usuario.setUsuario(edtLoginUsuario.getText().toString());

                            usuario = new UsuarioDAO().getUsuari(usuario);

                            if (edtLoginUsuario.getText().toString().trim().equals(usuario.getUsuario()) &&
                                    edtLoginPassword.getText().toString().trim().equals(usuario.getClave())) {
                                PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                        .edit()
                                        .putString("usuario", usuario.getUsuario())
                                        .putString("clave", usuario.getClave())
                                        .putString("nombre", usuario.getNombre() + " " + usuario.getApellido())
                                        .putString("correo", usuario.getCorreo())
                                        .putBoolean("ingreso", true).commit();
                                setMessage("Bienvenido");
                                pd.dismiss();
                                Ingresar();
                            } else {
                                setMessage("Usuario y/o password incorrectos");
                                pd.dismiss();
                                return;
                            }

                        }
                    }
                };
                timerThread.start();
            }
        }
    };


    private boolean validarCampos() {
        boolean res = true;

        if (TextUtils.isEmpty(edtLoginUsuario.getText().toString().trim())) {
            edtLoginUsuario.setError("Ingrese su usuario");
            res = false;
        } else if (TextUtils.isEmpty(edtLoginPassword.getText())) {
            edtLoginPassword.setError("Ingrese su password");
            res = false;
        } else if (edtLoginPassword.getText().length() < 4) {
            edtLoginPassword.setError("El password debe ser de 4 dígitos");
            res = false;
        }

        return res;
    }


    private void Ingresar() {
        Intent intent = new Intent(LoginActivity.this, ClientesListaActivity.class);
        startActivity(intent);
        finish();
    }


    private void setMessage(final String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
