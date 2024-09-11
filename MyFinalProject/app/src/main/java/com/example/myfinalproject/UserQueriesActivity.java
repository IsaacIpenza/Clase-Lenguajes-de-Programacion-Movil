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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myfinalproject.adapter.ListMovementsAdapter;
import com.example.myfinalproject.db.CreditCardDB;
import com.example.myfinalproject.db.DebitCardDB;
import com.example.myfinalproject.db.MovementsDB;
import com.example.myfinalproject.db.UserDB;
import com.example.myfinalproject.models.CreditCard;
import com.example.myfinalproject.models.DebitCard;
import com.example.myfinalproject.models.Movements;
import com.example.myfinalproject.models.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserQueriesActivity extends AppCompatActivity {

    private ListView movementsListView;
    private Spinner cardOptions;
    private TextView textCardNameCard, textCardEndingCard, textAvailableCard, textTotalCreditCard, textDebtCard;
    private TextView lblTotalCreditCard, lblDebtCard;
    private Button btnTranfer;

    private List<CreditCard> creditCards;
    private List<DebitCard> debitCards;
    private List<User> userList;
    private List<Movements> movementsList;

    private User user;
    private String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_queries_main);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        cardOptions = (Spinner)findViewById(R.id.cardSpinner);
        movementsListView = (ListView) findViewById(R.id.MoveListCards);

        textCardNameCard = (TextView) findViewById(R.id.textCardNameCard);
        textCardEndingCard = (TextView) findViewById(R.id.textCardEndingCards);
        textAvailableCard = (TextView) findViewById(R.id.textAvailableCard);
        textTotalCreditCard = (TextView) findViewById(R.id.textTotalCreditCard);
        textDebtCard = (TextView) findViewById(R.id.textDebtCard);

        lblTotalCreditCard = (TextView) findViewById(R.id.lblTotalCreditCard);
        lblDebtCard = (TextView) findViewById(R.id.lblDebtCard);

        btnTranfer = (Button) findViewById(R.id.btnTransfer);

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

        MovementsDB db3 = new MovementsDB(getApplicationContext(), "MyMovements", null, 1);
        db3.connect();
        movementsList = db3.retrieveData();
        db3.close();

        List<String> datos = new ArrayList<>();

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

        cardOptions.setAdapter(adaptador);

        List<Movements> finalMovementsList = new ArrayList<>();

        cardOptions.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {

                        if(debitCards.stream().anyMatch(dc -> dc.getDCName().equals(datos.get(position)))) {
                            DebitCard dc = debitCards.stream().filter(d -> d.getDCName().equals(datos.get(position))).findFirst().orElse(null);
                            cardId = dc.getDCId();

                            textCardNameCard.setText(dc.getDCName());
                            textCardEndingCard.setText("**** **** **** " + dc.getCardEnding());
                            textAvailableCard.setText(DecimalFormat.getCurrencyInstance().format(dc.getTotalBalance()));

                            textTotalCreditCard.setVisibility(View.INVISIBLE);
                            textDebtCard.setVisibility(View.INVISIBLE);
                            lblTotalCreditCard.setVisibility(View.INVISIBLE);
                            lblDebtCard.setVisibility(View.INVISIBLE);

                            btnTranfer.setVisibility(View.VISIBLE);
                        } else {
                            CreditCard cc = creditCards.stream().filter(c -> c.getCCName().equals(datos.get(position))).findFirst().orElse(null);
                            cardId = "";

                            textCardNameCard.setText(cc.getCCName());
                            textCardEndingCard.setText("**** **** **** " + cc.getCardEnding());
                            textAvailableCard.setText(DecimalFormat.getCurrencyInstance().format(cc.getTotalCredit() - cc.getDebt()));

                            textTotalCreditCard.setVisibility(View.VISIBLE);
                            textDebtCard.setVisibility(View.VISIBLE);
                            lblTotalCreditCard.setVisibility(View.VISIBLE);
                            lblDebtCard.setVisibility(View.VISIBLE);

                            textTotalCreditCard.setText(DecimalFormat.getCurrencyInstance().format(cc.getTotalCredit()));
                            textDebtCard.setText("- " + DecimalFormat.getCurrencyInstance().format(cc.getDebt()));

                            btnTranfer.setVisibility(View.GONE);
                        }

                        finalMovementsList.removeAll(finalMovementsList);

                        for (Movements m : movementsList) {

                            if (m.getCardId().contains("CC")) {
                                for (CreditCard cc : creditCards) {
                                    if (m.getCardId().equals(cc.getCCId()) && user.getUserId().equals(cc.getUserId()) && cc.getCCName().equals(datos.get(position))) {
                                        m.setCardName(cc.getCCName());
                                        m.setCardEnding(cc.getCardEnding());
                                        finalMovementsList.add(m);
                                    }
                                }
                            } else if (m.getCardId().contains("DC")) {
                                for (DebitCard dc : debitCards) {
                                    if (m.getCardId().equals(dc.getDCId()) && user.getUserId().equals(dc.getUserId()) && dc.getDCName().equals(datos.get(position))) {
                                        m.setCardName(dc.getDCName());
                                        m.setCardEnding(dc.getCardEnding());
                                        finalMovementsList.add(m);
                                    }
                                }
                            } else {
                                System.out.println("Movement not recognized");
                            }
                        }

                        ListMovementsAdapter adapter =
                                new ListMovementsAdapter( UserQueriesActivity.this,
                                        finalMovementsList.stream()
                                                .sorted(Comparator.comparing(Movements::getMoveDate).reversed())
                                                .collect(Collectors.toList()));
                        movementsListView.setAdapter(adapter);

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        btnTranfer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!cardId.equals("")) {
                    Intent intent = new Intent(UserQueriesActivity.this, TransferActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    Bundle b = new Bundle();
                    b.putString("id", user.getUserId().toString());
                    b.putString("debitCard", cardId);
                    intent.putExtras(b);
                    startActivity(intent);
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

        if (id == R.id.Balances) {
            Intent intent = new Intent(this, UserBalancesActivity.class);
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
