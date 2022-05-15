package com.example.fishbowlapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import static java.lang.System.lineSeparator;
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "insidedb5.db";

    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE dataDB(id INTEGER PRIMARY KEY AUTOINCREMENT,ntu INT, ph INT, tem INT)"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dataDB");
        onCreate(db);
    }

    public void insert(int ntu,int ph, int tem) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(String.format("INSERT INTO dataDB (ntu,ph,tem) VALUES('"+ntu+"','"+ph+"','"+tem+"');"));
        db.close();
    }
    public void UpdateNtu(int ntu) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE dataDB SET ntu = "+ntu+"");
        db.close();
    }
    public void UpdatePh(int ph) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE dataDB SET ph = "+ph+"");
        db.close();
    }
    public void UpdateTem(int tem) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE dataDB SET tem = "+tem+"");
        db.close();
    }

    public void ClearNTU() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(String.format("UPDATE dataDB SET (id,ntu,ph,tem) = DEFAULT"));
        db.close();
    }

    public String getResultNtu() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("SELECT ntu FROM dataDB",null);
        while (cursor.moveToNext()) {
            result +=cursor.getInt(0)+ lineSeparator();
        }
        return result;
    }
    public String getResultPh() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("SELECT ph FROM dataDB",null);
        while (cursor.moveToNext()) {
            result +=cursor.getInt(0)+ lineSeparator();
        }
        return result;
    }
    public String getResultTem() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("SELECT tem FROM dataDB",null);
        while (cursor.moveToNext()) {
            result +=cursor.getInt(0)+ lineSeparator();
        }
        return result;
    }

    public int[] latelyTem(int date) {
        SQLiteDatabase db = getReadableDatabase();
        int[] arr = new int[date];
        int i=0;
        Cursor cursor = db.rawQuery("SELECT id,tem FROM dataDB ORDER BY id desc", null);

        while(cursor.moveToNext()) {
            if(i == date) break;
            arr[i] = cursor.getInt(1);
            i++;
        }
        return arr;
    }



    public int[] latelyNtu(int date) {
        SQLiteDatabase db = getReadableDatabase();
        int[] arr = new int[date];
        int i=0;
        Cursor cursor = db.rawQuery("SELECT id,Ntu FROM dataDB ORDER BY id desc", null);

        while(cursor.moveToNext()) {
            if(i == date) break;
            arr[i] = cursor.getInt(1);
            i++;
        }
        return arr;
    }

    public int[] latelyPh(int date) {
        SQLiteDatabase db = getReadableDatabase();
        int[] arr = new int[date];
        int i=0;
        Cursor cursor = db.rawQuery("SELECT id,ph FROM dataDB ORDER BY id desc", null);

        while(cursor.moveToNext()) {
            if(i == date) break;
            arr[i] = cursor.getInt(1);
            i++;
        }
        return arr;
    }
    public String LastNtuResult() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ntu FROM dataDB ORDER BY id desc",null);
        int data;
        String result;
        cursor.moveToFirst();
        data = cursor.getInt(0);
        switch(data/100) {
            case 10:
            case 9: result = "매우 좋음"; break;
            case 8:
            case 7: result = "좋음"; break;
            case 6:
            case 5: result = "보통"; break;
            case 4:
            case 3: result = "나쁨"; break;
            default: result = "매우 나쁨";
        }

        return result;
    }

    public int LastTemResult() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tem FROM dataDB ORDER BY id desc",null);
        int result;
        cursor.moveToFirst();
        result = cursor.getInt(0);

        return result;
    }

    public int LastPhResult() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ph FROM dataDB ORDER BY id desc",null);
        int result;
        cursor.moveToFirst();
        result = cursor.getInt(0);

        return result;
    }

    public int LastNtuResult2() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ntu FROM dataDB ORDER BY id desc",null);
        int result;
        cursor.moveToFirst();
        result = cursor.getInt(0);

        return result;
    }
}
