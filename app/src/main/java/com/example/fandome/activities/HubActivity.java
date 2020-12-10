package com.example.fandome.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fandome.PostAdapter;
import com.example.fandome.R;
import com.example.fandome.RecyclerViewAdapterFH;
import com.example.fandome.fragments.ProfileFragment;
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

    public static final String TAG = "HubActivity";

    private RecyclerView rvHome;
    private PostAdapter adapter;
    protected SwipeRefreshLayout swipeContainer;
    private List<Post> allPosts;
    private ImageView backArrow;
    private TextView fandomHubTitle;
    private Button followHubButton;
    private RecyclerViewAdapterFH adapterFH;
    private List<Following> fandomeHub;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        rvHome = findViewById(R.id.rvHome);
        backArrow = findViewById(R.id.backArrow);
        fandomHubTitle = findViewById(R.id.fandomHubTitle);
        followHubButton = findViewById(R.id.followHubButton);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, " Back arrow pressed! ");
                onBackPressed();
            }
        });

        followHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Follow hub button was pressed!");
                if(followHubButton.isSelected()){
                    followHubButton.setBackgroundColor(Color.parseColor("#035CBC"));
                    followHubButton.setTextColor(Color.parseColor("#FFFFFF"));
                    followHubButton.setSelected(false);
                    followHub();
                }
                else{
                    followHubButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    followHubButton.setTextColor(Color.parseColor("#3F51B5"));
                    followHubButton.setSelected(true);
                    unfollowHub();
                }
//                followHubButton.setBackgroundColor(Color.parseColor("#035CBC"));
//                followHub();
            }
        });

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

    @Override
    public void onResume(){
        super.onResume();
        title = getIntent().getExtras().getString("value");
        fandomHubTitle.setText(title);

        //Add checker to see if current hub you're in belongs to your followed hub list.
        // if so, change button apperance accordingly
        isHubFollowed();
    }

    /**
     * Checks to see if the current hub a user is in is a followed hub
     * then changes follow appearance button according
     * @return false if current hub title DOES NOT match with a hub title in their followed hubs list
     *         true if current hub title DOES match with a hub title in their followed hubs list
     */
    private void isHubFollowed() {
        //getting the name of the current hub a user is in
        title = getIntent().getExtras().getString("value");
        fandomHubTitle.setText(title);

        //going into the user's following class
        ParseQuery<Following> followingParseQuery = ParseQuery.getQuery(Following.class);
//        followingParseQuery.whereEqualTo(Following.KEY_USER, ParseUser.getCurrentUser());
        //find if there's a fandom hub in their following list that matches the fandome hub of the hub activity they are in
        followingParseQuery.whereEqualTo(Following.KEY_FANDOME, fandomHubTitle.getText().toString());
        followingParseQuery.include(Following.KEY_FANDOME);
        followingParseQuery.findInBackground(new FindCallback<Following>() {
                @Override
                public void done(List<Following> followedHubs, ParseException e) {
                    for (Following hub : followedHubs){
                        //for every hub within the user's followed hub list
                        //check if the followed hubs match with the hub activity
                        //Log.i(TAG, "Current hub matches a hub in the user's following list!");
                    }
                }
            });

//        ParseQuery<Following> followingParseQuery = ParseQuery.getQuery("Following");
//
//        //if the user is in the hub page of a hub they are already following
//                if(title == fandomHubTitle.getText().toString()){
//                    //change button appearance to dark this is the UNFOLLOWED state of button
//                    followHubButton.setBackgroundColor(Color.parseColor("#035CBC"));
//                    followHubButton.setTextColor(Color.parseColor("#FFFFFF"));
//                    followHubButton.setSelected(false);
//                    Toast.makeText(HubActivity.this, "You are currently following this fandom hub", Toast.LENGTH_SHORT).show();
//                    return true;
//                } else if(title != fandomHubTitle.getText().toString()){
//                    //change button appearance to default white this is the UNFOLLOWED state of button
//                    followHubButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    followHubButton.setTextColor(Color.parseColor("#3F51B5"));
//                    followHubButton.setSelected(true);
//                    Toast.makeText(HubActivity.this, "You are currently not following this fandom hub", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//        return false;
    }

    /**
     * if ParseUser.getCurrentUser is ALREADY following the current fandom hub they're in
     * REMOVE current hub from (followed hubs) adapter
     */
    private void followHub() {
        title = getIntent().getExtras().getString("value");
        fandomHubTitle.setText(title);

//        if(isHubFollowed()==true){
//            Toast.makeText(HubActivity.this, "Followed hub " +fandomHubTitle.getText().toString(), Toast.LENGTH_SHORT).show();
//
////            REMOVE hub from adapter
////            Log.d(TAG, "added some hubs. adapter is now at size " + adapterSearch.getItemCount());
////            adapterFH.notifyDataSetChanged();
//        }

    }

    /**
     * if ParseUser.getCurrentUser is NOT following the current fandom hub they're in
     * ADD current hub to (followed hubs) adapter
     */
    private void unfollowHub() {
        title = getIntent().getExtras().getString("value");
        fandomHubTitle.setText(title);

        if(Following.KEY_FANDOME != fandomHubTitle.getText().toString()){
            Toast.makeText(HubActivity.this, "Unfollowed hub " +fandomHubTitle.getText().toString(), Toast.LENGTH_SHORT).show();
//            ADD hub to adapter
//            Log.d(TAG, "added some hubs. adapter is now at size " + adapterSearch.getItemCount());
//            adapterFH.notifyDataSetChanged();
        }

    }

    private void queryPosts() {

        title = getIntent().getExtras().getString("value");

        ParseQuery<Fandome> fandomeParseQuery = ParseQuery.getQuery(Fandome.class);
//        followingParseQuery.whereEqualTo(Following.KEY_USER, ParseUser.getCurrentUser());
        ParseQuery<Post> postParseQuery = ParseQuery.getQuery(Post.class);
//        postParseQuery.whereMatchesKeyInQuery(Post.KEY_FANDOME_NAME, Fandome.KEY_NAME, fandomeParseQuery);
        postParseQuery.whereEqualTo(Post.KEY_FANDOME_NAME, title);
        if(Post.KEY_FANDOME_NAME == title){
            Log.e(TAG, "POST and INTENT TITLE match!");
        }
        postParseQuery.include(Post.KEY_USER);
        postParseQuery.include(Post.KEY_FANDOME_NAME);
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