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
 * Created by cechenique on 19/05/2016.
 */
public class RVProductoAdapter extends RecyclerView.Adapter<RVProductoAdapter.RVProductoAdapterViewHolder> {

    private ArrayList<BEProducto> mLstProducto;
    private IRVProductoAdapter mIRVProductoAdapter;

    public RVProductoAdapter(IRVProductoAdapter mIRVProductoAdapter){
        this.mIRVProductoAdapter = mIRVProductoAdapter;
        mLstProducto = new ArrayList<>();
    }

    public void clearAndAddAll(ArrayList<BEProducto> lstProducto) {
        mLstProducto.clear();
        mLstProducto.addAll(lstProducto);
        notifyDataSetChanged();
    }

    @Override
    public RVProductoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new RVProductoAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_item,parent,false));
    }

    @Override
    public int getItemCount() {
        return mLstProducto.size();
    }

    class RVProductoAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tvIdProducto, tvNomProducto, tvPrecio, tvDescProducto;

        public RVProductoAdapterViewHolder(View itemView){
            super(itemView);
            tvIdProducto = (TextView) itemView.findViewById(R.id.tvIdProducto);
            tvNomProducto = (TextView) itemView.findViewById(R.id.tvNomProducto);
            tvDescProducto = (TextView) itemView.findViewById(R.id.tvDescProducto);
            tvPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);
        }
    }

    @Override
    public void onBindViewHolder(RVProductoAdapterViewHolder holder, int position){
        if (position == 0){
            ((RecyclerView.LayoutParams) holder.itemView.getLayoutParams()).topMargin = holder.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.heigth_toolbar);
        }

        BEProducto producto = mLstProducto.get(position);
        holder.tvIdProducto.setText(String.valueOf(producto.getIdProducto()));
        holder.tvNomProducto.setText(producto.getNomProducto());
        holder.tvDescProducto.setText(producto.getDescProducto());
        holder.tvPrecio.setText(String.valueOf(producto.getPrecioProducto()));

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(itemViewOnClickListener);
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mIRVProductoAdapter != null){
                int position = (int) v.getTag();
                mIRVProductoAdapter.onSelectItem(mLstProducto.get(position));
            }
        }
    };
}
