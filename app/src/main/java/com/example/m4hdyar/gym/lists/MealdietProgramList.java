package com.example.m4hdyar.gym.lists;

import com.example.m4hdyar.gym.MealdietProgram;

import java.util.ArrayList;

//Because Event Handlers work only with parameters to make difference between arraylists they need to be a class
public class MealdietProgramList
{
    //Is it last item for queue and do something after that in event bus
    public ArrayList<MealdietProgram> arrList;
    public MealdietProgramList(ArrayList<MealdietProgram> data) {
        arrList = data;
    }
}
