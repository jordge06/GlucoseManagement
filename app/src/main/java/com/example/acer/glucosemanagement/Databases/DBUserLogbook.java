package com.example.acer.glucosemanagement.Databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;

public class DBUserLogbook extends SQLiteOpenHelper {

    private static final int DB_VERSION = 4;
    private static final String DB_NAME = "userLogbook";
    private static final String TABLE_Users = "usersData";


    // Columns
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIL = "userEmail";
    public static final String KEY_DATE = "userDate";
    public static final String KEY_TIME = "userTime";
    public static final String KEY_SUGAR_LEVEL = "userSugarLevel";
    public static final String KEY_SYSTOLIC_PRESSURE = "userSystolic";
    public static final String KEY_DIASTOLIC_PRESSURE = "useDiastolic";
    public static final String KEY_A1C_LEVEL = "userA1cLevel";
    public static final String KEY_WEIGHT = "userWeight";
    public static final String KEY_PILLS = "userPills";
    public static final String KEY_PILLS_AMOUNT = "userPillsAmount";
    public static final String KEY_EVENT = "userEvent";
    public static final String KEY_NOTES = "userNotes";
    public static final String KEY_TAGS = "userTags";

    public DBUserLogbook(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Create Query

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_TIME + " TEXT,"
                + KEY_SUGAR_LEVEL + " TEXT,"
                + KEY_SYSTOLIC_PRESSURE + " TEXT,"
                + KEY_DIASTOLIC_PRESSURE + " TEXT,"
                + KEY_A1C_LEVEL + " TEXT,"
                + KEY_WEIGHT + " TEXT,"
                + KEY_PILLS + " TEXT,"
                + KEY_PILLS_AMOUNT + " TEXT,"
                + KEY_EVENT + " TEXT,"
                + KEY_NOTES + " TEXT,"
                + KEY_TAGS + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);

    }

    // Ignore OnCreate Method if Table Name is already existed

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        onCreate(db);
    }

    // Fetch the All User Data From Database

//    public ArrayList<HashMap<String, String>> getAllUserData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ArrayList<HashMap<String, String>> userData = new ArrayList<>();
//        String query = "SELECT * FROM " + TABLE_Users;
//        Cursor cursor = db.rawQuery(query, null);
//
//        while (cursor.moveToNext()) {
//            HashMap<String, String> user = new HashMap<>();
//
//            user.put(KEY_ID, cursor.getString(cursor.getColumnIndex(KEY_ID)));
//            user.put(KEY_EMAIL, cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
//            user.put("userDate", cursor.getString(cursor.getColumnIndex(KEY_DATE)));
//            user.put("userTime", cursor.getString(cursor.getColumnIndex(KEY_TIME)));
//            user.put("userSugarLevel", cursor.getString(cursor.getColumnIndex(KEY_SUGAR_LEVEL)));
//            user.put("userBloodPressure", cursor.getString(cursor.getColumnIndex(KEY_BLOOD_PRESSURE)));
//            user.put("userA1CLevel", cursor.getString(cursor.getColumnIndex(KEY_A1C_LEVEL)));
//            user.put("userWeight", cursor.getString(cursor.getColumnIndex(KEY_WEIGHT)));
//            user.put("userPills", cursor.getString(cursor.getColumnIndex(KEY_PILLS)));
//            user.put("userPillsAmount", cursor.getString(cursor.getColumnIndex(KEY_PILLS_AMOUNT)));
//            user.put("userInsulin", cursor.getString(cursor.getColumnIndex(KEY_INSULIN)));
//            user.put("userEvent", cursor.getString(cursor.getColumnIndex(KEY_EVENT)));
//            user.put("userNotes", cursor.getString(cursor.getColumnIndex(KEY_NOTES)));
//            user.put("userTags", cursor.getString(cursor.getColumnIndex(KEY_TAGS)));
//
//            userData.add(user);
//        }
//
//        // Added line of Code, Delete if encounter an Error
//        cursor.close();
//
//        return userData;
//    }

    public ArrayList<String> getDates() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<String> datesList = new ArrayList<>();
        String query = "SELECT " + KEY_DATE + " FROM " + TABLE_Users +
            " GROUP BY " + KEY_DATE + " HAVING COUNT (*) >= 1 ORDER BY " + KEY_DATE + " DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        while (cursor.moveToNext()) {
            datesList.add(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
        }
        cursor.close();
        return datesList;

    }

