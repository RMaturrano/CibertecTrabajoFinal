package cibertec.pe.proyectofinal;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by SOPORTE on 02/06/2016.
 */
public class Tercera_Actividad_Principal_Clientes extends AppCompatActivity {

    private DrawerLayout dlMenu;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private TextView tvPCACerrar;
    //private TextView tvMenuFirst, tvMenuSecond, tvMenuMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tercera_actividad_principal_cliente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dlMenu = (DrawerLayout) findViewById(R.id.dlMenu);
        tvPCACerrar = (TextView) findViewById(R.id.tvPCACerrar);

        tvPCACerrar.setOnClickListener(tvCerrarSesionOnClickListener);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(Tercera_Actividad_Principal_Clientes.this, dlMenu, R.string.app_name, R.string.app_name);

        dlMenu.addDrawerListener(mActionBarDrawerToggle);

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener tvCerrarSesionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PreferenceManager.getDefaultSharedPreferences(Tercera_Actividad_Principal_Clientes.this).edit().clear().commit();
            Intent intent = new Intent(Tercera_Actividad_Principal_Clientes.this, Segunda_Actividad_Login.class);
            startActivity(intent);
            finish();
        }

    };
}
