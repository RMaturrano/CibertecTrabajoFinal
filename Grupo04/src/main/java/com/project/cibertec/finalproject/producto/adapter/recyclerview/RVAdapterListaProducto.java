package com.project.cibertec.finalproject.producto.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.cibertec.finalproject.R;
import com.project.cibertec.finalproject.entities.Producto;
import com.project.cibertec.finalproject.producto.adapter.recyclerview.listeners.IRVAdapterListaProductoListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 28/05/2016.
 */
public class RVAdapterListaProducto extends RecyclerView.Adapter<RVAdapterListaProducto.RVAdapterListaProductoViewHolder> {

    private ArrayList<Producto> mLstProducto;

    private IRVAdapterListaProductoListener mIRVAdapterListaProductoListener;

    public RVAdapterListaProducto(IRVAdapterListaProductoListener mIRVAdapterListaProductoListener) {
        mLstProducto = new ArrayList<>();
        this.mIRVAdapterListaProductoListener = mIRVAdapterListaProductoListener;
    }

    public void add(Producto producto) {
        mLstProducto.add(producto);
        notifyItemInserted(mLstProducto.size() - 1);
    }

    public void clearAndAddAll(ArrayList<Producto> lstProducto) {
        mLstProducto.clear();
        mLstProducto.addAll(lstProducto);
        notifyDataSetChanged();
    }

    @Override
    public RVAdapterListaProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVAdapterListaProductoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_lista_productos_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RVAdapterListaProductoViewHolder holder, int position) {
        Producto producto = mLstProducto.get(position);
        holder.tvProductoListItemNombre.setText(producto.getNombreProducto());
        holder.tvProductoListItemDesc.setText(producto.getDescripcionProducto());
        holder.tvProductoListItemPrecio.setText("S/ " + String.valueOf(producto.getPrecioProducto()));

        holder.itemView.setOnClickListener(itemViewOnClickListener);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mLstProducto.size();
    }

    static class RVAdapterListaProductoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProductoListItemNombre, tvProductoListItemDesc, tvProductoListItemPrecio;

        public RVAdapterListaProductoViewHolder(View itemView) {
            super(itemView);

            tvProductoListItemNombre = (TextView) itemView.findViewById(R.id.tvProductoListItemNombre);
            tvProductoListItemDesc = (TextView) itemView.findViewById(R.id.tvProductoListItemDesc);
            tvProductoListItemPrecio = (TextView) itemView.findViewById(R.id.tvProductoListItemPrecio);
        }
    }

    public void animateTo(ArrayList<Producto> productos) {
        applyAndAnimateRemovals(productos);
        applyAndAnimateAdditions(productos);
        applyAndAnimateMovedItems(productos);
    }

    private void applyAndAnimateRemovals(List<Producto> newModels) {
        for (int i = mLstProducto.size() - 1; i >= 0; i--) {
            Producto model = mLstProducto.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Producto> newProducts) {
        for (int i = 0, count = newProducts.size(); i < count; i++) {
            final Producto prod = newProducts.get(i);
            if (!mLstProducto.contains(prod)) {
                addItem(i, prod);
            }
        }
    }

    private void applyAndAnimateMovedItems(ArrayList<Producto> newProducts) {
        for (int toPosition = newProducts.size() - 1; toPosition >= 0; toPosition--) {
            Producto model = newProducts.get(toPosition);
            int fromPosition = mLstProducto.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Producto removeItem(int position) {
        final Producto model = mLstProducto.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Producto prod) {
        mLstProducto.add(position, prod);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Producto model = mLstProducto.remove(fromPosition);
        mLstProducto.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void addAll(ArrayList<Producto> producto) {
        mLstProducto.addAll(producto);
    }

    public RVAdapterListaProducto() {
        mLstProducto = new ArrayList<>();
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mIRVAdapterListaProductoListener.onItemClick(mLstProducto.get((Integer) v.getTag()));
        }
    };


}
