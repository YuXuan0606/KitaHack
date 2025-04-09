package com.example.kitahack;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyVolunteerActivity extends AppCompatActivity {

    private TextView txtVolunteerDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_volunteer);

        txtVolunteerDetails = findViewById(R.id.txtVolunteerDetails);

        // Load volunteer details for the first time when the activity is created
        loadVolunteerDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVolunteerDetails();  // Ensure the data is refreshed when the activity resumes
    }

    private void loadVolunteerDetails() {
        SharedPreferences prefs = getSharedPreferences("user_profile", MODE_PRIVATE);

        // Retrieve updated volunteer role and availability
        String volunteerRole = prefs.getString("volunteer_role", "No role assigned");
        long availabilityDate = prefs.getLong("availability_date", 0);

        // Convert the availability date to a string (if available)
        String availability = "Not Set";
        if (availabilityDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            availability = sdf.format(new Date(availabilityDate));
        }

        // Display the volunteer role and availability
        String details = "Role: " + volunteerRole;
        details += "\nAvailability: " + availability;

        txtVolunteerDetails.setText(details);  // Update the text view with the fetched data
    }
}
