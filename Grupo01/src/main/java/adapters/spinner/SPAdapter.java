package adapters.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import entities.Cliente;
import trabajofinalcb.javecarlos.pe.trabajofinalcb.R;


public class SPAdapter extends ArrayAdapter<Cliente> {

    private List<Cliente> lista;

    public SPAdapter(Context context, List<Cliente> objects) {
        super(context, 0, objects);
        lista = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.sp_item_selected, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.tvItemCliente);

        Cliente cliente = getItem(position);
        tv.setText(cliente.getEmpresaNombre());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sp_item_dropdown, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.tvItemCliente);
        Cliente cliente = getItem(position);
        tv.setText(cliente.getEmpresaNombre());

        return convertView;
    }

    public int getItemIndexById(int id) {
        for (Cliente item : this.lista) {
            if(item.getEmpresaId() == id){
                return this.lista.indexOf(item);
            }
        }
        return 0;
    }
}
