package com.azhar.catering.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.azhar.catering.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GMaps extends FragmentActivity implements OnMapReadyCallback {
    // Implement OnMapReadyCallback.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.activity_gmaps);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.locMaps);  // Menggunakan ID yang benar
        mapFragment.getMapAsync(this);
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Menambahkan marker pada koordinat yang diberikan
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(-0.533212, 117.123817))  // Koordinat yang diberikan
                .title("Marker in Location"));

//        // Menyesuaikan kamera untuk menampilkan marker dengan zoom yang pas
//        googleMap.moveCamera(CAMERA_SERVICE.transform(new LatLng(-0.533212, 117.123817), 10));
    }
}
