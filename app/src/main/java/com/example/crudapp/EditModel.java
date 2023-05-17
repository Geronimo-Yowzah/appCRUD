package com.example.crudapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class EditModel implements Parcelable{

    private String title,body,id,userId;

    public EditModel(String id,String userId, String title , String body){
        this.title = title;
        this.body = body;
        this.id = id;
        this.userId = userId;
    }

    protected EditModel(Parcel in) {
        title = in.readString();
        body = in.readString();
        id = in.readString();
        userId = in.readString();
    }

    public static final Creator<EditModel> CREATOR = new Creator<EditModel>() {
        @Override
        public EditModel createFromParcel(Parcel in) {
            return new EditModel(in);
        }

        @Override
        public EditModel[] newArray(int size) {
            return new EditModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(id);
        dest.writeString(userId);
    }
}
