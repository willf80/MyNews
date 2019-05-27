package com.appinlab.mynews.fragments;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.appinlab.mynews.ArticleWebViewActivity;
import com.appinlab.mynews.adapters.AbstractArticleAdapter;
import com.appinlab.mynews.adapters.TopStoryArticleAdapter;
import com.appinlab.mynews.api.ArticleStreams;
import com.appinlab.mynews.models.ResultData;
import com.appinlab.mynews.models.TopStoryArticle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopStoriesArticleFragment extends AbstractArticleFragment<TopStoryArticle> implements AbstractArticleAdapter.OnDispatchListener<TopStoryArticle> {

    public static TopStoriesArticleFragment newInstance() {
        return new TopStoriesArticleFragment();
    }

    @Override
    public AbstractArticleAdapter<TopStoryArticle> setAdapter(@NonNull List<TopStoryArticle> topStoryArticles) {
        return new TopStoryArticleAdapter(topStoryArticles, this);
    }

    @Override
    public void loadArticles() {
        ArticleStreams
            .getTopStoriesArticles(getContext())
            .enqueue(new Callback<ResultData<List<TopStoryArticle>>>() {
                @Override
                public void onResponse(@NonNull Call<ResultData<List<TopStoryArticle>>> call,
                                       @NonNull Response<ResultData<List<TopStoryArticle>>> response) {

                    if(!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Get data from body
                    ResultData<List<TopStoryArticle>> articleResponseData = response.body();

                    // If response data is null stop updated
                    if(articleResponseData == null  || articleResponseData.results == null) return;

                    applyData(articleResponseData.results);
                }

                @Override
                public void onFailure(@NonNull Call<ResultData<List<TopStoryArticle>>> call,
                                      @NonNull Throwable t) {
                }
            });
    }

    @Override
    public void onItemClick(TopStoryArticle article) {
        Intent intent = new Intent(getContext(), ArticleWebViewActivity.class);
        intent.putExtra("url", article.getUrl());
        intent.putExtra("title", article.getTitle());
        startActivity(intent);
    }
}
