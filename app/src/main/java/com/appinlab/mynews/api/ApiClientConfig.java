package com.appinlab.mynews.api;

import android.content.Context;

import com.appinlab.mynews.BuildConfig;
import com.appinlab.mynews.R;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiClientConfig {

    static Retrofit getHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addInterceptor(interceptor);
        }

        OkHttpClient okHttpClient = builder.build();

        String baseUrl = context.getString(R.string.api_base_url);

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    static Map<String, String> getApiKey(Context context) {
        Map<String, String> map = new HashMap<>();
        String apiKey = context.getString(R.string.api_key);
        map.put("api-key", apiKey);

        return map;
    }
}
