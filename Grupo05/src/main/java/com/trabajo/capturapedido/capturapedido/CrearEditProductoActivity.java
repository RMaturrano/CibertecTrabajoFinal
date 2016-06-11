package com.trabajo.capturapedido.capturapedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.trabajo.capturapedido.capturapedido.datos.DAProducto;
import com.trabajo.capturapedido.capturapedido.entities.BEProducto;

/**
 * Created by Alejandra on 28/05/2016.
 */
public class CrearEditProductoActivity extends AppCompatActivity {

    public final static String ARG_EDIT_PEDIDO = "arg_edit_pedido";

    private Toolbar toolbar;
    private TextInputLayout tilCENombreProducto, tilCEDescripcionProducto, tilCEPrecioProducto;
    private BEProducto mProducto;
    private boolean isUpdate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearedit_producto_activity);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ARG_EDIT_PEDIDO)) {
            //actualizar
            mProducto = getIntent().getParcelableExtra(ARG_EDIT_PEDIDO);
            isUpdate = true;
        } else {
            //crear
            mProducto = null;
            isUpdate = false;
        }

        tilCENombreProducto = (TextInputLayout) findViewById(R.id.tilCENombreProducto);
        tilCEDescripcionProducto = (TextInputLayout) findViewById(R.id.tilCEDescripcionProducto);
        tilCEPrecioProducto = (TextInputLayout) findViewById(R.id.tilCEPrecioProducto);

        //Inflamos el toolbar
        toolbar = (Toolbar) findViewById(R.id.tbCEProducto);

        //Lo asignamos como ActionBar
        setSupportActionBar(toolbar);

        //Línea para activar la flecha hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);


        if (isUpdate) {
            setData();
            getSupportActionBar().setTitle("Actualizacion del Producto");
        } else {
            getSupportActionBar().setTitle("Producto Nuevo");
        }
    }

    private void setData() {
        tilCENombreProducto.getEditText().setText(mProducto.getNomProducto());
        tilCEDescripcionProducto.getEditText().setText(mProducto.getDescProducto());
        tilCEPrecioProducto.getEditText().setText(String.valueOf(mProducto.getPrecioProducto()));
    }

    public void guardarProducto() {
        boolean isOk = true;

        tilCENombreProducto.setError(null);
        tilCEDescripcionProducto.setError(null);
        tilCEPrecioProducto.setError(null);

        tilCENombreProducto.setErrorEnabled(false);
        tilCEDescripcionProducto.setErrorEnabled(false);
        tilCEPrecioProducto.setErrorEnabled(false);

        if (tilCENombreProducto.getEditText().getText().toString().trim().isEmpty()) {
            tilCENombreProducto.setError("*Ingrese Nombre del Producto");
            tilCENombreProducto.setErrorEnabled(true);
            isOk = false;
        }

        if (tilCEDescripcionProducto.getEditText().getText().toString().trim().isEmpty()) {
            tilCEDescripcionProducto.setError("*Ingrese Descripción del Producto");
            tilCEDescripcionProducto.setErrorEnabled(true);
            isOk = false;
        }

        if (tilCEPrecioProducto.getEditText().getText().toString().trim().isEmpty()) {
            tilCEPrecioProducto.setError("*Ingrese Precio del Producto");
            tilCEPrecioProducto.setErrorEnabled(true);
            isOk = false;
        } else if (Double.parseDouble(tilCEPrecioProducto.getEditText().getText().toString().trim()) <= 0) {
            tilCEPrecioProducto.setError("*Precio Invalido");
            tilCEPrecioProducto.setErrorEnabled(true);
            isOk = false;
        }

        if (isOk) {
            if (mProducto == null) {
                mProducto = new BEProducto();
            }


            mProducto.setNomProducto(tilCENombreProducto.getEditText().getText().toString().trim());
            mProducto.setDescProducto(tilCEDescripcionProducto.getEditText().getText().toString().trim());
            mProducto.setPrecioProducto(Double.parseDouble(tilCEPrecioProducto.getEditText().getText().toString().trim()));

            if (isUpdate) {
                boolean isUpdated = new DAProducto().updateProducto(mProducto);
                if (isUpdated) {
                    Toast.makeText(CrearEditProductoActivity.this, mProducto.getNomProducto() + " ha sido actualizdo", Toast.LENGTH_LONG).show();
                    finish();
                } else
                    new AlertDialog.Builder(CrearEditProductoActivity.this).setTitle(R.string.app_name).setMessage("No se pudo actualizar en la base de datos").setNegativeButton("Aceptar", null).show();
            } else {
                boolean isInserted = new DAProducto().insertProducto(mProducto);
                if (isInserted) {
                    Toast.makeText(CrearEditProductoActivity.this, mProducto.getNomProducto() + " ha sido registrado", Toast.LENGTH_LONG).show();
                    finish();
                } else
                    new AlertDialog.Builder(CrearEditProductoActivity.this).setTitle(R.string.app_name).setMessage("No se pudo regristrar en la base de datos").setNegativeButton("Aceptar", null).show();
            }


            //Intent intent = new Intent(CrearEditProductoActivity.this,DetalleProductoActivity.class);
            //intent.putExtra(DetalleProductoActivity.ARG_DETALLE_PRODUCTO,mProducto);
            //startActivity(intent);
            //finish();

            Intent intent = new Intent(CrearEditProductoActivity.this, ListaProductoActivity.class);
            startActivity(intent);
            finish();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el menú que va a aparecer en el Toolbar
        getMenuInflater().inflate(R.menu.menu_producto_crear, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Capturamos el click de cada item del menu
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.mpGuardar) {
            guardarProducto();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
