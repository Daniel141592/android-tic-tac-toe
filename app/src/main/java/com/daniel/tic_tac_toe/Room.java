package com.daniel.tic_tac_toe;

public class Room {
    private boolean canJoin, canPlay;
    private Integer[] b;
    private String[] nicks;
    private Integer playerNumber, turn, roomID;

    public boolean isCanJoin() {
        return canJoin;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public Integer[] getB() {
        return b;
    }

    public String[] getNicks() {
        return nicks;
    }

    public void setCanJoin(boolean canJoin) {
        this.canJoin = canJoin;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public void setB(Integer[] b) {
        this.b = b;
    }

    public void setNicks(String[] nicks) {
        this.nicks = nicks;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }
}
