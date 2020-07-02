package com.daniel.tic_tac_toe;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GameService {

    @GET(".")
    Call<Room> status();

    @GET("rooms/{id}/check")
    Call<Room> checkIfCanJoin(@Path("id") int id);

    @POST("rooms/{id}/join")
    Call<Room> joinRoom(@Path("id") int id, @Body RequestParam param);

    @POST("rooms/create")
    Call<Room> create(@Body RequestParam param);

    @POST("rooms/update")
    Call<Room> update(@Body RequestParam param);
}
