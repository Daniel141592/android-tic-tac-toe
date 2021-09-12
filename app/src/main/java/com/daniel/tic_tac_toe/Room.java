package com.daniel.tic_tac_toe;

/**
 * Simple class representing room - response from server during the game.
 */
public class Room {
    private boolean draw;
    private Integer[] board;
    private String[] nicks;
    private Integer playerNumber, turn, winner, roomID;

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
