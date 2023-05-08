package com.bmr.conapesca.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.bmr.conapesca.Entidades.DatosFoto;

public class FotosManageDB extends FotosHelperDB{
    Context context;
    public FotosManageDB(Context context, String name) {
        super(context, name);
        this.context = context;
    }

    public long InsertaDatos(int Id2, String Descripcion,String Ticket,String Reporte) {
        long id = 0;

        try {
            FotosHelperDB dbHelper = new FotosHelperDB(context, Ticket);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id2", Id2);
            values.put("Reporte", Reporte);
            values.put("Descripción", Descripcion);

            id = db.insert(TABLE_FOTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;

    }
    public int TamañoTabla(String Ticket) {
        FotosHelperDB dbHelper = new FotosHelperDB(context, Ticket);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_FOTOS );
        return numRows;

    }

    public DatosFoto mostrarDatos(String Ticket, int id) {
        FotosHelperDB dbHelper = new FotosHelperDB(context, Ticket);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DatosFoto datos = null;
        Cursor cursordatos = null;
        cursordatos = db.rawQuery("SELECT * FROM " + TABLE_FOTOS  + " WHERE id = " + id + " LIMIT 1 ", null);

        if (cursordatos.moveToFirst()) {
            do {
                datos = new DatosFoto();
                datos.setID(cursordatos.getInt(1));
                datos.setReporte(cursordatos.getString(2));
                datos.setDescripcion(cursordatos.getString(3));

            } while (cursordatos.moveToNext());

        }
        cursordatos.close();
        return datos;
    }

    public boolean editaFoto(String Ticket, String Descripcion,String Reporte, int i) {

        boolean correcto = false;

        FotosHelperDB dbHelper = new FotosHelperDB(context, Ticket);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues cv = new ContentValues();
            cv.put("id2",i);
            cv.put("Reporte",Reporte);
            cv.put("Descripción",Descripcion);
            db.update(TABLE_FOTOS,cv,"id="+String.valueOf(i),null);
            correcto = true;
        } catch (Exception ex) {
            System.out.printf(ex.toString());
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

}
