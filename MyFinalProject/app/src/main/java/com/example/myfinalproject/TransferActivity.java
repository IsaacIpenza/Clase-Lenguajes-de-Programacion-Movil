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
import android.widget.TextView;
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

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TransferActivity extends AppCompatActivity {

    private TextView textCardName, textCardEnding, textAvailable, lblCardOption, lblOtherBank, lblTransferAmount;
    private EditText cardNumber, textAmount;
    private Spinner transferOption, cardOptions;
    private Button btn;

    private List<CreditCard> creditCards;
    private List<DebitCard> debitCards;
    private List<User> userList;

    private DebitCard mainDC;
    private User user;
    private String transferType, cardEnding;
    private int amount;
    private List<String> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer);
        Toolbar toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);

        textCardName = (TextView) findViewById(R.id.textCardNameT);
        textCardEnding = (TextView) findViewById(R.id.textCardEndingT);
        textAvailable = (TextView) findViewById(R.id.textAvailableT);

        transferOption = (Spinner) findViewById(R.id.transferOption);
        cardOptions = (Spinner) findViewById(R.id.accountsOption);

        lblCardOption = (TextView) findViewById(R.id.lblAccountOption);
        lblOtherBank = (TextView) findViewById(R.id.lblOtherBank);
        lblTransferAmount = (TextView) findViewById(R.id.lblTransferAmount);

        cardNumber = (EditText) findViewById(R.id.editTextCardNumber);
        textAmount = (EditText) findViewById(R.id.editTextAmount);

        btn = (Button) findViewById(R.id.btnTransfer2);

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

        mainDC = debitCards.stream().filter(c -> c.getDCId().equals(b.get("debitCard"))).findFirst().orElse(null);
        debitCards.remove(mainDC);

        textCardName.setText(mainDC.getDCName());
        textCardEnding.setText("**** **** **** " + mainDC.getCardEnding());
        textAvailable.setText(DecimalFormat.getCurrencyInstance().format(mainDC.getTotalBalance()));

        List<String> optionsList = Arrays.asList("--","To the same bank", "To other bank", "To credit cards");

        ArrayAdapter<String> adapterOptions =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, optionsList);

        adapterOptions.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        transferOption.setAdapter(adapterOptions);



        transferOption.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        cards = new ArrayList<>();
                        cards.add("--");

                        transferType = optionsList.get(position);

                        if (optionsList.get(position).equals("To the same bank")) {
                            cardOptions.setVisibility(View.VISIBLE);
                            lblCardOption.setVisibility(View.VISIBLE);

                            lblOtherBank.setVisibility(View.GONE);
                            lblTransferAmount.setVisibility(View.GONE);

                            cardNumber.setVisibility(View.GONE);
                            textAmount.setVisibility(View.GONE);

                            btn.setVisibility(View.GONE);

                            cardNumber.setText("");

                            cards.addAll(debitCards.stream()
                                    .map(DebitCard::getCardEnding)
                                    .map(c -> "**** **** **** " + c)
                                    .collect(Collectors.toList()));

                            ArrayAdapter<String> adapterDebitCards = new ArrayAdapter<String>(TransferActivity.this,
                                            android.R.layout.simple_spinner_item, cards);

                            adapterDebitCards.setDropDownViewResource(
                                    android.R.layout.simple_spinner_dropdown_item);

                            cardOptions.setAdapter(adapterDebitCards);

                        } else if (optionsList.get(position).equals("To other bank")) {

                            cardOptions.setVisibility(View.GONE);
                            lblCardOption.setVisibility(View.GONE);

                            lblOtherBank.setVisibility(View.VISIBLE);
                            lblTransferAmount.setVisibility(View.VISIBLE);

                            cardNumber.setVisibility(View.VISIBLE);
                            textAmount.setVisibility(View.VISIBLE);

                            btn.setVisibility(View.VISIBLE);

                        } else if (optionsList.get(position).equals("To credit cards")) {
                            cardOptions.setVisibility(View.VISIBLE);
                            lblCardOption.setVisibility(View.VISIBLE);

                            lblOtherBank.setVisibility(View.GONE);
                            lblTransferAmount.setVisibility(View.GONE);

                            cardNumber.setVisibility(View.GONE);
                            textAmount.setVisibility(View.GONE);

                            btn.setVisibility(View.GONE);

                            cardNumber.setText("");

                            cards.addAll(creditCards.stream()
                                    .map(CreditCard::getCardEnding)
                                    .map(c -> "**** **** **** " + c)
                                    .collect(Collectors.toList()));

                            ArrayAdapter<String> adapterDebitCards = new ArrayAdapter<String>(TransferActivity.this,
                                    android.R.layout.simple_spinner_item, cards);

                            adapterDebitCards.setDropDownViewResource(
                                    android.R.layout.simple_spinner_dropdown_item);

                            cardOptions.setAdapter(adapterDebitCards);

                        } else {
                            cardOptions.setVisibility(View.GONE);
                            lblCardOption.setVisibility(View.GONE);

                            lblOtherBank.setVisibility(View.GONE);
                            lblTransferAmount.setVisibility(View.GONE);

                            cardNumber.setVisibility(View.GONE);
                            textAmount.setVisibility(View.GONE);

                            btn.setVisibility(View.GONE);
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        cardOptions.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {

                        cardEnding = cards.get(position);

                        if (cards.get(position).equals("--")) {
                            lblTransferAmount.setVisibility(View.GONE);
                            textAmount.setVisibility(View.GONE);
                            btn.setVisibility(View.GONE);

                        } else {
                            lblTransferAmount.setVisibility(View.VISIBLE);
                            textAmount.setVisibility(View.VISIBLE);
                            btn.setVisibility(View.VISIBLE);
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(textAmount.length() != 0) {
                    amount = Integer.parseInt(textAmount.getText().toString());
                }

                if(cardNumber.length() != 0) {
                    cardEnding = cardNumber.getText().toString();
                }

                if (transferType.equals("--") || cardEnding.equals("--") || amount == 0 ) {
                    Toast.makeText(TransferActivity.this, "Missing or invalid input", Toast.LENGTH_SHORT).show();
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

                    if (amount <= mainDC.getTotalBalance()) {
                        if(transferType.equals("To the same bank") ) {
                            DebitCard dc = debitCards.stream().filter(d -> cardEnding.contains(d.getCardEnding())).findFirst().orElse(null);

                            db3.insertMove("M-" + String.format("%02d", id), mainDC.getDCId(), amount, "Pay", date.getYear(), date.getMonthValue(), date.getDayOfMonth(), "Transfer - Sent");
                            db3.insertMove("M-" + String.format("%02d", id+1), dc.getDCId(), amount, "Deposit", date.getYear(), date.getMonthValue(), date.getDayOfMonth(), "Transfer - Received");

                            DebitCardDB db2 = new DebitCardDB(getApplicationContext(), "MyDebitCards", null, 1);
                            db2.connect();
                            db2.updateDC(mainDC.getDCId(), mainDC.getTotalBalance() - amount);
                            db2.updateDC(dc.getDCId(), dc.getTotalBalance() + amount);
                            db2.close();
                            Toast.makeText(TransferActivity.this, "Successful Transfer!", Toast.LENGTH_SHORT).show();

                        } else if (transferType.equals("To credit cards")){
                            CreditCard cc = creditCards.stream().filter(c -> cardEnding.contains(c.getCardEnding())).findFirst().orElse(null);
                            db3.insertMove("M-" + String.format("%02d", id), mainDC.getDCId(), amount, "Pay", date.getYear(), date.getMonthValue(), date.getDayOfMonth(), "Transfer - Sent");
                            db3.insertMove("M-" + String.format("%02d", id+1), cc.getCCId(), amount, "Deposit", date.getYear(), date.getMonthValue(), date.getDayOfMonth(), "Payment - Received");

                            DebitCardDB db2 = new DebitCardDB(getApplicationContext(), "MyDebitCards", null, 1);
                            db2.connect();
                            db2.updateDC(mainDC.getDCId(), mainDC.getTotalBalance() - amount);
                            db2.close();

                            CreditCardDB db1 = new CreditCardDB(getApplicationContext(), "MyCreditCards", null, 1);
                            db1.connect();
                            db1.updateCC(cc.getCCId(), cc.getDebt() - amount);
                            db1.close();
                            Toast.makeText(TransferActivity.this, "Successful Payment!", Toast.LENGTH_SHORT).show();
                        } else {
                            if(cardEnding.length() == 16) {

                                db3.insertMove("M-" + String.format("%02d", id), mainDC.getDCId(), amount, "Pay", date.getYear(), date.getMonthValue(), date.getDayOfMonth(), "Transfer to " + cardEnding + " - Send");

                                DebitCardDB db2 = new DebitCardDB(getApplicationContext(), "MyDebitCards", null, 1);
                                db2.connect();
                                db2.updateDC(mainDC.getDCId(), mainDC.getTotalBalance() - amount);
                                db2.close();
                                Toast.makeText(TransferActivity.this, "Successful Transfer - Other Bank!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TransferActivity.this, "Invalid Card - Must be 16 digits", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    } else {
                        Toast.makeText(TransferActivity.this, "Insufficient Funds", Toast.LENGTH_SHORT).show();
                        db3.close();
                        return;
                    }

                    db3.close();
                    transferOption.setAdapter(adapterOptions);

                    Intent intent = new Intent(TransferActivity.this, UserQueriesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    Bundle b = new Bundle();
                    b.putString("id", user.getUserId().toString());
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
