package com.project.cibertec.finalproject.pedido.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.cibertec.finalproject.entities.Cliente;
import com.project.cibertec.finalproject.entities.Pedido;

import java.util.ArrayList;


import com.project.cibertec.finalproject.R;
/**
 * Created by V825727 on 6/6/2016.
 */
public class SPAdapter extends ArrayAdapter<Cliente>{


    public SPAdapter (Context context){
        super (context,0,new ArrayList<Cliente>());
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.sp_lista_clientes_item, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.spClienteItem);
        Cliente cliente = getItem(position);
        textView.setText(cliente.getEmpresa().toUpperCase());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.sp_lista_clientes_item, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.spClienteItem);
        Cliente cliente = getItem(position);
        textView.setText(cliente.getEmpresa().toUpperCase());

        return view;
    }

}
