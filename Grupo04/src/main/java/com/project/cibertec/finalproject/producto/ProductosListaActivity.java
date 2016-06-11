package com.project.cibertec.finalproject.producto;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.project.cibertec.finalproject.R;
import com.project.cibertec.finalproject.dao.ProductoDAO;
import com.project.cibertec.finalproject.entities.Producto;
import com.project.cibertec.finalproject.producto.adapter.recyclerview.RVAdapterListaProducto;
import com.project.cibertec.finalproject.producto.adapter.recyclerview.listeners.IRVAdapterListaProductoListener;

import java.io.Serializable;
import java.util.ArrayList;


public class ProductosListaActivity extends AppCompatActivity implements IRVAdapterListaProductoListener {

    private DrawerLayout dlMenu;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView nvMenu;
    private RecyclerView rvProductosListado;
    private RVAdapterListaProducto mRVAdapterListaProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productos_lista_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProdList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.tituloProductoLista);

        //Inflo el recycler y configuramos el Adapter
        rvProductosListado = (RecyclerView) findViewById(R.id.rvProductosListado);
        rvProductosListado.setLayoutManager(new LinearLayoutManager(ProductosListaActivity.this));
        rvProductosListado.setHasFixedSize(true);
        mRVAdapterListaProducto = new RVAdapterListaProducto(ProductosListaActivity.this);
        rvProductosListado.setAdapter(mRVAdapterListaProducto);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRVAdapterListaProducto.clearAndAddAll(new ProductoDAO().listProducto());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_search, menu);

        final MenuItem item = menu.findItem(R.id.abFirstSearch);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(itemOnQueryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    SearchView.OnQueryTextListener itemOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            final ArrayList<Producto> filteredProductList = filter(new ProductoDAO().listProducto(), query);
            mRVAdapterListaProducto.animateTo(filteredProductList);
            rvProductosListado.scrollToPosition(0);
            return true;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.abFirstAdd:
//                Intent nuevoProd = new Intent(ProductosListaActivity.this, ProductosNuevoActivity.class);
//                startActivityForResult(nuevoProd, 99);
                startActivityForResult(new Intent(ProductosListaActivity.this, ProductosNuevoActivity.class), 998);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mActionBarDrawerToggle.onOptionsItemSelected(item))
//            return true;
//
//        if (item.getItemId() == R.id.abFirstAdd) {
//            startActivityForResult(new Intent(ProductosListaActivity.this, ProductosNuevoActivity.class), 999);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private ArrayList<Producto> filter(ArrayList<Producto> products, String query) {
        query = query.toLowerCase();

        final ArrayList<Producto> filteredProductList = new ArrayList<>();
        for (Producto producto : products) {
            final String nombre = producto.getNombreProducto().toLowerCase();
            final String descripcion = producto.getDescripcionProducto().toLowerCase();
            if (nombre.contains(query) || descripcion.contains(query)) {
                filteredProductList.add(producto);
            }
        }
        return filteredProductList;
    }

    @Override
    public void onItemClick(Producto producto) {
        Intent intent = new Intent(ProductosListaActivity.this, ProductosNuevoActivity.class);
        intent.putExtra("producto", producto);
        startActivity(intent);
    }


}
