package com.appinlab.mynews.api;

import android.content.Context;

import com.appinlab.mynews.models.Article;
import com.appinlab.mynews.models.MostPopularArticle;
import com.appinlab.mynews.models.ResponseData;
import com.appinlab.mynews.models.ResultData;
import com.appinlab.mynews.models.SearchArticleData;
import com.appinlab.mynews.models.TopStoryArticle;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class ArticleStreams {

    // Get top stories articles
    public static Call<ResultData<List<TopStoryArticle>>> getTopStoriesArticles(Context context) {
        ArticleService articleService = ApiClientConfig.getHttpClient(context).create(ArticleService.class);
        return articleService
                .getTopStoriesArticleList(ApiClientConfig.getApiKey(context));
    }

    // Get most popular articles
    public static Call<ResultData<List<MostPopularArticle>>> getMostPopularArticles(Context context) {
        ArticleService articleService = ApiClientConfig.getHttpClient(context).create(ArticleService.class);
        return articleService
                .getMostPopularArticleList(ApiClientConfig.getApiKey(context));
    }

    // Get search and other category articles
    public static Call<ResponseData<SearchArticleData>> getSearchArticles(Context context, Map<String, Object> queryMap) {
        ArticleService articleService = ApiClientConfig.getHttpClient(context).create(ArticleService.class);

        // Add api-key to authorize query
        queryMap.putAll(ApiClientConfig.getApiKey(context));

        return articleService
                .getSearchArticleList(queryMap);
    }

}
