package trabajofinalcb.javecarlos.pe.trabajofinalcb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import dao.ProductosDAO;
import entities.Productos;
import utils.Constantes;

/**
 * Created by gosoriot on 11/05/2016.
 */
public class ProductoAddEditActivity extends AppCompatActivity {

    private EditText etProductNombre, etCantStock, etPrecioUnid;
    Productos productos;
    boolean esNuevo;
    String Productox, stockx, preciox;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_add_edit);
        etProductNombre = (EditText) findViewById(R.id.etProductNombre);
        etCantStock = (EditText) findViewById(R.id.etCantStock);
        etPrecioUnid = (EditText) findViewById(R.id.etPrecioUnid);
        productos = ObtenerProducto();
        esNuevo = VerificarEsNuevo();

        //para el toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!esNuevo && productos != null) {
            SetDataCliente(productos);
            getSupportActionBar().setTitle(getResources().getString(R.string.texto_detalle_producto));
        } else {
            getSupportActionBar().setTitle(getResources().getString(R.string.texto_nuevo_producto));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el menú que va a aparecer en el Toolbar
        getMenuInflater().inflate(R.menu.menu_add_edit_guardar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Capturo el click de la flecha hacia atrás
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else  //Capturo el click del guardar
            if (item.getItemId() == R.id.btnGuardar) {
                GuardarDatos();
                return true;
            }

        return super.onOptionsItemSelected(item);
    }

    private void GuardarDatos() {
        boolean isOK = true;
        Productox = etProductNombre.getText().toString();
        stockx = etCantStock.getText().toString();
        preciox = etPrecioUnid.getText().toString();
        if (Productox.trim().isEmpty()) {
            etProductNombre.setError(getResources().getString(R.string.error_vacio_nombre_producto));
            isOK = false;
        } else {
            if (Productox.trim().length() > 20) {
                etProductNombre.setError(getResources().getString(R.string.error_maxlength_nombre_producto));
                isOK = false;
            }
        }

        if (stockx.trim().isEmpty()) {
            etCantStock.setError(getResources().getString(R.string.error_vacio_stock));
            isOK = false;
        }
        if (preciox.isEmpty()) {
            etPrecioUnid.setError(getResources().getString(R.string.error_vacio_precio_unitario));
            isOK = false;
        }
        if (isOK) {
            if (productos == null)
                productos = new Productos();
            productos.setNombreP(Productox.trim());
            productos.setStockP(Integer.parseInt(stockx.trim()));
            productos.setPrecioP(Double.parseDouble(preciox.trim()));

            if (esNuevo) {
                boolean isInserted = new ProductosDAO().insertProducto(productos);
                if (isInserted) {
                    Toast.makeText(ProductoAddEditActivity.this, productos.getNombreP() + " " +
                            getResources().getString(R.string.texto_mensaje_insertado), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ProductoAddEditActivity.this, ProductoActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    String errorInsercion = getResources().getString(R.string.error_insertar_cliente);
                    String btnAceptar = getResources().getString(R.string.btnAceptar);
                    new AlertDialog.Builder(ProductoAddEditActivity.this).setTitle(R.string.app_name)
                            .setMessage(errorInsercion).setNegativeButton(btnAceptar, null).show();
                }
            } else {
                boolean isInserted = new ProductosDAO().updateProducto(productos);
                if (isInserted) {
                    Toast.makeText(ProductoAddEditActivity.this, productos.getNombreP() + " " +
                            getResources().getString(R.string.texto_mensaje_actualizado), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ProductoAddEditActivity.this, ProductoActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    String errorActualizar = getResources().getString(R.string.error_actualizar_cliente);
                    String btnAceptar = getResources().getString(R.string.btnAceptar);
                    new AlertDialog.Builder(ProductoAddEditActivity.this).setTitle(R.string.app_name)
                            .setMessage(errorActualizar).setNegativeButton(btnAceptar, null).show();
                }
            }
        }
    }

    private void SetDataCliente(Productos productos) {
        etProductNombre.setText(ObtenerProducto().getNombreP());
        etCantStock.setText(String.valueOf(ObtenerProducto().getStockP()));
        etPrecioUnid.setText(String.valueOf(ObtenerProducto().getPrecioP()));
    }

    private boolean VerificarEsNuevo() {
        boolean resultado;

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constantes.ARG_NUEVO_PRODUCTO)) {
            resultado = getIntent().getBooleanExtra(Constantes.ARG_NUEVO_PRODUCTO, true);
        } else {
            resultado = true;
        }

        return resultado;
    }


    private Productos ObtenerProducto() {
        Productos ProductoInfo;
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constantes.ARG_PRODUCTO)) {
            ProductoInfo = getIntent().getParcelableExtra(Constantes.ARG_PRODUCTO);
        } else {
            ProductoInfo = null;
        }
        return ProductoInfo;
    }
}
