package com.example.m4hdyar.gym.lists;

import com.example.m4hdyar.gym.models.MealdietProgram;
import com.example.m4hdyar.gym.models.SportProgram;

import java.util.ArrayList;

//Because Event Handlers work only with parameters to make difference between arraylists they need to be a class
public class SportProgramList
{
    //Is it last item for queue and do something after that in event bus
    public ArrayList<SportProgram> arrList;
    public int volleyI; // Index of requests
    public SportProgramList(ArrayList<SportProgram> data) {
        volleyI=0;
        arrList = data;
    }

}
