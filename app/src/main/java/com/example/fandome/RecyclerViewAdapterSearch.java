package com.example.fandome;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.fandome.models.Fandome;
import com.example.fandome.models.Following;
import com.parse.ParseFile;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterSearch extends RecyclerView.Adapter<RecyclerViewAdapterSearch.ViewHolder> implements Filterable {
    public static final String TAG="RecyclerViewSearch";

    private Context context;
    private List<Fandome> fandomeHubs;
    private List<Fandome> fandomeHubsFiltered;
    private RecyclerViewClickListener listener;

    public RecyclerViewAdapterSearch(Context context, List<Fandome> fandomeHubs, RecyclerViewClickListener listener) {
        this.context = context;
        this.fandomeHubs = fandomeHubs;
        this.fandomeHubsFiltered = fandomeHubs;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_item, parent,false);
        return new RecyclerViewAdapterSearch.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fandome fandome = fandomeHubsFiltered.get(position);
        holder.bind(fandome);
    }
    @Override
    public int getItemCount() {
        return fandomeHubsFiltered.size();
    }
    //filters out the list of searched hubs
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key= constraint.toString();
                if (Key.isEmpty()){
                    fandomeHubsFiltered = fandomeHubs;
//                    the following line clears search results when search bar is empty but only after the bar is activated
//                    fandomeHubsFiltered.clear();
                }else {
                    List<Fandome> listFiltered = new ArrayList<>();
                    for(Fandome hub: fandomeHubs){
                        if(hub.getName().toLowerCase().contains(Key.toLowerCase())){
                            listFiltered.add(hub);
                            Log.d(TAG, "hub added "+ hub.getName());
                        }
                    }
                    fandomeHubsFiltered = listFiltered;
                    if(fandomeHubsFiltered != fandomeHubs){
                        Log.d(TAG, "list has been changed! " + fandomeHubsFiltered.size());
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = fandomeHubsFiltered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                fandomeHubsFiltered = (List<Fandome>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageButton fandomHubIcon;
        TextView fandomHubTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fandomHubIcon = itemView.findViewById(R.id.fandomHubIcon);
            fandomHubTitle = itemView.findViewById(R.id.fandomHubTitle);
            itemView.setOnClickListener(this);
        }
        public void bind(Fandome fandome) {
            fandomHubTitle.setText(fandome.getString("name"));
            //setting image URL
            ParseFile image = fandome.getParseFile("fandome_image");
            if(image != null){
                Glide.with(context).load(image.getUrl()).into(fandomHubIcon);
                Log.d("adapterSearch", "image url is " + image.getUrl());
            }
            fandomHubIcon.setClipToOutline(true);
        }
        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
            Log.d(TAG, "adapter position" + getAdapterPosition());
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        fandomeHubs.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Fandome> fandomeHub) {
        fandomeHubs.addAll(fandomeHub);
        notifyDataSetChanged();
    }
}