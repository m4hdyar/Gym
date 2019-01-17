package com.example.m4hdyar.gym;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.m4hdyar.gym.MealdietProgram.MealdietProgramDay.MealdietProgramRow;

import java.util.ArrayList;
import java.util.List;

public class MealdietProgramActivity extends AppCompatActivity {

    //Defining Program RecyclerView and Adapter
    RecyclerView programRecyclerView;
    MealdietListAdapter programListAdapter;

    List<MealdietProgramRow> programRowsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealdiet_program);

        programRowsList = new ArrayList<>();
        programRecyclerView = (RecyclerView) findViewById(R.id.programRecyclerView);
        programRecyclerView.setHasFixedSize(true);
        //Setting list horizontal and what is in recycler view (HINT :‌ There is a vertical linear layout in recycler view and another horizontal linear layout in that linear layout) :((((
        programRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Creating some temp lists to see the result
        programRowsList.add(
                new MealdietProgramRow(
                        "yechizi",
                        "Ziyad",
                        "zood")
        );
        programRowsList.add(
                new MealdietProgramRow(
                        "yechiziye dg",
                        "Ziyade",
                        "zoode")
        );
        programRowsList.add(
                new MealdietProgramRow(
                        "pofak",
                        "Ziyad",
                        "harmoghe")
        );
        programRowsList.add(
                new MealdietProgramRow(
                        "chips",
                        "Ziyad",
                        "dir")
        );
    }
}
