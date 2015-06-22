package com.example.raghuram.doit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghuram on 20/1/15.
 */
public class databasehandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "feed";
    public static final String TABLE_NAME = "goals";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DATE = "date";
    public static final String KEY_END_DATE = "end_date";


    public databasehandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT, " + KEY_DATE + " TEXT, " + KEY_END_DATE + " TEXT " + " ) ";
        db.execSQL(CREATE_TABLE);
        Log.d("created:", "created table");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void clearit() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.d("drop:", "dropped table goals");
        onCreate(db);

    }

    public ArrayList<name> listall() {
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
        return list;
    }

    public void addGoal(name Name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Name.getname());
        values.put(KEY_DATE, Name.getdate());
        values.put(KEY_END_DATE, Name.getEnd_date());
        Log.d("old:", Name.getdate());
        Log.d("new:", Name.getEnd_date());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public name getGoal(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_DATE, KEY_END_DATE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        name Name = new name();
        Name.setname(cursor.getString(1), cursor.getString(2), cursor.getString(3));
        Name.setId(Integer.parseInt(cursor.getString(0)));
        return Name;
    }
}