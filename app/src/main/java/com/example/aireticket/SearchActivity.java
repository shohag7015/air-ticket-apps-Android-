package com.example.aireticket;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aireticket.adapters.FlightAdapter;
import com.example.aireticket.models.Flight;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlightAdapter adapter;
    private List<Flight> allFlights;
    private List<Flight> filteredFlights;
    private EditText etFrom, etTo, etDate, etPassengers;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);
        etDate = findViewById(R.id.etDate);
        etPassengers = findViewById(R.id.etPassengers);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etDate.setOnClickListener(v -> showDatePicker());

        allFlights = new ArrayList<>();
        allFlights.add(new Flight("Biman Bangladesh", "Dhaka", "Dubai", "10:00 AM", "35000 BDT"));
        allFlights.add(new Flight("US-Bangla", "Dhaka", "Kuala Lumpur", "02:00 PM", "30000 BDT"));
        allFlights.add(new Flight("Emirates", "Dubai", "New York", "08:00 PM", "85000 BDT"));
        allFlights.add(new Flight("Air India", "Dhaka", "Kolkata", "11:00 AM", "12000 BDT"));
        allFlights.add(new Flight("Qatar Airways", "Dhaka", "Doha", "05:00 AM", "45000 BDT"));

        filteredFlights = new ArrayList<>(allFlights);
        adapter = new FlightAdapter(filteredFlights, this);
        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String from = etFrom.getText().toString().trim();
            String to = etTo.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String passengers = etPassengers.getText().toString().trim();

            if (from.isEmpty() || to.isEmpty() || date.isEmpty() || passengers.isEmpty()) {
                Toast.makeText(this, "Please fill all journey details", Toast.LENGTH_SHORT).show();
                return;
            }

            filterFlights(from, to, date, passengers);
        });
    }

    private void filterFlights(String from, String to, String date, String passengers) {
        filteredFlights.clear();
        for (Flight flight : allFlights) {
            if (flight.from.equalsIgnoreCase(from) && flight.to.equalsIgnoreCase(to)) {
                // এখানে ইউজারের ইনপুট করা তথ্যগুলো ফ্লাইটের সাথে যুক্ত করে দিচ্ছি
                flight.date = date;
                flight.passengers = passengers;
                filteredFlights.add(flight);
            }
        }

        if (filteredFlights.isEmpty()) {
            Toast.makeText(this, "No flights found for this route", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            etDate.setText(selectedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
