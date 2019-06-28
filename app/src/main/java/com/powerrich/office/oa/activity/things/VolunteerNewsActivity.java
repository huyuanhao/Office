package com.powerrich.office.oa.activity.things;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.ImageLoad;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.VolunteerImgsBean;
import com.yt.simpleframe.http.bean.VolunteerListBean;
import com.yt.simpleframe.http.bean.entity.VolunteerInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.StringUtil;
import com.yt.simpleframe.view.convenientbanner.CBViewHolderCreator;
import com.yt.simpleframe.view.convenientbanner.ConvenientBanner;
import com.yt.simpleframe.view.convenientbanner.Holder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/30
 * 版权：
 */

public class VolunteerNewsActivity extends YTBaseActivity {

    @BindView(R.id.rcv_voluteer_list)
    RecyclerView mRcvVoluteerList;
    @BindView(R.id.empty_retry_view)
    ImageView mEmptyRetryView;
    @BindView(R.id.empty_view_tv)
    TextView mEmptyViewTv;
    @BindView(R.id.empty_view)
    RelativeLayout mEmptyView;
    @BindView(R.id.fl_nodata)
    FrameLayout mFlNodata;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @Override
    protected View onCreateContentView() {
//        return super.onCreateContentView();
        View view = inflateContentView(R.layout.activity_volunteer_list);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("志愿者资讯");
        initRefreLayout();
        getImgs();

    }

    private void getImgs() {
        ApiManager.getApi().getVolunteerImgs(RequestBodyUtils.getHomeVolunteerImg())
                .compose(RxSchedulers.<VolunteerImgsBean>io_main())
                .subscribe(new BaseSubscriber<VolunteerImgsBean>(mRefreshLayout) {
                    @Override
                    public void result(VolunteerImgsBean volunteerListBean) {
                        if (null != volunteerListBean && null != volunteerListBean.getDATA()) {
                            List<VolunteerImgsBean.ImageInfo> beans = volunteerListBean.getDATA();
                            initBanner(beans);
                            getData();
                        }
                    }
                });
    }

    private void getData() {
        mFlNodata.setVisibility(View.GONE);
        mRcvVoluteerList.setVisibility(View.VISIBLE);
        ApiManager.getApi().getVolunteerList(RequestBodyUtils.queryVolunteerList("", page + "", pagesize + ""))
                .compose(RxSchedulers.<VolunteerListBean>io_main())
                .subscribe(new BaseSubscriber<VolunteerListBean>(mRefreshLayout) {
                    @Override
                    public void result(VolunteerListBean volunteerListBean) {
                        if (null != volunteerListBean && null != volunteerListBean.getDATA() && null != volunteerListBean
                                .getDATA().getDATA()) {
                            ArrayList<VolunteerInfo> listbean = volunteerListBean.getDATA().getDATA();
                            if (page == 1 && listbean.size() > 0) {
                                initrcv(listbean);
                            } else if(page == 1 && listbean.size() == 0){
                                mFlNodata.setVisibility(View.VISIBLE);
                                mRcvVoluteerList.setVisibility(View.GONE);
                            }else if(page > 1){
                                List oldList = adapter.getData();
                                oldList.addAll(listbean);
                                adapter.setNewData(oldList);
                                adapter.notifyDataSetChanged();
                            }

                        } else {
                            if(page == 1){
                                mFlNodata.setVisibility(View.VISIBLE);
                                mRcvVoluteerList.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }


    private int page = 1;
    private int pagesize = 10;

    private void initRefreLayout() {
        mRefreshLayout.setOnLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getData();
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getImgs();
            }
        });
    }

    //初始化banner *************
    View headView;
    ConvenientBanner mBnThirdbanner;

    private void initBanner(List<VolunteerImgsBean.ImageInfo> list) {
        headView = inflateContentView(R.layout.layout_adv);
        mBnThirdbanner = (ConvenientBanner) headView.findViewById(R.id.bn_thirdbanner);

        mBnThirdbanner.setPages(
                new CBViewHolderCreator<VolunteerNewsActivity.BannerViewHolder>() {
                    @Override
                    public VolunteerNewsActivity.BannerViewHolder createHolder() {
                        return new VolunteerNewsActivity.BannerViewHolder();
                    }
                }, list)
                .setPageIndicator(new int[]{R.drawable.point_gray, R.drawable.point_white})
                .startTurning(2500)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        mBnThirdbanner.setCanLoop(list.size() > 1);
    }

    public class BannerViewHolder implements Holder<VolunteerImgsBean.ImageInfo> {
        @BindView(R.id.glide_image)
        ImageView mGlideImage;
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        @Override
        public View createView(Context context) {
            View view = inflateContentView(R.layout.adv_image);
            ButterKnife.bind(this, view);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, final VolunteerImgsBean.ImageInfo data) {
            ImageLoad.setUrl(context, Constants.URL_IP + data.getIMG(), mGlideImage);
            mGlideImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VolunteerNewsActivity.this, VolunteerInfoActivity.class);
                    intent.putExtra("id", data.getNEW_ID());
                    startActivity(intent);
                }
            });
            mTvTitle.setText(data.getTITLE());
        }
    }

    BaseQuickAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    //初始化recycleview*********
    private void initrcv(ArrayList<VolunteerInfo> list) {
        adapter = new BaseQuickAdapter<VolunteerInfo, BaseViewHolder>(R.layout
                .item_volunteer, list) {
            @Override
            protected void convert(BaseViewHolder helper, VolunteerInfo item) {
                helper.setText(R.id.tv_title, item.getTITLE());
                String dataStr = item.getCREATE_DATE();
                if(!(StringUtil.isEmpty(dataStr)) && dataStr.length() > 10){
                    helper.setText(R.id.tv_time, dataStr.substring(0,10));
                }else{
                    helper.setText(R.id.tv_time, item.getCREATE_DATE());
                }
                helper.setText(R.id.tv_content, item.getSOURCE());
            }
        };

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List dList = adapter.getData();
                VolunteerInfo bean = (VolunteerInfo) dList.get(position);
                Intent intent = new Intent(VolunteerNewsActivity.this, VolunteerInfoActivity.class);
                intent.putExtra("id", bean.getID());
                startActivity(intent);
            }
        });

        if(linearLayoutManager == null){
            linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRcvVoluteerList.setLayoutManager(linearLayoutManager);
            mRcvVoluteerList.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(0, 0, 0, 2);
                }
            });
        }
        if (headView != null)
            adapter.addHeaderView(headView);
        mRcvVoluteerList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
