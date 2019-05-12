package com.appinlab.mynews.fragments;


import android.content.Intent;
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
import android.widget.Toast;

import com.appinlab.mynews.ArticleWebViewActivity;
import com.appinlab.mynews.R;
import com.appinlab.mynews.adapters.AbstractArticleAdapter;
import com.appinlab.mynews.adapters.ArticleAdapter;
import com.appinlab.mynews.api.ArticleStreams;
import com.appinlab.mynews.models.Article;
import com.appinlab.mynews.models.ResponseData;
import com.appinlab.mynews.models.ResultData;
import com.appinlab.mynews.models.SearchArticleData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleFragment extends AbstractArticleFragment<Article> implements AbstractArticleAdapter.OnDispatchListener<Article> {
    private static final String ARG_CATEGORIE_NAME = "categoryName";

    private String mCategoryName;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance(String category) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORIE_NAME, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryName = getArguments().getString(ARG_CATEGORIE_NAME);
        }
    }

    @Override
    public AbstractArticleAdapter<Article> setAdapter(@NonNull List<Article> articles) {
        return new ArticleAdapter(articles, this);
    }

    @Override
    public void loadArticles() {
        Map<String, Object> map = new HashMap<>();
        map.put("q", mCategoryName);
        map.put("sort", "newest");

        ArticleStreams
            .getSearchArticles(getContext(), map)
            .enqueue(new Callback<ResponseData<SearchArticleData>>() {
                @Override
                public void onResponse(@NonNull Call<ResponseData<SearchArticleData>> call,
                                       @NonNull Response<ResponseData<SearchArticleData>> response) {

                    if(!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Get data from body
                    ResponseData<SearchArticleData> articleResponseData = response.body();

                    // If response data is null stop updated
                    if(articleResponseData == null  || articleResponseData.response == null) return;

                    applyData(articleResponseData.response.getArticleList());
                }

                @Override
                public void onFailure(@NonNull Call<ResponseData<SearchArticleData>> call,
                                      @NonNull Throwable t) {
                }
            });
    }

    @Override
    public void onItemClick(Article article) {
        Intent intent = new Intent(getContext(), ArticleWebViewActivity.class);
        intent.putExtra("url", article.getUrl());
        intent.putExtra("title", article.getTitle());
        startActivity(intent);
    }
}
