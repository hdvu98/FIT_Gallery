package com.example.fitgallery;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Objects;

public class FavoritePhotos extends AppCompatActivity {

    public static ArrayList<String> photos = new ArrayList<>();
    CustomPrenferences prenferences;
    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        prenferences = new CustomPrenferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_timelines);
        actionBar = (ActionBar)FavoritePhotos.this.getSupportActionBar();
        assert actionBar != null;
        if(actionBar!=null)
        {
            actionBar.setTitle("Favorite");
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        GridView gallery = findViewById(R.id.galleryGridView);

        gallery.setAdapter(new TimelinesAdapter(FavoritePhotos.this, photos));
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        if (screenWidth > screenHeight) {
            gallery.setNumColumns(4);
        }

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != photos && !photos.isEmpty()) {
                    //Tạo intent gửi đến FullImageActivity
                    Intent i = new Intent(FavoritePhotos.this, FullScreenPhoto.class);
                    //Gửi vị trí ảnh hiện tại, tên ảnh và cả mảng file
                    i.putExtra("id", position);
                    i.putExtra("path", photos.get(position));
                    i.putExtra("allPath", photos);
                    startActivity(i);
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return false;
    }
}
