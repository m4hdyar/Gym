package com.example.m4hdyar.gym;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.m4hdyar.gym.MealdietProgram.MealdietProgramDay.MealdietProgramRow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MealdietProgramActivity extends AppCompatActivity {

    //Defining Program RecyclerView and Adapter
    RecyclerView programRecyclerView;
    MealdietListAdapter programListAdapter,programListAdapter2;

    List<MealdietProgramRow> programRowsList,programRowsList2;

    //TODO:It is a test you can have array of programs too
    MealdietProgram programTest,programTest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealdiet_program);

        //Getting user Mealdiet programs



        programRowsList = new ArrayList<>();
        programRecyclerView = (RecyclerView) findViewById(R.id.programRecyclerView);
        programRecyclerView.setHasFixedSize(true);
        //Setting list horizontal and what is in recycler view (HINT :‌ There is a vertical linear layout in recycler view and another horizontal linear layout in that linear layout) :((((
        programRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //TODO:Delete this and connect to database
        //Creating some temp lists to see the result
        programTest = new MealdietProgram(new Date());
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
        programListAdapter = new MealdietListAdapter(this,programRowsList);
        programRecyclerView.setAdapter(programListAdapter);






        programTest2 = new MealdietProgram(new Date());
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
        programListAdapter2 = new MealdietListAdapter(this,programRowsList2);
        programRecyclerView.setAdapter(programListAdapter);


        Button testbutton = (Button) findViewById(R.id.changeList);
        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programRecyclerView.swapAdapter(programListAdapter2, false);
            }
        });




        //CREATING DIVIDERS
        //programRecyclerView.addItemDecoration(new RecyclerListDivider(this));
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(programRecyclerView.getContext(),DividerItemDecoration.VERTICAL);
        Drawable verticalDivider = ContextCompat.getDrawable(this,R.drawable.line_divider);
        verticalDecoration.setDrawable(verticalDivider);
        programRecyclerView.addItemDecoration(verticalDecoration);


        //TODO : It does not do what it should.
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(programRecyclerView.getContext(),DividerItemDecoration.HORIZONTAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(this,R.drawable.line_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        programRecyclerView.addItemDecoration(horizontalDecoration);

        //horizontalDivider.setDrawable();
        programRecyclerView.addItemDecoration(new DividerItemDecoration(programRecyclerView.getContext(),DividerItemDecoration.HORIZONTAL));
    }
}
