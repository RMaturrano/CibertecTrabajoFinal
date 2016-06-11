package com.cibertec.capturapedido.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cibertec.capturapedido.R;
import com.cibertec.capturapedido.adapter.recyclerview.interfaces.IRVPedidoAdapter;
import com.cibertec.capturapedido.entities.Pedido;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jorge on 22/05/2016.
 */
public class RVPedidoAdapter extends RecyclerView.Adapter<RVPedidoAdapter.RVPedidoAdapterViewHolder> {
    private IRVPedidoAdapter mIRVPedidoAdapter;
    private ArrayList<Pedido> mLstPedidos;

    public RVPedidoAdapter(IRVPedidoAdapter mIRVPedidoAdapter) {
        this.mIRVPedidoAdapter = mIRVPedidoAdapter;
        this.mLstPedidos = new ArrayList<>();
    }

    @Override
    public RVPedidoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVPedidoAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false));
    }

    @Override
    public void onBindViewHolder(RVPedidoAdapterViewHolder holder, int position) {
        Pedido pedido = mLstPedidos.get(position);
        holder.itemView.setTag(position);
        holder.tvItemPedidoCliente.setText(pedido.getCliente());
        //holder.tvItemPedidoCantProductos.setText(String.valueOf(pedido.getDetalle().size()) + " productos");
        holder.tvItemPedidoCantProductos.setText(String.valueOf(pedido.getCant_productos()) + " productos");
        holder.tvItemPedidoTotal.setText(String.format(Locale.ENGLISH , "%1$,.2f", pedido.getTotal()));
        holder.itemView.setOnClickListener(itemClickListener);
    }

    View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mIRVPedidoAdapter.onSelectItem(mLstPedidos.get((Integer) v.getTag()));
        }
    };

    @Override
    public int getItemCount() {
        return mLstPedidos.size();
    }

    //Metodos de adaptador
    public void addAll(ArrayList<Pedido> lista) {
        mLstPedidos.clear();
        mLstPedidos.addAll(lista);
        notifyDataSetChanged();
    }

    class RVPedidoAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemPedidoCliente, tvItemPedidoCantProductos, tvItemPedidoTotal;

        public RVPedidoAdapterViewHolder(View itemView) {
            super(itemView);
            tvItemPedidoCliente = (TextView) itemView.findViewById(R.id.tvItemPedidoCliente);
            tvItemPedidoCantProductos = (TextView) itemView.findViewById(R.id.tvItemPedidoCantProductos);
            tvItemPedidoTotal = (TextView) itemView.findViewById(R.id.tvItemPedidoTotal);
        }
    }
}
