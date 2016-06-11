package com.cibertec.capturapedido;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jtorres on 20/05/2016.
 */
public class MainActivity extends AppCompatActivity {
    //DrawerLayout y NavigationView
    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle mActionBarDrawerToggle;


    //Nombre de usuario en el HeaderNavigationView
    private TextView tvUsuarioSistema;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity); //Layout base del main_activity

        //Toolbar NavigationView
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //drawerLayout.setDrawerListener(mActionBarDrawerToggle);
        //mActionBarDrawerToggle.syncState(); // Fue agregado al onPostCreate

        navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(onNavigationItemSelected);
        //Fin NavigationView

        //Encabezado NavigationView
        View header = LayoutInflater.from(this).inflate(R.layout.main_nav_header, null);
        navView.addHeaderView(header);
        tvUsuarioSistema = (TextView) header.findViewById(R.id.tvUsuarioSistema);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String nombre = "";
        if(preferences!=null) nombre = preferences.getString("nombre", "") + " " + preferences.getString("apellido", "");
        tvUsuarioSistema.setText(nombre);
        //Fin encabezado NavigationView

        //Iniciamos el fragmento de clientes
        if(savedInstanceState == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frame, new ClientesFragment());
            transaction.commit();
            navView.setCheckedItem(R.id.navItemClientes);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int itemId = item.getItemId();
            navView.setCheckedItem(itemId);

            Fragment f = null;

            if (itemId == R.id.navItemClientes) {
                f = new ClientesFragment();
            } else if (itemId == R.id.navItemProductos) {
                f = new ProductosFragment();
            }
            else if (itemId == R.id.navItemPedidos) {
                f = new PedidosFragment();
            }
            else if (itemId == R.id.navItemCerrarSesion) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //preferences.edit().clear().commit();
                preferences.edit().putBoolean("ingreso", false).commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
               // finish();
                MainActivity.this.finish();
            }

            if (f != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, f);
                transaction.commit();
                drawerLayout.closeDrawers();
                //drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

            return false;
        }
    };
}
