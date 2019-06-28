package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.tools.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页界面
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener{

    private ViewPager viewpager;
    private ArrayList<View> mViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        mViewList = new ArrayList<View>();
        LayoutInflater lf = getLayoutInflater().from(this);
        View view1 = lf.inflate(R.layout.we_indicator1, null);
        AutoUtils.auto(view1);
        View view2 = lf.inflate(R.layout.we_indicator2, null);
        AutoUtils.auto(view2);
        View view3 = lf.inflate(R.layout.we_indicator3, null);
        AutoUtils.auto(view3);
        view3.findViewById(R.id.go_main).setOnClickListener(this);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);

        viewpager.setAdapter(new ViewPagerAdapter(mViewList));

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guide;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_main:
                goHome();
                break;

        }
    }
    /**
     * 跳转到登录界面
     */
    public void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private List<View> mViewList ;

        public ViewPagerAdapter(List<View> mViewList ) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }

}
