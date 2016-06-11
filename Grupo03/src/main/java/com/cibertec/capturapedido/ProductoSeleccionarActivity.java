package com.cibertec.capturapedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cibertec.capturapedido.adapter.recyclerview.RVProductoAdapter;
import com.cibertec.capturapedido.adapter.recyclerview.interfaces.IRVProductoAdapter;
import com.cibertec.capturapedido.dao.ProductoDAO;
import com.cibertec.capturapedido.entities.PedidoDetalle;
import com.cibertec.capturapedido.entities.Producto;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jorge on 27/05/2016.
 */
public class ProductoSeleccionarActivity extends AppCompatActivity implements IRVProductoAdapter {
    public static final String ARG_PEDIDO = "arg_pedido";
    TextView tvProductoSeleccionarNombre, tvProductoSeleccionarPrecio, tvProductoSeleccionarTotal;
    EditText etProductoSeleccionarCantidad;
    Button btProductoSeleccionarAgregarCarrito;

    RecyclerView rvProductosSeleccionar;
    RVProductoAdapter mRVProductoAdapter;
    LinearLayout layout_producto_sel;
    Producto productoSeleccionado = null;

    //ArrayList<PedidoDetalle> mLstProductosSeleccionados;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_seleccionar_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle(R.string.app_title_tollbar_sproducto);

        layout_producto_sel = (LinearLayout) findViewById(R.id.layout_producto_sel);

        tvProductoSeleccionarNombre = (TextView) findViewById(R.id.tvProductoSeleccionarNombre);
        tvProductoSeleccionarPrecio = (TextView) findViewById(R.id.tvProductoSeleccionarPrecio);
        tvProductoSeleccionarTotal = (TextView) findViewById(R.id.tvProductoSeleccionarTotal);
        etProductoSeleccionarCantidad = (EditText) findViewById(R.id.etProductoSeleccionarCantidad);
        etProductoSeleccionarCantidad.addTextChangedListener(fieldValidatorTextWatcher);
        btProductoSeleccionarAgregarCarrito = (Button) findViewById(R.id.btProductoSeleccionarAgregarCarrito);
        btProductoSeleccionarAgregarCarrito.setOnClickListener(btProductoSeleccionarAgregarCarritoClickListener);

        rvProductosSeleccionar = (RecyclerView) findViewById(R.id.rvProductosSeleccionar);
        rvProductosSeleccionar.setLayoutManager(new LinearLayoutManager(this));
        mRVProductoAdapter = new RVProductoAdapter(this);
        rvProductosSeleccionar.setAdapter(mRVProductoAdapter);

        mRVProductoAdapter.clearAndAddAll(new ProductoDAO().listProduto(""));
        //mLstProductosSeleccionados = new ArrayList<>();
    }

    TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            CalcularTotal();
        }
    };

    View.OnClickListener btProductoSeleccionarAgregarCarritoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(etProductoSeleccionarCantidad.getText().toString().trim().length() == 0)
            {
                Toast.makeText(ProductoSeleccionarActivity.this, "Ingrese una cantidad", Toast.LENGTH_SHORT).show();
                return;
            }
            if(Double.valueOf(etProductoSeleccionarCantidad.getText().toString()) == 0)
            {
                Toast.makeText(ProductoSeleccionarActivity.this, "La cantidad debe ser mayor a cero", Toast.LENGTH_SHORT).show();
                return;
            }

            PedidoDetalle pedidoDetalle = new PedidoDetalle();
            pedidoDetalle.setProductoId(productoSeleccionado.getProductoId());
            pedidoDetalle.setProductoNombre(productoSeleccionado.getNombre());
            pedidoDetalle.setProductoDescripcion(productoSeleccionado.getDescripcion());
            pedidoDetalle.setCantidad(Double.valueOf(etProductoSeleccionarCantidad.getText().toString()));
            pedidoDetalle.setPrecio(productoSeleccionado.getPrecio());

            setResult(RESULT_OK, new Intent().putExtra(ARG_PEDIDO, pedidoDetalle));
            finish();
        }
    };

    private void CalcularTotal(){
        if (etProductoSeleccionarCantidad.getText().toString().trim().length() > 0) {
            double cantidad = Double.valueOf(etProductoSeleccionarCantidad.getText().toString());
            double precio = Double.valueOf(tvProductoSeleccionarPrecio.getText().toString());
            tvProductoSeleccionarTotal.setText(String.format(Locale.ENGLISH , "%1$,.2f", cantidad * precio));
        }else tvProductoSeleccionarTotal.setText("0.0");
    }

    public void onSelectItem(Producto producto) {
        //Agregamos una animacion translate (Carpeta anim)
        layout_producto_sel.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_move));
        layout_producto_sel.setVisibility(View.VISIBLE);

        productoSeleccionado = producto;
        tvProductoSeleccionarNombre.setText(producto.getNombre());
        tvProductoSeleccionarPrecio.setText(String.format(Locale.ENGLISH , "%1$,.2f",producto.getPrecio()));
        etProductoSeleccionarCantidad.setText("1");
        CalcularTotal();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            onBackPressed();

        return true;
    }
}
