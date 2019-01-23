package com.example.m4hdyar.gym;

public class User {
    private int athleteId;
    private String athleteName;
    private String athleteFamily;
    private String SubscriptionName;
    private String token;


    public User(int athleteId, String athleteName, String athleteFamily, String subscriptionName, String token) {
        this.athleteId = athleteId;
        this.athleteName = athleteName;
        this.athleteFamily = athleteFamily;
        SubscriptionName = subscriptionName;
        this.token = token;
    }

    public int getAthleteId() {
        return athleteId;
    }

    public String getAthleteName() {
        return athleteName;
    }

    public String getAthleteFamily() {
        return athleteFamily;
    }

    public String getSubscriptionName() {
        return SubscriptionName;
    }

    public String getToken() {
        return token;
    }
}
