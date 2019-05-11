package com.example.fitgallery;

import android.animation.TimeAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomNavigationView;
import android.support.media.ExifInterface;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class FullScreenPhoto extends AppCompatActivity {

    Toolbar toolBar;
    ImageView imageView;
    TextView txtModifiedDate;
    int position;
    CustomPrenferences prenferences;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
    BottomNavigationView mainNav;
    private float x1, x2, y1, y2;
    static boolean isFavoriteImage = false;
    View view;
    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;

    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prenferences = new CustomPrenferences(this);
        // fullscreen
        view = getWindow().getDecorView();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_full_screen_photo);

        toolBar = findViewById(R.id.nav_actionBar);
        setSupportActionBar(toolBar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //hide navigation
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        mainNav = findViewById(R.id.nav_bottom);

        txtModifiedDate = findViewById(R.id.txtModifiedDate);
        if (TimelinesFragment.hideToolbar == 0) {
            accessToFullScreenView();
        } else {
            exitFullScreenView();
        }

        Intent intentGet = getIntent();
        imageView = findViewById(R.id.fullImageView);

        position = Objects.requireNonNull(intentGet.getExtras()).getInt("id");
        String path=TimelinesFragment.images.get(position);
        Glide.with(getApplicationContext()).load(TimelinesFragment.images.get(position))
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(new RequestOptions().placeholder(null).fitCenter())
                .into(imageView);


        String returnUri = intentGet.getExtras().getString("path");
        assert returnUri != null;
        File file = new File(returnUri);
        txtModifiedDate.setText(sdf.format(file.lastModified()));

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        y1=event.getY();
                        x1=event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        y2=event.getY();
                        x2=event.getX();

                        try {
                            float diffX = x2 - x1;
                            float diffY = y2 - y1;
                            if((Math.abs(diffX) >= MIN_SWIPE_DISTANCE_X) && (Math.abs(diffX) <= MAX_SWIPE_DISTANCE_X))
                            {
                                if(diffX > 0)
                                {
                                    //swipe to right
                                    if (position > 0) {
//                                        finish();
//                                        Intent i = new Intent(getApplicationContext(), FullScreenPhoto.class);
//                                        i.putExtra("id", position - 1);
//                                        i.putExtra("path", TimelinesFragment.images.get(position - 1));
//                                        i.putExtra("allPath", TimelinesFragment.images);
//                                        startActivity(i);
                                        position--;
                                        Glide.with(getApplicationContext()).load(TimelinesFragment.images.get(position))
                                                .transition(new DrawableTransitionOptions().crossFade())
                                                .apply(new RequestOptions().placeholder(null).fitCenter())
                                                .into(imageView);
                                        String path=TimelinesFragment.images.get(position);
                                        File file = new File(path);
                                        txtModifiedDate.setText(sdf.format(file.lastModified()));
                                    }
                                    else{
//                                        finish();
//                                        Intent i = new Intent(getApplicationContext(), FullScreenPhoto.class);
//                                        i.putExtra("id", TimelinesFragment.images.size() - 1);
//                                        i.putExtra("path", TimelinesFragment.images.get(TimelinesFragment.images.size() - 1));
//                                        i.putExtra("allPath", TimelinesFragment.images);
//                                        startActivity(i);
                                        position=TimelinesFragment.images.size() - 1;
                                        Glide.with(getApplicationContext()).load(TimelinesFragment.images.get(position))
                                                .transition(new DrawableTransitionOptions().crossFade())
                                                .apply(new RequestOptions().placeholder(null).fitCenter())
                                                .into(imageView);
                                        String path=TimelinesFragment.images.get(position);
                                        File file = new File(path);
                                        txtModifiedDate.setText(sdf.format(file.lastModified()));

                                    }

                                }else
                                {
                                    //swipe to left
                                    if (position < TimelinesFragment.images.size() - 1) {
//                                        finish();
//                                        Intent i = new Intent(getApplicationContext(), FullScreenPhoto.class);
//                                        i.putExtra("id", position + 1);
//                                        i.putExtra("path", TimelinesFragment.images.get(position + 1));
//                                        i.putExtra("allPath", TimelinesFragment.images);
//                                        startActivity(i);
                                        position++;
                                        Glide.with(getApplicationContext()).load(TimelinesFragment.images.get(position))
                                                .transition(new DrawableTransitionOptions().crossFade())
                                                .apply(new RequestOptions().placeholder(null).fitCenter())
                                                .into(imageView);
                                        String path=TimelinesFragment.images.get(position);
                                        File file = new File(path);
                                        txtModifiedDate.setText(sdf.format(file.lastModified()));

                                    }
                                    else{
//                                        finish();
//                                        Intent i = new Intent(getApplicationContext(), FullScreenPhoto.class);
//                                        i.putExtra("id", 0);
//                                        i.putExtra("path", TimelinesFragment.images.get(0));
//                                        i.putExtra("allPath", TimelinesFragment.images);
//                                        startActivity(i);
                                        position=0;
                                        Glide.with(getApplicationContext()).load(TimelinesFragment.images.get(position))
                                                .transition(new DrawableTransitionOptions().crossFade())
                                                .apply(new RequestOptions().placeholder(null).fitCenter())
                                                .into(imageView);
                                        String path=TimelinesFragment.images.get(position);
                                        File file = new File(path);
                                        txtModifiedDate.setText(sdf.format(file.lastModified()));

                                    }
                                }
                            }
                            else if((Math.abs(diffY) >= MIN_SWIPE_DISTANCE_Y) && (Math.abs(diffY) <= MAX_SWIPE_DISTANCE_Y)){
                                finish();
                            }
                            else{
                                TimelinesFragment.hideToolbar = (TimelinesFragment.hideToolbar + 1) % 2;
                                if (TimelinesFragment.hideToolbar == 1) {
                                    accessToFullScreenView();
                                } else {
                                    exitFullScreenView();
                                }

                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        break;
                }
                return false;
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.full_screen_photo_option_menu, menu);

        isFavoriteImage = false;
        Intent i = getIntent();
        String path = Objects.requireNonNull(i.getExtras()).getString("path");
        if (null != FavoritePhotos.photos && !FavoritePhotos.photos.isEmpty()) {
            if (FavoritePhotos.photos.contains(path))
            {
                MenuItem menuItem = menu.findItem(R.id.action_Favorite);
                menuItem.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_is_favorite_photo));
                isFavoriteImage = true;
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        switch (id){
            case android.R.id.home:

                finish();
                break;

            case R.id.action_Details:
                String path=TimelinesFragment.images.get(position);
                File file = new File(path);
                final DecimalFormat dmf = new DecimalFormat("#.##"); // Tạo format cho size
                final double length = file.length();    // Lấy độ dài file
                String sLength;

                if (length > 1024 * 1024) {
                    sLength = dmf.format(length / (1024 * 1024)) + " MB";
                } else {
                    if (length > 1024) {
                        sLength = dmf.format(length / 1024) + " KB";
                    } else {
                        sLength = dmf.format(length) + " B";
                    }
                }
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(path);
                    String Details = getExif(exif);

                    Details = "Date: " + sdf.format(file.lastModified()) +
                            "\n\nSize: " + sLength +
                            "\n\nFile path: " + path +
                            Details;

                    TextView title = new TextView(getApplicationContext());
                    title.setPadding(60,50,50,50);
                    title.setText("Photo Details");
                    title.setTextSize(23);
                    title.setTypeface(null, Typeface.BOLD);
                    AlertDialog.Builder dialog= new AlertDialog.Builder(FullScreenPhoto.this,R.style.Theme_AppCompat_Light_Dialog);

                    dialog.setCustomTitle(title);
                    dialog.setMessage(Details);
                    dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;



            case R.id.action_Favorite:
                break;
            case R.id.action_slideshow:
//                Intent newIntentForSlideShowActivity = new Intent(FullScreenPhoto.this, SlideShowActivity.class);
//                newIntentForSlideShowActivity.putExtra("id", position);
//                startActivity(newIntentForSlideShowActivity);
                break;
            case R.id.action_setBackground:
                WallpaperManager bg = WallpaperManager.getInstance(getApplicationContext());
                Bitmap bitmap=((BitmapDrawable) imageView.getDrawable()).getBitmap();
                if(bitmap!=null)
                {
                    try {
                        bg.setBitmap(bitmap, null, false,
                                WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
                        Toast.makeText(getApplicationContext(), "Settings have been successfully applied!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;

        }

            return true;
    }

    private String getExif(ExifInterface exif) {
        String details = "";

        if (exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0) == 0) {
            return details;
        } else {
            details += "\n\nResolution: " + exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH) +
                    "x" + exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);

            if (exif.getAttribute(ExifInterface.TAG_MODEL) == null) {
                return details;
            }
        }

        final DecimalFormat apertureFormat = new DecimalFormat("#.#");
        String aperture = exif.getAttribute(ExifInterface.TAG_F_NUMBER);
        if (aperture != null) {
            Double aperture_double = Double.parseDouble(aperture);
            apertureFormat.format(aperture_double);
            details += "\n\nAperture: f/" + aperture_double + "\n\n";
        } else {
            details += "\n\nAperture: unknown\n\n";
        }

        details += "Model: " + exif.getAttribute(ExifInterface.TAG_MODEL)+"\n\n";

        String ExposureTime = exif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME);
        Double ExposureTime_double = Double.parseDouble(ExposureTime);
        Double Denominator = 1 / ExposureTime_double;

        ExposureTime = 1 + "/" + String.format("%.0f", Denominator);

        details += "Exposure Time: " + ExposureTime + "s\n\n";

        if (exif.getAttributeInt(ExifInterface.TAG_FLASH,0) ==0 ) {
            details += "Flash: Off\n\n";
        } else {
            details += "Flash: On\n\n";
        }
        details += "Focal Length: " + exif.getAttributeDouble(ExifInterface.TAG_FOCAL_LENGTH, 0) + "mm\n\n";
        details += "ISO: " + exif.getAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS) + "\n\n";


        return details;
    }

    public void accessToFullScreenView() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        mainNav.setVisibility(View.GONE);
        txtModifiedDate.setVisibility(View.GONE);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void exitFullScreenView(){
        mainNav.setVisibility(View.VISIBLE);
        txtModifiedDate.setVisibility(View.VISIBLE);
        Objects.requireNonNull(getSupportActionBar()).show();
    }
}
