package com.zeeice.bakingapp.Data.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Oriaje on 24/06/2017.
 */

public class StepObject implements Parcelable {


    private int id;
    private String thumbnailURL;
    private String videoURL;
    private String shortDescription;
    private String description;

    protected StepObject(Parcel in) {

        setId(in.readInt());
        setThumbnailURL(in.readString());
        setVideoURL(in.readString());
        setShortDescription(in.readString());
        setDescription(in.readString());
    }

    public static final Creator<StepObject> CREATOR = new Creator<StepObject>() {
        @Override
        public StepObject createFromParcel(Parcel in) {
            return new StepObject(in);
        }

        @Override
        public StepObject[] newArray(int size) {
            return new StepObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getThumbnailURL());
        dest.writeString(getVideoURL());
        dest.writeString(getShortDescription());
        dest.writeString(getDescription());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
