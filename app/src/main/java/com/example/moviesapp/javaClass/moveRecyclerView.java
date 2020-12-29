package com.example.moviesapp.javaClass;

import java.util.List;

public class moveRecyclerView {
    private int id;
    private String imageUrl;
    private String name;
    private String Data;
    private Double rated;
    private List<Integer> genersList;


    public moveRecyclerView(int id, String imageUrl, String name, String Data, Double rated, List<Integer> genersList) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.Data = Data;
        this.rated = rated;
        this.genersList = genersList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public Double getRated() {
        return rated;
    }

    public void setRated(Double rated) {
        this.rated = rated;
    }

    public void setGenersList(List<Integer> genersList) {
        this.genersList = genersList;
    }

    public List<Integer> getGenersList() {
        return genersList;
    }
}
