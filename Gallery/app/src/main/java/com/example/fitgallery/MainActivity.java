package com.example.fitgallery;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    CustomPrenferences prenferences;
    FragmentTransaction transaction;
    private int fragmentCode;

    TimelinesFragment timelinesFragment;
    private ViewPager viewPager;
    AlbumsFragment albumsFragment;
    PrivateFragment privateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            if(initPermission())
            {
                prenferences=new CustomPrenferences(this);


                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                Gson gson = new Gson();
                String json = sharedPreferences.getString("savedFavoriteImages","");

                Toolbar toolbar=findViewById(R.id.nav_actionBar);
                setSupportActionBar(toolbar);

                drawer=findViewById(R.id.drawer_Layout);

                ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

                TabLayout tabLayout=findViewById(R.id.tabs);
                ViewPager viewPager=findViewById(R.id.viewpager);
                TabViewPagerAdapter tabPageAdapter=new TabViewPagerAdapter(getSupportFragmentManager());
                tabPageAdapter.addFragment(new TimelinesFragment(), "Timesline");
                tabPageAdapter.addFragment(new AlbumsFragment(), "Albums");

                viewPager.setAdapter(tabPageAdapter);
                tabLayout.setupWithViewPager(viewPager);

            }
        }
        catch (Exception e)
        {
            Log.i("permission", "permission");
        }




    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("defaultFragment", fragmentCode);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_Layout);
        drawer.closeDrawer(GravityCompat.START);

        switch (id)
        {
            case R.id.nav_home:
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.nav_favorite:
                break;
        }
        return true;
    }
    public boolean initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
}
