package com.example.moviesapp.model.MoviesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApiInterface {

    @GET("movie/popular")
    Call<Movie> getAllPopularMovie(@Query("api_key") String api,@Query("page") String page);

    @GET("movie/top_rated")
    Call<Movie> getAllTopratedMovies(@Query("api_key") String api,@Query("page") String page);

    @GET("movie/now_playing")
    Call<Movie> getNowPlayingMovies(@Query("api_key") String api,@Query("page") String page);

    @GET("movie/upcoming")
    Call<Movie> getUpComingMovies(@Query("api_key") String api,@Query("page") String page);
}
