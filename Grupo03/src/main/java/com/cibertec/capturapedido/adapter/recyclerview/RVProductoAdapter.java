package com.cibertec.capturapedido.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cibertec.capturapedido.R;
import com.cibertec.capturapedido.adapter.recyclerview.interfaces.IRVProductoAdapter;
import com.cibertec.capturapedido.entities.Producto;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jorge on 22/05/2016.
 */
public class RVProductoAdapter extends RecyclerView.Adapter<RVProductoAdapter.RVProductoViewHolder> {
    private ArrayList<Producto> mLstProducto;
    private IRVProductoAdapter mIRVProductoAdapter;

    public RVProductoAdapter(IRVProductoAdapter mIRVProductoAdapter) {
        this.mIRVProductoAdapter = mIRVProductoAdapter;
        this.mLstProducto = new ArrayList<>();
    }

    @Override
    public RVProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVProductoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false));
    }

    @Override
    public void onBindViewHolder(RVProductoViewHolder holder, int position) {
        Producto producto = mLstProducto.get(position);
        holder.itemView.setTag(position);
        holder.tvItemProductoNombre.setText(producto.getNombre());
        holder.tvItemProductoDescripcion.setText(producto.getDescripcion());
        holder.tvItemProductoPrecio.setText(String.format(Locale.ENGLISH , "%1$,.2f",producto.getPrecio()));

        //new com.cibertec.capturapedido.util.ImageDownloadTask(holder.ivItemProductoFoto).execute("http://fanextrade.com/wp-content/uploads/2014/06/coca-cola-1500-ml.jpg");

        holder.itemView.setOnClickListener(itemViewOnClickListener);
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            mIRVProductoAdapter.onSelectItem(mLstProducto.get(position));
        }
    };
    public int getItemCount() {
        return mLstProducto.size();
    }

    //Metodos del adaptador
    public void add(Producto producto){
        mLstProducto.add(producto);
        notifyItemInserted(mLstProducto.size() - 1);
    }

    public void addAll(ArrayList<Producto> lista){
        mLstProducto.clear();
        mLstProducto.addAll(lista);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(ArrayList<Producto> lstProducto) {
        mLstProducto.clear();
        mLstProducto.addAll(lstProducto);
        notifyDataSetChanged();
    }

    class RVProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemProductoNombre, tvItemProductoDescripcion, tvItemProductoPrecio;
        //ImageView ivItemProductoFoto;
        public RVProductoViewHolder(View itemView) {
            super(itemView);
            //ivItemProductoFoto = (ImageView) itemView.findViewById(R.id.ivItemProductoFoto);
            tvItemProductoNombre = (TextView) itemView.findViewById(R.id.tvItemProductoNombre);
            tvItemProductoDescripcion = (TextView) itemView.findViewById(R.id.tvItemProductoDescripcion);
            tvItemProductoPrecio = (TextView) itemView.findViewById(R.id.tvItemProductoPrecio);
        }
    }
}
