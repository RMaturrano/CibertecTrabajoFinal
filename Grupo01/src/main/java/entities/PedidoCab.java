package entities;

import android.os.Parcel;
import android.os.Parcelable;

public class PedidoCab implements Parcelable {
    private int idPedido;
    private String Cliente;
    private int CantProducto;
    private double MontoTotal;

    public PedidoCab() {
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

    public int getCantProducto() {
        return CantProducto;
    }
    public void setCantProducto(int cantproducto) {
        CantProducto = cantproducto;
    }

    public double getMontoTotal() {
        return MontoTotal;
    }
    public void setMontoTotal(double montototal) {
        MontoTotal = montototal;
    }

    protected PedidoCab(Parcel in) {
        idPedido = in.readInt();
        Cliente = in.readString();
        CantProducto = in.readInt();
        MontoTotal=in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPedido);
        dest.writeString(Cliente);
        dest.writeInt(CantProducto);
        dest.writeDouble(MontoTotal);
    }

    @SuppressWarnings("unused")
    public static final Creator<PedidoCab> CREATOR = new Creator<PedidoCab>() {
        @Override
        public PedidoCab createFromParcel(Parcel in) {
            return new PedidoCab(in);
        }

        @Override
        public PedidoCab[] newArray(int size) {
            return new PedidoCab[size];
        }
    };
}
