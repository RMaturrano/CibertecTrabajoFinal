package com.trabajo.capturapedido.capturapedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.RVProductoAdapter;
import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.interfaces.IRVProductoAdapter;
import com.trabajo.capturapedido.capturapedido.datos.DAProducto;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseHelper;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseSingleton;
import com.trabajo.capturapedido.capturapedido.entities.BEPedido;
import com.trabajo.capturapedido.capturapedido.entities.BEProducto;

import java.io.IOException;

public class ProductoActivity extends AppCompatActivity implements IRVProductoAdapter {

    public final static String ARG_PEDIDO = "arg_pedido";
    private BEPedido mBEPedido = null;
    private Button btAddProducto;

    private RVProductoAdapter mRVProductoAdapter;
    private RecyclerView rvProducto;
    private Toolbar tb_producto;
    private DAProducto mDAProducto;

    private TextView tvIdProdPedido, tvNomProdPedido,tvDescProdPedido, tvPrecioProdPedido, tvTotalProdPedido;
    private EditText etCantProdPedido;

    private int pedidoDetId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_activity);

        rvProducto = (RecyclerView) findViewById(R.id.rvProducto);

        mDAProducto = new DAProducto();

        tb_producto = (Toolbar) findViewById(R.id.tbProducto);
        setSupportActionBar(tb_producto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Seleccione un Producto");
        tb_producto.setTitleTextColor(0xFFFFFFFF);


        DataBaseHelper dataBaseHelper = new DataBaseHelper(ProductoActivity.this);
        try {
            dataBaseHelper.createDataBase();
            new DataBaseSingleton(ProductoActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rvProducto.setLayoutManager(new LinearLayoutManager(ProductoActivity.this));
        rvProducto.setHasFixedSize(true);
        mRVProductoAdapter = new RVProductoAdapter(ProductoActivity.this);

        rvProducto.setAdapter(mRVProductoAdapter);

        mRVProductoAdapter.clearAndAddAll(mDAProducto.listaProducto());


        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("arg_pedido"))
            mBEPedido = getIntent().getParcelableExtra("arg_pedido");

        if (getIntent().getExtras().containsKey("arg_list_size"))
            pedidoDetId = getIntent().getIntExtra("arg_list_size", 0);

        tvIdProdPedido = (TextView) findViewById(R.id.tvIdProdPedido);
        tvNomProdPedido = (TextView) findViewById(R.id.tvNomProdPedido);
        tvDescProdPedido=(TextView)findViewById(R.id.tvDescProdPedido);
        tvPrecioProdPedido = (TextView) findViewById(R.id.tvPrecioProdPedido);
        tvTotalProdPedido = (TextView) findViewById(R.id.tvTotalProdPedido);
        etCantProdPedido = (EditText) findViewById(R.id.etCantProdPedido);
        btAddProducto = (Button) findViewById(R.id.btAddProducto);

        /*etCantProdPedido.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    double totalPedidoDet = Integer.parseInt(etCantProdPedido.getText().toString().trim()) * Double.parseDouble(tvPrecioProdPedido.getText().toString().trim());
                    tvTotalProdPedido.setText(String.valueOf(totalPedidoDet));
                    return true;
                }
                return false;
            }

        });*/


        etCantProdPedido.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 0) {
                    float totalPedidoDet = ( Integer.parseInt(etCantProdPedido.getText().toString().trim()) * Float.parseFloat(tvPrecioProdPedido.getText().toString().trim()) )*100/100;
                    tvTotalProdPedido.setText(String.valueOf(totalPedidoDet));
                }
                else
                    tvTotalProdPedido.setText("");
            }
        });

        btAddProducto.setOnClickListener(btAddProductoOnClickListener);

        if (mBEPedido != null)
            setData();
    }

    private void setData() {
        tvIdProdPedido.setText(mBEPedido.getIdProdPedidoDet());
        tvNomProdPedido.setText(mBEPedido.getNomProdPedidoDet());
        tvPrecioProdPedido.setText(String.valueOf(mBEPedido.getPrecioPedidoDet()));
        tvTotalProdPedido.setText(String.valueOf(mBEPedido.getTotalPedido()));
        etCantProdPedido.setText(String.valueOf(mBEPedido.getCantPedidoDet()));
    }

    @Override
    public void onSelectItem(BEProducto producto) {
        tvIdProdPedido.setText(String.valueOf(producto.getIdProducto()));
        tvNomProdPedido.setText(producto.getNomProducto());
        tvPrecioProdPedido.setText(String.valueOf(producto.getPrecioProducto()));
        tvDescProdPedido.setText(producto.getDescProducto());
        etCantProdPedido.setFocusableInTouchMode(true);
        etCantProdPedido.requestFocus();
    }


    View.OnClickListener btAddProductoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean isOK = true;
            if (tvIdProdPedido.getText().toString().trim().isEmpty()) {
                Toast.makeText(ProductoActivity.this, " Elegir Producto", Toast.LENGTH_LONG).show();
                isOK = false;
            }
            if (etCantProdPedido.getText().toString().trim().isEmpty() || Integer.parseInt(etCantProdPedido.getText().toString().trim()) == 0) {
                Toast.makeText(ProductoActivity.this, " Ingrese Cantidad", Toast.LENGTH_LONG).show();
                isOK = false;
            }
            if (isOK) {
                if (mBEPedido == null) {
                    mBEPedido = new BEPedido();
                    mBEPedido.setIdPedidoDet(pedidoDetId);
                }
                mBEPedido.setIdProdPedidoDet(Integer.parseInt(tvIdProdPedido.getText().toString().trim()));
                mBEPedido.setNomProdPedidoDet(tvNomProdPedido.getText().toString().trim());
                mBEPedido.setDescProdPedidoDet(tvDescProdPedido.getText().toString().trim());
                mBEPedido.setPrecioPedidoDet(Double.parseDouble(tvPrecioProdPedido.getText().toString().trim()));
                mBEPedido.setCantPedidoDet(Integer.parseInt(etCantProdPedido.getText().toString().trim()));
                double totalPedidoDet = Math.rint( ( Integer.parseInt(etCantProdPedido.getText().toString().trim()) * Double.parseDouble(tvPrecioProdPedido.getText().toString().trim()) )*100 )/100 ;
                mBEPedido.setTotalPedidoDet(totalPedidoDet);

                Intent intent = new Intent();
                intent.putExtra("arg_pedido", mBEPedido);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();


        return true;
    }

}
