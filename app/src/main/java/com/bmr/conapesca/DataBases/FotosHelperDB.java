package com.bmr.conapesca.DataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FotosHelperDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    public static final String TABLE_FOTOS="t_fotos";


    public FotosHelperDB (Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ TABLE_FOTOS +"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id2 INTEGER NOT NULL,"+
                "Reporte STRING NOT NULL,"+
                "Descripción STRING NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
