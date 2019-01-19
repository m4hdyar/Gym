package com.example.m4hdyar.gym;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText UserTxt ;
    EditText PassTxt ;
    Button LoginBt ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserTxt = (EditText) findViewById(R.id.editTextUserName);
        PassTxt = (EditText) findViewById(R.id.editTextPass);
        LoginBt = (Button) findViewById(R.id.buttonLogin);


        LoginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    public void login() {
//        Fragment fragment = null;
        if (UserTxt.getText().toString().equals("a") && PassTxt.getText().toString().equals("a")) {

            Toast.makeText(getApplicationContext(),
                    "ورود موفقیت آمیز",Toast.LENGTH_SHORT).show();
//            fragment = new SportProgramFragment();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "اطلاعات نا معتبر است",Toast.LENGTH_SHORT).show();
        }
    }

}