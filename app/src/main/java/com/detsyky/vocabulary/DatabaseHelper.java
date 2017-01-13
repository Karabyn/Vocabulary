package com.detsyky.vocabulary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by naliv on 13.01.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "vocabular";
    public final static String TABLE_NAME = "WORDS";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_TRANSLATE = "translate";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(" + COLUMN_ID
        + " integer primary key," + COLUMN_WORD + " text," + COLUMN_TRANSLATE + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
