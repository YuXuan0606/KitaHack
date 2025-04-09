package com.example.kitahack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FoodDonationOptionActivity extends AppCompatActivity {

    Button btnDropOff, btnPickUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_donation_option);

        btnDropOff = findViewById(R.id.btnDropOff);
        btnPickUp = findViewById(R.id.btnPickUp);

        btnDropOff.setOnClickListener(v -> {
            Intent intent = new Intent(FoodDonationOptionActivity.this, DropOffLocationActivity.class);
            startActivity(intent);
        });

        btnPickUp.setOnClickListener(v -> {
            Intent intent = new Intent(FoodDonationOptionActivity.this, PickUpRequestActivity.class);
            startActivity(intent);
        });
    }
}
