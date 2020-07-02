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

    public void setParam(String param) {
        this.nick = param;
    }

    public String getParam() {
        return nick;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }
}
