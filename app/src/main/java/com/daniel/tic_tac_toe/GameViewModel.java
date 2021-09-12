package com.daniel.tic_tac_toe;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import okhttp3.OkHttpClient;

/**
 * ViewModel class. Stores instances of ConnectionRepository and WebSocketsRepository.
 */
public class GameViewModel extends AndroidViewModel {
    private ConnectionStatus connectionStatus;
    private ConnectionRepository connectionRepository;
    private WebSocketsRepository webSocketsRepository;
    private OkHttpClient okHttpClient;

    /**
     * OkHttpClient object is created here and passed to connectionRepository.
     * @param application - Application object.
     */
    public GameViewModel(Application application) {
        super(application);
        okHttpClient = new OkHttpClient().newBuilder().cookieJar(new MyCookieJar()).build();
        connectionRepository = new ConnectionRepository(application.getString(R.string.url), okHttpClient);
    }

    /**
     * Create instance of WebSocketsRepository to start using websockets to update the game.
     */
    private void startPlayingGame() {
        webSocketsRepository = new WebSocketsRepository(getApplication().getString(R.string.ws_url), okHttpClient);
        webSocketsRepository.startListening();
    }

    public int getRoomID() {
        return connectionRepository.getRoomID();
    }

    public void setRoomID(int roomID) {
        connectionRepository.setRoomID(roomID);
    }

    public void checkIfCanJoinRoom(int roomID) {
        connectionRepository.checkIfCanJoinRoom(roomID);
    }

    public void joinRoom(String nick) {
        connectionRepository.joinRoom(nick);
    }

    public void create(String nick) {
        connectionRepository.create(nick);
    }

    public void update(int position) {
        webSocketsRepository.update(position);
    }

    public MutableLiveData<Room> getRoom() {
        return webSocketsRepository.getRoom();
    }

    public MutableLiveData<StartGameResponse> getStartGameRequest() { return connectionRepository.getStartGameRequest(); }

    public MutableLiveData<Boolean> getError() {
        return connectionRepository.getError();
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
        if (this.connectionStatus == ConnectionStatus.CONNECTED)
            startPlayingGame();
    }
}
