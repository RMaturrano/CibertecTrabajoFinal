package cibertec.pe.proyectofinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by SOPORTE on 31/05/2016.
 */
public class Segunda_Actividad_Login extends AppCompatActivity{

    private EditText etSegundaCorreo, etSegundaClave;
    private Button btSegundaIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segunda_actividad_login);

        etSegundaCorreo = (EditText) findViewById(R.id.etSegundaCorreo);
        etSegundaClave = (EditText) findViewById(R.id.etSegundaClave);
        btSegundaIngresar = (Button) findViewById(R.id.btSegundaIngresar);

        btSegundaIngresar.setOnClickListener(btIngresarOnClickListener);

        if (!PreferenceManager.getDefaultSharedPreferences(Segunda_Actividad_Login.this).getBoolean("ingreso", false))
        {
            PreferenceManager.getDefaultSharedPreferences(Segunda_Actividad_Login.this).edit().putString("correo", "pf@cib.edu.pe").putString("clave", "123456").putBoolean("ingreso", false).commit();
        } else
            Ingresar();

    }

    View.OnClickListener btIngresarOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (etSegundaCorreo.getText().toString().trim().isEmpty()){
                setMesage("Ingrese su correo");
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(etSegundaCorreo.getText().toString().trim()).matches())
            {
                setMesage("Ingrese un correo en formato correcto");
                return;
            }
            if(etSegundaClave.getText().toString().trim().isEmpty()){
                setMesage("Ingrese su clave");
                return;
            }

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Segunda_Actividad_Login.this);

            if(!etSegundaCorreo.getText().toString().trim().equals(sp.getString("correo",""))|| !etSegundaClave.getText().toString().trim().equals(sp.getString("clave",""))){
                setMesage("Correo y/o clave incorrectos");
                return;
            }

            sp.edit().putBoolean("ingreso",true).commit();
            Ingresar();

        }
    };

    private void Ingresar() {
        Intent intent = new Intent(Segunda_Actividad_Login.this,Tercera_Actividad_Principal_Clientes.class);
        startActivity(intent);
        finish();

    }

    public void setMesage(String mesage) {
        new AlertDialog.Builder(Segunda_Actividad_Login.this).setTitle(R.string.app_name).setMessage(mesage).setNegativeButton("Aceptar",null).show();
    }
}
