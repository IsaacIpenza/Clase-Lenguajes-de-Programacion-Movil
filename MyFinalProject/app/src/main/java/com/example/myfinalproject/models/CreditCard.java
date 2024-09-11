package com.example.myfinalproject.models;

import java.util.ArrayList;

public class CreditCard {

    private String CCId;
    private String CCName;
    private String CardNumber;
    private String CardEnding;
    private int TotalCredit;
    private int Debt;
    private String UserId;

    public CreditCard(String CCId, String CCName, String cardNumber, String cardEnding, int totalCredit, int debt, String userId) {
        this.CCId = CCId;
        this.CCName = CCName;
        CardNumber = cardNumber;
        CardEnding = cardEnding;
        TotalCredit = totalCredit;
        Debt = debt;
        UserId = userId;
    }

    public String getCCId() {
        return CCId;
    }

    public void setCCId(String CCId) {
        this.CCId = CCId;
    }

    public String getCCName() {
        return CCName;
    }

    public void setCCName(String CCName) {
        this.CCName = CCName;
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

    public int getTotalCredit() {
        return TotalCredit;
    }

    public void setTotalCredit(int totalCredit) {
        TotalCredit = totalCredit;
    }

    public int getDebt() {
        return Debt;
    }

    public void setDebt(int debt) {
        Debt = debt;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
