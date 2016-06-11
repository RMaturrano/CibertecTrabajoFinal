package adapters.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import adapters.recyclerview.interfaces.IRVProductoAdapter;
import entities.Productos;
import trabajofinalcb.javecarlos.pe.trabajofinalcb.R;
/**
 * Created by gosoriot on 10/05/2016.
 */

//extends RecyclerView.Adapter<RVClienteAdapter.RVClienteViewHolder>
public class RVProductoAdapter  extends RecyclerView.Adapter<RVProductoAdapter.RVProductoAdapterViewHolder> {

    private ArrayList<Productos> mLstProdcutos;
    private IRVProductoAdapter mIRVFirstAdapter;

    public RVProductoAdapter(IRVProductoAdapter mIRVFirstAdapter) {
        this.mIRVFirstAdapter = mIRVFirstAdapter;
        mLstProdcutos = new ArrayList<>();
    }

    public void add(Productos productos) {
        mLstProdcutos.add(productos);
        notifyItemInserted(mLstProdcutos.size() - 1);
    }

    public void addAll(ArrayList<Productos> lstProductos) {
        int position = mLstProdcutos.size();
        mLstProdcutos.addAll(lstProductos);
        notifyItemRangeInserted(position, lstProductos.size());
    }

    public void clearAndAddAll(ArrayList<Productos> lstProductos) {
        mLstProdcutos.clear();
        mLstProdcutos.addAll(lstProductos);
        notifyDataSetChanged();
    }

    @Override
    public RVProductoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVProductoAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_productos_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(RVProductoAdapterViewHolder holder, int position) {
        Productos productos = mLstProdcutos.get(position);
        String codigo = "00000000" + String.valueOf(productos.getIdProducto());
        holder.tvIdProduct.setText(codigo.substring(String.valueOf(productos.getIdProducto()).length()));
        holder.tvNombreProductoRV.setText(productos.getNombreP());
        holder.tvPrecioRV.setText(String.valueOf(productos.getPrecioP()));
        //holder.tvStockRV.setText(String.valueOf(productos.getStockP()));
       holder.itemView.setTag(position);
       holder.itemView.setOnClickListener(itemViewOnClickListener);
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            mIRVFirstAdapter.onSelectItem(mLstProdcutos.get(position));
        }
    };

    @Override
    public int getItemCount() {
        return mLstProdcutos.size();
    }
    public void setFilter(List<Productos> mProducto){
        mLstProdcutos = new ArrayList<>();
        mLstProdcutos.addAll(mProducto);
        notifyDataSetChanged();
    }
    class RVProductoAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdProduct, tvNombreProductoRV,  tvStockRV,  tvPrecioRV;

        public RVProductoAdapterViewHolder(View itemView) {
            super(itemView);
            tvIdProduct = (TextView) itemView.findViewById(R.id.tvIdProduct);
            tvNombreProductoRV = (TextView) itemView.findViewById(R.id.tvNombreProductoRV);
            //tvStockRV = (TextView) itemView.findViewById(R.id.tvStockRV);
            tvPrecioRV = (TextView) itemView.findViewById(R.id.tvPrecioRV);
        }
    }
}
