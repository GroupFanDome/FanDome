package com.example.fandome.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fandome.R;
import com.example.fandome.adapters.ComposeSpinnerAdapter;
import com.example.fandome.models.Fandome;
import com.example.fandome.models.Following;
import com.example.fandome.models.Post;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ComposeActivity extends AppCompatActivity {

    public static final String TAG="ComposeActivity";
    private static final int PICK_IMAGE_REQUEST = 12;
    private Uri cimageUri;
    Bitmap selectedImage;

    private Button btnPost;
    private Button btnAddImage;
    private Button btnAddFandome;
    private Button btnCloseImage;
    private Button btnCloseLayout;
    private EditText etBody;
    private ImageView ivpost;
    private LinearLayout layout_addFandome;
    private Spinner spinner;
    private ProgressBar pbLoading;

    private List<Fandome> fandomeList;
    private ParseUser currentUser;
    private ComposeSpinnerAdapter cAdapter;
    private Fandome selectedFandome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Slide slide = new Slide();

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(slide);
        getWindow().setExitTransition(slide);

        setContentView(R.layout.activity_compose);


        btnPost = findViewById(R.id.btn_Post);
        btnAddImage = findViewById(R.id.btn_addImage);
        btnAddFandome =findViewById(R.id.btn_addFandome);
        btnCloseLayout = findViewById(R.id.btn_close);
        btnCloseImage = findViewById(R.id.btn_close_image);
        layout_addFandome = findViewById(R.id.layout_addFandome);
        etBody = findViewById(R.id.et_postBody);
        final ImageView ivuser = findViewById(R.id.iv_UserImage);
        ivpost = findViewById(R.id.iv_PostImage);
        TextView tvuserName = findViewById(R.id.tv_UserName);
        spinner = findViewById(R.id.sp_fandome);
        pbLoading = findViewById(R.id.pbLoading);
        Toolbar toolbar = findViewById(R.id.compose_toolbar);

        queryCompose();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainActivity();
            }
        });
        etBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0 ){
                    btnPost.setEnabled(false);
                }else{
                    btnPost.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        currentUser = ParseUser.getCurrentUser();
        ParseFile user_image = currentUser.getParseFile("user_image");
        if(user_image != null){
            Glide.with(this).load(user_image.getUrl()).into(ivuser);
        }
        ivuser.setClipToOutline(true);
        tvuserName.setText(currentUser.getUsername());

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: The post button was clicked");
                String body = etBody.getText().toString();
                //empty
                if(body.isEmpty()){
                    Toast.makeText(ComposeActivity.this, "Sorry, your post needs text!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                pbLoading.setVisibility(View.VISIBLE);
                savePost(body);
            }
        });
        btnAddFandome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddFandome.setEnabled(false);
                layout_addFandome.setVisibility(View.VISIBLE);
            }
        });
        btnCloseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_addFandome.setVisibility(View.GONE);
                btnAddFandome.setEnabled(true);
            }
        });
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        btnCloseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivpost.setImageResource(0);
                ivpost.setVisibility(View.INVISIBLE);
                btnCloseImage.setVisibility(View.GONE);
                btnAddImage.setEnabled(true);
            }
        });

    }


    private void queryCompose(){
        ParseQuery<Following> followingParseQuery = ParseQuery.getQuery(Following.class);
        followingParseQuery.whereEqualTo(Following.KEY_USER, ParseUser.getCurrentUser());
        ParseQuery<Fandome> fandomeParseQuery = ParseQuery.getQuery(Fandome.class);
        fandomeParseQuery.whereMatchesKeyInQuery("objectId",
                "fandome.objectId", followingParseQuery);
        fandomeParseQuery.findInBackground(new FindCallback<Fandome>() {
            @Override
            public void done(List<Fandome> fandomes, ParseException e) {
                if(e != null){
                    Log.e("main", "Issue with getting fandome user follows",e);
                    return;
                }
                // success
                fandomeList = new ArrayList<>();
                fandomeList.addAll(fandomes);
                populateSpinner();
                //enable buttons
                btnAddFandome.setEnabled(true);
                btnAddImage.setEnabled(true);
            }
        });

    }
    private void populateSpinner(){
        cAdapter = new ComposeSpinnerAdapter(this,  fandomeList);
        spinner.setAdapter(cAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFandome = (Fandome) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    };

    private void savePost(String body) {
        Post post = new Post();
        post.setBody(body);
        post.setUser(currentUser);
        if(!(btnAddFandome.isEnabled())){
            post.setFandome(selectedFandome);
        }
        if(!(btnAddImage.isEnabled())){
            ParseFile file = bitmapToParseFile(selectedImage);
            post.setPostImage(file);
        }
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    pbLoading.setVisibility(View.GONE);
                    Log.e(TAG, "ERROR WHILE SAVING POST",e);
                    Toast.makeText(ComposeActivity.this, "Error while saving!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                pbLoading.setVisibility(View.GONE);
                Log.i(TAG, "POST SAVE SUCCESSFUL");
                Toast.makeText(ComposeActivity.this, "Post Saved Successful!",
                        Toast.LENGTH_LONG).show();
                etBody.setText("");
                btnPost.setEnabled(false);
                layout_addFandome.setVisibility(View.GONE);
                ivpost.setImageResource(0);
                ivpost.setVisibility(View.INVISIBLE);
                btnCloseImage.setVisibility(View.GONE);
                btnAddFandome.setEnabled(true);
                btnAddImage.setEnabled(true);
            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                 && data != null && data.getData() != null){
            cimageUri = data.getData();
            selectedImage = loadFromUri(cimageUri);
            btnAddImage.setEnabled(false);
            ivpost.setVisibility(View.VISIBLE);
            ivpost.setImageBitmap(selectedImage);
            btnCloseImage.setVisibility(View.VISIBLE);

        }else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_CANCELED){
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error Loading Image", Toast.LENGTH_SHORT).show();
        }
    }
    public Bitmap loadFromUri(Uri photoUri){
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    //convert to png
    public ParseFile bitmapToParseFile(Bitmap imageBitmap){
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        ParseFile parseFile = new ParseFile("image_file.png",imageByte);
        return parseFile;
    }
    private void goMainActivity() {
        startActivity(new Intent(ComposeActivity.this,MainActivity.class),
                ActivityOptions.makeSceneTransitionAnimation(ComposeActivity.this).toBundle());
    }

}