package entities;

import android.os.Parcel;
import android.os.Parcelable;

public class PedidoDet implements Parcelable {
    private int IdProducto;
    private String NombreProd;
    private double Cantidad;
    private double Precio;
    private double SubTotal;

    public PedidoDet() {
    }

    public String getNombreProd() {
        return NombreProd;
    }

    public void setNombreProd(String nombreProd) {
        NombreProd = nombreProd;
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double cantidad) {
        Cantidad = cantidad;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(double subtotal) {
        SubTotal = subtotal;
    }

    protected PedidoDet(Parcel in) {
        NombreProd = in.readString();
        IdProducto = in.readInt();
        Cantidad = in.readDouble();
        Precio = in.readDouble();
        SubTotal = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NombreProd);
        dest.writeInt(IdProducto);
        dest.writeDouble(Cantidad);
        dest.writeDouble(Precio);
        dest.writeDouble(SubTotal);
    }

    @SuppressWarnings("unused")
    public static final Creator<PedidoDet> CREATOR = new Creator<PedidoDet>() {
        @Override
        public PedidoDet createFromParcel(Parcel in) {
            return new PedidoDet(in);
        }

        @Override
        public PedidoDet[] newArray(int size) {
            return new PedidoDet[size];
        }
    };
}