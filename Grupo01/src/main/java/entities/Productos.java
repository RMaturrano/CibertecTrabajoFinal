package entities;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by gosoriot on 10/05/2016.
 */
public class Productos implements Parcelable {
    private int IdProducto;
    private String NombreP;
    private Double PrecioP;
    private int StockP;


    public Productos() {
    }

    public int getIdProducto() {
        return IdProducto;
    }
    public void setIdProducto(int IdProducto) {
        this.IdProducto = IdProducto;
    }


    public String getNombreP() {
        return NombreP;
    }
    public void setNombreP(String NombreP) {
        this.NombreP = NombreP;
    }


    public Double getPrecioP() {
        return PrecioP;
    }
    public void setPrecioP(Double PrecioP) {
        this.PrecioP = PrecioP;
    }

    public int getStockP() {
        return StockP;
    }
    public void setStockP(int StockP) {
        this.StockP = StockP;
    }


    protected Productos(Parcel in) {
        IdProducto= in.readInt();
        NombreP = in.readString();
        PrecioP = in.readDouble();
        StockP = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdProducto);
        dest.writeString(NombreP);
        dest.writeDouble(PrecioP);
        dest.writeInt(StockP);
    }

    @SuppressWarnings("unused")
    public static final Creator<Productos> CREATOR = new Creator<Productos>() {
        @Override
        public Productos createFromParcel(Parcel in) {
            return new Productos(in);
        }

        @Override
        public Productos[] newArray(int size) {
            return new Productos[size];
        }
    };
}