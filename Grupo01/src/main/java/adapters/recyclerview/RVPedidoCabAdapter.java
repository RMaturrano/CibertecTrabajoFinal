package adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.recyclerview.interfaces.IRVPedidoCabAdapter;
import entities.PedidoCab;
import trabajofinalcb.javecarlos.pe.trabajofinalcb.R;

public class RVPedidoCabAdapter extends RecyclerView.Adapter<RVPedidoCabAdapter.RVPedidoViewHolder> {
    private ArrayList<PedidoCab> listaPedido;
    private IRVPedidoCabAdapter _mIRVPedidoCabAdapter;

    public RVPedidoCabAdapter(IRVPedidoCabAdapter mIRVPedidoCabAdapter) {
        listaPedido = new ArrayList<>();
        _mIRVPedidoCabAdapter = mIRVPedidoCabAdapter;
    }

    public void add(PedidoCab pedido) {
        listaPedido.add(pedido);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<PedidoCab> lstPedido) {
        int position = listaPedido.size();
        listaPedido.addAll(lstPedido);
        notifyItemRangeInserted(position, lstPedido.size());
    }

    public void clearAndAddAll(ArrayList<PedidoCab> lstPedido) {
        listaPedido.clear();
        listaPedido.addAll(lstPedido);
        notifyDataSetChanged();
    }

    @Override
    public RVPedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVPedidoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pedido_cab_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(RVPedidoViewHolder holder, int position) {
        PedidoCab pedido = listaPedido.get(position);

        holder.tvIdPedidoRV.setText("Pedido : "+String.valueOf(pedido.getIdPedido()));
        holder.tvClienteRV.setText("Cliente : "+pedido.getCliente());
        holder.tvProductoRV.setText(String.valueOf(pedido.getCantProducto())+" producto(s)");
        holder.tvTotalRV.setText("S/. "+String.valueOf(pedido.getMontoTotal()));

        holder.itemView.setOnClickListener(itemViewOnClickListener);
        holder.itemView.setTag(position);
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PedidoCab pedido = listaPedido.get((Integer) v.getTag());
            _mIRVPedidoCabAdapter.onItemClick(pedido);
        }
    };

    @Override
    public int getItemCount() {
        return listaPedido.size();
    }

    static class RVPedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdPedidoRV, tvClienteRV, tvProductoRV, tvTotalRV;

        public RVPedidoViewHolder(View itemView) {
            super(itemView);

            tvIdPedidoRV = (TextView) itemView.findViewById(R.id.tvIdPedidoRV);
            tvClienteRV = (TextView) itemView.findViewById(R.id.tvClienteRV);
            tvProductoRV = (TextView) itemView.findViewById(R.id.tvProductoRV);
            tvTotalRV = (TextView) itemView.findViewById(R.id.tvTotalRV);
        }
    }
}
