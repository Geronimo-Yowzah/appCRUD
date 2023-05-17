package com.example.crudapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Posts implements Parcelable {
    public String title,id,userId,body;

    public Posts(String userId, String title, String body) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    protected Posts(Parcel in) {
        title = in.readString();
        id = in.readString();
        userId = in.readString();
        body = in.readString();
    }

    public static final Creator<Posts> CREATOR = new Creator<Posts>() {
        @Override
        public Posts createFromParcel(Parcel in) {
            return new Posts(in);
        }

        @Override
        public Posts[] newArray(int size) {
            return new Posts[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
    public String getUserID() {
        return userId;
    }

    public String getBody() {
        return body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(body);
    }
}
