package com.example.myfinalproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myfinalproject.models.CreditCard;
import com.example.myfinalproject.models.DebitCard;

import java.util.ArrayList;
import java.util.List;

public class CreditCardDB extends SQLiteOpenHelper {
    SQLiteDatabase db;
    private String sqlCreate = "CREATE TABLE CreditCards (cCId VARCHAR, cCName VARCHAR, cardNumber VARCHAR, cardEnding VARCHAR, totalCredit INTEGER, debt INTEGER, userId VARCHAR)";
    public CreditCardDB(Context contexto, String nameDB, SQLiteDatabase.CursorFactory factory, int
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
        db.execSQL("DROP TABLE CreditCards");
        db.execSQL(sqlCreate);
        System.out.println("Database updated");
    }
    public SQLiteDatabase connect() {
        db = this.getWritableDatabase();
        return db;
    }

    public List<CreditCard> retrieveData() {
        List<CreditCard> creditCardList = new ArrayList<>();

        String query = "SELECT * FROM CreditCards";
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                creditCardList.add(new CreditCard(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getInt(4),
                        c.getInt(5),
                        c.getString(6)));
            } while (c.moveToNext());
        }

        return creditCardList;
    }

    public void initializeData() {
        db.execSQL("DROP TABLE CreditCards");
        db.execSQL(sqlCreate);
        db.execSQL("INSERT INTO CreditCards (cCId, cCName, cardNumber, cardEnding, totalCredit, debt, userId ) " + "VALUES ('CC-01', 'IBIR Card', '5589452381957862', '7862', 30000, 15800, 'USER-01')");
        db.execSQL("INSERT INTO CreditCards (cCId, cCName, cardNumber, cardEnding, totalCredit, debt, userId ) " + "VALUES ('CC-02', 'WHITE Card', '5584219562843211', '3211', 6000, 150, 'USER-01')");
        db.execSQL("INSERT INTO CreditCards (cCId, cCName, cardNumber, cardEnding, totalCredit, debt, userId ) " + "VALUES ('CC-03', 'WHITE Card', '5584219562843215', '3215', 6000, 150, 'USER-02')");
    }

    public void updateCC(String CCId, int Debt) {
        ContentValues values = new ContentValues();
        values.put("debt", Debt);
        String where = "cCId = '" + CCId +"' ";
        db.update("CreditCards", values, where, null);
    }

    public void close() {
        if(db != null) {
            db.close();
        }
    }
}