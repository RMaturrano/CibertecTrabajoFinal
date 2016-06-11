package entities;

import android.os.Parcel;
import android.os.Parcelable;

public class PedidoRV implements Parcelable {
    private int idPedido;
    private String Cliente;
    private int Producto;
    private double Total;

    public PedidoRV() {
    }

    public int getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getCliente() {
        return Cliente;
    }
    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public int getProducto() {
        return Producto;
    }
    public void setProducto(int producto) {
        Producto = producto;
    }

    public double getTotal() {
        return Total;
    }
    public void setTotal(double total) {
        Total = total;
    }

    protected PedidoRV(Parcel in) {
        idPedido = in.readInt();
        Cliente = in.readString();
        Producto = in.readInt();
        Total=in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPedido);
        dest.writeString(Cliente);
        dest.writeInt(Producto);
        dest.writeDouble(Total);
    }

    @SuppressWarnings("unused")
    public static final Creator<PedidoRV> CREATOR = new Creator<PedidoRV>() {
        @Override
        public PedidoRV createFromParcel(Parcel in) {
            return new PedidoRV(in);
        }

        @Override
        public PedidoRV[] newArray(int size) {
            return new PedidoRV[size];
        }
    };
}
