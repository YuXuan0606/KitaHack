package com.example.kitahack;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageButton btnEvents = findViewById(R.id.btnEvents);
        ImageButton btnEditVolunteer = findViewById(R.id.btnEditVolunteer);
        ImageButton btnProfile = findViewById(R.id.btnProfile);

        // Event button click listener
        btnEvents.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            startActivity(intent);
        });

        // Edit Volunteer button click listener
        btnEditVolunteer.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditVolunteerActivity.class);
            startActivity(intent);
        });

        // Profile button click listener
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Map setup
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);

        // Edge to edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Donate food button click listener
        findViewById(R.id.btnDonateFood).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodDonationOptionActivity.class);
            startActivity(intent);
        });

        // Donate money button click listener
        findViewById(R.id.btnDonateMoney).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MoneyDonationActivity.class);
            startActivity(intent);
        });

        //Become Volunteer button click listener
        findViewById(R.id.btnBecomeVolunteer).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VolunteerApplicationActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        // Center the map on Malaysia
        gMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                new com.google.android.gms.maps.model.LatLng(4.2105, 101.9758), 5.5f));

        // Add hunger zone markers
        addHungerMarkers();

        gMap.setOnInfoWindowClickListener(marker -> {
            String stateName = marker.getTitle();
            showDonationDialog(stateName);
        });
    }

    private void addHungerMarkers() {
        java.util.List<HungerZone> hungerZones = java.util.Arrays.asList(
                new HungerZone("Sabah", new LatLng(5.9804, 116.0735), "Very High"),
                new HungerZone("Kelantan", new LatLng(6.1254, 102.2381), "High"),
                new HungerZone("Sarawak", new LatLng(1.5533, 110.3592), "High"),
                new HungerZone("Terengganu", new LatLng(5.3117, 103.1324), "Moderate-High"),
                new HungerZone("Perlis", new LatLng(6.4440, 100.2048), "Moderate-High"),
                new HungerZone("Kedah", new LatLng(6.1184, 100.3685), "Moderate"),
                new HungerZone("Pahang", new LatLng(3.8126, 103.3256), "Moderate"),
                new HungerZone("Negeri Sembilan", new LatLng(2.7258, 101.9424), "Low-Moderate"),
                new HungerZone("Johor", new LatLng(1.4854, 103.7618), "Low"),
                new HungerZone("Penang", new LatLng(5.4164, 100.3327), "Low"),
                new HungerZone("Melaka", new LatLng(2.1896, 102.2501), "Low"),
                new HungerZone("Selangor", new LatLng(3.0738, 101.5183), "Very Low"),
                new HungerZone("Kuala Lumpur", new LatLng(3.1390, 101.6869), "Very Low"),
                new HungerZone("Labuan", new LatLng(5.2831, 115.2308), "Very Low"),
                new HungerZone("Putrajaya", new LatLng(2.9264, 101.6964), "Very Low")
        );

        for (HungerZone zone : hungerZones) {
            float color;
            switch (zone.riskLevel) {
                case "Very High":
                    color = BitmapDescriptorFactory.HUE_RED;
                    break;
                case "High":
                    color = BitmapDescriptorFactory.HUE_ORANGE;
                    break;
                case "Moderate-High":
                    color = BitmapDescriptorFactory.HUE_YELLOW;
                    break;
                case "Moderate":
                    color = BitmapDescriptorFactory.HUE_CYAN;
                    break;
                case "Low-Moderate":
                    color = BitmapDescriptorFactory.HUE_AZURE;
                    break;
                case "Low":
                    color = BitmapDescriptorFactory.HUE_GREEN;
                    break;
                case "Very Low":
                    color = BitmapDescriptorFactory.HUE_BLUE;
                    break;
                default:
                    color = BitmapDescriptorFactory.HUE_VIOLET;
            }

            gMap.addMarker(new MarkerOptions()
                    .position(zone.latLng)
                    .title(zone.state)
                    .snippet("Hunger Risk: " + zone.riskLevel)
                    .icon(BitmapDescriptorFactory.defaultMarker(color))
            );
        }
    }

    private void showDonationDialog(String stateName) {
        android.widget.EditText input = new android.widget.EditText(this);
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setHint("Enter amount (RM)");

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Donate to " + stateName)
                .setMessage("How much would you like to donate?")
                .setView(input)
                .setPositiveButton("Donate", (dialog, which) -> {
                    String amountStr = input.getText().toString().trim();
                    if (!amountStr.isEmpty()) {
                        android.widget.Toast.makeText(MainActivity.this,
                                "Donated RM" + amountStr + " to " + stateName + " ❤️",
                                android.widget.Toast.LENGTH_LONG).show();
                    } else {
                        android.widget.Toast.makeText(MainActivity.this,
                                "Please enter a valid amount!",
                                android.widget.Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}

class HungerZone {
    String state;
    LatLng latLng;
    String riskLevel;

    HungerZone(String state, LatLng latLng, String riskLevel) {
        this.state = state;
        this.latLng = latLng;
        this.riskLevel = riskLevel;
    }
}
