package com.project.cibertec.finalproject.cliente;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.cibertec.finalproject.R;
import com.project.cibertec.finalproject.entities.Cliente;

public class ClientesMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private Cliente mCliente = null;

    private Location mLocation;
    private LatLng mLatLong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientes_mapa_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCliMap);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragMap);
        mapFragment.getMapAsync(ClientesMapActivity.this);

        if (getIntent().getExtras().containsKey("cliente")) {
            mCliente = (Cliente) getIntent().getExtras().get("cliente");
            setTitle(mCliente.getEmpresa());
        }

        getMyLocation();

    }

    private void getMyLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }else{
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
            mLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if(mLocation != null)
                mLatLong = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        }


    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El GPS parece estar deshabilitado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            mLatLong = new LatLng(location.getLongitude(),location.getLatitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            mLatLong = null;
        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        if(mCliente != null){
            LatLng latLng = new LatLng(Double.parseDouble(mCliente.getLatitud()),
                    Double.parseDouble(mCliente.getLongitud()));
            mGoogleMap.clear();
            mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Posición del cliente"));


            if(mLatLong != null){
                LatLngBounds.Builder builder = LatLngBounds.builder();
                mGoogleMap.addMarker(new MarkerOptions().position(mLatLong)
                        .title("Mi posición actual")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_black_36dp)));
                builder.include(latLng).include(mLatLong);
                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),width,height,32));
            }else{
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
            }


        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
