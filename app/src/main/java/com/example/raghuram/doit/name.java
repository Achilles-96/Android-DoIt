package com.example.raghuram.doit;

import android.util.Log;

import java.util.Date;

/**
 * Created by raghuram on 20/1/15.
 */
public class name {
    String name;
    int id;
    String date;
    String end_date;

    public name() {
    }

    public name(String _name, int _id) {
        this.name = _name;
        this.id = _id;
    }

    public String getname() {
        return this.name;
    }

    public String getdate() {
        return this.date;
    }

    public String getEnd_date() {
        return this.end_date;
    }

    public int getId() {
        return this.id;
    }

    public void setname(String _name, String _date, String _end_date) {
        this.name = _name;
        this.date = _date;
        this.end_date = _end_date;
        //Log.d("old:",this.date);
        //Log.d("new:",this.end_date);
    }

    public void setId(int _id) {
        this.id = _id;
    }
}