package com.example.moviesapp.model.MoviesApi;

import android.content.Context;

import com.example.moviesapp.View.flashScreen;

public class MoviePresenter implements MoveModelPresenter {

    private Context mcontext;
    private MoviesModel moviesModel;
    private MoveViewPresenter instance;

    public MoviePresenter() {
    }

    public MoviePresenter(String move) {
        moviesModel = new MoviesModel(move);
    }


    @Override
    public void sentDataToPresnter(Movie movies, String movieType) {
        instance = (MoveViewPresenter) flashScreen.flashContext;
        instance.setMoveData(movies, movieType);
    }
}
