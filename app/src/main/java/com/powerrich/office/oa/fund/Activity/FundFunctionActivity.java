package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.fragment.mine.base.LazyLoadFragment;
import com.powerrich.office.oa.fund.bean.BaseFund;
import com.powerrich.office.oa.fund.bean.FundBean;
import com.powerrich.office.oa.fund.bean.FundFunctionBean;
import com.powerrich.office.oa.fund.bean.LoanAccountInfo;
import com.powerrich.office.oa.fund.fragment.JiaoCunFragment;
import com.powerrich.office.oa.fund.fragment.QiTaFragment;
import com.powerrich.office.oa.fund.fragment.QuanBuFragment;
import com.powerrich.office.oa.fund.fragment.TiquFragment;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.DialogUtils;
import com.yt.simpleframe.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：公积金功能模块
 * 作者：梁帆
 * 时间：2019/3/1
 * 版权：
 */
public class FundFunctionActivity extends BaseActivity {


    @BindView(R.id.bar_back)
    ImageView mBarBack;
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    int type = 0;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fund_function;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        type = getIntent().getIntExtra("type",0);
        mBarTitle.setText("全部");
        mVpContent.setOffscreenPageLimit(getTitles().size() - 1);

        mList1 = new ArrayList<>();
        mList2 = new ArrayList<>();
        mList3 = new ArrayList<>();
        mList4 = new ArrayList<>();
        if (null != invoke) {
            //公积金信息查询
//            String zjbzxbm = (String) SharedPreferencesUtils.getParam(this,"zjbzxbm","C36060");
//            String grzh = (String) SharedPreferencesUtils.getParam(this,"grzh","000008481092");
//            String ksrq = (String) SharedPreferencesUtils.getParam(this,"khrq","1950-01-01");
//            String jsrq = DateUtils.getMonthAgo(DateUtils.parseDate(ksrq,"yyyy-MM-dd"), 600, "yyyy-MM-dd");
            String zjbzxbm = FundBean.zjbzxbm;
            String grzh = FundBean.grzh;
            String ksrq = FundBean.khrq;
            String jsrq = FundBean.jsrq;
            invoke.invokeWidthDialog(OAInterface.gjjywmxcx(zjbzxbm, grzh,ksrq,jsrq,1,10000), callBack, 100);
        }
    }

    List<FundFunctionBean> mList1,mList2,mList3,mList4;
    IRequestCallBack callBack = new BaseRequestCallBack() {
        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String message = item.getString("message");
            String code = item.getString("code");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (100 == what) {
                    String s = item.getDataStr();
                    BaseFund<FundFunctionBean> fundInfo  = new Gson().fromJson(s, new TypeToken<BaseFund<FundFunctionBean>>() {
                    }.getType());

                    if(fundInfo.getDATA()!=null){
                        if(fundInfo.getDATA().size()>0){
                            mList1 = fundInfo.getDATA();
                            for (int i = 0; i < fundInfo.getDATA().size(); i++) {
                                if(fundInfo.getDATA().get(i).getYwlx().equals("缴存")){
                                    mList2.add(fundInfo.getDATA().get(i));
                                }else if(fundInfo.getDATA().get(i).getYwlx().equals("提取")){
                                    mList3.add(fundInfo.getDATA().get(i));
                                }if(fundInfo.getDATA().get(i).getYwlx().equals("其他")){
                                    mList4.add(fundInfo.getDATA().get(i));
                                }
                            }

                            PageAdapter myAdapter = new PageAdapter(getSupportFragmentManager(), getTitles(), getFragments());
                            mVpContent.setAdapter(myAdapter);
                            mVpContent.setOffscreenPageLimit(getTitles().size() - 1);
                            myAdapter.notifyDataSetChanged();

                            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
                            mTabLayout.setupWithViewPager(mVpContent);

                            // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
                            mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabLayout.Tab tab) {
                                    mVpContent.setCurrentItem(tab.getPosition(), false);
                                    mBarTitle.setText(getTitles().get(tab.getPosition()));
                                }

                                @Override
                                public void onTabUnselected(TabLayout.Tab tab) {
                                }

                                @Override
                                public void onTabReselected(TabLayout.Tab tab) {
                                }
                            });

                            mVpContent.setCurrentItem(type);
//                            setData(fundInfo);
                        }
                    }
                }
            } else {
                DialogUtils.showToast(FundFunctionActivity.this, message);
            }
        }
    };


    //宿主activity中的getTitles()方法
    public List<FundFunctionBean> getList1(){
        return mList1;
    }

    public List<FundFunctionBean> getList2(){
        return mList2;
    }

    public List<FundFunctionBean> getList3(){
        return mList3;
    }
    public List<FundFunctionBean> getList4(){
        return mList4;
    }



    @OnClick(R.id.bar_back)
    public void onViewClicked() {
        finish();
    }

    protected List<LazyLoadFragment> getFragments() {
        List<LazyLoadFragment> lazyLoadFragments = Arrays.asList(new QuanBuFragment(), new JiaoCunFragment(),
                new TiquFragment(), new QiTaFragment());
        return lazyLoadFragments;
    }

    protected List<String> getTitles() {
        List<String> strings = Arrays.asList("全部", "缴存", "提取", "其他");
        return strings;
    }


    protected class PageAdapter extends FragmentPagerAdapter {
        private List<String> titles;
        List<LazyLoadFragment> lazyLoadFragments;


        public PageAdapter(FragmentManager fm, List<String> titles, List<LazyLoadFragment> lazyLoadFragments) {
            super(fm);
            this.titles = titles;
            this.lazyLoadFragments = lazyLoadFragments;
        }


        @Override
        public LazyLoadFragment getItem(int position) {
            return lazyLoadFragments.get(position);
        }


        @Override
        public int getCount() {
            return lazyLoadFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
