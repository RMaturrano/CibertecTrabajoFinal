package entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gosoriot on 13/05/2016.
 */
public class Usuarios implements Parcelable {

    private String USnombre;
    private String USapellidos;
    private String USfoto;
    private String USusu;
    private String USclave;
    private int USarea;

    public Usuarios() {
    }


    public String getUSnombre() {
        return USnombre;
    }
    public void setUSnombre(String USnombre) {
        this.USnombre = USnombre;
    }

    public String getUSapellidos() {
        return USapellidos;
    }
    public void setUSapellidos(String USapellidos) {
        this.USapellidos = USapellidos;
    }

    public String getUSfoto() {
        return USfoto;
    }
    public void setUSfoto(String USfoto) {
        this.USfoto = USfoto;
    }

    public String getUSusu() {   return USusu; }
    public void setUSusu(String USusu) {  this.USusu = USusu; }

    public String getUSclave() {
        return USclave;
    }
    public void setUSclave(String USclave) {
        this.USclave = USclave;
    }

    public Integer getUSarea() {
        return USarea;
    }
    public void setUSarea(Integer USarea) {
        this.USarea = USarea;
    }

    protected Usuarios(Parcel in) {
        USnombre = in.readString();
        USapellidos = in.readString();
        USfoto = in.readString();
        USusu = in.readString();
        USclave = in.readString();
        USarea = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(USnombre);
        dest.writeString(USapellidos);
        dest.writeString(USfoto);
        dest.writeString(USusu);
        dest.writeString(USclave);
        dest.writeInt(USarea);
    }

    @SuppressWarnings("unused")
    public static final Creator<Usuarios> CREATOR = new Creator<Usuarios>() {
        @Override
        public Usuarios createFromParcel(Parcel in) {
            return new Usuarios(in);
        }

        @Override
        public Usuarios[] newArray(int size) {
            return new Usuarios[size];
        }
    };
}