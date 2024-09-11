package com.example.mycarapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CarDetails extends Activity {

    private TextView textCardId, textBrand, textName, textModel, textNumCylinders, textPrice;
    private ImageView image;
    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cars_details);

        textCardId = (TextView) findViewById(R.id.textCardIdDetails);
        textBrand = (TextView) findViewById(R.id.textBrandDetails);
        textName = (TextView) findViewById(R.id.textNameDetails);
        textModel = (TextView) findViewById(R.id.textModelDetails);
        textNumCylinders = (TextView) findViewById(R.id.textNumCylindersDetails);
        textPrice = (TextView) findViewById(R.id.textPriceDetails);
        image = (ImageView) findViewById(R.id.imageCar);
        btnReturn = (Button)findViewById(R.id.ReturnButton);

        Bundle b = this.getIntent().getExtras();

        textCardId.setText(b.getString("id"));
        textBrand.setText(b.getString("brand"));
        textName.setText(b.getString("name"));
        textModel.setText(b.getString("model"));
        textNumCylinders.setText(String.valueOf(b.getInt("cylinders")));
        textPrice.setText(b.getString("price"));
        image.setImageResource(b.getInt("image"));

        btnReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CarDetails.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }
}
