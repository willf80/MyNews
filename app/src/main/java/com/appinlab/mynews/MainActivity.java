package com.appinlab.mynews;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.appinlab.mynews.fragments.ArticleFragment;
import com.appinlab.mynews.fragments.MostPopulateArticleFragment;
import com.appinlab.mynews.fragments.TopStoriesArticleFragment;
import com.appinlab.mynews.utils.CategoryUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    TabsPagerAdapter mTabsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        initializeViewPager();
    }

    private void initializeViewPager() {
        mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabsPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return handleOptionsMenuActions(item.getItemId());
    }

    private boolean handleOptionsMenuActions(int menuItemId) {
        switch (menuItemId) {
            case R.id.app_bar_search:
                openActivity(SearchArticleActivity.class);
                return true;

            case R.id.app_bar_notifications:
                openActivity(NotificationsActivity.class);
                return true;

            default:
                return false;
        }
    }

    private void openActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    // Tabs pager adapter
    // Define the different tabs page fragment
    public class TabsPagerAdapter extends FragmentPagerAdapter {

        String [] allCategoryList = CategoryUtils.getAllCategoryList();

        TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return TopStoriesArticleFragment.newInstance();

                case 1:
                    return MostPopulateArticleFragment.newInstance();

                default:
                    return ArticleFragment.newInstance(allCategoryList[i]);
            }

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return allCategoryList[position];
        }

        @Override
        public int getCount() {
            return allCategoryList.length;
        }
    }
}
