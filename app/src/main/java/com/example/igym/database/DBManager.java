package com.example.igym.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.igym.model.Actividad;
import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {
    public static final String TABLA_ACTIVIDAD = "actividades";
    public static final String DB_NAME = "actividadesDB";
    public static final int DB_VERSION = 7;
    public static final String NOMBRE = "nombre";
    public static final String DESCRIP = "descrip";
    public static final String TARIFAS = "tarifas";
    public static final String HORARIOS = "horarios";
    public static final String SELECCIONADA = "seleccionada"; // Nuevo campo

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBManager", NOMBRE + " creating: " + TABLA_ACTIVIDAD);

        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLA_ACTIVIDAD + "("
                    + NOMBRE + " string(255) PRIMARY KEY NOT NULL,"
                    + DESCRIP + " string(20) NOT NULL,"
                    + HORARIOS + " text NOT NULL,"
                    + TARIFAS + " double(20) NOT NULL,"
                    + SELECCIONADA + " INTEGER NOT NULL DEFAULT 0)" // Añade el campo seleccionada
            );
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.onCreate", exc.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBManager", DB_NAME + " " + oldVersion + " -> " + newVersion);

        try {
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_ACTIVIDAD);
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.onUpgrade", exc.getMessage());
        } finally {
            db.endTransaction();
        }

        this.onCreate(db);
    }

    public void add(String name, String descri, String horarios, double tarifa, int seleccionada) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues values = new ContentValues();

        values.put(NOMBRE, name);
        values.put(DESCRIP, descri);
        values.put(HORARIOS, horarios);
        values.put(TARIFAS, tarifa);
        values.put(SELECCIONADA, seleccionada); // Añade el valor para seleccionada

        try {
            db.beginTransaction();
            cursor = db.query(TABLA_ACTIVIDAD,
                    new String[]{NOMBRE},
                    NOMBRE + " = ?", new String[]{name},
                    null, null, null, "1");

            if (cursor.getCount() > 0) {
                db.update(TABLA_ACTIVIDAD, values,
                        NOMBRE + " = ?", new String[]{name});
            } else {
                db.insert(TABLA_ACTIVIDAD, null, values);
            }

            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("dbAdd", exc.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            db.endTransaction();
        }
    }
    public void edit(String name, String descri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NOMBRE, name);
        values.put(DESCRIP, descri);

        try {
            db.beginTransaction();
            db.update(TABLA_ACTIVIDAD, values,
                    NOMBRE + " = ?", new String[]{name});
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("dbEdit", exc.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void remove(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete(TABLA_ACTIVIDAD, NOMBRE + " = ?", new String[]{name});
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("dbRemove", exc.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<String> obtenerNombresActividades() {
        ArrayList<String> nombres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + NOMBRE + " FROM " + TABLA_ACTIVIDAD, null);

        if (cursor.moveToFirst()) {
            do {
                nombres.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return nombres;
    }

    public boolean estaSeleccionada(String nombreActividad) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLA_ACTIVIDAD, new String[]{SELECCIONADA}, NOMBRE + " = ?", new String[]{nombreActividad}, null, null, null);

        boolean seleccionada = false;
        if (cursor.moveToFirst()) {
            seleccionada = cursor.getInt(0) == 1;
        }
        cursor.close();
        return seleccionada;
    }

    public void toggleSeleccionada(String nombreActividad, boolean seleccionada) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SELECCIONADA, seleccionada ? 1 : 0);

        db.update(TABLA_ACTIVIDAD, values, NOMBRE + " = ?", new String[]{nombreActividad});
    }

    public ArrayList<Actividad> obtenerTodasLasActividades() {
        ArrayList<Actividad> listaActividades = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_ACTIVIDAD, null);

        if (cursor != null && cursor.moveToFirst()) {
            int indexNombre = cursor.getColumnIndex(NOMBRE);
            int indexDescrip = cursor.getColumnIndex(DESCRIP);
            int indexHorarios = cursor.getColumnIndex(HORARIOS);
            int indexTarifas = cursor.getColumnIndex(TARIFAS);

            // Verifica si los índices de las columnas son válidos
            if (indexNombre != -1 && indexDescrip != -1 && indexHorarios != -1 && indexTarifas != -1) {
                do {
                    String nombre = cursor.getString(indexNombre);
                    String descrip = cursor.getString(indexDescrip);
                    String horarios = cursor.getString(indexHorarios);
                    double tarifas = cursor.getDouble(indexTarifas);

                    listaActividades.add(new Actividad(nombre, descrip, horarios, tarifas));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return listaActividades;
    }

    }

