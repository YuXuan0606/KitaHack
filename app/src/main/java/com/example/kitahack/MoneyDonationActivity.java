package com.example.kitahack;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MoneyDonationActivity extends AppCompatActivity {

    private EditText editAmount;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_donation);

        // Initialize views
        TextView tv = findViewById(R.id.donationText);
        editAmount = findViewById(R.id.editAmount);
        btnConfirm = findViewById(R.id.btnConfirmDonation);

        // Set text
        tv.setText("Enter your donation amount below:");

        // Handle button click
        btnConfirm.setOnClickListener(v -> {
            String amountText = editAmount.getText().toString().trim();

            if (!amountText.isEmpty()) {
                int amount = Integer.parseInt(amountText);

                if (amount > 0) {
                    // Update coin balance in ProfileActivity (1 RM = 1 coin)
                    ProfileActivity.addCoin(amount);

                    // Add the donation to the history
                    String type = "Money";
                    String detail = "RM" + amount;
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    DonationHistoryActivity.addDonation(type, detail, date);

                    // Show a success message
                    Toast.makeText(this, "Thank you! You earned " + amount + " coins!", Toast.LENGTH_SHORT).show();

                    // Optionally, go back to the ProfileActivity or DonationHistoryActivity
                    finish(); // Close this screen and return to the previous one
                } else {
                    Toast.makeText(this, "Amount must be greater than 0", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
