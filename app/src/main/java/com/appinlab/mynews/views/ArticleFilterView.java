package com.appinlab.mynews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.appinlab.mynews.R;
import com.appinlab.mynews.adapters.ArticleCategoryAdapter;
import com.appinlab.mynews.models.ArticleCategory;

import java.util.ArrayList;
import java.util.List;

public class ArticleFilterView extends LinearLayout {
    private boolean mShowDate = false;

    private RecyclerView mRecyclerView;
    private LinearLayout mDateFilterLayout;
    private ArticleCategoryAdapter mArticleCategoryAdapter;
    private List<ArticleCategory> mArticleCategories;

    public ArticleFilterView(Context context) {
        super(context);
        init(null, 0);
    }

    public ArticleFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ArticleFilterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.article_filter_view, this);
        mRecyclerView = view.findViewById(R.id.articleCategoryRecyclerView);
        mDateFilterLayout = view.findViewById(R.id.dateFilterLayout);

        mArticleCategories = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mArticleCategories.add(new ArticleCategory());
        }
        mArticleCategoryAdapter = new ArticleCategoryAdapter(mArticleCategories);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mArticleCategoryAdapter);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ArticleFilterView, defStyle, 0);

        mShowDate = a.getBoolean(R.styleable.ArticleFilterView_showDate, false);
        handleDate();
        a.recycle();
    }

    private void handleDate() {
        if(mShowDate) {
            mDateFilterLayout.setVisibility(VISIBLE);
        } else {
            mDateFilterLayout.setVisibility(GONE);
        }
    }

    public boolean isShowDate() {
        return mShowDate;
    }

    public void setShowDate(boolean showDate) {
        mShowDate = showDate;
        handleDate();
    }

    public List<ArticleCategory> getArticleCategories() {
        return mArticleCategories;
    }

    public void setArticleCategories(List<ArticleCategory> articleCategories) {
        mArticleCategories = articleCategories;
        mArticleCategoryAdapter.notifyDataSetChanged();
    }
}
