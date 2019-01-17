package com.example.m4hdyar.gym;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.m4hdyar.gym.MealdietProgram.MealdietProgramDay;

import java.util.List;

/**
 * This Holds data of Meal Diet program and makes it ready to show in a recycler view.
 * https://www.youtube.com/watch?v=a4o9zFfyIM4
 * https://www.simplifiedcoding.net/android-recyclerview-cardview-tutorial/
 */
public class MealdietListAdapter extends RecyclerView.Adapter<MealdietListAdapter.MealDietListViewHolder> {


    private Context mCtx;
    private List<MealdietProgramDay.MealdietProgramRow> mealDietRowsList;

    public MealdietListAdapter(Context mCtx, List<MealdietProgramDay.MealdietProgramRow> mealdietRowsList) {
        this.mCtx = mCtx;
        this.mealdietRowsList = mealdietRowsList;
    }

    @NonNull
    @Override
    public MealDietListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MealDietListViewHolder holder, int position) {
        LayoutInflater inflater
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MealDietListViewHolder extends RecyclerView.ViewHolder{

        public MealDietListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
