package com.example.aireticket;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class AireTicketApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
