
package com.powerrich.office.oa.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.powerrich.office.oa.activity.MySpaceActivity;
import com.powerrich.office.oa.activity.mine.YtSearchActivity;
import com.powerrich.office.oa.enums.SearchActivityType;
import com.powerrich.office.oa.enums.SearchFragmentType;
import com.powerrich.office.oa.fragment.mine.base.BaseNavPagerFragment;

import java.util.ArrayList;

/**
 * 我的空间 ViewPager
 * 文件名：
 * 描述：
 * 版权：
 */
public class MySpaceFragment extends BaseNavPagerFragment {


    private SearchActivityType type;
    private MySpaceHistoryFragment instance1;
    private MySpaceHistoryFragment instance2;


    public void activityChangeFragment(String data) {

        //历史
        if (viewPager.getCurrentItem() == 0) {
            instance1.autoRefresh(data);
            //全部
        } else {
            instance2.autoRefresh(data);
        }
    }


    public static MySpaceFragment getInstance(SearchActivityType type) {
        MySpaceFragment fragment = new MySpaceFragment();
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
        MySpaceActivity activity = (MySpaceActivity) this.getActivity();
//        if (activity != null)
//            activity.showBottom(position);
    }


    @Override
    protected String[] getTitles() {
        String[] strs = null;
        strs = new String[]{"历史附件", "所有证照"};
        return strs;
    }

    @Override
    protected ArrayList<Fragment> fragmentClasses() {
        instance1 = MySpaceHistoryFragment.getInstance(1);
        instance2 = MySpaceHistoryFragment.getInstance(2);
        ArrayList<Fragment> list = new ArrayList<Fragment>();
        list.add(instance1);
        list.add(instance2);
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
