package com.example.m4hdyar.gym;

import java.util.ArrayList;

public class BodyStateList {
    static ArrayList<FatBodyState> fatList;
    static{
        fatList = new ArrayList<>();
    }

    static class FatBodyState {
        private String submitDate;
        private float fat;
        private int fatId;

        public FatBodyState(String submitDate, float fat, int fatId) {
            this.submitDate = submitDate;
            this.fat = fat;
            this.fatId = fatId;
        }

        public String getSubmitDate() {
            return submitDate;
        }

        public float getFat() {
            return fat;
        }

        public int getFatId() {
            return fatId;
        }
    }
    public static void addFatBodyState(String submitDate, float fat, int fatId){

        fatList.add(new FatBodyState(submitDate,fat,fatId));

    }

}
