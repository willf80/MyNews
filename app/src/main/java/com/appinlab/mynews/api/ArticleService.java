package com.appinlab.mynews.api;

import com.appinlab.mynews.models.Article;
import com.appinlab.mynews.models.MostPopularArticle;
import com.appinlab.mynews.models.ResponseData;
import com.appinlab.mynews.models.ResultData;
import com.appinlab.mynews.models.SearchArticleData;
import com.appinlab.mynews.models.TopStoryArticle;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ArticleService {

    @GET("topstories/v2/home.json")
    Call<ResultData<List<TopStoryArticle>>> getTopStoriesArticleList(@QueryMap Map<String, String> queryMap);

    @GET("mostpopular/v2/viewed/7.json")
    Call<ResultData<List<MostPopularArticle>>> getMostPopularArticleList(@QueryMap Map<String, String> queryMap);

    @GET("search/v2/articlesearch.json")
    Call<ResponseData<SearchArticleData>> getSearchArticleList(@QueryMap Map<String, Object> queryMap);

}
