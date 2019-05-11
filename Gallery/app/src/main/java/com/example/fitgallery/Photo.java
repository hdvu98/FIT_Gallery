package com.example.fitgallery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Date;

public class Photo implements Parcelable {


    private String mPath;
    private String mFolderName;
    private long mModifiedDate;

    public Photo() {
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public String getmFolderName() {
        return mFolderName;
    }

    public void setmFolderName(String mFolderName) {
        this.mFolderName = mFolderName;
    }

    public long getmModifiedDate() {
        return mModifiedDate;
    }

    public void setmModifiedDate(long mModifiedDate) {
        this.mModifiedDate = mModifiedDate;
    }

    protected Photo(Parcel in) {
        this.mPath=in.readString();
        this.mFolderName=in.readString();
        this.mModifiedDate=in.readLong();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPath);
        dest.writeString(mFolderName);
        dest.writeLong(mModifiedDate);
    }
}
