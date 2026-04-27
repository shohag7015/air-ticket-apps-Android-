package com.example.aireticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aireticket.models.Flight;

public class PaymentActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private RadioGroup rgPaymentMethods;
    private LinearLayout layoutCardDetails, layoutBkashDetails;
    private EditText etCardNumber, etExpiry, etCVV, etBkashNumber;
    private TextView tvTotalAmount;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        dbHelper = new DBHelper(this);

        // Initialize UI
        rgPaymentMethods = findViewById(R.id.rgPaymentMethods);
        layoutCardDetails = findViewById(R.id.layoutCardDetails);
        layoutBkashDetails = findViewById(R.id.layoutBkashDetails);
        etCardNumber = findViewById(R.id.etCardNumber);
        etExpiry = findViewById(R.id.etExpiry);
        etCVV = findViewById(R.id.etCVV);
        etBkashNumber = findViewById(R.id.etBkashNumber);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnPay = findViewById(R.id.btnPay);

        // Get Data from Intent
        String airline = getIntent().getStringExtra("airline");
        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String time = getIntent().getStringExtra("time");
        String price = getIntent().getStringExtra("price");
        String date = getIntent().getStringExtra("date");
        String passengers = getIntent().getStringExtra("passengers");
        String selectedSeats = getIntent().getStringExtra("selectedSeats");

        tvTotalAmount.setText("Total Amount: " + price);

        // Payment Method Toggle Logic
        rgPaymentMethods.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbCard) {
                layoutCardDetails.setVisibility(View.VISIBLE);
                layoutBkashDetails.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbBkash) {
                layoutCardDetails.setVisibility(View.GONE);
                layoutBkashDetails.setVisibility(View.VISIBLE);
            }
        });

        btnPay.setOnClickListener(v -> {
            if (validatePayment()) {
                Flight flight = new Flight(airline, from, to, time, price);
                flight.date = date;
                flight.passengers = passengers;
                flight.selectedSeats = selectedSeats;

                boolean isSaved = dbHelper.insertBooking(flight);

                if (isSaved) {
                    Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                    
                    // Navigate to Ticket Activity
                    Intent intent = new Intent(this, TicketActivity.class);
                    intent.putExtra("airline", airline);
                    intent.putExtra("from", from);
                    intent.putExtra("to", to);
                    intent.putExtra("time", time);
                    intent.putExtra("price", price);
                    intent.putExtra("date", date);
                    intent.putExtra("passengers", passengers);
                    intent.putExtra("selectedSeats", selectedSeats);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Database Error: Could not save booking", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validatePayment() {
        int selectedId = rgPaymentMethods.getCheckedRadioButtonId();
        
        if (selectedId == R.id.rbCard) {
            String cardNo = etCardNumber.getText().toString().trim();
            String expiry = etExpiry.getText().toString().trim();
            String cvv = etCVV.getText().toString().trim();
            
            if (cardNo.length() != 16) {
                etCardNumber.setError("Invalid Card Number");
                return false;
            }
            if (expiry.isEmpty()) {
                etExpiry.setError("Required");
                return false;
            }
            if (cvv.length() < 3) {
                etCVV.setError("Invalid CVV");
                return false;
            }
        } else if (selectedId == R.id.rbBkash) {
            String bkashNo = etBkashNumber.getText().toString().trim();
            if (bkashNo.length() != 11) {
                etBkashNumber.setError("Invalid bKash Number");
                return false;
            }
        }
        return true;
    }
}
