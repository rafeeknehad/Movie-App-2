package com.example.moviesapp.model.roomDataBase;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MovieDao {
    @Insert
    public void insert(MoviesRoomData item);

    @Update
    public void update(MoviesRoomData item);

    @Delete
    public int delete(MoviesRoomData item);

    @Query("select * from MovieDataBase")
    public LiveData<List<MoviesRoomData>> getAllMovies();

    @Query("select id from MovieDataBase where movieId=:moveId")
    public int findItem(int moveId);

    @Query("select count(id) from MovieDataBase")
    public int getCount();

    @Query("select id from MovieDataBase where movieId =:movieId")
    public int findId(int movieId);
}
