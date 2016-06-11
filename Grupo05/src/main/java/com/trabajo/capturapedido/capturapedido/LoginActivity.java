package com.trabajo.capturapedido.capturapedido;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Shutten on 04/06/2016.
 */
public class LoginActivity extends Activity {

    public static final String SESSION_PREFERENCE = "logout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Button btingresar= (Button) findViewById(R.id.btingresar);

        SharedPreferences settings = getSharedPreferences(SESSION_PREFERENCE, 0);
        if (settings.getString("logged", "").toString().equals("logged")) {
            Intent intent = new Intent(LoginActivity.this, ClientesActivity.class);
            startActivity(intent);
            finish();
        }



        btingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etusuario= (EditText) findViewById(R.id.etusuario);
                EditText etpassword= (EditText) findViewById(R.id.etpassword);

                if (etusuario.getText().toString().length()>0 && etpassword.getText().toString().length()>0)
                {

                    if (etusuario.getText().toString().equals("test") && etpassword.getText().toString().equals("test"))
                    {

                        SharedPreferences settings = getSharedPreferences(SESSION_PREFERENCE, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("logged", "logged");
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, ClientesActivity.class);
                        startActivity(intent);
                        finish();


                    }

                    else
                    {
                        Context context = getApplicationContext();
                        CharSequence text = "El usuario y contraseña son incorrectos";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();


                    }


                }


            }
        });


    }
}
