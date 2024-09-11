package com.example.myfinalproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myfinalproject.models.CreditCard;
import com.example.myfinalproject.models.Movements;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class MovementsDB extends SQLiteOpenHelper {
    SQLiteDatabase db;
    private String sqlCreate = "CREATE TABLE Movements (MoveId VARCHAR, CardId VARCHAR, Amount INTEGER, MoveType VARCHAR, MoveDateYear INTEGER, MoveDateMonth INTEGER, MoveDateDay INTEGER, Description VARCHAR)";
    public MovementsDB(Context contexto, String nameDB, SQLiteDatabase.CursorFactory factory, int
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
        db.execSQL("DROP TABLE Movements");
        db.execSQL(sqlCreate);
        System.out.println("Database updated");
    }
    public SQLiteDatabase connect() {
        db = this.getWritableDatabase();
        return db;
    }

    public List<Movements> retrieveData() {
        List<Movements> movementsList = new ArrayList<>();

        String query = "SELECT * FROM Movements";
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                movementsList.add(new Movements(
                        c.getString(0),
                        c.getString(1),
                        c.getInt(2),
                        c.getString(3),
                        LocalDate.of(
                                c.getInt(4),
                                c.getInt(5),
                                c.getInt(6)),
                        c.getString(7)));
            } while (c.moveToNext());
        }

        return movementsList;
    }

    public void initializeData() {
        db.execSQL("DROP TABLE Movements");
        db.execSQL(sqlCreate);
        db.execSQL("INSERT INTO Movements (MoveId, CardId, Amount, MoveType, MoveDateYear, MoveDateMonth, MoveDateDay, Description) " + "VALUES ('M-01', 'DC-01', 2500, 'Pay', 2024, 3, 15, 'Pago 1')");
        db.execSQL("INSERT INTO Movements (MoveId, CardId, Amount, MoveType, MoveDateYear, MoveDateMonth, MoveDateDay, Description) " + "VALUES ('M-02', 'CC-01', 200, 'Pay', 2024, 2, 20, 'Pago 2')");
        db.execSQL("INSERT INTO Movements (MoveId, CardId, Amount, MoveType, MoveDateYear, MoveDateMonth, MoveDateDay, Description) " + "VALUES ('M-03', 'CC-01', 800, 'Deposit', 2024, 1, 8, 'Deposito 1')");
        db.execSQL("INSERT INTO Movements (MoveId, CardId, Amount, MoveType, MoveDateYear, MoveDateMonth, MoveDateDay, Description) " + "VALUES ('M-04', 'CC-02', 1200, 'Pay', 2023, 3, 15, 'Pago 3')");
        db.execSQL("INSERT INTO Movements (MoveId, CardId, Amount, MoveType, MoveDateYear, MoveDateMonth, MoveDateDay, Description) " + "VALUES ('M-05', 'DC-01', 5505, 'Deposit', 2024, 3, 14, 'Deposito 2')");
        db.execSQL("INSERT INTO Movements (MoveId, CardId, Amount, MoveType, MoveDateYear, MoveDateMonth, MoveDateDay, Description) " + "VALUES ('M-06', 'CC-03', 5506, 'Deposit', 2024, 3, 14, 'Deposito 3')");

    }

    public void insertMove(String MoveId, String CardId, int Amount, String MoveType, int MoveDateYear, int MoveDateMonth, int MoveDateDay, String Description) {

        ContentValues values = new ContentValues();
        values.put("MoveId", MoveId);
        values.put("CardId", CardId);
        values.put("Amount", Amount);
        values.put("MoveType", MoveType);
        values.put("MoveDateYear", MoveDateYear);
        values.put("MoveDateMonth", MoveDateMonth);
        values.put("MoveDateDay", MoveDateDay);
        values.put("Description", Description);
        db.insert("Movements", null, values);
    }

    public void close() {
        if(db != null) {
            db.close();
        }
    }
}
