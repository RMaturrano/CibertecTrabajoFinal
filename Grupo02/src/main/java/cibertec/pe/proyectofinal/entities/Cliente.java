package cibertec.pe.proyectofinal.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JOSE on 03/06/2016.
 */
public class Cliente implements Parcelable {
    private int idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String empresa;
    private String direccion;
    private String distrito;
    private String referencia;
    private double latitud;
    private double longitud;

    public Cliente() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idCliente);
        dest.writeString(this.nombre);
        dest.writeString(this.apellido);
        dest.writeString(this.telefono);
        dest.writeString(this.correo);
        dest.writeString(this.empresa);
        dest.writeString(this.direccion);
        dest.writeString(this.distrito);
        dest.writeString(this.referencia);
        dest.writeDouble(this.latitud);
        dest.writeDouble(this.longitud);
    }

    protected Cliente(Parcel in) {
        this.idCliente = in.readInt();
        this.nombre = in.readString();
        this.apellido = in.readString();
        this.telefono = in.readString();
        this.correo = in.readString();
        this.empresa = in.readString();
        this.direccion = in.readString();
        this.distrito = in.readString();
        this.referencia = in.readString();
        this.latitud = in.readDouble();
        this.longitud = in.readDouble();
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel source) {
            return new Cliente(source);
        }

        @Override
        public Cliente[] newArray(int size) { return new Cliente[size]; }
    };

}
