package com.powerrich.office.oa.thirdapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.office.oa.fragment.mine.base.LazyLoadFragment;
import com.powerrich.office.oa.thirdapp.activity.ArticleInfoActivity;
import com.powerrich.office.oa.thirdapp.activity.ArticleListActivity;
import com.powerrich.office.oa.thirdapp.activity.ArticleTopicActivity;
import com.powerrich.office.oa.thirdapp.http.ThirdApiManager;
import com.powerrich.office.oa.thirdapp.http.ThirdBaseSubscriber;
import com.powerrich.office.oa.thirdapp.http.bean.GetArticleBean;
import com.powerrich.office.oa.thirdapp.http.bean.GetArticleListBean;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.ImageLoad;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.utils.StringUtil;
import com.yt.simpleframe.view.convenientbanner.CBViewHolderCreator;
import com.yt.simpleframe.view.convenientbanner.ConvenientBanner;
import com.yt.simpleframe.view.convenientbanner.Holder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/23
 * 版权：
 */

public class ArticleListFragment extends LazyLoadFragment {


    ArticleListActivity mActivity;
    //    @BindView(R.id.bn_thirdbanner)
    ConvenientBanner mBnThirdbanner;

    @BindView(R.id.rcv_article_list)
    RecyclerView mRcvArticleList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.fl_nodata)
    FrameLayout mFlNodata;

    @BindView(R.id.wv_fragment)
    WebView mWvFragment;

    public View view;
    Unbinder unbinder;

    GetArticleBean.ChildClassifysBean mData;

    public static ArticleListFragment getInstance(GetArticleBean.ChildClassifysBean bean) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle b = new Bundle();
        b.putSerializable("data", bean);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected View createContentView() {
        view = inflateContentView(R.layout.fragment_article_list);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void requestData() {
        if (view == null) {
            view = inflateContentView(R.layout.fragment_article_list);
            unbinder = ButterKnife.bind(this, view);
        }

        if (!StringUtil.isEmpty(mData.getUrl())) {
            mWvFragment.setVisibility(View.VISIBLE);
            mRcvArticleList.setVisibility(View.GONE);
            mFlNodata.setVisibility(View.GONE);

            WebSettings webSettings = mWvFragment.getSettings();
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);//设置webview推荐使用的窗口
            webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
            webSettings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
            webSettings.setDisplayZoomControls(false);//隐藏webview缩放按钮
            webSettings.setDomStorageEnabled(true);
            mWvFragment.setWebViewClient(new WebViewClient());
            mWvFragment.loadUrl(mData.getUrl());
            return;
        }
        initRefreLayout();
        page = 1;
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (ArticleListActivity) context;
        this.mData = (GetArticleBean.ChildClassifysBean) getArguments().getSerializable("data");
    }

    List<GetArticleListBean.TopArticleBean> topList;
    List<GetArticleListBean.ArticleListBean> articleList;

    //加载url

    private void getData() {
        mFlNodata.setVisibility(View.GONE);
        mWvFragment.setVisibility(View.GONE);
        mRcvArticleList.setVisibility(View.VISIBLE);
        //http://cmswebv3.aheading.com/api/Article/List?ClassifyIdx=31087&ClassifyType=17&IsFound=0&NewspaperIdx=8673&PageIndex
        // =1&PageSize=15&Type=0
        Observable<GetArticleListBean> api = ThirdApiManager.getApi().getArticleList(mData.getId(), mData.getType
                (), 0, mData.nid, page, pagesize, 0)
                .compose(RxSchedulers.<GetArticleListBean>io_main());
        if(page == 1){
            api = api.compose(this.<GetArticleListBean>loadingDialog());
        }
        api.subscribe(new ThirdBaseSubscriber<GetArticleListBean>() {
            @Override
            public void result(GetArticleListBean getArticleBean) {
                topList = getArticleBean.getTopArticle();
                articleList = getArticleBean.getArticleList();
                if (topList.size() == 0 && articleList.size() == 0 && page == 1) {
                    mFlNodata.setVisibility(View.VISIBLE);
                    mWvFragment.setVisibility(View.GONE);
                    mRcvArticleList.setVisibility(View.GONE);
                    return;
                }
                if (topList != null && topList.size() > 0) {
                    initBanner();
                }
                if (page == 1) {
                    initrcv();
                } else {
                    List oldList = adapter.getData();
                    oldList.addAll(articleList);
                    adapter.setNewData(oldList);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onComplete() {
                super.onComplete();
                mRefreshLayout.finishLoadmore();
                mRefreshLayout.finishRefresh();
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
                getData();
            }
        });
    }


    //初始化banner *************
    View headView;

    private void initBanner() {
        headView = inflateContentView(R.layout.layout_adv);
        mBnThirdbanner = (ConvenientBanner) headView.findViewById(R.id.bn_thirdbanner);

        mBnThirdbanner.setPages(
                new CBViewHolderCreator<BannerViewHolder>() {
                    @Override
                    public BannerViewHolder createHolder() {
                        return new BannerViewHolder();
                    }
                }, topList)
                .setPageIndicator(new int[]{R.drawable.point_gray, R.drawable.point_white})
                .startTurning(2500)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        mBnThirdbanner.setCanLoop(topList.size() > 1);
    }

    public class BannerViewHolder implements Holder<GetArticleListBean.TopArticleBean> {
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
        public void UpdateUI(Context context, int position, final GetArticleListBean.TopArticleBean data) {
            ImageLoad.setUrl(context, ThirdApiManager.ULR + data.getImgSrc(), mGlideImage);
            mGlideImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到详情页面
                    Intent intent = new Intent(mActivity, ArticleInfoActivity.class);
                    intent.putExtra("nid", mData.nid);
                    intent.putExtra("id", data.getId());
                    startActivity(intent);
                }
            });
            mTvTitle.setText(data.getTitle());
        }
    }

    BaseQuickAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    //初始化recycleview*********
    private void initrcv() {

        adapter = new BaseQuickAdapter<GetArticleListBean.ArticleListBean, BaseViewHolder>(R.layout
                .item_article, articleList) {
            @Override
            protected void convert(BaseViewHolder helper, GetArticleListBean.ArticleListBean item) {
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_time, DateUtils.UtcToStr(item.getLiveStartTime()));
                ImageView iv = helper.getView(R.id.iv_img);
                if (StringUtil.isEmpty(item.getImgSrc())) {
                    iv.setVisibility(View.GONE);
                } else {
                    iv.setVisibility(View.VISIBLE);
                    ImageLoad.setUrl(mActivity, ThirdApiManager.ULR + item.getImgSrc(), iv);
                }
                TextView tvArticle = helper.getView(R.id.tv_article);
                if (item.getType() == 7) {
                    tvArticle.setVisibility(View.VISIBLE);
                } else {
                    tvArticle.setVisibility(View.GONE);
                }
            }
        };

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List dList = adapter.getData();
                GetArticleListBean.ArticleListBean bean = (GetArticleListBean.ArticleListBean) dList.get(position);
                if (bean.getType() == 7) {
                    //跳转到专题
                    Intent intent = new Intent(mActivity, ArticleTopicActivity.class);
                    intent.putExtra("nid", mData.nid);
                    intent.putExtra("id", bean.getId());
                    startActivity(intent);
                } else {
                    //跳转到详情页面
                    Intent intent = new Intent(mActivity, ArticleInfoActivity.class);
                    intent.putExtra("nid", mData.nid);
                    intent.putExtra("id", bean.getId());
                    startActivity(intent);
                }
            }
        });

        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcvArticleList.setLayoutManager(linearLayoutManager);
        mRcvArticleList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 2);
            }
        });
        if (headView != null)
            adapter.addHeaderView(headView);
        mRcvArticleList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
