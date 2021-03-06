package com.apps.geo.notes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 1038844 on 04.01.2017.
 */

public class DBHelper extends SQLiteOpenHelper implements DBConstants{

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +  POINT_INFO + "("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "description text,"
                + "term long,"
                + "latitude real,"
                + "longitude real,"
                + "radius real,"
                + "active integer default 1,"
                + "date long"+ ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1)
        {
            db.execSQL("alter table " + POINT_INFO + " add column is_active integer");
        }
    }
}
