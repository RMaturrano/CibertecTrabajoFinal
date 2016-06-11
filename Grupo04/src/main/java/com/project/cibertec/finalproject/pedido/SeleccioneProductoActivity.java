package com.project.cibertec.finalproject.pedido;

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

import com.google.android.gms.maps.model.LatLng;
import com.project.cibertec.finalproject.R;
import com.project.cibertec.finalproject.dao.ProductoDAO;
import com.project.cibertec.finalproject.entities.PedidoDetalle;
import com.project.cibertec.finalproject.entities.Producto;
import com.project.cibertec.finalproject.producto.adapter.recyclerview.RVAdapterListaProducto;
import com.project.cibertec.finalproject.producto.adapter.recyclerview.listeners.IRVAdapterListaProductoListener;

public class SeleccioneProductoActivity extends AppCompatActivity implements IRVAdapterListaProductoListener {



    private TextView tvPedidoSelProNom, tvPedidoSelProPreUni, tvPedidoSelProTotal;
    private EditText edtPedidoSelProCantidad;
    private Button btnPedidoSelProAgregar;
    private RecyclerView mRVProductosListado;
    private Producto mProductoSeleccionado;

    private RVAdapterListaProducto mRVAdapterListaProducto ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos_seleccione_producto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.tituloSeleccioneProducto);

        tvPedidoSelProNom = (TextView) findViewById(R.id.tvPedidoSelProNom);
        tvPedidoSelProPreUni = (TextView) findViewById(R.id.tvPedidoSelProPreUni);
        tvPedidoSelProTotal = (TextView) findViewById(R.id.tvPedidoSelProTotal);
        edtPedidoSelProCantidad = (EditText) findViewById(R.id.edtPedidoSelProCantidad);
        btnPedidoSelProAgregar = (Button) findViewById(R.id.btnPedidoSelProAgregar);

        edtPedidoSelProCantidad.addTextChangedListener(edtPedidoSelProCantidadTextWatcher);
        btnPedidoSelProAgregar.setOnClickListener(btnPedidoSelProAgregarOnClickListener);

        mRVProductosListado = (RecyclerView) findViewById(R.id.rvPedidosSeleccion);
        mRVProductosListado.setLayoutManager(new LinearLayoutManager(SeleccioneProductoActivity.this));
        mRVProductosListado.setHasFixedSize(true);
        mRVAdapterListaProducto = new RVAdapterListaProducto(SeleccioneProductoActivity.this);
        mRVProductosListado.setAdapter(mRVAdapterListaProducto);

    }

    View.OnClickListener btnPedidoSelProAgregarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mProductoSeleccionado != null){
                if(!edtPedidoSelProCantidad.getText().toString().equals("")){
                    if(Integer.parseInt(edtPedidoSelProCantidad.getText().toString()) > 0){

                        PedidoDetalle detail = new PedidoDetalle();
                        detail.setIdProducto(mProductoSeleccionado.getIdProducto());
                        detail.setNombreProducto(mProductoSeleccionado.getNombreProducto());
                        detail.setDescripcionProducto(mProductoSeleccionado.getDescripcionProducto());
                        detail.setCantidad(Integer.parseInt(edtPedidoSelProCantidad.getText().toString()));
                        detail.setTotalLinea(Double.parseDouble(tvPedidoSelProTotal.getText().toString().split("S/")[1].trim()));

                        Intent intent = new Intent();
                        intent.putExtra("producto", detail);
                        setResult(RESULT_OK, intent);
                        finish();

                    }else
                        Toast.makeText(SeleccioneProductoActivity.this,"La cantidad debe ser mayor a cero",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(SeleccioneProductoActivity.this,"Ingrese una cantidad",Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(SeleccioneProductoActivity.this,"Seleccione el producto que desea agregar",Toast.LENGTH_SHORT).show();
        }
    };

    //Evento textChange del edit text Cantidad
    TextWatcher edtPedidoSelProCantidadTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(mProductoSeleccionado != null && !s.toString().equals("")){
                if(Integer.parseInt(s.toString()) > 0)
                tvPedidoSelProTotal.setText("S/ " +String.valueOf(DoubleRound.round(Integer.parseInt(s.toString()) * mProductoSeleccionado.getPrecioProducto(),2)));
            }else
                tvPedidoSelProTotal.setText("");
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Override
    protected void onStart() {
        super.onStart();
        mRVAdapterListaProducto.clearAndAddAll(new ProductoDAO().listProducto());
    }

    @Override
    public void onItemClick(Producto producto) {
        mProductoSeleccionado = new Producto();
        mProductoSeleccionado = producto;

        tvPedidoSelProNom.setText(producto.getNombreProducto());
        tvPedidoSelProPreUni.setText("S/ "+ String.valueOf(producto.getPrecioProducto()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
