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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.recyclerview.RVPedidoDetAdapter;
import adapters.recyclerview.interfaces.IRVPedidoDetAdapter;
import adapters.spinner.SPAdapter;
import dao.ClienteDAO;
import dao.PedidoDAO;
import entities.Cliente;
import entities.Pedido;
import entities.PedidoCab;
import entities.PedidoDet;
import utils.Constantes;

public class PedidoNuevoActivity extends AppCompatActivity implements IRVPedidoDetAdapter {

    ArrayList<PedidoDet> prod =new ArrayList<>();
    Double dTotal;
    private SPAdapter mSPAdapter;
    private Spinner spCliente;

    Cliente cliente = new Cliente();

    private RVPedidoDetAdapter mRVPedidoDetAdapter;
    private RecyclerView rvPedidoDet;

    private TextView tvTotal;

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_nuevo);
        rvPedidoDet = (RecyclerView) findViewById(R.id.rvPedidosDet);
        spCliente = (Spinner)findViewById(R.id.spCliente);
        tvTotal= (TextView) findViewById(R.id.tvTotalPedidoInfo);
        dTotal=0.0;
        tvTotal.setText(String.valueOf(dTotal));

        //RecyclerView
        rvPedidoDet.setLayoutManager(new LinearLayoutManager(PedidoNuevoActivity.this));
        mRVPedidoDetAdapter = new RVPedidoDetAdapter(PedidoNuevoActivity.this);
        rvPedidoDet.setAdapter(mRVPedidoDetAdapter);

        //Spinner
        mSPAdapter = new SPAdapter(PedidoNuevoActivity.this,new ClienteDAO().listCliente());
        spCliente.setAdapter(mSPAdapter);
        spCliente.setOnItemSelectedListener(spClienteOnItemSelectedListener);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarNuevo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.texto_label_pedido_nuevo));

        cliente = ObtenerCliente();
        if (cliente != null){
            spCliente.setSelection(mSPAdapter.getItemIndexById(cliente.getEmpresaId()));
            spCliente.setEnabled(false);
        }
        else{
            spCliente.setEnabled(true);
        }
    }

    private Cliente ObtenerCliente() {
        Cliente clienteInfo;
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constantes.ARG_CLIENTE)) {
            clienteInfo = getIntent().getParcelableExtra(Constantes.ARG_CLIENTE);
        } else {
            clienteInfo = null;
        }

        return clienteInfo;

    }

    AdapterView.OnItemSelectedListener spClienteOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            cliente = (Cliente) parent.getSelectedItem();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    @Override
    public void onItemClick(PedidoDet pedido) {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //para el toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el menú que va a aparecer en el Toolbar
        getMenuInflater().inflate(R.menu.menu_tool_nuevo_guardar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Capturo el click de la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else  //Capturo el click de agregar producto o guardar pedido
            if (item.getItemId() == R.id.btnAgregar) {
                Intent intent = new Intent(PedidoNuevoActivity.this, PedidoProductosActivity.class);
                startActivityForResult(intent,0);
                return true;
            }
            if (item.getItemId() == R.id.btnGuardar) {
                GuardarPedido();
                finish();
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PedidoDet pedido = new PedidoDet();
        if(resultCode==69){
            pedido.setNombreProd(data.getExtras().getString("pProducto"));
            pedido.setIdProducto(data.getExtras().getInt("pIdProducto"));
            pedido.setCantidad(Double.valueOf(data.getExtras().getString("pCantidad")));
            pedido.setPrecio(Double.valueOf(data.getExtras().getString("pPrecio")));
            pedido.setSubTotal(Double.valueOf(data.getExtras().getString("pSubTotal")));

            dTotal += pedido.getSubTotal();
            tvTotal.setText(String.valueOf(dTotal));
            prod.add(pedido);
        }
        mRVPedidoDetAdapter.clearAndAddAll(prod);
    }
    private void GuardarPedido(){
        int idPedido;
        idPedido=new PedidoDAO().NewIdPedido();
        Pedido ped =new Pedido();
        for(int i=0;i<prod.size();i++){
            ped.setIdPedido(idPedido);
            ped.setIdCliente(cliente.getEmpresaId());
            ped.setIdProducto(prod.get(i).getIdProducto());
            ped.setNuPrecio(prod.get(i).getPrecio());
            ped.setNuCantidad(prod.get(i).getCantidad());
            new PedidoDAO().insertPedido(ped);
        }
    }
}