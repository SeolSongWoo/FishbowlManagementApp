package com.example.fishbowlapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "insidedb.db";

    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE dataDB(ntu INTEGER, ph INTEGER, tem INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dataDB");
        onCreate(db);
    }

    public void insertNtu(int ntu) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO dataDB VALUES('"+ntu+"')");
        db.close();
    }
    public void insertPh(int ph) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO dataDB VALUES('"+ph+"')");
        db.close();
    }
    public void insertTem(int INT) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO dataDB VALUES('"+INT+"')");
        db.close();
    }

    public void UpdateNtu(int ntu) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE dbdata SET time = "+ntu+"");
        db.close();
    }
    public void UpdatePh(int ph) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE dbdata SET time = "+ph+"");
        db.close();
    }
    public void UpdateTem(int tem) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE dbdata SET time = "+tem+"");
        db.close();
    }
    public String getResult() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("SELECT * FROM dbdata",null);
        while (cursor.moveToNext()) {
            result +="time =" + cursor.getInt(0)+" minute ="+cursor.getInt(1);
        }
        return result;
    }
}
