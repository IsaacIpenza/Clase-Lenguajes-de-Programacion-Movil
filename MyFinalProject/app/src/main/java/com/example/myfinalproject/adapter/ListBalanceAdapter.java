package com.example.myfinalproject.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myfinalproject.R;
import com.example.myfinalproject.models.DebitCard;

import java.text.DecimalFormat;
import java.util.List;

public class ListBalanceAdapter extends ArrayAdapter<DebitCard> {

    private Activity context;
    private List<DebitCard> debitCardList;

    public ListBalanceAdapter(Activity context, List<DebitCard> objects) {
        super(context, R.layout.list_balance, objects);
        this.context = context;
        this.debitCardList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.list_balance, null);

        TextView textCardNameCC = (TextView)item.findViewById(R.id.textCardName);
        textCardNameCC.setText(debitCardList.get(position).getDCName());

        TextView textCardEnding = (TextView)item.findViewById(R.id.textCardEnding);
        textCardEnding.setText("*" + debitCardList.get(position).getCardEnding());

        TextView textAvailable = (TextView)item.findViewById(R.id.textAvailable);
        textAvailable.setText(DecimalFormat.getCurrencyInstance().format(debitCardList.get(position).getTotalBalance()));

        return item;
    }
}
