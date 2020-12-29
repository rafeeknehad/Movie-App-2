package com.example.moviesapp.javaClass;

public class CastCrew {
    private String type; //cast or crew
    private Integer id;
    private String name;
    private Object profileImage;
    private String typeId;

    public CastCrew(String type, Integer id, String name, Object profileImage, String typeId) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.typeId = typeId;
    }


    public String getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Object getProfileImage() {
        return profileImage;
    }

    public String getTypeId() {
        return typeId;
    }
}
