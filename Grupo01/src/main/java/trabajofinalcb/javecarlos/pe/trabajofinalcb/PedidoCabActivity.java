package trabajofinalcb.javecarlos.pe.trabajofinalcb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;

import adapters.recyclerview.RVPedidoCabAdapter;
import adapters.recyclerview.interfaces.IRVPedidoCabAdapter;
import dao.PedidoDAO;
import entities.PedidoCab;
import utils.Constantes;

public class PedidoCabActivity extends AppCompatActivity implements IRVPedidoCabAdapter {

    private RVPedidoCabAdapter mRVPedidoCabAdapter;
    private RecyclerView rvPedido;

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        rvPedido = (RecyclerView) findViewById(R.id.rvPedidos);

        rvPedido.setLayoutManager(new LinearLayoutManager(PedidoCabActivity.this));
        mRVPedidoCabAdapter = new RVPedidoCabAdapter(PedidoCabActivity.this);
        rvPedido.setAdapter(mRVPedidoCabAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.texto_menu_pedidos));
    }

    @Override
    public void onItemClick(PedidoCab pedido) {
        Intent intent = new Intent(PedidoCabActivity.this, PedidoInfoActivity.class);
        intent.putExtra(Constantes.ARG_PEDIDO, pedido);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRVPedidoCabAdapter.clearAndAddAll(new PedidoDAO().lstPedidoCab());
    }

    //para el toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el menú que va a aparecer en el Toolbar
        getMenuInflater().inflate(R.menu.menu_lista_pedido, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Capturo el click de la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else  //Capturo el click del agregar
            if (item.getItemId() == R.id.btnAgregar) {
                Intent intent = new Intent(PedidoCabActivity.this, PedidoNuevoActivity.class);
                startActivity(intent);
                return true;
            }

        return super.onOptionsItemSelected(item);
    }

}