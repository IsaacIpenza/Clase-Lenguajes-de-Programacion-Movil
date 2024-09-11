package com.example.myfinalproject.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myfinalproject.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDB extends SQLiteOpenHelper {
    SQLiteDatabase db;
    private String sqlCreate = "CREATE TABLE Users (userId VARCHAR, name VARCHAR, email VARCHAR, password VARCHAR)";
    public UserDB(Context contexto, String nameDB, SQLiteDatabase.CursorFactory factory, int
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
        db.execSQL("DROP TABLE Users");
        db.execSQL(sqlCreate);
        System.out.println("Database updated");
    }
    public SQLiteDatabase connect() {
        db = this.getWritableDatabase();
        return db;
    }

    public List<User> retrieveData() {
        List<User> userlist = new ArrayList<>();

        String query = "SELECT * FROM Users";
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                userlist.add(new User(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3)));
            } while (c.moveToNext());
        }
        return userlist;
    }

    public void initializeData() {
        db.execSQL("DROP TABLE Users");
        db.execSQL(sqlCreate);
        db.execSQL("INSERT INTO Users (userId, name, email, password) " + "VALUES ('USER-01', 'Isaac', 'ipenza@ibm.com', 'hola')");
        db.execSQL("INSERT INTO Users (userId, name, email, password) " + "VALUES ('USER-02', 'Benjamin', 'benjamin.ipre@gmail.com', 'hola1234')");
    }

    public void close() {
        if(db != null) {
            db.close();
        }
    }
}