package com.trabajo.capturapedido.capturapedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.RVAddPedidoAdapter;
import com.trabajo.capturapedido.capturapedido.datos.DAPedido;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseHelper;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseSingleton;
import com.trabajo.capturapedido.capturapedido.entities.BEPedido;

import java.io.IOException;

public class EditPedidoActivity extends AppCompatActivity {

    public final static String ARG_PEDIDO = "arg_pedido";
    private RVAddPedidoAdapter mRVdetPedidoAdapter;
    private RecyclerView rvDetPedido;
    private Toolbar tb_detPedido;

    private TextView tvdetTotalPedido, tvdetNomCliente, tvIdPedido;

    private BEPedido mBEPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editpedido_activity);

        rvDetPedido = (RecyclerView) findViewById(R.id.rvDetPedido);
        tb_detPedido = (Toolbar) findViewById(R.id.tbDetPedido);
        tvdetTotalPedido = (TextView) findViewById(R.id.tvDetTotalPedido);
        tvdetNomCliente = (TextView) findViewById(R.id.tvdetNomCliente);
        tvIdPedido = (TextView) findViewById(R.id.tvIdPedido);

        setSupportActionBar(tb_detPedido);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalle del Pedido");
        tb_detPedido.setTitleTextColor(0xFFFFFFFF);


        rvDetPedido.setLayoutManager(new LinearLayoutManager(EditPedidoActivity.this));
        mRVdetPedidoAdapter = new RVAddPedidoAdapter();
        rvDetPedido.setAdapter(mRVdetPedidoAdapter);


        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ARG_PEDIDO)) {
            mBEPedido = getIntent().getParcelableExtra(ARG_PEDIDO);
            tvdetNomCliente.setText(mBEPedido.getNomClientePedido());
            tvIdPedido.setText(String.valueOf(mBEPedido.getIdPedido()));
            tvdetTotalPedido.setText(String.valueOf(mBEPedido.getTotalPedido()));
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(EditPedidoActivity.this);
        try {
            dataBaseHelper.createDataBase();
            new DataBaseSingleton(EditPedidoActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mRVdetPedidoAdapter.clearAndAddAll(new DAPedido().listaPedidoDet(mBEPedido.getIdPedido()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.del_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            /*Intent intent = new Intent(EditPedidoActivity.this, PedidoActivity.class);
            startActivity(intent);
            finish();
            return true;*/
            onBackPressed();
        } else if (item.getItemId() == R.id.ab_del) {
            DeletePedido();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void DeletePedido() {
        boolean isOk = new DAPedido().deletePedido(mBEPedido);
        if (isOk)
            finish();
        else
            Toast.makeText(EditPedidoActivity.this, "Pedido " + String.valueOf(mBEPedido.getIdPedido()) + " No se puedo Eliminar", Toast.LENGTH_LONG).show();
    }
}
