package com.example.fitgallery;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.nfc.Tag;
import android.provider.MediaStore;
import  android.provider.MediaStore.Images.Media;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class TimelinesAdapter extends BaseAdapter {

    private Activity context;
    private LayoutInflater mInflater;
    CustomPrenferences prenferences;


    public TimelinesAdapter(Activity context) {
            this.context = context;
//           TimelinesFragment.images = getAllShownImagesPath(this.context);
        TimelinesFragment.images =new ArrayList<>();
        TimelinesFragment.images = getAllShownImagesPath(this.context);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public TimelinesAdapter(Activity context, ArrayList<String> arrayList) {
        this.context = context;
        TimelinesFragment.images = arrayList;
        mInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return TimelinesFragment.images.size() ;
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
        ImageView picturesView;
        if (convertView == null) {

            prenferences = new CustomPrenferences(context);
            //Đoạn code kiểm tra tình trạng màn hình để hiển thị số cột ảnh
            //Nếu màn hình portrait thì hiển thị 4 cột (mặc định trong layout xml), nếu màn hình landscape hiển thị 6 cột
            final Integer[] columns = prenferences.getNumberOfColumns();
            final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
            int column = columns[1];
            if (screenWidth < screenHeight) {
                column = columns[0];
            }
            int sizeOfImage = (screenWidth - (column + 1) * 8) / column;
            picturesView = new ImageView(context);
            picturesView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            picturesView.setLayoutParams(new GridView.LayoutParams(sizeOfImage, sizeOfImage));
        } else {
            picturesView = (ImageView) convertView;
        }
        Glide.with(context).load(TimelinesFragment.images.get(position))
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(new RequestOptions().placeholder(null).centerCrop())
                .into(picturesView);
        return picturesView;

    }

    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        //Khởi tạo
//
        ArrayList<String> listOfAllImages = new ArrayList<>();
        //Khởi tạo con trỏ đọc các file trong Media, lấy dữ liệu về DATA, sắp xếp theo ngày chỉnh sửa giảm dần
        Cursor cursor = activity.getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.MediaColumns.DATA} ,
                null, null, "DATE_MODIFIED DESC");
        //Nếu con trỏ không rỗng
        if (cursor!=null) {
            //Duyệt qua từng file
            while (cursor.moveToNext()) {
                //Thêm file vào mảng
                listOfAllImages.add(cursor.getString(0));
                Log.e(TAG,cursor.getString(0));
            }
            cursor.close();
        }
        return listOfAllImages;
    }
    private class ViewHolder{
        private CheckBox checkBox;
        public ImageView picturesView;
    }
}
