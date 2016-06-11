package com.trabajo.capturapedido.capturapedido;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.RVAdapterProductoFirst;
import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.interfaces.IRVProductoAdapter;
import com.trabajo.capturapedido.capturapedido.datos.DAProducto;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseHelper;
import com.trabajo.capturapedido.capturapedido.datos.DataBaseSingleton;
import com.trabajo.capturapedido.capturapedido.entities.BEProducto;

import java.io.IOException;

/**
 * Created by Alejandra on 01/06/2016.
 */
public class ListaProductoActivity extends AppCompatActivity implements IRVProductoAdapter {

    private RecyclerView rvFirstActivity;
    private RVAdapterProductoFirst mRVAdapterProductoFirst ;
    private Toolbar tbFirstProductoActivity;

    SearchView search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_producto_activity);

        rvFirstActivity = (RecyclerView) findViewById(R.id.rvFirstProductoActivity);
        tbFirstProductoActivity = (Toolbar) findViewById(R.id.tbFirstProductoActivity);
        setSupportActionBar(tbFirstProductoActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Productos");
        tbFirstProductoActivity.setTitleTextColor(0xFFFFFFFF);

        rvFirstActivity.setLayoutManager(new LinearLayoutManager(ListaProductoActivity.this));

        mRVAdapterProductoFirst = new RVAdapterProductoFirst(ListaProductoActivity.this);
        rvFirstActivity.setHasFixedSize(true);
        rvFirstActivity.setAdapter(mRVAdapterProductoFirst);

        DataBaseHelper dbHelper = new DataBaseHelper(ListaProductoActivity.this);
        try {
            dbHelper.createDataBase();
            new DataBaseSingleton(ListaProductoActivity.this);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mRVAdapterProductoFirst.clearAndAddAll(new DAProducto().listaProducto());
    }



    @Override
    public void onSelectItem(BEProducto producto) {
        Intent intent = new Intent(ListaProductoActivity.this, DetalleProductoActivity.class);
        intent.putExtra(DetalleProductoActivity.ARG_DETALLE_PRODUCTO, producto);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_producto,menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        search = (SearchView) searchItem.getActionView();
        search.setQueryHint("Buscar....");

        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                Toast.makeText(getBaseContext(), String.valueOf(hasFocus), Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
               // Toast.makeText(getBaseContext(), query, Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mRVAdapterProductoFirst.clearAndAddAll(new DAProducto().FiltraProducto(newText));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }else if (item.getItemId() == R.id.menuCrearProducto){
            Intent intent = new Intent(ListaProductoActivity.this, CrearEditProductoActivity.class);
            startActivity(intent);
        }

        return true;
    }


}
