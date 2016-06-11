package trabajofinalcb.javecarlos.pe.trabajofinalcb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.recyclerview.RVProductoAdapter;
import adapters.recyclerview.interfaces.IRVProductoAdapter;
import dao.PedidoDAO;
import dao.ProductosDAO;
import entities.PedidoCab;
import entities.PedidoDet;
import entities.Productos;
import utils.Constantes;

public class PedidoProductosActivity extends AppCompatActivity implements IRVProductoAdapter {

    ArrayList<Productos> prod =new ArrayList<>();
    private RVProductoAdapter mRVProductoAdapter;
    private RecyclerView rvProductos;
    private Button btAgregar;
    private TextView tvNombreProd,tvPrecio,tvTotal;
    private EditText etCantidad;
    private Toolbar toolbar;
    private int idProducto=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_producto);
        rvProductos = (RecyclerView) findViewById(R.id.rvProductos);
        tvTotal= (TextView) findViewById(R.id.tvTotal);
        tvPrecio= (TextView) findViewById(R.id.tvPrecio);
        tvNombreProd= (TextView) findViewById(R.id.tvNombreProd);
        etCantidad= (EditText) findViewById(R.id.etCantidad);
        btAgregar= (Button) findViewById(R.id.btAgregar);

        rvProductos.setLayoutManager(new LinearLayoutManager(PedidoProductosActivity.this));
        mRVProductoAdapter = new RVProductoAdapter(PedidoProductosActivity.this);
        rvProductos.setAdapter(mRVProductoAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.texto_label_pedido_seleccionar));
        btAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resp="OK";
                if (etCantidad.getText().toString().trim().isEmpty()) {
                    resp = getResources().getString(R.string.error_cantidad_pedido);
                }
                if (idProducto < 1) {
                    resp = getResources().getString(R.string.error_escoger_producto_pedido);
                }
                Toast.makeText(PedidoProductosActivity.this,getResources().getString(R.string.texto_producto_agregado_carrito),Toast.LENGTH_LONG).show();
                if(resp.equals("OK")) {
                    Intent intent = new Intent(PedidoProductosActivity.this, PedidoNuevoActivity.class);
                    intent.putExtra("pCantidad", etCantidad.getText().toString());
                    intent.putExtra("pIdProducto", idProducto);
                    intent.putExtra("pProducto", tvNombreProd.getText().toString());
                    intent.putExtra("pPrecio", tvPrecio.getText().toString());
                    intent.putExtra("pSubTotal", tvTotal.getText().toString());
                    setResult(69, intent);
                    finish();
                }else{
                    Toast.makeText(PedidoProductosActivity.this,resp,Toast.LENGTH_LONG).show();
                }
            }
        });

       etCantidad.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {   }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count){
                Double sTotal;
                try {
                    sTotal = Double.valueOf(etCantidad.getText().toString()) * Double.valueOf(tvPrecio.getText().toString());
                    tvTotal.setText(String.valueOf(sTotal));
                }catch(RuntimeException e){
                    tvTotal.setText("0");
                }
            }
       });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvNombreProd.setText("");
        tvPrecio.setText("");
        etCantidad.setText("1");
        tvTotal.setText("");
        prod=new ProductosDAO().listProducto();
        mRVProductoAdapter.clearAndAddAll(prod);
    }

    //para el toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Capturo el click de la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelectItem(Productos productos) {
        idProducto=productos.getIdProducto();
        tvNombreProd.setText(productos.getNombreP());
        tvPrecio.setText(String.valueOf(productos.getPrecioP()));
        Double sTotal;
        try {
            sTotal = Double.valueOf(etCantidad.getText().toString()) * Double.valueOf(tvPrecio.getText().toString());
            tvTotal.setText(String.valueOf(sTotal));
        }catch(RuntimeException e){
            tvTotal.setText("0");
        }
    }
}