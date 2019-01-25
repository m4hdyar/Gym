package com.example.m4hdyar.gym.lists;

import com.example.m4hdyar.gym.models.MealdietProgram;
import com.example.m4hdyar.gym.models.SportProgram;

import java.util.ArrayList;

public class SportDaysList
{
    public ArrayList<SportProgram.SportProgramDay> arrList;
    public SportProgramList parentList;
    public int volleyI;
    public SportDaysList(ArrayList<SportProgram.SportProgramDay> data, SportProgramList parentData) {
        volleyI = 0;
        arrList = data;
        parentList = parentData;
    }
}
