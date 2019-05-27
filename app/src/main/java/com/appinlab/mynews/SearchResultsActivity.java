package com.appinlab.mynews;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.appinlab.mynews.fragments.ArticleFragment;
import com.appinlab.mynews.models.SearchArticleParameter;
import com.appinlab.mynews.utils.DateUtils;
import com.google.gson.Gson;

import butterknife.ButterKnife;

public class SearchResultsActivity extends BaseActivity {
    public final static String EXTRA_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ButterKnife.bind(this);
        showReturnHome();

        String data = getIntent().getStringExtra(EXTRA_DATA);

        SearchArticleParameter searchArticleParameter =
                new Gson().fromJson(data, SearchArticleParameter.class);

        // If deserializable failed then exit
        if(searchArticleParameter == null) {
            finish();
            return;
        }

        // Show the title
        setTitle(String.format("Search : %s", searchArticleParameter.getQuery()));

        initFragment(searchArticleParameter);
    }

    private void initFragment(SearchArticleParameter searchArticleParameter) {
        String startDate = null;
        String endDate = null;

        if(searchArticleParameter.getStartDate() != null) {
            startDate = DateUtils.parseDateToString(searchArticleParameter.getStartDate(), "yyyyMMdd");
        }

        if(searchArticleParameter.getEndDate() != null) {
            endDate = DateUtils.parseDateToString(searchArticleParameter.getEndDate(), "yyyyMMdd");
        }

        String query = searchArticleParameter.getQuery();
        String categoriesFilter = searchArticleParameter.getCategoryListToFilterQuery();

        ArticleFragment articleFragment = ArticleFragment
                .newInstance(categoriesFilter, query, startDate, endDate);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, articleFragment)
                .commit();
    }
}
