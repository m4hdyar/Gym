package com.example.m4hdyar.gym;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //variables of profile page
    EditText txtEmail;
    EditText txtFullName;
    EditText txtTel;
    EditText txtImage;
    EditText txtSubscriptionName;
    EditText txtRemainDays;
    EditText txtLatestSubscriptionDate;
    ImageView imgAthlete;

    String imageAddr;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container,false);
        // Inflate the layout for this fragment
        txtEmail = (EditText) view.findViewById(R.id.txtUserEmail);
        txtFullName = (EditText) view.findViewById(R.id.txtFullName);
        txtTel = (EditText) view.findViewById(R.id.txtTel);
        txtSubscriptionName = (EditText) view.findViewById(R.id.txtUserSubscription);
        txtRemainDays = (EditText) view.findViewById(R.id.txtRemainDays);
        txtLatestSubscriptionDate = (EditText) view.findViewById(R.id.txtLatestSubmitSubscription);
        imgAthlete =(ImageView) view.findViewById(R.id.imgUserImage);

        fillInTheProfile();



        return view;
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

    private void fillInTheProfile() {

        final Context context = getActivity();
        RequestQueue queue = Volley.newRequestQueue(context);

        String baseUrl = "https://sayehparsaei.com/GymAPI/";
        String file = "Profile";
        String uri = baseUrl + file;
        //It's what fragment push in queue - Get  data and it specified by token and token set when user login
        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {

                    try {
                        JSONArray profileContentArr = response.getJSONArray("Profile_Content");
                        for(int i = 0; i < profileContentArr.length();i++){

                            JSONObject profileContent= profileContentArr.getJSONObject(i);



                            String name = profileContent.getString("Name");
                            String family = profileContent.getString("Family");
                            txtFullName.append(name+" "+family);



                            String subscriptionName = profileContent.getString("Subscription_Name");
                            txtSubscriptionName.append(subscriptionName);

                            String remainDays = profileContent.getString("Remain_Days");
                            txtRemainDays.append(remainDays+" مانده است");

                            String latestSubmitDate = profileContent.getString("Latest_Subscription_Date");
                            txtLatestSubscriptionDate.append(latestSubmitDate);


                            String email = profileContent.getString("Email");
                            txtEmail.append(email);

                            String tel = profileContent.getString("Tel");
                            txtTel.append(tel);

                            imageAddr = profileContent.getString("Image");
                            Picasso.get().load(imageAddr).into(imgAthlete, new Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.e("Picasso","Succeed");
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e("Picasso","Fail");
                                    e.printStackTrace();

                                }
                            });

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

        queue.add(arrReq);//Send order in queue to run
    }
}
