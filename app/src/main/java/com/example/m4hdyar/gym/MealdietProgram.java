package com.example.m4hdyar.gym;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//One Mealdiet Program with so many days
public class MealdietProgram {

    //Need to check what is Date type
    //TODO:Change it to date
    private String submitDate;
    private int programID;
    //List of days each program have
    private ArrayList<MealdietProgramDay> programDays;

    public class MealdietProgramDay {
        private int dayNumber;
        private int dayID;
        //List of rows in each program
        private ArrayList<MealdietProgramRow> programRows;
        //Each row of Meal Diet Program
        public class MealdietProgramRow {
            private String foodName;
            private String mealAmount, mealTime,rowNumber;

//            private class PrescribedFood {
//
//            }

            public MealdietProgramRow(String foodName, String mealAmount, String mealTime,String rowNumber) {
                this.foodName = foodName;
                this.mealAmount = mealAmount;
                this.mealTime = mealTime;
                this.rowNumber = rowNumber;
            }

            public String getFoodName() {
                return foodName;
            }

            public String getMealAmount() {
                return mealAmount;
            }

            public String getMealTime() {
                return mealTime;
            }

        }

        //Day Constructor
        public MealdietProgramDay(int dayNumber,int dayID) {
            this.dayNumber = dayNumber;
            this.programRows = new ArrayList<>();
        }

        //TODO:PRESCRIBED FOOD
        //Adding one row to a day
        public MealdietProgramRow addOneRow(String foodName,String mealAmount,String mealTime){
            //TODO:Delete 1
            MealdietProgramRow newRow = new MealdietProgramRow(foodName,mealAmount,mealTime,"1");
            programRows.add(newRow);
            return newRow;

        }


        //Get specific program day from program
        public MealdietProgramRow getProgramRow(int rowNumber){
            return this.programRows.get(rowNumber);
        }

        public ArrayList<MealdietProgramRow> getMealDietRows(Context context,final ServerCallBack serverCallBack){
            //TODO: Get token from login page
            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMSIsImV4cCI6MTU0OTM1NDA0OSwiaXNzIjoibG9jYWxob3N0IiwiaWF0IjoxNTQ4MDU4MDQ5fQ.3SdO6mUfur51-mfoKq_psdPoMJGYE9BB5M-cbb9bvx8";

            RequestQueue queue = Volley.newRequestQueue(context);

            final String baseUrl = "https://sayehparsaei.com/GymAPI/";
            final String file = "GetMealDiet/";
            final String programID = String.valueOf(MealdietProgram.this.programID);
            final String urlSeparator = "/";
            final String dayID = String.valueOf(this.dayID);
            //Something like https://sayehparsae.com/GymAPI/GetMealDiet/1/1
            final String uri = baseUrl + file + programID + urlSeparator + dayID;

            //Create an array list
            final ArrayList<MealdietProgramRow> mealdietRowsArrayList = new ArrayList<>();
            JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response.length() > 0) {

                        //Checking if Error code is 200 and everything is fine
                        //TODO:Other error codes need to be checked
                        try {
                            if(response.getInt("Error_Code")==200){
                                //This time for getting meal diet days
                                JSONArray rowsArr = response.getJSONArray("Meal_Diets");
                                for(int i = 0; i < rowsArr.length();i++){
                                    JSONObject rowJson= rowsArr.getJSONObject(i);
                                    //Converting strings to int
                                    String thisRowNumber=rowJson.getString("Row_Number");
                                    String thisRowMealTime=rowJson.getString("Meal_Time");
                                    String thisRowAmount=rowJson.getString("Amount");
                                    String thisRowName=rowJson.getString("Name");
                                    mealdietRowsArrayList.add(new MealdietProgramRow(thisRowName,thisRowAmount,thisRowMealTime,thisRowNumber));
                                }
                                serverCallBack.onSucceed(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
//                    try {
//                        JSONArray profileContentArr = response.getJSONArray("Profile_Content");
//                        for(int i = 0; i < profileContentArr.length();i++){
//                            JSONObject profileContent= profileContentArr.getJSONObject(i);
//                            String email = profileContent.getString("Email");
//                            txtEmail.append(email);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                        Log.d("Volley",response.toString());

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", "Error on response."+error.getMessage());
                }
            })

            {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("TOKEN_VALUE", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMSIsImV4cCI6MTU0OTM1NDA0OSwiaXNzIjoibG9jYWxob3N0IiwiaWF0IjoxNTQ4MDU4MDQ5fQ.3SdO6mUfur51-mfoKq_psdPoMJGYE9BB5M-cbb9bvx8");
                    return headers;
                }
            };

            queue.add(arrReq);


            return mealdietRowsArrayList;
        }

    }


    //Constructor
    public MealdietProgram(String submitDate,int programID) {
        this.submitDate = submitDate;
        this.programID = programID;
        programDays = new ArrayList<>();
    }

