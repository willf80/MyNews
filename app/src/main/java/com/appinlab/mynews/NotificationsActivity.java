package com.appinlab.mynews;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.appinlab.mynews.broadcast.NotificationAlarmReceiver;
import com.appinlab.mynews.models.SearchArticleParameter;
import com.appinlab.mynews.utils.SearchArticleUtils;
import com.appinlab.mynews.views.ArticleFilterView;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsActivity extends BaseActivity implements ArticleFilterView.OnDispatchListener {

    public final static String SHARED_PREF_DATA_KEY = "data";
    public final static String SHARED_PREF_ALARM_DATA_KEY = "alarm_data";
    public final static long ALARM_DAY_INTERVAL_MILLIS = 60000;//24 * 60 * 60 * 60; // 86 400 000 millis

    @BindView(R.id.articleFilterView)
    ArticleFilterView mArticleFilterView;

    @BindView(R.id.notificationSwitch)
    Switch mNotificationSwitch;

    PendingIntent mAlarmPendingIntent;
    boolean isAlarmEnabled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);
        showReturnHome();

        getSharedPreferencesInfo();

        mNotificationSwitch.setOnCheckedChangeListener(mSwitchOnCheckedChangeListener);
        mArticleFilterView.setOnDispatchListener(this);

        configureAlarmManager();

    }

    private CompoundButton.OnCheckedChangeListener
            mSwitchOnCheckedChangeListener = (group, checkedId) -> {

        if(!checkedId) {
            disableNotification();
            return;
        }

        handleSwitchChecker();
    };

    private void handleSwitchChecker() {
        if(canActiveNotification()) {
            // Activate notification
            enableNotification();
            return;
        }

        // Disable notification
        // Set switch to unchecked
        mNotificationSwitch.setChecked(false);
    }

    private boolean canActiveNotification() {
        SearchArticleParameter searchArticleParameter = mArticleFilterView.getSearchArticleParameters();
        if(!searchArticleParameter.isValidForNotification()) {
            Toast.makeText(NotificationsActivity.this,
                    "Query term and at least one category required to active notification",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void enableNotification() {
        saveSharedPreferencesNotificationInfos(mArticleFilterView.getSearchArticleParameters());
        startAlarm();
    }

    private void disableNotification() {
        saveSharedPreferencesNotificationInfos(null);
        stopAlarm();
    }

    private void configureAlarmManager() {
        Intent alarmIntent = new Intent(this, NotificationAlarmReceiver.class);
        mAlarmPendingIntent = PendingIntent.getBroadcast(this, 0,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void startAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5000,
                ALARM_DAY_INTERVAL_MILLIS, mAlarmPendingIntent);
    }

    private void stopAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(mAlarmPendingIntent);
    }

    private void getSharedPreferencesInfo() {
        SearchArticleParameter searchArticleParameter =
                SearchArticleUtils.getParameterFromSharedPreferences(this);

        mArticleFilterView.setSearchArticleParameter(searchArticleParameter);

        if(searchArticleParameter != null) {
            mNotificationSwitch.setChecked(true);
        }
    }

    private void saveSharedPreferencesNotificationInfos(SearchArticleParameter parameter) {
        String data = null;
        if(parameter != null) {
            data = new Gson().toJson(parameter);
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREF_DATA_KEY, MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(SHARED_PREF_ALARM_DATA_KEY, data)
                .apply();
    }

    @Override
    public void onCategoryListChanged(SearchArticleParameter parameter) {
        boolean isActiveNotification = mNotificationSwitch.isChecked();

        if(!isActiveNotification) return;

        if(!canActiveNotification()) return;

        saveSharedPreferencesNotificationInfos(parameter);
    }
}
