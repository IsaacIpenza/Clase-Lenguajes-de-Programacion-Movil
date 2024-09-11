package com.example.myfinalproject.models;

import java.time.LocalDate;

public class Movements {

    private String MoveId;
    private String CardId;
    private int Amount;
    private String MoveType; //Deposit or Pay
    private LocalDate MoveDate;
    private String Description;

    private String CardName = "";
    private String CardEnding = "";


    public Movements(String moveId, String cardId, int amount, String moveType, LocalDate moveDate, String description) {
        MoveId = moveId;
        CardId = cardId;
        Amount = amount;
        MoveType = moveType;
        MoveDate = moveDate;
        Description = description;
    }

    public String getMoveId() {
        return MoveId;
    }

    public void setMoveId(String moveId) {
        MoveId = moveId;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getMoveType() {
        return MoveType;
    }

    public void setMoveType(String moveType) {
        MoveType = moveType;
    }

    public LocalDate getMoveDate() {
        return MoveDate;
    }

    public void setMoveDate(LocalDate moveDate) {
        MoveDate = moveDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getCardEnding() {
        return CardEnding;
    }

    public void setCardEnding(String cardEnding) {
        CardEnding = cardEnding;
    }
}
