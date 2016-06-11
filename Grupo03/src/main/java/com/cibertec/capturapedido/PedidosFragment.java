package com.cibertec.capturapedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cibertec.capturapedido.adapter.recyclerview.RVPedidoAdapter;
import com.cibertec.capturapedido.adapter.recyclerview.interfaces.IRVPedidoAdapter;
import com.cibertec.capturapedido.dao.PedidoDAO;
import com.cibertec.capturapedido.entities.Pedido;
import com.cibertec.capturapedido.entities.PedidoDetalle;

import java.util.ArrayList;

/**
 * Created by jtorres on 20/05/2016.
 */
public class PedidosFragment extends Fragment implements IRVPedidoAdapter {
    private RecyclerView rvMainPedidos;
    private RVPedidoAdapter mRVPedidoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.pedidos_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getActivity().setTitle(R.string.app_title_tollbar_pedidos);

        rvMainPedidos = (RecyclerView) view.findViewById(R.id.rvMainPedidos);
        rvMainPedidos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRVPedidoAdapter = new RVPedidoAdapter(this);
        rvMainPedidos.setAdapter(mRVPedidoAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mRVPedidoAdapter.addAll(new PedidoDAO().listPedido());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pedido_lista, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.ab_PedidoNuevo){
            Intent intent = new Intent(PedidosFragment.this.getActivity(), PedidoNuevoActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onSelectItem(Pedido pedido) {
        Intent intent = new Intent(this.getActivity(), PedidoDetalleActivity.class);
        intent.putExtra(PedidoDetalleActivity.ARG_PEDIDO, pedido.getPedidoId());
        startActivity(intent);
    }
}
