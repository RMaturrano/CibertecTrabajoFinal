package com.cibertec.capturapedido.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jtorres on 16/05/2016.
 */
public class Producto implements Parcelable {
    private int productoId;
    private String nombre;
    private String descripcion;
    private double precio;

    public Producto(){}

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    protected Producto(Parcel in) {
        productoId = in.readInt();
        nombre = in.readString();
        descripcion = in.readString();
        precio = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productoId);
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeDouble(precio);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };
}
