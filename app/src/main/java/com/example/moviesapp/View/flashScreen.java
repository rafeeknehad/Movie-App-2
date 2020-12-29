package com.example.moviesapp.View;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.example.moviesapp.R;
import com.example.moviesapp.javaClass.DataBase;
import com.example.moviesapp.model.MoviesApi.MoveViewPresenter;
import com.example.moviesapp.model.MoviesApi.Movie;
import com.example.moviesapp.model.MoviesApi.MoviePresenter;
import com.example.moviesapp.model.MoviesApi.Result;
import com.example.moviesapp.model.geners.GenersPresenter;
import com.example.moviesapp.model.geners.GenersViewPresenter;
import com.example.moviesapp.model.geners.Genre;
import com.example.moviesapp.model.roomDataBase.MoviesDataBase;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class flashScreen extends AppCompatActivity implements MoveViewPresenter, GenersViewPresenter {

    public static DataBase dataBase;
    public static MoviesDataBase moviesDataBase;
    public static List<Result> mPopularMovies;
    public static List<Result> mUpcominMovies;
    public static List<Result> mTopRatedMovies;
    public static List<Result> mNowPlayingMovies;
    public static Context flashContext;
    public static List<Genre> mGenersList;
    private static MoviePresenter moviePresenter;
    private static GenersPresenter genersPresenter;

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        mPopularMovies = new ArrayList<>();
        mNowPlayingMovies = new ArrayList<>();
        mTopRatedMovies = new ArrayList<>();
        mUpcominMovies = new ArrayList<>();
        mGenersList = new ArrayList<>();
        moviesDataBase = MoviesDataBase.getInstance(this);
        flashContext = this;
        new Asyc().execute(this);
        dataBase = new DataBase(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(getApplicationContext(), Mainform.class);
                startActivityForResult(myIntent, 0);
            }
        }, 1000);

    }

    //set the data from the presenter of movies
    @Override
    public void setMoveData(Movie movies, String movieType) {
        if (movieType.equals("popular_movies")) {
            mPopularMovies = movies.getResults();
        } else if (movieType.equals("top_rated_movies"))
            mTopRatedMovies = movies.getResults();
        else if (movieType.equals("now_playing_movies"))
            mNowPlayingMovies = movies.getResults();
        else if (movieType.equals("up_coming_movies"))
            mUpcominMovies = movies.getResults();
    }

    //set the data from the presenter of geners
    @Override
    public void setData(List<Genre> genreList) {
        mGenersList = genreList;
    }

    public static class Asyc extends AsyncTask<Context, Void, Void> {
        @Override
        protected Void doInBackground(Context... contexts) {
            moviePresenter = new MoviePresenter("popular_movies");
            moviePresenter = new MoviePresenter("top_rated_movies");
            moviePresenter = new MoviePresenter("now_playing_movies");
            moviePresenter = new MoviePresenter("up_coming_movies");
            genersPresenter = new GenersPresenter(flashScreen.flashContext);
            return null;
        }
    }

}