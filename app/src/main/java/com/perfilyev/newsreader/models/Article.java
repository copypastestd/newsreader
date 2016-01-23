package com.perfilyev.newsreader.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Article implements Parcelable {

    private int id;
    private String title;
    private String image;
    private String description;
    @SerializedName("created_at")
    private String date;
    private String thumbnail;
    private String lead;
    private String text;
    private String source;

    protected Article(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        description = in.readString();
        date = in.readString();
        thumbnail = in.readString();
        lead = in.readString();
        text = in.readString();
        source = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", lead='" + lead + '\'' +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(thumbnail);
        dest.writeString(lead);
        dest.writeString(text);
        dest.writeString(source);
    }

    public String getText() {
        return text;
    }

    public String getSource() {
        return source;
    }
}
