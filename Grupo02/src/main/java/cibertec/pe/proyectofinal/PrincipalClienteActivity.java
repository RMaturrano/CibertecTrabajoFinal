package cibertec.pe.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import cibertec.pe.proyectofinal.adapter.recyclerview.RVPrimerAdapter;
import cibertec.pe.proyectofinal.dao.ClienteDAO;
import cibertec.pe.proyectofinal.dao.DataBaseHelper;
import cibertec.pe.proyectofinal.dao.DataBaseSingleton;
import cibertec.pe.proyectofinal.entities.Cliente;

/**
 * Created by JOSE on 03/06/2016.
 */
public class PrincipalClienteActivity extends AppCompatActivity {

    private RVPrimerAdapter mRVPrimerAdapter;
    private RecyclerView rvPrimerClientes;
    private ClienteDAO mClienteDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_cliente_activity);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_principal_cliente_activity);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mClienteDAO = new ClienteDAO();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(PrincipalClienteActivity.this);
        try {
            dataBaseHelper.createDataBase();
            new DataBaseSingleton(PrincipalClienteActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rvPrimerClientes = (RecyclerView) findViewById(R.id.rvPCACliente);
        mRVPrimerAdapter = new RVPrimerAdapter(null);

        rvPrimerClientes .setLayoutManager(new LinearLayoutManager(PrincipalClienteActivity.this));
        rvPrimerClientes .setHasFixedSize(true);
        rvPrimerClientes .setAdapter(mRVPrimerAdapter);

        mRVPrimerAdapter.clearAndAddAll(mClienteDAO.listCliente());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_cliente_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.principal_cliente_menu_agregar) {
            Intent intent = new Intent(PrincipalClienteActivity.this, ClienteNuevoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    };

}
