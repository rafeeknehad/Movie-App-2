package com.example.moviesapp.model.Credits;

import com.example.moviesapp.model.Video.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CreditsApiInt {

    @GET("movie/{movie_id}/credits")
    Call<CreditsResponse> getCreditsForMovies(@Path("movie_id") Integer moveId,
                                              @Query("api_key") String apiKey);


    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getMovieVideo(@Path("movie_id") Integer movieId,
                                      @Query("api_key") String apiKey);

}
