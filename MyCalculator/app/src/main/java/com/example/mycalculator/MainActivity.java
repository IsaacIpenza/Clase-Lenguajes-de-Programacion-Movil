package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView lblOperacion, lblResultado;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    private Button btnDiv, btnMult, btnResta, btnSuma, btnClear, btnIgual;

    private String temp = "";
    private Integer value1, value2, result;

    private enum Operaciones {
        SUMA,
        RESTA,
        MULTIPLICACION,
        DIVISION,
    }

    Operaciones operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NumericalButtonsLogic("1"); }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NumericalButtonsLogic("2"); }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NumericalButtonsLogic("3"); }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NumericalButtonsLogic("4"); }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NumericalButtonsLogic("5"); }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NumericalButtonsLogic("6"); }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NumericalButtonsLogic("7"); }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NumericalButtonsLogic("8"); }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NumericalButtonsLogic("9"); }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NumericalButtonsLogic("0"); }
        });

        btnSuma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (temp.length() > 0 && value1 == null) {
                    saveValueOrResult();
                    operacion = Operaciones.SUMA;
                    lblOperacion.setText(lblOperacion.getText().toString() + " + ");
                    temp = "";
                }
            }
        });

        btnResta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (temp.length() > 0 && value1 == null) {
                    saveValueOrResult();
                    operacion = Operaciones.RESTA;
                    lblOperacion.setText(lblOperacion.getText().toString() + " - ");
                    temp = "";
                }
            }
        });

        btnMult.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (temp.length() > 0 && value1 == null) {
                    saveValueOrResult();
                    operacion = Operaciones.MULTIPLICACION;
                    lblOperacion.setText(lblOperacion.getText().toString() + " x ");
                    temp = "";
                }
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (temp.length() > 0 && value1 == null) {
                    saveValueOrResult();
                    operacion = Operaciones.DIVISION;
                    lblOperacion.setText(lblOperacion.getText().toString() + " / ");
                    temp = "";
                }
            }
        });

        btnIgual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (temp.length() > 0 && value1 != null && result == null) {
                    saveValueOrResult();
                    lblOperacion.setText(lblOperacion.getText().toString() + " =");
                    lblResultado.setText(String.valueOf(result));
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               clear();
            }
        });

    }


    private void initializeVariables() {

        lblOperacion = (TextView)findViewById(R.id.Operacion);
        lblResultado = (TextView)findViewById(R.id.Resultado);

        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);
        btn4 = (Button)findViewById(R.id.button4);
        btn5 = (Button)findViewById(R.id.button5);
        btn6 = (Button)findViewById(R.id.button6);
        btn7 = (Button)findViewById(R.id.button7);
        btn8 = (Button)findViewById(R.id.button8);
        btn9 = (Button)findViewById(R.id.button9);
        btn0 = (Button)findViewById(R.id.button0);
        btnDiv = (Button)findViewById(R.id.buttonDiv);
        btnMult = (Button)findViewById(R.id.buttonMult);
        btnResta = (Button)findViewById(R.id.buttonResta);
        btnSuma = (Button)findViewById(R.id.buttonSuma);
        btnClear = (Button)findViewById(R.id.buttonClear);
        btnIgual = (Button)findViewById(R.id.buttonIgual);
    }

    private void NumericalButtonsLogic(String value){
        if(lblResultado.getText().length() > 0) {
            clear();
        }
        lblOperacion.setText(lblOperacion.getText().toString() + value);
        temp = temp + value;
    }

    private void saveValueOrResult() {
            if (value1 != null) {
                value2 = Integer.parseInt(temp);

                switch (operacion) {
                    case SUMA:
                        result = value1 + value2;
                        break;
                    case RESTA:
                        result = value1 - value2;
                        break;
                    case MULTIPLICACION:
                        result = value1 * value2;
                        break;
                    case DIVISION:
                        result = value1 / value2;
                        break;
                    default:
                        break;
                }
            } else {
                value1 = Integer.parseInt(temp);
            }
    }

    private void clear() {
        lblOperacion.setText(null);
        lblResultado.setText(null);
        operacion = null;
        temp = "";
        value1 = null;
        value2 = null;
        result = null;

    }
}