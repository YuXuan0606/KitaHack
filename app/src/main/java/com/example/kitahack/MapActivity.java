package com.example.kitahack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gMap;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);

        // Image button click listeners for navigation
        ImageButton btnEvents = findViewById(R.id.btnEvents);
        ImageButton btnEditVolunteer = findViewById(R.id.btnEditVolunteer);
        ImageButton btnProfile = findViewById(R.id.btnProfile);

        btnEvents.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, EventActivity.class);
            startActivity(intent);
        });

        btnEditVolunteer.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, EditVolunteerActivity.class);
            startActivity(intent);
        });

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, ProfileActivity.class);
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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // Center the map on Malaysia
        gMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                new LatLng(4.2105, 101.9758), 5.5f));

        // Add hunger zone markers
        addHungerMarkers();
    }

    private void addHungerMarkers() {
        HungerZone[] hungerZones = {
                new HungerZone("Sabah", new LatLng(5.9804, 116.0735), "Very High"),
                new HungerZone("Kelantan", new LatLng(6.1254, 102.2381), "High"),
                new HungerZone("Sarawak", new LatLng(1.5533, 110.3592), "High"),
                // Add other zones here
        };

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
}

