package com.example.crudapp;

import android.app.Application;

import java.util.List;

public class MyApplication extends Application {
    private List<Posts> data;
    public List<Posts> getData() {
        return data;
    }
    public void setData(List<Posts> data) {
        this.data = data;
    }
}
