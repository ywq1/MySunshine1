package com.bwu.yuwanqing.mysunshine1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.lang.String;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import com.bwu.yuwanqing.mysunshine1.data.WeatherContract.LocationEntry;
import com.bwu.yuwanqing.mysunshine1.data.WeatherContract.WeatherEntry;

/**
 * Created by yuwanqing on 2017-03-10.
 * Manages a local database for weather data.
 */
public class WeatherDbHelper extends SQLiteOpenHelper{
    //If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "weather.bd";
    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + LocationEntry.TABLE_NAME + " (" +
                LocationEntry._ID + " INTEGER PRIMARY KEY, " +
                LocationEntry.LOCATION_ID + " TEXT, " +
                LocationEntry.LOCATION_NAME + " TEXT, " +
                LocationEntry.LOCATION_COUNTRY + " TEXT, " +
                LocationEntry.LOCATION_PATH + " TEXT);";
        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + WeatherEntry.TABLE_NAME + " (" +
                WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WeatherEntry.COLUMN_LOC_KEY + "INTEGER NOT NULL" +
                WeatherEntry.COLUMN_DATE + "DATE NOT NULL, " +
                WeatherEntry.COLUMN_TEXT_DAY + "TEXT NOT NULL" +
                WeatherEntry.COLUMN_CODE_DAY + "INTEGER NOT NULL, " +
                WeatherEntry.COLUMN_HIGH + "REAL NOT NULL, " +
                WeatherEntry.COLUMN_LOW + "REAL NOT NULL, " +
                WeatherEntry.COLUMN_WIND_DIRECTION + "TEXT NOT NULL, " +
                WeatherEntry.COLUMN_WIND_DIRECTION_DEGREE + "INTEGER, " +
                WeatherEntry.COLUMN_WIND_SCALE + "INTEGER, "+
                " FOREIGN KEY (" + WeatherEntry.COLUMN_LOC_KEY + ") REFERENCES " +
                LocationEntry.TABLE_NAME + " (" + LocationEntry._ID + "), " +
                " UNIQUE (" + WeatherEntry.COLUMN_DATE + ", " +
                WeatherEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
