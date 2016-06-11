package trabajofinalcb.javecarlos.pe.trabajofinalcb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import entities.Cliente;
import entities.Productos;
import utils.Constantes;

/**
 * Created by gosoriot on 12/05/2016.
 */
public class ProductoInfoActivity extends AppCompatActivity {

    private TextView tvNomProducInfo, tvStockInfo, tvPrecioInfo;
    private Productos productos;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_info);
        CargarTextView();

        productos = ObtenerProducto();

        if (productos != null)
            SetDataProducto(productos);

        //para el toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(productos != null ? getResources().getString(R.string.texto_detalle_producto) : "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el menú que va a aparecer en el Toolbar
        getMenuInflater().inflate(R.menu.menu_cliente_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Capturo el click de la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else  //Capturo el click del editar
            if (item.getItemId() == R.id.btnEditar) {
                boolean esNuevo;
                Intent intent = new Intent(ProductoInfoActivity.this, ProductoAddEditActivity.class);
                esNuevo = productos == null;
                intent.putExtra(Constantes.ARG_NUEVO_PRODUCTO, esNuevo);
                intent.putExtra(Constantes.ARG_PRODUCTO, productos);
                startActivity(intent);
                return true;
            }

        return super.onOptionsItemSelected(item);
    }

    private Productos ObtenerProducto() {
        Productos productosInfo;
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constantes.ARG_PRODUCTO)) {
            productosInfo = getIntent().getParcelableExtra(Constantes.ARG_PRODUCTO);
        } else {
            productosInfo = null;
        }
        return productosInfo;
    }

    private void SetDataProducto(Productos producto) {
        tvNomProducInfo.setText(producto.getNombreP());
        tvStockInfo.setText(String.valueOf(producto.getStockP()));
        tvPrecioInfo.setText(String.valueOf(producto.getPrecioP()));
    }

    private void CargarTextView() {
        tvNomProducInfo = (TextView) findViewById(R.id.tvNomProducInfo);
        tvStockInfo = (TextView) findViewById(R.id.tvStockInfo);
        tvPrecioInfo = (TextView) findViewById(R.id.tvPrecioInfo);
    }
}
