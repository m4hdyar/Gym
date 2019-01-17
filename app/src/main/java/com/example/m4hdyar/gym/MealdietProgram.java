package com.example.m4hdyar.gym;

import java.util.ArrayList;
import java.util.Date;

//One Mealdiet Program with so many days
public class MealdietProgram {

    //Need to check what is Date type
    private Date submitDate;

    ArrayList<MealdietProgramDay> programDays;
    //TODO:REMOVE STATICS
    public static class MealdietProgramDay {
        private int dayNumber;
        //Each row of Meal Diet Program
        public static class MealdietProgramRow {
            private String foodName;
            private String mealAmount, mealTime;
            private class PrescribedFood {
            }

            public MealdietProgramRow(String foodName, String mealAmount, String mealTime) {
                this.foodName = foodName;
                this.mealAmount = mealAmount;
                this.mealTime = mealTime;
            }

            public String getFoodName() {
                return foodName;
            }

            public String getMealAmount() {
                return mealAmount;
            }

            public String getMealTime() {
                return mealTime;
            }

        }

    }

}
