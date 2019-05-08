package com.appinlab.mynews;

import android.os.Bundle;

public class SearchArticleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);
        showReturnHome();
    }
}
