package com.project.cibertec.finalproject.cliente;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.project.cibertec.finalproject.LoginActivity;
import com.project.cibertec.finalproject.R;
import com.project.cibertec.finalproject.cliente.adapter.recyclerview.RVAdapterListaCliente;
import com.project.cibertec.finalproject.cliente.adapter.recyclerview.listeners.IRVAdapterListaClienteListener;
import com.project.cibertec.finalproject.dao.ClienteDAO;
import com.project.cibertec.finalproject.entities.Cliente;
import com.project.cibertec.finalproject.pedido.PedidosListaActivity;
import com.project.cibertec.finalproject.producto.ProductosListaActivity;


public class ClientesListaActivity extends AppCompatActivity implements IRVAdapterListaClienteListener {

    private DrawerLayout dlMenu;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView nvMenu;
    private RecyclerView rvClientesListado;
    private RVAdapterListaCliente mRVAdapterListaCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCliList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Drawer Layout
        dlMenu = (DrawerLayout) findViewById(R.id.dlmenu);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(ClientesListaActivity.this, dlMenu, R.string.app_name, R.string.app_name);
        dlMenu.addDrawerListener(mActionBarDrawerToggle);

        //Navigation View
        nvMenu = (NavigationView) findViewById(R.id.nvMenu);
        nvMenu.setNavigationItemSelectedListener(nvMenuOnNavigationItemSelectedListener);

        View headerView = nvMenu.inflateHeaderView(R.layout.menu_header); //Header del drawer
        TextView txtUserName = (TextView) headerView.findViewById(R.id.username);

        if (PreferenceManager.getDefaultSharedPreferences(ClientesListaActivity.this).getBoolean("ingreso", false)) {
            //LLenar head del drawer
            String nombreEmpleado = PreferenceManager.getDefaultSharedPreferences(ClientesListaActivity.this).getString("nombre", "");
            txtUserName.setText(nombreEmpleado);
        }

        //Inflar el recycler y configuramos el Adapter
        rvClientesListado = (RecyclerView) findViewById(R.id.rvClientesListado);
        rvClientesListado.setLayoutManager(new LinearLayoutManager(ClientesListaActivity.this));
        rvClientesListado.setHasFixedSize(true);
        mRVAdapterListaCliente = new RVAdapterListaCliente(ClientesListaActivity.this);
        rvClientesListado.setAdapter(mRVAdapterListaCliente);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mRVAdapterListaCliente.clearAndAddAll(new ClienteDAO().listCliente());
    }

    private NavigationView.OnNavigationItemSelectedListener nvMenuOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nvItemclientes:
                    nvMenu.setCheckedItem(item.getItemId());
                    dlMenu.closeDrawer(GravityCompat.START);
                    return true;
                case R.id.nvItemProductos:
                    Intent listaProd = new Intent(ClientesListaActivity.this, ProductosListaActivity.class);
                    startActivity(listaProd);
                    dlMenu.closeDrawer(GravityCompat.START);
                    return true;
                case R.id.nvItemPedidos:
                    Intent listaPedidos = new Intent(ClientesListaActivity.this, PedidosListaActivity.class);
                    startActivity(listaPedidos);
                    dlMenu.closeDrawer(GravityCompat.START);
                    return true;
                case R.id.nvItemCerrarSesion:
                    nvMenu.setCheckedItem(R.id.nvItemclientes);
                    dlMenu.closeDrawer(GravityCompat.START);
                    closeSession();
                    return true;
                default:
                    return true;
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.drawer, menu);
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        if (item.getItemId() == R.id.abFirstAdd) {
            startActivityForResult(new Intent(ClientesListaActivity.this, ClientesNuevoActivity.class), 999);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void closeSession() {

        AlertDialog.Builder alert = new AlertDialog.Builder(ClientesListaActivity.this);
        alert.setTitle("Confirmación");
        alert.setMessage("¿Desea cerrar la sesión?");
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(DialogInterface dialog, int whichButton) {

                PreferenceManager.getDefaultSharedPreferences(ClientesListaActivity.this)
                        .edit()
                        .clear()
                        .commit();
                Intent login = new Intent(ClientesListaActivity.this, LoginActivity.class);
                startActivity(login);
                finish();

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();

    }

    @Override
    public void onItemClick(Cliente cliente) {

        Intent detalleCliente = new Intent(ClientesListaActivity.this, ClientesDetalleActivity.class);
        detalleCliente.putExtra("cliente", cliente);
        startActivity(detalleCliente);
    }

    @Override
    public void onItemMapPinClick(Cliente cliente) {
        Intent mapaCliente = new Intent(ClientesListaActivity.this, ClientesMapActivity.class);
        mapaCliente.putExtra("cliente", cliente);
        startActivity(mapaCliente);
    }

    @Override
    public void onItemPhoneClick(Cliente cliente) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL, null);
            callIntent.setData(Uri.parse("tel:" + cliente.getTelefono()));
            if (ActivityCompat.checkSelfPermission(ClientesListaActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.d("Calling a Phone Number", "Call failed"
                    + activityException);
        }
    }

}
