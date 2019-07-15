package com.sokool.intimacyup.model;

public class LevelResults {
    private String Level;
    private int playerOneTotal;
    private int playerTwoTotal;

    public LevelResults(String level, int playerOneTotal, int playerTwoTotal) {
        Level = level;
        this.playerOneTotal = playerOneTotal;
        this.playerTwoTotal = playerTwoTotal;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public int getPlayerOneTotal() {
        return playerOneTotal;
    }

    public void setPlayerOneTotal(int playerOneTotal) {
        this.playerOneTotal = playerOneTotal;
    }

    public int getPlayerTwoTotal() {
        return playerTwoTotal;
    }

    public void setPlayerTwoTotal(int playerTwoTotal) {
        this.playerTwoTotal = playerTwoTotal;
    }
}
