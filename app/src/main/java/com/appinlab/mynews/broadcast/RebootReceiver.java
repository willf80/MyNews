package com.appinlab.mynews.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.appinlab.mynews.NotificationsActivity;
import com.appinlab.mynews.models.SearchArticleParameter;
import com.appinlab.mynews.utils.SearchArticleUtils;

public class RebootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("RebootReceiver", "Notifications alarm started");

        SearchArticleParameter searchArticleParameter = SearchArticleUtils.getParameterFromSharedPreferences(context);
        if(searchArticleParameter.isValidForNotification()) {
            startAlarm(context);
        }
    }

    private void startAlarm(Context context) {
        Intent alarmIntent = new Intent(context, NotificationAlarmReceiver.class);
        PendingIntent mAlarmPendingIntent = PendingIntent.getBroadcast(
                context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5000,
                NotificationsActivity.ALARM_DAY_INTERVAL_MILLIS, mAlarmPendingIntent);
    }
}
