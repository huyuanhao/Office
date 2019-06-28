
package com.powerrich.office.oa.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.powerrich.office.oa.activity.mine.YtSearchActivity;
import com.powerrich.office.oa.enums.SearchActivityType;
import com.powerrich.office.oa.enums.SearchFragmentType;
import com.powerrich.office.oa.fragment.mine.base.BaseNavPagerFragment;

import java.util.ArrayList;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public class ActivityTypeFragment extends BaseNavPagerFragment {


    private SearchActivityType type;


    public static ActivityTypeFragment getInstance(SearchActivityType type) {
        ActivityTypeFragment fragment = new ActivityTypeFragment();
        Bundle b = new Bundle();
        b.putSerializable("type", type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (SearchActivityType) getArguments().getSerializable("type");
    }

    @Override
    public void getpositon(int position) {
        super.getpositon(position);
        YtSearchActivity activity = (YtSearchActivity) this.getActivity();
        if (activity != null)
            activity.showBottom(position);
    }


    @Override
    protected String[] getTitles() {
        String[] strs = null;
        strs = new String[]{"暂存", "在办", "已完结"};
        return strs;
    }

    @Override
    protected ArrayList<Fragment> fragmentClasses() {
        ArrayList<Fragment> list = new ArrayList<Fragment>();
        list.add(FragmentTypeFragment.getInstance(SearchFragmentType.暂存));
        list.add(FragmentTypeFragment.getInstance(SearchFragmentType.已办理));
        list.add(FragmentTypeFragment.getInstance(SearchFragmentType.已完结));
        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setPageAdapter(fragmentClasses(), getTitles());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
