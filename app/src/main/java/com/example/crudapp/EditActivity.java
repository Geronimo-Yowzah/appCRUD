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

public class EditActivity extends AppCompatActivity {

    EditText id,body,title,userid;
    Button btnedit;
    String getId,getBody,getTitle,getuserId;
    int getid;
    String getUrl = "https://jsonplaceholder.typicode.com/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(getUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JSONPlaceholder jsonPlaceholder = retrofit.create(JSONPlaceholder.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        EditModel editModel = bundle.getParcelable("data");

        id = findViewById(R.id.editId);
        body = findViewById(R.id.editBody);
        title = findViewById(R.id.editTitle);
        userid = findViewById(R.id.editUserId);

        btnedit = findViewById(R.id.btnEdit);

        id.setText(editModel.getId());
        body.setText(editModel.getBody());
        title.setText(editModel.getTitle());
        userid.setText(editModel.getUserId());


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getId = id.getText().toString();
//                getid = Integer.parseInt(getId);
                getBody = body.getText().toString();
                getuserId = userid.getText().toString();
                getTitle = title.getText().toString();
                updatePost();
            }
        });


    }

    private void updatePost() {
        Posts posts = new Posts(getuserId,getTitle,getBody);

        Call<Posts> call = jsonPlaceholder.patchPosts(getId,posts);
        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(EditActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Posts> postsList = new ArrayList<>();
                postsList.add(response.body());

                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("dataPut",new ArrayList<>(postsList));

                Toast.makeText(EditActivity.this, "Edit Successful", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EditActivity.this, MainActivity.class);
                i.putExtras(bundle1);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Toast.makeText(EditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
