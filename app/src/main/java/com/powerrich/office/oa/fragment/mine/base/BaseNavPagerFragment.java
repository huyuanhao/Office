package com.powerrich.office.oa.fragment.mine.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yt.simpleframe.R;
import com.yt.simpleframe.adapter.BaseFragmentAdapter;
import com.yt.simpleframe.tabpagerindictor.TabPageIndicator;
import com.yt.simpleframe.utils.ArrayUtils;

import java.util.ArrayList;


/**
 * Created by wujian on 2017/3/12.
 * 顶部导航栏选项
 */

public abstract class BaseNavPagerFragment extends YtBaseFragment {

    protected ViewPager viewPager;
    public TabPageIndicator tabPageIndicator;
    private BaseFragmentAdapter pageAdapter;


//    private TextView statusBarTv;
    @Override
    protected View createContentView() {
        View view = inflateContentView(R.layout.fragment_base_nav_pager);
        return view;

    }

    protected abstract String[] getTitles();

    protected abstract ArrayList<Fragment> fragmentClasses();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public   void  setPageAdapter(ArrayList<Fragment> fragments, String[] titles){
        pageAdapter = new BaseFragmentAdapter(getChildFragmentManager(),fragments, ArrayUtils.getStringList(titles));
        viewPager.setAdapter(pageAdapter);
        tabPageIndicator.setViewPager(viewPager);
        tabPageIndicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_NOSAME);
        tabPageIndicator.setOnTabClickListener(new TabPageIndicator.OnTabClickListener() {
            @Override
            public void onClick(int position) {
                viewPager.setCurrentItem(position);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                getpositon(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void getpositon(int position){
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabPageIndicator = (TabPageIndicator) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

    }

}
