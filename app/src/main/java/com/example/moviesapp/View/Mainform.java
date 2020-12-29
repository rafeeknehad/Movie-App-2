package com.example.moviesapp.View;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moviesapp.R;
import com.example.moviesapp.javaClass.moveRecyclerView;
import com.example.moviesapp.model.Credits.CreditPVInt;
import com.example.moviesapp.model.Credits.CreditPresenter;
import com.example.moviesapp.model.Credits.CreditsResponse;
import com.example.moviesapp.model.MoviesApi.Result;
import com.example.moviesapp.model.geners.Genre;
import com.example.moviesapp.model.roomDataBase.MoviesRoomData;
import com.example.moviesapp.model.roomDataBase.MoviesViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class Mainform extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CreditPVInt {

    private static final String tag = "Mainform  ";
    public static List<Result> allMoviesList;
    public static MoviesListFragment mFragment;
    public static HashMap<String, CreditsResponse> creditsResponseHashMap;
    private ArrayList<String> mGenersList;
    private ArrayList<moveRecyclerView> moviesList;
    private DrawerLayout drawerLayout;
    private MoviesViewModel moviesViewModel;
    private ArrayList<moveRecyclerView> moveRecyclerViewsList;
    private CreditPresenter mCreditPresenter;

    //on create Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);
        allMoviesList = new ArrayList<>();
        mFragment = new MoviesListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                mFragment).commit();
        collectAllMoviesList();
        initialNavigation(savedInstanceState);
    }

    //on backPressed
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //create a option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("11111111111111111111111111111111");
                MoviesListFragment.mMoviesAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    //navigation selected item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        List<Result> selectedItem = new ArrayList<>();
        mFragment = new MoviesListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                mFragment).commit();
        switch (item.getItemId()) {
            case R.id.popular_movies:
                selectedItem = flashScreen.mPopularMovies;
                break;
            case R.id.top_rated_movies:
                selectedItem = flashScreen.mTopRatedMovies;
                break;
            case R.id.now_playing_movies:
                selectedItem = flashScreen.mNowPlayingMovies;
                break;
            case R.id.up_coming_movies:
                selectedItem = flashScreen.mUpcominMovies;
                break;
            case R.id.favorite_movies:
                initialMoviesViewModel();
                Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
                break;
        }
        recyclerViewItem(selectedItem);
        setGenersData();
        mFragment.setMoviesFragment(moveRecyclerViewsList, this);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //put the data of geners data in array list
    public void setGenersData() {
        mGenersList = new ArrayList<>();
        mGenersList.add("All&-1");
        for (Genre item : flashScreen.mGenersList) {
            mGenersList.add(item.getName() + "&" + item.getId());
        }
        mFragment.setGenersFragment(mGenersList, this);
    }

    //initial a toolbar & first fragment
    private void initialNavigation(Bundle bundle) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (bundle == null) {
            setGenersData();
            recyclerViewItem(flashScreen.mPopularMovies);
            mFragment.setMoviesFragment(moveRecyclerViewsList, this);
            navigationView.setCheckedItem(R.id.popular_movies);
        }
    }

    //initial a view model of favouriteFragment
    private void initialMoviesViewModel() {
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getAllMovies().observe(this, new Observer<List<MoviesRoomData>>() {
            @Override
            public void onChanged(List<MoviesRoomData> moviesRoomData) {

            }
        });
    }

    //put a data of movies in a arrayList
    private void recyclerViewItem(List<Result> allMoviesList) {
        moveRecyclerViewsList = new ArrayList<>();
        for (Result item : allMoviesList) {
            moveRecyclerViewsList.add(new moveRecyclerView(item.getId(), item.getPosterPath(), item.getTitle(), item.getReleaseDate(),
                    item.getVoteAverage(), item.getGenreIds()));
        }
    }

    //put all catogry of movies in one arrayList
    public void collectAllMoviesList() {
        allMoviesList.addAll(flashScreen.mPopularMovies);
        allMoviesList.addAll(flashScreen.mNowPlayingMovies);
        allMoviesList.addAll(flashScreen.mTopRatedMovies);
        allMoviesList.addAll(flashScreen.mUpcominMovies);

       /* Set<String> allMoviesId = new HashSet<>();
        for (Result item : allMoviesList) {
            allMoviesId.add(String.valueOf(item.getId()));
        }
        CreditPresenter creditPresenter = new CreditPresenter(Mainform.this, allMoviesId);*/
    }

    @Override
    public void setDataViewCreit(HashMap<String, CreditsResponse> data) {
        System.out.println(tag + data.size());
        this.creditsResponseHashMap = data;
    }
}