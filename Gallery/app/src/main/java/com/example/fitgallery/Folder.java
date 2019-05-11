package com.example.fitgallery;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Folder implements Parcelable {

    //Tên thư mục
    private String mName;
    //Danh sách các tập tin
    private ArrayList<Photo> mPhotos = new ArrayList<>();

    public Folder() {
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ArrayList<Photo> getmPhotos() {
        return mPhotos;
    }

    ArrayList<String> getAllPaths() {
        ArrayList<String> allFileName = new ArrayList<>();
        for(int i=0;i<mPhotos.size();i++) {
            allFileName.add(mPhotos.get(i).getmPath());
        }
        return allFileName;
    }

    public void setmPhotos(ArrayList<Photo> mPhotos) {
        this.mPhotos = mPhotos;
    }

    void addPhoto(Photo photo) {
        mPhotos.add(photo);
    }
    int getFolderSize(){
        return mPhotos.size();
    }

    Photo getLastedPhoto(){
        return mPhotos.get(0);
    }

    ArrayList<String> GetFileNames() {
        ArrayList<String> fileNames = new ArrayList<>();
        for(int i=0;i<mPhotos.size();i++) {
            fileNames.add(mPhotos.get(i).getmPath());
        }
        return fileNames;
    }

    protected Folder(Parcel in) {
        mName=in.readString();
        mPhotos=in.createTypedArrayList(Photo.CREATOR);
    }

    public static final Creator<Folder> CREATOR = new Creator<Folder>() {
        @Override
        public Folder createFromParcel(Parcel in) {
            return new Folder(in);
        }

        @Override
        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeTypedList(mPhotos);
    }
}
