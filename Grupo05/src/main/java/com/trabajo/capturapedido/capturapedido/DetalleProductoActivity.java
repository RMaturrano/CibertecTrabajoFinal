package com.trabajo.capturapedido.capturapedido;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.trabajo.capturapedido.capturapedido.datos.DAProducto;
import com.trabajo.capturapedido.capturapedido.entities.BEProducto;

/**
 * Created by Alejandra on 01/06/2016.
 */
public class DetalleProductoActivity extends AppCompatActivity {
    public final static String ARG_DETALLE_PRODUCTO = "arg_detalle";

    private Toolbar tbDetalleProducto;
    private TextView tvDetalleNombreProducto, tvDetalleDescripcionProducto, tvDetallePrecioProducto;
    private BEProducto mBEProducto;
    private DAProducto mDAProducto;
    private boolean isOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_producto_activity);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ARG_DETALLE_PRODUCTO)) {
            mBEProducto = getIntent().getParcelableExtra(ARG_DETALLE_PRODUCTO);
            isOk = true;
        }

        tvDetalleNombreProducto = (TextView) findViewById(R.id.tvNomCliente);
        tvDetalleDescripcionProducto = (TextView) findViewById(R.id.tvDetalleDescripcionProducto);
        tvDetallePrecioProducto = (TextView) findViewById(R.id.tvDetallePrecioProducto);

        tbDetalleProducto = (Toolbar) findViewById(R.id.tbDetalleProducto);
        setSupportActionBar(tbDetalleProducto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalle Del Producto");
        tbDetalleProducto.setTitleTextColor(0xFFFFFFFF);

        if (isOk)
        setData();


    }

    private void setData(){
        tvDetalleNombreProducto.setText(mBEProducto.getNomProducto());
        tvDetalleDescripcionProducto.setText(mBEProducto.getDescProducto());
        tvDetallePrecioProducto.setText(String.valueOf(mBEProducto.getPrecioProducto()));
    }


    private void eliminarProducto(BEProducto producto){
        mDAProducto = new DAProducto();
        mDAProducto.deleteProducto(producto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_producto_detalle,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            Intent intent  = new Intent(DetalleProductoActivity.this, ListaProductoActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else if (item.getItemId() == R.id.mpEditar){
            Intent intent = new Intent(DetalleProductoActivity.this, CrearEditProductoActivity.class);
            intent.putExtra(CrearEditProductoActivity.ARG_EDIT_PEDIDO,mBEProducto);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.mpEliminar){
                eliminarProducto(mBEProducto);
            Intent intent = new Intent(DetalleProductoActivity.this, ListaProductoActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
