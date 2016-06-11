package com.trabajo.capturapedido.capturapedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trabajo.capturapedido.capturapedido.entities.BECliente;
import com.trabajo.capturapedido.capturapedido.datos.DACliente;

public class Cliente2Activity extends AppCompatActivity implements OnMapReadyCallback {

    public final static String ARG_CLIENTE = "arg_cliente";
    private Toolbar tb_cliente;

    private TextInputLayout tilNomCliente, tilApellCliente, tilTelfCliente, tilCorreCliente, tilCiaCliente, tilDireccCliente, tilDistCliente, tilRefCliente;

    private GoogleMap mGoogleMap;
    private LatLng mLatLng;

    private BECliente mCliente;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente2_activity);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ARG_CLIENTE)) {
            mCliente = getIntent().getParcelableExtra(ARG_CLIENTE);
            isUpdate = true;
        } else {
            mCliente = null;
            isUpdate = false;
        }

        tilNomCliente = (TextInputLayout) findViewById(R.id.tilNomCliente);
        tilApellCliente = (TextInputLayout) findViewById(R.id.tilApellCliente);
        tilTelfCliente = (TextInputLayout) findViewById(R.id.tilTelfCliente);
        tilCorreCliente = (TextInputLayout) findViewById(R.id.tilCorreoCliente);
        tilCiaCliente = (TextInputLayout) findViewById(R.id.tilCiaCliente);
        tilDireccCliente = (TextInputLayout) findViewById(R.id.tilDireccCliente);
        tilDistCliente = (TextInputLayout) findViewById(R.id.tilDistCliente);
        tilRefCliente = (TextInputLayout) findViewById(R.id.tilRefCliente);

        tb_cliente = (Toolbar) findViewById(R.id.tbAddCliente);
        setSupportActionBar(tb_cliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb_cliente.setTitleTextColor(0xFFFFFFFF);



        if (isUpdate) {
            setData();
            getSupportActionBar().setTitle(mCliente.getCiaCliente());
        }
        else
            getSupportActionBar().setTitle("Nuevo Cliente");


        ((MapFragment) getFragmentManager().findFragmentById(R.id.fracClienteMapa)).getMapAsync(Cliente2Activity.this);
    }


    private void setData() {
        tilNomCliente.getEditText().setText(mCliente.getNomCliente());
        tilApellCliente.getEditText().setText(mCliente.getApellCliente());
        tilTelfCliente.getEditText().setText(mCliente.getTelfCliente());
        tilCorreCliente.getEditText().setText(mCliente.getCorreoCliente());
        tilCiaCliente.getEditText().setText(mCliente.getCiaCliente());
        tilDireccCliente.getEditText().setText(mCliente.getDireccCliente());
        tilDistCliente.getEditText().setText(mCliente.getDistCliente());
        tilRefCliente.getEditText().setText(mCliente.getRefCliente());
        mLatLng = new LatLng(mCliente.getLatitud(), mCliente.getLongitud());
    }

    private void setMarkers() {
        if (mGoogleMap != null) {
            mGoogleMap.clear();
            LatLngBounds.Builder builder = LatLngBounds.builder();
            if (mLatLng != null) {
                builder.include(mLatLng);
                mGoogleMap.addMarker(new MarkerOptions().position(mLatLng));

                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 17));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        this.mGoogleMap.setOnMapClickListener(googleMapOnMapClickListener);
        //googleMap.getUiSettings().setAllGesturesEnabled(false);
        setMarkers();
    }

    GoogleMap.OnMapClickListener googleMapOnMapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            mLatLng = latLng;
            mGoogleMap.clear();
            mGoogleMap.addMarker(new MarkerOptions().position(latLng));
            if (isUpdate)
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

        }
    };

    public void GrabarCliente() {
        boolean isOK = true;

        tilNomCliente.setError(null);
        tilApellCliente.setError(null);
        tilTelfCliente.setError(null);
        tilCorreCliente.setError(null);
        tilCiaCliente.setError(null);
        tilDireccCliente.setError(null);
        tilDistCliente.setError(null);
        tilRefCliente.setError(null);

        tilNomCliente.setErrorEnabled(false);
        tilApellCliente.setErrorEnabled(false);
        tilTelfCliente.setErrorEnabled(false);
        tilCorreCliente.setErrorEnabled(false);
        tilCiaCliente.setErrorEnabled(false);
        tilDireccCliente.setErrorEnabled(false);
        tilDistCliente.setErrorEnabled(false);
        tilRefCliente.setErrorEnabled(false);

        if (tilNomCliente.getEditText().getText().toString().trim().isEmpty()) {
            tilNomCliente.setError("Ingrese su nombre");
            tilNomCliente.setErrorEnabled(true);
            isOK = false;
        }

        if (tilApellCliente.getEditText().getText().toString().trim().isEmpty()) {
            tilApellCliente.setError("Ingrese su apellido");
            tilApellCliente.setErrorEnabled(true);
            isOK = false;
        }
        if (tilTelfCliente.getEditText().getText().toString().trim().isEmpty()) {
            tilTelfCliente.setError("Ingrese su Telefono");
            tilTelfCliente.setErrorEnabled(true);
            isOK = false;
        }
        if (tilCorreCliente.getEditText().getText().toString().trim().isEmpty()) {
            tilCorreCliente.setError("Ingrese su Correo");
            tilCorreCliente.setErrorEnabled(true);
            isOK = false;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(tilCorreCliente.getEditText().getText().toString().trim()).matches()) {
                tilCorreCliente.setError("Ingrese un correo en un formato correcto");
                tilCorreCliente.setErrorEnabled(true);
                isOK = false;
            }
        }

        if (tilCiaCliente.getEditText().getText().toString().trim().isEmpty()) {
            tilCiaCliente.setError("Ingrese su Empresa");
            tilCiaCliente.setErrorEnabled(true);
            isOK = false;
        }
        if (tilDireccCliente.getEditText().getText().toString().trim().isEmpty()) {
            tilDireccCliente.setError("Ingrese su Direccion");
            tilDireccCliente.setErrorEnabled(true);
            isOK = false;
        }
        if (tilDistCliente.getEditText().getText().toString().trim().isEmpty()) {
            tilDistCliente.setError("Ingrese su Distrito");
            tilDistCliente.setErrorEnabled(true);
            isOK = false;
        }
        if (tilRefCliente.getEditText().getText().toString().trim().isEmpty()) {
            tilRefCliente.setError("Ingrese su Referencia");
            tilRefCliente.setErrorEnabled(true);
            isOK = false;
        }

        if (mLatLng == null) {
            Toast.makeText(Cliente2Activity.this, "No ha elegido ubicacion en el mapa", Toast.LENGTH_LONG).show();
            isOK = false;
        }

        if (isOK) {
            if (mCliente == null)
                mCliente = new BECliente();

            mCliente.setNomCliente(tilNomCliente.getEditText().getText().toString().trim());
            mCliente.setApellCliente(tilApellCliente.getEditText().getText().toString().trim());
            mCliente.setTelfCliente(tilTelfCliente.getEditText().getText().toString().trim());
            mCliente.setCorreoCliente(tilCorreCliente.getEditText().getText().toString().trim());
            mCliente.setCiaCliente(tilCiaCliente.getEditText().getText().toString().trim());
            mCliente.setDireccCliente(tilDireccCliente.getEditText().getText().toString().trim());
            mCliente.setDistCliente(tilDistCliente.getEditText().getText().toString().trim());
            mCliente.setRefCliente(tilRefCliente.getEditText().getText().toString().trim());
            mCliente.setLatitud(mLatLng.latitude);
            mCliente.setLongitud(mLatLng.longitude);

            if (isUpdate) {
                boolean isUpdated = new DACliente().updateCliente(mCliente);
                if (isUpdated) {
                    Toast.makeText(Cliente2Activity.this, mCliente.getNomCliente() + " " + mCliente.getApellCliente() + " ha sido actualizdo", Toast.LENGTH_LONG).show();
                    finish();
                } else
                    new AlertDialog.Builder(Cliente2Activity.this).setTitle(R.string.app_name).setMessage("No se pudo actualizar en la base de datos").setNegativeButton("Aceptar", null).show();
            } else {
                boolean isInserted = new DACliente().insertCliente(mCliente);
                if (isInserted) {
                    Toast.makeText(Cliente2Activity.this, mCliente.getNomCliente() + " " + mCliente.getApellCliente() + " ha sido registrado", Toast.LENGTH_LONG).show();
                    finish();
                } else
                    new AlertDialog.Builder(Cliente2Activity.this).setTitle(R.string.app_name).setMessage("No se pudo regristrar en la base de datos").setNegativeButton("Aceptar", null).show();
            }
            Intent intent = new Intent(Cliente2Activity.this, ClientesActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.ab_save) {
            GrabarCliente();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
