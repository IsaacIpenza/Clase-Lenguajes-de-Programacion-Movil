package com.example.myfinalproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myfinalproject.models.DebitCard;
import com.example.myfinalproject.models.User;

import java.util.ArrayList;
import java.util.List;

public class DebitCardDB extends SQLiteOpenHelper {
    SQLiteDatabase db;
    private String sqlCreate = "CREATE TABLE DebitCards (dCId VARCHAR, dCName VARCHAR, cardNumber VARCHAR, cardEnding VARCHAR, totalBalance INTEGER, userId VARCHAR)";
    public DebitCardDB(Context contexto, String nameDB, SQLiteDatabase.CursorFactory factory, int
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
        db.execSQL("DROP TABLE DebitCards");
        db.execSQL(sqlCreate);
        System.out.println("Database updated");
    }
    public SQLiteDatabase connect() {
        db = this.getWritableDatabase();
        return db;
    }

    public List<DebitCard> retrieveData() {
        List<DebitCard> debitCardList = new ArrayList<>();

        String query = "SELECT * FROM DebitCards";
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                debitCardList.add(new DebitCard(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getInt(4),
                        c.getString(5)));
            } while (c.moveToNext());
        }

        return debitCardList;
    }

    public void initializeData() {
        db.execSQL("DROP TABLE DebitCards");
        db.execSQL(sqlCreate);
        db.execSQL("INSERT INTO DebitCards (dCId, dCName, cardNumber, cardEnding, totalBalance, userId) " + "VALUES ('DC-01', 'Debit Card', '4481786592815492', '5492', 22500, 'USER-01')");
        db.execSQL("INSERT INTO DebitCards (dCId, dCName, cardNumber, cardEnding, totalBalance, userId) " + "VALUES ('DC-02', 'Account 2', '4478518495632148', '2148', 12000, 'USER-02')");
        db.execSQL("INSERT INTO DebitCards (dCId, dCName, cardNumber, cardEnding, totalBalance, userId) " + "VALUES ('DC-03', 'Money Card', '4481786592815889', '5889', 12250, 'USER-01')");

    }

    public void updateDC(String DCId, int totalBalance) {
        ContentValues values = new ContentValues();
        values.put("totalBalance", totalBalance);
        String where = "dCId = '" + DCId  + "' ";
        db.update("DebitCards", values, where, null);
    }

    public void close() {
        if(db != null) {
            db.close();
        }
    }
}