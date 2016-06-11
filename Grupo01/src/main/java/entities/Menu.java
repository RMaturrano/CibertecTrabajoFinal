package entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carlosarmando on 04/05/2016.
 */
public class Menu implements Parcelable {
    private int Id;
    private String Icono;
    private String Descripcion;

    public Menu(){

    }

    public Menu(int id, String icono, String descripcion){
        this.Id = id;
        this.Icono = icono;
        this.Descripcion = descripcion;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getIcono() {
        return Icono;
    }

    public void setIcono(String icono) {
        Icono = icono;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    protected Menu(Parcel in) {
        Id = in.readInt();
        Icono = in.readString();
        Descripcion = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Icono);
        dest.writeString(Descripcion);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Menu> CREATOR = new Parcelable.Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };
}
