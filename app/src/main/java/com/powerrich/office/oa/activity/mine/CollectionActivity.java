package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.FamilyRegisterDetailActivity;
import com.powerrich.office.oa.activity.WorkGuideNewActivity;
import com.powerrich.office.oa.activity.mine.base.BaseRefreshActivity;
import com.powerrich.office.oa.enums.SearchFragmentType;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.MyDialog;
import com.powerrich.office.oa.viewholder.ProcessViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.CollectionListBean;
import com.yt.simpleframe.http.bean.entity.CollectionInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.KeyboardUtils;
import com.yt.simpleframe.view.StatusView;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/6/006
 * 版权：
 */
public class CollectionActivity extends BaseRefreshActivity<CollectionInfo> {

    Unbinder unbinder;
    @BindView(R.id.et_search_txt)
    EditText mEtSearchTxt;
    @BindView(R.id.bt_search)
    Button mBtSearch;
    @BindView(R.id.tv_mine)
    TextView mTvMine;
    @BindView(R.id.cb_select_all)
    CheckBox mCbSelectAll;
    @BindView(R.id.tv_select)
    TextView mTvSelect;
    @BindView(R.id.tv_select_count)
    TextView mTvSelectCount;
    @BindView(R.id.bt_del)
    Button mBtDel;
    @BindView(R.id.lt_frame)
    LinearLayout mLtFrame;
    boolean longboo = false;


    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_collection);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        statusView = (StatusView) view.findViewById(R.id.multiplestatusview);
        ButterKnife.bind(this, view);
        mLtFrame.setVisibility(View.GONE);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的收藏");
        mTvMine.setText("收藏事项");
        showBack();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void loadListData() {
        getDate("");
    }

    public void getDate(final String searchName) {
        ApiManager.getApi().collectItemList(RequestBodyUtils.collectItemList(
                LoginUtils.getInstance().getUserInfo().getAuthtoken(), searchName, kPage, kPageSize))
                .compose(RxSchedulers.<CollectionListBean>io_main())
                .subscribe(new BaseSubscriber<CollectionListBean>(refreshLayout) {
                    @Override
                    public void result(CollectionListBean listBean) {
                        ArrayList<CollectionInfo> data = listBean.getDATA().getDATA();
                        if (!TextUtils.isEmpty(searchName)) {
                            _adapter.clearDatas();
                        }
                        if(listBean != null && listBean.getDATA() != null){
                            setListData(listBean.getDATA().getDATA());
                        }else{
                            setListData(null);
                        }

                    }
                });
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(int viewType) {
        View view = inflateContentView(R.layout.item_process_fragment);
        ProcessViewHolder holder = new ProcessViewHolder(unbinder, view, SearchFragmentType.收藏);
        holder.mCbProcess.setVisibility(View.GONE);
        return holder;
    }


    @Override
    public void BindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, int viewType, Object data) {
        final ProcessViewHolder holder = (ProcessViewHolder) viewHolder;
        holder.bindView(data);
        if (longboo) {
            holder.mCbProcess.setVisibility(View.VISIBLE);
        } else {
            holder.mCbProcess.setVisibility(View.GONE);
        }
        holder.convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mLtFrame.setVisibility(View.VISIBLE);
                longboo = true;
                _adapter.notifyDataSetChanged();
                return false;
            }
        });
        holder.mCbProcess.setChecked(((CollectionInfo) data).checkBoo);
        holder.mCbProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = holder.mCbProcess.isChecked();
                ArrayList<CollectionInfo> list = (ArrayList<CollectionInfo>) _adapter.getData();
                CollectionInfo info = list.get(position);
                info.checkBoo = b;
                list.set(position, info);
                calculatCheckBox();
            }
        });


        final CollectionInfo info = (CollectionInfo) data;
        holder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!longboo) {
                    Intent intent = new Intent(CollectionActivity.this, WorkGuideNewActivity.class);
                    intent.putExtra("item_id", info.getITEMID());
                    intent.putExtra("item_name", info.getITEMNAME());
                    startActivity(intent);
                }else{

                    ArrayList<CollectionInfo> list = (ArrayList<CollectionInfo>) _adapter.getData();
                    CollectionInfo info = list.get(position);
                    info.checkBoo = !info.checkBoo;
                    list.set(position, info);
                    calculatCheckBox();
                }

            }
        });
//
//        holder.mTvProcessGui.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent  = new Intent(CollectionActivity.this,DeclareNoticeActivity.class);
//                intent.putExtra("item_id",info.getITEMID());
//                intent.putExtra("position","0");
//                startActivity(intent);
//            }
//        });

    }

    public void calculatCheckBox() {
        int selectCount = 0;
        ArrayList<CollectionInfo> list = (ArrayList<CollectionInfo>) _adapter.getData();
        for (CollectionInfo info :
                list) {
            if (info.checkBoo)
                selectCount++;
        }
        mTvSelectCount.setText(Html.fromHtml("共选择<font color='#ea7805'><big>" + selectCount + "</big></font>条数据"));
        _adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    @OnClick({R.id.bt_search, R.id.bt_del, R.id.cb_select_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                String txt = mEtSearchTxt.getText().toString();
                if (!TextUtils.isEmpty(txt)) {
                    getDate(txt);
                } else {
//                    refreshLayout.autoRefresh(0);
                    ToastUtils.showMessage(this, "请输入搜索内容");
                }
                KeyboardUtils.hideSoftInput(this);
                break;
            case R.id.bt_del:
                MyDialog.showDialog(this, "提示", "确定要删除所选数据吗？", new MyDialog.InterfaceClick() {
                    @Override
                    public void click() {
                        dele();
                        longboo = false;
                        mLtFrame.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.cb_select_all:
                mTvSelect.setText(mCbSelectAll.isChecked() ? "全取消" : "全选");
                selectCheckBox(mCbSelectAll.isChecked());
                break;
        }
    }

    private void selectCheckBox(boolean b) {
        ArrayList<CollectionInfo> list = (ArrayList<CollectionInfo>) _adapter.getData();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).checkBoo = b;
        }
        calculatCheckBox();
    }

    //批量删除收藏
    public void dele() {
        ArrayList<CollectionInfo> list = (ArrayList<CollectionInfo>) _adapter.getData();
        JSONArray ja = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            CollectionInfo info = list.get(i);
            if (info.checkBoo) {
                ja.put(info.getSCID());
            }
        }
        ApiManager.getApi().exeNormal(RequestBodyUtils.deleteCollectes(LoginUtils.getInstance().getUserInfo()
                .getAuthtoken(), ja.toString()))
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean baseBean) {
                        refreshLayout.autoRefresh(0);
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mLtFrame.getVisibility() == View.VISIBLE) {
                mLtFrame.setVisibility(View.GONE);
                longboo = false;
                selectCheckBox(false);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
