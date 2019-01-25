package com.example.m4hdyar.gym.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.m4hdyar.gym.lists.SportDayRowsList;
import com.example.m4hdyar.gym.lists.SportDaysList;
import com.example.m4hdyar.gym.lists.SportProgramList;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//One Mealdiet Program with so many days
public class SportProgram {

    //Need to check what is Date type
    //TODO:Change it to date
    private String submitDate;
    private int programID;
    //Is last program to do something after that probably with eventbus
    private boolean isLastProgram;
    //List of days each program have
    private ArrayList<SportProgramDay> programDays;

    public class SportProgramDay {
        private int dayNumber;
        private int dayID;
        //Is last day in a program to do something after that probably with eventbus
        private boolean isLastDay;
        //List of rows in each program
        private ArrayList<SportProgramRow> programDayRows;

        public void addThisToParentList() {
            SportProgram.this.programDays.add(this);
        }

        //Each row of Sport day Program
        public class SportProgramRow {
            private String moveName,rowNumber,setNumber,movesInSet;

            //Is last row in a day to do something after that probably with eventbus
            private boolean isLastRow;
//            private class PrescribedFood {
//
//            }

            public SportProgramRow(String rowNumber, String moveName, String setNumber,String movesInSet) {
                this.rowNumber = rowNumber;
                this.moveName = moveName;
                this.setNumber = setNumber;
                this.movesInSet = movesInSet;
                this.isLastRow=false;
            }

            public String getMoveName() {
                return moveName;
            }

            public String getSetNumber() {
                return setNumber;
            }

            public String getMovesInSet() {
                return movesInSet;
            }

            public String getRowNumber() { return rowNumber; }

            public boolean isLastProgram() {
                return SportProgram.this.isLastProgram();
            }
            public boolean isLastDay() { return SportProgramDay.this.isLastDay(); }
            public boolean isLastRow() {
                return isLastRow;
            }

            public void setLastRow(boolean lastRow) {
                isLastRow = lastRow;
            }
        }

        //Day Constructor
        public SportProgramDay(int dayNumber,int dayID) {
            this.dayNumber = dayNumber;
            this.dayID = dayID;
            this.programDayRows = new ArrayList<>();
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
        public SportProgramRow addOneRow(String rowNumber,String moveName,String setNumber,String movesInSet){
            //TODO:Delete 1
            SportProgramRow newRow = new SportProgramRow(rowNumber,moveName,setNumber,movesInSet);
            programDayRows.add(newRow);
            return newRow;

        }

        public ArrayList<SportProgramRow> getProgramRows() {
            return programDayRows;
        }

        //Get specific program day from program
        public SportProgramRow getProgramRow(int rowNumber){
            return this.programDayRows.get(rowNumber);
        }

        public void getDayRowsReq(Context context, final SportDaysList parentList){

            RequestQueue queue = Volley.newRequestQueue(context);

            final String baseUrl = "https://sayehparsaei.com/GymAPI/";
            final String file = "GetSportProgram/";
            final String programID = String.valueOf(SportProgram.this.programID);
            final String urlSeparator = "/";
            final String dayID = String.valueOf(this.dayID);
            //Something like https://sayehparsae.com/GymAPI/GetSportProgram/1/1
            final String uri = baseUrl + file + programID + urlSeparator + dayID;

            //Create an array list
            final ArrayList<SportProgramRow> sportProgramRowsArrayList = new ArrayList<>();
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
                                JSONArray rowsArr = response.getJSONArray("Sport_Programs");
                                for(int i = 0; i < rowsArr.length();i++){
                                    JSONObject rowJson= rowsArr.getJSONObject(i);
                                    //Converting strings to int
                                    String thisRowNumber=rowJson.getString("Row_Number");
                                    String thisMoveName=rowJson.getString("Name");
                                    String thisSetNumber=rowJson.getString("Set_number");
                                    String thisMovesInSet=rowJson.getString("Moves_In_Set");



                                    SportProgramRow newSportProgramRow = new SportProgramRow(thisRowNumber,thisMoveName,thisSetNumber,thisMovesInSet);
                                    //Setting if this program is last program
                                    if(i==rowsArr.length()-1) {
                                        newSportProgramRow.setLastRow(true);
                                    }
                                    sportProgramRowsArrayList.add(newSportProgramRow);

                                }
                                //Changing array list to an object to send it with event bus
                                SportDayRowsList sportDayRowsList = new SportDayRowsList(sportProgramRowsArrayList,parentList);
                                //Adding this to Day class list so it always know his children :))
                                SportProgramDay.this.programDayRows=sportProgramRowsArrayList;
                                EventBus.getDefault().post(sportDayRowsList);
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
                    headers.put("TOKEN_VALUE", User.token);
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
    public SportProgram(String submitDate, int programID) {
        this.submitDate = submitDate;
        this.programID = programID;
        programDays = new ArrayList<>();
        isLastProgram=false;
    }

    //Adding day to a program
    public SportProgramDay addOneDay(int dayNumber){
        //TODO:Delete 1
        SportProgramDay newDay=new SportProgramDay(dayNumber,1);
        programDays.add(newDay);
        return newDay;
    }

    //Get specific program day from program
    public SportProgramDay getProgramDay(int dayNumber){

        for(SportProgramDay sportProgramDay : programDays){

            if(sportProgramDay.getDayNumber()==dayNumber){
                return sportProgramDay;
            }

        }
        //Return null if not find
        return null;
    }

    //Getting meal diet programs from server
    public static void getSportProgramsReq(Context context){

        RequestQueue queue = Volley.newRequestQueue(context);

        final String baseUrl = "https://sayehparsaei.com/GymAPI/";
        final String file = "GetSportProgram/";
        final String uri = baseUrl + file;

        //Create an array list
        final ArrayList<SportProgram> sportProgramArrayList = new ArrayList<>();

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
                                JSONArray dietsArr = response.getJSONArray("Sport_Programs");
                                for(int i = 0; i < dietsArr.length();i++){
                                    JSONObject programJson= dietsArr.getJSONObject(i);
                                    String thisProgramSubmitDate = programJson.getString("Submit_Date");
                                    //Converting diet id as integer to string
                                    int thisProgramID = Integer.parseInt(programJson.getString("Sport_Program_ID"));
                                    SportProgram newSportProgram = new SportProgram(thisProgramSubmitDate,thisProgramID);
                                    //Setting if this program is last program
                                    if(i == dietsArr.length()-1) {
                                        newSportProgram.setLastProgram(true);
                                    }
                                    sportProgramArrayList.add(newSportProgram);
                                }
                                //Changing array list to an object to send it with event bus
                                SportProgramList mealdietProgramList = new SportProgramList(sportProgramArrayList);
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
                headers.put("TOKEN_VALUE", User.token);
                return headers;
            }
        };

        queue.add(arrReq);
    }