    //Adding day to a program
    public MealdietProgramDay addOneDay(int dayNumber){
        //TODO:Delete 1
        MealdietProgramDay newDay=new MealdietProgramDay(dayNumber,1);
        programDays.add(newDay);
        return newDay;
    }

    //Get specific program day from program
    public MealdietProgramDay getProgramDay(int dayNumber){
        return this.programDays.get(dayNumber);
    }

    //Getting meal diet programs from server
    public static void getMealDietPrograms(Context context,final ServerCallBack serverCallBack){
        //TODO: Get token from login page
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMSIsImV4cCI6MTU0OTM1NDA0OSwiaXNzIjoibG9jYWxob3N0IiwiaWF0IjoxNTQ4MDU4MDQ5fQ.3SdO6mUfur51-mfoKq_psdPoMJGYE9BB5M-cbb9bvx8";

        RequestQueue queue = Volley.newRequestQueue(context);

        final String baseUrl = "https://sayehparsaei.com/GymAPI/";
        final String file = "GetMealDiet";
        final String uri = baseUrl + file;

        //Create an array list
        final ArrayList<MealdietProgram> mealdietProgramArrayList = new ArrayList<>();

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {


                    //Checking if Error code is 200 and everything is fine
                    //TODO:Other error codes need to be checked
                    try {
                        if(response.getInt("Error_Code")==200){
                            //JSON PARSING IS IN CALLBACK
                            serverCallBack.onSucceed(response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//
//                    try {
//                        JSONArray profileContentArr = response.getJSONArray("Profile_Content");
//                        for(int i = 0; i < profileContentArr.length();i++){
//                            JSONObject profileContent= profileContentArr.getJSONObject(i);
//                            String email = profileContent.getString("Email");
//                            txtEmail.append(email);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                    Log.d("Volley",response.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error on response."+error.getMessage());
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("TOKEN_VALUE", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMSIsImV4cCI6MTU0OTM1NDA0OSwiaXNzIjoibG9jYWxob3N0IiwiaWF0IjoxNTQ4MDU4MDQ5fQ.3SdO6mUfur51-mfoKq_psdPoMJGYE9BB5M-cbb9bvx8");
                return headers;
            }
        };

        queue.add(arrReq);


        return;
    }

    //Getting one meal diet days from server
    public ArrayList<MealdietProgramDay> getMealDietDays(Context context,final ServerCallBack serverCallBack){
        //TODO: Get token from login page
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMSIsImV4cCI6MTU0OTM1NDA0OSwiaXNzIjoibG9jYWxob3N0IiwiaWF0IjoxNTQ4MDU4MDQ5fQ.3SdO6mUfur51-mfoKq_psdPoMJGYE9BB5M-cbb9bvx8";

        RequestQueue queue = Volley.newRequestQueue(context);

        final String baseUrl = "https://sayehparsaei.com/GymAPI/";
        final String file = "GetMealDiet/";
        final String programID = String.valueOf(this.programID);
        //Something like https://sayehparsae.com/GymAPI/GetMealDiet/1
        final String uri = baseUrl + file + programID;

        //Create an array list
        final ArrayList<MealdietProgramDay> mealdietDaysArrayList = new ArrayList<>();

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {

                    //Checking if Error code is 200 and everything is fine
                    //TODO:Other error codes need to be checked
                    try {
                        if(response.getInt("Error_Code")==200){
                            //This time for getting meal diet days
                            JSONArray daysArr = response.getJSONArray("Meal_Diets");
                            for(int i = 0; i < daysArr.length();i++){
                                JSONObject dayJson= daysArr.getJSONObject(i);
                                //Converting strings to int
                                int thisDayNumber = Integer.parseInt(dayJson.getString("Day_Number"));
                                int thisDayID = Integer.parseInt(dayJson.getString("Meal_Diet_Day_ID"));
                                mealdietDaysArrayList.add(new MealdietProgramDay(thisDayNumber,thisDayID));
                                serverCallBack.onSucceed(response);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//
//                    try {
//                        JSONArray profileContentArr = response.getJSONArray("Profile_Content");
//                        for(int i = 0; i < profileContentArr.length();i++){
//                            JSONObject profileContent= profileContentArr.getJSONObject(i);
//                            String email = profileContent.getString("Email");
//                            txtEmail.append(email);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                    Log.d("Volley",response.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error on response."+error.getMessage());
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("TOKEN_VALUE", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMSIsImV4cCI6MTU0OTM1NDA0OSwiaXNzIjoibG9jYWxob3N0IiwiaWF0IjoxNTQ4MDU4MDQ5fQ.3SdO6mUfur51-mfoKq_psdPoMJGYE9BB5M-cbb9bvx8");
                return headers;
            }
        };

        queue.add(arrReq);


        return mealdietDaysArrayList;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public int getProgramID() {
        return programID;
    }
}
