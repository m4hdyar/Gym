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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.m4hdyar.gym.R;
import com.example.m4hdyar.gym.adapters.SportListAdapter;
import com.example.m4hdyar.gym.lists.SportDayRowsList;
import com.example.m4hdyar.gym.lists.SportDaysList;
import com.example.m4hdyar.gym.lists.SportProgramList;
import com.example.m4hdyar.gym.models.SportProgram;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SportProgramFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SportProgramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SportProgramFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     *
     *  Mine added Variables
     *
     */
    //Create context
    Context context;

    //Spinners
    Spinner sportProgramSpinner;
    Spinner sportDaySpinner;
    //Prefix before spinner
    String sportProgramSpinnerPrefix;
    String sportdaySpinnerPrefix;

    //Saving current program and day
    int currentSportProgram,currentSportDay;

    //Defining Program RecyclerView and Adapter
    RecyclerView sportProgramRecyclerView;
    SportListAdapter programListAdapter;
    SportListAdapter programListAdapter2;
    List<SportProgram.SportProgramDay.SportProgramRow> sportRowsList;

    ArrayList<SportProgram> programsArr;

    // * End of my variables *


    private OnFragmentInteractionListener mListener;

    public SportProgramFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SportProgramFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SportProgramFragment newInstance(String param1, String param2) {
        SportProgramFragment fragment = new SportProgramFragment();
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
        View view = inflater.inflate(R.layout.fragment_sport_program, container, false);
        context = getActivity();

        sportProgramSpinnerPrefix = getString(R.string.sport_program_spinner_prefix);
        sportdaySpinnerPrefix = getString(R.string.sport_day_spinner_prefix);
        //Current program on start is always 1 and day also 1
        currentSportProgram=1;
        currentSportDay=1;

        //Creating a list that gets Program lists
        programsArr = new ArrayList<>();

        //Find spinner view
        sportProgramSpinner = (Spinner) view.findViewById(R.id.sProgramSpinner);
        sportDaySpinner = (Spinner) view.findViewById(R.id.sProgramDaySpinner);


        sportProgramRecyclerView = (RecyclerView) view.findViewById(R.id.sProgramRecyclerView);
        sportProgramRecyclerView.setHasFixedSize(true);
        //Setting list horizontal and what is in recycler view (HINT :‌ There is a vertical linear layout in recycler view and another horizontal linear layout in that linear layout) :((((
        sportProgramRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        //TODO:Here
        //It is required but I don't know why?! (Now I know why :))
        //You need to assign adapter to recyclerView as soon as activity starts
        sportRowsList = new ArrayList<>();
        //Choose program list adapter value and assign adapter to recycle view
        programListAdapter = new SportListAdapter(context,sportRowsList);
        sportProgramRecyclerView.setAdapter(programListAdapter);

        //Update List
        updateProgramList();
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        toolbar.setBackground(new ColorDrawable(Color.RED));


        //What happens when an item in spinner is selected
        sportProgramSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Notify the selected item text
                String selectedItemText = (String) parent.getItemAtPosition(position);
                //Getting resource prefix size dynamically so you can change it
                final int prefixSize=sportProgramSpinnerPrefix.length();
                //Deleting prefix from item

                selectedItemText = selectedItemText.substring(prefixSize);
                //Getting integer value from text
                int selectedProgramNumber= Integer.parseInt(selectedItemText.trim());

                //Refresh the list with day 1 of program chosen
                currentSportProgram=selectedProgramNumber;
                refreshList(selectedProgramNumber,1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //What happens when an item in spinner day is selected
        sportDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Notify the selected item text
                String selectedItemText = (String) parent.getItemAtPosition(position);
                //Getting resource prefix size dynamically so you can change it
                final int prefixSize=sportdaySpinnerPrefix.length();
                //Deleting prefix from item
                selectedItemText = selectedItemText.substring(prefixSize);
                //Getting integer value from text
                int selectedDayNumber= Integer.parseInt(selectedItemText.trim());

                //Refresh the list with day 1 of program chosen
                refreshList(currentSportProgram,selectedDayNumber);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //CREATING DIVIDERS
        //programRecyclerView.addItemDecoration(new RecyclerListDivider(this));
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(sportProgramRecyclerView.getContext(),DividerItemDecoration.VERTICAL);
        Drawable verticalDivider = ContextCompat.getDrawable(context,R.drawable.line_divider);
        verticalDecoration.setDrawable(verticalDivider);
        sportProgramRecyclerView.addItemDecoration(verticalDecoration);


        //TODO : It does not do what it should.
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(sportProgramRecyclerView.getContext(),DividerItemDecoration.HORIZONTAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(context,R.drawable.line_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        sportProgramRecyclerView.addItemDecoration(horizontalDecoration);

        //horizontalDivider.setDrawable();
        sportProgramRecyclerView.addItemDecoration(new DividerItemDecoration(sportProgramRecyclerView.getContext(),DividerItemDecoration.HORIZONTAL));


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

        SportProgram.getSportProgramsReq(context);

    }

    /**
     *  From now Event bus event handlers Start
     */


    //Parsing response from get programs when getting event program get succeed!
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgramGetSucceed(SportProgramList programsArr) {
        //Foreach
        //Passing program list to main program list
        this.programsArr = programsArr.arrList;

        //Need to chain volley requests so requests go serial and not parallel.
        programsArr.volleyI+=1;

        //Check if there is another program that we didn't get it's days(Also rows).
        if(programsArr.volleyI<=this.programsArr.size()){

            this.programsArr.get(programsArr.volleyI-1).getSportDaysReq(context,programsArr);
        }else{
            //After all program is finished go to event and Do something after program call is finished something like finish call back
            onGettingListFinished();
        }


        //Oldie and not so goldie , It goes faster but maybe you get last row earlier than first rows so you have an error
//        for (MealdietProgram mealProgram: programsArr.arrList) {
//            mealProgram.getMealDietDays(context);
//        }


    }

    //Parsing response from get programs when getting event program get succeed!
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgramDaysGetSucceed(SportDaysList sportDaysList) {
        //TODO: Add them on top to select
        //Foreach
        //Need to chain volley requests so requests go serial and not parallel.
        sportDaysList.volleyI+=1;
        //Check if there is another day that we didn't get it's rows.
        if(sportDaysList.volleyI<=sportDaysList.arrList.size()){
            sportDaysList.arrList.get(sportDaysList.volleyI-1).getDayRowsReq(context,sportDaysList);
        }else{
            //If days in this program is finished let's go to parent program list to continue with another program
            EventBus.getDefault().post(sportDaysList.parentList);
        }
        //Do something after one diet day is finished something like finish call back
//        for (MealdietProgram.MealdietProgramDay dietDay: dietDaysArr.arrList) {
//            //IF that not worked.
////                dietDay.addThisToParentList();
//                dietDay.getMealDietRows(context);
//        }

    }

    //Parsing response from get programs when getting event program get succeed!
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgramRowsGetSucceed(SportDayRowsList sportDayRowsList) {
        //TODO: Add them on top to select
        //Foreach

        //When getting rows finished let's go to parent program to continue with another day
        EventBus.getDefault().post(sportDayRowsList.parentList);

//         if you want to do something for each rows uncomment this and create another volleyI in rows list
//        if(dietDayRowsArr.volleyI<=dietDayRowsArr.arrList.size()){
////            dietDayRowsArr.arrList.get(dietDayRowsArr.volleyI-1).getMealDietRows(context);
//        }else{
//
//        }

        //Do something for each row and find last row
//        for (MealdietProgram.MealdietProgramDay.MealdietProgramRow dietRow: dietDayRowsArr.arrList) {
//            //TODO: What to do for each row
//
//            if(dietRow.isLastRow() && dietRow.isLastDay() && dietRow.isLastProgram()){
//                onGettingListFinished();
//            }
//        }
    }

    //Called when all getting list is already finished.
    private void onGettingListFinished(){

        refreshList(currentSportProgram,currentSportDay);

        //Adding programs to spinner list
        List<String> programSpinnerArr =  new ArrayList<String>();
        for(SportProgram program : programsArr){

            programSpinnerArr.add(sportProgramSpinnerPrefix + String.valueOf(program.getProgramID()));
        }

        ArrayAdapter<String> programAdapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_dropdown_item, programSpinnerArr);

        programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportProgramSpinner.setAdapter(programAdapter);

        //Adding days to spinner list
        SportProgram thisProgram = findSportProgramInAList(currentSportProgram);
        List<String> daySpinnerArr =  new ArrayList<String>();

        assert thisProgram != null;
        for(SportProgram.SportProgramDay thisProgramDay : thisProgram.getProgramDaysList()){
            daySpinnerArr.add(sportdaySpinnerPrefix + String.valueOf(thisProgramDay.getDayNumber()));
        }

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_dropdown_item, daySpinnerArr);

        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportDaySpinner.setAdapter(dayAdapter);

//        System.out.println("Nooo");
//        ((MainActivity) getActivity()).setActionBarDiets();
    }

    //Called when you want to refresh list (Maybe not getting the list again from server)
    private void refreshList(int programID,int dayNumber){
        SportProgram chosenProgram;
        SportProgram.SportProgramDay chosenDay;
        //Getting program from program list

        chosenProgram = findSportProgramInAList(programID);
        if(chosenProgram==null) {
            return;
        }
        //Getting day from days list
        chosenDay = chosenProgram.getProgramDay(dayNumber);
        if(chosenDay==null){
            return;
        }

        ArrayList<SportProgram.SportProgramDay.SportProgramRow> rowsInChosenDay = chosenDay.getProgramRows();
        sportRowsList = new ArrayList<>();
        sportRowsList.addAll(rowsInChosenDay);

        //FINALLY SHOW THE LIST
        //programListAdapter = new MealdietListAdapter(context,programRowsList);
        //Updates list too
        programListAdapter.updateSportAdapter(sportRowsList);
//        Toolbar toolbar = (Toolbar) getActivity().too
//        menu.add(0, 0, 0, "Option1").setShortcut('3', 'c');
        //programRecyclerView.swapAdapter(programListAdapter2, false);
    }

    private SportProgram findSportProgramInAList(int programID){

        for(SportProgram sportProgram : this.programsArr){
            if(sportProgram.getProgramID()== programID){
                return sportProgram;
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
}
