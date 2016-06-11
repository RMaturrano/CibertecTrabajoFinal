package com.trabajo.capturapedido.capturapedido.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cechenique on 18/05/2016.
 */
public class BECliente implements Parcelable {

    private int IdCliente;
    private String NomCliente;
    private String ApellCliente;
    private String TelfCliente;
    private String CorreoCliente;
    private String CiaCliente;
    private String DireccCliente;
    private String DistCliente;
    private String RefCliente;
    private Double Latitud;
    private Double Longitud;

    public BECliente(){

    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        this.IdCliente = idCliente;
    }

    public String getNomCliente() {
        return NomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.NomCliente = nomCliente;
    }

    public String getApellCliente() {
        return ApellCliente;
    }

    public void setApellCliente(String apellCliente) {
        this.ApellCliente = apellCliente;
    }

    public String getTelfCliente() {
        return TelfCliente;
    }

    public void setTelfCliente(String telfCliente) {
        this.TelfCliente = telfCliente;
    }

    public String getCorreoCliente() {
        return CorreoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.CorreoCliente = correoCliente;
    }

    public String getCiaCliente() {
        return CiaCliente;
    }

    public void setCiaCliente(String ciaCliente) {
        this.CiaCliente = ciaCliente;
    }

    public String getDireccCliente() {
        return DireccCliente;
    }

    public void setDireccCliente(String direccCliente) {
        this.DireccCliente = direccCliente;
    }

    public String getDistCliente() {
        return DistCliente;
    }

    public void setDistCliente(String distCliente) {
        this.DistCliente = distCliente;
    }

    public String getRefCliente() {
        return RefCliente;
    }

    public void setRefCliente(String refCliente) {
        this.RefCliente = refCliente;
    }

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        this.Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        this.Longitud = longitud;
    }


    protected BECliente(Parcel in) {
        this.IdCliente = in.readInt();
        this.NomCliente = in.readString();
        this.ApellCliente = in.readString();
        this.TelfCliente = in.readString();
        this.CorreoCliente = in.readString();
        this.CiaCliente = in.readString();
        this.DireccCliente = in.readString();
        this.DistCliente = in.readString();
        this.RefCliente = in.readString();
        Latitud = in.readByte() == 0x00 ? null : in.readDouble();
        Longitud = in.readByte() == 0x00 ? null : in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdCliente);
        dest.writeString(NomCliente);
        dest.writeString(ApellCliente);
        dest.writeString(TelfCliente);
        dest.writeString(CorreoCliente);
        dest.writeString(CiaCliente);
        dest.writeString(DireccCliente);
        dest.writeString(DistCliente);
        dest.writeString(RefCliente);
        if (Latitud == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(Latitud);
        }
        if (Longitud == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(Longitud);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BECliente> CREATOR = new Parcelable.Creator<BECliente>() {
        @Override
        public BECliente createFromParcel(Parcel source) {
            return new BECliente(source);
        }

        @Override
        public BECliente[] newArray(int size) {
            return new BECliente[size];
        }
    };
}
