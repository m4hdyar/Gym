package com.example.m4hdyar.gym.lists;

import com.example.m4hdyar.gym.models.SportProgram;

import java.util.ArrayList;

public class SportDayRowsList
{
    public ArrayList<SportProgram.SportProgramDay.SportProgramRow> arrList;
    public SportDaysList parentList;

    //public int volleyI;
    public SportDayRowsList(ArrayList<SportProgram.SportProgramDay.SportProgramRow> data, SportDaysList parentData) {
        //volleyI = 0;
        arrList = data;
        parentList = parentData;
    }
}
