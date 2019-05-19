package com.appinlab.mynews.models;

import java.util.Date;
import java.util.List;

public class SearchArticleParameter {
    private String mQuery;
    private List<Category> mCategoryList;
    private Date mStartDate;
    private Date mEndDate;

    public SearchArticleParameter() {
    }

    public SearchArticleParameter(String query, List<Category> categoryList) {
        mQuery = query;
        mCategoryList = categoryList;
    }

    public boolean isValidForSearch() {
        return false;
    }

    public boolean isValidForNotification() {
        return  false;
    }

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        this.mQuery = query;
    }

    public List<Category> getCategoryList() {
        return mCategoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        mCategoryList = categoryList;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }
}
