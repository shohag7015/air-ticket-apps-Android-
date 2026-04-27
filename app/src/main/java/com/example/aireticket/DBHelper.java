package com.example.aireticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.aireticket.models.Flight;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AireTicket.db";
    private static final int DB_VERSION = 2;

    // User table
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PASS = "password";

    // Booking table
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COL_BOOK_ID = "id";
    private static final String COL_BOOK_AIRLINE = "airline";
    private static final String COL_BOOK_FROM = "fromCity";
    private static final String COL_BOOK_TO = "toCity";
    private static final String COL_BOOK_TIME = "time";
    private static final String COL_BOOK_PRICE = "price";
    private static final String COL_BOOK_DATE = "date";
    private static final String COL_BOOK_PASSENGERS = "passengers";
    private static final String COL_BOOK_SEATS = "selectedSeats";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_NAME + " TEXT, " +
                COL_USER_EMAIL + " TEXT UNIQUE, " +
                COL_USER_PASS + " TEXT)";

        String createBookingTable = "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                COL_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BOOK_AIRLINE + " TEXT, " +
                COL_BOOK_FROM + " TEXT, " +
                COL_BOOK_TO + " TEXT, " +
                COL_BOOK_TIME + " TEXT, " +
                COL_BOOK_PRICE + " TEXT, " +
                COL_BOOK_DATE + " TEXT, " +
                COL_BOOK_PASSENGERS + " TEXT, " +
                COL_BOOK_SEATS + " TEXT)";

        db.execSQL(createUserTable);
        db.execSQL(createBookingTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }

    // --- User Methods ---
    public boolean registerUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, name);
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PASS, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USER_EMAIL + "=? AND " + COL_USER_PASS + "=?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // --- Booking Methods ---
    public boolean insertBooking(Flight flight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BOOK_AIRLINE, flight.airline);
        values.put(COL_BOOK_FROM, flight.from);
        values.put(COL_BOOK_TO, flight.to);
        values.put(COL_BOOK_TIME, flight.time);
        values.put(COL_BOOK_PRICE, flight.price);
        values.put(COL_BOOK_DATE, flight.date);
        values.put(COL_BOOK_PASSENGERS, flight.passengers);
        values.put(COL_BOOK_SEATS, flight.selectedSeats);
        long result = db.insert(TABLE_BOOKINGS, null, values);
        return result != -1;
    }

    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_BOOKINGS + " ORDER BY " + COL_BOOK_ID + " DESC", null);
    }
}
