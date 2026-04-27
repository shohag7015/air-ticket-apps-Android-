package com.example.aireticket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FlightDetailsActivity extends AppCompatActivity {

    private TextView tvAirline, tvFrom, tvTo, tvTime, tvPrice, tvSelectedDate, tvSelectedPassengers, tvSelectedSeats;
    private Button btnProceedToPayment;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);

        tvAirline = findViewById(R.id.tvAirline);
        tvFrom = findViewById(R.id.tvFrom);
        tvTo = findViewById(R.id.tvTo);
        tvTime = findViewById(R.id.tvTime);
        tvPrice = findViewById(R.id.tvPrice);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSelectedPassengers = findViewById(R.id.tvSelectedPassengers);
        tvSelectedSeats = findViewById(R.id.tvSelectedSeats);
        
        btnProceedToPayment = findViewById(R.id.btnBook);
        btnBack = findViewById(R.id.btnBack);

        String airline = getIntent().getStringExtra("airline");
        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String time = getIntent().getStringExtra("time");
        String priceStr = getIntent().getStringExtra("price"); // e.g., "35000 BDT"
        String date = getIntent().getStringExtra("date");
        String passengers = getIntent().getStringExtra("passengers");
        String selectedSeats = getIntent().getStringExtra("selectedSeats");

        // Price Calculation
        int totalPassengers = 1;
        try {
            if (passengers != null) totalPassengers = Integer.parseInt(passengers);
        } catch (Exception e) { }

        long totalPrice = 0;
        String currency = "BDT";
        if (priceStr != null) {
            // "35000 BDT" থেকে শুধু নম্বরটা আলাদা করছি
            String numericPrice = priceStr.replaceAll("[^0-9]", "");
            if (!numericPrice.isEmpty()) {
                totalPrice = Long.parseLong(numericPrice) * totalPassengers;
            }
            if (priceStr.contains("USD") || priceStr.contains("$")) currency = "USD";
        }

        tvAirline.setText(airline);
        tvFrom.setText(from);
        tvTo.setText(to);
        tvTime.setText("Time: " + time);
        tvPrice.setText("Total Price: " + totalPrice + " " + currency);
        
        if(date != null) tvSelectedDate.setText("Journey Date: " + date);
        if(passengers != null) tvSelectedPassengers.setText("Passengers: " + passengers);
        if(selectedSeats != null) tvSelectedSeats.setText("Selected Seats: " + selectedSeats);

        btnBack.setOnClickListener(v -> finish());

        final String finalTotalPrice = totalPrice + " " + currency;
        btnProceedToPayment.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra("airline", airline);
            intent.putExtra("from", from);
            intent.putExtra("to", to);
            intent.putExtra("price", finalTotalPrice);
            intent.putExtra("time", time);
            intent.putExtra("date", date);
            intent.putExtra("passengers", passengers);
            intent.putExtra("selectedSeats", selectedSeats);
            startActivity(intent);
        });
    }
}
