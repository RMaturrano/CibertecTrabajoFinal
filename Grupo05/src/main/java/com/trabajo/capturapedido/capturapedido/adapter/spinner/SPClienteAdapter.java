package com.trabajo.capturapedido.capturapedido.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.trabajo.capturapedido.capturapedido.R;
import com.trabajo.capturapedido.capturapedido.entities.BECliente;

import java.util.List;

/**
 * Created by cechenique on 24/05/2016.
 */
public class SPClienteAdapter extends ArrayAdapter<BECliente>{

    public SPClienteAdapter(Context context, List<BECliente> objects){
        super(context,0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cliente_selected,parent,false);

        //TextView tvItemIdCliente = (TextView) convertView.findViewById(R.id.tvSpIdCliente)
        TextView tvItemNomCliente = (TextView) convertView.findViewById(R.id.tvSpNomCliente);

        BECliente cliente = getItem(position);
        tvItemNomCliente.setText(cliente.getNomCliente()+ " "+ cliente.getApellCliente());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cliente_dropdown,parent,false);

        TextView tvItemNomCliente = (TextView) convertView.findViewById(R.id.tvSpNomCliente);

        BECliente cliente = getItem(position);
        tvItemNomCliente.setText(cliente.getNomCliente()+ " "+ cliente.getApellCliente());

        return convertView;
    }
}
