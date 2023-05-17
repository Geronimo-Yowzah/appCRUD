package com.example.crudapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JSONPlaceholder {
    @GET("posts")
    Call<List<Posts>> getPosts();

    @POST("posts")
    Call<Posts> createPosts(@Body Posts posts);

    @FormUrlEncoded
    @POST("posts")
    Call<Posts> createPosts(@Field("userId") String userId,@Field("title") String title,@Field("body") String body);

    @PUT("posts")
    Call<Posts> putPosts(@Path("id")int id,@Body Posts posts);

    @PATCH("posts/{id}")
    Call<Posts> patchPosts(@Path("id")String id,@Body Posts posts);

    @DELETE("posts")
    Call<Posts> deletePosts(@Path("id")int id);
}
