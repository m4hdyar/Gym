package com.example.m4hdyar.gym;

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
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MealdietProgramFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MealdietProgramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealdietProgramFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Defining Program RecyclerView and Adapter
    RecyclerView programRecyclerView;
    MealdietListAdapter programListAdapter,programListAdapter2;

    List<MealdietProgram.MealdietProgramDay.MealdietProgramRow> programRowsList,programRowsList2;

    //TODO:It is a test you can have array of programs too
    MealdietProgram programTest,programTest2;
    ArrayList<MealdietProgram> programsArr;


    public MealdietProgramFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MealdietProgramFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealdietProgramFragment newInstance(String param1, String param2) {
        MealdietProgramFragment fragment = new MealdietProgramFragment();
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
        //TODO: Save this to SQLite
        //This is to find view(NOTE :‌ It is fragment not activity)
        View view = inflater.inflate(R.layout.fragment_mealdiet_program, container, false);
        Context context = getActivity();

        programsArr = new ArrayList<>();
        MealdietProgram.getMealDietPrograms(context, new ServerCallBack() {
            @Override
            public void onSucceed(JSONObject response) {
                try {
                    JSONArray dietsArr = response.getJSONArray("Meal_Diets");
                    for(int i = 0; i < dietsArr.length();i++){
                        JSONObject programJson= dietsArr.getJSONObject(i);
                        String thisDietSubmitDate = programJson.getString("Submit_Date");
                        //Converting diet id as integer to string
                        int thisDietID = Integer.parseInt(programJson.getString("Meal_Diet_ID"));
                        programsArr.add(new MealdietProgram(thisDietSubmitDate,thisDietID));
                    }
                    for(int i=0;i<programsArr.size();i++){
                        System.out.println(programsArr.get(i).getSubmitDate());
                        System.out.println(programsArr.get(i).getProgramID());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        programRowsList = new ArrayList<>();
        programRecyclerView = (RecyclerView) view.findViewById(R.id.programRecyclerView);
        programRecyclerView.setHasFixedSize(true);
        //Setting list horizontal and what is in recycler view (HINT :‌ There is a vertical linear layout in recycler view and another horizontal linear layout in that linear layout) :((((
        programRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        //TODO:Delete this and connect to database
        //Creating some temp lists to see the result
        programTest = new MealdietProgram("2018-02-21",1);
        MealdietProgram.MealdietProgramDay programDayTest = programTest.addOneDay(0);

        programRowsList.add(
                programDayTest.addOneRow(
                        "yechizi",
                        "Ziyad",
                        "zood")
        );
        programRowsList.add(
                programDayTest.addOneRow(
                        "yechiziye dg",
                        "Ziyade",
                        "zoode")
        );
        programRowsList.add(
                programDayTest.addOneRow(
                        "pofak",
                        "Ziyad",
                        "harmoghe")
        );
        programRowsList.add(
                programDayTest.addOneRow(
                        "chips",
                        "Ziyad",
                        "dir")
        );
        //Choose program list adapter value and assign adapter to recycle view
        programListAdapter = new MealdietListAdapter(context,programRowsList);
        programRecyclerView.setAdapter(programListAdapter);






        programTest2 = new MealdietProgram("2018-02-20",1);
        MealdietProgram.MealdietProgramDay programDayTest2 = programTest2.addOneDay(1);

        programRowsList2 = new ArrayList<>();
        programRowsList2.add(
                programDayTest2.addOneRow(
                        "pizza",
                        "200g",
                        "asr")
        );
        programRowsList2.add(
                programDayTest2.addOneRow(
                        "sandwich",
                        "550g",
                        "sob")
        );
        programRowsList2.add(
                programDayTest2.addOneRow(
                        "pofak",
                        "Ziyad",
                        "harmoghe")
        );
        programRowsList2.add(
                programDayTest2.addOneRow(
                        "burger",
                        "1kg",
                        "shab")
        );
        programRowsList2.add(
                programDayTest2.addOneRow(
                        "HI",
                        "HUU",
                        "HRWG")
        );

        //Choose program list adapter value and assign adapter to recycle view
        programListAdapter2 = new MealdietListAdapter(context,programRowsList2);
        programRecyclerView.setAdapter(programListAdapter);


        Button testbutton = (Button) view.findViewById(R.id.changeList);
        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programRecyclerView.swapAdapter(programListAdapter2, false);
            }
        });




        //CREATING DIVIDERS
        //programRecyclerView.addItemDecoration(new RecyclerListDivider(this));
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(programRecyclerView.getContext(),DividerItemDecoration.VERTICAL);
        Drawable verticalDivider = ContextCompat.getDrawable(context,R.drawable.line_divider);
        verticalDecoration.setDrawable(verticalDivider);
        programRecyclerView.addItemDecoration(verticalDecoration);


        //TODO : It does not do what it should.
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(programRecyclerView.getContext(),DividerItemDecoration.HORIZONTAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(context,R.drawable.line_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        programRecyclerView.addItemDecoration(horizontalDecoration);

        //horizontalDivider.setDrawable();
        programRecyclerView.addItemDecoration(new DividerItemDecoration(programRecyclerView.getContext(),DividerItemDecoration.HORIZONTAL));


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


    //Added by me
//    ServerCallBack programFetchListener=new ServerCallBack() {
//        @Override
//        public void onSucceed(JSONObject response) {
//
//        }
//    };
//    ServerCallBack programDaysFetchListener=new ServerCallBack() {
//        @Override
//        public void onSucceed(JSONObject response) {
//
//        }
//    };
//    ServerCallBack programRowsFetchListener=new ServerCallBack() {
//        @Override
//        public void onSucceed(JSONObject response) {
//
//        }
//    };
}
