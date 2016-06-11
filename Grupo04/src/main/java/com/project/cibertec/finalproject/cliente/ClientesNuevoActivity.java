package com.project.cibertec.finalproject.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.cibertec.finalproject.R;
import com.project.cibertec.finalproject.dao.ClienteDAO;
import com.project.cibertec.finalproject.entities.Cliente;

public class ClientesNuevoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private EditText edtCliNuevoNombre, edtCliNuevoApellido, edtCliNuevoTlf, edtCliNuevoCorreo,
            edtCliNuevoEmpresa, edtCliNuevoDireccion, edtCliNuevoDistrito, edtCliNuevoReferencia;
    private GoogleMap mGoogleMap;
    private Cliente mCliente;
    private boolean isUpdate;
    private LatLng mLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientes_nuevo_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragMap);
        mapFragment.getMapAsync(ClientesNuevoActivity.this);

        edtCliNuevoNombre = (EditText) findViewById(R.id.edtCliNuevoNombre);
        edtCliNuevoApellido = (EditText) findViewById(R.id.edtCliNuevoApellido);
        edtCliNuevoTlf = (EditText) findViewById(R.id.edtCliNuevoTlf);
        edtCliNuevoCorreo = (EditText) findViewById(R.id.edtCliNuevoCorreo);
        edtCliNuevoEmpresa = (EditText) findViewById(R.id.edtCliNuevoEmpresa);
        edtCliNuevoDireccion = (EditText) findViewById(R.id.edtCliNuevoDireccion);
        edtCliNuevoDistrito = (EditText) findViewById(R.id.edtCliNuevoDistrito);
        edtCliNuevoReferencia = (EditText) findViewById(R.id.edtCliNuevoReferencia);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("cliente")) {
            mCliente = (Cliente) getIntent().getExtras().get("cliente");
            isUpdate = true;
            setTitle(mCliente.getEmpresa());
            fillData();
        } else {
            mCliente = null;
            isUpdate = false;
            setTitle(R.string.tituloClienteNuevo);
        }
    }

    private void fillData() {
        edtCliNuevoNombre.setText(mCliente.getNombre());
        edtCliNuevoApellido.setText(mCliente.getApellido());
        edtCliNuevoTlf.setText(mCliente.getTelefono());
        edtCliNuevoCorreo.setText(mCliente.getCorreo());
        edtCliNuevoEmpresa.setText(mCliente.getEmpresa());
        edtCliNuevoDireccion.setText(mCliente.getDireccion());
        edtCliNuevoDistrito.setText(mCliente.getDistrito());
        edtCliNuevoReferencia.setText(mCliente.getReferencia());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Capturo el click de la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else  //Capturo el click del guardar
            if (item.getItemId() == R.id.abSecondSave) {
                save();
                return true;
            }
        return super.onOptionsItemSelected(item);
    }


    private void save() {

        boolean isOK = checkTexts();

        if (isOK) {
            if (mCliente == null)
                mCliente = new Cliente();

            mCliente.setNombre(edtCliNuevoNombre.getText().toString().trim());
            mCliente.setApellido(edtCliNuevoApellido.getText().toString().trim());
            mCliente.setTelefono(edtCliNuevoTlf.getText().toString().trim());
            mCliente.setCorreo(edtCliNuevoCorreo.getText().toString().trim());
            mCliente.setEmpresa(edtCliNuevoEmpresa.getText().toString().trim());
            mCliente.setDireccion(edtCliNuevoDireccion.getText().toString().trim());
            mCliente.setDistrito(edtCliNuevoDistrito.getText().toString().trim());
            mCliente.setReferencia(edtCliNuevoReferencia.getText().toString().trim());
            mCliente.setLatitud(String.valueOf(mLatLng.latitude));
            mCliente.setLongitud(String.valueOf(mLatLng.longitude));
            //
            if (isUpdate) {
                boolean isUpdated = new ClienteDAO().updateCliente(mCliente);
                if (isUpdated) {
                    Toast.makeText(ClientesNuevoActivity.this, mCliente.getNombre() + " " + mCliente.getApellido() + " ha sido actualizdo", Toast.LENGTH_LONG).show();
                    finish();
                } else
                    new AlertDialog.Builder(ClientesNuevoActivity.this).setTitle(R.string.app_name).setMessage("No se pudo actualizar en la base de datos").setNegativeButton("Aceptar", null).show();
            } else {
                boolean isInserted = new ClienteDAO().insertCliente(mCliente);
                if (isInserted) {
                    Toast.makeText(ClientesNuevoActivity.this, mCliente.getNombre() + " " + mCliente.getApellido() + " ha sido registrado", Toast.LENGTH_LONG).show();
                    finish();
                } else
                    new AlertDialog.Builder(ClientesNuevoActivity.this).setTitle(R.string.app_name).setMessage("No se pudo regristrar en la base de datos").setNegativeButton("Aceptar", null).show();
            }
        }

    }

    private boolean checkTexts(){

        if(TextUtils.isEmpty(edtCliNuevoNombre.getText().toString().trim())){
            edtCliNuevoNombre.setError("Campo obligatorio");
            Snackbar.make(getCurrentFocus(),"Ingrese el nombre del cliente",Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edtCliNuevoApellido.getText().toString().trim())){
            edtCliNuevoApellido.setError("Campo obligatorio");
            Snackbar.make(getCurrentFocus(),"Ingrese el apellido del cliente",Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edtCliNuevoTlf.getText().toString().trim())){
            edtCliNuevoTlf.setError("Campo obligatorio");
            Snackbar.make(getCurrentFocus(),"Ingrese el teléfono del cliente",Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(edtCliNuevoTlf.getText().toString().trim().length() < 7){
            edtCliNuevoTlf.setError("Teléfono inválido");
            Snackbar.make(getCurrentFocus(),"El teléfono debe tener siete números como mínimo",Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edtCliNuevoCorreo.getText().toString().trim())){
            edtCliNuevoCorreo.setError("Campo obligatorio");
            Snackbar.make(getCurrentFocus(),"Ingrese el correo del cliente",Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edtCliNuevoEmpresa.getText().toString().trim())){
            edtCliNuevoEmpresa.setError("Campo obligatorio");
            Snackbar.make(getCurrentFocus(),"Ingrese el nombre de la empresa del cliente",Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edtCliNuevoDireccion.getText().toString().trim())){
            edtCliNuevoDireccion.setError("Campo obligatorio");
            Snackbar.make(getCurrentFocus(),"Ingrese la dirección de la empresa del cliente",Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edtCliNuevoDistrito.getText().toString().trim())){
            edtCliNuevoDistrito.setError("Campo obligatorio");
            Snackbar.make(getCurrentFocus(),"Ingrese el distrito de la empresa del cliente",Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edtCliNuevoReferencia.getText().toString().trim())){
            edtCliNuevoReferencia.setError("Campo obligatorio");
            Snackbar.make(getCurrentFocus(),"Ingrese la referencia de la empresa del cliente",Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(mLatLng == null){
            Snackbar.make(getCurrentFocus(),"Seleccione en el mapa la ubicación del cliente",Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.setOnMapClickListener(mGoogleMapOnMapClickListener);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.087, -77.049489), 17));
    }


    GoogleMap.OnMapClickListener mGoogleMapOnMapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            mLatLng = latLng;
            mGoogleMap.clear();
            mGoogleMap.addMarker(new MarkerOptions().position(latLng));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

        }
    };

}
