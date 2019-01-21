package com.example.m4hdyar.gym;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


    }

    private void testVolley() {

        RequestQueue queue = Volley.newRequestQueue(this);

        String baseUrl="https://sayehparsaei.com/GymAPI/";
        String file="Profile";
        String uri = baseUrl + file;

        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, uri, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    // The user does have repos, so let's loop through them all.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            // For each repo, add a new line to our repo list.
                            JSONObject jsonObj = response.getJSONObject(i);
                            String errorCode = jsonObj.get("Error_Code").toString();
                            System.out.println(errorCode + "HIIIIIII");
                            //String lastUpdated = jsonObj.get("updated_at").toString();
                            //addToRepoList(repoName, lastUpdated);
                        } catch (JSONException e) {
                            // If there is an error then output this to the logs.
                            //Log.e("Volley", "Invalid JSON Object.");
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        
        queue.add(arrReq);

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
//        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, uri,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // Check the length of our response (to see if the user has any repos)
//                        if (response.length() > 0) {
//                            // The user does have repos, so let's loop through them all.
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//                                    // For each repo, add a new line to our repo list.
//                                    JSONObject jsonObj = response.getJSONObject(i);
//                                    String repoName = jsonObj.get("name").toString();
//                                    String lastUpdated = jsonObj.get("updated_at").toString();
//                                    //addToRepoList(repoName, lastUpdated);
//                                } catch (JSONException e) {
//                                    // If there is an error then output this to the logs.
//                                    //Log.e("Volley", "Invalid JSON Object.");
//                                }
//
//                            }
//                        } else {
//                            // The user didn't have any repos.
//                            //setRepoListText("No repos found.");
//                        }
//
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // If there a HTTP error then add a note to our repo list.
//                        //setRepoListText("Error while calling REST API");
//                        //Log.e("Volley", error.toString());
//                    }
//                }
//        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        //requestQueue.add(arrReq);

    }
}
