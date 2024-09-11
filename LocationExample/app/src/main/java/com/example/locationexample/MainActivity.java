package com.example.locationexample;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import android.os.Build;
import androidx.core.app.ActivityCompat;

public class MainActivity extends Activity {
    private Button btnUpdate;
    private Button btnDeactivate;
    private TextView lblLatitud;
    private TextView lblLongitud;
    private TextView lblPrecision;
    private TextView lblState;
    private LocationManager locManager;
    private LocationListener locListener;
    final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUpdate = (Button) findViewById(R.id.BtnUpdate);
        btnDeactivate = (Button) findViewById(R.id.BtnDeactivate);
        lblLatitud = (TextView) findViewById(R.id.LblPosLatitud);
        lblLongitud = (TextView) findViewById(R.id.LblPosLongitud);
        lblPrecision = (TextView) findViewById(R.id.LblPosPrecision);
        lblState = (TextView) findViewById(R.id.LblState);

        btnUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lblState.setText("Provider ON ");
                startLocation();
            }
        });

        btnDeactivate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                locManager.removeUpdates(locListener);
                lblState.setText("Provider OFF ");

            }
        });
    }

    private void startLocation() {
        //We get a reference to the LocationManager
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= 23) { // Marshmallow

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }

        }


        //We obtain the last known position
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //We show the last known position
        showPosition(loc);

        //We sign up for position updates
        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                showPosition(location);
            }

            public void onProviderDisabled(String provider) {
                lblState.setText("Provider OFF");
            }

            public void onProviderEnabled(String provider) {
                lblState.setText("Provider ON ");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("", "Provider Status: " + status);
                lblState.setText("Provider Status: " + status);
            }
        };


        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 3000, 0, locListener);
    }

    private void showPosition(Location loc) {
        if(loc != null)
        {
            lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            lblLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            lblPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
            Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
        else
        {
            lblLatitud.setText("Latitud: (sin_datos)");
            lblLongitud.setText("Longitud: (sin_datos)");
            lblPrecision.setText("Precision: (sin_datos)");
        }
    }
}