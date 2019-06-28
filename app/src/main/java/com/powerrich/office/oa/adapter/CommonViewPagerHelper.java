package com.powerrich.office.oa.adapter;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.common.LoadDataListener;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.view.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager共用类，直接设置数据源即可
 * 用法：
 * 在显示viewpager的地方，在布局文件中加一个LinearLayout
  <LinearLayout
 android:id="@+id/viewpage"
 style="@style/commen_match_lay"
 android:orientation="vertical">
 </LinearLayout>
 *必须在onreate方法中调用
  LayoutInflater linearLayout = getLayoutInflater();
 LinearLayout layout = (LinearLayout) findViewById(R.id.viewpage);
 CommonViewPagerHelper pagerHeplper = new CommonViewPagerHelper(this, layout);
  String[] arr = {"news", "attention", "personal"};
 pagerHeplper.onCreate(savedInstanceState);
  如果只有view，所有界面处理都在主界面中，则只设置布局界面即可
 List<View> views = new ArrayList<View>();
 views.add(linearLayout.inflate(R.layout.activity_login, null));
 views.add(linearLayout.inflate(R.layout.activity_attention, null));
 views.add(linearLayout.inflate(R.layout.activity_personal, null));
 * 如果每个界面的逻辑处理都是分离的，则需要将activity传递进来
 ArrayList<Class> clss = new ArrayList<Class>();
 clss.add(NewsActivity.class);
clss.add(AttentionActivity.class);
 clss.add(PersonalActivity.class);
List<String> title = Arrays.asList(arr);
List<PagerModel> pages = new ArrayList<PagerModel>();
for (int i = 0; i < arr.length; i++) {
        pages.add(pagerHeplper.new PagerModel(arr[i], null, clss.get(i)));
}
pagerHeplper.showViews(pages);
** Created by root on 16-6-17.
*/
public class CommonViewPagerHelper {

    /**
     * 需要使用的上下文
     * */
    private Context mContext;
    /**
     * 显示的界面列表
     * */
    private List<PagerModel> mData = new ArrayList<PagerModel>();
    private ViewPager mPager;
    private LocalActivityManager activityManager;
    private TabPageIndicator mTabPage;
    private CommonPagerAdapter mAdapter;
    private LayoutInflater mInflater;
    private ViewGroup mGroup;
    private int curIndex = 0;
    private ViewPager.OnPageChangeListener listener;

    public CommonViewPagerHelper(Context context, ViewGroup v, ViewPager.OnPageChangeListener listener){
        this.mContext = context;
        mGroup = v;
        mInflater = LayoutInflater.from(mContext);
        this.listener = listener;
        initViews();
    }

    //设置数据源，显示界面
    public void showViews(List<PagerModel> data) {
        if (!BeanUtils.isEmpty(data)) {
            for (int i = 0; i < data.size(); i++) {
                if (null == data.get(i).getCls()) {
                    break;
                }
                if (null != activityManager) {
                    data.get(i).setView(activityManager.startActivity(("view" + i), new Intent(mContext, data.get(i).getCls())).getDecorView());
                }
            }
            mData.clear();
            mData.addAll(data);
            updateUI();
        }
    }

    //刷新界面
    public void updateUI() {
        mAdapter.notifyDataSetChanged();
        mTabPage.notifyDataSetChanged();
        mTabPage.setCurrentItem(curIndex);
    }

    //获取当前显示的页面下标
    public int getCurrentIndex() {
        return curIndex;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (null == activityManager && mContext instanceof Activity) {
            activityManager = new LocalActivityManager((Activity) mContext, true);
        }
        activityManager.dispatchCreate(savedInstanceState);
    }

    public void onResume() {
        if (null != activityManager) {
            activityManager.dispatchResume();
        }
    }
    
    private void initViews() {
        View rootView = mInflater.inflate(R.layout.common_viewpager_layout, mGroup);
        mTabPage = (TabPageIndicator) rootView.findViewById(R.id.common_pager_tabs);
        mPager = (ViewPager) rootView.findViewById(R.id.common_viewpager);
        mAdapter = new CommonPagerAdapter();
        mPager.setAdapter(mAdapter);
        mTabPage.setViewPager(mPager);
        mTabPage.setOnPageChangeListener(listener);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        	curIndex=position;
//        	Activity temp = activityManager.getActivity("view" + position);
//        	if (null != mContext && mContext instanceof LoadDataListener) {
//        		((LoadDataListener) mContext).loadData(position);
//        	}
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        	if (state==0) {
        		if (null != mContext && mContext instanceof LoadDataListener) {
            		((LoadDataListener) mContext).loadData(curIndex);
            	}
			}
        }
    };

    class CommonPagerAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return mData.get(position).getTabString();
        }
        /**
         */
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
        /**
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mData.get(position).getView());
            return mData.get(position).getView();
        }
        /**
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    public class PagerModel {
        private String tabString;
        private View view;
        private Class cls;

        public PagerModel(String tabTitle, View v, Class c) {
            tabString = tabTitle;
            view = v;
            cls = c;
        }

        public String getTabString() {
            return tabString;
        }

        public void setTabString(String tabString) {
            this.tabString = tabString;
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public Class getCls() {
            return cls;
        }

        public void setIntent(Class c) {
            this.cls = c;
        }
    }

}
