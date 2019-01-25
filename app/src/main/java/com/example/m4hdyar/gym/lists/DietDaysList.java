package com.example.m4hdyar.gym.lists;

import com.example.m4hdyar.gym.MealdietProgram;

import java.util.ArrayList;

public class DietDaysList
{
    public ArrayList<MealdietProgram.MealdietProgramDay> arrList;
    public MealdietProgramList parentList;
    public int volleyI;
    public DietDaysList(ArrayList<MealdietProgram.MealdietProgramDay> data,MealdietProgramList parentData) {
        volleyI = 0;
        arrList = data;
        parentList = parentData;
    }
}
