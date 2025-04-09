package com.example.kitahack;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PickUpRequestActivity extends AppCompatActivity {

    private EditText nameInput, addressInput, phoneInput, foodDetailsInput, foodCountField;
    private Button submitPickUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_request);

        nameInput = findViewById(R.id.inputName);
        addressInput = findViewById(R.id.inputAddress);
        phoneInput = findViewById(R.id.inputPhone);
        foodDetailsInput = findViewById(R.id.inputFoodDetails);
        foodCountField = findViewById(R.id.inputFoodItemCount);  // Field to enter the number of food items
        submitPickUpButton = findViewById(R.id.btnSubmitPickUp);

        submitPickUpButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String address = addressInput.getText().toString();
            String phone = phoneInput.getText().toString();
            String foodDetails = foodDetailsInput.getText().toString();
            String foodCountStr = foodCountField.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || foodDetails.isEmpty() || foodCountStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                int foodCount = Integer.parseInt(foodCountStr);

                // Increase the coin balance (5 coins per food item)
                ProfileActivity.addCoin(foodCount * 5);  // Assuming 5 coins per food item
                Toast.makeText(this, "Pick-up requested for " + foodCount + " food items! Coins added: " + (foodCount * 5), Toast.LENGTH_SHORT).show();

                // Add donation to history
                String donationDate = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
                DonationHistoryActivity.addDonation("Food", foodCount + " food items - Pick-up from " + address, donationDate);

                // Optionally reset the food count field
                foodCountField.setText("");
            }
        });
    }
}
