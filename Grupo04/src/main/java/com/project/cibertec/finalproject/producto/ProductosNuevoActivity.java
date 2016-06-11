package com.project.cibertec.finalproject.producto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.project.cibertec.finalproject.R;
import com.project.cibertec.finalproject.dao.ProductoDAO;
import com.project.cibertec.finalproject.entities.Producto;


/**
 * Created by PC on 31/05/2016.
 */
public class ProductosNuevoActivity extends AppCompatActivity {

    private EditText edtProNuevoNombre, edtProNuevoDescripcion, edtProNuevoPreUnitario;
    private Producto mProducto;
    private boolean isUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productos_nuevo_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProdNuevo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.tituloProductoNuevo);

        edtProNuevoNombre = (EditText) findViewById(R.id.edtProNuevoNombre);
        edtProNuevoDescripcion = (EditText) findViewById(R.id.edtProNuevoDescripcion);
        edtProNuevoPreUnitario = (EditText) findViewById(R.id.edtProNuevoPreUnitario);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("producto")) {
            mProducto = (Producto) getIntent().getExtras().get("producto");
            isUpdate = true;
            setTitle(mProducto.getNombreProducto());
            fillData();
        } else {
            mProducto = null;
            isUpdate = false;
            setTitle(R.string.tituloProductoNuevo);
        }
    }

    private void fillData() {
        edtProNuevoNombre.setText(mProducto.getNombreProducto());
        edtProNuevoDescripcion.setText(mProducto.getDescripcionProducto());
        edtProNuevoPreUnitario.setText(String.valueOf(mProducto.getPrecioProducto()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.abSecondSave:
                save();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {

        boolean isOK = checkTexts();

        if (isOK) {
            if (mProducto == null)
                mProducto = new Producto();

            mProducto.setNombreProducto(edtProNuevoNombre.getText().toString().trim());
            mProducto.setDescripcionProducto(edtProNuevoDescripcion.getText().toString().trim());
            mProducto.setPrecioProducto(Double.parseDouble(edtProNuevoPreUnitario.getText().toString().trim()));

            if (isUpdate) {
                boolean isUpdated = new ProductoDAO().updateProducto(mProducto);
                if (isUpdated) {
                    Toast.makeText(ProductosNuevoActivity.this, mProducto.getNombreProducto() + " " + mProducto.getDescripcionProducto() + " ha sido actualizdo", Toast.LENGTH_LONG).show();
                    finish();
                } else
                    new AlertDialog.Builder(ProductosNuevoActivity.this).setTitle(R.string.app_name).setMessage("No se pudo actualizar en la base de datos").setNegativeButton("Aceptar", null).show();
            } else {
                boolean isInserted = new ProductoDAO().insertProducto(mProducto);
                if (isInserted) {
                    Toast.makeText(ProductosNuevoActivity.this, mProducto.getNombreProducto() + " " + mProducto.getDescripcionProducto() + " ha sido registrado", Toast.LENGTH_LONG).show();
                    finish();
                } else
                    new AlertDialog.Builder(ProductosNuevoActivity.this).setTitle(R.string.app_name).setMessage("No se pudo regristrar en la base de datos").setNegativeButton("Aceptar", null).show();
            }
        }

    }


    private boolean checkTexts() {

        if (TextUtils.isEmpty(edtProNuevoNombre.getText().toString().trim())) {
            edtProNuevoNombre.setError("Campo obligatorio");
            Snackbar.make(getCurrentFocus(),"Ingrese el nombre del producto",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtProNuevoDescripcion.getText().toString().trim())) {
            edtProNuevoDescripcion.setError("Campo obligatorio");
            Snackbar.make(getCurrentFocus(),"Ingrese la descripción del producto",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtProNuevoPreUnitario.getText().toString().trim())) {
            edtProNuevoPreUnitario.setError("Campo obligatorio");
            Snackbar.make(getCurrentFocus(),"Ingrese el precio del producto",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (Double.parseDouble(edtProNuevoPreUnitario.getText().toString().trim()) <= 0) {
            edtProNuevoPreUnitario.setError("Precio no válido");
            Snackbar.make(getCurrentFocus(),"El precio debe ser mayor a cero.",Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


}
