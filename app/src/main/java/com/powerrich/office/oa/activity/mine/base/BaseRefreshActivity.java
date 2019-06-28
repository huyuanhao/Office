package com.powerrich.office.oa.activity.mine.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
 * Created by wfpb on 15/6/25.
 * <p/>
 * 带刷新的recycleview
 */
public abstract class BaseRefreshActivity<T> extends YTBaseActivity implements OnRefreshListener, OnLoadmoreListener {
    protected final int kPageSize = 10;
    public int kPage = 1;
    //网络列
    protected int spanCount = 2;
    //网格方向
    protected int orientation = 1;
    protected int spacingInPixels;
    public RecyclerView mRecyclerView;
    public RefreshLayout refreshLayout;
    public StatusView statusView;

    protected List<T> _dataSource = new ArrayList<>();
    private RefreshState _RefreshState;
    protected BaseRecyclerViewAdapter _adapter;
    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.fragment_base_refresh);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        statusView = (StatusView) view.findViewById(R.id.multiplestatusview);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(getAdapter());
        initView();
//        loadData();
    }



    private int preScrollState;

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
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        } else {
            if (!showDivider()) {
                mRecyclerView.addItemDecoration(new GridSpaceItemDecoration(getSpanCount(), spacingInPixels, false));
            }
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, getSpanCount(), orientation, false));

        }
        //设置空页面
        if (_adapter == null) {
            _adapter = new ListViewAdapter(mContext, _dataSource);
            mRecyclerView.setAdapter(_adapter);
        }


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

    private void showEmpty(List<T> dataSource) {
        if(dataSource==null||dataSource.size()==0){
            statusView.showEmpty(emptyView());
        }
    }
    private void showDataView(List<T> dataSource) {
        if(dataSource != null && dataSource.size() > 0){
            statusView.showContent();
        }
    }


    protected int pageSize() {
        return kPageSize;
    }

    protected boolean isPaged() {
        return true;
    }

    //是否是网格
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

    protected int getSpacingInPixels() {
        return getResources().getDimensionPixelSize(R.dimen.dimen_div_5);
    }

    private void updateData(final boolean showLoading) {
//        if (showLoading) {
//            this.showLoading();
//        }
        loadListData();
    }

    private void reloadData(List<T> dataSource) {
        if (dataSource != null) {
            showDataView(dataSource);
            if (_RefreshState == RefreshState.LS_INIT) {
                _adapter.setDatas(dataSource);
                refreshLayout.setEnableLoadmore(dataSource.size() >= kPageSize);
                showEmpty(dataSource);
            } else if (_RefreshState == RefreshState.LS_Refresh) {
                showEmpty(dataSource);
                _dataSource = dataSource;
                _adapter.setDatas(_dataSource);
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


    protected void loadData() {
        _RefreshState = RefreshState.LS_INIT;
        kPage = 1;
        this.updateData(true);
    }

    // region abstract methods
    public abstract void loadListData();

    protected boolean showDivider() {
        return false;
    }

    //自己复写这个方法
    public void setListData(List<T> arrayList) {
        reloadData(arrayList);
//        hideLoading();
    }


    // endregion

    protected View emptyView() {
        View view = getEmptyView();
        if (view == null) {
            view = inflateContentView(R.layout.no_data_view);

        }
        return view;
    }

    protected int getItemCount() {
        return pageSize();
    }


    protected class ListViewAdapter extends BaseRecyclerViewAdapter {

        public ListViewAdapter(Context context, List datas) {
            super(context, datas);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(viewType);
        }

        @Override
        public int getItemViewType(int position) {
            return getListItemViewType(position);
        }

        @Override
        public void setUpData(RecyclerView.ViewHolder holder, int position, int viewType, Object data) {
            BindViewHolder(holder, position, viewType, data);
        }

    }


    protected View getEmptyView() {
        return null;
    }

    protected BaseRecyclerViewAdapter getAdapter() {
        return null;
    }

    protected int getCount() {
        return _dataSource.size();
    }

    protected T getItem(int index) {
        return _dataSource.get(index);
    }


    public int getListItemViewType(int position) {
        return 0;
    }

    public abstract RecyclerView.ViewHolder getViewHolder(int viewType);

    public abstract void BindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType, Object data);



    public void onRefresh() {
        onRefresh(refreshLayout);
    }

//    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        // 下拉刷新
        _RefreshState = RefreshState.LS_Refresh;
        kPage = 1;
        this.updateData(false);
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

}
