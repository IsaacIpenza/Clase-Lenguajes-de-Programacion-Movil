package com.example.mycarapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
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

        Bundle bundle = this.getIntent().getExtras();

        int position  = bundle.getInt("CarPosition");

        Resources res = getResources();
        TypedArray ta = res.obtainTypedArray(R.array.cars_catalog);
        int id = ta.getResourceId(position, 0);
        String[] array = res.getStringArray(id);

        textCardId.setText(array[0]);
        textBrand.setText(array[1]);
        textName.setText(array[2]);
        textModel.setText(array[3]);
        textNumCylinders.setText(array[4]);
        textPrice.setText(array[5]);

        ta = res.obtainTypedArray(R.array.cars_photo);
        id = ta.getResourceId(position, 0);

        image.setImageResource(id);

        ta.recycle();

        btnReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CarDetails.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }
}
