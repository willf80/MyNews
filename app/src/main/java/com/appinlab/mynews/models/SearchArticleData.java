package com.appinlab.mynews.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchArticleData {

    @SerializedName("docs")
    private List<Article> mArticleList;

    public List<Article> getArticleList() {
        return mArticleList;
    }

    public void setArticleList(List<Article> articleList) {
        mArticleList = articleList;
    }
}
