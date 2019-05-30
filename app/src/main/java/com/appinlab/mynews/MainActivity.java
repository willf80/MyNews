package com.appinlab.mynews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TabLayout.BaseOnTabSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.activity_main_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.activity_main_navigation_view)
    NavigationView mNavigationView;

    TabsPagerAdapter mTabsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        initializeViewPager();

        configureDrawerLayout();

        configureNavigationView();
    }

    private void initializeViewPager() {
        mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabsPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(this);
    }

    // Configure Drawer Layout
    private void configureDrawerLayout(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView(){
        buildNavigationMenu(mNavigationView.getMenu());
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void buildNavigationMenu(Menu menu) {
        // Clean the current menu
        menu.clear();

        // Create menu with category array
        int menuGroupId = 1;
        menu.setGroupCheckable(menuGroupId, true, true);
        String[] categoryArrayList = CategoryUtils.getAllCategoryList();
        for (int i = 0; i < categoryArrayList.length; i++) {
            String category = categoryArrayList[i];

            MenuItem menuItem = menu.add(menuGroupId, i, i, category).setCheckable(true);;

            // Active the first item by default
            if(i == 0) {
                menuItem.setChecked(true);
            }
        }
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

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // Get the selected menu item id == index
        int id = menuItem.getItemId();

        // Set the current viewPager
        mViewPager.setCurrentItem(id);

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // Check navigation menu item when tab is selected
        mNavigationView.getMenu()
                .getItem(tab.getPosition())
                .setChecked(true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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
