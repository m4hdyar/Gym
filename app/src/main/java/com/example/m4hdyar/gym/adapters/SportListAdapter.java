package com.example.m4hdyar.gym.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.m4hdyar.gym.R;
import com.example.m4hdyar.gym.models.SportProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * This Holds data of Meal Diet program and makes it ready to show in a recycler view.
 * https://www.youtube.com/watch?v=a4o9zFfyIM4
 * https://www.simplifiedcoding.net/android-recyclerview-cardview-tutorial/
 */
public class SportListAdapter extends RecyclerView.Adapter<SportListAdapter.SportProgramListViewHolder> {

    //We want context to get layout from it !! :/
    private Context mCtx;

    private List<SportProgram.SportProgramDay.SportProgramRow> sportProgramRowList;

    public SportListAdapter(Context mCtx, List<SportProgram.SportProgramDay.SportProgramRow> sportProgramRowList) {
        this.mCtx = mCtx;
        this.sportProgramRowList = sportProgramRowList;
    }


    //Let's override create view holder
    @NonNull
    @Override
    public SportProgramListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating layout from context
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //TODO:maybe changing null to parent?! https://stackoverflow.com/questions/30691150/match-parent-width-does-not-work-in-recyclerview
        View view = inflater.inflate(R.layout.sport_list_item_layout, parent,false);
        //Finally creating view holder

        /**
         * For perfomance we combine these lines.
         * MealDietListViewHolder vholder = new MealDietListViewHolder(view);
         * return vholder;
         */
        return new SportProgramListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SportProgramListViewHolder holder, int position) {
        //Now we are setting each row element data
        SportProgram.SportProgramDay.SportProgramRow sportProgramRow = sportProgramRowList.get(position);

        //TODO: Add String prefix
        holder.txtRowNumber.setText(sportProgramRow.getRowNumber());
        holder.txtMoveName.setText(sportProgramRow.getMoveName());
        holder.txtMoveNumberAndSet.setText(sportProgramRow.getMovesInSet() + " * " + sportProgramRow.getSetNumber());
    }

    @Override
    public int getItemCount() {
        return sportProgramRowList.size();
    }

    class SportProgramListViewHolder extends RecyclerView.ViewHolder{

        TextView txtRowNumber,txtMoveName,txtMoveNumberAndSet;

        public SportProgramListViewHolder(@NonNull View itemView) {
            super(itemView);
            //Finding list elements
            txtRowNumber = itemView.findViewById(R.id.txtRowNumber);
            txtMoveName = itemView.findViewById(R.id.txtMoveName);
            txtMoveNumberAndSet = itemView.findViewById(R.id.txtMoveNumberAndSet);
        }
    }

    public void updateSportAdapter(List<SportProgram.SportProgramDay.SportProgramRow> newList) {
        this.sportProgramRowList = new ArrayList<>();
        this.sportProgramRowList.addAll(newList);
        this.notifyDataSetChanged();
    }
}
