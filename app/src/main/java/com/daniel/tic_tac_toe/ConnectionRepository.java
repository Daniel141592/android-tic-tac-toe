package com.daniel.tic_tac_toe;

import androidx.lifecycle.MutableLiveData;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionRepository implements Callback<StartGameRequest> {
    private MutableLiveData<StartGameRequest> startGameRequest;
    private MutableLiveData<Boolean> error;
    private int roomID;
    private GameService gameService;
    private OkHttpClient okHttpClient;

    public ConnectionRepository(final String BASE_URL) {
        startGameRequest = new MutableLiveData<>();
        error = new MutableLiveData<>();
        okHttpClient = new OkHttpClient().newBuilder().cookieJar(new MyCookieJar()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gameService = retrofit.create(GameService.class);
    }

    @Override
    public void onResponse(Call<StartGameRequest> call, Response<StartGameRequest> response) {
        if (response.isSuccessful()) {
            error.setValue(false);
            startGameRequest.setValue(response.body());
        } else if (!error.getValue()) { //no need to set an error if it is already set
            error.setValue(true);
        }
    }

    @Override
    public void onFailure(Call<StartGameRequest> call, Throwable t) {
        if (error.getValue() == null || !error.getValue())
            error.setValue(true);
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
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

    public MutableLiveData<StartGameRequest> getStartGameRequest() {
        return startGameRequest;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public OkHttpClient getOkHttpClient() { return okHttpClient; }
}
