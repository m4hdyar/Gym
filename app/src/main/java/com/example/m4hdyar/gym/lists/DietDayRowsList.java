package com.example.m4hdyar.gym.lists;

import com.example.m4hdyar.gym.models.MealdietProgram;

import java.util.ArrayList;

public class DietDayRowsList
{
    public ArrayList<MealdietProgram.MealdietProgramDay.MealdietProgramRow> arrList;
    public DietDaysList parentList;

    //public int volleyI;
    public DietDayRowsList(ArrayList<MealdietProgram.MealdietProgramDay.MealdietProgramRow> data,DietDaysList parentData) {
        //volleyI = 0;
        arrList = data;
        parentList = parentData;
    }
}
