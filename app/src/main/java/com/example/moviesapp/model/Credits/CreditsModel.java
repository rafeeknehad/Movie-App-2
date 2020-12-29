package com.example.moviesapp.model.Credits;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import java.util.HashMap;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreditsModel {
    private static final String tag = "CreditsModel  ";
    private static String apiKey = "939d60a07bf55c9dc7b0a3a0db237e02";
    private static HashMap<String, CreditsResponse> mMoviesCreditsHash;
    private static CreditMPInt ICreditMPInt;
    private static Retrofit retrofit;
    private CreditPresenter OCreditPresenter;

    public CreditsModel(Set<String> moviesIdSet, Context mContext, CreditPresenter creditPresenter) {
        ICreditMPInt = (CreditMPInt) creditPresenter;
        mCallRetrofit(moviesIdSet, mContext);
    }

    private void mCallRetrofit(Set<String> idData, Context mContext) {
        mMoviesCreditsHash = new HashMap<>();
        new AsycTaskCredit().execute(idData);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("ccccccccccccccccccccccccc "+mMoviesCreditsHash);
                ICreditMPInt.getMPData(mMoviesCreditsHash);
            }
        },5000);
    }

    public class AsycTaskCredit extends AsyncTask<Set<String>, Void, Void> {
        @Override
        protected Void doInBackground(Set<String>... sets) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Set<String> data = sets[0];

            CreditsApiInt apiInt = retrofit.create(CreditsApiInt.class);
            for (final String item : data) {
                Call<CreditsResponse> responseCall = apiInt.getCreditsForMovies(Integer.valueOf(item), apiKey);
                responseCall.enqueue(new Callback<CreditsResponse>() {
                    @Override
                    public void onResponse(Call<CreditsResponse> call, Response<CreditsResponse> response) {
                        if (!response.isSuccessful()) {
                            System.out.println("CreditsResponse Code " + response.code());
                        } else {
                            System.out.println("bbbbbbbb " + response.body());
                            mMoviesCreditsHash.put(item, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<CreditsResponse> call, Throwable t) {
                        System.out.println("CreditsResponse Error " + t.getMessage());
                    }
                });
            }
            return null;
        }
    }


}
