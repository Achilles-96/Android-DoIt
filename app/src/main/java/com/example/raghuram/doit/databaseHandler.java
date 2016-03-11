package com.example.raghuram.doit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Raghuram Vadapalli on 20/1/15.
 */
public class databaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "feed";
    public static final String TABLE_NAME = "goals";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DATE = "date";
    public static final String KEY_END_DATE = "end_date";
    private static final String TAG = databaseHandler.class.getSimpleName();

    public databaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS" + TABLE_NAME + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT, "
                + KEY_DATE + " TEXT, "
                + KEY_END_DATE + " TEXT " +
                " ) ";
        db.execSQL(CREATE_TABLE);
        Log.i(TAG, "Created table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void clearDB() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(TAG, "Cleared DB");
    }

    public ArrayList<name> listAllTasksFromDB() {
        ArrayList<name> list = new ArrayList<name>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                name Name = new name();
                Name.setId(Integer.parseInt(cursor.getString(0)));
                Name.setname(cursor.getString(1), cursor.getString(2), cursor.getString(3));
                list.add(Name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.i(TAG, "Fetched task list from DB");
        return list;
    }

    public void addNewGoal(name Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Name.getname());
        values.put(KEY_DATE, Name.getdate());
        values.put(KEY_END_DATE, Name.getEnd_date());
        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.i(TAG, "Added new goal");
    }

    public name getGoalFromDB(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        KEY_ID,
                        KEY_NAME,
                        KEY_DATE,
                        KEY_END_DATE
                }, KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );
        name Name = new name();
        if( cursor!=null && cursor.moveToFirst() ) {
            Name.setname(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            Name.setId(Integer.parseInt(cursor.getString(0)));
            cursor.close();
        }
        Log.i(TAG, "Fetched goal with ID" + id + "from DB");
        return Name;
    }
}