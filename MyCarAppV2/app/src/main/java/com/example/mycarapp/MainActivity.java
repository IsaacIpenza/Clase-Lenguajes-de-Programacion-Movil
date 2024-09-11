package com.example.mycarapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView carsList;
    private List<CarsCatalog> catalog ;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carsList = (ListView) findViewById(R.id.CarsList);

        ControlBD db = new ControlBD(getApplicationContext(), "MyCarApp", null, 1);
        catalog = db.insertData();
        db.close();

        CarsCatalogAdapter adaptador = new CarsCatalogAdapter(this, catalog);
        carsList.setAdapter(adaptador);


        carsList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                position = i;

                for (int x=0;x<parent.getChildCount();x++){
                    parent.getChildAt(x).setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(Color.GRAY);

                registerForContextMenu(carsList);
            }
        });

        carsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                position = i;

                for (int x=0;x<parent.getChildCount();x++){
                    parent.getChildAt(x).setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(Color.GRAY);

                registerForContextMenu(carsList);

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_detalles) {
            Intent intent = new Intent(this, CarDetails.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", catalog.get(position).getCarID());
            b.putString("brand", catalog.get(position).getBrand());
            b.putString("name", catalog.get(position).getName());
            b.putString("model", catalog.get(position).getModel());
            b.putInt("cylinders", catalog.get(position).getNoC());
            b.putString("price", catalog.get(position).getPrice());
            b.putInt("image", catalog.get(position).getImage());

            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_sucursales) {
            Intent intent = new Intent(this, MapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        this.getMenuInflater().inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Bundle b = new Bundle();

        if(item.getItemId() == R.id.detallesContextMenu) {
            Intent intent = new Intent(this, CarDetails.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            b.putString("id", catalog.get(position).getCarID());
            b.putString("brand", catalog.get(position).getBrand());
            b.putString("name", catalog.get(position).getName());
            b.putString("model", catalog.get(position).getModel());
            b.putInt("cylinders", catalog.get(position).getNoC());
            b.putString("price", catalog.get(position).getPrice());
            b.putInt("image", catalog.get(position).getImage());

            intent.putExtras(b);
            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}