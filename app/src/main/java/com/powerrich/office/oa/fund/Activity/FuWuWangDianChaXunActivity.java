package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.fund.Adapter.FuWuWangDianChaXunAdapter;
import com.powerrich.office.oa.fund.bean.FuwuWangdianBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yt.simpleframe.view.recyclerview.decoration.SpaceItemDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：服务网点查询
 * 作者：梁帆
 * 时间：2019/3/1
 * 版权：
 */
public class FuWuWangDianChaXunActivity extends BaseActivity {


    @BindView(R.id.bar_back)
    ImageView mBarBack;
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private FuWuWangDianChaXunAdapter adapter;
    private List<FuwuWangdianBean> list;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fuwu_wangdian_chaxun;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mBarTitle.setText("服务网点查询");
        //模拟数据源
        list = Arrays.asList(new FuwuWangdianBean("鹰潭住房公积金管理中心","鹰潭市月湖区胜利东路32号"),
        new FuwuWangdianBean("鹰潭市住房公积金管理中心余江办事处","鹰潭市余江区广场路10号"),
                new FuwuWangdianBean("鹰潭市住房公积金管理中心贵溪办事处","鹰潭市贵溪市治金路工商银行滨江支行二楼\n"));

        mBarTitle.setText("服务网点查询");
        mRecyclerview.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(com.yt.simpleframe.R.dimen.sp_10),this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);
//        adapter = new FuWuWangDianChaXunAdapter(R.layout.item_fuwu_wangdian_chaxun, list);
//        mRecyclerview.setAdapter(adapter);
    }


    @OnClick(R.id.bar_back)
    public void onViewClicked() {
        finish();
    }
}
