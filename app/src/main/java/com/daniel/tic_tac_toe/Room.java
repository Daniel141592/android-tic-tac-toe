package com.daniel.tic_tac_toe;

public class Room {
    private boolean canJoin, canPlay, draw;
    private Integer[] board;
    private String[] nicks;
    private Integer playerNumber, turn, winner, roomID;

    public boolean isCanJoin() {
        return canJoin;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public Integer[] getBoard() {
        return board;
    }

    public String[] getNicks() {
        return nicks;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public Integer getTurn() {
        return turn;
    }

    public boolean isDraw() {
        return draw;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public Integer getWinner() {
        return winner;
    }
}
