package com.example.fitgallery;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.sql.Time;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelinesFragment extends Fragment {

    //View
    View view;
    //Mảng tĩnh chứa danh sách file
    public static ArrayList<String> images;
    //Trạng thái ẩn/hiện của toolbar, 0 là hiện, 1 là ẩn
    static int hideToolbar = 0;
    CustomPrenferences prenferences;


    public TimelinesFragment() {
        // Required empty public constructor
    }
    public static TimelinesFragment newInstance() {
        return new TimelinesFragment();
    }

    private float x1, x2, y1, y2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timelines, container, false);
        final GridView gallery = (GridView) view.findViewById(R.id.galleryGridView);

        prenferences = new CustomPrenferences(getContext());

        final Integer[] columns = prenferences.getNumberOfColumns();
        final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        if (screenWidth > screenHeight) {
            gallery.setNumColumns(columns[1]);
        } else {
            gallery.setNumColumns(columns[0]);
        }
        gallery.setAdapter(new TimelinesAdapter(this.getActivity()));

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (null != images && !images.isEmpty()) {
                    //Tạo intent gửi đến FullImageActivity
                    Intent i = new Intent(TimelinesFragment.super.getActivity(), FullScreenPhoto.class);
                    //Gửi vị trí ảnh hiện tại, tên ảnh và cả mảng file
                    i.putExtra("id", position);
                    i.putExtra("path", images.get(position));
                    i.putExtra("allPath", images);
                    startActivity(i);
                }
            }
        });

        return view;
    }



}
