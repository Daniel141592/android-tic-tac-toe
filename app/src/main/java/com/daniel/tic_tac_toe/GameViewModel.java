package com.daniel.tic_tac_toe;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class GameViewModel extends AndroidViewModel {
    private ConnectionStatus connectionStatus;
    private GameRepository repository;
    private WebSocketsRepository webSocketsRepository;

    public GameViewModel(Application application) {
        super(application);
        repository = new GameRepository(application.getString(R.string.url));
    }

    private void startPlayingGame() {
        webSocketsRepository = new WebSocketsRepository(getApplication().getString(R.string.ws_url), repository.getOkHttpClient());
        webSocketsRepository.startListening();
    }

    public int getRoomID() {
        return repository.getRoomID();
    }

    public void setRoomID(int roomID) {
        repository.setRoomID(roomID);
    }

    public void checkIfCanJoinRoom(int roomID) {
        repository.checkIfCanJoinRoom(roomID);
    }

    public void joinRoom(String nick) {
        repository.joinRoom(nick);
    }

    public void create(String nick) {
        repository.create(nick);
    }

    public void update(int position) {
        webSocketsRepository.update(position);
    }

    public MutableLiveData<Room> getRoom() {
        return webSocketsRepository.getRoom();
    }

    public  MutableLiveData<StartGameRequest> getStartGameRequest() { return repository.getStartGameRequest(); }

    public MutableLiveData<Boolean> getError() {
        return repository.getError();
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
