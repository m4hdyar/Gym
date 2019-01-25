package com.example.m4hdyar.gym;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_ID = "keyid";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_FAMILY = "keyfamily";
    private static final String KEY_SUBSCRIPTION_NAME = "keysubscribtionname";
    private static final String KEY_TOKEN ="token";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.getAthleteId());
        editor.putString(KEY_NAME, user.getAthleteName());
        editor.putString(KEY_FAMILY, user.getAthleteFamily());
        editor.putString(KEY_SUBSCRIPTION_NAME, user.getSubscriptionName());
        editor.putString(KEY_TOKEN, user.getToken());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getUser();
        return sharedPreferences.getString(KEY_NAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_FAMILY, null),
                sharedPreferences.getString(KEY_SUBSCRIPTION_NAME, null),
                sharedPreferences.getString(KEY_TOKEN, null)
        );
    }

    //this method will logout the user
    public void logout(Context context) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ((Activity)context).finish();//Block back button to MainActivity
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    //TODO::complete this method
    //this method will keep user logged in
    public boolean stillLogIn(){
            if(User.token==null){
                SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                //TODO:Check back button
                mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
                return false;
            }
            return true;

    }
}