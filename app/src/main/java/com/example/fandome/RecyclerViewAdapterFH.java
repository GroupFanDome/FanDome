package com.example.fandome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
 * View for fandome hub icon gallery on profile fragment screen
 */
public class RecyclerViewAdapterFH extends RecyclerView.Adapter<RecyclerViewAdapterFH.ViewHolder>{

    private Context context;
    private List<Following> fandomeHub;
    RequestOptions options;

    public RecyclerViewAdapterFH(Context context, List<Following> following) {
        this.context = context;
        this.fandomeHub = following;

//        //Request option for Glide
//        this.options = new RequestOptions().centerCrop().placeholder(R.drawable.bg_login_gradient);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterFH.ViewHolder holder, int position) {
//        holder.fandomHubTitle.setText(fandomeHub.get(position).getName());
//        Glide.with(context).load(fandomeHub.get(position).getImageURL()).apply(options).into(holder.bg_login_gradient);

        Following following= fandomeHub.get(position);
        holder.bind(following);
    }

    @Override
    public int getItemCount() {
        return fandomeHub.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView bg_login_gradient;
        ImageButton fandomHubIcon;
        TextView fandomHubTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fandomHubIcon = itemView.findViewById(R.id.fandomHubIcon);
            fandomHubTitle = itemView.findViewById(R.id.fandomHubTitle);
        }
        public void bind(Following following) {
            fandomHubTitle.setText(following.getFandome().getString("name"));
            //setting image URL
            ParseFile image = following.getParseFile("fandome_image");
            if(image != null){
                Glide.with(context).load(image.getUrl()).into(fandomHubIcon);
            }
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
