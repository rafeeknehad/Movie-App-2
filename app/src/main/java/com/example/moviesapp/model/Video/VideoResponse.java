package com.example.moviesapp.model.Video;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<VideoClass> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideoClass> getResults() {
        return results;
    }

    public void setResults(List<VideoClass> results) {
        this.results = results;
    }
}
