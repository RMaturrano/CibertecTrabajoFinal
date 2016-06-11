package com.trabajo.capturapedido.capturapedido.adapter.recyclerview;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trabajo.capturapedido.capturapedido.R;
import com.trabajo.capturapedido.capturapedido.adapter.recyclerview.interfaces.IRVProductoAdapter;
import com.trabajo.capturapedido.capturapedido.entities.BEProducto;

import java.util.ArrayList;

/**
 * Created by Alejandra on 01/06/2016.
 */
public class RVAdapterProductoFirst extends RecyclerView.Adapter<RVAdapterProductoFirst.RVAdapterProductoFirstViewHolder> {

    private ArrayList<BEProducto> mLstProductoFirst;
    private IRVProductoAdapter mIRVProductoAdapterFirst;

    public RVAdapterProductoFirst(IRVProductoAdapter mIRVProductoAdapterFirst) {
        this.mIRVProductoAdapterFirst = mIRVProductoAdapterFirst;
        mLstProductoFirst = new ArrayList<>();
    }

    public void clearAndAddAll(ArrayList<BEProducto> lstProducto) {
        mLstProductoFirst.clear();
        mLstProductoFirst.addAll(lstProducto);
        notifyDataSetChanged();
    }

    @Override
    public RVAdapterProductoFirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVAdapterProductoFirstViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_producto,parent,false));
    }

    @Override
    public void onBindViewHolder(RVAdapterProductoFirstViewHolder holder, int position) {
        if (position == 0){
            ((RecyclerView.LayoutParams) holder.itemView.getLayoutParams()).topMargin = holder.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.heigth_toolbar);
        }

        BEProducto producto = mLstProductoFirst.get(position);
//        holder.tvIdProducto.setText(String.valueOf(producto.getIdProducto()));
        holder.tvNomProducto.setText(producto.getNomProducto());
        holder.tvDescProducto.setText(producto.getDescProducto());
        holder.tvPrecio.setText(String.valueOf(producto.getPrecioProducto()));

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(itemViewOnClickListener);
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mIRVProductoAdapterFirst != null){
                int position = (int) v.getTag();
                mIRVProductoAdapterFirst.onSelectItem(mLstProductoFirst.get(position));
            }
        }
    };

    @Override
    public int getItemCount() {
        return mLstProductoFirst.size();
    }

    public class RVAdapterProductoFirstViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdProducto, tvNomProducto, tvPrecio, tvDescProducto;

        public RVAdapterProductoFirstViewHolder(View itemView) {
            super(itemView);

            tvNomProducto = (TextView) itemView.findViewById(R.id.tvNomCliente);
            tvDescProducto = (TextView) itemView.findViewById(R.id.tvItemDescripcionProducto);
            tvPrecio = (TextView) itemView.findViewById(R.id.tvItemPrecioProducto);
        }
    }
}
