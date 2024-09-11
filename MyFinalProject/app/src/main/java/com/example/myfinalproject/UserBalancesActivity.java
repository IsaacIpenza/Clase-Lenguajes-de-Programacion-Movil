package com.example.myfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myfinalproject.adapter.ListBalanceAdapter;
import com.example.myfinalproject.adapter.ListBalanceCCAdapter;
import com.example.myfinalproject.db.CreditCardDB;
import com.example.myfinalproject.db.DebitCardDB;
import com.example.myfinalproject.db.UserDB;
import com.example.myfinalproject.models.CreditCard;
import com.example.myfinalproject.models.DebitCard;
import com.example.myfinalproject.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserBalancesActivity extends AppCompatActivity {

    private ListView creditCardListView;
    private ListView debitCardListView;

    private List<CreditCard> creditCards;
    private List<DebitCard> debitCards;
    private List<User> userList;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_balances_main);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        Bundle b = this.getIntent().getExtras();

        UserDB db = new UserDB(getApplicationContext(), "MyUsers", null, 1);
        db.connect();
        userList = db.retrieveData();
        db.close();

        user = userList.stream().filter(u -> u.getUserId().equals(b.get("id"))).findFirst().orElse(null);

        creditCardListView = (ListView) findViewById(R.id.CCList);
        debitCardListView = (ListView) findViewById(R.id.DCList);

        CreditCardDB db1 = new CreditCardDB(getApplicationContext(), "MyCreditCards", null, 1);
        db1.connect();
        creditCards = db1.retrieveData();
        db1.close();

        DebitCardDB db2 = new DebitCardDB(getApplicationContext(), "MyDebitCards", null, 1);
        db2.connect();
        debitCards = db2.retrieveData();
        db2.close();

        ListBalanceCCAdapter adaptador =
                new ListBalanceCCAdapter(this,
                        creditCards.stream().filter(cc -> user.getUserId().equals(cc.getUserId())).collect(Collectors.toList()));
        creditCardListView.setAdapter(adaptador);

        ListBalanceAdapter adaptador1 =
                new ListBalanceAdapter(this,
                        debitCards.stream().filter(dc -> user.getUserId().equals(dc.getUserId())).collect(Collectors.toList()));
        debitCardListView.setAdapter(adaptador1);

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
