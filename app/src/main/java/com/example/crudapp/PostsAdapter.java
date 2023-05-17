package com.example.crudapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudapp.Posts;
import com.example.crudapp.R;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    List<Posts> postsList = new ArrayList<>();
    Context context;
    PostsAdapterListener listener;

    public interface PostsAdapterListener{
        void onClicked(Posts data);

        void onEdit(Posts data);

        void onDelete(Posts data);
    }

    public PostsAdapter(Context context, PostsAdapterListener listener){
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<Posts> post){
        postsList.clear();
        postsList.addAll(post);
        notifyDataSetChanged();
    }

//    public PostsAdapter(Context context, List<Posts> post){
//        this.context = context;
//        postsList = post;
//    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list,parent,false);
        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        Posts post = postsList.get(position);
        holder.id.setText(post.getId());
        holder.body.setText(post.getBody());
        holder.userid.setText(post.getUserID());
        holder.title.setText("Title : "+post.getTitle());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClicked(post);
            }
        });

        holder.editPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEdit(post);
            }
        });

        holder.deletePosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDelete(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder{
        TextView userid,id,body,title;
        LinearLayout item,editPosts,deletePosts;
        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);

            userid = itemView.findViewById(R.id.text_userid);
            id = itemView.findViewById(R.id.text_id);
            title = itemView.findViewById(R.id.text_title);
            body = itemView.findViewById(R.id.text_body);
            item = itemView.findViewById(R.id.item);
            editPosts = itemView.findViewById(R.id.editPosts);
            deletePosts = itemView.findViewById(R.id.deletePosts);
        }
    }
}
