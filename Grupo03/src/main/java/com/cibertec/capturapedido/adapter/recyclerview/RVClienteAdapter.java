package com.cibertec.capturapedido.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cibertec.capturapedido.R;
import com.cibertec.capturapedido.adapter.recyclerview.interfaces.IRVClienteAdapter;
import com.cibertec.capturapedido.entities.Cliente;

import java.util.ArrayList;

/**
 * Created by bestrada on 10/05/2016.
 */
public class RVClienteAdapter extends RecyclerView.Adapter<RVClienteAdapter.RVFirstAdapterViewHolder> {

    private ArrayList<Cliente> mLstCliente;
    private IRVClienteAdapter mIRVClienteAdapter;

    public RVClienteAdapter(IRVClienteAdapter mIRVClienteAdapter) {
        this.mIRVClienteAdapter = mIRVClienteAdapter;
        mLstCliente = new ArrayList<>();
    }

    public void add(Cliente cliente) {
        mLstCliente.add(cliente);
        notifyItemInserted(mLstCliente.size() - 1);
    }

    public void addAll(ArrayList<Cliente> lstCliente) {
        int position = mLstCliente.size();
        mLstCliente.addAll(lstCliente);
        notifyItemRangeInserted(position, lstCliente.size());
    }

    public void clearAndAddAll(ArrayList<Cliente> lstCliente) {
        mLstCliente.clear();
        mLstCliente.addAll(lstCliente);
        notifyDataSetChanged();
    }

    @Override
    public RVFirstAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVFirstAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false));
    }

    @Override
    public void onBindViewHolder(RVFirstAdapterViewHolder holder, int position) {
        Cliente cliente = mLstCliente.get(position);
        //holder.itemView.setTag(position);
        holder.tvItemClienteNombre.setText(cliente.getEmpresa());
        holder.tvItemClienteDireccion.setText(cliente.getDireccion());
        holder.tvItemClienteTelefono.setText(cliente.getTelefono());
        holder.lyClienteDatos.setTag(position);
        holder.ivClientePosicion.setTag(position);
        holder.lyClienteDatos.setOnClickListener(itemViewOnClickListener);
        holder.ivClientePosicion.setOnClickListener(ivClientePosicionOnClickListener);
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            mIRVClienteAdapter.onSelectItem(mLstCliente.get(position));
        }
    };

    View.OnClickListener ivClientePosicionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            mIRVClienteAdapter.onPositionMap(mLstCliente.get(position));
        }
    };

    @Override
    public int getItemCount() {
        return mLstCliente.size();
    }

    public class RVFirstAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemClienteNombre, tvItemClienteDireccion, tvItemClienteTelefono;
        LinearLayout lyClienteDatos;
        ImageView ivClientePosicion;

        public RVFirstAdapterViewHolder(View itemView) {
            super(itemView);
            lyClienteDatos = (LinearLayout) itemView.findViewById(R.id.lyClienteDatos);
            ivClientePosicion = (ImageView) itemView.findViewById(R.id.ivClientePosicion);
            tvItemClienteNombre = (TextView) itemView.findViewById(R.id.tvItemClienteNombre);
            tvItemClienteDireccion = (TextView) itemView.findViewById(R.id.tvItemClienteDireccion);
            tvItemClienteTelefono = (TextView) itemView.findViewById(R.id.tvItemClienteTelefono);
        }
    }
}
