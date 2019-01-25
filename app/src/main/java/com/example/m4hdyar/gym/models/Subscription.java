package com.example.m4hdyar.gym.models;



//It is our subscription history object
public class Subscription {
    String submitDate;
    String paid_Amount;
    String name;

    public Subscription(String submitDate, String paid_Amount, String name) {
        this.submitDate = submitDate;
        this.paid_Amount = paid_Amount;
        this.name = name;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public String getPaid_Amount() {
        return paid_Amount;
    }

    public String getName() {
        return name;
    }
}
