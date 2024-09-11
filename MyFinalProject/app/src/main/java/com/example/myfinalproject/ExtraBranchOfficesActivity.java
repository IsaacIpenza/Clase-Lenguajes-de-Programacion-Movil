package com.example.myfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.myfinalproject.db.UserDB;
import com.example.myfinalproject.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class ExtraBranchOfficesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private List<User> userList;
    private User user;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extra_branch_offices);
        Toolbar toolbar = findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar);

        Bundle b = this.getIntent().getExtras();
        UserDB db = new UserDB(getApplicationContext(), "MyUsers", null, 1);
        db.connect();
        userList = db.retrieveData();
        db.close();
        user = userList.stream().filter(u -> u.getUserId().equals(b.get("id"))).findFirst().orElse(null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng o1 = new LatLng(20.678764, -103.421854);
        LatLng o2 = new LatLng(20.700529, -103.386272);
        LatLng o3 = new LatLng(20.670658, -103.344215);
        LatLng o4 = new LatLng(20.650741, -103.399146);
        LatLng o5 = new LatLng(20.724453, -103.3878173);
        LatLng o6 = new LatLng(20.630920, -103.4359250);


        Marker marker1 = mMap.addMarker(new MarkerOptions().position(o1).title("Bank 1 - Matriz"));
        marker1.showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(o2).title("Bank 2"));
        mMap.addMarker(new MarkerOptions().position(o3).title("Bank 3"));
        mMap.addMarker(new MarkerOptions().position(o4).title("Bank 4"));
        mMap.addMarker(new MarkerOptions().position(o5).title("Bank 5"));
        mMap.addMarker(new MarkerOptions().position(o6).title("Bank 6"));


        LatLng camaraPosition = new LatLng(20.674540153602926, -103.39739800938419);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camaraPosition, 12));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Queries) {
            Intent intent = new Intent(this, UserQueriesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else if (id == R.id.Movements) {
            Intent intent = new Intent(this, UserMovementsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else if (id == R.id.Account_Statements) {
            Intent intent = new Intent(this, UserAccountActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else if (id == R.id.Payments) {
            Intent intent = new Intent(this, ExtraPaymentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else if (id == R.id.Purchases) {
            Intent intent = new Intent(this, ExtraPurchasesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else if (id == R.id.Balances) {
            Intent intent = new Intent(this, UserBalancesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
