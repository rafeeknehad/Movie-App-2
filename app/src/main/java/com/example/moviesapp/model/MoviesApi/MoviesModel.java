package com.example.moviesapp.model.MoviesApi;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.moviesapp.View.flashScreen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesModel {
    private static String api_key = "939d60a07bf55c9dc7b0a3a0db237e02";
    private Context mContext;
    private Movie movieList;

    public MoviesModel(String move) {

        new AsyTask().execute(move);
    }

    public static class AsyTask extends AsyncTask<String, Void, Void> {

        private MoviesApiInterface moviesApiInterface;
        private MoveModelPresenter moveModelPresenter;

        @Override
        protected Void doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            moviesApiInterface = retrofit.create(MoviesApiInterface.class);
            moveModelPresenter = (MoveModelPresenter) new MoviePresenter();
            if (strings[0].equals("popular_movies")) {
                popularMovies(strings[0]);
            } else if (strings[0].equals("top_rated_movies")) {
                topratedMovies(strings[0]);
            } else if (strings[0].equals("now_playing_movies")) {
                nowPlayingMovies(strings[0]);
            } else if (strings[0].equals("up_coming_movies")) {
                upComingMovies(strings[0]);
            }
            return null;
        }

        private void popularMovies(final String type) {

            Call<Movie> call = moviesApiInterface.getAllPopularMovie(api_key, "1");
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(flashScreen.flashContext, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    } else {
                        Movie list = response.body();
                        moveModelPresenter.sentDataToPresnter(list,type);
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Toast.makeText(flashScreen.flashContext, "Error Connection", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void topratedMovies(final String type) {
            Call<Movie> call = moviesApiInterface.getAllTopratedMovies(api_key, "1");
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(flashScreen.flashContext, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    } else {
                        Movie list = response.body();
                        moveModelPresenter.sentDataToPresnter(list,type);
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    System.out.println("Error Connection TopRatedMovies");
                }
            });
        }

        private void nowPlayingMovies(final String type) {
            Call<Movie> call = moviesApiInterface.getNowPlayingMovies(api_key, "1");
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(flashScreen.flashContext, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    } else {
                        Movie list = response.body();
                        moveModelPresenter.sentDataToPresnter(list,type);
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    System.out.println("Error Connection TopRatedMovies");
                }
            });
        }

        private void upComingMovies(final String type) {
            Call<Movie> call = moviesApiInterface.getUpComingMovies(api_key, "1");
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(flashScreen.flashContext, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    } else {
                        Movie list = response.body();
                        moveModelPresenter.sentDataToPresnter(list,type);
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    System.out.println("Error Connection TopRatedMovies");
                }
            });
        }
    }
}
