package com.example.mycarapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CarsCatalogAdapter extends ArrayAdapter<CarsCatalog> {

    private Activity context;
    private List<CarsCatalog> catalogs;

    public CarsCatalogAdapter(Activity context, List<CarsCatalog> objects) {
        super(context, R.layout.list_cars, objects);
        this.context = context;
        this.catalogs = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.list_cars, null);

        TextView textCardID = (TextView)item.findViewById(R.id.textCarID);
        textCardID.setText(catalogs.get(position).getCarID());

        TextView textBrand = (TextView)item.findViewById(R.id.textBrand);
        textBrand.setText(catalogs.get(position).getBrand());

        TextView textName = (TextView)item.findViewById(R.id.textName);
        textName.setText(catalogs.get(position).getName());

        TextView textModel = (TextView)item.findViewById(R.id.textModel);
        textModel.setText(catalogs.get(position).getModel());

        return item;
    }
}
