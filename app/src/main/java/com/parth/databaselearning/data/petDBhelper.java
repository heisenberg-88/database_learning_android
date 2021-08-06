package com.parth.databaselearning.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.parth.databaselearning.data.petcontract.petentry;

public class petDBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="shelter.db";

    private static final int DATABASE_VERSION=1;


    public petDBhelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + petentry.TABLE_NAME + "(" +
                petentry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                petentry.COLUMN_PET_NAME+" TEXT NOT NULL, "+
                petentry.COLUMN_PET_BREED+" TEXT, "+
                petentry.COLUMN_PET_GENDER+" INTEGER NOT NULL, "+
                petentry.COLUMN_PET_WEIGHT+" INTEGER NOT NULL DEFAULT 0 ) ";

        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
