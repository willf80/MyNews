package com.appinlab.mynews;

import android.os.Bundle;

public class NotificationsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        showReturnHome();
    }
}
