package com.example.fandome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fandome.R;
import com.example.fandome.RecyclerViewAdapterSearch;
import com.example.fandome.fragments.SearchFragment;
import com.example.fandome.models.Fandome;
import com.parse.ParseFile;

/**
 * Hub gallery screen that provides hub details such as its name and descrption
 */
public class HubGalleryActivity extends AppCompatActivity {

    public static final String TAG = "HubGalleryActivity";

    private ImageButton fandomHubIcon;
    private TextView fandomHubTitle;
    private TextView hubBio;
    private ImageView backArrow;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_gallery);

        fandomHubIcon = findViewById(R.id.fandomHubIcon);
        fandomHubTitle = findViewById(R.id.fandomHubTitle);
        hubBio = findViewById(R.id.hubBio);
        backArrow = findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, " Back arrow pressed! ");
                onBackPressed();
            }
        });

        //placeholders for the hub image icon, title, and description
        String imageIcon = "image.png";
        String hubTitle = "Title";
        String hubBioDesc = "bio";

        Bundle extras = getIntent().getExtras();
        Log.i(TAG, "extras for image url is "+ extras.getString(Fandome.KEY_IMAGEURL));

        if(extras != null){
            imageIcon = extras.getString(Fandome.KEY_IMAGEURL);
            Log.i(TAG, "the image url from the fandome key "+ getIntent().getStringExtra(Fandome.KEY_IMAGEURL));

            hubTitle = extras.getString(Fandome.KEY_NAME);
            hubBioDesc = extras.getString(Fandome.KEY_DESCRIPTION);
        }

        //loading the extras intent from the search adapter class
        Glide.with(this).load(imageIcon).into(fandomHubIcon);
        Log.d(TAG, "image url is we got from adapter search intent is " + imageIcon);
        Log.d(TAG, "hub title we got from extra intent() is " + hubTitle);

        fandomHubIcon.setClipToOutline(true);

        fandomHubTitle.setText(hubTitle);
        hubBio.setText(hubBioDesc);

        fandomHubIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHubActivity();
            }
        });

    }

    //entering the hub activity screen
    private void goHubActivity() {
        Intent i = new Intent(this, HubActivity.class);

        title = fandomHubTitle.getText().toString();
        i.putExtra("value", title);
        Log.i(TAG, "fandome hub title is "+ fandomHubTitle.getText().toString());

        startActivity(i);
    }

}