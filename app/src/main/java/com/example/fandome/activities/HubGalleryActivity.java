package com.example.fandome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

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
//    ImageButton fandomHubIcon;
//    TextView fandomHubTitle;
//    TextView hubBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_gallery);

        ImageButton fandomHubIcon = findViewById(R.id.fandomHubIcon);
        TextView fandomHubTitle = findViewById(R.id.fandomHubTitle);
        TextView hubBio = findViewById(R.id.hubBio);

        String hubIconURL = "image.png";
        String hubTitle = "Title";
        String hubBioDesc = "bio";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            hubIconURL = extras.getString(Fandome.KEY_IMAGEURL);
            hubTitle = extras.getString(Fandome.KEY_NAME);
            hubBioDesc = extras.getString(Fandome.KEY_DESCRIPTION);
        }
        Fandome fandome = new Fandome();
        ParseFile image = fandome.getParseFile("fandome_image");
        if(image != null){
            Glide.with(this).load(hubIconURL).into(fandomHubIcon);
        }
        fandomHubIcon.setClipToOutline(true);


        fandomHubTitle.setText(hubTitle);
        hubBio.setText(hubBioDesc);

    }


}