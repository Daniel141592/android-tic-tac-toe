package com.daniel.tic_tac_toe;

public class RequestParam {
    private String nick;
    private Integer b;

    public RequestParam(String param) {
        this.nick = param;
        this.b = null;
    }

    public RequestParam(int param) {
        this.nick = null;
        this.b = param;
    }
}
