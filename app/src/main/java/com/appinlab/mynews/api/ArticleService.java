package com.appinlab.mynews.api;

import com.appinlab.mynews.models.Article;
import com.appinlab.mynews.models.ResponseData;
import com.appinlab.mynews.models.ResultData;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ArticleService {

    @GET("topstories/v2/home.json")
    Call<ResultData<List<Article>>> getTopStoriesArticleList();

    @GET("mostpopular/v2/viewed/7.json")
    Call<ResultData<List<Article>>> getMostPopularArticleList();

    @GET("search/v2/articlesearch.json")
    Call<ResponseData<List<Article>>> getSearchArticleList(@QueryMap Map<String, Object> map);

}
