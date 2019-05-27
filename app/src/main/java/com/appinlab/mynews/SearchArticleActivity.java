package com.appinlab.mynews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.appinlab.mynews.models.SearchArticleParameter;
import com.appinlab.mynews.views.ArticleFilterView;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchArticleActivity extends BaseActivity {

    @BindView(R.id.articleFilterView)
    ArticleFilterView mArticleFilterView;

    @BindView(R.id.searchButton)
    Button mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);
        ButterKnife.bind(this);
        showReturnHome();

        mSearchButton.setOnClickListener(mOnSearchClickListener);
    }

    private View.OnClickListener mOnSearchClickListener = v -> {
        SearchArticleParameter searchArticleParameter = mArticleFilterView.getSearchArticleParameters();

        if(!searchArticleParameter.isValidForSearch()) {
            Toast.makeText(SearchArticleActivity.this,
                    "Query term and at least one category required !",
                    Toast.LENGTH_LONG).show();
            return;
        }

        gotoSearch(searchArticleParameter);
    };

    // Do search and show the result in result search activity
    private void gotoSearch(SearchArticleParameter searchArticleParameter) {
        String data = new Gson().toJson(searchArticleParameter);

        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra(SearchResultsActivity.EXTRA_DATA, data);
        startActivity(intent);
    }
}
