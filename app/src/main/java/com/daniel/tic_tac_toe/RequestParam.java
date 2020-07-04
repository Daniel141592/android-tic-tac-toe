package com.daniel.tic_tac_toe;

public class RequestParam {
    private String nick;
    private Integer position;

    public RequestParam(String param) {
        this.nick = param;
        this.position = null;
    }

    public RequestParam(int param) {
        this.nick = null;
        this.position = param;
    }
}
