package com.example.aireticket.models;

public class Flight {
    public String airline, from, to, time, price, date, passengers, selectedSeats;

    public Flight() {}

    public Flight(String airline, String from, String to, String time, String price) {
        this.airline = airline;
        this.from = from;
        this.to = to;
        this.time = time;
        this.price = price;
    }
}
