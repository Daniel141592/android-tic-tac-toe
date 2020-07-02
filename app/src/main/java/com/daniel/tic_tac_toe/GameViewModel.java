package com.daniel.tic_tac_toe;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {
    private ConnectionStatus connectionStatus;
    private GameRepository repository;
    private static final String BASE_URL = "http://test.node.server:3000/api/";

    public GameViewModel() {
        repository = new GameRepository(BASE_URL);
    }

    public void status() {
        repository.status();
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

    public void update(int b) {
        repository.update(b);
    }

    public MutableLiveData<Room> getRoom() {
        return repository.getRoom();
    }

    public MutableLiveData<Boolean> getError() {
        return repository.getError();
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}
