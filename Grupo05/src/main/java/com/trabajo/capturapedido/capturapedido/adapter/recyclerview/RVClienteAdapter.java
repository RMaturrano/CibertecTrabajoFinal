package com.trabajo.capturapedido.capturapedido.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trabajo.capturapedido.capturapedido.R;
import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.interfaces.IRVClienteAdapter;
import com.trabajo.capturapedido.capturapedido.entities.BECliente;

import java.util.ArrayList;

/**
 * Created by cechenique on 18/05/2016.
 */
public class RVClienteAdapter extends RecyclerView.Adapter<RVClienteAdapter.RVClienteAdapterViewHolder> {

    private ArrayList<BECliente> mLstCliente;
    private IRVClienteAdapter mIRVClienteAdapter;

    public RVClienteAdapter(IRVClienteAdapter mIRVClienteAdapter) {
        this.mIRVClienteAdapter = mIRVClienteAdapter;
        mLstCliente = new ArrayList<>();
    }

    public void add(BECliente cliente) {
        mLstCliente.add(cliente);
        notifyItemInserted(mLstCliente.size() - 1);
    }

    public void addAll(ArrayList<BECliente> lstCliente) {
        int position = mLstCliente.size();
        mLstCliente.addAll(lstCliente);
        notifyItemRangeInserted(position, lstCliente.size());
    }

    public void clearAndAddAll(ArrayList<BECliente> lstCliente) {
        mLstCliente.clear();
        mLstCliente.addAll(lstCliente);
        notifyDataSetChanged();
    }

    @Override
    public RVClienteAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVClienteAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RVClienteAdapterViewHolder holder, int position) {
        if (position == 0) {
            ((RecyclerView.LayoutParams) holder.itemView.getLayoutParams()).topMargin = holder.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.heigth_toolbar);
        } else ((RecyclerView.LayoutParams) holder.itemView.getLayoutParams()).topMargin = 0;

        BECliente cliente = mLstCliente.get(position);

        holder.tvIdCliente.setText(String.valueOf(cliente.getIdCliente()));
        holder.tvNomCliente.setText(cliente.getNomCliente() + " " + cliente.getApellCliente());
        holder.tvDirCliente.setText(String.valueOf(cliente.getDireccCliente()));
        holder.tvTelfCliente.setText(String.valueOf(cliente.getTelfCliente()));

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(itemViewOnClickListener);

        holder.imgMapa.setOnClickListener(holderOnClickListener);
        holder.imgMapa.setTag(position);
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            mIRVClienteAdapter.onSelectItem(mLstCliente.get(position));
            //mIRVClienteAdapter.onItemClick(mLstCliente.get(position));
        }
    };

    View.OnClickListener holderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.imgMapa)
                mIRVClienteAdapter.onItemMapClick(mLstCliente.get((Integer) v.getTag()));
        }
    };

    @Override
    public int getItemCount() {
        return mLstCliente.size();
    }

    class RVClienteAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView tvIdCliente, tvNomCliente, tvDirCliente, tvTelfCliente;
        ImageView imgMapa;

        public RVClienteAdapterViewHolder(View itemView) {
            super(itemView);
            tvIdCliente = (TextView) itemView.findViewById(R.id.tvIdCliente);
            tvNomCliente = (TextView) itemView.findViewById(R.id.tvNomCliente);
            tvDirCliente = (TextView) itemView.findViewById(R.id.tvDirCliente);
            tvTelfCliente = (TextView) itemView.findViewById(R.id.tvTelfCliente);
            imgMapa = (ImageView) itemView.findViewById(R.id.imgMapa);
        }
    }
}

