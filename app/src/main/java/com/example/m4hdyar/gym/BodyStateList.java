package com.example.m4hdyar.gym;

import java.util.ArrayList;

public class BodyStateList {
    static ArrayList<DataBodyState> dataBodyStatesList;
    static{
        dataBodyStatesList = new ArrayList<>();
    }

    static class DataBodyState {
        private String submitDate;
        private float dataList;
        private int dataListId;

        public DataBodyState(String submitDate, float dataList, int dataListId) {
            this.submitDate = submitDate;
            this.dataList = dataList;
            this.dataListId = dataListId;
        }

        public String getSubmitDate() {
            return submitDate;
        }

        public float getDataList() {
            return dataList;
        }

        public int getDataListId() {
            return dataListId;
        }
    }
    public static void addDataBodyState(String submitDate, float dataList, int dataListId){

        dataBodyStatesList.add(new DataBodyState(submitDate,dataList,dataListId));

    }

}
