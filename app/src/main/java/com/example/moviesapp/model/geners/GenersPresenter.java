package com.example.moviesapp.model.geners;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class GenersPresenter implements GenersPresenterModel {
    public static final String TAG = "ApiPresenter";
    private Context mcontext;
    private GenersViewPresenter genersViewPresenter;
    private GenersModel genersModel;

    public GenersPresenter(Context context, int x) {
        genersViewPresenter = (GenersViewPresenter) context;
    }

    public GenersPresenter(Context context) {
        this.mcontext = context;
        genersViewPresenter = (GenersViewPresenter) context;
        genersModel = new GenersModel(context);
    }

    @Override
    public void getDataFromModel(List<Genre> genreList) {
        Log.d(TAG, "getDataFromModel: 111");
        genersViewPresenter.setData(genreList);
    }
}
