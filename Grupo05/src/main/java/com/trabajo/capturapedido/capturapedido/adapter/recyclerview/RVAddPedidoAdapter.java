package com.trabajo.capturapedido.capturapedido.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trabajo.capturapedido.capturapedido.R;
import com.trabajo.capturapedido.capturapedido.entities.BEPedido;

import java.util.ArrayList;

/**
 * Created by cechenique on 25/05/2016.
 */
public class RVAddPedidoAdapter extends RecyclerView.Adapter<RVAddPedidoAdapter.RVAddPedidoAdapterViewHolder>{
    private ArrayList<BEPedido> mLstPedido;
   // private IRVPedidoAddAdapter mIRVPedidoAddAdapter;

    public RVAddPedidoAdapter() {
        mLstPedido = new ArrayList<>();
    }

    public void add(BEPedido pedido) {
        mLstPedido.add(pedido);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(ArrayList<BEPedido> lstPedido) {
        mLstPedido.clear();
        mLstPedido.addAll(lstPedido);
        notifyDataSetChanged();
    }

    public ArrayList<BEPedido> ListaDetallePedido() {
        ArrayList<BEPedido> lstPedido = new ArrayList<>();
        lstPedido.addAll(mLstPedido);
        return lstPedido;
    }

    /*public void update(BEPedido pedido) {
        boolean isUpdated = false;
        for (int i = 0; i < mLstPedido.size(); i++) {
            if (mLstPedido.get(i).getIdProdPedidoDet() == pedido.getIdProdPedidoDet()) {
                mLstPedido.get(i).setCantPedidoDet(pedido.getCantPedidoDet());

                notifyItemChanged(i);

                isUpdated = true;
                break;
            }
        }

        if (!isUpdated)
            add(pedido);
    }*/

   /* public void remove(BEPedido pedido) {
        for (int i = 0; i < mLstPedido.size(); i++) {
            if (mLstPedido.get(i).getIdProdPedidoDet() == pedido.getIdProdPedidoDet()) {
                mLstPedido.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }*/

    @Override
    public RVAddPedidoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVAddPedidoAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pedido_additem, parent, false));
    }

    @Override
    public int getItemCount() {
        return mLstPedido.size();
    }

    static class RVAddPedidoAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView tvIdProducto, tvNomProducto, tvCantProd, tvDescProducto,  tvPrecioProd;

        public RVAddPedidoAdapterViewHolder(View itemView) {
            super(itemView);
            tvIdProducto = (TextView) itemView.findViewById(R.id.tvAddIdProd);
            tvNomProducto = (TextView) itemView.findViewById(R.id.tvAddNomProd);
            tvCantProd =(TextView)itemView.findViewById(R.id.tvAddCantProd);
            tvDescProducto = (TextView) itemView.findViewById(R.id.tvAddDescProd);
            tvPrecioProd = (TextView) itemView.findViewById(R.id.tvAddPrecioProd);
        }
    }

    @Override
    public void onBindViewHolder(RVAddPedidoAdapterViewHolder holder, int position) {

        BEPedido pedido = mLstPedido.get(position);
        holder.tvIdProducto.setText(String.valueOf(pedido.getIdProdPedidoDet()));
        holder.tvNomProducto.setText(pedido.getNomProdPedidoDet());
        holder.tvCantProd.setText(String.valueOf(pedido.getCantPedidoDet()));
        holder.tvDescProducto.setText(pedido.getDescProdPedidoDet());
        holder.tvPrecioProd.setText(String.valueOf(pedido.getPrecioPedidoDet()));

       // holder.itemView.setTag(position);
       // holder.itemView.setOnClickListener(itemViewOnClickListener);
    }

    /*View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ( mIRVPedidoAddAdapter != null) {
                int position = (int) v.getTag();
                 mIRVPedidoAddAdapter.onSelectItem(mLstPedido.get(position));
                // mIRVPedidoAddAdapter.onItemClick(mLstPedido.get(position));
            }
        }
    };*/
}

