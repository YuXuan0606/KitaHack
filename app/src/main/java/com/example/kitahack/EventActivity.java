package com.example.kitahack;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Button btnDonate = findViewById(R.id.btnDonateEvent);
        Button btnVolunteer = findViewById(R.id.btnVolunteerEvent);

        btnDonate.setOnClickListener(v -> {
            // Open donation selection screen
            Intent intent = new Intent(EventActivity.this, DonateChoiceActivity.class);
            startActivity(intent);
        });

        btnVolunteer.setOnClickListener(v -> {
            // Open volunteer form or info page
            Intent intent = new Intent(EventActivity.this, EditVolunteerActivity.class);
            startActivity(intent);
        });
    }
}