package com.appinlab.mynews.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Media {

    @SerializedName("media-metadata")
    private List<Image> mImageList;

    public List<Image> getImageList() {
        return mImageList;
    }

    public void setImageList(List<Image> imageList) {
        mImageList = imageList;
    }

}
