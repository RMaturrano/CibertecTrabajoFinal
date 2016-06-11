package com.trabajo.capturapedido.capturapedido.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trabajo.capturapedido.capturapedido.R;
import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.interfaces.IRVPedidoAdapter;
import com.trabajo.capturapedido.capturapedido.entities.BEPedido;

import java.util.ArrayList;

/**
 * Created by cechenique on 25/05/2016.
 */
public class RVPedidoAdapter extends RecyclerView.Adapter<RVPedidoAdapter.RVPedidoAdapterViewHolder> {

    private ArrayList<BEPedido> mLstPedido;
    private IRVPedidoAdapter mIRVPedidoAdapter;

    public RVPedidoAdapter( IRVPedidoAdapter mIRVPedidoAdapter){
        this.mIRVPedidoAdapter = mIRVPedidoAdapter;
        mLstPedido = new ArrayList<>();
    }

    public void add(BEPedido pedido){
        mLstPedido.add(pedido);
        notifyItemInserted(mLstPedido.size()-1);
    }

    public void addAll(ArrayList<BEPedido> lstPedido) {
        int position = mLstPedido.size();
        mLstPedido.addAll(lstPedido);
        notifyItemRangeInserted(position, lstPedido.size());
    }

    public void clearAndAddAll(ArrayList<BEPedido> lstPedido) {
        mLstPedido.clear();
        mLstPedido.addAll(lstPedido);
        notifyDataSetChanged();
    }

    @Override
    public RVPedidoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVPedidoAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pedido_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return mLstPedido.size();
    }

    class RVPedidoAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView tvIdPedido, tvNomCliente, tvFecha, tvTotal, tvCantTot;

        public RVPedidoAdapterViewHolder(View itemView) {
            super(itemView);
            tvIdPedido = (TextView) itemView.findViewById(R.id.tvIdPedido);
            tvNomCliente = (TextView) itemView.findViewById(R.id.tvNomCliente);
            tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
            tvCantTot = (TextView) itemView.findViewById(R.id.tvCantTot);
            tvTotal = (TextView) itemView.findViewById(R.id.tvTotal);
        }
    }

    @Override
    public void onBindViewHolder(RVPedidoAdapterViewHolder holder, int position) {

        if (position == 0) {
            ((RecyclerView.LayoutParams) holder.itemView.getLayoutParams()).topMargin = holder.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.heigth_toolbar);
        } else ((RecyclerView.LayoutParams) holder.itemView.getLayoutParams()).topMargin = 0;

        BEPedido pedido = mLstPedido.get(position);
        //String codigo = "00000000" + String.valueOf(pedido.getIdPedido());
        //holder.tvIdPedido.setText(codigo.substring(String.valueOf(pedido.getIdPedido()).length()));
        holder.tvIdPedido.setText(String.valueOf(pedido.getIdPedido()));
        holder.tvNomCliente.setText(pedido.getNomClientePedido());
        holder.tvFecha.setText(pedido.getFechaPedido());
        holder.tvCantTot.setText(String.valueOf(pedido.getCanttotPedido())+ " Productos");
        holder.tvTotal.setText(String.valueOf(pedido.getTotalPedido()));

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(itemViewOnClickListener);
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mIRVPedidoAdapter != null) {
                int position = (int) v.getTag();
                // mIRVPedidoAdapter.onSelectItem(mLstPedido.get(position));
                mIRVPedidoAdapter.onItemClick(mLstPedido.get(position));
            }
        }
    };
}
