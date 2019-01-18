package com.example.m4hdyar.gym;

import java.util.ArrayList;
import java.util.Date;

//One Mealdiet Program with so many days
public class MealdietProgram {

    //Need to check what is Date type
    private Date submitDate;
    //List of days each program have
    private ArrayList<MealdietProgramDay> programDays;

    //TODO:REMOVE STATICS
    public class MealdietProgramDay {
        private int dayNumber;
        //List of rows in each program
        private ArrayList<MealdietProgramRow> programRows;
        //Each row of Meal Diet Program
        public class MealdietProgramRow {
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

        //Day Constructor
        public MealdietProgramDay(int dayNumber) {
            this.dayNumber = dayNumber;
            this.programRows = new ArrayList<>();
        }

        //TODO:PRESCRIBED FOOD
        //Adding one row to a day
        public MealdietProgramRow addOneRow(String foodName,String mealAmount,String mealTime){
            MealdietProgramRow newRow = new MealdietProgramRow(foodName,mealAmount,mealTime);
            programRows.add(newRow);
            return newRow;

        }


        //Get specific program day from program
        public MealdietProgramRow getProgramRow(int rowNumber){
            return this.programRows.get(rowNumber);
        }
    }


    //Constructor
    public MealdietProgram(Date submitDate) {
        this.submitDate = submitDate;
        programDays = new ArrayList<>();
    }

    //Adding day to a program
    public MealdietProgramDay addOneDay(int dayNumber){
        MealdietProgramDay newDay=new MealdietProgramDay(dayNumber);
        programDays.add(newDay);
        return newDay;
    }

    //Get specific program day from program
    public MealdietProgramDay getProgramDay(int dayNumber){
        return this.programDays.get(dayNumber);
    }

}
