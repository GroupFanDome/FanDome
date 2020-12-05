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
import com.example.fandome.models.Post;


import java.util.List;

/**
 * View for fandome hub icon gallery on profile fragment screen
 */
public class RecyclerViewAdapterFH extends RecyclerView.Adapter<RecyclerViewAdapterFH.ViewHolder>{

    private Context context;
    private List<Fandome> fandomeHub;
    RequestOptions options;

    public RecyclerViewAdapterFH(Context context, List<Fandome> fandome) {
        this.context = context;
        this.fandomeHub = fandomeHub;

        //Request option for Glide
        this.options = new RequestOptions().centerCrop().placeholder(R.drawable.bg_login_gradient);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterFH.ViewHolder holder, int position) {
        holder.fandomHubTitle.setText(fandomeHub.get(position).getName());
        Glide.with(context).load(fandomeHub.get(position).getImageURL()).apply(options).into(holder.bg_login_gradient);

        Fandome fandome = fandomeHub.get(position);
        holder.bind(fandome);
    }

    @Override
    public int getItemCount() {
        return fandomeHub.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView bg_login_gradient;
        ImageButton fandomHubIcon;
        TextView fandomHubTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fandomHubIcon = itemView.findViewById(R.id.fandomHubIcon);
            fandomHubTitle = itemView.findViewById(R.id.fandomHubTitle);
        }
        public void bind(Fandome fandome) {
            fandomHubTitle.setText(fandome.getName());
            //setting image URL
            fandomHubIcon.setImageURI(Uri.parse("fandome_image"));
        }

    }

    // Clean all elements of the recycler
    public void clear() {
        fandomeHub.clear();
        notifyDataSetChanged();
    }
    // Add a list of items -- change to type used
    public void addAll(List<Fandome> hub) {
        fandomeHub.addAll(hub);
        notifyDataSetChanged();
    }

}
