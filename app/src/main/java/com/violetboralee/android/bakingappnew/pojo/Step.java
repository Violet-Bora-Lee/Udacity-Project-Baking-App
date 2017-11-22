package com.violetboralee.android.bakingappnew.pojo;

/**
 * Created by bora on 16/11/2017.
 */

public class Step
//        implements Parcelable
{
    //    public static final Creator<Step> CREATOR = new Creator<Step>() {
//        @Override
//        public Step createFromParcel(Parcel in) {
//            return new Step(in);
//        }
//
//        @Override
//        public Step[] newArray(int size) {
//            return new Step[size];
//        }
//    };
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

//    protected Step(Parcel in) {
//        id = in.readInt();
//        shortDescription = in.readString();
//        description = in.readString();
//        videoURL = in.readString();
//        thumbnailURL = in.readString();
//    }

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

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(shortDescription);
//        dest.writeString(description);
//        dest.writeString(videoURL);
//        dest.writeString(thumbnailURL);
//    }
}
