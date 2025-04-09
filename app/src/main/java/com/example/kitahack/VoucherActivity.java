package com.example.kitahack;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoucherActivity extends AppCompatActivity {

    TextView txtCoinBalance, txtRedeemedVouchers;
    Button btnRedeem5, btnRedeem10;

    // List to store redeemed vouchers
    List<String> redeemedVouchers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        // Initialize UI components
        txtCoinBalance = findViewById(R.id.txtCoinBalance);
        txtRedeemedVouchers = findViewById(R.id.txtRedeemedVouchers);  // TextView for displaying redeemed vouchers
        btnRedeem5 = findViewById(R.id.btnRedeem5);
        btnRedeem10 = findViewById(R.id.btnRedeem10);

        // Update the coin balance text
        updateCoinText();

        // Handle Redeem RM5 voucher
        btnRedeem5.setOnClickListener(v -> {
            attemptVoucherRedemption(50, "RM5 Voucher");
        });

        // Handle Redeem RM10 voucher
        btnRedeem10.setOnClickListener(v -> {
            attemptVoucherRedemption(100, "RM10 Voucher");
        });
    }

    // General method to handle voucher redemption
    private void attemptVoucherRedemption(int voucherAmount, String voucherName) {
        if (ProfileActivity.coinBalance >= voucherAmount) {
            showConfirmationDialog(voucherAmount, voucherName);
        } else {
            Toast.makeText(this, "Not enough coins!", Toast.LENGTH_SHORT).show();
        }
    }

    // Show a confirmation dialog before redeeming the voucher
    private void showConfirmationDialog(int voucherAmount, String voucherName) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Redeem Voucher")
                .setMessage("Are you sure you want to redeem " + voucherName + " for " + voucherAmount + " coins?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    ProfileActivity.coinBalance -= voucherAmount;
                    redeemedVouchers.add(generateVoucherCode());
                    Toast.makeText(this, voucherName + " redeemed!", Toast.LENGTH_SHORT).show();
                    updateCoinText();
                    updateRedeemedVouchers();  // Update the redeemed vouchers section
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Method to update the coin balance on the UI
    private void updateCoinText() {
        txtCoinBalance.setText("Your Coin Balance: " + ProfileActivity.coinBalance);
    }

    // Method to update the list of redeemed vouchers
    private void updateRedeemedVouchers() {
        StringBuilder vouchers = new StringBuilder();
        for (String voucher : redeemedVouchers) {
            vouchers.append("Voucher Code: ").append(voucher).append("\n");
        }
        txtRedeemedVouchers.setText("Redeemed Vouchers:\n" + vouchers.toString());
    }

    // Generate a unique voucher code
    private String generateVoucherCode() {
        // Create a unique voucher code (e.g., using UUID)
        return "VOUCHER" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
