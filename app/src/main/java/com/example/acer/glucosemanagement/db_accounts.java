package com.example.acer.glucosemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class db_accounts extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "usersdb";
    private static final String TABLE_Users = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";

    public db_accounts(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_EMAIL + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("password",cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
            user.put("email",cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            userList.add(user);
        }
        return  userList;
    }

    public ArrayList<HashMap<String, String>> GetUserByEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users;
        Cursor cursor = db.query(TABLE_Users,
                new String[]{KEY_ID, KEY_EMAIL, KEY_PASSWORD},
                KEY_EMAIL + "=?",new String[]{String.valueOf(email)},
                null, null, null, null);

        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("email",cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            user.put("password",cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
            userList.add(user);
        }
        return  userList;
    }

    public Boolean checkUser(String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users;
        Cursor cursor = db.query(TABLE_Users,
                new String[]{KEY_ID, KEY_EMAIL, KEY_PASSWORD, KEY_EMAIL},
                KEY_EMAIL + "=?",new String[]{String.valueOf(user)},
                null, null, null, null);

        if  (cursor.getCount() > 0) {
            return false;
        } return true;
    }


    public void insertUserDetails(String password, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_PASSWORD, password);
        cValues.put(KEY_EMAIL, email);
        long newRowId = db.insert(TABLE_Users,null, cValues);
        db.close();
    }
}
