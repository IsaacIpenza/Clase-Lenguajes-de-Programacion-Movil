package com.example.ejemplosqlite;

import android.content.Context;import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlBD extends SQLiteOpenHelper
{
    SQLiteDatabase db;
    private String sqlCreate = "CREATE TABLE Usuarios (codigo INTEGER, nombre TEXT)";

    public ControlBD(Context contexto, String nombre,CursorFactory factory, int version)
    {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Se ejecuta la sentencia SQL de creacion de la tabla
        db.execSQL(sqlCreate);
        System.out.println("Base de datos creada");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva)
    {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        db.execSQL(sqlCreate);
        System.out.println("Base de datos actualizada");
    }

    public SQLiteDatabase conectar()
    {
        db = this.getWritableDatabase();
        return db;
    }

    public void insertarUsuario(int codigo, String nombre)
    {
        db.execSQL("INSERT INTO Usuarios (codigo, nombre) " + "VALUES (" + codigo + ", '" + nombre +"')");
        System.out.println("Usuario insertado");
    }

    public void cerrar()
    {
        if(db != null)
        {
            db.close();
        }
    }

}
