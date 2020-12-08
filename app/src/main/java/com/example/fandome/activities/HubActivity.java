package com.example.fandome.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.example.fandome.PostAdapter;
import com.example.fandome.R;
import com.example.fandome.models.Fandome;
import com.example.fandome.models.Following;
import com.example.fandome.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Hub activity screen, user can view most recent posts from fandom hub
 */
public class HubActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hub);
//    }

    public static final String TAG = "HubActivity";
    private RecyclerView rvHome;
    private PostAdapter adapter;
    protected SwipeRefreshLayout swipeContainer;
    private List<Post> allPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        rvHome = findViewById(R.id.rvHome);

        swipeContainer = findViewById(R.id.swipeContainer);
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
        adapter = new PostAdapter(this,allPosts);
        rvHome.setAdapter(adapter);
        rvHome.setLayoutManager(new LinearLayoutManager(this));

        queryPosts();
    }

    private void queryPosts() {
//        String hubTitle = "Title";
//
//        Bundle extras = getIntent().getExtras();
//        if(extras != null){
//            hubTitle = extras.getString(Fandome.KEY_NAME);
//            Log.i(TAG, "hub name is "+ extras.getString(Fandome.KEY_NAME) );
//        }
        String fandomeTitle = getIntent().getStringExtra("fandomeHubName");

        ParseQuery<Fandome> fandomeParseQuery = ParseQuery.getQuery(Fandome.class);
//        followingParseQuery.whereEqualTo(Following.KEY_USER, ParseUser.getCurrentUser());
        ParseQuery<Post> postParseQuery = ParseQuery.getQuery(Post.class);
        postParseQuery.whereMatchesKeyInQuery(Post.KEY_FANDOME_NAME, Fandome.KEY_NAME, fandomeParseQuery);
//        if(Post.KEY_FANDOME_NAME != Fandome.KEY_NAME){
//            Log.e(TAG, "POST and FANDOME key don't match!");
//        }
        postParseQuery.include(Post.KEY_USER);
        postParseQuery.include(Post.KEY_FANDOME);
        postParseQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postParseQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting post for fandom",e);
                    return;
                }
                // success
                adapter.clear();
                adapter.addAll(posts);
                Log.d(TAG, "added some posts. adapter is now at size " + adapter.getItemCount());
                swipeContainer.setRefreshing(false);
            }
        });
    }



}