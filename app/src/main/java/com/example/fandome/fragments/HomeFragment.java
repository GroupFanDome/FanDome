package com.example.fandome.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fandome.PostAdapter;
import com.example.fandome.R;
import com.example.fandome.models.Following;
import com.example.fandome.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for user home screen
 */
public class HomeFragment extends Fragment {

    public static final String TAG="HomeFragment";
    private RecyclerView rvHome;
    private PostAdapter adapter;
    protected SwipeRefreshLayout swipeContainer;
    private List<Post> allPosts;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvHome = view.findViewById(R.id.rvHome);

        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "Fetching new data");
                queryPosts();
            }
        });

        allPosts = new ArrayList<>();
        adapter = new PostAdapter(getContext(),allPosts);
        rvHome.setAdapter(adapter);
        rvHome.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }

    private void queryPosts() {
        ParseQuery<Following> followingParseQuery = ParseQuery.getQuery(Following.class);
        followingParseQuery.whereEqualTo(Following.KEY_USER, ParseUser.getCurrentUser());
        ParseQuery<Post> postParseQuery = ParseQuery.getQuery(Post.class);
        postParseQuery.whereMatchesKeyInQuery(Post.KEY_FANDOME, Following.KEY_FANDOME, followingParseQuery);
        postParseQuery.include(Post.KEY_USER);
        postParseQuery.include(Post.KEY_FANDOME);
        postParseQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postParseQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e("main", "Issue with getting post for fandome",e);
                    return;
                }
                // success
                adapter.clear();
                adapter.addAll(posts);
                swipeContainer.setRefreshing(false);
            }
        });
    }
}