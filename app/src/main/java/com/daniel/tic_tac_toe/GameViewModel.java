package com.daniel.tic_tac_toe;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class GameViewModel extends AndroidViewModel {
    private ConnectionStatus connectionStatus;
    private ConnectionRepository connectionRepository;
    private WebSocketsRepository webSocketsRepository;

    public GameViewModel(Application application) {
        super(application);
        connectionRepository = new ConnectionRepository(application.getString(R.string.url));
    }

    private void startPlayingGame() {
        webSocketsRepository = new WebSocketsRepository(getApplication().getString(R.string.ws_url), connectionRepository.getOkHttpClient());
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

    public MutableLiveData<StartGameRequest> getStartGameRequest() { return connectionRepository.getStartGameRequest(); }

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
