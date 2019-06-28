package com.powerrich.office.oa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.CertificateImageActivity;
import com.powerrich.office.oa.adapter.CommonRvAdapter;
import com.powerrich.office.oa.adapter.ViewHolderRv;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.MaterialsDepotBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 文 件 名：MaterialsDepotActivity
 * 描   述：材料库
 * 作   者：Wangzheng
 * 时   间：2018-6-13 11:05:36
 * 版   权：v1.0
 */
public class MaterialsDepotActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_ALL_FILE_LIST_BY_USER_CODE = 0;
    private SmartRefreshLayout refresh_layout;
    private RecyclerView recycler_view;
    private TextView tv_no_data;
    private EditText et_query_content;
    private TextView tv_query;
    private CommonRvAdapter<MaterialsDepotBean> adapter;
    private int currentPage = 1;
    private List<MaterialsDepotBean> beanList = new ArrayList<>();
    private List<MaterialsDepotBean> materialsDepotBeanList = new ArrayList<>();
    private int currentRows = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BeanUtils.isEmpty(beanList)) {
            beanList.clear();
        }
        getAllFileListByUser("");
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_materials_depot;
    }

    private void initView() {
        initTitleBar(getString(R.string.history_attachment), getString(R.string.confirm), this, this);
        refresh_layout = findViewById(R.id.refresh_layout);
        recycler_view = findViewById(R.id.recycler_view);
        tv_no_data = findViewById(R.id.tv_no_data);
        et_query_content = findViewById(R.id.et_query_content);
        tv_query = findViewById(R.id.tv_search);
    }

    private void initData() {
        tv_query.setOnClickListener(this);
        recycler_view.setLayoutManager(new LinearLayoutManager(MaterialsDepotActivity.this));
        refresh_layout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refresh_layout.finishRefresh();
                currentPage = 1;
                et_query_content.setText("");
                getAllFileListByUser("");
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refresh_layout.finishLoadmore();
                currentPage++;
                getAllFileListByUser("");
            }

        });
        et_query_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_query_content.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(MaterialsDepotActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    loadData();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 查询当前用户的历史材料信息请求
     */
    private void getAllFileListByUser(String fileName) {
        ApiRequest request = OAInterface.getAllFileListByUser(fileName, String.valueOf(currentPage));
        invoke.invokeWidthDialog(request, callBack, GET_ALL_FILE_LIST_BY_USER_CODE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == GET_ALL_FILE_LIST_BY_USER_CODE) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    if (currentPage == 1 && !BeanUtils.isEmpty(beanList)) {
                        beanList.clear();
                        currentRows = 0;
                    }
                    currentRows += result.getInt("CURRENTROWS");
                    if (currentRows == result.getInt("ROWS")) {
                        refresh_layout.setLoadmoreFinished(true);
                    } else {
                        refresh_layout.setLoadmoreFinished(false);
                    }
                    List<ResultItem> items = result.getItems("DATA");
                    parseData(items);
                }
            } else {
                DialogUtils.showToast(MaterialsDepotActivity.this, message);
            }
        }
    };


    private void parseData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            tv_no_data.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);
            return;
        }
        tv_no_data.setVisibility(View.GONE);
        recycler_view.setVisibility(View.VISIBLE);
        for (ResultItem item : items) {
            MaterialsDepotBean bean = new MaterialsDepotBean();
            bean.setCOMP_FILE_NAME(item.getString("COMP_FILE_NAME"));
            bean.setCREATEPERSON(item.getString("CREATEPERSON"));
            bean.setCREATETIME(item.getString("CREATETIME"));
            bean.setFILESIZE(item.getString("FILESIZE"));
            bean.setHDFSFILENAME(item.getString("HDFSFILENAME"));
            bean.setID(item.getString("ID"));
            bean.setNAME(item.getString("NAME"));
            bean.setPATH(item.getString("PATH"));
            bean.setTASK_FILE_ID(item.getString("TASK_FILE_ID"));
            beanList.add(bean);
        }
        if (null == adapter) {
            adapter = new CommonRvAdapter<MaterialsDepotBean>(beanList, R.layout.materials_depot_item) {
                @Override
                public void convert(ViewHolderRv holder, final MaterialsDepotBean item, int position) {
                    holder.setText(R.id.tv_attachment_name, item.getNAME());
                    holder.setText(R.id.tv_link_name, item.getCOMP_FILE_NAME());
                    holder.setText(R.id.tv_upload_time, item.getCREATETIME());
                    CheckBox cb_selected = holder.getItemView(R.id.cb_selected);
                    cb_selected.setChecked(item.isSelected());
                    holder.getItemView(R.id.iv_check_attachment).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MaterialsDepotActivity.this, CertificateImageActivity.class);
                            intent.putExtra("CARD_FILE", item.getPATH());
                            intent.putExtra("HDFSFILENAME", item.getHDFSFILENAME());
                            intent.putExtra("FILENAME", item.getNAME());
                            startActivity(intent);
                        }
                    });
                }
            };
            recycler_view.setAdapter(adapter);
        } else {
            adapter.setData(beanList);
        }
        adapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                beanList.get(position).setSelected(!beanList.get(position).isSelected());
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                MaterialsDepotActivity.this.finish();
                break;
            case R.id.tv_top_right:
                if (!BeanUtils.isEmpty(beanList)) {
                    for (MaterialsDepotBean bean : beanList) {
                        if (bean.isSelected()) {
                            materialsDepotBeanList.add(bean);
                        }
                    }
                    if (BeanUtils.isEmpty(materialsDepotBeanList)) {
                        DialogUtils.showToast(MaterialsDepotActivity.this, "请选择材料附件！");
                        return;
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("materialsDepotBeanList", (Serializable) materialsDepotBeanList);
                setResult(RESULT_OK, intent);
                MaterialsDepotActivity.this.finish();
                break;
            case R.id.tv_search:
                // 通过输入的条件进行查询
                loadData();
                break;
        }
    }

    private void loadData() {
        String content = et_query_content.getText().toString().trim();
        if (BeanUtils.isEmptyStr(content)) {
            DialogUtils.showToast(MaterialsDepotActivity.this, "请输入搜索内容");
            return;
        }
        currentPage = 1;
        getAllFileListByUser(content);
    }
}
