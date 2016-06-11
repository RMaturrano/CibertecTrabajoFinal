package trabajofinalcb.javecarlos.pe.trabajofinalcb;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import dao.ClienteDAO;
import entities.Cliente;
import utils.Constantes;
import utils.Utils;

/**
 * Created by carlosarmando on 09/05/2016.
 */
public class ClienteAddEditActivity extends AppCompatActivity implements OnMapReadyCallback {
    private EditText etContactoNombre, etContactoApellido, etContactoTelefono, etContactoCorreo, etClienteNombre,
            etClienteDireccion, etClienteDistrito, etClienteReferencia;
    private Toolbar toolbar;

    Cliente cliente;
    boolean esNuevo;
    private GoogleMap mGoogleMap;
    private LatLng mLatLng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_add_edit);

        CargarEditText();

        cliente = ObtenerCliente();
        esNuevo = VerificarEsNuevo();

        //para el toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!esNuevo && cliente != null) {
            SetDataCliente(cliente);
            getSupportActionBar().setTitle(getResources().getString(R.string.texto_detalle_cliente));
        } else {
            getSupportActionBar().setTitle(getResources().getString(R.string.texto_nuevo_cliente));
        }

        //Obtenego el fragmento del tipo mama declarado en mi Layout
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragMap);

        //Le digo al fragmento mapa que cuando ya esté listo me avise
        mapFragment.getMapAsync(ClienteAddEditActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el menú que va a aparecer en el Toolbar
        getMenuInflater().inflate(R.menu.menu_add_edit_guardar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Capturo el click de la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else  //Capturo el click del guardar
            if (item.getItemId() == R.id.btnGuardar) {
                GuardarDatos();
                return true;
            }

        return super.onOptionsItemSelected(item);
    }

    private void GuardarDatos(){
        String mensaje = ValidarDatos();

        if (mensaje.isEmpty()) {
            if (cliente == null)
                cliente = new Cliente();
            cliente.setContactoNombre(etContactoNombre.getText().toString().trim());
            cliente.setContactoApellido(etContactoApellido.getText().toString().trim());
            cliente.setContactoTelefono(Integer.parseInt(etContactoTelefono.getText().toString().trim()));
            cliente.setContactoCorreo(etContactoCorreo.getText().toString().trim());
            cliente.setEmpresaNombre(etClienteNombre.getText().toString().trim());
            cliente.setEmpresaDireccion(etClienteDireccion.getText().toString().trim());
            cliente.setEmpresaDistrito(etClienteDistrito.getText().toString().trim());
            cliente.setEmpresaReferencia(etClienteReferencia.getText().toString().trim());
            cliente.setEmpresaLatitud(String.valueOf(mLatLng.latitude));
            cliente.setEmpresaLongitud(String.valueOf(mLatLng.longitude));

                /*Logica para Guardar Cliente*/
            if (esNuevo) {
                //Insert
                boolean isInserted = new ClienteDAO().insertCliente(cliente);
                if (isInserted) {
                    Toast.makeText(ClienteAddEditActivity.this, cliente.getEmpresaNombre() + " " +
                            getResources().getString(R.string.texto_mensaje_insertado), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ClienteAddEditActivity.this, ClienteActivity.class);
                    startActivity(intent);
                    finish();
                    if (checkCallPermission()) {
                        String msj = getResources().getString(R.string.mensaje_sms_inicio) + " " +
                                cliente.getEmpresaNombre() + " " + getResources().getString(R.string.mensaje_sms_fin);
                        sendSMS(etContactoTelefono.getText().toString().trim(),msj);
                    }
                } else {
                    String errorInsercion = getResources().getString(R.string.error_insertar_cliente);
                    String btnAceptar = getResources().getString(R.string.btnAceptar);
                    new AlertDialog.Builder(ClienteAddEditActivity.this).setTitle(R.string.app_name)
                            .setMessage(errorInsercion).setNegativeButton(btnAceptar, null).show();
                }
            } else {
                //Update
                boolean isUpdated = new ClienteDAO().updateCliente(cliente);
                if (isUpdated) {
                    Toast.makeText(ClienteAddEditActivity.this, cliente.getEmpresaNombre() + " " +
                            getResources().getString(R.string.texto_mensaje_actualizado), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ClienteAddEditActivity.this, ClienteActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String errorActualizar = getResources().getString(R.string.error_actualizar_cliente);
                    String btnAceptar = getResources().getString(R.string.btnAceptar);
                    new AlertDialog.Builder(ClienteAddEditActivity.this).setTitle(R.string.app_name)
                            .setMessage(errorActualizar).setNegativeButton(btnAceptar, null).show();
                }
            }
        } else {
            String btnAceptar = getResources().getString(R.string.btnAceptar);
            new AlertDialog.Builder(ClienteAddEditActivity.this).setTitle(R.string.mensaje_error_cliente)
                    .setMessage(mensaje).setNeutralButton(btnAceptar, null).show();
        }
    }
    private void sendSMS(String phoneNumber, String message){
        SmsManager sms =SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null,message,null,null);
    }
    private boolean checkCallPermission() {
        String permission = "android.permission.SEND_SMS";
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    private String ValidarDatos() {
        String resultado = "";

        if (etContactoNombre.getText().toString().trim().isEmpty()) {
            resultado = getResources().getString(R.string.error_vacio_nombre_contacto);
            return resultado;
        }

        if (etContactoNombre.getText().toString().trim().length() > 50) {
            resultado = getResources().getString(R.string.error_maxlength_nombre_contacto);
            return resultado;
        }

        if (etContactoApellido.getText().toString().trim().isEmpty()) {
            resultado = getResources().getString(R.string.error_vacio_apellido_contacto);
            return resultado;
        }

        if (etContactoApellido.getText().toString().trim().length() > 50) {
            resultado = getResources().getString(R.string.error_maxlength_apellido_contacto);
            return resultado;
        }

        if (etContactoTelefono.getText().toString().trim().isEmpty()) {
            resultado = getResources().getString(R.string.error_vacio_telefono_contacto);
            return resultado;
        }

        if (etContactoTelefono.getText().toString().trim().length() < 9) {
            resultado = getResources().getString(R.string.error_maxlength_telefono_contacto);
            return resultado;
        }

        if (etContactoCorreo.getText().toString().trim().isEmpty()) {
            resultado = getResources().getString(R.string.error_vacio_correo_contacto);
            return resultado;
        }

        if (!Utils.IsEmailValid(etContactoCorreo.getText().toString().trim())) {
            resultado = getResources().getString(R.string.error_formato_correo_contacto);
            return resultado;
        }

        if (etContactoCorreo.getText().toString().trim().length() > 50) {
            resultado = getResources().getString(R.string.error_maxlength_correo_contacto);
            return resultado;
        }

        if (etClienteNombre.getText().toString().trim().isEmpty()) {
            resultado = getResources().getString(R.string.error_vacio_nombre_empresa);
            return resultado;
        }

        if (etClienteNombre.getText().toString().trim().length() > 50) {
            resultado = getResources().getString(R.string.error_maxlength_nombre_empresa);
            return resultado;
        }

        if (etClienteDireccion.getText().toString().trim().isEmpty()) {
            resultado = getResources().getString(R.string.error_vacio_direccion_empresa);
            return resultado;
        }

        if (etClienteDireccion.getText().toString().trim().length() > 50) {
            resultado = getResources().getString(R.string.error_maxlength_direccion_empresa);
            return resultado;
        }

        if (etClienteDistrito.getText().toString().trim().isEmpty()) {
            resultado = getResources().getString(R.string.error_vacio_distrito_empresa);
            return resultado;
        }

        if (etClienteDistrito.getText().toString().trim().length() > 50) {
            resultado = getResources().getString(R.string.error_maxlength_distrito_empresa);
            return resultado;
        }

        if (etClienteReferencia.getText().toString().trim().isEmpty()) {
            resultado = getResources().getString(R.string.error_vacio_referencia_empresa);
            return resultado;
        }

        if (etClienteReferencia.getText().toString().trim().length() > 50) {
            resultado = getResources().getString(R.string.error_maxlength_referencia_empresa);
            return resultado;
        }

        if (mLatLng == null){
            resultado = getResources().getString(R.string.error_vacio_mapa_empresa);
        }

        return resultado;
    }

    private void SetDataCliente(Cliente cliente) {
        etContactoNombre.setText(cliente.getContactoNombre());
        etContactoApellido.setText(cliente.getContactoApellido());
        etContactoTelefono.setText(String.valueOf(cliente.getContactoTelefono()));
        etContactoCorreo.setText(cliente.getContactoCorreo());
        etClienteNombre.setText(cliente.getEmpresaNombre());
        etClienteDireccion.setText(cliente.getEmpresaDireccion());
        etClienteDistrito.setText(cliente.getEmpresaDistrito());
        etClienteReferencia.setText(cliente.getEmpresaReferencia());
    }

    private Cliente ObtenerCliente() {
        Cliente clienteInfo;
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constantes.ARG_CLIENTE)) {
            clienteInfo = getIntent().getParcelableExtra(Constantes.ARG_CLIENTE);
        } else {
            clienteInfo = null;
        }

        return clienteInfo;
    }

    private boolean VerificarEsNuevo() {
        boolean resultado;

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constantes.ARG_NUEVO_CLIENTE)) {
            resultado = getIntent().getBooleanExtra(Constantes.ARG_NUEVO_CLIENTE, true);
        } else {
            resultado = true;
        }

        return resultado;
    }

    private void CargarEditText() {
        etContactoNombre = (EditText) findViewById(R.id.etContactoNombre);
        etContactoApellido = (EditText) findViewById(R.id.etContactoApellido);
        etContactoTelefono = (EditText) findViewById(R.id.etContactoTelefono);
        etContactoCorreo = (EditText) findViewById(R.id.etContactoCorreo);
        etClienteNombre = (EditText) findViewById(R.id.etClienteNombre);
        etClienteDireccion = (EditText) findViewById(R.id.etClienteDireccion);
        etClienteDistrito = (EditText) findViewById(R.id.etClienteDistrito);
        etClienteReferencia = (EditText) findViewById(R.id.etClienteReferencia);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        this.mGoogleMap.setOnMapClickListener(googleMapOnMapClickListener);
        //googleMap.getUiSettings().setAllGesturesEnabled(false);
        setMarker();
    }

    GoogleMap.OnMapClickListener googleMapOnMapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            mLatLng = latLng;
            mGoogleMap.clear();
            mGoogleMap.addMarker(new MarkerOptions().position(latLng));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }
    };

    private void setMarker(){
        if (cliente != null){
            mGoogleMap.clear();

            LatLng marker = new LatLng(Double.parseDouble(cliente.getEmpresaLatitud()),Double.parseDouble(cliente.getEmpresaLongitud()));
            mLatLng = marker;
            mGoogleMap.addMarker(new MarkerOptions().position(marker));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 17));
        }
        else {
            mGoogleMap.clear();
            final LatLngBounds.Builder builder = LatLngBounds.builder();
            double latitudInicio = Double.parseDouble(getResources().getString(R.string.latitud_default_map_inicio));
            double longitudInicio = Double.parseDouble(getResources().getString(R.string.longitud_dafault_map_inicio));
            double latitudFin = Double.parseDouble(getResources().getString(R.string.latitud_default_map_fin));
            double longitudFin = Double.parseDouble(getResources().getString(R.string.longitud_dafault_map_fin));

            LatLng latLngInicio = new LatLng(latitudInicio, longitudInicio);
            LatLng latLngFin = new LatLng(latitudFin, longitudFin);

            builder.include(latLngInicio).include(latLngFin);

            mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 32));
                }
            });
        }
    }
}
