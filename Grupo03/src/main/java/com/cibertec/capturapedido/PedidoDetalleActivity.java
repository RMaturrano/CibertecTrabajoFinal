package com.cibertec.capturapedido;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cibertec.capturapedido.adapter.recyclerview.RVPedidoDetalleAdapter;
import com.cibertec.capturapedido.adapter.recyclerview.interfaces.IRVPedidoDetalleAdapter;
import com.cibertec.capturapedido.dao.PedidoDAO;
import com.cibertec.capturapedido.entities.Pedido;
import com.cibertec.capturapedido.entities.PedidoDetalle;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jorge on 26/05/2016.
 */
public class PedidoDetalleActivity extends AppCompatActivity implements IRVPedidoDetalleAdapter {
    public static final String ARG_PEDIDO = "arg_pedido";

    TextView tvPedidoDetalle_NombreCliente, tvPedidoDetalleTotal;
    RecyclerView rvPedidoDetalleProductos;
    RVPedidoDetalleAdapter mRVPedidoDetalleAdapter;
    Pedido pedido;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_detalle_activity);

        this.setTitle(R.string.app_title_tollbar_dpedido);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvPedidoDetalle_NombreCliente = (TextView) findViewById(R.id.tvPedidoDetalle_NombreCliente);
        tvPedidoDetalleTotal = (TextView) findViewById(R.id.tvPedidoDetalleTotal);

        rvPedidoDetalleProductos = (RecyclerView) findViewById(R.id.rvPedidoDetalleProductos);
        rvPedidoDetalleProductos.setLayoutManager(new LinearLayoutManager(PedidoDetalleActivity.this));
        mRVPedidoDetalleAdapter = new RVPedidoDetalleAdapter(PedidoDetalleActivity.this);
        rvPedidoDetalleProductos.setAdapter(mRVPedidoDetalleAdapter);

        int pedidoId = getIntent().getIntExtra(ARG_PEDIDO, 0);
        pedido = new PedidoDAO().getPedido(pedidoId);
        if(pedido != null){
            tvPedidoDetalle_NombreCliente.setText(pedido.getCliente());

            ArrayList<PedidoDetalle> detalle = pedido.getDetalle();
            mRVPedidoDetalleAdapter.clearAndAddAll(detalle);
            tvPedidoDetalleTotal.setText(String.format(Locale.ENGLISH , "%1$,.2f", mRVPedidoDetalleAdapter.getTotal()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pedido_detalle, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home)
            onBackPressed();
        else if (itemId == R.id.ab_PedidoDetalleEliminar) {
            //Alerta de eliminacion
            AlertDialog myDialogBox = new AlertDialog.Builder(this)
                    //set message, title, and icon
                    .setTitle("Eliminar")
                    .setMessage("¿Desea eliminar el pedido?")
                    //.setIcon(R.drawable.ic_delete_white_24dp)
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();

                            boolean eliminado = new PedidoDAO().deletePedido(pedido);
                            if(eliminado){
                                Toast.makeText(PedidoDetalleActivity.this, "El pedido ha sido eliminado", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();

            myDialogBox.show();

        }

        return false;
    }

    @Override
    public void onItemSelect(PedidoDetalle detalle) {

    }

}
