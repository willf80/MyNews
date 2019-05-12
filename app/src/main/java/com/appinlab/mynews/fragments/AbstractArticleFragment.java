package com.appinlab.mynews.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appinlab.mynews.R;
import com.appinlab.mynews.adapters.AbstractArticleAdapter;
import com.appinlab.mynews.adapters.ArticleAdapter;
import com.appinlab.mynews.api.ArticleStreams;
import com.appinlab.mynews.models.Article;
import com.appinlab.mynews.models.ResponseData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbstractArticleFragment<TModel> extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    AbstractArticleAdapter<TModel> mArticleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);

        List<TModel> articleList = new ArrayList<>();

        mArticleAdapter = setAdapter(articleList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mArticleAdapter);

        loadArticles();

        return view;
    }

    public abstract AbstractArticleAdapter<TModel> setAdapter(@NonNull List<TModel> models);

    public abstract void loadArticles();

    public void applyData(List<TModel> articles) {
        mArticleAdapter.setArticleList(articles);
    }
}
