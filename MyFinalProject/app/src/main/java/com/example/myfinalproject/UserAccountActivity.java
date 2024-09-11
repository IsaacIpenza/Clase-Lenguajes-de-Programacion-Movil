package com.example.myfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myfinalproject.db.CreditCardDB;
import com.example.myfinalproject.db.DebitCardDB;
import com.example.myfinalproject.db.UserDB;
import com.example.myfinalproject.models.CreditCard;
import com.example.myfinalproject.models.DebitCard;
import com.example.myfinalproject.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserAccountActivity extends AppCompatActivity {

    private Spinner options, accounts, months;
    private Button btn;

    private List<CreditCard> creditCards;
    private List<DebitCard> debitCards;
    private List<User> userList;
    private User user;
    private String opt, acc, mon = "--";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account_main);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        options = (Spinner)findViewById(R.id.accountSpinner);
        accounts = (Spinner)findViewById(R.id.accounts);
        months = (Spinner)findViewById(R.id.months);

        btn = (Button)findViewById(R.id.download);


        Bundle b = this.getIntent().getExtras();

        UserDB db = new UserDB(getApplicationContext(), "MyUsers", null, 1);
        db.connect();
        userList = db.retrieveData();
        db.close();

        user = userList.stream().filter(u -> u.getUserId().equals(b.get("id"))).findFirst().orElse(null);

        CreditCardDB db1 = new CreditCardDB(getApplicationContext(), "MyCreditCards", null, 1);
        db1.connect();
        creditCards = db1.retrieveData().stream().filter(cc -> user.getUserId().equals(cc.getUserId())).collect(Collectors.toList());
        db1.close();

        DebitCardDB db2 = new DebitCardDB(getApplicationContext(), "MyDebitCards", null, 1);
        db2.connect();
        debitCards = db2.retrieveData().stream().filter(dc -> user.getUserId().equals(dc.getUserId())).collect(Collectors.toList());
        db2.close();


        List<String> optionsList = Arrays.asList("--","Checking and Investment Account", "Credit Card");
        List<String> monthsList = Arrays.asList("--","January 2024", "February 2024", "March 2024", "April 2024");
        List<String> datos = new ArrayList<>();

        ArrayAdapter<String> adapterOptions =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, optionsList);

        adapterOptions.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        options.setAdapter(adapterOptions);

        ArrayAdapter<String> adapterMonths =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, monthsList);

        adapterMonths.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        months.setAdapter(adapterMonths);

        accounts.setEnabled(false);
        months.setEnabled(false);

        options.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        opt = optionsList.get(position);

                        if(!optionsList.get(position).equals("--")) {
                            if (optionsList.get(position).equals("Credit Card")) {
                                datos.removeAll(datos);
                                datos.add("--");
                                datos.addAll(creditCards.stream()
                                        .map(CreditCard::getCardEnding)
                                        .map(c -> "**** **** **** " + c)
                                        .collect(Collectors.toList()));
                            } else {
                                datos.removeAll(datos);
                                datos.add("--");
                                datos.addAll(debitCards.stream()
                                        .map(DebitCard::getCardEnding)
                                        .map(d -> "**** **** **** " + d)
                                        .collect(Collectors.toList()));
                            }

                            ArrayAdapter<String> adapterAccount =
                                    new ArrayAdapter<String>(UserAccountActivity.this,
                                            android.R.layout.simple_spinner_item, datos);

                            adapterAccount.setDropDownViewResource(
                                    android.R.layout.simple_spinner_dropdown_item);

                            accounts.setAdapter(adapterAccount);
                            accounts.setEnabled(true);
                        } else {
                            accounts.setEnabled(false);
                            months.setEnabled(false);
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {}
                });

        accounts.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        acc = datos.get(position);
                        if(!datos.get(position).equals("--")) {
                            months.setEnabled(true);
                        } else {
                            months.setEnabled(false);
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {}
                });

        months.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        mon = monthsList.get(position);
                        if (!monthsList.get(position).equals("--")) {
                            btn.setEnabled(true);
                        } else {
                            btn.setEnabled(false);
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {}
                });

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(UserAccountActivity.this, "Account Statement Downloaded Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Queries) {
            Intent intent = new Intent(this, UserQueriesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else if (id == R.id.Movements) {
            Intent intent = new Intent(this, UserMovementsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else if (id == R.id.Balances) {
            Intent intent = new Intent(this, UserBalancesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else if (id == R.id.Payments) {
            Intent intent = new Intent(this, ExtraPaymentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else if (id == R.id.Purchases) {
            Intent intent = new Intent(this, ExtraPurchasesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else if (id == R.id.Branch_Offices) {
            Intent intent = new Intent(this, ExtraBranchOfficesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bundle b = new Bundle();
            b.putString("id", user.getUserId().toString());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
