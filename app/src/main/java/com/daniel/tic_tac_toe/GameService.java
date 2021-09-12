package com.daniel.tic_tac_toe;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GameService {
    @GET("rooms/{id}/check")
    Call<StartGameResponse> checkIfCanJoin(@Path("id") int id);

    @POST("rooms/{id}/join")
    Call<StartGameResponse> joinRoom(@Path("id") int id, @Body RequestParam param);

    @POST("rooms/create")
    Call<StartGameResponse> create(@Body RequestParam param);
}
