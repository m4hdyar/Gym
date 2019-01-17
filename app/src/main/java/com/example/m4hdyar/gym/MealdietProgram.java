package com.example.m4hdyar.gym;

import java.util.ArrayList;
import java.util.Date;

//One Mealdiet Program with so many days
public class MealdietProgram {

    //Need to check what is Date type
    private Date submitDate;

    ArrayList<MealdietProgramDay> programDays;

    public class MealdietProgramDay {
        private int dayNumber;
        //Each row of Meal Diet Program
        public class MealdietProgramRow {
            private String foodName;
            private String foodAmount, mealTime;
            private class PrescribedFood {
            }
        }
    }

}
