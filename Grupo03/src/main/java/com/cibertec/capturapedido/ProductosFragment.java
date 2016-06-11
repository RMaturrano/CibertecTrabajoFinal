package com.cibertec.capturapedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.cibertec.capturapedido.adapter.recyclerview.RVProductoAdapter;
import com.cibertec.capturapedido.adapter.recyclerview.interfaces.IRVProductoAdapter;
import com.cibertec.capturapedido.dao.ProductoDAO;
import com.cibertec.capturapedido.entities.Producto;

/**
 * Created by jtorres on 20/05/2016.
 */
public class ProductosFragment extends Fragment implements IRVProductoAdapter {
    private RVProductoAdapter mRVProductoAdapter;
    private RecyclerView rvMainProductos;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.productos_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mRVProductoAdapter.clearAndAddAll(new ProductoDAO().listProduto(""));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getActivity().setTitle(R.string.app_title_tollbar_producto);
        rvMainProductos = (RecyclerView) view.findViewById(R.id.rvMainProductos);
        rvMainProductos.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mRVProductoAdapter = new RVProductoAdapter(ProductosFragment.this);
        rvMainProductos.setAdapter(mRVProductoAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_producto_lista, menu);
        MenuItem item = menu.findItem(R.id.ab_ProductoBuscar);
        searchView = new SearchView(((MainActivity) this.getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(searchOnQueryTextListener);
    }

    SearchView.OnQueryTextListener searchOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mRVProductoAdapter.clearAndAddAll(new ProductoDAO().listProduto(query));
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                mRVProductoAdapter.clearAndAddAll(new ProductoDAO().listProduto(""));
            }
            return true;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ab_ProductoNuevo) {
            Intent intent = new Intent(this.getActivity(), ProductoGrabarActivity.class);
            startActivity(intent);
            //startActivityForResult(intent, 99);
        }
        return true;
    }

    @Override
    public void onSelectItem(Producto producto) {
        Intent intent = new Intent(this.getActivity(), ProductoDetalleActivity.class);
        intent.putExtra(ProductoDetalleActivity.ARG_PRODUCTO, producto);
        startActivity(intent);
    }
}
