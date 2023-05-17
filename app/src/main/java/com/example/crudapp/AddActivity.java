package com.example.crudapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddActivity extends AppCompatActivity {

    EditText id,userId,title,body;
    String getId,getBody,getTitle,getuserId;
    Button btnadd;

    String getUrl = "https://jsonplaceholder.typicode.com/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(getUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JSONPlaceholder jsonPlaceholder = retrofit.create(JSONPlaceholder.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

//        id = findViewById(R.id.editId);
        body = findViewById(R.id.editBody);
        title = findViewById(R.id.editTitle);
        userId = findViewById(R.id.editUserId);
        btnadd = findViewById(R.id.btnAdd);

//        getId = id.getText().toString();

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBody = body.getText().toString();
                getuserId = userId.getText().toString();
                getTitle = title.getText().toString();
                createPost();
            }
        });
    }

    private void createPost() {
//        Posts posts1 = new Posts(posts.getUserID(),posts.getTitle(),posts.getBody());
        Call<Posts> call = jsonPlaceholder.createPosts(getuserId,getTitle,getBody);

        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(AddActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Posts> postsList = new ArrayList<>();
                postsList.add(response.body());

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("dataPost",new ArrayList<>(postsList));

                Toast.makeText(AddActivity.this, "Add Successful", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AddActivity.this,MainActivity.class);
                i.putExtras(bundle);
                startActivity(i);


            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Toast.makeText(AddActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
