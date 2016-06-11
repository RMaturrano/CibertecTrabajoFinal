package adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.recyclerview.interfaces.IRVPedidoDetAdapter;
import entities.PedidoDet;
import trabajofinalcb.javecarlos.pe.trabajofinalcb.R;

public class RVPedidoDetAdapter extends RecyclerView.Adapter<RVPedidoDetAdapter.RVPedidoDetViewHolder> {
    private ArrayList<PedidoDet> listaPedido;
    private IRVPedidoDetAdapter _mIRVPedidoDetAdapter;

    public RVPedidoDetAdapter(IRVPedidoDetAdapter mIRVPedidoDetAdapter) {
        listaPedido = new ArrayList<>();
        _mIRVPedidoDetAdapter = mIRVPedidoDetAdapter;
    }

    public void add(PedidoDet pedido) {
        listaPedido.add(pedido);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<PedidoDet> lstPedido) {
        int position = listaPedido.size();
        listaPedido.addAll(lstPedido);
        notifyItemRangeInserted(position, lstPedido.size());
    }

    public void clearAndAddAll(ArrayList<PedidoDet> lstPedido) {
        listaPedido.clear();
        listaPedido.addAll(lstPedido);
        notifyDataSetChanged();
    }

    @Override
    public RVPedidoDetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVPedidoDetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pedido_det_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(RVPedidoDetViewHolder holder, int position) {
        PedidoDet pedido = listaPedido.get(position);

        holder.tvNombreProdRV.setText(pedido.getNombreProd());
        //holder.tvDescripcionProdRV.setText(pedido.getDescripcionProd());
        holder.tvCantidadRV.setText("Cantidad : "+String.valueOf(pedido.getCantidad()));
        holder.tvMontoRV.setText("Monto : S/. "+String.valueOf(pedido.getSubTotal()));

        holder.itemView.setOnClickListener(itemViewOnClickListener);
        holder.itemView.setTag(position);
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PedidoDet pedido = listaPedido.get((Integer) v.getTag());
            _mIRVPedidoDetAdapter.onItemClick(pedido);
        }
    };

    @Override
    public int getItemCount() {
        return listaPedido.size();
    }

    static class RVPedidoDetViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreProdRV, tvDescripcionProdRV, tvCantidadRV, tvMontoRV;

        public RVPedidoDetViewHolder(View itemView) {
            super(itemView);

            tvNombreProdRV = (TextView) itemView.findViewById(R.id.tvNombreProductoRV);
            //tvDescripcionProdRV = (TextView) itemView.findViewById(R.id.tvDescripcionProductoRV);
            tvCantidadRV = (TextView) itemView.findViewById(R.id.tvCantidadRV);
            tvMontoRV = (TextView) itemView.findViewById(R.id.tvMontoRV);
        }
    }
}