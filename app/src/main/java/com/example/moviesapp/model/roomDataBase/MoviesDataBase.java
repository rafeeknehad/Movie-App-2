package com.example.moviesapp.model.roomDataBase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MoviesRoomData.class},version = 1)
public abstract class MoviesDataBase extends RoomDatabase {

    private static MoviesDataBase instance;

    public abstract MovieDao movieDao();

    public static synchronized MoviesDataBase getInstance(Context context)
    {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    ,MoviesDataBase.class,"Movies_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
