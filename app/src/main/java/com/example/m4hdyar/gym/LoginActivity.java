package com.example.m4hdyar.gym;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText UserTxt = (EditText) findViewById(R.id.editTextUserName);
    EditText PassTxt = (EditText) findViewById(R.id.editTextPass);
    Button LoginBt = (Button) findViewById(R.id.buttonLogin);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    public void login() {
        if (UserTxt.getText().toString().equals("admin") && PassTxt.getText().toString().equals("admin")) {

            Toast.makeText(getApplicationContext(),
                    "ورود موفقیت آمیز",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "اطلاعات نا معتبر است",Toast.LENGTH_SHORT).show();
        }
    }

}