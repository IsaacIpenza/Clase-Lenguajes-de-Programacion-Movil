package com.example.myfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfinalproject.db.CreditCardDB;
import com.example.myfinalproject.db.DebitCardDB;
import com.example.myfinalproject.db.MovementsDB;
import com.example.myfinalproject.db.UserDB;
import com.example.myfinalproject.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText emailInput, passwordInput;
    private String email;
    private String password;

    private List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.continueBtn);
        emailInput = (EditText)findViewById(R.id.editTextTextEmailAddress);
        passwordInput = (EditText) findViewById(R.id.editTextTextPassword);

        UserDB db = new UserDB(getApplicationContext(), "MyUsers", null, 1);
        db.connect();
        db.initializeData();
        userList = db.retrieveData();

        CreditCardDB db1 = new CreditCardDB(getApplicationContext(), "MyCreditCards", null, 1);
        db1.connect();
        db1.initializeData();

        DebitCardDB db2 = new DebitCardDB(getApplicationContext(), "MyDebitCards", null, 1);
        db2.connect();
        db2.initializeData();

        MovementsDB db3 = new MovementsDB(getApplicationContext(), "MyMovements", null, 1);
        db3.connect();
        db3.initializeData();


        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.close();
                db1.close();
                db2.close();
                db3.close();

                email = emailInput.getText().toString().toLowerCase().trim();
                password = passwordInput.getText().toString().toLowerCase().trim();

                for (User user : userList) {
                    if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                        emailInput.setText("");
                        passwordInput.setText("");

                        Intent intent = new Intent(MainActivity.this, UserBalancesActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        Bundle b = new Bundle();
                        b.putString("id", user.getUserId().toString());
                        intent.putExtras(b);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Welcome " + user.getName() + " :)", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                Toast.makeText(MainActivity.this, "Wrong email and/or password", Toast.LENGTH_SHORT).show();
                emailInput.setText("");
                passwordInput.setText("");

            }
        });



    }
}