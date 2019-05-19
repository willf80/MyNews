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
    private static final String ARG_QUERY = "query";
    private static final String ARG_START_DATE = "startDate";
    private static final String ARG_END_DATE = "endDate";
    private static final String ARG_SEARCH_MODE = "searchMode";

    private String mCategoryName;
    private String mQuery;
    private String mStartDate;
    private String mEndDate;
    private boolean isSearchMode = false;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance(String filterWithCategoryName) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORIE_NAME, filterWithCategoryName);
        args.putBoolean(ARG_SEARCH_MODE, false);
        fragment.setArguments(args);
        return fragment;
    }

    public static ArticleFragment newInstance(
            String filterWithCategoriesNames, String query, String startDate, String endDate) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORIE_NAME, filterWithCategoriesNames);
        args.putString(ARG_QUERY, query);
        args.putString(ARG_START_DATE, startDate);
        args.putString(ARG_END_DATE, endDate);
        args.putBoolean(ARG_SEARCH_MODE, true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mCategoryName = bundle.getString(ARG_CATEGORIE_NAME);
            isSearchMode = bundle.getBoolean(ARG_SEARCH_MODE, false);

            if(!isSearchMode) return;

            mQuery = bundle.getString(ARG_QUERY);
            mStartDate = bundle.getString(ARG_START_DATE);
            mEndDate = bundle.getString(ARG_END_DATE);
        }
    }

    @Override
    public AbstractArticleAdapter<Article> setAdapter(@NonNull List<Article> articles) {
        return new ArticleAdapter(articles, this);
    }

    private Map<String, Object> builQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("sort", "newest");

        if(!isSearchMode) {
            map.put("fq", String.format("news_desk:(\"%s\")", mCategoryName));
        }else {
            map.put("q", mQuery);
            map.put("fq", mCategoryName);

            if(mStartDate != null) {
                map.put("begin_date", mStartDate);
            }

            if(mEndDate != null) {
                map.put("end_date", mEndDate);
            }
        }

        return map;
    }

    @Override
    public void loadArticles() {
        ArticleStreams
            .getSearchArticles(getContext(), builQueryMap())
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

                    showNotFoundMessage(articleResponseData.response.getArticleList());

                    applyData(articleResponseData.response.getArticleList());
                }

                @Override
                public void onFailure(@NonNull Call<ResponseData<SearchArticleData>> call,
                                      @NonNull Throwable t) {
                }
            });
    }

    private void showNotFoundMessage(List<Article> articles) {
        if(isSearchMode && articles.isEmpty()) {
            Toast.makeText(getContext(), "No articles found !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(Article article) {
        Intent intent = new Intent(getContext(), ArticleWebViewActivity.class);
        intent.putExtra("url", article.getUrl());
        intent.putExtra("title", article.getTitle());
        startActivity(intent);
    }
}
