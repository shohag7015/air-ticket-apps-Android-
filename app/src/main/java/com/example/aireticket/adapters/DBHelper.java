package com.example.airticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aireticket.models.Flight;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AirTicket.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "bookings";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "airline TEXT, fromCity TEXT, toCity TEXT," +
                "time TEXT, price TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert Booking
    public boolean insertBooking(Flight flight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("airline", flight.airline);
        values.put("fromCity", flight.from);
        values.put("toCity", flight.to);
        values.put("time", flight.time);
        values.put("price", flight.price);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Get All Bookings
    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
