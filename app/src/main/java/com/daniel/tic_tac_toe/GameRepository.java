package com.daniel.tic_tac_toe;

import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameRepository implements Callback<Room> {
    private MutableLiveData<Room> room;
    private MutableLiveData<Boolean> error;
    private int roomID;
    private GameService gameService;

    public GameRepository(final String BASE_URL) {
        room = new MutableLiveData<>();
        error = new MutableLiveData<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient().newBuilder().cookieJar(new MyCookieJar()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gameService = retrofit.create(GameService.class);
    }

    @Override
    public void onResponse(Call<Room> call, Response<Room> response) {
        if (response.isSuccessful()) {
            error.setValue(false);
            room.setValue(response.body());
        } else {
            error.setValue(true);
        }
    }

    @Override
    public void onFailure(Call<Room> call, Throwable t) {
        error.setValue(true);
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void status() {
        gameService.status().enqueue(this);
    }

    public void checkIfCanJoinRoom(int roomID) {
        this.roomID = roomID;
        gameService.checkIfCanJoin(roomID).enqueue(this);
    }

    public void joinRoom(String nick) {
        gameService.joinRoom(roomID, new RequestParam(nick)).enqueue(this);
    }

    public void create(String nick) {
        gameService.create(new RequestParam(nick)).enqueue(this);
    }

    public void update(int b) {
        gameService.update(new RequestParam(b)).enqueue(this);
    }

    public MutableLiveData<Room> getRoom() {
        return room;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }
}
