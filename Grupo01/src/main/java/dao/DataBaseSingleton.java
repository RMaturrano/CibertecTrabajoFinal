package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Android-SAB-PM on 07/05/2016.
 */
public class DataBaseSingleton {

    private static DataBaseHelper dataBaseHelper;
    private static Context mContext;

    public DataBaseSingleton(Context context) {
        this.mContext = context;
    }

    public static SQLiteDatabase getInstance() {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(mContext);
            dataBaseHelper.openDataBase();
        }

        return dataBaseHelper.getWritableDatabase();
    }
}
