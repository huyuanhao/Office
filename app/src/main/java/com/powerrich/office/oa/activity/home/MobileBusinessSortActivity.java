package com.powerrich.office.oa.activity.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.fragment.MobileBusinessSortFragment;
import com.powerrich.office.oa.tools.AutoUtils;

import java.util.ArrayList;

public class MobileBusinessSortActivity extends BaseActivity implements View.OnClickListener{

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main_sort;
    }

    private void initView() {
        initTitleBar("移动政商", this, null);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        initData();
    }

    private void initData() {
        ArrayList<String> titleDatas=new ArrayList<>();
        titleDatas.add("政务");
        titleDatas.add("商务");
        titleDatas.add("政商通");
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(MobileBusinessSortFragment.newInstance("17"));//政务
        fragmentList.add(MobileBusinessSortFragment.newInstance("10"));//商务
        fragmentList.add(MobileBusinessSortFragment.newInstance("16"));//政商通
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas,fragmentList);
        viewPager.setAdapter(myViewPageAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < titleDatas.size(); i++) {
            tabLayout.getTabAt(i).setCustomView(myViewPageAdapter.getTabView(i));
        }
        if (tabLayout.getTabCount() > 0) {
            TabLayout.Tab tab = tabLayout.getTabAt(0);//初始化第一条为选中状态
            setTextColor(tab, true);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() == null) return;
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.textView);
                textView.setTextColor(getResources().getColor(R.color.main_select_color));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() == null) return;
                setTextColor(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setTextColor(TabLayout.Tab tab, boolean isSelected) {
        TextView textView = (TextView) tab.getCustomView().findViewById(R.id.textView);
        if (isSelected) {
            textView.setTextColor(getResources().getColor(R.color.main_select_color));
        } else {
            textView.setTextColor(getResources().getColor(R.color.black));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
        }
    }

    public class MyViewPageAdapter extends FragmentPagerAdapter {
        private ArrayList<String> titleList;
        private ArrayList<Fragment> fragmentList;
        public MyViewPageAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Fragment> fragmentList) {
            super(fm);
            this.titleList = titleList;
            this.fragmentList = fragmentList;
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        public View getTabView(int position){
            View view = LayoutInflater.from(MobileBusinessSortActivity.this).inflate(R.layout.tab_item, null);
            TextView tv= (TextView) view.findViewById(R.id.textView);
            tv.setText(titleList.get(position));
            AutoUtils.auto(view);
            return view;
        }
    }
}
