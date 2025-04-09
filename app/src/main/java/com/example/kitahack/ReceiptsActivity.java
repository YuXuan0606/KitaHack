package com.example.kitahack;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class ReceiptsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        ListView receiptList = findViewById(R.id.listReceipts);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1
        );

        for (DonationHistoryActivity.Donation d : DonationHistoryActivity.donationsList) {
            adapter.add(d.toString());
        }

        receiptList.setAdapter(adapter);
    }
}
