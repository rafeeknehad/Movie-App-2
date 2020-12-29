package com.example.moviesapp.javaClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    public static final String dataBaseName = "movies.db";

    public static final String dataTable = "usersApp";
    public static final String idKey = "ID";
    public static final String userName = "USERNAME";
    public static final String email = "EMAIL";
    public static final String password = "PASSWORD";


    public DataBase(@Nullable Context context) {
        super(context, dataBaseName, null, 1);
        System.out.println("haaaaaaaa");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("haaaaaaaa111");
        db.execSQL("create table "+dataTable+" " +
                "(ID integer primary key autoincrement , " +
                "USERNAME text , " +
                "EMAIL text ," +
                "PASSWORD text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userApp");
        onCreate(db);
    }

    public boolean insertData(String mUserName, String mEmail, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(userName, mUserName);
        contentValues.put(email, mEmail);
        contentValues.put(password, pass);

        long result = db.insert(dataTable, null, contentValues);
        if (result == -1)
            return false;
        return true;
    }

    public ArrayList<String> getAllRecord() {
        ArrayList<String> allData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + dataTable, null);
        while (res.moveToNext()) {
            String id = res.getString(0);
            String name = res.getString(1);
            String email = res.getString(2);
            String pass = res.getString(3);

            String row = id + "//" + name + "//" + email + "//" + pass;
            allData.add(row);
        }

        return allData;
    }

    public Cursor allData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + dataTable, null);
        return res;
    }

    public boolean updateData(String mUserName, String mEmail, String mPass) {
        int id = findId(mUserName);
        if (id == 0) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(userName, mUserName);
        contentValues.put(email, mEmail);
        contentValues.put(password, mPass);

        SQLiteDatabase db = this.getWritableDatabase();
        String mId = String.valueOf(id);
        db.update(dataTable, contentValues, "ID = ?", new String[]{mId});
        return true;
    }

    public boolean deleteData(String name) {
        int id = findId(name);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(dataTable, "ID = ?", new String[]{String.valueOf(id)});
        return true;
    }

    public int findId(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + dataTable, null);
        while (res.moveToNext()) {
            if (res.getString(1).equals(name)) {
                return Integer.valueOf(res.getString(0));
            }
        }
        return 0;
    }

    public boolean checkUser(String userNamePa,String pass)
    {
        Cursor res = allData();
        while (res.moveToNext())
        {
            if(res.getString(1).equals(userNamePa) && res.getString(3).equals(pass))
            {
                return true;
            }
        }
        return false;
    }
}
