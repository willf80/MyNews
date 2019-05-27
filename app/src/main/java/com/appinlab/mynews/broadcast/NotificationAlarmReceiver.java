package com.appinlab.mynews.broadcast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.appinlab.mynews.R;
import com.appinlab.mynews.SearchResultsActivity;
import com.appinlab.mynews.api.ArticleStreams;
import com.appinlab.mynews.models.ResponseData;
import com.appinlab.mynews.models.SearchArticleData;
import com.appinlab.mynews.models.SearchArticleParameter;
import com.appinlab.mynews.utils.SearchArticleUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAlarmReceiver extends BroadcastReceiver {

    Context mContext;
    String CHANNEL_ID = "default";
    SearchArticleParameter searchArticleParameter;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        searchArticleParameter = SearchArticleUtils.getParameterFromSharedPreferences(mContext);
        doSearch();
    }

    private Map<String, Object> builQueryMap(String query, String categories) {
        Map<String, Object> map = new HashMap<>();
        map.put("sort", "newest");

        map.put("q", query);
        map.put("fq", categories);

        return map;
    }

    private void doSearch() {
        if(searchArticleParameter == null) return;

        String query = searchArticleParameter.getQuery();
        String categoriesFormattedForSearch = searchArticleParameter.getCategoryListToFilterQuery();

        Map<String, Object> map = builQueryMap(query, categoriesFormattedForSearch);

        ArticleStreams.getSearchArticles(mContext, map)
            .enqueue(new Callback<ResponseData<SearchArticleData>>() {
                @Override
                public void onResponse(@NonNull Call<ResponseData<SearchArticleData>> call,
                                       @NonNull Response<ResponseData<SearchArticleData>> response) {
                    if(!response.isSuccessful()) return;

                    ResponseData<SearchArticleData> responseData = response.body();
                    if(responseData == null || responseData.response == null) return;

                    int count = responseData.response.getArticleList().size();
                    if(count <= 0) return;

                    showNotification(count);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseData<SearchArticleData>> call, @NonNull Throwable t) {

                }
            });
    }

    private void showNotification(int count) {
        String notificationMessage = String.format(Locale.getDefault(),
                "%s : Vous avez %d articles disponible", searchArticleParameter.getQuery(), count);

        // Serialize searchArticle parameter for doSearch when user click on notification
        String data = new Gson().toJson(searchArticleParameter);

        createNotificationChannel();

        // Activity shown were user clicked on notification
        Intent intent = new Intent(mContext, SearchResultsActivity.class);
        intent.putExtra(SearchResultsActivity.EXTRA_DATA, data);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.nytimes_placeholder)
                .setContentTitle("MyNews")
                .setContentText(notificationMessage)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notificationMessage))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mContext);
        // notify user
        notificationManagerCompat.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = mContext.getString(R.string.channel_name);
            String description = mContext.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
