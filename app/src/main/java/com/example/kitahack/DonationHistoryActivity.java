package com.example.kitahack;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class DonationHistoryActivity extends AppCompatActivity {

    public static class Donation {
        public String type;  // "Money" or "Food"
        public String detail; // e.g. "RM10" or "5 food packs"
        public String date;

        public Donation(String type, String detail, String date) {
            this.type = type;
            this.detail = detail;
            this.date = date;
        }

        @Override
        public String toString() {
            return type + " Donation: " + detail + " on " + date;
        }
    }

    // List to store donations
    public static List<Donation> donationsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history);

        // Example: Populate your ListView here using the donationsList
        ListView listView = findViewById(R.id.donationListView);

        // Use an adapter to display the donation history
        DonationAdapter adapter = new DonationAdapter(this, donationsList);
        listView.setAdapter(adapter);
    }

    // Method to add donation to the history
    public static void addDonation(String type, String detail, String date) {
        donationsList.add(new Donation(type, detail, date));
    }
}
