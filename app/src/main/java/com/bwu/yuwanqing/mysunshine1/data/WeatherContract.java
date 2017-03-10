package com.bwu.yuwanqing.mysunshine1.data;

import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by yuwanqing on 2017-03-10.
 * Defines table and column names for the weather datadase.
 */
public class WeatherContract {
    // To make it easy to query for the exact精确 date, we normalise all dates that go into
    // the database to the start of the the Julian day at UTC世界标准时间.
    /*
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning og the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }
    */
    /*
    Inner class that defines the table contents of the location table
    Students: This is where you will add the strings. (Similar to what has been
    done for WeatherEntry()
     */
    public static final class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "location";
        public static final String LOCATION_ID = "id";
        public static final String LOCATION_NAME = "name";
        public static final String LOCATION_COUNTRY = "country";
        public static final String LOCATION_PATH = "path";
    }
    /* Inner class that defines the table contents of the weather table */
    public static final class WeatherEntry implements BaseColumns {
        public static final String TABLE_NAME = "weather";

        //Column with the foreign key into the location table.
        public static final String COLUMN_LOC_KEY = "location_id";
        //Date, stored as long in milliseconds since the epoch.
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TEXT_DAY = "text_day";
        //Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_CODE_DAY = "code_day";

        //Low and high temperature for the day (stored as floats)
        public static final String COLUMN_LOW = "low";
        public static final String COLUMN_HIGH = "high";

        public static final String COLUMN_WIND_DIRECTION = "wind_direction";
        public static final String COLUMN_WIND_DIRECTION_DEGREE = "wind_direction_degree";
        public static final String COLUMN_WIND_SPEED = "wind_speed";
        public static final String COLUMN_WIND_SCALE = "wind_scale";
    }
}
