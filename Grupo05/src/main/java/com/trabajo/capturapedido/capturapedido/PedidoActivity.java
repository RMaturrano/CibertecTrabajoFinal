package com.trabajo.capturapedido.capturapedido;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.RVPedidoAdapter;
import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.interfaces.IRVPedidoAdapter;
import com.trabajo.capturapedido.capturapedido.datos.DAPedido;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseHelper;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseSingleton;
import com.trabajo.capturapedido.capturapedido.entities.BEPedido;

import java.io.IOException;

public class PedidoActivity extends AppCompatActivity implements IRVPedidoAdapter {

    private RVPedidoAdapter mRVPedidoAdapter;
    private RecyclerView rvPedido;
    private Toolbar tb_pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_activity);

        rvPedido = (RecyclerView) findViewById(R.id.rvPedido);
        tb_pedido = (Toolbar) findViewById(R.id.tbPedido);

        setSupportActionBar(tb_pedido);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       getSupportActionBar().setTitle("Pedidos");
        tb_pedido.setTitleTextColor(0xFFFFFFFF);

        rvPedido.setLayoutManager(new LinearLayoutManager(PedidoActivity.this));

        mRVPedidoAdapter = new RVPedidoAdapter(PedidoActivity.this);
        rvPedido.setHasFixedSize(true);
        rvPedido.setAdapter(mRVPedidoAdapter);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(PedidoActivity.this);
        try {
            dataBaseHelper.createDataBase();
            new DataBaseSingleton(PedidoActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        mRVPedidoAdapter.clearAndAddAll(new DAPedido().listaPedido());
    }


    @Override
    public void onItemClick(BEPedido pedido) {
        Intent intent = new Intent(PedidoActivity.this, EditPedidoActivity.class);
        intent.putExtra(EditPedidoActivity.ARG_PEDIDO, pedido);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //onBackPressed();
            Intent intent = new Intent(PedidoActivity.this, ClientesActivity.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.ab_add) {
            Intent intent = new Intent(PedidoActivity.this, PedidoDetActivity.class);
            startActivity(intent);
            finish();
        }

        return true;
    }
}
