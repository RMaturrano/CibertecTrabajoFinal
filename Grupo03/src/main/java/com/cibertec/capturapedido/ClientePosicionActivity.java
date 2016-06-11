package com.cibertec.capturapedido;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.cibertec.capturapedido.entities.Cliente;
import com.cibertec.capturapedido.entities.Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Jorge on 27/05/2016.
 */
public class ClientePosicionActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    public static final String ARG_CLIENTE = "arg_cliente";
    protected GoogleMap mGoogleMap;
    Cliente cliente;

    protected LocationManager locationManager;
    //LatLngBounds.Builder builder;
    MarkerOptions markerGPS;
    Marker marker;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 30;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_posicion_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cliente = getIntent().getParcelableExtra(ARG_CLIENTE);
        if (cliente != null) {
            this.setTitle(cliente.getEmpresa());
        }

        //init google map
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragClienteDetalleMap);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;



        if (cliente != null) {
            //builder = LatLngBounds.builder();

            if (cliente.getLatitud() != 0 && cliente.getLongitud() != 0) {
                LatLng latLng = new LatLng(cliente.getLatitud(), cliente.getLongitud());
                mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(cliente.getEmpresa()).snippet(cliente.getDireccion()));

                //builder.include(latLng);

                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                //mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 10));
            }
        }
        //Mi ubicación
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        this.mGoogleMap.setMyLocationEnabled(true);

    }

    @Override
    public void onLocationChanged(Location location) {
        if (markerGPS == null) {
            if (mGoogleMap != null) {
                markerGPS = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_pushpin));
                markerGPS.position(new LatLng(location.getLatitude(), location.getLongitude()));
                marker = mGoogleMap.addMarker(markerGPS);
            }
        } else {
            markerGPS.position(new LatLng(location.getLatitude(), location.getLongitude())).title(new Usuario().getNombre());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        if (marker != null){
            marker.remove();
            markerGPS = null;
        }
        Toast.makeText(this, "El GPS está deshabilitado", Toast.LENGTH_SHORT).show();
    }

}
