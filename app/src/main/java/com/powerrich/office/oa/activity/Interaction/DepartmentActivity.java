package com.powerrich.office.oa.activity.Interaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.IWantBean;
import com.powerrich.office.oa.bean.WorkInfo;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.MySpinner;
import com.yt.simpleframe.view.recyclerview.decoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 部门选择
 */

public class DepartmentActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private static int GETSITELIST = 000;
    List<WorkInfo> departments;
    BaseQuickAdapter adapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.title_recycle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        getServiceOrItems();
    }

    private void initView() {
        barTitle.setText("请选择部门");
        departments = new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new SpaceItemDecoration(1));
        recyclerview.setAdapter(adapter = new BaseQuickAdapter<WorkInfo,BaseViewHolder>(R.layout.item_text_mechanism,departments) {
            @Override
            protected void convert(BaseViewHolder helper, WorkInfo item) {
                helper.setText(R.id.tv_spinner,item.getSite_name());
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("dep_name",departments.get(position).getSite_name());
                intent.putExtra("dep_id",departments.get(position).getSite_no());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    /**
     * 获取部门列表
     */
    private void getServiceOrItems() {
        if (null != invoke)
            invoke.invokeWidthDialog(OAInterface.getSiteAndItemList(""), callBack, GETSITELIST);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {
        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (GETSITELIST == what) {
                    List<ResultItem> data = item.getItems("DATA");
                    showDepartmentList(data);
                }
            } else {
                DialogUtils.showToast(DepartmentActivity.this, message);
            }
        }
    };

    /**
     * 展示部门列表
     *
     * @param items
     */
    protected void showDepartmentList(List<ResultItem> items) {
        List<String> departNames = new ArrayList<String>();
        for (ResultItem resultItem : items) {
            String siteName = resultItem.getString("SITE_NAME");
            String siteNo = resultItem.getString("SITE_NO");
            WorkInfo workInfo = new WorkInfo();
            workInfo.setSite_name(siteName);
            workInfo.setSite_no(siteNo);
            departNames.add(siteName);
            departments.add(workInfo);
        }
        adapter.notifyLoadMoreToLoading();
    }

    @OnClick({R.id.bar_back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.bar_back:
                finish();
                break;
        }
    }
}
