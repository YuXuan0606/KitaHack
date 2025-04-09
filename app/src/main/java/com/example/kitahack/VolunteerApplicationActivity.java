package com.example.kitahack;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VolunteerApplicationActivity extends AppCompatActivity {

    private Spinner spinnerRole;
    private CalendarView calendarView;
    private Button btnSubmitApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_application);

        spinnerRole = findViewById(R.id.spinnerRole);
        calendarView = findViewById(R.id.calendarView);
        btnSubmitApplication = findViewById(R.id.btnSubmitApplication);

        // Define a list of roles
        String[] roles = {"Food Distribution", "Admin Support", "Driver"};

        // Set up an adapter to display roles in the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        // Set up a button click listener to submit the application
        btnSubmitApplication.setOnClickListener(v -> {
            String selectedRole = spinnerRole.getSelectedItem().toString();
            long selectedDate = calendarView.getDate(); // Get the selected date

            if (selectedRole.isEmpty()) {
                Toast.makeText(VolunteerApplicationActivity.this, "Please select a role", Toast.LENGTH_SHORT).show();
            } else if (selectedDate == 0) {
                Toast.makeText(VolunteerApplicationActivity.this, "Please select an availability date", Toast.LENGTH_SHORT).show();
            } else {
                // Save the selected role and availability date in SharedPreferences
                SharedPreferences prefs = getSharedPreferences("user_profile", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                // Generate a unique key for the new application (could use a counter or timestamp)
                String applicationKey = "volunteer_application_" + System.currentTimeMillis();  // Using timestamp as unique key

                // Save the selected role and availability for this application
                editor.putString(applicationKey + "_role", selectedRole);
                editor.putLong(applicationKey + "_availability_date", selectedDate);
                editor.apply();

                Toast.makeText(VolunteerApplicationActivity.this, "Application Submitted for Role: " + selectedRole, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
