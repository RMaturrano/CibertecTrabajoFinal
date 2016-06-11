package com.trabajo.capturapedido.capturapedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.RVClienteAdapter;
import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.interfaces.IRVClienteAdapter;
import com.trabajo.capturapedido.capturapedido.datos.DACliente;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseHelper;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseSingleton;
import com.trabajo.capturapedido.capturapedido.entities.BECliente;

import java.io.IOException;

public class ClienteActivity extends AppCompatActivity implements IRVClienteAdapter {

    private RVClienteAdapter mRVClienteAdapter;
    private RecyclerView rvCliente;
    private Toolbar tb_cliente;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_activity);

        rvCliente = (RecyclerView) findViewById(R.id.rvCliente);


        tb_cliente = (Toolbar) findViewById(R.id.tbCliente);
        setSupportActionBar(tb_cliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        rvCliente.setLayoutManager(new LinearLayoutManager(ClienteActivity.this));

        mRVClienteAdapter = new RVClienteAdapter(ClienteActivity.this);

        rvCliente.setAdapter(mRVClienteAdapter);


        DataBaseHelper dataBaseHelper = new DataBaseHelper(ClienteActivity.this);
        try {
            dataBaseHelper.createDataBase();
            new DataBaseSingleton(ClienteActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mRVClienteAdapter.clearAndAddAll(new DACliente().listaCliente());
    }

    @Override
    public void onSelectItem(BECliente cliente) {
        //public void onItemClick (BECliente cliente) {
        Intent intent = new Intent(ClienteActivity.this, EditClienteActivity.class);
        intent.putExtra(EditClienteActivity.ARG_CLIENTE, cliente);
        startActivity(intent);
    }

    @Override
    public void onItemMapClick(BECliente cliente) {
        Intent intent = new Intent(ClienteActivity.this, MapaClienteActivity.class);
        intent.putExtra(MapaClienteActivity.ARG_CLIENTE, cliente);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.ab_add) {
            Intent intent = new Intent(ClienteActivity.this, Cliente2Activity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}