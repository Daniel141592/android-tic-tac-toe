package com.daniel.tic_tac_toe;

import android.app.Application;
import android.os.Handler;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class GameViewModel extends AndroidViewModel {
    private ConnectionStatus connectionStatus;
    private GameRepository repository;
    private static final int INTERVAL = 1000;
    private Handler handler;
    private Runnable runnable = this::refresh;
    private boolean refreshing;

    public GameViewModel(Application application) {
        super(application);
        repository = new GameRepository(application.getString(R.string.url));
        handler = new Handler();
        refreshing = false;
    }

    public int getRoomID() {
        return repository.getRoomID();
    }

    public void setRoomID(int roomID) {
        repository.setRoomID(roomID);
    }

    private void refresh() {
        if (!refreshing)
            return;
        repository.status();
        handler.postDelayed(runnable, INTERVAL);
    }

    public void startRefreshing() {
        if (!refreshing) {
            refreshing = true;
            refresh();
        }
    }

    public void stopRefreshing() {
        refreshing = false;
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
