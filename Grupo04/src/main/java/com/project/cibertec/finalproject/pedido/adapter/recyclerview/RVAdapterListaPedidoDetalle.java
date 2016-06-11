package com.project.cibertec.finalproject.pedido.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.cibertec.finalproject.R;
import com.project.cibertec.finalproject.entities.PedidoDetalle;
import com.project.cibertec.finalproject.entities.Producto;
import com.project.cibertec.finalproject.producto.adapter.recyclerview.listeners.IRVAdapterListaProductoListener;

import java.util.ArrayList;

/**
 * Created by PC on 28/05/2016.
 */
public class RVAdapterListaPedidoDetalle extends RecyclerView.Adapter<RVAdapterListaPedidoDetalle.RVAdapterListaPedidoDetalleViewHolder> {

    private ArrayList<PedidoDetalle> mLstPedidoDetalle;

    public void add(PedidoDetalle pedidoDetalle) {
        mLstPedidoDetalle.add(pedidoDetalle);
        notifyItemInserted(mLstPedidoDetalle.size() - 1);
    }

    public ArrayList<PedidoDetalle> getAll(){
        return mLstPedidoDetalle;
    }

    public void clearAndAddAll(ArrayList<PedidoDetalle> lstPedidoDetalle) {
        mLstPedidoDetalle.clear();
        mLstPedidoDetalle.addAll(lstPedidoDetalle);
        notifyDataSetChanged();
    }

    @Override
    public RVAdapterListaPedidoDetalleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVAdapterListaPedidoDetalleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_lista_pedidos_detalle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RVAdapterListaPedidoDetalleViewHolder holder, int position) {
        PedidoDetalle pedidoDetalle = mLstPedidoDetalle.get(position);
        holder.tvPedidoDetalleProNombre.setText(pedidoDetalle.getNombreProducto());
        holder.tvPedidoDetalleProCantidad.setText(String.valueOf(pedidoDetalle.getCantidad()));
        holder.tvPedidoDetalleProDescripcion.setText(pedidoDetalle.getDescripcionProducto());
        holder.tvPedidoDetalleProMonto.setText("S/ " + String.valueOf(pedidoDetalle.getTotalLinea()));
    }

    @Override
    public int getItemCount() {
        return mLstPedidoDetalle.size();
    }

    static class RVAdapterListaPedidoDetalleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPedidoDetalleProNombre, tvPedidoDetalleProCantidad, tvPedidoDetalleProDescripcion, tvPedidoDetalleProMonto;

        public RVAdapterListaPedidoDetalleViewHolder(View itemView) {
            super(itemView);

            tvPedidoDetalleProNombre = (TextView) itemView.findViewById(R.id.tvPedidoDetalleProNombre);
            tvPedidoDetalleProCantidad = (TextView) itemView.findViewById(R.id.tvPedidoDetalleProCantidad);
            tvPedidoDetalleProDescripcion = (TextView) itemView.findViewById(R.id.tvPedidoDetalleProDescripcion);
            tvPedidoDetalleProMonto = (TextView) itemView.findViewById(R.id.tvPedidoDetalleProMonto);
        }
    }

    public RVAdapterListaPedidoDetalle() {
        mLstPedidoDetalle = new ArrayList<>();
    }


}
