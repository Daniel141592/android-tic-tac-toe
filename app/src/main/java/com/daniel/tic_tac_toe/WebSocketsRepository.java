package com.daniel.tic_tac_toe;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.tinder.scarlet.Scarlet;
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter;
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory;
import com.tinder.scarlet.websocket.okhttp.OkHttpClientUtils;

import okhttp3.OkHttpClient;

/**
 * Class manages the Game using websockets (update the board and change info text).
 * Stores MutableLiveData for room details.
 */
public class WebSocketsRepository {
    private final WebSocketsService webSocketsService;
    private final MutableLiveData<Room> roomMutableLiveData;

    /**
     * Websockets connection created here using Scarlet.
     * @param WS_URL - URL used by websockets server
     * @param okHttpClient - http Client, must be the same that was used in pre-game connection.
     */
    public WebSocketsRepository(String WS_URL, OkHttpClient okHttpClient) {
        roomMutableLiveData = new MutableLiveData<>();
        Scarlet scarletInstance = new Scarlet.Builder()
                .webSocketFactory(OkHttpClientUtils.newWebSocketFactory(okHttpClient, WS_URL))
                .addMessageAdapterFactory(new GsonMessageAdapter.Factory())
                .addStreamAdapterFactory(new RxJava2StreamAdapterFactory())
                .build();
        webSocketsService = scarletInstance.create(WebSocketsService.class);
    }

    public void update(int position) {
        webSocketsService.update(position);
    }

    @SuppressLint("CheckResult")
    public void startListening() {
        webSocketsService.observe().subscribe(roomMutableLiveData::postValue);
    }

    public MutableLiveData<Room> getRoom() { return roomMutableLiveData; }
}
