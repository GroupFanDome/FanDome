package com.example.fandome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fandome.models.Fandome;
import com.example.fandome.models.Following;
import com.example.fandome.models.Post;
import com.parse.ParseFile;


import java.util.List;

/**
 * Adapter for the user's profile (profile fragment). Controls the user's followed hubs list
 */
public class RecyclerViewAdapterFH extends RecyclerView.Adapter<RecyclerViewAdapterFH.ViewHolder>{

    private Context context;
    private List<Following> fandomeHub;

    public RecyclerViewAdapterFH(Context context, List<Following> following) {
        this.context = context;
        this.fandomeHub = following;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterFH.ViewHolder holder, int position) {
        Following following= fandomeHub.get(position);
        holder.bind(following);
    }

    @Override
    public int getItemCount() {
        return fandomeHub.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton fandomHubIcon;
        TextView fandomHubTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fandomHubIcon = itemView.findViewById(R.id.fandomHubIcon);
            fandomHubTitle = itemView.findViewById(R.id.fandomHubTitle);
        }
        public void bind(Following following) {
            //setting the title of the hub user is following
            fandomHubTitle.setText(following.getFandome().getString("name"));
            //setting image URL
            ParseFile image = following.getFandome().getParseFile("fandome_image");
//            Log.d("adapterFH", "image url is " + image.getUrl());
            if(image != null){
                Glide.with(context).load(image.getUrl()).into(fandomHubIcon);
                Log.d("adapterFH", "adapterFH image url is " + image.getUrl());
            }
            Log.d("adapterFH", "adapterFH image url is " + image.getUrl());
            fandomHubIcon.setClipToOutline(true);
        }

    }

    // Clean all elements of the recycler
    public void clear() {
        fandomeHub.clear();
        notifyDataSetChanged();
    }
    // Add a list of items -- change to type used
    public void addAll(List<Following> hub) {
        fandomeHub.addAll(hub);
        notifyDataSetChanged();
    }

}
