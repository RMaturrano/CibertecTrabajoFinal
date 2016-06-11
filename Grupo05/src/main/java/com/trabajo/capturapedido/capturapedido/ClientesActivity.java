package com.trabajo.capturapedido.capturapedido;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.RVClienteAdapter;
import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.interfaces.IRVClienteAdapter;
import com.trabajo.capturapedido.capturapedido.datos.DACliente;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseHelper;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseSingleton;
import com.trabajo.capturapedido.capturapedido.entities.BECliente;

import java.io.IOException;
import java.util.ArrayList;

public class ClientesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IRVClienteAdapter {

    public static final String SESSION_PREFERENCE = "logout";
    private RVClienteAdapter mRVClienteAdapter;
    private RecyclerView rvClientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        rvClientes = (RecyclerView) findViewById(R.id.rvClientes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        rvClientes.setLayoutManager(new LinearLayoutManager(ClientesActivity.this));

        mRVClienteAdapter = new RVClienteAdapter(ClientesActivity.this);

        rvClientes.setAdapter(mRVClienteAdapter);


        DataBaseHelper dataBaseHelper = new DataBaseHelper(ClientesActivity.this);
        try {
            dataBaseHelper.createDataBase();
            new DataBaseSingleton(ClientesActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }







        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }






    @Override
    protected void onStart() {
        super.onStart();
        mRVClienteAdapter.clearAndAddAll(new DACliente().listaCliente());
    }












    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clientes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addCliente) {

            Intent intent = new Intent(ClientesActivity.this, Cliente2Activity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_cliente) {

            startActivity(new Intent(this,ClientesActivity.class));
            //finish();

        } else if (id == R.id.nav_pedido) {

            startActivity(new Intent(this,PedidoActivity.class));
            //finish();

        } else if (id == R.id.nav_producto) {

            startActivity(new Intent(this,ListaProductoActivity.class));

        } else if (id == R.id.nav_cerrar) {

            SharedPreferences settings = getSharedPreferences(SESSION_PREFERENCE, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("logged");
            editor.commit();
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSelectItem(BECliente cliente) {
        //public void onItemClick (BECliente cliente) {
        Intent intent = new Intent(ClientesActivity.this, EditClienteActivity.class);
        intent.putExtra(EditClienteActivity.ARG_CLIENTE, cliente);
        startActivity(intent);
    }

    @Override
    public void onItemMapClick(BECliente cliente) {
        Intent intent = new Intent(ClientesActivity.this, MapaClienteActivity.class);
        intent.putExtra(MapaClienteActivity.ARG_CLIENTE, cliente);
        startActivity(intent);
    }


}
