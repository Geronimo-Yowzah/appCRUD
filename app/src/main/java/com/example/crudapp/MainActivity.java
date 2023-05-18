package com.example.crudapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    String getUrl = "https://jsonplaceholder.typicode.com/";

    TextView okay_text, cancel_text, delete_text;

    ImageView btnadd, btnedit, btndelete;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(getUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    JSONPlaceholder jsonPlaceholder = retrofit.create(JSONPlaceholder.class);

    Dialog dialog;

    PostsAdapter postsAdapter;

    List<Posts> deleteData = new ArrayList<>();

    List<Posts> postsList = new ArrayList<>();

    MyApplication app = (MyApplication) getApplication();

    Bundle bundle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        app = (MyApplication) getApplication();

        initialAdapter();
        if (app.getData() == null) {
            callResponse();
        } else {
            bundle = getIntent().getExtras();
            ArrayList<Posts> postsList1 = bundle.getParcelableArrayList("dataPost");
            ArrayList<Posts> postsList2 = bundle.getParcelableArrayList("dataPut");
            if (postsList1 != null) {
                callCreate();
            } else if (postsList2 != null) {
                callUpdate();
            }
        }

        btnadd = findViewById(R.id.btnAdd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });
    }

    private void initialAdapter() {
        dialog = new Dialog(MainActivity.this);
        postsAdapter = new PostsAdapter(MainActivity.this, new PostsAdapter.PostsAdapterListener() {
            @Override
            public void onClicked(Posts data) {
                dialog.setContentView(R.layout.dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                TextView userId = dialog.findViewById(R.id.userIdText);
                TextView id = dialog.findViewById(R.id.IdText);
                TextView title = dialog.findViewById(R.id.TitleText);
                TextView body = dialog.findViewById(R.id.BodyText);

                userId.setText("User ID : " + data.getUserID());
                id.setText("ID : " + data.getId());
                title.setText("Title : " + data.getTitle());
                body.setText("Body : " + data.getBody());


                okay_text = dialog.findViewById(R.id.okay_text);

                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Okay clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }

            @Override
            public void onEdit(Posts data) {
                Toast.makeText(MainActivity.this, "Edit", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                EditModel editModel = new EditModel(data.getId(), data.getUserID(), data.getTitle(), data.getBody());
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", editModel);
                i.putExtras(bundle);
                startActivity(i);
            }

            @Override
            public void onDelete(Posts data) {
                Toast.makeText(MainActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                dialog.setContentView(R.layout.dialog_delete);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                TextView title = dialog.findViewById(R.id.TitleText);
                title.setText("Title : " + data.getTitle());

                cancel_text = dialog.findViewById(R.id.cancel_text);
                cancel_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Cancel Delete", Toast.LENGTH_SHORT).show();
                    }
                });

                delete_text = dialog.findViewById(R.id.delete_text);
                delete_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteData.add(data);
                        deletePost(Integer.parseInt(data.getId()));
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        recyclerView.setAdapter(postsAdapter);
    }

    private void callResponse() {
        Call<List<Posts>> call = jsonPlaceholder.getPosts();
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                postsList = response.body();
                if (app.getData() == null) {
                    app.setData(postsList);
                }
                postsAdapter.setData(app.getData());
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    bundle = getIntent().getExtras();
//                if(bundle != null){
//        ArrayList<Posts> postsList1 = bundle.getParcelableArrayList("dataPost");
//        ArrayList<Posts> postsList2 = bundle.getParcelableArrayList("dataPut");
//        if(postsList1 != null){
//            posts.clear();
//            posts.addAll(postsList1);
//            posts.addAll(postsList);
//            postsAdapter.setData(posts);
//        }
//        if(postsList2 != null){
//            Posts posts1 = null;
//            for (int i=0 ; i < postsList.size() ; i++){
//                if(postsList.get(i).id.equals(postsList2.get(0).getId())){
//                    posts1 = postsList.get(i);
//                }
//            }
//            if(posts1 != null){
//                postsList.remove(posts1);
//            }
//            posts.clear();
//            posts.addAll(postsList2);
//            posts.addAll(postsList);
//            postsAdapter.setData(posts);
//        }
//    }else {
//        posts.clear();
//        if(postsList != null && deleteData.size() != 0){
//            for(int i = 0 ; i< postsList.size() ; i++){
//                for(int j = 0 ; j < deleteData.size() ; j++){
//                    if(postsList.get(i).getId().equals(deleteData.get(j).getId())){
//                        postsList.remove(postsList.get(i));
//                    }
//                }
//            }
//            posts.addAll(postsList);
//            postsAdapter.setData(posts);
//        }else {
//            posts.addAll(postsList);
//            postsAdapter.setData(posts);
//        }
//    }
//}


    private void callCreate() {
        List<Posts> postsTemp = new ArrayList<>();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<Posts> postsList1 = bundle.getParcelableArrayList("dataPost");
            if (postsList1 != null) {
                postsTemp.addAll(postsList1);
                postsTemp.addAll(app.getData());
                app.setData(postsTemp);
                postsAdapter.setData(postsTemp);
            }
        }
    }

    private void callUpdate() {
        List<Posts> postsTemp = new ArrayList<>();
        List<Posts> postsTemp2 = new ArrayList<>();
        postsTemp.addAll(app.getData());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<Posts> postsList2 = bundle.getParcelableArrayList("dataPut");
            if (postsList2 != null) {
                Posts posts1 = null;
                if (postsList2.get(0).getId() == null) {
                    postsList2.get(0).id = "101";
                }
                for (int i = 0; i < postsTemp.size(); i++) {
                    if (postsTemp.get(i).id.equals("101") && postsTemp.get(i).userId.equals(postsList2.get(0).getId())) {
                        posts1 = postsTemp.get(i);
                    } else if (postsTemp.get(i).id.equals(postsList2.get(0).getId())) {
                        posts1 = postsTemp.get(i);
                    }
                }
                if (posts1 != null) {
                    postsTemp.remove(posts1);
                    postsTemp2.addAll(postsTemp);
                }
                postsTemp.clear();
                postsTemp.addAll(postsList2);
                postsTemp.addAll(postsTemp2);
                app.setData(postsTemp);
                postsAdapter.setData(postsTemp);
            }
        }
    }

    private void callDelete() {
        List<Posts> postsTemp = new ArrayList<>();
        postsTemp.addAll(app.getData());
        if (postsTemp != null && deleteData.size() != 0) {
            if (deleteData.get(0).getId().equals("101")) {
                for (int i = 0; i < postsTemp.size(); i++) {
                    for (int j = 0; j < deleteData.size(); j++) {
                        if (postsTemp.get(i).userId.equals(deleteData.get(j).userId)) {
                            postsTemp.remove(postsTemp.get(i));
                        }
                    }
                }
            } else {
                for (int i = 0; i < postsTemp.size(); i++) {
                    for (int j = 0; j < deleteData.size(); j++) {
                        if (postsTemp.get(i).getId().equals(deleteData.get(j).getId())) {
                            postsTemp.remove(postsTemp.get(i));
                        }
                    }
                }
            }

            app.setData(postsTemp);
            postsAdapter.setData(postsTemp);

        }
    }


    public void deletePost(int id) {
        Call<Void> call = jsonPlaceholder.deletePosts(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Toast.makeText(MainActivity.this, "Delete Successful " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        callDelete();
    }

}