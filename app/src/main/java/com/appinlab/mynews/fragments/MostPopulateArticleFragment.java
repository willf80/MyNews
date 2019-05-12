package com.appinlab.mynews.fragments;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.appinlab.mynews.ArticleWebViewActivity;
import com.appinlab.mynews.R;
import com.appinlab.mynews.adapters.AbstractArticleAdapter;
import com.appinlab.mynews.adapters.MostPopularArticleAdapter;
import com.appinlab.mynews.api.ArticleStreams;
import com.appinlab.mynews.models.MostPopularArticle;
import com.appinlab.mynews.models.ResultData;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopulateArticleFragment extends AbstractArticleFragment<MostPopularArticle> implements AbstractArticleAdapter.OnDispatchListener<MostPopularArticle> {

    public static MostPopulateArticleFragment newInstance() {
        return new MostPopulateArticleFragment();
    }

    @Override
    public AbstractArticleAdapter<MostPopularArticle> setAdapter(@NonNull List<MostPopularArticle> mostPopularArticles) {
        return new MostPopularArticleAdapter(mostPopularArticles, this);
    }

    @Override
    public void loadArticles() {
        ArticleStreams
            .getMostPopularArticles(getContext())
            .enqueue(new Callback<ResultData<List<MostPopularArticle>>>() {
                @Override
                public void onResponse(@NonNull Call<ResultData<List<MostPopularArticle>>> call,
                                       @NonNull Response<ResultData<List<MostPopularArticle>>> response) {

                    if(!response.isSuccessful()) {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Get data from body
                    ResultData<List<MostPopularArticle>> articleResponseData = response.body();

                    // If response data is null stop updated
                    if(articleResponseData == null  || articleResponseData.results == null) return;

                    applyData(articleResponseData.results);
                }

                @Override
                public void onFailure(@NonNull Call<ResultData<List<MostPopularArticle>>> call,
                                      @NonNull Throwable t) {
                }
            });
    }

    @Override
    public void onItemClick(MostPopularArticle article) {
        Intent intent = new Intent(getContext(), ArticleWebViewActivity.class);
        intent.putExtra("url", article.getUrl());
        intent.putExtra("title", article.getTitle());
        startActivity(intent);
    }
}
