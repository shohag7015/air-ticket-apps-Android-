package com.example.aireticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionActivity extends AppCompatActivity {

    private GridLayout seatGrid;
    private TextView tvSeatInfo;
    private Button btnConfirmSeats;
    private ImageButton btnBack;
    private int maxSeats = 1;
    private List<String> selectedSeatsList = new ArrayList<>();
    private String airline, from, to, price, time, date, passengers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        seatGrid = findViewById(R.id.seatGrid);
        tvSeatInfo = findViewById(R.id.tvSeatInfo);
        btnConfirmSeats = findViewById(R.id.btnConfirmSeats);
        btnBack = findViewById(R.id.btnBack);

        airline = getIntent().getStringExtra("airline");
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        price = getIntent().getStringExtra("price");
        time = getIntent().getStringExtra("time");
        date = getIntent().getStringExtra("date");
        passengers = getIntent().getStringExtra("passengers");
        
        if (passengers != null && !passengers.isEmpty()) {
            try {
                maxSeats = Integer.parseInt(passengers);
            } catch (Exception e) {
                maxSeats = 1;
            }
        }
        
        tvSeatInfo.setText("Please select " + maxSeats + " seats");

        // সীট গ্রিড তৈরি করা
        seatGrid.post(() -> createSeatGrid());

        btnBack.setOnClickListener(v -> finish());

        btnConfirmSeats.setOnClickListener(v -> {
            if (selectedSeatsList.size() < maxSeats) {
                Toast.makeText(this, "Please select all " + maxSeats + " seats", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < selectedSeatsList.size(); i++) {
                sb.append(selectedSeatsList.get(i));
                if (i < selectedSeatsList.size() - 1) sb.append(", ");
            }

            Intent intent = new Intent(this, FlightDetailsActivity.class);
            intent.putExtra("airline", airline);
            intent.putExtra("from", from);
            intent.putExtra("to", to);
            intent.putExtra("price", price);
            intent.putExtra("time", time);
            intent.putExtra("date", date);
            intent.putExtra("passengers", passengers);
            intent.putExtra("selectedSeats", sb.toString());
            startActivity(intent);
            finish();
        });
    }

    private void createSeatGrid() {
        int rows = 10;
        int cols = 4;
        seatGrid.removeAllViews();
        
        // গ্রিডের মোট উইডথ অনুযায়ী সীটের উইডথ ক্যালকুলেট করা
        int totalWidth = seatGrid.getMeasuredWidth();
        int seatWidth = (totalWidth / cols) - 20; // ২০ পিক্সেল মার্জিন বা প্যাডিং বাদ দিয়ে

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final String seatName = (char)('A' + i) + "" + (j + 1);
                TextView seat = new TextView(this);
                
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = seatWidth;
                params.height = 120;
                params.setMargins(10, 10, 10, 10);
                seat.setLayoutParams(params);
                
                seat.setBackgroundResource(R.drawable.bg_seat_available);
                seat.setText(seatName);
                seat.setGravity(Gravity.CENTER);
                seat.setTextColor(ContextCompat.getColor(this, R.color.black));
                
                seat.setOnClickListener(v -> {
                    if (selectedSeatsList.contains(seatName)) {
                        selectedSeatsList.remove(seatName);
                        seat.setBackgroundResource(R.drawable.bg_seat_available);
                        seat.setTextColor(ContextCompat.getColor(this, R.color.black));
                    } else {
                        if (selectedSeatsList.size() < maxSeats) {
                            selectedSeatsList.add(seatName);
                            seat.setBackgroundResource(R.drawable.bg_seat_selected);
                            seat.setTextColor(ContextCompat.getColor(this, R.color.white));
                        } else {
                            Toast.makeText(this, "Limit reached: " + maxSeats + " seats", Toast.LENGTH_SHORT).show();
                        }
                    }
                    tvSeatInfo.setText("Selected: " + selectedSeatsList.size() + "/" + maxSeats);
                });

                seatGrid.addView(seat);
            }
        }
    }
}
