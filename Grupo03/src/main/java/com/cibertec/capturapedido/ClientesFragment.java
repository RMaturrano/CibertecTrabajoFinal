package com.cibertec.capturapedido;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cibertec.capturapedido.adapter.recyclerview.RVClienteAdapter;
import com.cibertec.capturapedido.adapter.recyclerview.interfaces.IRVClienteAdapter;
import com.cibertec.capturapedido.dao.ClienteDAO;
import com.cibertec.capturapedido.entities.Cliente;

/**
 * Created by jtorres on 20/05/2016.
 */
public class ClientesFragment extends Fragment implements IRVClienteAdapter {
    private RVClienteAdapter mRVClienteAdapter;
    private RecyclerView rvMainClientes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.clientes_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mRVClienteAdapter.clearAndAddAll(new ClienteDAO().listCliente());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.getActivity().setTitle(R.string.app_title_tollbar_cliente);

        rvMainClientes = (RecyclerView) view.findViewById(R.id.rvMainClientes);
        rvMainClientes.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mRVClienteAdapter = new RVClienteAdapter(ClientesFragment.this);
        rvMainClientes.setAdapter(mRVClienteAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cliente_nuevo, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onSelectItem(Cliente cliente) {
        Intent intent = new Intent(this.getActivity(), ClienteDetalleActivity.class);
        intent.putExtra(ClienteDetalleActivity.ARG_CLIENTE, cliente);
        startActivity(intent);
    }

    @Override
    public void onPositionMap(Cliente cliente) {
        Intent intent = new Intent(this.getActivity(), ClientePosicionActivity.class);
        intent.putExtra(ClientePosicionActivity.ARG_CLIENTE, cliente);
        startActivity(intent);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            if(requestCode == 99){
                mRVClienteAdapter.add(data.<Cliente>getParcelableExtra(ClienteGrabarActivity.ARG_CLIENTE));
            }
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if (item.getItemId() == R.id.ab_nuevo) {
           Intent intent = new Intent(this.getActivity(), ClienteGrabarActivity.class);
           startActivity(intent);
           //startActivityForResult(intent, 99);
        }
        return true;
    }
}
