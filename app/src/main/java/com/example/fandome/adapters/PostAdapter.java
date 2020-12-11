package com.example.fandome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fandome.R;
import com.example.fandome.models.Post;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvUsername;
        private TextView tvTimeStamp;
        private TextView tvBody;
        private TextView tvFandome;
        private ImageView ivUserImage;
        private ImageView ivPostImage;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvFandome = itemView.findViewById(R.id.tvFandome);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
        }

        public void bind(Post post) {
            tvUsername.setText(post.getUser().getUsername());
            tvTimeStamp.setText(post.getDateCreated());
            tvBody.setText(post.getBody());
            tvFandome.setText(post.getFandome().getString("name"));

            ParseFile user_image = post.getUser().getParseFile("user_image");
            if(user_image != null){
                Glide.with(context).load(user_image.getUrl()).into(ivUserImage);
            }
            ivUserImage.setClipToOutline(true);

            ParseFile post_image = post.getPostImage();
            if(post_image != null){
                ivPostImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(post_image.getUrl()).into(ivPostImage);

            }else{
                ivPostImage.setVisibility(View.GONE);
            }

        }
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }
    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
