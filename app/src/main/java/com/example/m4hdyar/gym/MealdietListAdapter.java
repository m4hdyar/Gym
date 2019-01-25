package com.example.m4hdyar.gym;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.m4hdyar.gym.MealdietProgram.MealdietProgramDay;

import java.util.ArrayList;
import java.util.List;

/**
 * This Holds data of Meal Diet program and makes it ready to show in a recycler view.
 * https://www.youtube.com/watch?v=a4o9zFfyIM4
 * https://www.simplifiedcoding.net/android-recyclerview-cardview-tutorial/
 */
public class MealdietListAdapter extends RecyclerView.Adapter<MealdietListAdapter.MealDietListViewHolder> {

    //We want context to get layout from it !! :/
    private Context mCtx;

    private List<MealdietProgramDay.MealdietProgramRow> mealdietRowsList;

    public MealdietListAdapter(Context mCtx, List<MealdietProgramDay.MealdietProgramRow> mealdietRowsList) {
        this.mCtx = mCtx;
        this.mealdietRowsList = mealdietRowsList;
    }


    //Let's override create view holder
    @NonNull
    @Override
    public MealDietListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating layout from context
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //TODO:maybe changing null to parent?! https://stackoverflow.com/questions/30691150/match-parent-width-does-not-work-in-recyclerview
        View view = inflater.inflate(R.layout.mealdiet_list_item_layout, parent,false);
        //Finally creating view holder

        /**
         * For perfomance we combine these lines.
         * MealDietListViewHolder vholder = new MealDietListViewHolder(view);
         * return vholder;
         */
        return new MealDietListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MealDietListViewHolder holder, int position) {
        //Now we are setting each row element data
        MealdietProgramDay.MealdietProgramRow mealdietRow = mealdietRowsList.get(position);

        holder.txtMealTime.setText(mealdietRow.getMealTime());
        holder.txtMealAmount.setText(mealdietRow.getMealAmount());
        holder.txtMealName.setText(mealdietRow.getFoodName());
    }

    @Override
    public int getItemCount() {
        return mealdietRowsList.size();
    }

    class MealDietListViewHolder extends RecyclerView.ViewHolder{

        TextView txtMealTime,txtMealName,txtMealAmount;

        public MealDietListViewHolder(@NonNull View itemView) {
            super(itemView);
            //Finding list elements
            txtMealAmount = itemView.findViewById(R.id.mealAmount);
            txtMealName = itemView.findViewById(R.id.mealName);
            txtMealTime = itemView.findViewById(R.id.mealTime);
        }
    }

    public void updateReceiptsList(List<MealdietProgramDay.MealdietProgramRow> newList) {
        this.mealdietRowsList = new ArrayList<>();
        this.mealdietRowsList.addAll(newList);
        this.notifyDataSetChanged();
    }
}
