package entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by USER on 07/06/2016.
 */
public class Pedido implements Parcelable {
    private int IdPedido;
    private int IdProducto;
    private int IdCliente;
    private Date FeEntrega;
    private Double NuPrecio;
    private Double NuCantidad;
    private String EsEstado;

    public Pedido() {
    }

    public int getIdPedido() {
        return IdPedido;
    }

    public void setIdPedido(int idPedido) {
        IdPedido = idPedido;
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        IdCliente = idCliente;
    }

    public Date getFeEntrega() {
        return FeEntrega;
    }

    public void setFeEntrega(Date feEntrega) {
        FeEntrega = feEntrega;
    }

    public Double getNuPrecio() {
        return NuPrecio;
    }

    public void setNuPrecio(Double nuPrecio) {
        NuPrecio = nuPrecio;
    }

    public Double getNuCantidad() {
        return NuCantidad;
    }

    public void setNuCantidad(Double nuCantidad) {
        NuCantidad = nuCantidad;
    }

    public String getEsEstado() {
        return EsEstado;
    }

    public void setEsEstado(String esEstado) {
        EsEstado = esEstado;
    }

    protected Pedido(Parcel in) {
        IdPedido = in.readInt();
        IdProducto = in.readInt();
        IdCliente = in.readInt();
        long tmpFeEntrega = in.readLong();
        FeEntrega = tmpFeEntrega != -1 ? new Date(tmpFeEntrega) : null;
        NuPrecio = in.readByte() == 0x00 ? null : in.readDouble();
        NuCantidad = in.readByte() == 0x00 ? null : in.readDouble();
        EsEstado = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdPedido);
        dest.writeInt(IdProducto);
        dest.writeInt(IdCliente);
        dest.writeLong(FeEntrega != null ? FeEntrega.getTime() : -1L);
        if (NuPrecio == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(NuPrecio);
        }
        if (NuCantidad == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(NuCantidad);
        }
        dest.writeString(EsEstado);
    }

    @SuppressWarnings("unused")
    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };
}
