package com.project.cibertec.finalproject.pedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.project.cibertec.finalproject.R;
import com.project.cibertec.finalproject.dao.PedidoDAO;

import com.project.cibertec.finalproject.entities.Pedido;
import com.project.cibertec.finalproject.pedido.adapter.recyclerview.RVAdapterListaPedido;
import com.project.cibertec.finalproject.pedido.adapter.recyclerview.listeners.IRVAdapterListaPedidoListener;

public class PedidosListaActivity extends AppCompatActivity implements IRVAdapterListaPedidoListener{

    private RVAdapterListaPedido mRVAdapterListaPedido;
    private RecyclerView mRVPedidosListado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos_lista_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPedidosList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.tituloPedidoLista);

        //Inflo el recycler y configuramos el Adapter
        mRVPedidosListado = (RecyclerView) findViewById(R.id.rvPedidosLista);
        mRVPedidosListado.setLayoutManager(new LinearLayoutManager(PedidosListaActivity.this));
        mRVPedidosListado.setHasFixedSize(true);

        mRVAdapterListaPedido = new RVAdapterListaPedido(PedidosListaActivity.this);
        mRVPedidosListado.setAdapter(mRVAdapterListaPedido);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mRVAdapterListaPedido.clearAndAddAll(new PedidoDAO().listPedidos());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.abFirstAdd:
                startActivity(new Intent(PedidosListaActivity.this,NuevoPedidoActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(Pedido pedido) {

        Intent detallePedido = new Intent(PedidosListaActivity.this, DetallePedidoActivity.class);
        detallePedido.putExtra("detallePedido", pedido);
        startActivity(detallePedido);
    }
}
