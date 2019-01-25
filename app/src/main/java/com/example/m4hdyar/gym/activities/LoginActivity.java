package com.example.m4hdyar.gym.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.m4hdyar.gym.R;
import com.example.m4hdyar.gym.utils.SharedPrefManager;
import com.example.m4hdyar.gym.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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


        //User login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });


    }

    private void userLogin() {
        //first getting the values
        final String tel = UserTxt.getText().toString();
        final String lockerCode = PassTxt.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(tel)) {
            UserTxt.setError("لطفا شماره تلفن همراه خود را وارد کنید:)");
            PassTxt.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lockerCode)) {
            UserTxt.setError("لطفا شماره لاکر خود را وارد کنید:)");
            PassTxt.requestFocus();
            return;
        }


        RequestQueue queue = Volley.newRequestQueue(this);

        String baseUrl = "https://sayehparsaei.com/GymAPI/";
        String file = "user";
        String uri = baseUrl + file;


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressBar.setVisibility(View.GONE);
                            Log.e("response",String.valueOf(response.length()));
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (obj.getInt("Error_Code")==200) {

                                //creating a new user object
                                User user = new User(
                                        obj.getString("User_ID"),
                                        obj.getString("Name"),
                                        obj.getString("Family"),
                                        obj.getString("Type_Name"),
                                        obj.getString("Token")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Tel", tel);
                params.put("Locker_Code", lockerCode);
                return params;
            }
        };

        queue.add(stringRequest);
    }


}