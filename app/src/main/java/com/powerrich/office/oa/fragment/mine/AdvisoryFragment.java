package com.powerrich.office.oa.fragment.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.powerrich.office.oa.activity.mine.AdvisoryActivity;
import com.powerrich.office.oa.enums.AdvisoryType;
import com.powerrich.office.oa.fragment.mine.base.BaseNavPagerFragment;

import java.util.ArrayList;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public class AdvisoryFragment extends BaseNavPagerFragment {
    AdvisoryActivity mActivity;
    AdvisoryType type;

    public static AdvisoryFragment getInstance(AdvisoryType type){
        AdvisoryFragment fragment= new AdvisoryFragment();
        Bundle b = new Bundle();
        b.putSerializable("type",type);
        fragment.setArguments(b);
        return fragment;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (AdvisoryActivity) context;
        type = (AdvisoryType) getArguments().getSerializable("type");

    }


    @Override
    public void getpositon(int position) {
        super.getpositon(position);

        if (mActivity != null)
            mActivity.setBottomText(position);
    }

    @Override
    protected String[] getTitles() {
        String[] strs = new String[]{"咨询", "投诉", "建议"};
        return strs;
    }


    protected void setPageItem(){

    }

    @Override
    protected ArrayList<Fragment> fragmentClasses() {
        ArrayList<Fragment> list = new ArrayList<Fragment>();
        list.add(AdvisoryTypeFragment.getInstance(AdvisoryType.咨询));
        list.add(AdvisoryTypeFragment.getInstance(AdvisoryType.投诉));
        list.add(AdvisoryTypeFragment.getInstance(AdvisoryType.建议));
        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setPageAdapter(fragmentClasses(), getTitles());
        if(type == AdvisoryType.咨询){
            viewPager.setCurrentItem(0);
        }else if(type == AdvisoryType.投诉){
            viewPager.setCurrentItem(1);
        }else if(type == AdvisoryType.建议){
            viewPager.setCurrentItem(2);
        }
    }
}
