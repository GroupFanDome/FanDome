package com.example.fandome.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.fandome.R;
import com.example.fandome.RecyclerViewAdapterSearch;
import com.example.fandome.activities.HubGalleryActivity;
import com.example.fandome.models.Fandome;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for user search screen
 */
public class SearchFragment extends Fragment {
    public static final String TAG="SearchFragment";

    private RecyclerViewAdapterSearch.RecyclerViewClickListener listener;

    private EditText searchBar;
    private RecyclerView recyclerView;

    //profile hubs info
    private RecyclerViewAdapterSearch adapterSearch;
    private List<Fandome> fandomeHubList;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.myList);
        searchBar = view.findViewById(R.id.searchBar);

        fandomeHubList = new ArrayList<>();
        setOnClickListener();
        adapterSearch = new RecyclerViewAdapterSearch(getContext(), fandomeHubList, listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterSearch);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterSearch.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        getHubs();
    }

    //queries through the hubs being search for
    private void getHubs() {
        ParseQuery<Fandome> fandomeParseQuery = ParseQuery.getQuery(Fandome.class);
        fandomeParseQuery.include(Fandome.KEY_NAME);
        fandomeParseQuery.findInBackground(new FindCallback<Fandome>() {
            @Override
            public void done(List<Fandome> hubs, ParseException e) {
                if(e != null){
                    Log.e("main", "Issue with getting list of fandoms the user follows",e);
                    return;
                }
                // success
                adapterSearch.clear();
                adapterSearch.addAll(hubs);
                Log.d(TAG, "added some hubs. adapter is now at size " + adapterSearch.getItemCount());
            }
        });
    }

    private void setOnClickListener(){
        listener = new RecyclerViewAdapterSearch.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getContext(), HubGalleryActivity.class);

                intent.putExtra(Fandome.KEY_IMAGEURL, adapterSearch.getFandom(position).getParseFile("fandome_image").getUrl());
                Log.i(TAG, "the image url in the adapter search is "+ adapterSearch.getFandom(position).getImageURL());

                intent.putExtra(Fandome.KEY_NAME, adapterSearch.getFandom(position).getName());
                intent.putExtra(Fandome.KEY_DESCRIPTION, adapterSearch.getFandom(position).getDescription());

                Bundle extras = intent.getExtras();
                Log.i(TAG, "extras for image url is "+ extras.getString(Fandome.KEY_IMAGEURL));
                getContext().startActivity(intent);
            }
        };
    }


}