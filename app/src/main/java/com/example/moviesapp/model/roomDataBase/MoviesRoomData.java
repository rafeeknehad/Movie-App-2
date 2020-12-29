package com.example.moviesapp.model.roomDataBase;


import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "MovieDataBase", indices = {@Index(value = {"movieId"}, unique = true)})
public class MoviesRoomData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int movieId;

    public MoviesRoomData(int movieId) {
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

}
