package com.example.ejemplointent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FrmSaludo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saludo);

        //ImageView iv = new ImageView(this);

        ImageView iv = (ImageView) findViewById(R.id.imageView);

        iv.setImageResource(R.mipmap.m1);


        //Locate the controls
        TextView txtSaludo = (TextView)findViewById(R.id.TxtSaludo);

        //We recover the information passed in the intent
        Bundle bundle = this.getIntent().getExtras();

        //We build the message to show
        txtSaludo.setText("Hello " + bundle.getString("Name") + " " + bundle.getInt("Key"));
    }
}