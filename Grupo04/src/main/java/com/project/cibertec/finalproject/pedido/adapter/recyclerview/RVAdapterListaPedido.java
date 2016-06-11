package com.project.cibertec.finalproject.pedido.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.cibertec.finalproject.R;

import com.project.cibertec.finalproject.entities.Pedido;
import com.project.cibertec.finalproject.pedido.adapter.recyclerview.listeners.IRVAdapterListaPedidoListener;

import java.util.ArrayList;


public class RVAdapterListaPedido extends RecyclerView.Adapter<RVAdapterListaPedido.RVAdapterListaPedidoViewHolder> {

    private ArrayList<Pedido> mLstPedido;
    private ArrayList<Pedido> mLstDetailPedido;

    private IRVAdapterListaPedidoListener mIRVAdapterListaPedidoListener;

    public RVAdapterListaPedido(IRVAdapterListaPedidoListener mIRVAdapterListaPedidoListener ) {
        mLstPedido = new ArrayList<>();
        mLstDetailPedido = new ArrayList<>();
        this.mIRVAdapterListaPedidoListener = mIRVAdapterListaPedidoListener;
    }

    public void add(Pedido pedido) {
        mLstPedido.add(pedido);
        notifyItemInserted(mLstPedido.size() - 1);
    }

    public void addAll(ArrayList<Pedido> pedidos) {
        mLstPedido.addAll(pedidos);
    }

    @Override
    public RVAdapterListaPedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVAdapterListaPedidoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_lista_pedidos_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RVAdapterListaPedidoViewHolder holder, int position) {
        Pedido pedido = mLstPedido.get(position);
        holder.tvPedidoListItemNombreCli.setText(pedido.getEmpresa());
        String productConcat =" productos";
        if(pedido.getCantidadProductos()== 1) productConcat =" producto";
        holder.tvPedidoListItemCantProductos.setText(String.valueOf(pedido.getCantidadProductos()).concat(productConcat));
        holder.tvPedidoListItemTotal.setText("S/ "+String.valueOf(pedido.getTotalPedido()));

        holder.itemView.setOnClickListener(itemViewOnClickListener);
        holder.itemView.setTag(position);

    }


    //Evento click en la fila
    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mIRVAdapterListaPedidoListener.onItemClick(mLstPedido.get((Integer) v.getTag()));
        }
    };
    @Override
    public int getItemCount() {
        return mLstPedido.size();
    }

    static class RVAdapterListaPedidoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPedidoListItemNombreCli, tvPedidoListItemCantProductos, tvPedidoListItemTotal;

        public RVAdapterListaPedidoViewHolder(View itemView) {
            super(itemView);

            tvPedidoListItemNombreCli = (TextView) itemView.findViewById(R.id.tvPedidoListItemNombreCli);
            tvPedidoListItemCantProductos = (TextView) itemView.findViewById(R.id.tvPedidoListItemCantProductos);
            tvPedidoListItemTotal = (TextView) itemView.findViewById(R.id.tvPedidoListItemTotal);
        }
    }

    public void clearAndAddAll(ArrayList<Pedido> lstPedido) {
        mLstPedido.clear();
        mLstPedido.addAll(lstPedido);
        notifyDataSetChanged();
    }
    public void clearAndAddAllDetail (ArrayList<Pedido> lstPedido) {
        mLstDetailPedido.clear();
        mLstDetailPedido.addAll(lstPedido);
        notifyDataSetChanged();
    }


}
