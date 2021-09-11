package com.daniel.tic_tac_toe;

public class StartGameRequest {
    private boolean canJoin, canPlay;
    private Integer roomID;

    public boolean isCanJoin() {
        return canJoin;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public Integer getRoomID() {
        return roomID;
    }
}
