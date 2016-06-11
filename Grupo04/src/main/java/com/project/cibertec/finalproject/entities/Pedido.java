package com.project.cibertec.finalproject.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Pedido implements Parcelable  {

    private int idPedido;
    private int idCliente;
    private String nombreCliente;
    private int cantidadProductos;
    private double totalPedido;
    private double precio ;
    private ArrayList<PedidoDetalle> lineas;
    private String empresa;

    public  Pedido (){}

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public ArrayList<PedidoDetalle> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<PedidoDetalle> lineas) {
        this.lineas = lineas;
    }



    protected Pedido(Parcel in) {
        idPedido = in.readInt();
        idCliente = in.readInt();
        nombreCliente = in.readString();
        cantidadProductos = in.readInt();
        empresa = in.readString();
        totalPedido = in.readDouble();
        precio = in.readDouble();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPedido);
        dest.writeInt(idCliente);
        dest.writeString(nombreCliente);
        dest.writeInt(cantidadProductos);
        dest.writeString(empresa);
        dest.writeDouble(totalPedido);
        dest.writeDouble(precio);
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
