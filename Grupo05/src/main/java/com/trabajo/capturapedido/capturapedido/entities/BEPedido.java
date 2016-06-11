package com.trabajo.capturapedido.capturapedido.entities;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by cechenique on 18/05/2016.
 */
public class BEPedido implements Parcelable {

    private int idPedido;
    private int idClientePedido;
    private String nomClientePedido;
    private String fechaPedido;
    private int canttotPedido;
    private double totalPedido;


    private int idPedidoDet;
    private int idProdPedidoDet;
    private String nomProdPedidoDet;
    private String descProdPedidoDet;
    private int cantPedidoDet;
    private double precioPedidoDet;
    private double totalPedidoDet;


    public BEPedido(){

    }


    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdClientePedido() {
        return idClientePedido;
    }

    public void setIdClientePedido(int idClientePedido) {
        this.idClientePedido = idClientePedido;
    }

    public String getNomClientePedido() {
        return nomClientePedido;
    }

    public void setNomClientePedido(String nomClientePedido) {
        this.nomClientePedido = nomClientePedido;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getCanttotPedido() {
        return canttotPedido;
    }

    public void setCanttotPedido(int canttotPedido) {
        this.canttotPedido = canttotPedido;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public int getIdPedidoDet() {
        return idPedidoDet;
    }

    public void setIdPedidoDet(int idPedidoDet) {
        this.idPedidoDet = idPedidoDet;
    }

    public int getIdProdPedidoDet() {
        return idProdPedidoDet;
    }

    public void setIdProdPedidoDet(int idProdPedidoDet) {
        this.idProdPedidoDet = idProdPedidoDet;
    }

    public String getNomProdPedidoDet() {
        return nomProdPedidoDet;
    }

    public void setNomProdPedidoDet(String nomProdPedidoDet) {
        this.nomProdPedidoDet = nomProdPedidoDet;
    }

    public String getDescProdPedidoDet() {
        return descProdPedidoDet;
    }

    public void setDescProdPedidoDet(String descProdPedidoDet) {
        this.descProdPedidoDet = descProdPedidoDet;
    }

    public int getCantPedidoDet() {
        return cantPedidoDet;
    }

    public void setCantPedidoDet(int cantPedidoDet) {
        this.cantPedidoDet = cantPedidoDet;
    }

    public double getPrecioPedidoDet() {
        return precioPedidoDet;
    }

    public void setPrecioPedidoDet(double precioPedidoDet) {
        this.precioPedidoDet = precioPedidoDet;
    }

    public double getTotalPedidoDet() {
        return totalPedidoDet;
    }

    public void setTotalPedidoDet(double totalPedidoDet) {
        this.totalPedidoDet = totalPedidoDet;
    }


    protected BEPedido(Parcel in) {
        idPedido = in.readInt();
        idClientePedido = in.readInt();
        nomClientePedido = in.readString();
        fechaPedido = in.readString();
        canttotPedido = in.readInt();
        totalPedido = in.readDouble();
        idPedidoDet = in.readInt();
        idProdPedidoDet = in.readInt();
        nomProdPedidoDet = in.readString();
        descProdPedidoDet = in.readString();
        cantPedidoDet = in.readInt();
        precioPedidoDet = in.readDouble();
        totalPedidoDet = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPedido);
        dest.writeInt(idClientePedido);
        dest.writeString(nomClientePedido);
        dest.writeString(fechaPedido);
        dest.writeInt(canttotPedido);
        dest.writeDouble(totalPedido);
        dest.writeInt(idPedidoDet);
        dest.writeInt(idProdPedidoDet);
        dest.writeString(nomProdPedidoDet);
        dest.writeString(descProdPedidoDet);
        dest.writeInt(cantPedidoDet);
        dest.writeDouble(precioPedidoDet);
        dest.writeDouble(totalPedidoDet);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BEPedido> CREATOR = new Parcelable.Creator<BEPedido>() {
        @Override
        public BEPedido createFromParcel(Parcel in) {
            return new BEPedido(in);
        }

        @Override
        public BEPedido[] newArray(int size) {
            return new BEPedido[size];
        }
    };
}