package com.project.cibertec.finalproject.pedido;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.project.cibertec.finalproject.LoginActivity;
import com.project.cibertec.finalproject.R;
import com.project.cibertec.finalproject.dao.PedidoDAO;
import com.project.cibertec.finalproject.entities.Pedido;
import com.project.cibertec.finalproject.entities.PedidoDetalle;
import com.project.cibertec.finalproject.pedido.adapter.recyclerview.RVAdapterListaPedido;
import com.project.cibertec.finalproject.pedido.adapter.recyclerview.RVAdapterListaPedidoDetalle;
import com.project.cibertec.finalproject.pedido.adapter.recyclerview.listeners.IRVAdapterListaPedidoListener;

import java.util.ArrayList;

/**
 * Created by V825727 on 6/7/2016.
 */
public class DetallePedidoActivity extends AppCompatActivity{

    private TextView tvPedidoDetalleCliente,tvPedidoDetalleTotal;
    private RecyclerView mRVPedidoDetalle;

    private Pedido mPedido = null;
    private ArrayList<PedidoDetalle> mPedidoDetalle = null;
    private RVAdapterListaPedidoDetalle mRVAdapterListaDetailPedido;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos_detalle_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardetallePedidos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.tituloDetallePedido);

        tvPedidoDetalleCliente = (TextView) findViewById(R.id.tvPedidoDetalleCliente);
        tvPedidoDetalleTotal = (TextView) findViewById(R.id.tvPedidoDetalleTotal);

        //Inflo el recycler y configuramos el Adapter
        mRVPedidoDetalle = (RecyclerView) findViewById(R.id.rvPedidosDetalle);
        mRVPedidoDetalle.setLayoutManager(new LinearLayoutManager(DetallePedidoActivity.this));
        mRVPedidoDetalle.setHasFixedSize(true);

        if (getIntent().getExtras().containsKey("detallePedido")) {
            mPedido = (Pedido) getIntent().getExtras().get("detallePedido");
            mPedidoDetalle = new ArrayList<>();
            mPedidoDetalle = new PedidoDAO().listDetailPedido(mPedido.getIdPedido());

            mRVAdapterListaDetailPedido = new RVAdapterListaPedidoDetalle();
            mRVAdapterListaDetailPedido.clearAndAddAll(mPedidoDetalle);
            mRVPedidoDetalle.setAdapter(mRVAdapterListaDetailPedido);

            tvPedidoDetalleCliente.setText(mPedido.getEmpresa());
            tvPedidoDetalleTotal.setText("S/ "+String.valueOf(mPedido.getTotalPedido()));
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.abFirstDelete:
                eliminarPedido();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void eliminarPedido() {

        AlertDialog.Builder alert = new AlertDialog.Builder(DetallePedidoActivity.this);
        alert.setTitle("Confirmación");
        alert.setMessage("¿Desea eliminar el pedido?");
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(DialogInterface dialog, int whichButton) {

                if(new PedidoDAO().deletePedido(mPedido)){
                    Toast.makeText(DetallePedidoActivity.this,"Se eliminó el pedido", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(DetallePedidoActivity.this,"Ocurrió un error al tratar de eliminar el pedido", Toast.LENGTH_SHORT).show();

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();

    }

}
