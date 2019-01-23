package com.example.m4hdyar.gym;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.m4hdyar.gym.lists.DietDayRowsList;
import com.example.m4hdyar.gym.lists.DietDaysList;
import com.example.m4hdyar.gym.lists.MealdietProgramList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    //Create context
    Context context;

    //Menu menu;

    private OnFragmentInteractionListener mListener;

    //Defining Program RecyclerView and Adapter
    RecyclerView programRecyclerView;
    MealdietListAdapter programListAdapter;

    List<MealdietProgram.MealdietProgramDay.MealdietProgramRow> programRowsList;

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

        //Setting actionbar customizable
        setHasOptionsMenu(true);

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
        context = getActivity();

        //Creating a list that gets Program lists
        programsArr = new ArrayList<>();

        //Update List
        updateProgramList();
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        toolbar.setBackground(new ColorDrawable(Color.RED));

        programRecyclerView = (RecyclerView) view.findViewById(R.id.programRecyclerView);
        programRecyclerView.setHasFixedSize(true);
        //Setting list horizontal and what is in recycler view (HINT :‌ There is a vertical linear layout in recycler view and another horizontal linear layout in that linear layout) :((((
        programRecyclerView.setLayoutManager(new LinearLayoutManager(context));


        //It is required but I don't know why?!
        programRowsList = new ArrayList<>();
        //Choose program list adapter value and assign adapter to recycle view
        programListAdapter = new MealdietListAdapter(context,programRowsList);


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

    //Update the list
    private void updateProgramList() {
        //TODO:Here
        MealdietProgram.getMealDietPrograms(context);

    }

    /**
     *  From now Event bus event handlers Start
     */


    //Parsing response from get programs when getting event program get succeed!
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgramGetSucceed(MealdietProgramList programsArr) {
        //Foreach
        //Passing program list to main program list
        this.programsArr = programsArr.arrList;
        //Do something after program call is finished something like finish call back

        for (MealdietProgram mealProgram: programsArr.arrList) {
            mealProgram.getMealDietDays(context);
        }
//        for (MealdietProgram mealProgram: programsArr.arrList) {
//            Log.d("Meal_Diet",mealProgram.getProgramDay(1).getProgramRow(1).getFoodName());
//        }

    }

    //Parsing response from get programs when getting event program get succeed!
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgramDaysGetSucceed(DietDaysList dietDaysArr) {
        //TODO: Add them on top to select
        //Foreach
        //Do something after one diet day is finished something like finish call back
        for (MealdietProgram.MealdietProgramDay dietDay: dietDaysArr.arrList) {
            //IF that not worked.
//                dietDay.addThisToParentList();
                dietDay.getMealDietRows(context);
        }

    }

    //Parsing response from get programs when getting event program get succeed!
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgramRowsGetSucceed(DietDayRowsList dietDayRowsArr) {
        //TODO: Add them on top to select
        //Foreach
        //Do something for each row and find last row
        for (MealdietProgram.MealdietProgramDay.MealdietProgramRow dietRow: dietDayRowsArr.arrList) {
            //TODO: What to do for each row

            if(dietRow.isLastRow() && dietRow.isLastDay() && dietRow.isLastProgram()){
                onGettingListFinished();
            }

        }


    }

    //Called when all getting list is already finished.
    private void onGettingListFinished(){
        refreshList(1,1);
    }

    //Called when you want to refresh list (Maybe not getting the list again from server)
    private void refreshList(int programID,int dayNumber){
        MealdietProgram chosenDiet;
        MealdietProgram.MealdietProgramDay chosenDay;
        //Getting program from program list

        chosenDiet = findDietProgramInAList(programID);
        if(chosenDiet==null) {
            return;
        }
        //Getting day from days list
        chosenDay = chosenDiet.getProgramDay(dayNumber);
        if(chosenDay==null){
            return;
        }

        ArrayList<MealdietProgram.MealdietProgramDay.MealdietProgramRow> rowsInChosenDay = chosenDay.getProgramRows();


        //FINALLY SHOW THE FUCKING LIST
        programListAdapter = new MealdietListAdapter(context,rowsInChosenDay);
        programRecyclerView.setAdapter(programListAdapter);
        programListAdapter.notifyDataSetChanged();
//        Toolbar toolbar = (Toolbar) getActivity().too
//        menu.add(0, 0, 0, "Option1").setShortcut('3', 'c');
        //programRecyclerView.swapAdapter(programListAdapter2, false);
    }

    private MealdietProgram findDietProgramInAList(int programID){

        for(MealdietProgram mealdietProgram : this.programsArr){
            if(mealdietProgram.getProgramID()== programID){
                return mealdietProgram;
            }
        }
        return null;

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

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.program_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_program1:
                // do s.th.
                Log.d("Action_Setting", "Yesss");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
