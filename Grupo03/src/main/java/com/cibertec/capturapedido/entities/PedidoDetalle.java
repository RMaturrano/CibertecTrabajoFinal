package com.cibertec.capturapedido.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jorge on 22/05/2016.
 */
public class PedidoDetalle implements Parcelable {
    private int pedidoId;
    private int productoId;
    private String productoNombre;
    private String productoDescripcion;
    private double cantidad;
    private double precio;

    public PedidoDetalle(){}

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public String getProductoDescripcion() {
        return productoDescripcion;
    }

    public void setProductoDescripcion(String productoDescripcion) {
        this.productoDescripcion = productoDescripcion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    protected PedidoDetalle(Parcel in) {
        pedidoId = in.readInt();
        productoId = in.readInt();
        productoNombre = in.readString();
        productoDescripcion = in.readString();
        cantidad = in.readDouble();
        precio = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pedidoId);
        dest.writeInt(productoId);
        dest.writeString(productoNombre);
        dest.writeString(productoDescripcion);
        dest.writeDouble(cantidad);
        dest.writeDouble(precio);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PedidoDetalle> CREATOR = new Parcelable.Creator<PedidoDetalle>() {
        @Override
        public PedidoDetalle createFromParcel(Parcel in) {
            return new PedidoDetalle(in);
        }

        @Override
        public PedidoDetalle[] newArray(int size) {
            return new PedidoDetalle[size];
        }
    };
}