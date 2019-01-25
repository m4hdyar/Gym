package com.example.m4hdyar.gym.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.m4hdyar.gym.R;
import com.example.m4hdyar.gym.models.Subscription;
import com.example.m4hdyar.gym.adapters.SubscriptionListAdapter;
import com.example.m4hdyar.gym.models.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubscriptionHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubscriptionHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubscriptionHistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Defining list object
    RecyclerView subscriptionRecyclerView;
    SubscriptionListAdapter subscriptionListAdapter;
    List<Subscription> subscriptionList;
    Context context;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscriptionGetComplete(JSONObject response) {

        //TODO:Make an Order BY query

        try {
            //For each history we will create a subscription object
            JSONArray subsJsonArray = response.getJSONArray("Subscription_Histories");
            for (int i = 0; i < subsJsonArray.length(); i++) {
                JSONObject subsJson = subsJsonArray.getJSONObject(i);

                String name = subsJson.getString("Name");
                String submitDate = subsJson.getString("Submit_Date");
                String paidAmount = subsJson.getString("Paid_Amount");
                //Adding new subscription to subscription list
                subscriptionList.add(new Subscription(submitDate, paidAmount, name));
                //Setting if this program is last program
            }
            //FINALLY SHOW THE LIST
            subscriptionListAdapter = new SubscriptionListAdapter(context,subscriptionList);
            subscriptionRecyclerView.setAdapter(subscriptionListAdapter);
            subscriptionListAdapter.notifyDataSetChanged();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    //On start is not necessary but for event handling by Event Bus you need to register them here.
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

    private OnFragmentInteractionListener mListener;

    public SubscriptionHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubscriptionHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubscriptionHistoryFragment newInstance(String param1, String param2) {
        SubscriptionHistoryFragment fragment = new SubscriptionHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subscription_history, container, false);
        context = getActivity();

        subscriptionList = new ArrayList<>();

        subscriptionRecyclerView = (RecyclerView) view.findViewById(R.id.subscriptionRecyclerView);
        subscriptionRecyclerView.setHasFixedSize(true);
        //Setting list horizontal and what is in recycler view (HINT :‌ There is a vertical linear layout in recycler view and another horizontal linear layout in that linear layout) :((((
        subscriptionRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        subscriptionListAdapter = new SubscriptionListAdapter(context,subscriptionList);


        //CREATING DIVIDERS
        //programRecyclerView.addItemDecoration(new RecyclerListDivider(this));
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(subscriptionRecyclerView.getContext(),DividerItemDecoration.VERTICAL);
        Drawable verticalDivider = ContextCompat.getDrawable(context,R.drawable.line_divider);
        verticalDecoration.setDrawable(verticalDivider);
        subscriptionRecyclerView.addItemDecoration(verticalDecoration);


        //Finally let's go for the list
        refreshList(context);

        return view;
    }

    public void refreshList(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        //declaring subscriptionList here to interact with it in Volley

        String baseUrl="https://sayehparsaei.com/GymAPI/";
        String file="GetSubscriptionHistory";
        String uri = baseUrl + file;
        JsonObjectRequest objReq = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response.length() > 0) {
                    // The user does have repos, so let's loop through them all.
                    try {
                        if(response.getInt("Error_Code")==200) {

                            EventBus.getDefault().post(response);
                        }
                        //String lastUpdated = jsonObj.get("updated_at").toString();
                        //addToRepoList(repoName, lastUpdated);
                    } catch (JSONException e) {
                        // If there is an error then output this to the logs.
                        //Log.e("Volley", "Invalid JSON Object.");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
            //Passing tokens
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("TOKEN_VALUE", User.token);
                return headers;
            }
        };

        queue.add(objReq);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
}
