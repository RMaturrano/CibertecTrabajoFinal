package com.cibertec.capturapedido.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cibertec.capturapedido.R;
import com.cibertec.capturapedido.adapter.recyclerview.interfaces.IRVPedidoAdapter;
import com.cibertec.capturapedido.adapter.recyclerview.interfaces.IRVPedidoDetalleAdapter;
import com.cibertec.capturapedido.entities.PedidoDetalle;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jorge on 22/05/2016.
 */
public class RVPedidoDetalleAdapter extends RecyclerView.Adapter<RVPedidoDetalleAdapter.RVPedidoDetalleAdapterViewHolder> {
    private IRVPedidoDetalleAdapter mIRVPedidoDetalleAdapter;
    private ArrayList<PedidoDetalle> mLstPedidoDetalle;

    public RVPedidoDetalleAdapter(IRVPedidoDetalleAdapter mIRVPedidoDetalleAdapter) {
        this.mIRVPedidoDetalleAdapter = mIRVPedidoDetalleAdapter;
        this.mLstPedidoDetalle = new ArrayList<>();
    }

    @Override
    public RVPedidoDetalleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVPedidoDetalleAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_detalle, parent, false));
    }

    @Override
    public void onBindViewHolder(RVPedidoDetalleAdapterViewHolder holder, int position) {
        PedidoDetalle detalle = mLstPedidoDetalle.get(position);
        holder.itemView.setTag(position);
        holder.tvItemPedidoDetalleProductoNombre.setText(detalle.getProductoNombre());
        holder.tvItemPedidoDetalleProductoDescripcion.setText(detalle.getProductoDescripcion());
        holder.tvItemPedidoDetalleCantidad.setText(String.valueOf(detalle.getCantidad()));
        holder.tvItemPedidoDetalleProductoPrecio.setText(String.format(Locale.ENGLISH , "%1$,.2f",detalle.getPrecio()));
        holder.itemView.setOnLongClickListener(itemViewOnLongClickListener);
    }

    View.OnLongClickListener itemViewOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag();
            mIRVPedidoDetalleAdapter.onItemSelect(mLstPedidoDetalle.get(position));
            return false;
        }
    };

    public int getItemCount() {
        return mLstPedidoDetalle.size();
    }

    public void delete(int position){
        mLstPedidoDetalle.remove(position);
        //notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void editCantidad(PedidoDetalle pedidoDetalle){
        int position = searchIndex(pedidoDetalle);
        mLstPedidoDetalle.get(position).setCantidad(pedidoDetalle.getCantidad());
        notifyItemChanged(position);
    }

    public PedidoDetalle getItem(int position){
        return mLstPedidoDetalle.get(position);
    }

    public void clearAndAddAll(ArrayList<PedidoDetalle> lista) {
        mLstPedidoDetalle.clear();
        mLstPedidoDetalle.addAll(lista);
        notifyDataSetChanged();
    }

    public int searchIndex(PedidoDetalle pedido) {
        int index = -1;
        for (int i = 0; i < mLstPedidoDetalle.size(); i++) {
            if (mLstPedidoDetalle.get(i).getProductoId() == pedido.getProductoId())
                index = i;
        }
        return index;
    }

    public void add(PedidoDetalle pedido) {
        int index_item = searchIndex(pedido);

        if (index_item == -1) {
            mLstPedidoDetalle.add(pedido);
            notifyItemInserted(mLstPedidoDetalle.size() - 1);
        } else {
            PedidoDetalle pedidoDetalle = mLstPedidoDetalle.get(index_item);
            pedidoDetalle.setCantidad(pedidoDetalle.getCantidad() + pedido.getCantidad());
            mLstPedidoDetalle.set(index_item, pedidoDetalle);
            notifyItemChanged(index_item);
        }
    }

    public ArrayList<PedidoDetalle> getPedidoDetalle() {
        return mLstPedidoDetalle;
    }

    public double getTotal() {
        double total = 0;

        for (PedidoDetalle detalle : mLstPedidoDetalle) {
            total += detalle.getCantidad() * detalle.getPrecio();
        }

        return total;
    }

    class RVPedidoDetalleAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemPedidoDetalleProductoNombre, tvItemPedidoDetalleProductoDescripcion,
                tvItemPedidoDetalleCantidad, tvItemPedidoDetalleProductoPrecio;

        public RVPedidoDetalleAdapterViewHolder(View itemView) {
            super(itemView);
            tvItemPedidoDetalleProductoNombre = (TextView) itemView.findViewById(R.id.tvItemPedidoDetalleProductoNombre);
            tvItemPedidoDetalleProductoDescripcion = (TextView) itemView.findViewById(R.id.tvItemPedidoDetalleProductoDescripcion);
            tvItemPedidoDetalleCantidad = (TextView) itemView.findViewById(R.id.tvItemPedidoDetalleCantidad);
            tvItemPedidoDetalleProductoPrecio = (TextView) itemView.findViewById(R.id.tvItemPedidoDetalleProductoPrecio);
        }
    }
}
