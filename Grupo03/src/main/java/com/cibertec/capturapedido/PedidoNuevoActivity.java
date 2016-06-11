package com.cibertec.capturapedido;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cibertec.capturapedido.adapter.recyclerview.RVPedidoDetalleAdapter;
import com.cibertec.capturapedido.adapter.recyclerview.interfaces.IRVPedidoDetalleAdapter;
import com.cibertec.capturapedido.adapter.spinner.SPClienteAdapter;
import com.cibertec.capturapedido.dao.ClienteDAO;
import com.cibertec.capturapedido.dao.PedidoDAO;
import com.cibertec.capturapedido.entities.Cliente;
import com.cibertec.capturapedido.entities.Pedido;
import com.cibertec.capturapedido.entities.PedidoDetalle;

import java.util.Locale;

/**
 * Created by Jorge on 27/05/2016.
 */
public class PedidoNuevoActivity extends AppCompatActivity implements IRVPedidoDetalleAdapter {
    public static final String ARG_CLIENTE = "arg_cliente";

    TextView tvPedidoNuevo_NombreCliente, tvPedidoNuevoTotal;

    RecyclerView rvPedidoNuevoProductos;
    RVPedidoDetalleAdapter mRVPedidoDetalleAdapter;

    Spinner spPedidoNuevoClientes;
    SPClienteAdapter mSPClienteAdapter;

    Cliente cliente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_nuevo_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle(R.string.app_title_tollbar_npedidos);

        tvPedidoNuevo_NombreCliente = (TextView) findViewById(R.id.tvPedidoNuevo_NombreCliente);
        tvPedidoNuevoTotal = (TextView) findViewById(R.id.tvPedidoNuevoTotal);

        //Spinner
        spPedidoNuevoClientes = (Spinner) findViewById(R.id.spPedidoNuevoClientes);

        //RecyclerView
        rvPedidoNuevoProductos = (RecyclerView) findViewById(R.id.rvPedidoNuevoProductos);
        rvPedidoNuevoProductos.setLayoutManager(new LinearLayoutManager(this));
        mRVPedidoDetalleAdapter = new RVPedidoDetalleAdapter(this);
        rvPedidoNuevoProductos.setAdapter(mRVPedidoDetalleAdapter);

        if (getIntent().getParcelableExtra(ARG_CLIENTE) != null) {
            cliente = getIntent().getParcelableExtra(ARG_CLIENTE);
            tvPedidoNuevo_NombreCliente.setText(cliente.getEmpresa());
            spPedidoNuevoClientes.setVisibility(View.GONE);
            //tvPedidoNuevo_NombreCliente.setVisibility(View.VISIBLE);
        } else {
            cliente = null;
            //spPedidoNuevoClientes.setVisibility(View.VISIBLE);
            tvPedidoNuevo_NombreCliente.setVisibility(View.GONE);

            mSPClienteAdapter = new SPClienteAdapter(this, new ClienteDAO().listCliente());
            spPedidoNuevoClientes.setAdapter(mSPClienteAdapter);
            spPedidoNuevoClientes.setOnItemSelectedListener(spPedidoNuevoClientes_OnItemSelectedListener);
        }

        //Swiped: Desplazar el item
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) { // ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getLayoutPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    mRVPedidoDetalleAdapter.delete(position);
                    MostrarTotal();
                    Toast.makeText(PedidoNuevoActivity.this, R.string.app_npedido_mensaje_eliminado, Toast.LENGTH_LONG).show();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvPedidoNuevoProductos);
    }

    AdapterView.OnItemSelectedListener spPedidoNuevoClientes_OnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            cliente = (Cliente) parent.getSelectedItem();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pedido_nuevo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.ab_pedido_agregar_producto) {
            Intent intent = new Intent(PedidoNuevoActivity.this, ProductoSeleccionarActivity.class);
            startActivityForResult(intent, 99);
        } else if (id == R.id.ab_pedido_guardar) {
            if (cliente != null) {
                Pedido pedido = new Pedido();
                pedido.setClienteId(cliente.getClienteId());
                pedido.setDetalle(mRVPedidoDetalleAdapter.getPedidoDetalle());

                if (pedido.getDetalle().size() > 0) {
                    int pedidoId = new PedidoDAO().insertPedido(pedido);
                    if (pedidoId != -1) {
                        Toast.makeText(this, R.string.app_npedido_mensaje_guardar, Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Toast.makeText(this,R.string.app_npedido_mensaje_alerta_noitem, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this,R.string.app_npedido_mensaje_alerta_selectitem , Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelect(PedidoDetalle detalle) {
        editarCantidad(detalle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 99) {
                mRVPedidoDetalleAdapter.add(data.<PedidoDetalle>getParcelableExtra(ProductoSeleccionarActivity.ARG_PEDIDO));
                MostrarTotal();
            }
        }
    }

    private void editarCantidad(final PedidoDetalle pedidoDetalle) {
        // get prompt_cantidad.xml view
        LayoutInflater li = LayoutInflater.from(PedidoNuevoActivity.this);
        View promptsView = li.inflate(R.layout.prompt_cantidad, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PedidoNuevoActivity.this);

        // set prompt_cantidad.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        //final PedidoDetalle pedidoDetalle = mRVPedidoDetalleAdapter.getItem(position);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.etDialogUserInputCantidad);
        userInput.setText(String.valueOf(pedidoDetalle.getCantidad()));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String cantidad = userInput.getText().toString();
                                if (cantidad.length() > 0) {
                                    if (cantidad != ".")
                                        if (Double.valueOf(cantidad) > 0) {
                                            pedidoDetalle.setCantidad(Double.valueOf(cantidad));
                                            mRVPedidoDetalleAdapter.editCantidad(pedidoDetalle);
                                            MostrarTotal();
                                        } else {
                                            Toast.makeText(PedidoNuevoActivity.this, "Cantidad debe ser mayor que cero", Toast.LENGTH_SHORT).show();
                                        }
                                }
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void MostrarTotal() {
        tvPedidoNuevoTotal.setText(String.format(Locale.ENGLISH , "%1$,.2f", mRVPedidoDetalleAdapter.getTotal()));
    }
}
