package com.trabajo.capturapedido.capturapedido;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trabajo.capturapedido.capturapedido.entities.BECliente;

public class MapaClienteActivity extends AppCompatActivity implements OnMapReadyCallback {

    public final static String ARG_CLIENTE = "arg_cliente";
    private BECliente mCliente;
    private Toolbar tb_cliente;
    private GoogleMap mGoogleMap;
    private LatLng mLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapacliente_activity);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ARG_CLIENTE)) {
            mCliente = getIntent().getParcelableExtra(ARG_CLIENTE);
            mLatLng = new LatLng(mCliente.getLatitud(), mCliente.getLongitud());
        }

        ((MapFragment) getFragmentManager().findFragmentById(R.id.fragMap)).getMapAsync(MapaClienteActivity.this);

        tb_cliente = (Toolbar) findViewById(R.id.tbMapaCliente);
        setSupportActionBar(tb_cliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mCliente.getCiaCliente());
        tb_cliente.setTitleTextColor(0xFFFFFFFF);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        setMarkers();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
