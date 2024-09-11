package com.example.ejemplointent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //We get a reference to the interface controls
        final EditText txtNombre = (EditText)findViewById(R.id.TxtNombre);
        final Button btnHola = (Button)findViewById(R.id.BtnHola);

        //We implement the button click event
        btnHola.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //We create the Intent
                Intent intent = new Intent(MainActivity.this, FrmSaludo.class); //We indicate the initial context and the destination activity
                intent.putExtra("Key", 123);
                intent.putExtra("Name", txtNombre.getText().toString());
                intent.putExtra("class",this.getClass());

                //We create the information to pass between activities
                Bundle b = new Bundle();
                b.putString("Name", txtNombre.getText().toString()); // a parameter is added to the Intent
                // for this the name of the parameter and the value are required
                b.putInt("Key", 123);
                //We add the information to the intent
                intent.putExtras(b);

                //We start the new activity
                startActivity(intent);
            }
        });
    }


}