//    public ArrayList<String> dates() {
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ArrayList<String> datesList = new ArrayList<>();
//        String query = "SELECT " + KEY_DATE + " FROM " + TABLE_Users + " ORDER BY " + KEY_DATE + " DESC";
//
//
//        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
//
//        while (cursor.moveToNext()) {
//            datesList.add(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
//        }
//
//        cursor.close();
//        return datesList;
//    }

    public ArrayList<HashMap<String, String>> getSugarLevel() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        String query = "SELECT " + KEY_SUGAR_LEVEL + " FROM " + TABLE_Users;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        while (cursor.moveToNext()) {

            HashMap<String, String> userData = new HashMap<>();
            userData.put(KEY_SUGAR_LEVEL, cursor.getString(cursor.getColumnIndex(KEY_SUGAR_LEVEL)));

            data.add(userData);
        }
        cursor.close();

        return data;
    }

    public ArrayList<HashMap<String, String>> getSystolic() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        String query = "SELECT " + KEY_SYSTOLIC_PRESSURE + " FROM " + TABLE_Users;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        while (cursor.moveToNext()) {

            HashMap<String, String> userData = new HashMap<>();
            userData.put(KEY_SYSTOLIC_PRESSURE, cursor.getString(cursor.getColumnIndex(KEY_SYSTOLIC_PRESSURE)));

            data.add(userData);
        }
        cursor.close();

        return data;
    }

    public ArrayList<HashMap<String, String>> getDiastolic() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        String query = "SELECT " + KEY_DIASTOLIC_PRESSURE + " FROM " + TABLE_Users;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        while (cursor.moveToNext()) {

            HashMap<String, String> userData = new HashMap<>();
            userData.put(KEY_DIASTOLIC_PRESSURE, cursor.getString(cursor.getColumnIndex(KEY_DIASTOLIC_PRESSURE)));

            data.add(userData);
        }
        cursor.close();

        return data;
    }

    public ArrayList<HashMap<String, String>> getA1CLevel() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        String query = "SELECT " + KEY_A1C_LEVEL + " FROM " + TABLE_Users;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        while (cursor.moveToNext()) {

            HashMap<String, String> userData = new HashMap<>();
            userData.put(KEY_A1C_LEVEL, cursor.getString(cursor.getColumnIndex(KEY_A1C_LEVEL)));

            data.add(userData);
        }
        cursor.close();

        return data;
    }

    public ArrayList<HashMap<String, String>> getUserByDate(String date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userData = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_Users + " WHERE " + KEY_DATE + " = "+ "'" + date + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();

            user.put(KEY_ID, cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put(KEY_EMAIL, cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            user.put(KEY_DATE, cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            user.put(KEY_TIME, cursor.getString(cursor.getColumnIndex(KEY_TIME)));
            user.put(KEY_SUGAR_LEVEL, cursor.getString(cursor.getColumnIndex(KEY_SUGAR_LEVEL)));
            user.put(KEY_SYSTOLIC_PRESSURE, cursor.getString(cursor.getColumnIndex(KEY_SYSTOLIC_PRESSURE)));
            user.put(KEY_DIASTOLIC_PRESSURE, cursor.getString(cursor.getColumnIndex(KEY_DIASTOLIC_PRESSURE)));
            user.put(KEY_A1C_LEVEL, cursor.getString(cursor.getColumnIndex(KEY_A1C_LEVEL)));
            user.put(KEY_WEIGHT, cursor.getString(cursor.getColumnIndex(KEY_WEIGHT)));
            user.put(KEY_PILLS, cursor.getString(cursor.getColumnIndex(KEY_PILLS)));
            user.put(KEY_PILLS_AMOUNT, cursor.getString(cursor.getColumnIndex(KEY_PILLS_AMOUNT)));
            user.put(KEY_EVENT, cursor.getString(cursor.getColumnIndex(KEY_EVENT)));
            user.put(KEY_NOTES, cursor.getString(cursor.getColumnIndex(KEY_NOTES)));
            user.put(KEY_TAGS, cursor.getString(cursor.getColumnIndex(KEY_TAGS)));

            userData.add(user);
        }

        cursor.close();

        return userData;
    }

    // Insert User Inputted Data into the Database

    public void insertUserData(String email, String date, String time, String sugarLevel,
                               String systolic, String diastolic, String a1cLevel, String weight,
                               String pills, String pillsAmount, String event, String notes, String tags) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_EMAIL, email);
        contentValues.put(KEY_DATE, date);
        contentValues.put(KEY_TIME, time);
        contentValues.put(KEY_SUGAR_LEVEL, sugarLevel);
        contentValues.put(KEY_SYSTOLIC_PRESSURE, systolic);
        contentValues.put(KEY_DIASTOLIC_PRESSURE, diastolic);
        contentValues.put(KEY_A1C_LEVEL, a1cLevel);
        contentValues.put(KEY_WEIGHT, weight);
        contentValues.put(KEY_PILLS, pills);
        contentValues.put(KEY_PILLS_AMOUNT, pillsAmount);
        contentValues.put(KEY_EVENT, event);
        contentValues.put(KEY_NOTES, notes);
        contentValues.put(KEY_TAGS, tags);

        db.insert(TABLE_Users, null, contentValues);
        db.close();
    }

    // Update User Data from Database

    public int UpdateUserData(String sugarLevel, String systolic, String diastolic, String a1cLevel, String weight,
                              String pills, String pillsAmount, String event, String notes, String tags,
                                    int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_SUGAR_LEVEL, sugarLevel);
        contentValues.put(KEY_SYSTOLIC_PRESSURE, systolic);
        contentValues.put(KEY_DIASTOLIC_PRESSURE, diastolic);
        contentValues.put(KEY_A1C_LEVEL, a1cLevel);
        contentValues.put(KEY_WEIGHT, weight);
        contentValues.put(KEY_PILLS, pills);
        contentValues.put(KEY_PILLS_AMOUNT, pillsAmount);
        contentValues.put(KEY_EVENT, event);
        contentValues.put(KEY_NOTES, notes);
        contentValues.put(KEY_TAGS, tags);

        //int count = db.update(TABLE_Users, contentValues, KEY_ID +" = ?",new String[]{String.valueOf(id)});
        //return  count;

        // Added line of Code, Replace if encounter an Error
        //return db.update(TABLE_Users, contentValues, KEY_ID +" = ?",new String[]{String.valueOf(id)});
        return db.update(TABLE_Users, contentValues, KEY_ID +" = ?",new String[]{String.valueOf(id)});
    }

    // Delete User Data from Database

    public void deleteUserData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID +" = ?",new String[]{String.valueOf(id)});
        db.close();
    }
}
