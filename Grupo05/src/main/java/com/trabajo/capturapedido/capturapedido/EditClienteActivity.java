package com.trabajo.capturapedido.capturapedido;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.trabajo.capturapedido.capturapedido.entities.BECliente;

public class EditClienteActivity extends AppCompatActivity {

    public final static String ARG_CLIENTE = "arg_cliente";
    private Toolbar tb_cliente;
    private Button btPedido;
    private ImageView imgMapa,imgCall;


    private TextView tvNomCliente, tvApellCliente, tvTelfCliente, tvCorreCliente, tvDireccCliente, tvDistCliente, tvRefCliente;

    private BECliente mCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editcliente_activity);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ARG_CLIENTE)) {
            mCliente = getIntent().getParcelableExtra(ARG_CLIENTE);

            tvNomCliente = (TextView) findViewById(R.id.tvNomCliEdit);
            tvApellCliente = (TextView) findViewById(R.id.tvApellCliEdit);
            tvTelfCliente = (TextView) findViewById(R.id.tvTelfCliEdit);
            tvCorreCliente = (TextView) findViewById(R.id.tvCorreoCliEdit);
            tvDireccCliente = (TextView) findViewById(R.id.tvDirCliEdit);
            tvDistCliente = (TextView) findViewById(R.id.tvDistCliEdit);
            tvRefCliente = (TextView) findViewById(R.id.tvRefCliEdit);
            imgMapa = (ImageView) findViewById(R.id.ivpoint);
            imgMapa.setOnClickListener(imgMapaOnClickListener);

            imgCall = (ImageView) findViewById(R.id.ivcall);
            imgCall.setOnClickListener(imgCallOnClickListener);









            tb_cliente = (Toolbar) findViewById(R.id.tbEditCliente);
            setSupportActionBar(tb_cliente);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            btPedido = (Button) findViewById(R.id.btCliPedido);
            btPedido.setOnClickListener(btPedidoOnClickListener);

            setData();
            getSupportActionBar().setTitle(mCliente.getCiaCliente());
            tb_cliente.setTitleTextColor(0xFFFFFFFF);
        }
    }

    private void setData() {
        tvNomCliente.setText(mCliente.getNomCliente());
        tvApellCliente.setText(mCliente.getApellCliente());
        tvTelfCliente.setText(mCliente.getTelfCliente());
        tvCorreCliente.setText(mCliente.getCorreoCliente());
        tvDireccCliente.setText(mCliente.getDireccCliente());
        tvDistCliente.setText(mCliente.getDistCliente());
        tvRefCliente.setText(mCliente.getRefCliente());
    }

    View.OnClickListener btPedidoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(EditClienteActivity.this, PedidoDetActivity.class);
            intent.putExtra(PedidoDetActivity.ARG_CLIENTE, mCliente);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener imgMapaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(EditClienteActivity.this, MapaClienteActivity.class);
            intent.putExtra(MapaClienteActivity.ARG_CLIENTE, mCliente);
            startActivity(intent);
        }
    };



    View.OnClickListener imgCallOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            String temp = "tel:" + mCliente.getTelfCliente();
            intent.setData(Uri.parse(temp));

            startActivity(intent);


        }
    };




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.ab_edit) {
            Intent intent = new Intent(EditClienteActivity.this, Cliente2Activity.class);
            intent.putExtra(Cliente2Activity.ARG_CLIENTE, mCliente);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
