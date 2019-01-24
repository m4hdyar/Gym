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
    Button LoginBtn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        UserTxt = (EditText) findViewById(R.id.editTextTel);
        PassTxt = (EditText) findViewById(R.id.editTextLockerCode);
        LoginBtn = (Button) findViewById(R.id.buttonLogin);


        LoginBtn.setOnClickListener(new View.OnClickListener() {
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