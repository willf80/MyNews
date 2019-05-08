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

        static final int TAB_ELEMENT_COUNT = 3;

        TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return ArticleFragment.newInstance("", "");
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tab_name_most_popular);

                case 1:
                    return getString(R.string.tab_name_top_stories);

                case 2:
                    return getString(R.string.tab_name_business);
            }
            return super.getPageTitle(position);
        }

        @Override
        public int getCount() {
            return TAB_ELEMENT_COUNT;
        }
    }
}
