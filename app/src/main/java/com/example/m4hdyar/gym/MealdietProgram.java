package com.example.m4hdyar.gym;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.m4hdyar.gym.lists.DietDayRowsList;
import com.example.m4hdyar.gym.lists.DietDaysList;
import com.example.m4hdyar.gym.lists.MealdietProgramList;

import org.greenrobot.eventbus.EventBus;
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
    //Is last program to do something after that probably with eventbus
    private boolean isLastProgram;
    //List of days each program have
    private ArrayList<MealdietProgramDay> programDays;

    public class MealdietProgramDay {
        private int dayNumber;
        private int dayID;
        //Is last day in a program to do something after that probably with eventbus
        private boolean isLastDay;
        //List of rows in each program
        private ArrayList<MealdietProgramRow> programRows;

        public void addThisToParentList() {
            MealdietProgram.this.programDays.add(this);
        }

        //Each row of Meal Diet Program
        public class MealdietProgramRow {
            private String foodName;
            private String mealAmount, mealTime,rowNumber;

            //Is last row in a day to do something after that probably with eventbus
            private boolean isLastRow;
//            private class PrescribedFood {
//
//            }

            public MealdietProgramRow(String foodName, String mealAmount, String mealTime,String rowNumber) {
                this.foodName = foodName;
                this.mealAmount = mealAmount;
                this.mealTime = mealTime;
                this.rowNumber = rowNumber;
                this.isLastRow=false;
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



            public boolean isLastProgram() {
                return MealdietProgram.this.isLastProgram();
            }
            public boolean isLastDay() {
                return MealdietProgramDay.this.isLastDay();
            }
            public boolean isLastRow() {
                return isLastRow;
            }

            public void setLastRow(boolean lastRow) {
                isLastRow = lastRow;
            }
        }

        //Day Constructor
        public MealdietProgramDay(int dayNumber,int dayID) {
            this.dayNumber = dayNumber;
            this.dayID = dayID;
            this.programRows = new ArrayList<>();
            this.isLastDay = false;
        }

        public boolean isLastDay() {
            return isLastDay;
        }

        public void setLastDay(boolean lastDay) {
            isLastDay = lastDay;
        }

        public int getDayID() {
            return dayID;
        }

        public int getDayNumber() {
            return dayNumber;
        }

        //TODO:PRESCRIBED FOOD
        //Adding one row to a day
        public MealdietProgramRow addOneRow(String foodName,String mealAmount,String mealTime){
            //TODO:Delete 1
            MealdietProgramRow newRow = new MealdietProgramRow(foodName,mealAmount,mealTime,"1");
            programRows.add(newRow);
            return newRow;

        }

        public ArrayList<MealdietProgramRow> getProgramRows() {
            return programRows;
        }

        //Get specific program day from program
        public MealdietProgramRow getProgramRow(int rowNumber){
            return this.programRows.get(rowNumber);
        }

        public void getMealDietRows(Context context, final DietDaysList parentList){
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
                                Log.d("Volley",response.toString());
                                //This time for getting meal diet days
                                JSONArray rowsArr = response.getJSONArray("Meal_Diets");
                                for(int i = 0; i < rowsArr.length();i++){
                                    JSONObject rowJson= rowsArr.getJSONObject(i);
                                    //Converting strings to int
                                    String thisRowNumber=rowJson.getString("Row_Number");
                                    String thisRowMealTime=rowJson.getString("Meal_Time");
                                    String thisRowAmount=rowJson.getString("Amount");
                                    String thisRowName=rowJson.getString("Name");


                                    MealdietProgramRow newMealdietProgramRow = new MealdietProgramRow(thisRowName,thisRowAmount,thisRowMealTime,thisRowNumber);
                                    //Setting if this program is last program
                                    if(i==rowsArr.length()-1) {
                                        newMealdietProgramRow.setLastRow(true);
                                    }
                                    mealdietRowsArrayList.add(newMealdietProgramRow);

                                }
                                //Changing array list to an object to send it with event bus
                                DietDayRowsList dietDayRowsList = new DietDayRowsList(mealdietRowsArrayList,parentList);
                                //Adding this to Day class list so it always know his children :))
                                MealdietProgramDay.this.programRows=mealdietRowsArrayList;
                                EventBus.getDefault().post(dietDayRowsList);
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
//TODO:Here
            queue.add(arrReq);
//            RequestQueue serialRequestQueue = SerialRequestQueue.getSerialRequestQueue(context);
//            serialRequestQueue.start();
//            serialRequestQueue.add(arrReq);
        }

    }


    //Constructor
    public MealdietProgram(String submitDate,int programID) {
        this.submitDate = submitDate;
        this.programID = programID;
        programDays = new ArrayList<>();
        isLastProgram=false;
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

        for(MealdietProgramDay mealdietProgramDay : programDays){

            if(mealdietProgramDay.getDayNumber()==dayNumber){
                return mealdietProgramDay;
            }

        }
        //Return null if not find
        return null;
    }

    //Getting meal diet programs from server
    public static void getMealDietPrograms(Context context){
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
                            //JSON PARSING
                            try {
                                JSONArray dietsArr = response.getJSONArray("Meal_Diets");
                                for(int i = 0; i < dietsArr.length();i++){
                                    JSONObject programJson= dietsArr.getJSONObject(i);
                                    String thisDietSubmitDate = programJson.getString("Submit_Date");
                                    //Converting diet id as integer to string
                                    int thisDietID = Integer.parseInt(programJson.getString("Meal_Diet_ID"));
                                    MealdietProgram newMealdietProgram = new MealdietProgram(thisDietSubmitDate,thisDietID);
                                    //Setting if this program is last program
                                    if(i == dietsArr.length()-1) {
                                        newMealdietProgram.setLastProgram(true);
                                    }
                                    mealdietProgramArrayList.add(newMealdietProgram);
                                }
                                //Changing array list to an object to send it with event bus
                                MealdietProgramList mealdietProgramList = new MealdietProgramList(mealdietProgramArrayList);
                                EventBus.getDefault().post(mealdietProgramList);
                            } catch (JSONException e) {
                                e.printStackTrace();
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
    }

    //Getting one meal diet days from server
    public void getMealDietDays(Context context, final MealdietProgramList parentList){
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
                                MealdietProgramDay mealdietProgramDay = new MealdietProgramDay(thisDayNumber,thisDayID);
                                //Setting if this program is last program
                                if(i == daysArr.length()-1) {
                                    mealdietProgramDay.setLastDay(true);
                                }
                                mealdietDaysArrayList.add(mealdietProgramDay);
                            }

                            //Changing array list to an object to send it with event bus
                            DietDaysList dietDaysList = new DietDaysList(mealdietDaysArrayList,parentList);
                            //Adding this to Program class list so it always know his children :))
                            MealdietProgram.this.programDays=mealdietDaysArrayList;
                            EventBus.getDefault().post(dietDaysList);
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
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public int getProgramID() {
        return programID;
    }

    public boolean isLastProgram() {
        return isLastProgram;
    }

    public void setLastProgram(boolean lastProgram) {
        isLastProgram = lastProgram;
    }

    public ArrayList<MealdietProgramDay> getProgramDaysList() {
        return programDays;
    }
}
