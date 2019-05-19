package com.appinlab.mynews.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchArticleParameter implements Serializable {
    private String mQuery;
    private List<Category> mCategoryList;
    private Date mStartDate;
    private Date mEndDate;

    public SearchArticleParameter() {
        mCategoryList = new ArrayList<>();
    }

    public SearchArticleParameter(String query, List<Category> categoryList) {
        mQuery = query;
        mCategoryList = categoryList;
    }

    // Before refactoring
//    public boolean isValidForSearch() {
//        if(mCategoryList.size() <= 0) {
//            return false;
//        }
//
//        if(mQuery != null && !mQuery.isEmpty()) {
//
//            if((mStartDate != null && mEndDate != null) && (mStartDate.after(mEndDate))){
//                return false;
//            } else {
//                return true;
//            }
//        }
//
//        return false;
//    }

    public boolean isValidForSearch() {
        if(mCategoryList.size() <= 0) {
            return false;
        }

        if(mQuery != null && !mQuery.isEmpty()) {
            return (mStartDate == null || mEndDate == null) || (!mStartDate.after(mEndDate));
        }

        return false;
    }

    // Before refactoring
//    public boolean isValidForNotification() {
//        if(mCategoryList.size() <= 0) {
//            return false;
//        }
//
//        if(mQuery != null && !mQuery.isEmpty()) {
//            return true;
//        }
//
//        return  false;
//    }

    public boolean isValidForNotification() {
        if(mCategoryList.size() <= 0) {
            return false;
        }

        return mQuery != null && !mQuery.isEmpty();
    }

    public String getCategoryListToFilterQuery() {
        StringBuilder filter = new StringBuilder("news_desk:(");

        for (Category category : mCategoryList) {
            filter.append("\"").append(category.getLibelle()).append("\"");
        }
        filter.append(")");

        return filter.toString();
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
