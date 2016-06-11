package com.project.cibertec.finalproject.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable {
    private int clienteId;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String empresa;
    private String direccion;
    private String distrito;
    private String referencia;
    private String latitud;
    private String longitud;

    public Cliente() {
    }

    public Cliente(int clienteId , String nombre , String apellido, String telefono, String correo, String empresa,
                   String direccion, String distrito, String referencia, String latitud, String longitud){

        this.clienteId = clienteId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.empresa = empresa;
        this.direccion = direccion;
        this.distrito = distrito;
        this.referencia = referencia;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    protected Cliente(Parcel in) {
        clienteId = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        telefono = in.readString();
        correo = in.readString();
        empresa = in.readString();
        direccion = in.readString();
        distrito = in.readString();
        referencia = in.readString();
        latitud = in.readString();
        longitud = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(clienteId);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(telefono);
        dest.writeString(correo);
        dest.writeString(empresa);
        dest.writeString(direccion);
        dest.writeString(distrito);
        dest.writeString(referencia);
        dest.writeString(latitud);
        dest.writeString(longitud);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cliente> CREATOR = new Parcelable.Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

}