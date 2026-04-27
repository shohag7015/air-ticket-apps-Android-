package com.example.aireticket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.UUID;

public class TicketActivity extends AppCompatActivity {

    private TextView tvTicketID, tvAirlineMain, tvAirlineFull, tvFromCode, tvToCode, tvFromName, tvToName, tvDate, tvTime, tvSeats, tvPrice;
    private ImageView ivQRCode, ivBarcode;
    private Button btnDownload;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        // Initialize Views
        tvTicketID = findViewById(R.id.tvTicketID);
        tvAirlineMain = findViewById(R.id.tvAirlineMain);
        tvAirlineFull = findViewById(R.id.tvAirlineFull);
        tvFromCode = findViewById(R.id.tvFromCode);
        tvToCode = findViewById(R.id.tvToCode);
        tvFromName = findViewById(R.id.tvFromName);
        tvToName = findViewById(R.id.tvToName);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvSeats = findViewById(R.id.tvSeats);
        tvPrice = findViewById(R.id.tvPrice);
        ivQRCode = findViewById(R.id.ivQRCode);
        ivBarcode = findViewById(R.id.ivBarcode);
        btnDownload = findViewById(R.id.btnDownload);
        btnBack = findViewById(R.id.btnBack);

        // Get Data from Intent
        String airline = getIntent().getStringExtra("airline");
        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String time = getIntent().getStringExtra("time");
        String price = getIntent().getStringExtra("price");
        String date = getIntent().getStringExtra("date");
        String selectedSeats = getIntent().getStringExtra("selectedSeats");

        // Generate Random Ticket ID (Numeric)
        String ticketId = String.valueOf((long) (Math.random() * 900000000L) + 100000000L);
        
        // Set Data
        tvTicketID.setText(ticketId);
        tvAirlineMain.setText(airline != null ? airline.toUpperCase() : "BIMAN BANGLADESH");
        tvAirlineFull.setText(airline + " Airlines");
        
        if (from != null && from.length() >= 3) tvFromCode.setText(from.substring(0, 3).toUpperCase());
        if (to != null && to.length() >= 3) tvToCode.setText(to.substring(0, 3).toUpperCase());

        tvFromName.setText(from);
        tvToName.setText(to);
        tvDate.setText(date);
        tvTime.setText(time);
        tvSeats.setText(selectedSeats);
        tvPrice.setText(price);

        // Auto Generate QR Code and Barcode
        try {
            Bitmap qrCode = generateQRCode(ticketId);
            ivQRCode.setImageBitmap(qrCode);

            Bitmap barcode = generateBarcode(ticketId);
            ivBarcode.setImageBitmap(barcode);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        btnDownload.setOnClickListener(v -> {
            Toast.makeText(this, "Ticket Downloading...", Toast.LENGTH_SHORT).show();
        });
    }

    private Bitmap generateQRCode(String text) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 300, 300, null);
        } catch (IllegalArgumentException iae) {
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }

    private Bitmap generateBarcode(String text) throws WriterException {
        BitMatrix result;
        try {
            // Code 128 format is good for numeric/alphanumeric ticket IDs
            result = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, 600, 150, null);
        } catch (IllegalArgumentException iae) {
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }
}
