package com.example.fandome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.fandome.R;
import com.example.fandome.models.Fandome;
import com.parse.ParseFile;

import java.util.List;

public class ComposeSpinnerAdapter extends ArrayAdapter<Fandome> {

    public ComposeSpinnerAdapter(Context context, List<Fandome> fandomes){
        super(context,0,fandomes);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position,View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.compose_spinner_row,
                    parent,false
            );
        }
        ImageView ivFandomeImage = convertView.findViewById(R.id.ivFandomeImage);
        TextView tvFandomeName = convertView.findViewById(R.id.tvFandomeName);
        TextView tvFandomeKeyWord = convertView.findViewById(R.id.tvFandomeKeyWord);

        Fandome currentItem = getItem(position);

        if(currentItem != null) {
            ParseFile image = currentItem.getFandomeImage();
            if (image != null) {
                Glide.with(getContext()).load(image.getUrl()).into(ivFandomeImage);
            }
            tvFandomeName.setText(currentItem.getName());
            tvFandomeKeyWord.setText(currentItem.getKeyword());
        }

        return convertView;
    }
}
