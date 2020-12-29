package com.example.moviesapp.View;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviesapp.R;
import com.example.moviesapp.javaClass.moveRecyclerView;
import com.example.moviesapp.model.Credits.CreditsApiInt;
import com.example.moviesapp.model.Credits.CreditsResponse;
import com.example.moviesapp.model.MoviesApi.Result;
import com.example.moviesapp.model.Video.VideoResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MoviesListFragment extends Fragment {


    private static final String tag = "MoviesListFragment  ";
    private static String apiKey = "939d60a07bf55c9dc7b0a3a0db237e02";

    public static String movieKey = "Movie";
    public static MoviesAdapter mMoviesAdapter;
    private RecyclerView mMoviesRecy;
    private RecyclerView.LayoutManager mMoviesLayout;
    private RecyclerView mGenersRecy;
    private GenersAdapter mGenersAdapter;
    private RecyclerView.LayoutManager mGenersLayout;
    private ArrayList<moveRecyclerView> mMoviesData;
    private List<String> mGenersData;
    private Context mContext;
    private HashMap<String, CreditsResponse> creditsResponseHashMap;
    private static Retrofit retrofit;
    public int selextItem;
    public static CreditsResponse creditsResponseData;
    public static VideoResponse videoResponseData;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies_list, container, false);
        mMoviesRecy = v.findViewById(R.id.movieslist_movies);
        mGenersRecy = v.findViewById(R.id.movieslist_movies_type);
        setGenersRecy();
        setMoviesRecy();
        return v;
    }

    public void setGenersRecy() {
        final ArrayList<String> arrayListGeners = new ArrayList<>(mGenersData);
        mGenersAdapter = new GenersAdapter(arrayListGeners, Mainform.mFragment.getContext());
        mGenersRecy.setHasFixedSize(true);
        mGenersLayout = new LinearLayoutManager(Mainform.mFragment.getContext(), RecyclerView.HORIZONTAL, false);
        mGenersRecy.setAdapter(mGenersAdapter);
        mGenersRecy.setLayoutManager(mGenersLayout);

        mGenersAdapter.setOnClickListener(new GenersAdapter.onItemClickedListener() {
            @Override
            public void itemClickListener(int pos) {
                String selectedItem = arrayListGeners.get(pos);
                String id = arrayListGeners.get(pos).split("&")[1];
                //Toast.makeText(Mainform.mFragment.getContext(),selectedItem + "  "+id, Toast.LENGTH_SHORT).show();
                mMoviesAdapter.updateMoviesGeners(id);
            }
        });
    }

    public void setMoviesRecy() {
        mMoviesAdapter = new MoviesAdapter(mMoviesData, mContext.getApplicationContext());
        mMoviesRecy.setHasFixedSize(true);
        mMoviesLayout = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mMoviesRecy.setAdapter(mMoviesAdapter);
        mMoviesRecy.setLayoutManager(mMoviesLayout);
        mMoviesAdapter.setOnClickListener(new MoviesAdapter.onItemClickListener() {
            @Override
            public void onCliskListener(int pos) {
                moveRecyclerView itemSelected = mMoviesData.get(pos);
                Result itemSelectedResult = getSelectedItem(itemSelected);
                selextItem = itemSelectedResult.getId();
                new CreditTask().execute();
                Intent myIntent = new Intent(mContext.getApplicationContext(), MoveProfile.class);
                myIntent.putExtra(movieKey, itemSelectedResult);
                startActivity(myIntent);
            }
        });
    }

    public Result getSelectedItem(moveRecyclerView selectedItem) {
        for (Result item : Mainform.allMoviesList) {
            if (item.getId() == selectedItem.getId()) {
                return item;
            }
        }
        return null;
    }

    public void setMoviesFragment(ArrayList<moveRecyclerView> data, Context context) {
        this.mMoviesData = data;
        this.mContext = context;
    }

    public void setGenersFragment(List<String> data, Context context) {
        this.mGenersData = data;
        this.mContext = context;
    }

    public void setMovieCredit(HashMap<String,CreditsResponse> credit)
    {
        this.creditsResponseHashMap = credit;
        System.out.println(tag + creditsResponseHashMap);
    }

    public class CreditTask extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CreditsApiInt apiInt = retrofit.create(CreditsApiInt.class);

            //get Credit Movie
            Call<CreditsResponse> responseCall = apiInt.getCreditsForMovies(Integer.valueOf(selextItem), apiKey);
            responseCall.enqueue(new Callback<CreditsResponse>() {
                @Override
                public void onResponse(Call<CreditsResponse> call, Response<CreditsResponse> response) {
                    if (!response.isSuccessful()) {
                        System.out.println("CreditsResponse Code " + response.code());
                    } else {
                        System.out.println("bbbbbbbb " + response.body());
                        creditsResponseData = response.body();
                    }
                }

                @Override
                public void onFailure(Call<CreditsResponse> call, Throwable t) {
                    System.out.println("CreditsResponse Error " + t.getMessage());
                }
            });

            //get Video Movie
            Call<VideoResponse> videoResponseCall = apiInt.getMovieVideo(Integer.valueOf(selextItem),apiKey);
            videoResponseCall.enqueue(new Callback<VideoResponse>() {
                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    if(!response.isSuccessful())
                    {
                        System.out.println("videoResponseCall Code " + response.code());
                    }
                    else
                    {
                        videoResponseData = response.body();
                        System.out.println("hhhhhhhhhhh "+videoResponseData);
                    }

                }

                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {
                    System.out.println("videoResponseCall Error " + t.getMessage());
                }
            });
            return null;
        }
    }

}
