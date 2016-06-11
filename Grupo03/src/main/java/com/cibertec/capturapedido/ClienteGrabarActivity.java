package com.cibertec.capturapedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cibertec.capturapedido.dao.ClienteDAO;
import com.cibertec.capturapedido.entities.Cliente;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by bestrada on 17/05/2016.
 */
public class ClienteGrabarActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String ARG_CLIENTE = "arg_cliente";

    TextInputLayout tilClienteNombre, tilClienteApellido, tilClienteTelf,
            tilClienteCorreo, tilClienteEmpresa, tilClienteDireccion, tilClienteDistrito, tilClienteReferencia;

    Cliente mCliente;
    GoogleMap mGoogleMap;
    MarkerOptions marker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_grabar_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.app_title_tollbar_ncliente);

        mCliente = null;
        marker = null;

        if (getIntent().getParcelableExtra(ARG_CLIENTE) != null) {
            mCliente = getIntent().getParcelableExtra(ARG_CLIENTE);
            getSupportActionBar().setTitle(mCliente.getEmpresa());
        }
        tilClienteNombre = (TextInputLayout) findViewById(R.id.tilClienteNombre);
        tilClienteApellido = (TextInputLayout) findViewById(R.id.tilclienteApellido);
        tilClienteTelf = (TextInputLayout) findViewById(R.id.tilClienteTelf);
        tilClienteCorreo = (TextInputLayout) findViewById(R.id.tilClienteCorreo);
        tilClienteEmpresa = (TextInputLayout) findViewById(R.id.tilClienteEmpresa);
        tilClienteDireccion = (TextInputLayout) findViewById(R.id.tilClienteDireccion);
        tilClienteDistrito = (TextInputLayout) findViewById(R.id.tilClienteDistrito);
        tilClienteReferencia = (TextInputLayout) findViewById(R.id.tilClienteReferencia);

        if (mCliente != null)
            setData();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragClienteGrabarMap);
        mapFragment.getMapAsync(ClienteGrabarActivity.this);
    }

    private void setData() {
        tilClienteNombre.getEditText().setText(mCliente.getNombre());
        tilClienteApellido.getEditText().setText(mCliente.getApellido());
        tilClienteTelf.getEditText().setText(mCliente.getTelefono());
        tilClienteCorreo.getEditText().setText(mCliente.getCorreo());
        tilClienteEmpresa.getEditText().setText(mCliente.getEmpresa());
        tilClienteDireccion.getEditText().setText(mCliente.getDireccion());
        tilClienteDistrito.getEditText().setText(mCliente.getDistrito());
        tilClienteReferencia.getEditText().setText(mCliente.getReferencia());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cliente_grabar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void save() {
        boolean isOK = true;

        tilClienteNombre.setError(null);
        tilClienteApellido.setError(null);
        tilClienteTelf.setError(null);
        tilClienteCorreo.setError(null);
        tilClienteEmpresa.setError(null);
        tilClienteDireccion.setError(null);
        tilClienteDistrito.setError(null);
        tilClienteReferencia.setError(null);
        tilClienteNombre.setErrorEnabled(false);
        tilClienteApellido.setErrorEnabled(false);
        tilClienteTelf.setErrorEnabled(false);
        tilClienteCorreo.setErrorEnabled(false);
        tilClienteEmpresa.setErrorEnabled(false);
        tilClienteDireccion.setErrorEnabled(false);
        tilClienteDistrito.setErrorEnabled(false);
        tilClienteReferencia.setErrorEnabled(false);

        if (tilClienteNombre.getEditText().getText().toString().trim().isEmpty()) {
            tilClienteNombre.setError("Ingrese su Nombre");
            tilClienteNombre.setErrorEnabled(true);
            isOK = false;
        }
        if (tilClienteApellido.getEditText().getText().toString().trim().isEmpty()) {
            tilClienteApellido.setError("Ingrese su Apellido");
            tilClienteApellido.setErrorEnabled(true);
            isOK = false;
        }
        if (tilClienteTelf.getEditText().getText().toString().trim().isEmpty()) {
            tilClienteTelf.setError("Ingrese su Telefono");
            tilClienteTelf.setErrorEnabled(true);
            isOK = false;
        } else {
            if (tilClienteTelf.getEditText().getText().toString().trim().length() != 9 && tilClienteTelf.getEditText().getText().toString().trim().length() != 7) {
                tilClienteTelf.setError("Su número debe ser de 7 o 9 digitos");
                tilClienteTelf.setErrorEnabled(true);
                isOK = false;
            }
        }
        if (tilClienteCorreo.getEditText().getText().toString().trim().isEmpty()) {
            tilClienteCorreo.setError("Ingrese su Correo");
            tilClienteCorreo.setErrorEnabled(true);
            isOK = false;
        }
        if (tilClienteEmpresa.getEditText().getText().toString().trim().isEmpty()) {
            tilClienteEmpresa.setError("Ingrese su Nombre de su empresa");
            tilClienteEmpresa.setErrorEnabled(true);
            isOK = false;
        }
        if (tilClienteDireccion.getEditText().getText().toString().trim().isEmpty()) {
            tilClienteDireccion.setError("Ingrese su Dirección");
            tilClienteDireccion.setErrorEnabled(true);
            isOK = false;
        }
        if (tilClienteReferencia.getEditText().getText().toString().trim().isEmpty()) {
            tilClienteReferencia.setError("Ingrese la Referencia");
            tilClienteReferencia.setErrorEnabled(true);
            isOK = false;
        }
        if (isOK) {
            boolean estado = true;//Varible de validación
            if (mCliente == null) mCliente = new Cliente(); //Si es un cliente Nuevo

            mCliente.setNombre(tilClienteNombre.getEditText().getText().toString().trim());
            mCliente.setApellido(tilClienteApellido.getEditText().getText().toString().trim());
            mCliente.setTelefono(tilClienteTelf.getEditText().getText().toString().trim());
            mCliente.setCorreo(tilClienteCorreo.getEditText().getText().toString().trim());
            mCliente.setEmpresa(tilClienteEmpresa.getEditText().getText().toString().trim());
            mCliente.setDireccion(tilClienteDireccion.getEditText().getText().toString().trim());
            mCliente.setDistrito(tilClienteDistrito.getEditText().getText().toString().trim());
            mCliente.setReferencia(tilClienteReferencia.getEditText().getText().toString().trim());

            if (marker != null) {
                mCliente.setLatitud(marker.getPosition().latitude);
                mCliente.setLongitud(marker.getPosition().longitude);
            }

            if (mCliente.getClienteId() > 0) {//si el cliente ya esta registrado
                if (!new ClienteDAO().updateCliente(mCliente)) estado = false;
            } else if (!new ClienteDAO().insertCliente(mCliente)) estado = false;//Nuevo Cliente

            if (estado) {//Registro exitoso
                Toast.makeText(ClienteGrabarActivity.this, "Se guardó correctamente", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, new Intent().putExtra(ARG_CLIENTE, mCliente));
                finish();
            } else {
                Toast.makeText(ClienteGrabarActivity.this, "Error en Guardar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        else if (item.getItemId() == R.id.ab_save) save();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        this.mGoogleMap.setOnMapClickListener(mGoogleMapOnMapClickListener);

        if (mCliente != null) {
            if (mCliente.getLatitud() != 0 && mCliente.getLongitud() != 0) {
                mGoogleMap.clear();
                LatLng latLng = new LatLng(mCliente.getLatitud(), mCliente.getLongitud());
                marker = new MarkerOptions();
                marker.position(latLng);
                mGoogleMap.addMarker(marker);
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            }
        }
    }

    GoogleMap.OnMapClickListener mGoogleMapOnMapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            if (marker == null) {
                marker = new MarkerOptions();
            }
            mGoogleMap.clear();
            marker.position(latLng);
            mGoogleMap.addMarker(marker);
            //mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
        }
    };
}
