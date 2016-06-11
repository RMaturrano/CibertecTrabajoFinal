package cibertec.pe.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cibertec.pe.proyectofinal.dao.ClienteDAO;
import cibertec.pe.proyectofinal.entities.Cliente;

/**
 * Created by JOSE on 03/06/2016.
 */
public class ClienteNuevoActivity extends AppCompatActivity {

    private TextInputLayout tilCNANombre, tilCNAApellido, tilCNATelefono, tilCNACorreo;
    private TextInputLayout tilCNAEmpresa, tilCNADireccion, tilCNADistrito, tilCNAReferencia;
    private TextInputLayout tilCNALatitud, tilCNALongitud;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_nuevo_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cliente_nuevo_activity);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tilCNANombre = (TextInputLayout) findViewById(R.id.tilCNANombre);
        tilCNAApellido = (TextInputLayout) findViewById(R.id.tilCNAApellido);
        tilCNATelefono = (TextInputLayout) findViewById(R.id.tilCNATelefono);
        tilCNACorreo = (TextInputLayout) findViewById(R.id.tilCNACorreo);
        tilCNAEmpresa = (TextInputLayout) findViewById(R.id.tilCNAEmpresa);
        tilCNADireccion = (TextInputLayout) findViewById(R.id.tilCNADireccion);
        tilCNADistrito = (TextInputLayout) findViewById(R.id.tilCNADistrito);
        tilCNAReferencia = (TextInputLayout) findViewById(R.id.tilCNAReferencia);
        tilCNALatitud = (TextInputLayout) findViewById(R.id.tilCNALatitud);
        tilCNALongitud = (TextInputLayout) findViewById(R.id.tilCNALongitud);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cliente_nuevo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Capturo el click de la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else  //Capturo el click del guardar
            if (item.getItemId() == R.id.cliente_nuevo_menu_grabar) {
                save();
                return true;
            }

        return super.onOptionsItemSelected(item);
    }

    private void save() {
        if (tilCNANombre.getEditText().getText().toString().trim().isEmpty())
            showMessage("Ingrese su nombre");
        else if (tilCNAApellido.getEditText().getText().toString().trim().isEmpty())
            showMessage("Ingrese su apellido");
        else if (tilCNATelefono.getEditText().getText().toString().trim().isEmpty())
            showMessage("Ingrese su teléfono");
        else if (tilCNACorreo.getEditText().getText().toString().trim().isEmpty())
            showMessage("Ingrese su correo");
        else if (tilCNAEmpresa.getEditText().getText().toString().trim().isEmpty())
            showMessage("Ingrese el nombre de la empresa");
        else if (tilCNADireccion.getEditText().getText().toString().trim().isEmpty())
            showMessage("Ingrese la dirección");
        else if (tilCNADistrito.getEditText().getText().toString().trim().isEmpty())
            showMessage("Ingrese el distrito");
        else if (tilCNAReferencia.getEditText().getText().toString().trim().isEmpty())
            showMessage("Ingrese la referencia");
        else if (tilCNALatitud.getEditText().getText().toString().trim().isEmpty())
            showMessage("Ingrese la latitud");
        else if (tilCNALongitud.getEditText().getText().toString().trim().isEmpty())
            showMessage("Ingrese la longitud");
        else {
            Cliente cliente = new Cliente();
            cliente.setNombre(tilCNANombre.getEditText().getText().toString().trim());
            cliente.setApellido(tilCNAApellido.getEditText().getText().toString().trim());
            cliente.setTelefono(tilCNATelefono.getEditText().getText().toString().trim());
            cliente.setCorreo(tilCNACorreo.getEditText().getText().toString().trim());
            cliente.setEmpresa(tilCNAEmpresa.getEditText().getText().toString().trim());
            cliente.setDireccion(tilCNADireccion.getEditText().getText().toString().trim());
            cliente.setDistrito(tilCNADistrito.getEditText().getText().toString().trim());
            cliente.setReferencia(tilCNAReferencia.getEditText().getText().toString().trim());
            cliente.setLatitud(Double.parseDouble(tilCNALatitud.getEditText().getText().toString().trim()));
            cliente.setLongitud(Double.parseDouble(tilCNALongitud.getEditText().getText().toString().trim()));

            boolean isInserted = new ClienteDAO().insertCliente(cliente);
            if (isInserted) {
                Toast.makeText(ClienteNuevoActivity.this, cliente.getEmpresa() + " " + " ha sido registrado", Toast.LENGTH_LONG).show();
                finish();
            } else {
                new AlertDialog.Builder(ClienteNuevoActivity.this).setTitle(R.string.app_name).setMessage("No se pudo regristrar en la base de datos").setNegativeButton("Aceptar", null).show();
            }

            Intent intent = new Intent();
            intent.putExtra("cliente", cliente);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void showMessage(String message) {
        new AlertDialog.Builder(ClienteNuevoActivity.this).setTitle(R.string.app_name).setMessage(message).setPositiveButton("Aceptar", null).show();
    }
}