    //Getting one meal diet days from server
    public void getSportDaysReq(Context context, final SportProgramList parentList){
        RequestQueue queue = Volley.newRequestQueue(context);

        final String baseUrl = "https://sayehparsaei.com/GymAPI/";
        final String file = "GetSportProgram/";
        final String programID = String.valueOf(this.programID);
        //Something like https://sayehparsae.com/GymAPI/GetMealDiet/1
        final String uri = baseUrl + file + programID;

        //Create an array list
        final ArrayList<SportProgramDay> sportProgramDaysArrayList = new ArrayList<>();

        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {

                    //Checking if Error code is 200 and everything is fine
                    //TODO:Other error codes need to be checked
                    try {
                        if(response.getInt("Error_Code")==200){
                            //This time for getting meal diet days
                            JSONArray daysArr = response.getJSONArray("Sport_Programs");
                            for(int i = 0; i < daysArr.length();i++){
                                JSONObject dayJson= daysArr.getJSONObject(i);
                                //Converting strings to int
                                int thisDayNumber = Integer.parseInt(dayJson.getString("Day_Number"));
                                int thisDayID = Integer.parseInt(dayJson.getString("Sport_Program_Day_ID"));
                                SportProgramDay mealdietProgramDay = new SportProgramDay(thisDayNumber,thisDayID);
                                //Setting if this program is last program
                                if(i == daysArr.length()-1) {
                                    mealdietProgramDay.setLastDay(true);
                                }
                                sportProgramDaysArrayList.add(mealdietProgramDay);
                            }

                            //Changing array list to an object to send it with event bus
                            SportDaysList sportDaysList = new SportDaysList(sportProgramDaysArrayList,parentList);
                            //Adding this to Program class list so it always know his children :))
                            SportProgram.this.programDays=sportProgramDaysArrayList;
                            EventBus.getDefault().post(sportDaysList);
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
                headers.put("TOKEN_VALUE", User.token);
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

    public ArrayList<SportProgramDay> getProgramDaysList() {
        return programDays;
    }
}
