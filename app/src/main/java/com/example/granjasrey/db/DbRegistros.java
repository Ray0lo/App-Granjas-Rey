package com.example.granjasrey.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.granjasrey.entidades.Registros;

import java.util.ArrayList;

public class DbRegistros extends DbHelper {

    Context context;

    public DbRegistros(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarRegistro(String nombre, String fecha, String hora) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("fecha", fecha);
            values.put("hora", hora);

            id = db.insert(TABLE_REGISTRO, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Registros> mostrarRegistros() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Registros> listaRegistros = new ArrayList<>();
        Registros registro;
        Cursor cursorRegistros;

        cursorRegistros = db.rawQuery("SELECT * FROM " + TABLE_REGISTRO + " ORDER BY nombre ASC", null);

        if (cursorRegistros.moveToFirst()) {
            do {
                registro = new Registros();
                registro.setId(cursorRegistros.getInt(0));
                registro.setNombre(cursorRegistros.getString(1));
                registro.setFecha(cursorRegistros.getString(2));
                registro.setHora(cursorRegistros.getString(3));
                listaRegistros.add(registro);
            } while (cursorRegistros.moveToNext());
        }

        cursorRegistros.close();

        return listaRegistros;
    }

    public Registros verRegistro(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Registros registros = null;
        Cursor cursorRegistros;

        cursorRegistros = db.rawQuery("SELECT * FROM " + TABLE_REGISTRO + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorRegistros.moveToFirst()) {
            registros= new Registros();
            registros.setId(cursorRegistros.getInt(0));
            registros.setNombre(cursorRegistros.getString(1));
            registros.setFecha(cursorRegistros.getString(2));
            registros.setHora(cursorRegistros.getString(3));
        }

        cursorRegistros.close();

        return registros;
    }

    public boolean editarRegistro(int id, String nombre, String fecha, String hora) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_REGISTRO + " SET nombre = '" + nombre + "', fecha = '" + fecha + "', hora = '" + hora + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarRegistro(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_REGISTRO + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
