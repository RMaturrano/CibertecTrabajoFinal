package com.cibertec.capturapedido.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cibertec.capturapedido.R;
import com.cibertec.capturapedido.entities.Cliente;

import java.util.List;

/**
 * Created by Jorge on 27/05/2016.
 */
public class SPClienteAdapter extends ArrayAdapter<Cliente> {

    public SPClienteAdapter(Context context, List<Cliente> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sp_cliente_selected, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.tvSpinnerClienteNombreSelect);

        Cliente cliente = getItem(position);
        tv.setText(cliente.getEmpresa());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sp_cliente_dropdown, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.tvSpinnerClienteNombre);

        Cliente cliente = getItem(position);
        tv.setText(cliente.getEmpresa());

        return convertView;
    }
}
