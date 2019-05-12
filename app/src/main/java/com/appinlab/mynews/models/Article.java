package com.appinlab.mynews.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {

    @SerializedName("section_name")
    private String section;

    private String subsection;

    @SerializedName("snippet")
    private String title;

    @SerializedName("web_url")
    private String url;

    @SerializedName("pub_date")
    private String publishedDate;

    @SerializedName("multimedia")
    private List<Image> imageList;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}
