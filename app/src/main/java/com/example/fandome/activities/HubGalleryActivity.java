package com.example.fandome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fandome.R;
import com.example.fandome.fragments.SearchFragment;
import com.example.fandome.models.Fandome;
import com.parse.ParseFile;

/**
 * Hub gallery screen that provides hub details such as its name and descrption
 */
public class HubGalleryActivity extends AppCompatActivity {

    public static final String TAG = "HubGalleryActivity";
    ImageButton fandomHubIcon;
    TextView fandomHubTitle;
    TextView hubBio;

//    TODO: Hubs may not load because there may need to be an adapter that loads images for them. search tab items load
//    TODO: and between that screen and hub activity/profile they do not have an adapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_gallery);

        fandomHubIcon = findViewById(R.id.fandomHubIcon);
        fandomHubTitle = findViewById(R.id.fandomHubTitle);
        hubBio = findViewById(R.id.hubBio);

        String hubIconURL = "image.png";
        String hubTitle = "Title";
        String hubBioDesc = "bio";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
//            Log.i(TAG, "extras is "+ extras);
            hubIconURL = extras.getString(Fandome.KEY_IMAGEURL);
            hubTitle = extras.getString(Fandome.KEY_NAME);
            hubBioDesc = extras.getString(Fandome.KEY_DESCRIPTION);
        }
        Fandome fandome = new Fandome();
        ParseFile image = fandome.getParseFile("fandome_image");
        if(image != null){
        Log.i(TAG, "hubIconURL is "+ hubIconURL);
        Glide.with(this).load(hubIconURL).into(fandomHubIcon);
        }
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
        getIntent().putExtra("fandomeHubName", fandomHubTitle.getText().toString());
        Log.i(TAG, "fandome hub title is "+ fandomHubTitle.getText().toString());
        startActivity(i);
    }


}