package com.example.ejemplolistview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView lblEtiqueta;
    private ListView lstOpciones;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lblEtiqueta = (TextView)findViewById(R.id.LblEtiqueta);
        lstOpciones = (ListView)findViewById(R.id.LstOpciones);
        final String[] datos =
                new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5","Elem6","Elem7","Elem8","Elem9","Elem10"};

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, datos);
        lstOpciones.setAdapter(adaptador);

        lstOpciones.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {


                lblEtiqueta.setText("Opcion seleccionada: " + datos[position]);
            }
        });

    }




}