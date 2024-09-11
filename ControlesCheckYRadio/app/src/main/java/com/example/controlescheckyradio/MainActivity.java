package com.example.controlescheckyradio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

    private CheckBox cbMarcame;
    private TextView lblMensaje;
    private RadioGroup rgOpciones;
    private RadioButton  radioB;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbMarcame = (CheckBox)findViewById(R.id.ChkMarcame);

        cbMarcame.setChecked(false);

        radioB = (RadioButton) findViewById(R.id.radio1);



        cbMarcame.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbMarcame.setText("Checkbox Checked!");
                }
                else {
                    cbMarcame.setText("Checkbox Unhecked!");
                }
            }
        });

        lblMensaje = (TextView)findViewById(R.id.LblSeleccion);
        rgOpciones = (RadioGroup)findViewById(R.id.gruporb);

        rgOpciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String cad = null;
                if(checkedId == R.id.radio1)
                {
                    cad = "RadioButton1";

                    cbMarcame.setChecked(false);
                }
                else
                {
                    cad = "RadioButton2";
                }


                lblMensaje.setText("ID selected: " + checkedId + cad );

            }
        });
    }


}
