package com.powerrich.office.oa.activity.Interaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.NewsDetailActivity;
import com.powerrich.office.oa.activity.WorkActivity;
import com.powerrich.office.oa.adapter.InteractAdapter;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.entity.NewsInfo;
import com.yt.simpleframe.http.bean.xmlentity.NewsInfoBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.KeyboardUtils;
import com.yt.simpleframe.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyuanhao on 2018/6/12.
 * 新闻搜索
 */

public class NewsSearchActivity extends BaseActivity implements OnRefreshLoadmoreListener {
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private List<NewsInfo> list = new ArrayList<>();
    private InteractAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_news_search;
    }

    private void initView() {
        barBack.setVisibility(View.VISIBLE);
        barTitle.setText("新闻搜索");
        refreshLayout.setOnRefreshLoadmoreListener(this);

        setAdapter();
    }

    @OnClick({R.id.bar_back, R.id.tv_search})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.tv_search:
                KeyboardUtils.hideSoftInput(this);
                page = 1;
                initNet();
                break;
        }
    }

    private void setAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(NewsSearchActivity.this));
        recyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 2);
            }
        });
        adapter = new InteractAdapter(R.layout.item_interact, list);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(NewsSearchActivity.this, NewsDetailActivity.class).putExtra("news_id", list.get(position).getNEWS_ID()));
            }
        });
        recyclerview.setAdapter(adapter);

        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardUtils.hideSoftInput(NewsSearchActivity.this);
                    page = 1;
                    initNet();
                    return true;
                }
                return false;
            }
        });
    }

    //网络请求
    private int page = 1;
    private int CURRENTROWS = 0;

    public void initNet() {
        if (TextUtils.isEmpty(searchEt.getText().toString().trim())) {
            T.showShort(this,"请输入搜索内容!");
            return;
        }
        ApiManager.getApi().getNewsList(RequestBodyUtils.getNews("", searchEt.getText().toString().trim(), page))
                .compose(RxSchedulers.<NewsInfoBean>io_main())
                .subscribe(new BaseSubscriber<NewsInfoBean>() {
                    @Override
                    public void result(NewsInfoBean baseBean) {
                        if (baseBean.getDATA() != null) {
                            if (page == 1) {
                                adapter.getData().clear();
                                CURRENTROWS = 0;
                                if (baseBean.getDATA().getDATA() == null || baseBean.getDATA().getDATA().size() == 0) {
                                    tvEmpty.setVisibility(View.VISIBLE);
                                } else {
                                    tvEmpty.setVisibility(View.GONE);
                                }
                            }
                            if(baseBean.getDATA().getROWS() > 0) {
                                tvResult.setVisibility(View.VISIBLE);
                                tvResult.setText("共搜索到" + baseBean.getDATA().getROWS() + "条关于“" + searchEt.getText().toString().trim() + "”的新闻");
                            }else {
                                tvResult.setVisibility(View.GONE);
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
}
