package com.example.kitahack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DonateChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_choice);

        Button btnMoney = findViewById(R.id.btnDonateMoneyChoice);
        Button btnFood = findViewById(R.id.btnDonateFoodChoice);

        btnMoney.setOnClickListener(v -> {
            // Navigate to money donation page
            Intent intent = new Intent(DonateChoiceActivity.this, MoneyDonationActivity.class);
            startActivity(intent);
        });

        btnFood.setOnClickListener(v -> {
            // Navigate to food donation option (drop-off or pick-up)
            Intent intent = new Intent(DonateChoiceActivity.this, FoodDonationOptionActivity.class);
            startActivity(intent);
        });
    }
}