package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.NewsListAdapter;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.NewsInfo;
import com.powerrich.office.oa.bean.RollItem;
import com.powerrich.office.oa.common.PicassoImageLoader;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.BannerLayout;
import com.powerrich.office.oa.view.pull.PullToRefreshBase;
import com.powerrich.office.oa.view.pull.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 文 件 名：NewsActivity
 * 描   述：政务要闻标题列表界面
 * 作   者：Wangzheng
 * 时   间：2017/11/16
 * 版   权：v1.0
 */
public class NewsActivity extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView pullListView;
    private List<NewsInfo> newsInfoList = new ArrayList<>();
    private NewsListAdapter newsListAdapter;
    private ListView mListView;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_news;
    }

    private void initView() {
        initTitleBar(R.string.title_activity_news, this, null);
        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner);
        pullListView = (PullToRefreshListView) findViewById(R.id.news_pull_lv);
        mListView = BeanUtils.setProperty(pullListView);
        List<RollItem> list = new ArrayList<>(3);
        list.add(new RollItem("", R.drawable.n001));
        list.add(new RollItem("", R.drawable.n002));
        list.add(new RollItem("", R.drawable.n003));
        bannerLayout.setImageLoader(new PicassoImageLoader());
        bannerLayout.setViewUrls(list);

    }

    /**
     * 获取政务要闻标题列表请求
     * @param currentPage
     */
    private void loadData(int currentPage) {
        invoke.invokeWidthDialog(OAInterface.getNewsList("", "", String.valueOf(currentPage)), callBack);
    }

    private void initData() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String newsId = newsInfoList.get(position).getId();
                Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                intent.putExtra("news_id", newsId);
                startActivity(intent);
            }
        });
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            // 上拉刷新数据
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                loadData(currentPage);
            }

            // 下拉加载下一页的数据
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                loadData(currentPage);
            }
        });
        loadData(currentPage);
    }

    private void updateList(List<ResultItem> resultItems) {
        int[] res = {R.drawable.p001, R.drawable.p002, R.drawable.p000, R.drawable.p003, R.drawable.p004};
        Random random = new Random();
        if (!BeanUtils.isEmpty(resultItems)) {
            for (ResultItem resultItem : resultItems) {
                String title = resultItem.getString("TITLE");
                String time = resultItem.getString("CREATE_DATE");
                String id = resultItem.getString("NEWS_ID");
                NewsInfo newsInfo = new NewsInfo();
                newsInfo.setNewsTitle(title);
                newsInfo.setId(id);
                newsInfo.setDate(time);
                newsInfo.setIcon(res[random.nextInt(5)]);
                newsInfoList.add(newsInfo);
            }
        }
        if (null == newsListAdapter) {
            newsListAdapter = new NewsListAdapter(this);
            mListView.setAdapter(newsListAdapter);
        }
        newsListAdapter.setData(newsInfoList);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                ResultItem out = (ResultItem) item.get("DATA");
                List<ResultItem> resultItems = out.getItems("DATA");
                if (currentPage == 1 && !BeanUtils.isEmpty(newsInfoList)) {
                    newsInfoList.clear();
                }
                updateList(resultItems);
                //判断是否已经全部加载完成：如果完成了就关闭“下拉加载更多”功能，否则，继续打开“下拉加载更多”功能
                if (BeanUtils.isEmpty(resultItems) || resultItems.size() < Constants.COMMON_PAGE) {
                    // 已经全部加载完成，关闭UI组件的下拉加载更多功能
                    pullListView.onPullDownRefreshComplete();
                    pullListView.onPullUpRefreshComplete();
                    pullListView.setHasMoreData(false);
                } else {
                    // 还有更多数据，继续打开“下拉加载更多”功能
                    pullListView.onPullDownRefreshComplete();
                    pullListView.onPullUpRefreshComplete();
                    pullListView.setHasMoreData(true);
                }
            } else {
                DialogUtils.showToast(NewsActivity.this, message);
            }
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.system_back == id) {
            finish();
        }
    }

}
