package com.appinlab.mynews.api;

import android.content.Context;

import com.appinlab.mynews.models.Article;
import com.appinlab.mynews.models.ResponseData;
import com.appinlab.mynews.models.ResultData;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class ArticleStreams {

    public static Call<ResultData<List<Article>>> getTopStoriesArticles(Context context) {
        ArticleService articleService = ApiClientConfig.getHttpClient(context).create(ArticleService.class);
        return articleService
                .getTopStoriesArticleList();
    }

    public static Call<ResultData<List<Article>>> getMostPopularArticles(Context context) {
        ArticleService articleService = ApiClientConfig.getHttpClient(context).create(ArticleService.class);
        return articleService
                .getMostPopularArticleList();
    }

    public static Call<ResponseData<List<Article>>> getSearchArticles(Context context, Map<String, Object> queryMap) {
        ArticleService articleService = ApiClientConfig.getHttpClient(context).create(ArticleService.class);
        return articleService
                .getSearchArticleList(queryMap);
    }

}
