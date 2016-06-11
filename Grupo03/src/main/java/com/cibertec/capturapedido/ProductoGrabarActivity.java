package com.cibertec.capturapedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cibertec.capturapedido.dao.ClienteDAO;
import com.cibertec.capturapedido.dao.ProductoDAO;
import com.cibertec.capturapedido.entities.Cliente;
import com.cibertec.capturapedido.entities.Producto;

/**
 * Created by bestrada on 17/05/2016.
 */
public class ProductoGrabarActivity extends AppCompatActivity {
    TextInputLayout tilProductoNombre, tilProductoDescripcion, tilProductoPrecio;

    Producto mProducto = null;
    public static final String ARG_PRODUCTO = "arg_producto";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_grabar_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.app_title_tollbar_nproducto);

        if (getIntent().getParcelableExtra(ARG_PRODUCTO) != null) {
            mProducto = getIntent().getParcelableExtra(ARG_PRODUCTO);
            getSupportActionBar().setTitle(mProducto.getNombre() );
        }
        tilProductoNombre = (TextInputLayout) findViewById(R.id.tilProductoNombre);
        tilProductoDescripcion = (TextInputLayout) findViewById(R.id.tilProductoDescripcion);
        tilProductoPrecio = (TextInputLayout) findViewById(R.id.tilProductoPrecio);

        if (mProducto != null)
            setData();
    }

    private void setData() {
        tilProductoNombre.getEditText().setText(mProducto.getNombre());
        tilProductoDescripcion.getEditText().setText(mProducto.getDescripcion());
        tilProductoPrecio.getEditText().setText(String.valueOf(mProducto.getPrecio()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cliente_grabar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void save() {
        boolean isOK = true;

        tilProductoNombre.setError(null);
        tilProductoDescripcion.setError(null);
        tilProductoPrecio.setError(null);

        if (tilProductoNombre.getEditText().getText().toString().trim().isEmpty()) {
            tilProductoNombre.setError("Ingrese el Nombre");
            tilProductoNombre.setErrorEnabled(true);
            isOK = false;
        }
        if (tilProductoDescripcion.getEditText().getText().toString().trim().isEmpty()) {
            tilProductoDescripcion.setError("Ingrese la descripción");
            tilProductoDescripcion.setErrorEnabled(true);
            isOK = false;
        }
        if (tilProductoPrecio.getEditText().getText().toString().trim().isEmpty()) {
            tilProductoPrecio.setError("Ingrese el precio");
            tilProductoPrecio.setErrorEnabled(true);
            isOK = false;
        }

        if (isOK) {
            boolean estado = true;//Varible de validación
            if (mProducto == null) mProducto = new Producto(); //Si es un Producto Nuevo

            mProducto.setNombre(tilProductoNombre.getEditText().getText().toString().trim());
            mProducto.setDescripcion(tilProductoDescripcion.getEditText().getText().toString().trim());
            mProducto.setPrecio(Double.parseDouble(tilProductoPrecio.getEditText().getText().toString().trim()));

            if (mProducto.getProductoId() > 0) {//si el Producto ya esta registrado
                if (!new ProductoDAO().updateProducto(mProducto)) estado = false;
            } else if (!new ProductoDAO().insertProducto(mProducto)) estado = false;//Nuevo Producto

            if (estado) {//Registro exitoso
                Toast.makeText(ProductoGrabarActivity.this, "Se guardó correctamente", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, new Intent().putExtra(ARG_PRODUCTO, mProducto));
                finish();
            } else {
                Toast.makeText(ProductoGrabarActivity.this, "Error en Guardar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        else if (item.getItemId() == R.id.ab_save) save();
        return true;
    }
}
