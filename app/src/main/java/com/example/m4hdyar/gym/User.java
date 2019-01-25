package com.example.m4hdyar.gym;

public class User {
    private String athleteId;
    public static String athleteName;
    public static String athleteFamily;
    public static String SubscriptionName;
    public static String token;



    public User(String athleteId, String athleteName, String athleteFamily, String subscriptionName, String token) {
        this.athleteId = athleteId;
        User.athleteName = athleteName;
        User.athleteFamily = athleteFamily;
        SubscriptionName = subscriptionName;
        User.token = token;
    }



    public String getAthleteId() {
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
