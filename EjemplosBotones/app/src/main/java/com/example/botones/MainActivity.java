package com.example.botones;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    private TextView lblMensaje;
    private Button btnBoton1;
    private Button btnBoton5;
    private ToggleButton btnBoton2;
    private ImageButton btnBoton3;
    private ToggleButton btnBoton4;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblMensaje = (TextView)findViewById(R.id.LblMensaje); // se obtiene la referencia del TextView declarado en el layout y
        // se le hace el cast al objeto correspondiente
        btnBoton1 = (Button)findViewById(R.id.BtnBoton1);

        btnBoton1.setOnClickListener(new View.OnClickListener() { //se vincula un manejador de eventos al objeto Bot�n
            public void onClick(View arg0)
            {
                lblMensaje.setText("Boton 1 pulsado!");
            }
        });

        btnBoton5 = (Button)findViewById(R.id.BtnBoton5);

        btnBoton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                lblMensaje.setText("Boton 5 pulsado!");
            }
        });

        btnBoton2 = (ToggleButton)findViewById(R.id.BtnBoton2);

        btnBoton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                if(btnBoton2.isChecked())
                    lblMensaje.setText("Boton 2: ON");
                else
                    lblMensaje.setText("Boton 2: OFF");
            }
        });

        btnBoton3 = (ImageButton)findViewById(R.id.BtnBoton3);

        btnBoton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                lblMensaje.setText("Boton 3 pulsado!");
            }
        });

        btnBoton4 = (ToggleButton)findViewById(R.id.BtnBoton4);

        btnBoton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                if(btnBoton4.isChecked())
                    lblMensaje.setText("Boton 4: ON");
                else
                    lblMensaje.setText("Boton 4: OFF");
            }
        });
    }

}
