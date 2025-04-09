package com.example.kitahack;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DropOffLocationActivity extends AppCompatActivity {

    private Spinner locationSpinner;
    private EditText foodCountField;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_off_location);

        locationSpinner = findViewById(R.id.spinnerLocations);
        foodCountField = findViewById(R.id.editFoodItemCount);  // Field to enter number of food items
        confirmButton = findViewById(R.id.btnConfirmDropOff);

        String[] dropOffPoints = {
                "Food Bank Malaysia, Selangor",
                "Kuala Lumpur Community Centre",
                "Kelantan Food Distribution Hub",
                "Terengganu Donation Station",
                "Sabah Hunger Relief Center"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dropOffPoints);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        confirmButton.setOnClickListener(v -> {
            String selected = locationSpinner.getSelectedItem().toString();
            String foodCountStr = foodCountField.getText().toString().trim();

            if (foodCountStr.isEmpty()) {
                Toast.makeText(this, "Please enter the number of food items!", Toast.LENGTH_SHORT).show();
            } else {
                int foodCount = Integer.parseInt(foodCountStr);

                // Increase the coin balance (5 coins per food item)
                ProfileActivity.addCoin(foodCount * 5);  // Assuming 5 coins per food item
                Toast.makeText(this, "Donated " + foodCount + " food items! Coins added: " + (foodCount * 5), Toast.LENGTH_SHORT).show();

                // Add donation to history
                String donationDate = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
                DonationHistoryActivity.addDonation("Food", foodCount + " food items - Drop-off at " + selected, donationDate);

                // Optionally reset the food count field
                foodCountField.setText("");
            }
        });
    }
}
