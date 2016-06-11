package com.project.cibertec.finalproject.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class PedidoDetalle implements Parcelable {

    private int idPedido;
    private int idProducto;
    private int cantidad;
    private double totalLinea;
    private String nombreProducto;
    private String descripcionProducto;

    public PedidoDetalle(){

    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotalLinea() {
        return totalLinea;
    }

    public void setTotalLinea(double totalLinea) {
        this.totalLinea = totalLinea;
    }

    protected PedidoDetalle(Parcel in) {
        idPedido = in.readInt();
        idProducto = in.readInt();
        cantidad = in.readInt();
        totalLinea = in.readDouble();
        nombreProducto = in.readString();
        descripcionProducto = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPedido);
        dest.writeInt(idProducto);
        dest.writeInt(cantidad);
        dest.writeDouble(totalLinea);
        dest.writeString(nombreProducto);
        dest.writeString(descripcionProducto);
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
