package com.cibertec.capturapedido.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jorge on 22/05/2016.
 */
public class Pedido implements Parcelable {
    private int pedidoId;
    private int clienteId;
    private String fecha;
    private String cliente;
    private double total;
    private int cant_productos;
    private ArrayList<PedidoDetalle> detalle;

    public Pedido() {
        this.detalle = new ArrayList<>();
    }

    public ArrayList<PedidoDetalle> getDetalle() {
        return detalle;
    }

    public int getCant_productos() {
        return cant_productos;
    }

    public void setCant_productos(int cant_productos) {
        this.cant_productos = cant_productos;
    }

    public void setDetalle(ArrayList<PedidoDetalle> detalle) {
        this.detalle = detalle;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    protected Pedido(Parcel in) {
        pedidoId = in.readInt();
        clienteId = in.readInt();
        fecha = in.readString();
        cliente = in.readString();
        total = in.readDouble();
        cant_productos = in.readInt();
        if (in.readByte() == 0x01) {
            detalle = new ArrayList<PedidoDetalle>();
            in.readList(detalle, PedidoDetalle.class.getClassLoader());
        } else {
            detalle = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pedidoId);
        dest.writeInt(clienteId);
        dest.writeString(fecha);
        dest.writeString(cliente);
        dest.writeDouble(total);
        dest.writeInt(cant_productos);
        if (detalle == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(detalle);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pedido> CREATOR = new Parcelable.Creator<Pedido>() {
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