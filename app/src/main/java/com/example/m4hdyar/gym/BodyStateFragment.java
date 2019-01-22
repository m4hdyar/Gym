package com.example.m4hdyar.gym;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.m4hdyar.gym.BodyStateList.fatList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BodyStateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BodyStateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BodyStateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //tag for custom queue
    private final String REQUEST_TAG = getClass().getName();
    //
    private EventBus eventBus2;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyEvent(JSONObject response) {
        try {
            JSONArray profileContentArr = response.getJSONArray("BodyStates");
            for (int i = 0; i < profileContentArr.length(); i++) {

                JSONObject profileContent = profileContentArr.getJSONObject(i);

                String submitDate = profileContent.getString("Submit_Date");
                float fat = Float.valueOf(profileContent.getString("Fat"));
                //add data to list
                fatList.add(new BodyStateList.FatBodyState(submitDate,fat,i));
            }
            fillFatBar();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BodyStateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BodyStateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BodyStateFragment newInstance(String param1, String param2) {
        BodyStateFragment fragment = new BodyStateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    BarChart barChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          View view =inflater.inflate(R.layout.fragment_body_state, container, false);

        //test getting value of fat
//        BodyStateList.addFatBodyState("2019/01/22",24.3f,1);
//        BodyStateList.addFatBodyState("2019/01/22",78.5f,1);
//        BodyStateList.addFatBodyState("2019/01/22",12.3f,1);
//        BodyStateList.addFatBodyState("2019/01/22",20.3f,1);
//
//        for(int i = 0; i < BodyStateList.fatList.size(); i++){
//            System.out.println(BodyStateList.fatList.get(i).getFat());
//        }




        //testing how barGraph shows
        barChart = (BarChart) view.findViewById(R.id.Bar_Graph);
//
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        barEntries.add(new BarEntry(35f,0));
//        barEntries.add(new BarEntry(30f,1));
//        barEntries.add(new BarEntry(20f,2));
//        barEntries.add(new BarEntry(21f,3));
//        barEntries.add(new BarEntry(28f,4));
//        barEntries.add(new BarEntry(51f,5));
//        BarDataSet barDataSet = new BarDataSet(barEntries,"Data");
//
//        ArrayList<String> Mounth = new ArrayList<>();
//        Mounth.add("mehr");
//        Mounth.add("aban");
//        Mounth.add("azar");
//        Mounth.add("dey");
//
//        BarData barData = new BarData (barDataSet);
//        barChart.setData(barData);
//
//        barChart.setTouchEnabled(true);
//        barChart.setDragEnabled(true);
//        barChart.setScaleEnabled(true);

        //Getting data in sequence, then importing them in front-end
//        getFatData(new ServerCallBack() {
//            @Override
//            public void onSucceed(JSONObject response) {
//
//            }
//        });


        getFatData();

        // Inflate the layout for this fragment
        return view;
    }

    public void onEvent(JSONObject response){
        try {
            JSONArray profileContentArr = response.getJSONArray("BodyStates");
            for (int i = 0; i < profileContentArr.length(); i++) {

                JSONObject profileContent = profileContentArr.getJSONObject(i);

                String submitDate = profileContent.getString("Submit_Date");
                float fat = Float.valueOf(profileContent.getString("Fat"));
                //add data to list
                fatList.add(new BodyStateList.FatBodyState(submitDate,fat,i));
            }
            fillFatBar();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onEvent(String response) {
    //    textView.setText(response);
    }

    public void onEvent(VolleyError error) {
      //  textView.setText("That didn't work!");
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        WebRequestQueue.getInstance(getActivity()).cancelAll(REQUEST_TAG);
//        EventBus.getDefault().unregister(getActivity());
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        WebRequestQueue.getInstance(getActivity()).cancelAll(REQUEST_TAG);
//        EventBus.getDefault().register(getActivity());
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //get data of athlete's fat
    private void getFatData() {
        final EventBus eventBus=EventBus.getDefault();//create eventBus
        final Context context = getActivity();
        RequestQueue queue = Volley.newRequestQueue(context);

        String baseUrl = "https://sayehparsaei.com/GymAPI/";
        String file = "GetBodyState";
        String uri = baseUrl + file;
        //It's what fragment push in queue - Get  data and it specified by token and token set when user login
        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {

                    try {
                        if (response.getInt("Error_Code")==200){
                            //serverCallBack.onSucceed(response);
                            EventBus.getDefault().post(response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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


        //queue.add(arrReq);//Send order in queue to run
        WebRequestQueue.getInstance(context).addToQueue(arrReq,REQUEST_TAG);
    }
    private void fillFatBar(){

//        barChart = (BarChart) getView().findViewById(R.id.Bar_Graph);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(BodyStateList.fatList.get(0).getFatId(),BodyStateList.fatList.get(0).getFat()));
        barEntries.add(new BarEntry(BodyStateList.fatList.get(1).getFatId(),BodyStateList.fatList.get(1).getFat()));
        barEntries.add(new BarEntry(BodyStateList.fatList.get(2).getFatId(),BodyStateList.fatList.get(2).getFat()));
        Log.e("BarChart", String.valueOf(BodyStateList.fatList.get(0).getFatId()));
        BarDataSet barDataSet = new BarDataSet(barEntries,"Data");

        BarData barData = new BarData (barDataSet);
        barChart.setData(barData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

    }

    public class MessageEvent{
        MessageEvent(JSONObject response){
            try {
                JSONArray profileContentArr = response.getJSONArray("BodyStates");
                for (int i = 0; i < profileContentArr.length(); i++) {

                    JSONObject profileContent = profileContentArr.getJSONObject(i);

                    String submitDate = profileContent.getString("Submit_Date");
                    float fat = Float.valueOf(profileContent.getString("Fat"));
                    //add data to list
                    fatList.add(new BodyStateList.FatBodyState(submitDate,fat,i));
                }
                fillFatBar();
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    //@Subscribe(threadMode = ThreadMode.MAIN)
    //public void onMessageEvent{

//    }

}
