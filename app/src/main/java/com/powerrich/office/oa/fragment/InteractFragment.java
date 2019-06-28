package com.powerrich.office.oa.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.Interaction.ConsultationActivity;
import com.powerrich.office.oa.activity.Interaction.IwantActivity;
import com.powerrich.office.oa.activity.Interaction.NewsSearchActivity;
import com.powerrich.office.oa.activity.NewsDetailActivity;
import com.powerrich.office.oa.activity.things.VolunteerInfoActivity;
import com.powerrich.office.oa.activity.things.VolunteerNewsActivity;
import com.powerrich.office.oa.adapter.InteractAdapter;
import com.powerrich.office.oa.base.BaseFragment;
import com.powerrich.office.oa.bean.RollItem;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.common.GlideImageLoader;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.VerificationUtils;
import com.powerrich.office.oa.view.BannerLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.NewImageBeanListBean;
import com.yt.simpleframe.http.bean.VolunteerImgsBean;
import com.yt.simpleframe.http.bean.entity.NewImageInfo;
import com.yt.simpleframe.http.bean.entity.NewsInfo;
import com.yt.simpleframe.http.bean.xmlentity.NewsInfoBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyuanhao on 2018/6/6.
 * 互动
 */

public class InteractFragment extends BaseFragment implements View.OnClickListener, OnRefreshLoadmoreListener {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private InteractAdapter adapter;
    private BannerLayout bannerLayout;
    private List<NewsInfo> list = new ArrayList<>();
    private View headView;
    private EditText et_search;
    private LinearLayout ll_search;
    private LinearLayout interact1, interact2, interact3, interact4;


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_interact;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        ll_search = (LinearLayout) view.findViewById(R.id.ll_search);
        ll_search.setOnClickListener(this);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.layout_refresh);
        refreshLayout.setOnRefreshLoadmoreListener(this);
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_interact_head, null);
        interact1 = (LinearLayout) headView.findViewById(R.id.ll_interact1);
        interact2 = (LinearLayout) headView.findViewById(R.id.ll_interact2);
        interact3 = (LinearLayout) headView.findViewById(R.id.ll_interact3);
        interact4 = (LinearLayout) headView.findViewById(R.id.ll_interact4);
        interact1.setOnClickListener(this);
        interact2.setOnClickListener(this);
        interact3.setOnClickListener(this);
        interact4.setOnClickListener(this);
        setAdapter(view);
        getBannerList();
        initNet();
    }

    private void setBanner(final List<NewImageInfo> bannerList) {
        if (BeanUtils.isEmpty(bannerList)) {
            return;
        }
        List<RollItem> items = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            String img = bannerList.get(i).getIMG().replace("\\", "/");
            String url = Constants.URL_IP + img;
            items.add(new RollItem(bannerList.get(i).getTITLE(), -1, url));
        }
        bannerLayout = (BannerLayout) headView.findViewById(R.id.banner);
        bannerLayout.setImageLoader(new GlideImageLoader());
        bannerLayout.setViewUrls(items, true);
        //添加监听事件
        bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Intent intent = new Intent(getActivity(), VolunteerInfoActivity.class);
//                intent.putExtra("id", bannerList.get(position).getNEW_ID());
//                startActivity(intent);

                startActivity(new Intent(getActivity(), NewsDetailActivity.class).putExtra("news_id", bannerList.get(position).getNEW_ID()));
            }
        });
    }

    private void setAdapter(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 2);
            }
        });
        adapter = new InteractAdapter(R.layout.item_interact, list);
        adapter.addHeaderView(headView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), NewsDetailActivity.class).putExtra("news_id", list.get(position).getNEWS_ID()));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                startActivity(new Intent(getActivity(), NewsSearchActivity.class));
                break;
            case R.id.ll_interact1:
                getUserInfo("1");
                break;
            case R.id.ll_interact2:
                getUserInfo("2");
                break;
            case R.id.ll_interact3:
                getUserInfo("3");
                break;
            case R.id.ll_interact4:
                getUserInfo("4");
//                startActivity(new Intent(getActivity(), ConsultationActivity.class));
                break;
        }
    }

    public void getUserInfo(String type) {
//        if(!LoginUtils.getInstance().isLoginSuccess()){
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//        }else if(TextUtils.isEmpty(LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME())){
//            startActivity(new Intent(getActivity(), PerfectInfomationActivity.class));
//        }else {
        if (!VerificationUtils.all(getActivity())) {
            return;
        }
        switch (type) {
            case "1":
                startActivity(new Intent(getActivity(), IwantActivity.class).putExtra("iwant_type", Constants.CONSULTING_TYPE));
                break;
            case "2":
                startActivity(new Intent(getActivity(), IwantActivity.class).putExtra("iwant_type", Constants.COMPLAIN_TYPE));
                break;
            case "3":
                startActivity(new Intent(getActivity(), IwantActivity.class).putExtra("iwant_type", Constants.SUGGEST_TYPE));
                break;
            case "4":
                startActivity(new Intent(getActivity(), IwantActivity.class).putExtra("iwant_type", Constants.LETTER_TYPE));
                break;
        }
//        }
    }

    //网络请求
    private int page = 1;
    private int CURRENTROWS = 0;

    //获取首页轮播图
    public void getBannerList() {
//        ApiManager.getApi().getVolunteerImgs(RequestBodyUtils.getHomeVolunteerImg())
//                .compose(RxSchedulers.<VolunteerImgsBean>io_main())
//                .subscribe(new BaseSubscriber<VolunteerImgsBean>() {
//                    @Override
//                    public void result(VolunteerImgsBean volunteerListBean) {
//                        if (null != volunteerListBean && null != volunteerListBean.getDATA()) {
//                            List<VolunteerImgsBean.ImageInfo> beans = volunteerListBean.getDATA();
//                            setBanner(beans);
//                        }
//                    }
//                });
        ApiManager.getApi().getHomeNewsImg(RequestBodyUtils.getHomeNewsImg())
                .compose(RxSchedulers.<NewImageBeanListBean>io_main())
                .subscribe(new BaseSubscriber<NewImageBeanListBean>() {
                    @Override
                    public void result(NewImageBeanListBean baseBean) {
                        if (baseBean.getDATA() != null) {
                            setBanner(baseBean.getDATA());
                        }
                    }
                });
    }

    public void initNet() {
        ApiManager.getApi().getNewsList(RequestBodyUtils.getNews("", "", page))
                .compose(RxSchedulers.<NewsInfoBean>io_main())
                .subscribe(new BaseSubscriber<NewsInfoBean>() {
                    @Override
                    public void result(NewsInfoBean baseBean) {
                        if (page == 1) {
                            adapter.getData().clear();
                            CURRENTROWS = 0;
                        }
                        CURRENTROWS = CURRENTROWS + baseBean.getDATA().getCURRENTROWS();
                        if (CURRENTROWS == baseBean.getDATA().getROWS()) {
                            refreshLayout.setLoadmoreFinished(true);
                        } else {
                            refreshLayout.setLoadmoreFinished(false);
                        }
                        adapter.addData(baseBean.getDATA().getDATA());
                        System.out.println("data = " + baseBean.getDATA().toString());
                    }
                });
    }

    @Override
    public void onLoadmore(RefreshLayout refreshLayout) {
        refreshLayout.finishLoadmore();
        page++;
        initNet();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        page = 1;
        initNet();
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        tv_home_title = (TextView) view.findViewById(R.id.tv_top_title);
//        tv_home_title.setText("你好");
//    }

}
