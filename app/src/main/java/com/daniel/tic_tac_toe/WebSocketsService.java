package com.daniel.tic_tac_toe;

import com.tinder.scarlet.ws.Receive;
import com.tinder.scarlet.ws.Send;

import io.reactivex.Flowable;

public interface WebSocketsService {
    @Receive
    Flowable<Room> observe();

    @Send
    void update(int position);
}
