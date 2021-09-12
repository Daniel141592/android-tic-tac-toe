package com.daniel.tic_tac_toe;

/**
 * Simple class representing servers response at the time of starting game (joining or starting new).
 */
public class StartGameResponse {
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
