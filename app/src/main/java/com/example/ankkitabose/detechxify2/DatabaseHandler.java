package com.example.ankkitabose.detechxify2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by rishabh on 06/02/17.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDB.db";
    public static final String TABLE_USER = "user_table";
    public static final String TABLE_APPS = "app_table";
    //column for checklist table
    public static final String APP_NAME = "app_name";
    public static final String DURATION = "duration";
    public static final String STARTTIME = "start";

    //columns for note/label table
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + TABLE_USER +
                        "(" + USERNAME + " text primary key, " + PASSWORD + " text, " + EMAIL + " text);"
        );

        db.execSQL(
                "create table " + TABLE_APPS +
                        "(" + APP_NAME + " text primary key, " + DURATION + " integer, " + STARTTIME + " integer);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPS);
        onCreate(db);
    }


    public boolean insertUser(String username, String password, String email) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USERNAME, username);
        contentValues.put(PASSWORD, password);
        contentValues.put(EMAIL, email);

        db.insert(TABLE_USER, null, contentValues);
        return true;
    }

    public Cursor getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_USER + " where " + USERNAME + "=\"" + username + "\";", null);
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_USER + " where " + EMAIL + "=\"" + email + "\";", null);
    }
    /*
        public int numberOfRows() {
            SQLiteDatabase db = this.getReadableDatabase();
            return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        }
    */
    public boolean updateUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, username);
        contentValues.put(PASSWORD, password);
        contentValues.put(EMAIL, email);
        db.update(TABLE_USER, contentValues, USERNAME + " = ? ", new String[]{username});
        return true;
    }

    public Integer deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER,
                USERNAME + " = ? ",
                new String[]{username});
    }


    public boolean insertApp(String name, int duration, long start) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(APP_NAME, name);
        contentValues.put(DURATION, duration);
        contentValues.put(STARTTIME, start);

        db.insert(TABLE_APPS, null, contentValues);
        return true;
    }

    public Cursor getAppData(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_APPS + " where " + APP_NAME + "=\"" + name + "\";", null);
    }

    public Cursor getAllApps() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_APPS +";", null);
    }

    public int size() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_APPS);
    }

    public boolean updateApp(String name, int duration, long start) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(APP_NAME, name);
        contentValues.put(DURATION, duration);
        contentValues.put(STARTTIME, start);
        db.update(TABLE_APPS, contentValues, APP_NAME + " = ? ", new String[]{name});
        return true;
    }

    public Integer deleteApp(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_APPS,
                APP_NAME + " = ? ",
                new String[]{name});
    }
 /*
    public Integer deleteLabel(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_LABEL + " = ? ",
                new String[]{label});
    }

    public ArrayList<String> getAllLabelsofAType(String type) {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_TYPE + "=\"" + type + "\" ORDER BY " + COLUMN_LABEL + ";", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            array_list.add(res.getString(res.getColumnIndex(COLUMN_LABEL)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }


    public ArrayList<String> getAllLabels() {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select distinct " + COLUMN_LABEL + " from " + TABLE_NAME + " order by " + COLUMN_LABEL + ";", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            array_list.add(res.getString(res.getColumnIndex(COLUMN_LABEL)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }


    public boolean searchLabel(String label) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean find = false;
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + ";", null);

        res.moveToFirst();
        while(!res.isAfterLast()){

            if( label.equals(res.getString(res.getColumnIndex(COLUMN_LABEL)))){
               // find = res.getString(res.getColumnIndex(COLUMN_TYPE));
                find = true;
                break;
            }
            res.moveToNext();
        }
        res.close();

        return find;

    }
    //new function
    public boolean searchCheckListLabel(String label) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean find = false;
        Cursor res = db.rawQuery("select * from " + TABLE_CHECKLIST + ";", null);
        System.out.println("Going in here");
        res.moveToFirst();
        while(!res.isAfterLast()){

            if( label.equals(res.getString(res.getColumnIndex(COLUMN_ITEM)))){
                // find = res.getString(res.getColumnIndex(COLUMN_TYPE));
                find = true;
                break;
            }
            res.moveToNext();
        }
        res.close();
        System.out.println("Coming out");
        return find;
    }

    public String getDescription(String label){

        SQLiteDatabase db = this.getReadableDatabase();
        String description = new String();
        Cursor res = db.rawQuery( "select * from " + TABLE_NAME + " WHERE " + COLUMN_LABEL + " = \"" + label + "\";", null );
        //meaning = res.getString(0);
        res.moveToFirst();
        while(!res.isAfterLast()){

            description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
            res.moveToNext();
        }
        res.close();

        return description;
        //return res.getString(res.getColumnIndex(COLUMN_MEANING));
    }


*/

}
