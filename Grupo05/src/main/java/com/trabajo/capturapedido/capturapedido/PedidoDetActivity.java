package com.trabajo.capturapedido.capturapedido;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.RVAddPedidoAdapter;
import com.trabajo.capturapedido.capturapedido.adapter.spinner.SPClienteAdapter;
import com.trabajo.capturapedido.capturapedido.datos.DACliente;
import com.trabajo.capturapedido.capturapedido.datos.DAPedido;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseHelper;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseSingleton;
import com.trabajo.capturapedido.capturapedido.entities.BECliente;
import com.trabajo.capturapedido.capturapedido.entities.BEPedido;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PedidoDetActivity extends AppCompatActivity {

    public final static String ARG_CLIENTE = "arg_cliente";

    private RVAddPedidoAdapter mRVAddPedidoAdapter;
    private RecyclerView rvAddPedido;
    private SPClienteAdapter mSPClienteAdapter;
    private Spinner spCliente;
    private BEPedido mBEPedido;
    private BECliente mCliente;

    private Toolbar tb_addPedido;
    private TextView tvTotalPedido, tvNomClienteAdd;

    private int mIdCliente;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidodet_activity);

        rvAddPedido = (RecyclerView) findViewById(R.id.rvAddPedido);

        spCliente = (Spinner) findViewById(R.id.spCliente);
        tb_addPedido = (Toolbar) findViewById(R.id.tbAddPedido);
        tvTotalPedido = (TextView) findViewById(R.id.tvTotalPedido);
        tvNomClienteAdd = (TextView) findViewById(R.id.tvNomClienteAdd);

        setSupportActionBar(tb_addPedido);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pedido Nuevo");
        tb_addPedido.setTitleTextColor(0xFFFFFFFF);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(PedidoDetActivity.this);
        try {
            dataBaseHelper.createDataBase();
            new DataBaseSingleton(PedidoDetActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mSPClienteAdapter = new SPClienteAdapter(PedidoDetActivity.this, new DACliente().listaCliente());
        spCliente.setAdapter(mSPClienteAdapter);
        spCliente.setOnItemSelectedListener(spClienteOnItemSelectedListener);


        rvAddPedido.setLayoutManager(new LinearLayoutManager(PedidoDetActivity.this));
        mRVAddPedidoAdapter = new RVAddPedidoAdapter();
        rvAddPedido.setAdapter(mRVAddPedidoAdapter);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ARG_CLIENTE)) {
            mCliente = getIntent().getParcelableExtra(ARG_CLIENTE);
            mIdCliente = mCliente.getIdCliente();
            spCliente.setVisibility(View.GONE);
            tvNomClienteAdd.setText(mCliente.getNomCliente() + " " + mCliente.getApellCliente());
            tvNomClienteAdd.setVisibility(View.VISIBLE);
        }
    }

    AdapterView.OnItemSelectedListener spClienteOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            /*if (isFirst) {
                isFirst = false;
                return;
            }*/
            BECliente cliente = (BECliente) parent.getSelectedItem();
            mIdCliente = cliente.getIdCliente();
        }


        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addpedido_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // onBackPressed();
            Intent intent = new Intent(PedidoDetActivity.this, PedidoActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.ab_add) {
            Intent intent = new Intent(PedidoDetActivity.this, ProductoActivity.class);
            intent.putExtra("arg_list_size", mRVAddPedidoAdapter.getItemCount() + 1);
            startActivityForResult(intent, 99);
        } else if (item.getItemId() == R.id.ab_save) {
            if (GrabaPedido()) {
                Intent intent = new Intent(PedidoDetActivity.this, PedidoActivity.class);
                startActivity(intent);
            }
        }

        return true;
    }

    private boolean GrabaPedido() {
        boolean isOK = true;
        int mIdPedido;
        java.util.Date fecha = new Date();
        String fechapedido = new SimpleDateFormat("dd/MM/yyyy").format(fecha);

        if (mIdCliente == 0) {
            Toast.makeText(PedidoDetActivity.this, "Elija Cliente", Toast.LENGTH_LONG).show();
            isOK = false;
        }
        if (Double.parseDouble(tvTotalPedido.getText().toString().trim()) == 0) {
            Toast.makeText(PedidoDetActivity.this, "No se han adicionado Productos", Toast.LENGTH_LONG).show();
            isOK = false;
        }
        if (isOK) {
            mBEPedido = new BEPedido();
            mBEPedido.setIdClientePedido(mIdCliente);
            mBEPedido.setFechaPedido(fechapedido.toString());
            mBEPedido.setTotalPedido(Double.parseDouble(tvTotalPedido.getText().toString()));
            mBEPedido.setCanttotPedido(mRVAddPedidoAdapter.getItemCount());
            mIdPedido = new DAPedido().insertPedido(mBEPedido);

            ArrayList<BEPedido> lstPedido = mRVAddPedidoAdapter.ListaDetallePedido();

            for (int i = 0; i < lstPedido.size(); i++) {
                mBEPedido.setIdPedido(mIdPedido);
                mBEPedido.setIdProdPedidoDet(lstPedido.get(i).getIdProdPedidoDet());
                mBEPedido.setCantPedidoDet(lstPedido.get(i).getCantPedidoDet());
                mBEPedido.setPrecioPedidoDet(lstPedido.get(i).getPrecioPedidoDet());
                mBEPedido.setTotalPedidoDet(lstPedido.get(i).getTotalPedidoDet());

                int mIdPedidoDet = new DAPedido().insertPedidoDet(mBEPedido);
            }


            if (mIdPedido > 0) {
                Toast.makeText(PedidoDetActivity.this, "Pedido " + String.valueOf(mBEPedido.getIdPedido()) + " ha sido Grabado", Toast.LENGTH_LONG).show();
                finish();
            } else {
                new AlertDialog.Builder(PedidoDetActivity.this).setTitle(R.string.app_name).setMessage("No se pudo registrar el Pedido en la base de datos").setNegativeButton("Aceptar", null).show();
                isOK = false;
            }
        }
        return isOK;
    }

    /*@Override
    public void onSelectItem(BEPedido pedido) {
        // Toast.makeText(PedidoDetActivity.this, pedido.getIdProdPedidoDet(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PedidoDetActivity.this, ProductoActivity.class);
        intent.putExtra(ProductoActivity.ARG_PEDIDO, pedido);
        startActivity(intent);
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 99) {
                mRVAddPedidoAdapter.add(data.<BEPedido>getParcelableExtra("arg_pedido"));

                ArrayList<BEPedido> lstPedido = mRVAddPedidoAdapter.ListaDetallePedido();
                float totpedido = 0;
                for (int i = 0; i < lstPedido.size(); i++) {
                    totpedido += lstPedido.get(i).getTotalPedidoDet();
                }
                tvTotalPedido.setText(String.valueOf(totpedido));
            }
        }
    }
}


