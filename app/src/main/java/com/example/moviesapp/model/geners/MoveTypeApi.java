package com.example.moviesapp.model.geners;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoveTypeApi {

    @GET("genre/movie/list")
    Call<GenresResponse> getData(@Query("api_key") String api);
}
