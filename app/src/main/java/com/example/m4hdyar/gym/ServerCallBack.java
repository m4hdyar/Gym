package com.example.m4hdyar.gym;

import org.json.JSONObject;

public interface ServerCallBack {
    //We only need on fetch complete
    void onSucceed(JSONObject response);

//    void onFetchFailure(String msg);
//
//    void onFetchStart();
}
