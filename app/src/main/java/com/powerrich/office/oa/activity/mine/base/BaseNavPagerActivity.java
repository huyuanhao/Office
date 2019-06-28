package com.powerrich.office.oa.activity.mine.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.powerrich.office.oa.R;
import com.yt.simpleframe.tabpagerindictor.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujian on 2017/3/12.
 * 顶部导航栏选项
 */

public abstract class BaseNavPagerActivity extends YTBaseActivity {

    protected ViewPager viewPager;
    private TabPageIndicator tabPageIndicator;
    private PageAdapter pageAdapter;
    private FrameLayout mFlNodata;
    private LinearLayout mLltMainContent;


    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.fragment_base_nav_pager);
    }


    protected abstract String[] getTitles();

    protected abstract ArrayList<Fragment> fragmentClasses();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.tabs);
        mFlNodata  = (FrameLayout) findViewById(R.id.fl_nodata);
        mLltMainContent = (LinearLayout) findViewById(R.id.main_content);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

//        pageAdapter = new PageAdapter(getSupportFragmentManager());
//        String[] titles = getTitles();
//        for (int i = 0; i < titles.length; i++) {
//            pageAdapter.addFragment(fragmentClasses().get(i), titles[i]);
//        }
//        viewPager.setAdapter(pageAdapter);
//        tabPageIndicator.setViewPager(viewPager);
//        tabPageIndicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_SAME);
//        tabPageIndicator.setOnTabClickListener(new TabPageIndicator.OnTabClickListener() {
//            @Override
//            public void onClick(int position) {
//                viewPager.setCurrentItem(position);
//            }
//        });
    }

    public void showNodata(){
        mLltMainContent.setVisibility(View.GONE);
        mFlNodata.setVisibility(View.VISIBLE);
    }

    public void setPageAdapter(String[] titles){
        viewPager.setOffscreenPageLimit(titles.length -1);

        pageAdapter = new PageAdapter(getSupportFragmentManager());
//        String[] titles = getTitles();
        for (int i = 0; i < titles.length; i++) {
            pageAdapter.addFragment(fragmentClasses().get(i), titles[i]);
        }
        viewPager.setAdapter(pageAdapter);
        tabPageIndicator.setViewPager(viewPager);
        tabPageIndicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_SAME);
        tabPageIndicator.setOnTabClickListener(new TabPageIndicator.OnTabClickListener() {
            @Override
            public void onClick(int position) {
                viewPager.setCurrentItem(position);
            }
        });

    }
    public void getpositon(int position){
    }

    protected class PageAdapter extends FragmentPagerAdapter {
        private List<CharSequence> titles = new ArrayList<>();
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, CharSequence title) {
//            fragmentClasses().add(fragment);
            titles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentClasses().get(position);
        }

        @Override
        public int getCount() {
            return fragmentClasses().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
