package com.example.fitgallery;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment {

    View view;
    public static ArrayList<Folder> folders;

    public AlbumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_albums, container, false);
        final GridView gallery = (GridView) view.findViewById(R.id.albumGridView);
        gallery.setAdapter(new AlbumsAdapter(AlbumsFragment.super.getActivity()));
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        if (screenWidth > screenHeight)
            gallery.setNumColumns(2);
        else
            gallery.setNumColumns(1);

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != AlbumsFragment.folders && !AlbumsFragment.folders.isEmpty()) {
                    Intent i = new Intent(AlbumsFragment.super.getActivity(), ShowAlbumItems.class);
                    i.putExtra("name",AlbumsFragment.folders.get(position).getmName());
                    i.putExtra("getPhotos", AlbumsFragment.folders.get(position).getAllPaths());
                    startActivity(i);
                }
            }
        });

        return view;
    }

}
