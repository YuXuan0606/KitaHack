package com.example.kitahack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtCoinBalance;
    private Button btnDonationHistory;
    private Button btnMyVouchers;
    private Button btnReceipts;
    private Button btnEditProfile;
    private Button btnVolunteerActivity;

    // New text views for user info
    private TextView txtUserName, txtUserEmail, txtUserPhone, txtUserAddress;

    public static int coinBalance = 0;

    public static void addCoin(int coins) {
        coinBalance += coins;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        txtCoinBalance = findViewById(R.id.txtCoinBalance);
        btnVolunteerActivity = findViewById(R.id.btnVolunteerActivity);
        btnDonationHistory = findViewById(R.id.btnDonationHistory);
        btnMyVouchers = findViewById(R.id.btnMyVouchers);
        btnReceipts = findViewById(R.id.btnReceipts);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        // New: Link UI elements
        txtUserName = findViewById(R.id.txtUserName);
        txtUserEmail = findViewById(R.id.txtUserEmail);
        txtUserPhone = findViewById(R.id.txtUserPhone);
        txtUserAddress = findViewById(R.id.txtUserAddress);

        // Load user profile info from SharedPreferences
        loadUserProfile();

        if (txtCoinBalance != null) {
            txtCoinBalance.setText("Coins: " + coinBalance);
        }

        if (btnDonationHistory != null) {
            btnDonationHistory.setOnClickListener(v ->
                    startActivity(new Intent(this, DonationHistoryActivity.class)));
        }

        if (btnMyVouchers != null) {
            btnMyVouchers.setOnClickListener(v ->
                    startActivity(new Intent(this, VoucherActivity.class)));
        }

        if (btnReceipts != null) {
            btnReceipts.setOnClickListener(v ->
                    startActivity(new Intent(this, ReceiptsActivity.class)));
        }

        if (btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v ->
                    startActivity(new Intent(this, EditProfileActivity.class)));
        }
        if (btnVolunteerActivity != null) {
            btnVolunteerActivity.setOnClickListener(v ->
                    startActivity(new Intent(this, MyVolunteerActivity.class)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (txtCoinBalance != null) {
            txtCoinBalance.setText("Coins: " + coinBalance);
        }
        // Reload user info in case it's updated
        loadUserProfile();
    }

    private void loadUserProfile() {
        SharedPreferences prefs = getSharedPreferences("user_profile", MODE_PRIVATE);
        String name = prefs.getString("name", "No name");
        String email = prefs.getString("email", "No email");
        String phone = prefs.getString("phone", "No phone");
        String address = prefs.getString("address", "No address");

        if (txtUserName != null) txtUserName.setText("Name: " + name);
        if (txtUserEmail != null) txtUserEmail.setText("Email: " + email);
        if (txtUserPhone != null) txtUserPhone.setText("Phone: " + phone);
        if (txtUserAddress != null) txtUserAddress.setText("Address: " + address);
    }
}
