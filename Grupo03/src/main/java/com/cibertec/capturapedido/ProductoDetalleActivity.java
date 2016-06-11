package com.cibertec.capturapedido;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cibertec.capturapedido.dao.ProductoDAO;
import com.cibertec.capturapedido.entities.Producto;

/**
 * Created by bestrada on 12/05/2016.
 */
public class ProductoDetalleActivity extends AppCompatActivity {
    final public static String ARG_PRODUCTO = "arg_producto";
    Toolbar toolbar_item;
    Producto producto = null;

    TextView tvProductoDetalleNombre, tvProductoDetalleDescripcion, tvProductoDetallePrecio;
    Button btClienteDetalleNuevoPedido;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_detalle_activity);

        tvProductoDetalleNombre = (TextView) findViewById(R.id.tvProductoDetalleNombre);
        tvProductoDetalleDescripcion = (TextView) findViewById(R.id.tvProductoDetalleDescripcion);
        tvProductoDetallePrecio = (TextView) findViewById(R.id.tvProductoDetallePrecio);

        btClienteDetalleNuevoPedido = (Button) findViewById(R.id.btClienteDetalleNuevoPedido);
       // btClienteDetalleNuevoPedido.setOnClickListener(btClienteDetalleNuevoPedidoOnClickListener);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        producto = getIntent().getParcelableExtra(ARG_PRODUCTO);
        setData(producto);
    }


    private void setData(Producto producto){
        getSupportActionBar().setTitle(producto.getNombre());

        tvProductoDetalleNombre.setText(producto.getNombre());
        tvProductoDetalleDescripcion.setText(producto.getDescripcion());
        tvProductoDetallePrecio.setText(String.valueOf( producto.getPrecio()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_producto_editar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        else if (item.getItemId()==R.id.ab_ProductoEdicion_Editar){
           Intent intent = new Intent(ProductoDetalleActivity.this, ProductoGrabarActivity.class);
            intent.putExtra(ProductoGrabarActivity.ARG_PRODUCTO, producto);
            startActivityForResult(intent, 99);
        }
        else if (item.getItemId()==R.id.ab_ProductoEdicion_Eliminar){

            AlertDialog myDialogBox = new AlertDialog.Builder(this)
                    //set message, title, and icon
                    .setTitle("Eliminar")
                    .setMessage("¿Desea eliminar el producto?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();

                            if(new ProductoDAO().deleteProducto(producto)){
                                Toast.makeText(ProductoDetalleActivity.this, "Producto eliminado", Toast.LENGTH_LONG).show();
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

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 99){
                producto = data.<Producto>getParcelableExtra(ProductoGrabarActivity.ARG_PRODUCTO);
                setData(producto);
            }
        }
    }
}
