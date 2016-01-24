package com.perfilyev.newsreader.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Spotlight implements Parcelable {

    private int id;
    private String title;
    private String image;
    private String description;
    @SerializedName("created_at")
    private String createdAt;
    private String thumbnail;

    protected Spotlight(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        description = in.readString();
        createdAt = in.readString();
        thumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(createdAt);
        dest.writeString(thumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Spotlight> CREATOR = new Creator<Spotlight>() {
        @Override
        public Spotlight createFromParcel(Parcel in) {
            return new Spotlight(in);
        }

        @Override
        public Spotlight[] newArray(int size) {
            return new Spotlight[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
