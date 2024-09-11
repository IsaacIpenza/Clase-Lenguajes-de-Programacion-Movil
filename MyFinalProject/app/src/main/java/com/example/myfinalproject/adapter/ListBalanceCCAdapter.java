package com.example.myfinalproject.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myfinalproject.R;
import com.example.myfinalproject.models.CreditCard;
import com.example.myfinalproject.models.DebitCard;

import java.text.DecimalFormat;
import java.util.List;

public class ListBalanceCCAdapter extends ArrayAdapter<CreditCard> {

    private Activity context;
    private List<CreditCard> creditCardList;

    public ListBalanceCCAdapter(Activity context, List<CreditCard> objects) {
        super(context, R.layout.list_balance_cc, objects);
        this.context = context;
        this.creditCardList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.list_balance_cc, null);

        TextView textCardNameCC = (TextView)item.findViewById(R.id.textCardNameCC);
        textCardNameCC.setText(creditCardList.get(position).getCCName());

        TextView textCardEndingCC = (TextView)item.findViewById(R.id.textCardEndingCC);
        textCardEndingCC.setText("*" + creditCardList.get(position).getCardEnding());

        TextView textAvailableCC = (TextView)item.findViewById(R.id.textAvailableCC);
        textAvailableCC.setText(DecimalFormat.getCurrencyInstance().format(creditCardList.get(position).getTotalCredit() - creditCardList.get(position).getDebt()));

        TextView textTotalCredit = (TextView)item.findViewById(R.id.textTotalCredit);
        textTotalCredit.setText(DecimalFormat.getCurrencyInstance().format(creditCardList.get(position).getTotalCredit()));

        return item;
    }
}