package com.example.kitahack;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class VolunteerAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> volunteerApplications;

    public VolunteerAdapter(Context context, List<String> volunteerApplications) {
        super(context, R.layout.item_volunteer, volunteerApplications);
        this.context = context;
        this.volunteerApplications = volunteerApplications;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_volunteer, parent, false);
        }

        String applicationId = volunteerApplications.get(position);
        SharedPreferences prefs = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE);

        // Retrieve role and availability for each application
        String role = prefs.getString(applicationId + "_role", "No role");
        String availability = prefs.getString(applicationId + "_availability", "Not Set");

        TextView txtVolunteerDetails = convertView.findViewById(R.id.txtVolunteerDetails);
        txtVolunteerDetails.setText("Role: " + role + "\nAvailability: " + availability);

        return convertView;
    }
}
