package com.example.moviesapp.model.Credits;

import android.content.Context;

import java.util.HashMap;
import java.util.Set;

public class CreditPresenter implements CreditMPInt {
    private static final String tag = "CreditPresenter  ";
    private Context mcontext;
    private Set<String> mMoviesId;
    private CreditsModel OCreditsModel;
    private CreditPVInt mCreditPVInt;
    public CreditPresenter(Context mcontext,Set<String> moviesId) {
        this.mcontext = mcontext;
        this.mMoviesId = moviesId;
        mCreditPVInt = (CreditPVInt) mcontext;
        OCreditsModel = new CreditsModel(moviesId,mcontext,this);
    }


    @Override
    public void getMPData(HashMap<String, CreditsResponse> data) {
        System.out.println(tag + data.size());
        mCreditPVInt.setDataViewCreit(data);
    }
}
