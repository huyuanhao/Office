package com.powerrich.office.oa.fragment.mine.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yt.simpleframe.R;
import com.yt.simpleframe.enums.RefreshState;
import com.yt.simpleframe.view.StatusView;
import com.yt.simpleframe.view.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.yt.simpleframe.view.recyclerview.decoration.GridSpaceItemDecoration;
import com.yt.simpleframe.view.recyclerview.decoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/5/22 0022
 * 版权：
 */

public abstract class BaseRefreshFragment<T> extends YtBaseFragment implements OnRefreshListener, OnLoadmoreListener {

    public final int kPageSize = 10;
    public int kPage = 1;


    //网格方向
    protected int orientation = 1;
    protected int spacingInPixels;
    //网络列
    protected int spanCount = 2;

    public RecyclerView mRecyclerView;
    public StatusView statusView;
    public RefreshLayout refreshLayout;
    protected List<T> _dataSource = new ArrayList<>();
    private RefreshState _RefreshState;
    protected BaseRecyclerViewAdapter _adapter;



    @Override
    protected View createContentView() {
        View view = inflateContentView(R.layout.fragment_base_refresh);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        statusView = (StatusView) view.findViewById(R.id.multiplestatusview);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void onPause() {
        super.onPause();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
    }

    public void finishLoad(){
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefresh();
    }



    @SuppressLint("ResourceAsColor")
    private void initView() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);
        spacingInPixels = getSpacingInPixels();
        orientation = getOrientation();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.getItemAnimator().setChangeDuration(0);

        if (getSortType()) {
            if (!showDivider()) {
                mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        } else {
            if (!showDivider()) {
                mRecyclerView.addItemDecoration(new GridSpaceItemDecoration(spanCount, spacingInPixels, false));
            }

            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, spanCount, orientation, false));

        }
        _adapter = new ListViewAdapter(mContext, _dataSource);
        mRecyclerView.setAdapter(_adapter);
        //设置空页面
        //   mRecyclerView.setEmptyView(emptyView());
    }


    protected class ListViewAdapter extends BaseRecyclerViewAdapter {

        public ListViewAdapter(Context context, List datas) {
            super(context, datas);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return createVH(viewType);
        }

        @Override
        public int getItemViewType(int position) {
            return ItemViewType(position);
        }

        @Override
        public void setUpData(RecyclerView.ViewHolder holder, int position, int viewType, Object data) {
            bindVH(holder, viewType, position, data);
        }

    }
    public abstract RecyclerView.ViewHolder createVH(int viewType);
    public abstract void bindVH(RecyclerView.ViewHolder viewHolder, int viewType, int position, Object data);
    //类型
    public int ItemViewType(int position) {
        return 0;
    }



    protected boolean showDivider() {
        return false;
    }
    protected boolean getSortType() {
        return true;
    }

    //方向
    protected int getSpanCount() {
        return spanCount;
    }

    //方向
    protected int getOrientation() {
        return orientation;
    }

    /**中间间隔大小*/
    protected int getSpacingInPixels() {
        return getResources().getDimensionPixelSize(R.dimen.dimen_div_5);
    }



    public void requestData() {
        loadData();
    }
    protected void loadData() {
        _RefreshState = RefreshState.LS_INIT;
        kPage = 1;
        this.updateData(true);
    }

    private void updateData(final boolean showLoading) {
//        if (showLoading) {
//            this.showLoading();
//        }
        loadListData();
    }


    public void onRefresh(RefreshLayout refreshlayout) {
        // 下拉刷新
        _RefreshState = RefreshState.LS_Refresh;
        kPage = 1;
        this.updateData(false);
    }

    public void onRefresh() {
        onRefresh(refreshLayout);
    }

    public void setListAdapter(BaseRecyclerViewAdapter _adapter) {
        this._adapter = _adapter;
    }

    //
    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        //        // 上拉加载更多
        _RefreshState = RefreshState.LS_LoadMore;
        kPage += 1;
        this.updateData(false);
    }


    /**设置数据源*/
    public void setListData(ArrayList<T> arrayList) {
        reloadData(arrayList);
        if(getActivity() != null && !getActivity().isFinishing()){
//            hideLoading();
        }
    }

    public List<T> getData(){
        return _adapter.getData();
    }

    /**判断下拉上拉加载数据*/
    private void reloadData(List<T> dataSource) {

        if (dataSource != null) {
            if (_RefreshState == RefreshState.LS_INIT) {
                _adapter.setDatas(dataSource);
                refreshLayout.setEnableLoadmore(dataSource.size() >= kPageSize);
                showEmpty(dataSource);
            } else if (_RefreshState == RefreshState.LS_Refresh) {
                _dataSource = dataSource;
                _adapter.setDatas(_dataSource);
                showEmpty(dataSource);
                //下拉刷新
                refreshLayout.setEnableLoadmore(dataSource.size() >= kPageSize);
                refreshLayout.finishRefresh();
            } else {
                //上拉加载更多
                refreshLayout.finishLoadmore();
                if (dataSource.size() == 0) {
                    refreshLayout.setEnableLoadmore(false);
                }
                _adapter.addItems(dataSource);
            }
            _dataSource = _adapter.getData();
        }
        if(_adapter != null && _adapter.getData() != null && _adapter.getData().size() > 0){
            showDataView(_adapter.getData());
        }else{
            _adapter.setDatas(new ArrayList());
            showEmpty(dataSource);
        }

    }
    private void showEmpty(List<T> dataSource) {
        if (dataSource == null || dataSource.size() == 0) {
            statusView.showEmpty(inflateContentView(R.layout.no_data_view));
        } else {
            statusView.showContent();
        }
    }
    private void showDataView(List<T> dataSource) {
        if(dataSource != null && dataSource.size() > 0){
            statusView.showContent();
        }
    }


    /** 子类实现的方法 */
    public abstract void loadListData();
}
