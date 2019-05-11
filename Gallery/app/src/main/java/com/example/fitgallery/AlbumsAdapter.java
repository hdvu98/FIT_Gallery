package com.example.fitgallery;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AlbumsAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater mInflater;

    AlbumsAdapter(Activity context) {
        activity = context;
        AlbumsFragment.folders = getMedias(activity);
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return AlbumsFragment.folders.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.album_item, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.albumName);
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.albumThumbnail);
            holder.thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
            int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
            int sizeOfImage = screenWidth;
            if (screenWidth > screenHeight) {
                sizeOfImage = screenWidth / 2;
            }
            holder.thumbnail.setLayoutParams(new RelativeLayout.LayoutParams(sizeOfImage,sizeOfImage/2));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(activity).load(AlbumsFragment.folders.get(position).getLastedPhoto().getmPath())
                .apply(new RequestOptions().placeholder(null).centerCrop())
                .into(holder.thumbnail);
        String title = "";
            title += String.valueOf(AlbumsFragment.folders.get(position).getFolderSize());
            title+="\n"+AlbumsFragment.folders.get(position).getmName();
        holder.name.setText(title);
        return convertView;
    }

    private class ViewHolder {
        private TextView name;
        public ImageView thumbnail;
    }

    private static final String[] MEDIA = {
            MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
    };

    private ArrayList<Folder> getMedias(Activity activity) {



        Map<String, Folder> albumFolderMap = new HashMap<>();
        Cursor cursor = activity.getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MEDIA, null, null, "DATE_MODIFIED DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                Photo file = new Photo();
                file.setmPath(cursor.getString(0));
                file.setmFolderName(name);
                Folder albumFolder = albumFolderMap.get(name);
                if (albumFolder != null)
                    albumFolder.addPhoto(file);
                else {
                    albumFolder = new Folder();
                    albumFolder.setmName(name);
                    albumFolder.addPhoto(file);
                    albumFolderMap.put(name, albumFolder);
                }
            }
            cursor.close();
        }
        ArrayList<Folder> folders = new ArrayList<>();
        for (Map.Entry<String, Folder> folderEntry : albumFolderMap.entrySet()) {
            Folder albumFolder = folderEntry.getValue();
//            Collections.sort(albumFolder.getmPhotos());
                folders.add(albumFolder);
        }
        return folders;
    }

}
