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
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import adapters.recyclerview.RVPedidoDetAdapter;
import adapters.recyclerview.interfaces.IRVPedidoDetAdapter;
import dao.PedidoDAO;
import entities.PedidoCab;
import entities.PedidoDet;
import utils.Constantes;

public class PedidoInfoActivity extends AppCompatActivity implements IRVPedidoDetAdapter {
    PedidoCab pc = new PedidoCab();
    PedidoDAO pd = new PedidoDAO();
    private RVPedidoDetAdapter mRVPedidoDetAdapter;
    private RecyclerView rvPedidoDet;

    private TextView tvTotal, tvIdPedido, tvClientePedido;

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_info);
        rvPedidoDet = (RecyclerView) findViewById(R.id.rvPedidosDet);
        tvTotal = (TextView) findViewById(R.id.tvTotalPedidoInfo);
        tvIdPedido = (TextView) findViewById(R.id.tvIdPedido);
        tvClientePedido = (TextView) findViewById(R.id.tvClientePedido);

        rvPedidoDet.setLayoutManager(new LinearLayoutManager(PedidoInfoActivity.this));
        mRVPedidoDetAdapter = new RVPedidoDetAdapter(PedidoInfoActivity.this);
        rvPedidoDet.setAdapter(mRVPedidoDetAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.texto_label_pedido_detalle));
    }

    @Override
    public void onItemClick(PedidoDet pedido) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        pc = getIntent().getParcelableExtra(Constantes.ARG_PEDIDO);
        tvTotal.setText(String.valueOf(pc.getMontoTotal()));
        tvIdPedido.setText(getResources().getString(R.string.texto_label_pedido) + ": " + String.valueOf(pc.getIdPedido()));
        tvClientePedido.setText(getResources().getString(R.string.texto_label_pedido_cliente) + ": " + String.valueOf(pc.getCliente()));
        mRVPedidoDetAdapter.clearAndAddAll(new PedidoDAO().lstPedidoDet(pc));
    }

    //para el toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el menú que va a aparecer en el Toolbar
        getMenuInflater().inflate(R.menu.menu_tool_eliminar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Capturo el click de la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else  //Capturo el click de eliminar
            if (item.getItemId() == R.id.btnEliminar) {
                pd.deletePedido(pc);
                finish();
            }

        return super.onOptionsItemSelected(item);
    }

}