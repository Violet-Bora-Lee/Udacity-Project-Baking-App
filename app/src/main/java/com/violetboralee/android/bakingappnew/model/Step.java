package com.violetboralee.android.bakingappnew.model;


/**
 * Created by bora on 16/11/2017.
 */

public class Step {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    // Constructor
    public Step(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoUrl;
        this.thumbnailURL = thumbnailUrl;
    }


    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

}
