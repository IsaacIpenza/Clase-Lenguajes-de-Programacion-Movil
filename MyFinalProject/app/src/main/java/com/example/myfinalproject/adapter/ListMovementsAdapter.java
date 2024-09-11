package com.example.myfinalproject.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myfinalproject.R;
import com.example.myfinalproject.models.Movements;

import java.text.DecimalFormat;
import java.util.List;

public class ListMovementsAdapter extends ArrayAdapter<Movements> {

    private Activity context;
    private List<Movements> movementsList;

    public ListMovementsAdapter(Activity context, List<Movements> objects) {
        super(context, R.layout.list_movements, objects);
        this.context = context;
        this.movementsList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.list_movements, null);

        TextView textMoveId = (TextView)item.findViewById(R.id.textMoveId);
        textMoveId.setText(movementsList.get(position).getMoveId());

        TextView textCardNameM = (TextView)item.findViewById(R.id.textCardNameM);
        textCardNameM.setText(movementsList.get(position).getCardName());

        TextView textCardEndingM = (TextView)item.findViewById(R.id.textCardEndingM);
        textCardEndingM.setText("*" + movementsList.get(position).getCardEnding());

        TextView textAmountM = (TextView)item.findViewById(R.id.textAmountM);

        if (movementsList.get(position).getMoveType().equals("Deposit")) {
            textAmountM.setText("+ " + DecimalFormat.getCurrencyInstance().format(movementsList.get(position).getAmount()));
            textAmountM.setTextColor(0xFF43A047);

        } else {
            textAmountM.setText("- " + DecimalFormat.getCurrencyInstance().format(movementsList.get(position).getAmount()));
            textAmountM.setTextColor(0xDCF80101);
        }

        TextView textDateM = (TextView)item.findViewById(R.id.textDateM);
        textDateM.setText(movementsList.get(position).getMoveDate().toString());

        TextView textMovementM = (TextView)item.findViewById(R.id.textMovementM);
        textMovementM.setText(" -> " + movementsList.get(position).getDescription());

        return item;
    }
}
