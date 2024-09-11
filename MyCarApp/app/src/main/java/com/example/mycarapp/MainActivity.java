package com.example.mycarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView carsList;
    private TextView textCardID;
    ArrayList<CarsCatalog> catalog = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carsList = (ListView)findViewById(R.id.CarsList);

        Resources res = getResources();
        TypedArray ta = res.obtainTypedArray(R.array.cars_catalog);
        int n = ta.length();
        String[][] array = new String[n][];
        for (int i = 0; i < n; ++i) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {
                array[i] = res.getStringArray(id);
                catalog.add(new CarsCatalog(array[i][0], array[i][1], array[i][2], array[i][3]));
            }
        }
        ta.recycle();

        CarsCatalogAdapter adaptador = new CarsCatalogAdapter(this, catalog);
        carsList.setAdapter(adaptador);

        carsList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, CarDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Bundle b = new Bundle();
                b.putInt("CarPosition", position);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }
}