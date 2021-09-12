package com.daniel.tic_tac_toe;

import androidx.lifecycle.MutableLiveData;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class manages connection (joining a game or starting new).
 * Stores MutableLiveData for servers response and errors.
 */
public class ConnectionRepository implements Callback<StartGameResponse> {
    private final MutableLiveData<StartGameResponse> startGameRequest;
    private final MutableLiveData<Boolean> error;
    private int roomID;
    private final GameService gameService;

    /**
     * Create retrofit object to manage connection in constructor.
     * @param BASE_URL - base URL of api endpoint
     * @param okHttpClient - client using to connect
     */
    public ConnectionRepository(final String BASE_URL, OkHttpClient okHttpClient) {
        startGameRequest = new MutableLiveData<>();
        error = new MutableLiveData<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gameService = retrofit.create(GameService.class);
    }

    @Override
    public void onResponse(Call<StartGameResponse> call, Response<StartGameResponse> response) {
        if (response.isSuccessful()) {
            error.setValue(false);
            startGameRequest.setValue(response.body());
        } else if (!error.getValue()) { //no need to set an error if it is already set
            error.setValue(true);
        }
    }

    @Override
    public void onFailure(Call<StartGameResponse> call, Throwable t) {
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

    public MutableLiveData<StartGameResponse> getStartGameRequest() {
        return startGameRequest;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }
}
