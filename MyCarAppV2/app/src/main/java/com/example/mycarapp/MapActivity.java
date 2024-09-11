package com.example.mycarapp;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng o1 = new LatLng(20.678764, -103.421854);
        LatLng o2 = new LatLng(20.862240, -103.239685);
        LatLng o3 = new LatLng(20.289859, -103.192812);
        LatLng o4 = new LatLng(20.802668, -102.758596);


        Marker marker1 = mMap.addMarker(new MarkerOptions().position(o1).title("Sucursal 1 - Matriz"));
        marker1.showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(o2).title("Sucursal 2"));
        mMap.addMarker(new MarkerOptions().position(o3).title("Sucursal 3"));
        mMap.addMarker(new MarkerOptions().position(o4).title("Sucursal 4"));

        LatLng camaraPosition = new LatLng(20.572053, -103.148712);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camaraPosition, 9));
    }
}
