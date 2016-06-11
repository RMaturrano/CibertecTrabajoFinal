package adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import adapters.recyclerview.interfaces.IRVClienteAdapter;
import entities.Cliente;
import trabajofinalcb.javecarlos.pe.trabajofinalcb.R;

/**
 * Created by carlosarmando on 07/05/2016.
 */
public class RVClienteAdapter extends RecyclerView.Adapter<RVClienteAdapter.RVClienteViewHolder> {
    private ArrayList<Cliente> listaCliente;
    private IRVClienteAdapter _mIRVClienteAdapter;

    public RVClienteAdapter(IRVClienteAdapter mIRVClienteAdapter) {
        listaCliente = new ArrayList<>();
        _mIRVClienteAdapter = mIRVClienteAdapter;
    }

    public void add(Cliente cliente) {
        listaCliente.add(cliente);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Cliente> lstCliente) {
        int position = listaCliente.size();
        listaCliente.addAll(lstCliente);
        notifyItemRangeInserted(position, lstCliente.size());
    }

    public void clearAndAddAll(ArrayList<Cliente> lstCliente) {
        listaCliente.clear();
        listaCliente.addAll(lstCliente);
        notifyDataSetChanged();
    }

    @Override
    public RVClienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVClienteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cliente_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(RVClienteViewHolder holder, int position) {
        Cliente cliente = listaCliente.get(position);

        holder.tvNombreClienteRV.setText(cliente.getEmpresaNombre());
        holder.tvDireccionClienteRV.setText(cliente.getEmpresaDireccion());
        holder.tvTelefonoClienteRV.setText(String.valueOf(cliente.getContactoTelefono()));

        holder.itemView.setOnClickListener(itemViewOnClickListener);
        holder.itemView.setTag(position);

        holder.imgClienteMapa.setOnClickListener(imgClienteMapaOnClickListener);
        holder.imgClienteMapa.setTag(position);
    }

    View.OnClickListener imgClienteMapaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Cliente cliente = listaCliente.get((Integer) v.getTag());
            _mIRVClienteAdapter.onItemMapaClick(cliente);
        }
    };

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Cliente cliente = listaCliente.get((Integer) v.getTag());
            _mIRVClienteAdapter.onItemClick(cliente);
        }
    };

    public void setFilter(List<Cliente> mCliente){
        listaCliente = new ArrayList<>();
        listaCliente.addAll(mCliente);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listaCliente.size();
    }
    static class RVClienteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreClienteRV, tvDireccionClienteRV, tvTelefonoClienteRV;
        ImageView imgClienteMapa;

        public RVClienteViewHolder(View itemView) {
            super(itemView);

            tvNombreClienteRV = (TextView) itemView.findViewById(R.id.tvNombreClienteRV);
            tvDireccionClienteRV = (TextView) itemView.findViewById(R.id.tvDireccionClienteRV);
            tvTelefonoClienteRV = (TextView) itemView.findViewById(R.id.tvTelefonoClienteRV);
            imgClienteMapa = (ImageView)itemView.findViewById(R.id.imgClienteMapa);
        }
    }

}
