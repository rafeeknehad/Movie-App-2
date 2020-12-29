package com.example.moviesapp.model.geners;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenersModel {
    private static String api_key = "939d60a07bf55c9dc7b0a3a0db237e02";
    private static Context mcontext;
    private static GenersPresenterModel genersPresenterModel;

    public GenersModel(Context context) {
        this.mcontext = context;
        genersPresenterModel = new GenersPresenter(context, 1);
        initializeApi();
    }

    public void initializeApi() {
        new asycTask().execute();
    }

    public static class asycTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Retrofit retrofitMoveType = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MoveTypeApi moveTypeApi = retrofitMoveType.create(MoveTypeApi.class);

            moveTypeApi.getData(api_key).enqueue(new Callback<GenresResponse>() {
                @Override
                public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mcontext, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    } else {
                        List<Genre> reGenreList = response.body().getGenres();
                        genersPresenterModel.getDataFromModel(reGenreList);
                    }
                }
                @Override
                public void onFailure(Call<GenresResponse> call, Throwable t) {
                    Toast.makeText(mcontext, "Check Your Connection", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
    }
}
