package com.example.aireticket;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aireticket.adapters.BookingAdapter;
import com.example.aireticket.models.Flight;
import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private List<Flight> bookingList;
    private DBHelper dbHelper;
    private TextView tvNoBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        recyclerView = findViewById(R.id.recyclerView);
        tvNoBookings = findViewById(R.id.tvNoBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);
        bookingList = new ArrayList<>();
        
        loadBookingsFromSQLite();

        adapter = new BookingAdapter(bookingList, this);
        recyclerView.setAdapter(adapter);
    }

    private void loadBookingsFromSQLite() {
        bookingList.clear();
        Cursor cursor = dbHelper.getAllBookings();
        
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Flight flight = new Flight();
                // Column indices should match the order in DBHelper.getAllBookings() rawQuery
                // or use cursor.getColumnIndex()
                flight.airline = cursor.getString(cursor.getColumnIndexOrThrow("airline"));
                flight.from = cursor.getString(cursor.getColumnIndexOrThrow("fromCity"));
                flight.to = cursor.getString(cursor.getColumnIndexOrThrow("toCity"));
                flight.time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                flight.price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                flight.date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                flight.passengers = cursor.getString(cursor.getColumnIndexOrThrow("passengers"));
                flight.selectedSeats = cursor.getString(cursor.getColumnIndexOrThrow("selectedSeats"));
                
                bookingList.add(flight);
            } while (cursor.moveToNext());
            cursor.close();
        }

        if (bookingList.isEmpty()) {
            tvNoBookings.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoBookings.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void goBack(View view) {
        finish();
    }
}
