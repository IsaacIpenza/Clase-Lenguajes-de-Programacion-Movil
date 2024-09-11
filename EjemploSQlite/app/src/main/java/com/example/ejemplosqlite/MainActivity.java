package com.example.ejemplosqlite;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity
{
    private EditText txtNombre;
    private Button btnHola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = (EditText)findViewById(R.id.TxtNombre);
        btnHola = (Button)findViewById(R.id.BtnHola);



        btnHola.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString();
                System.out.println("Creando...");
                ControlBD control = new ControlBD(v.getContext(),nombre,null,1);
                control.conectar();
                control.insertarUsuario(1, "Chepo");
                control.cerrar();

            }});


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
