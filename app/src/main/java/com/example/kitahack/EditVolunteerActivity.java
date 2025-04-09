package com.example.kitahack;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

public class EditVolunteerActivity extends AppCompatActivity {

    private Spinner spinnerRole;
    private CalendarView calendarView;
    private Button btnSave;
    private TextView txtSelectedDate;
    private Spinner spinnerApplication;  // Spinner to select which application to edit
    private String selectedActivityId; // To store which application is being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_volunteer);

        spinnerRole = findViewById(R.id.spinnerRole);
        calendarView = findViewById(R.id.calendarView);
        btnSave = findViewById(R.id.btnSave);
        txtSelectedDate = findViewById(R.id.txtSelectedDate);
        spinnerApplication = findViewById(R.id.spinnerApplication); // Spinner to choose application to edit

        // Define a list of roles
        String[] roles = {"Food Distribution", "Admin Support", "Driver"};

        // Set up an adapter to display roles in the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        // Set up a button click listener to save edited data
        btnSave.setOnClickListener(v -> {
            String selectedRole = spinnerRole.getSelectedItem().toString();
            long selectedDate = calendarView.getDate(); // Get the selected date

            if (selectedRole.isEmpty()) {
                Toast.makeText(EditVolunteerActivity.this, "Please select a role", Toast.LENGTH_SHORT).show();
            } else if (selectedDate == 0) {
                Toast.makeText(EditVolunteerActivity.this, "Please select an availability date", Toast.LENGTH_SHORT).show();
            } else {
                // Save the updated volunteer details in SharedPreferences
                SharedPreferences prefs = getSharedPreferences("user_profile", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                // Save the selected role and availability for the chosen application
                String applicationKey = selectedActivityId; // Use the unique ID for the selected application
                editor.putString(applicationKey + "_role", selectedRole);
                editor.putLong(applicationKey + "_availability_date", selectedDate);
                editor.apply();

                Toast.makeText(EditVolunteerActivity.this, "Volunteer details updated!", Toast.LENGTH_SHORT).show();
            }
        });

        // Load existing volunteer applications into the spinner for selection
        loadVolunteerApplications();
    }

    // Load volunteer data from SharedPreferences based on the selected application
    private void loadVolunteerData(String applicationKey) {
        SharedPreferences prefs = getSharedPreferences("user_profile", MODE_PRIVATE);

        // Retrieve the volunteer role and availability for the selected application
        String volunteerRole = prefs.getString(applicationKey + "_role", "No role assigned");
        long availabilityDate = prefs.getLong(applicationKey + "_availability_date", 0);

        // Debug: Log the retrieved data
        System.out.println("Loaded Volunteer Data: Role - " + volunteerRole + ", Date - " + availabilityDate);

        // Set the role in the spinner
        int position = ((ArrayAdapter<String>) spinnerRole.getAdapter()).getPosition(volunteerRole);
        spinnerRole.setSelection(position);

        // Set the availability date in the calendar
        if (availabilityDate != 0) {
            calendarView.setDate(availabilityDate);
        }
    }

    // Load all volunteer applications from SharedPreferences into the spinner
    private void loadVolunteerApplications() {
        SharedPreferences prefs = getSharedPreferences("user_profile", MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();  // Retrieve all entries from SharedPreferences

        // Find all volunteer application keys and load them into the spinner
        ArrayList<String> applicationKeys = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            if (key.contains("volunteer_application")) {  // Check for keys related to volunteer applications
                applicationKeys.add(key);
            }
        }

        // Set up the spinner with the list of application keys
        ArrayAdapter<String> applicationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, applicationKeys);
        applicationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApplication.setAdapter(applicationAdapter);

        // Set a listener to load the selected application
        spinnerApplication.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedActivityId = applicationKeys.get(position); // Get the key for the selected application
                loadVolunteerData(selectedActivityId); // Load data for the selected application
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when no item is selected (optional)
            }
        });
    }
}
