package com.appinlab.mynews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.appinlab.mynews.NotificationsActivity;
import com.appinlab.mynews.models.SearchArticleParameter;
import com.google.gson.Gson;

public class SearchArticleUtils {

    public static SearchArticleParameter getParameterFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NotificationsActivity.SHARED_PREF_DATA_KEY, Context.MODE_PRIVATE);

        String dataString = sharedPreferences.getString(
                NotificationsActivity.SHARED_PREF_ALARM_DATA_KEY, null);

        return new Gson().fromJson(dataString, SearchArticleParameter.class);
    }

}
