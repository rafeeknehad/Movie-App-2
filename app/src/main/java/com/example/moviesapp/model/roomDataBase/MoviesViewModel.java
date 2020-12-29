package com.example.moviesapp.model.roomDataBase;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MoviesViewModel extends AndroidViewModel {
    private LiveData<List<MoviesRoomData>> allMovies;
    private MoviesDataBase moviesDataBase;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        moviesDataBase = MoviesDataBase.getInstance(application);
        allMovies = moviesDataBase.movieDao().getAllMovies();
    }

    public void insert(MoviesRoomData item)
    {
        moviesDataBase.movieDao().insert(item);
    }

    public void delete(MoviesRoomData item)
    {
        moviesDataBase.movieDao().delete(item);
    }

    public void update(MoviesRoomData item)
    {
        moviesDataBase.movieDao().update(item);
    }

    public LiveData<List<MoviesRoomData>> getAllMovies() {
        return allMovies;
    }
}
