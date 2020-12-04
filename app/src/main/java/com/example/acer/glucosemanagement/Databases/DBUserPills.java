package com.example.acer.glucosemanagement.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;

public class DBUserPills extends SQLiteOpenHelper {

    public static final int DB_VERSION1 = 3;
    private static final String DB_NAME = "userPills";
    private static final String TABLE_Users = "usersPillsData";

    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "userEmail";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_PILLS = "userPills";

    public DBUserPills(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_Users + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_EMAIL + " TEXT," +
                KEY_USER_ID + " TEXT," +
                KEY_PILLS + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        onCreate(db);
    }

    public void insertAllPillsData(String email, ArrayList<String> pills) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int i = 0;
        for (String s : pills) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_EMAIL, email);
            contentValues.put(KEY_PILLS, s);
            contentValues.put(KEY_USER_ID, i);

            sqLiteDatabase.insert(TABLE_Users, null, contentValues);
            i++;
        }
        sqLiteDatabase.close();
    }

    public void deleteAllPills(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_Users,  KEY_EMAIL +" = ?",new String[]{String.valueOf(email)});
    }

    public void deletePill(int position) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_Users,  KEY_USER_ID +" = ?",new String[]{String.valueOf(position)});
    }

    public ArrayList<String> getAllPillsData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> userPillsData = new ArrayList<>();
        String query = "SELECT "+ KEY_PILLS +" FROM " + TABLE_Users;

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            userPillsData.add(cursor.getString(cursor.getColumnIndex(KEY_PILLS)));
        }

        cursor.close();

        return userPillsData;
    }

    public ArrayList<HashMap<String, String>> getAllPills() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userPillsData = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_Users;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            HashMap<String, String> data = new HashMap<>();
            data.put("userPills", cursor.getString(cursor.getColumnIndex(KEY_PILLS)));
            data.put("userId", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            data.put("user", cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
            userPillsData.add(data);
        }

        cursor.close();

        return userPillsData;
    }
}
