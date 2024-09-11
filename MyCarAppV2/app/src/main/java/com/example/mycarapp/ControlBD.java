package com.example.mycarapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ControlBD extends SQLiteOpenHelper {
    SQLiteDatabase db;
    private String sqlCreate = "CREATE TABLE Cars (id INTEGER, brand VARCHAR, name VARCHAR, model VARCHAR, cylinders VARCHAR, price VARCHAR, image INTEGER)";
    public ControlBD(Context contexto, String nameDB, CursorFactory factory, int
            version) {
        super(contexto, nameDB, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    //The SQL statement to create the table is executed
        db.execSQL(sqlCreate);
        System.out.println("Database created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva)
    {
        db.execSQL("DROP TABLE Cars");
        db.execSQL(sqlCreate);
        System.out.println("Database updated");
    }
    public SQLiteDatabase connect() {
        db = this.getWritableDatabase();
        return db;
    }

    public List<CarsCatalog> insertData() {
        List<CarsCatalog> catalog = new ArrayList<>();

        catalog.add(new CarsCatalog("CAR-01","Toyota","Rav4","2024",4,"$700,000",R.drawable.rav4));
        catalog.add(new CarsCatalog("CAR-02","Volkswagen","Passat","2024",6,"$800,000",R.drawable.passat));
        catalog.add(new CarsCatalog("CAR-03","Nissan","Sentra","2010",4,"$400,000",R.drawable.sentra));
        catalog.add(new CarsCatalog("CAR-04","Toyota","Corolla","2022",6,"$500,000",R.drawable.corolla));

        return catalog;
    }

    public void close() {
        if(db != null) {
            db.close();
        }
    }
}
