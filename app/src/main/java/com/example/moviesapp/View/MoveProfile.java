package com.example.moviesapp.View;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.moviesapp.R;
import com.example.moviesapp.javaClass.CastCrew;
import com.example.moviesapp.model.Credits.Cast;
import com.example.moviesapp.model.Credits.Crew;
import com.example.moviesapp.model.MoviesApi.Result;
import com.example.moviesapp.model.roomDataBase.MoviesRoomData;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.moviesapp.View.MoviesListFragment.creditsResponseData;

public class MoveProfile extends YouTubeBaseActivity {

    private final String API_KEY = "AIzaSyBQfPM5DhTbPPg1ltGgNH6omi-YRIDsZl8";
    private ImageView imageViewCollasping;
    private ImageView imageViewProfile;
    private TextView movieName;
    private TextView movieRatingValue;
    private RatingBar movieRatingBar;
    private TextView movieDescription;
    private Toolbar toolbar;
    private CollapsingToolbarLayout appBarLayout;
    private FloatingActionButton favouriteActionButton;
    private RecyclerView creditRecyclerView;
    private VideoView videoView;
    private RecyclerView.LayoutManager layoutManager;
    private CreditAdapter creditAdapter;
    private List<CastCrew> actorList;
    private Result selectedItem;
    private int mId;
    private YouTubePlayerView youTubePlayerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_profile2);
        progressBar = findViewById(R.id.activity_movie_profile2_progressbar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                setContentView(R.layout.activity_move_profile);
                selectedItem = getIntent().getParcelableExtra(MoviesListFragment.movieKey);
                toolbar = findViewById(R.id.MoveProfile_toolbar);
                appBarLayout = findViewById(R.id.MoveProfile_collasping);
                imageViewCollasping = findViewById(R.id.MoveProfile_image_collasping);
                imageViewProfile = findViewById(R.id.MoveProfile_small_imageView);
                movieName = findViewById(R.id.MoveProfile_text_moviesName);
                movieRatingValue = findViewById(R.id.MoveProfile_ratingBar_Number);
                movieRatingBar = findViewById(R.id.MoveProfile_ratingBar);
                movieDescription = findViewById(R.id.MoveProfile_description);
                favouriteActionButton = findViewById(R.id.MoveProfile_favourite);
                youTubePlayerView = findViewById(R.id.MoveProfile_video);
                creditRecyclerView = findViewById(R.id.MoveProfile_Personal_recyclerView);
                System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmm " + creditsResponseData);

                initialData();
            }
        }, 3000);
    }

    private void initialData() {

        actorList = new ArrayList<>();
        for (Cast item : creditsResponseData.getCast()) {
            actorList.add(new CastCrew("Cast", item.getId(), item.getName(),
                    item.getProfilePath(), String.valueOf(item.getCastId())));
        }
        for (Crew item : creditsResponseData.getCrew()) {
            actorList.add(new CastCrew("Crew", item.getId(), item.getName(),
                    item.getProfilePath(), item.getCreditId()));
        }

        appBarLayout.setTitle(selectedItem.getTitle());
        appBarLayout.setExpandedTitleTextAppearance(R.style.TextAppearance_AppCompat_Small);
        String urlImage1 = "http://image.tmdb.org/t/p/w400/" + selectedItem.getBackdropPath();
        String urlImage2 = "http://image.tmdb.org/t/p/w185/" + selectedItem.getPosterPath();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;
        Picasso.with(getApplicationContext()).load(urlImage1).resize(screenWidth, 900).into(imageViewCollasping);
        Picasso.with(getApplicationContext()).load(urlImage2).resize(150, 100).into(imageViewProfile);
        movieName.setText(selectedItem.getTitle());
        float ratingValue = (float) (selectedItem.getVoteAverage() / 2);
        movieRatingValue.setText(String.valueOf(ratingValue));
        movieRatingBar.setRating(ratingValue);
        movieDescription.setText(selectedItem.getOverview());

        layoutManager = new LinearLayoutManager(this,
                RecyclerView.HORIZONTAL, false);
        creditAdapter = new CreditAdapter(actorList, this);

        creditRecyclerView.setHasFixedSize(true);
        creditRecyclerView.setAdapter(creditAdapter);
        creditRecyclerView.setLayoutManager(layoutManager);

        youTubePlayerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    youTubePlayer.loadVideo(MoviesListFragment.videoResponseData.getResults().get(0).getKey());
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(MoveProfile.this, "Youtube Error", Toast.LENGTH_SHORT).show();
            }
        });

        favouriteActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),String.valueOf(mId),Toast.LENGTH_SHORT).show();
                if (mId == 0) {
                    favouriteActionButton.setImageResource(R.drawable.add_favourite);
                    try {
                        flashScreen.moviesDataBase.movieDao().insert(new MoviesRoomData(selectedItem.getId()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    mId = 1;
                } else {
                    favouriteActionButton.setImageResource(R.drawable.ic_baseline_favorite_24);
                    int id = flashScreen.moviesDataBase.movieDao().findItem(selectedItem.getId());
                    MoviesRoomData data = new MoviesRoomData(selectedItem.getId());
                    data.setId(id);
                    int x = flashScreen.moviesDataBase.movieDao().delete(data);
                    //Toast.makeText(getApplicationContext(), String.valueOf(x), Toast.LENGTH_SHORT).show();
                    mId = 0;
                }
            }
        });

        mId = flashScreen.moviesDataBase.movieDao().findItem(selectedItem.getId());
        if (mId != 0) {
            favouriteActionButton.setImageResource(R.drawable.add_favourite);
        }
    }
}