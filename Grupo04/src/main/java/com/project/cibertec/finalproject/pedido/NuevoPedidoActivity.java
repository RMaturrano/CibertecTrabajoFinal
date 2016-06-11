package com.project.cibertec.finalproject.pedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.project.cibertec.finalproject.R;
import com.project.cibertec.finalproject.dao.ClienteDAO;
import com.project.cibertec.finalproject.dao.PedidoDAO;
import com.project.cibertec.finalproject.entities.Cliente;
import com.project.cibertec.finalproject.entities.Pedido;
import com.project.cibertec.finalproject.entities.PedidoDetalle;
import com.project.cibertec.finalproject.pedido.adapter.recyclerview.RVAdapterListaPedidoDetalle;
import com.project.cibertec.finalproject.pedido.adapter.spinner.SPAdapter;


public class NuevoPedidoActivity extends AppCompatActivity {

    public static int REQUEST_CODE_AGREGAR_PRODUCTO = 2;

    private Spinner spClientes;
    private SPAdapter spFirstAdapter;
    private TextView tvClienteNombrePedido, tvPedidoNuevoTotal;
    private RecyclerView mRVPedidoDetalleListado;
    private RVAdapterListaPedidoDetalle mRVAdapterListaDetailPedido;

    private Cliente mCliente = null;
    private double totalPedido = 0;
    private int cantidadProductos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos_nuevo_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarNuevoPedidoList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.tituloNuevoPedido);


        //Spinner
        spClientes =(Spinner) findViewById(R.id.spClientes);
        spFirstAdapter = new SPAdapter(NuevoPedidoActivity.this);
        spFirstAdapter.addAll(new ClienteDAO().listCliente());
        spClientes.setAdapter(spFirstAdapter);
        spClientes.setVisibility(View.VISIBLE);
        spClientes.setOnItemSelectedListener(spClientesOnItemSelectedListener);

        tvPedidoNuevoTotal = (TextView) findViewById(R.id.tvPedidoNuevoTotal);
        tvPedidoNuevoTotal.setText("S/ " + String.valueOf(totalPedido));

        tvClienteNombrePedido = (TextView) findViewById(R.id.tvClienteNombrePedido);
        tvClienteNombrePedido.setVisibility(View.GONE);


        if (getIntent().getExtras()!= null) {
            //.containsKey("cliente")
            if(getIntent().getExtras().containsKey("cliente")){
                mCliente = (Cliente) getIntent().getExtras().get("cliente");
                tvClienteNombrePedido.setText(mCliente.getEmpresa());
                spClientes.setVisibility(View.GONE);
                tvClienteNombrePedido.setVisibility(View.VISIBLE);
            }
        }

        mRVAdapterListaDetailPedido = new RVAdapterListaPedidoDetalle();
        mRVPedidoDetalleListado = (RecyclerView) findViewById(R.id.rvPedidosNuevo);
        mRVPedidoDetalleListado.setLayoutManager(new LinearLayoutManager(NuevoPedidoActivity.this));
        mRVPedidoDetalleListado.setHasFixedSize(true);
        mRVPedidoDetalleListado.setAdapter(mRVAdapterListaDetailPedido);

    }

    AdapterView.OnItemSelectedListener spClientesOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mCliente = (Cliente) parent.getSelectedItem();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_AGREGAR_PRODUCTO){
           PedidoDetalle newDetail = (PedidoDetalle) data.getParcelableExtra("producto");
           mRVAdapterListaDetailPedido.add(newDetail);
           totalPedido += newDetail.getTotalLinea();
           cantidadProductos += newDetail.getCantidad();
           tvPedidoNuevoTotal.setText("S/ "+ String.valueOf(DoubleRound.round(totalPedido,2)));
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_save, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.abFirstAdd:
                startActivityForResult(new Intent(NuevoPedidoActivity.this,SeleccioneProductoActivity.class), REQUEST_CODE_AGREGAR_PRODUCTO);
                return true;
            case R.id.abSecondSave:
                if(mRVAdapterListaDetailPedido.getItemCount() > 0){
                    agregarPedido();
                }else
                    Toast.makeText(NuevoPedidoActivity.this,"Agregue productos al carrito", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void agregarPedido(){
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setIdCliente(mCliente.getClienteId());
        nuevoPedido.setCantidadProductos(cantidadProductos);
        nuevoPedido.setTotalPedido(totalPedido);
        nuevoPedido.setLineas(mRVAdapterListaDetailPedido.getAll());

        if(new PedidoDAO().insertPedido(nuevoPedido)){
            Toast.makeText(NuevoPedidoActivity.this,"Se registró el nuevo pedido", Toast.LENGTH_LONG).show();
            finish();
        }else
            Toast.makeText(NuevoPedidoActivity.this,"Hubo un error al registrar el pedido", Toast.LENGTH_SHORT).show();

    }

}
