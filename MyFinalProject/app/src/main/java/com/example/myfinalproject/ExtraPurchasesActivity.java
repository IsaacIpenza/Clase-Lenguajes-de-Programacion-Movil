package com.example.myfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myfinalproject.db.CreditCardDB;
import com.example.myfinalproject.db.DebitCardDB;
import com.example.myfinalproject.db.MovementsDB;
import com.example.myfinalproject.db.UserDB;
import com.example.myfinalproject.models.CreditCard;
import com.example.myfinalproject.models.DebitCard;
import com.example.myfinalproject.models.Movements;
import com.example.myfinalproject.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExtraPurchasesActivity extends AppCompatActivity {

    private Spinner phoneSpinner;
    private Button btnBuy;
    private EditText textPhone, textNumber;

    private List<CreditCard> creditCards;
    private List<DebitCard> debitCards;
    private List<User> userList;
    private User user;
    private String card, phone = "--";
    private int amount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extra_purchases);
        Toolbar toolbar = findViewById(R.id.toolbar7);
        setSupportActionBar(toolbar);

        phoneSpinner = (Spinner)findViewById(R.id.phoneSpinner);
        btnBuy = (Button) findViewById(R.id.buy);
        textPhone = (EditText)  findViewById(R.id.editTextPhone);
        textNumber = (EditText)  findViewById(R.id.editTextNumber);

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

        List<String> datos = new ArrayList<>();

        datos.add("--");
        datos.addAll(debitCards.stream()
                .map(DebitCard::getDCName)
                .collect(Collectors.toList()));
        datos.addAll(creditCards.stream()
                .map(CreditCard::getCCName)
                .collect(Collectors.toList()));

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, datos);

        adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        phoneSpinner.setAdapter(adaptador);

        phoneSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        card = datos.get(position);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        btnBuy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                phone = textPhone.getText().toString();
                if(textNumber.length() != 0) {
                    amount = Integer.parseInt(textNumber.getText().toString());
                }

                if (card.equals("--") || phone.length() < 10 || amount == 0) {
                    Toast.makeText(ExtraPurchasesActivity.this, "Missing or invalid input", Toast.LENGTH_SHORT).show();
                } else {

                    MovementsDB db3 = new MovementsDB(getApplicationContext(), "MyMovements", null, 1);
                    db3.connect();
                    int id = 0;

                    List<String> movements = db3.retrieveData().stream().map(Movements::getMoveId).collect(Collectors.toList());

                    LocalDate date = LocalDate.now();

                    for (String move : movements) {
                        int temp = Integer.parseInt(move.substring(2));
                        if( temp >= id ) {
                            id = temp + 1;
                        }
                    }

                    if(debitCards.stream().anyMatch(dc -> dc.getDCName().equals(card))) {
                        DebitCard dc = debitCards.stream().filter(d -> d.getDCName().equals(card)).findFirst().orElse(null);

                        if (amount <= dc.getTotalBalance()) {
                            db3.insertMove("M-" + String.format("%02d", id), dc.getDCId(), amount, "Pay", date.getYear(), date.getMonthValue(), date.getDayOfMonth(), "Buy Phone Minutes");
                            Thread thread = new Thread(new Runnable() {
                                @Override public void run() {
                                    DebitCardDB db2 = new DebitCardDB(getApplicationContext(), "MyDebitCards", null, 1);
                                    db2.connect();
                                    db2.updateDC(dc.getDCId(), dc.getTotalBalance() - amount);
                                    db2.close();

                                    try {
                                        Thread.sleep(5000);
                                    } catch(InterruptedException e) {}

                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(ExtraPurchasesActivity.this, "Successful Purchase!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            thread.start();

                        } else {
                            Toast.makeText(ExtraPurchasesActivity.this, "Insufficient Funds", Toast.LENGTH_SHORT).show();
                            db3.close();
                            return;
                        }

                    } else {
                        CreditCard cc = creditCards.stream().filter(c -> c.getCCName().equals(card)).findFirst().orElse(null);

                        if (amount <= cc.getTotalCredit() - cc.getDebt()) {
                            db3.insertMove("M-" + String.format("%02d", id), cc.getCCId(), amount, "Pay", date.getYear(), date.getMonthValue(), date.getDayOfMonth(), "Buy Phone Minutes");

                            Thread thread = new Thread(new Runnable() {
                                @Override public void run() {
                                    // code to run in background thread
                                    CreditCardDB db1 = new CreditCardDB(getApplicationContext(), "MyCreditCards", null, 1);
                                    db1.connect();
                                    db1.updateCC(cc.getCCId(), cc.getDebt() + amount);
                                    db1.close();

                                    try {
                                        Thread.sleep(4000);
                                    } catch(InterruptedException e) {}

                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(ExtraPurchasesActivity.this, "Successful Purchase!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            thread.start();

                        } else {
                            Toast.makeText(ExtraPurchasesActivity.this, "Insufficient Funds", Toast.LENGTH_SHORT).show();
                            db3.close();
                            return;
                        }
                    }

                    db3.close();

                    textNumber.setText("");
                    textPhone.setText("");
                    phoneSpinner.setAdapter(adaptador);

                    Toast.makeText(ExtraPurchasesActivity.this, "Purchase in Process!", Toast.LENGTH_SHORT).show();
                }
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
        } else if (id == R.id.Account_Statements) {
            Intent intent = new Intent(this, UserAccountActivity.class);
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
        } else if (id == R.id.Balances) {
            Intent intent = new Intent(this, UserBalancesActivity.class);
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
