package com.example.myfinalproject.models;

import java.util.ArrayList;

public class DebitCard {
    private String DCId;
    private String DCName;
    private String CardNumber;
    private String CardEnding;
    private int TotalBalance;
    private String UserId;

    public DebitCard(String DCId, String DCName, String cardNumber, String cardEnding, int totalBalance, String userId) {
        this.DCId = DCId;
        this.DCName = DCName;
        CardNumber = cardNumber;
        CardEnding = cardEnding;
        TotalBalance = totalBalance;
        UserId = userId;
    }

    public String getDCId() {
        return DCId;
    }

    public void setDCId(String DCId) {
        this.DCId = DCId;
    }

    public String getDCName() {
        return DCName;
    }

    public void setDCName(String DCName) {
        this.DCName = DCName;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getCardEnding() {
        return CardEnding;
    }

    public void setCardEnding(String cardEnding) {
        CardEnding = cardEnding;
    }

    public int getTotalBalance() {
        return TotalBalance;
    }

    public void setTotalBalance(int totalBalance) {
        TotalBalance = totalBalance;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
