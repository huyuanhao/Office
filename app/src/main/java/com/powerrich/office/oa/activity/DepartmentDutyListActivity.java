package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonRvAdapter;
import com.powerrich.office.oa.adapter.ViewHolderRv;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.DepartmentInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：PublicityActivity
 * 描   述：阳光政务部门责任清单界面
 * 作   者：Wangzheng
 * 时   间：2017/12/4
 * 版   权：v1.0
 */
public class DepartmentDutyListActivity extends BaseActivity implements View.OnClickListener, OnRefreshLoadmoreListener {

    private static final int SITE_LIST_REQ = 0;
    private static final int DEPARTMENT_DUTY_LIST_REQ = 1;
    private CommonRvAdapter<DepartmentInfo> leftAdapter;
    private CommonRvAdapter<String> rightAdapter;
    private RecyclerView recyclerView_left, recyclerView_right;
    private int oldPosition = 0;
    private TextView tv_no_data;

    private int currentPage = 1;
    private SmartRefreshLayout refresh_layout;
    private String siteNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_department_duty_list;
    }

    private void initView() {
        initTitleBar(R.string.department_duty_list, this, null);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        recyclerView_left = (RecyclerView) findViewById(R.id.recyclerView_left);
        recyclerView_right = (RecyclerView) findViewById(R.id.recyclerView_right);
        refresh_layout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        initData();
    }

    private void initData() {
        recyclerView_left.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_right.setLayoutManager(new LinearLayoutManager(this));
        refresh_layout.setOnRefreshLoadmoreListener(this);
        load();
    }

    private void load() {
        oldPosition = 0;
        getSiteList();
    }
    /**
     * 获取部门列表请求
     */
    private void getSiteList() {
        ApiRequest request = OAInterface.getSiteList();
        invoke.invokeWidthDialog(request, callBack, SITE_LIST_REQ);
    }

    /**
     * 查询阳光政务部门责任清单请求
     *
     */
    private void getDepartmentDutyList() {
        ApiRequest request = OAInterface.getDepartmentDutyList(siteNo, String.valueOf(currentPage));
        invoke.invokeWidthDialog(request, callBack, DEPARTMENT_DUTY_LIST_REQ);
    }

    /**
     * 获取部门列表数据解析
     *
     * @param items
     */
    private void parseData(List<ResultItem> items) {
        List<DepartmentInfo> deptInfoList = new ArrayList<>();
        if (!BeanUtils.isEmpty(items)) {
            for (ResultItem item : items) {
                DepartmentInfo deptInfo = new DepartmentInfo();
                deptInfo.setSiteName(item.getString("SITE_NAME"));
                deptInfo.setShortName(item.getString("SHORT_NAME"));
                deptInfo.setSiteNo(item.getString("SITE_NO"));
                deptInfoList.add(deptInfo);
            }
        }

        if (deptInfoList.size() != 0) {//初始化第一条分类
            deptInfoList.get(0).setSelected(true);
            siteNo = deptInfoList.get(0).getSiteNo();
            getDepartmentDutyList();
        }
        if (leftAdapter == null) {
            leftAdapter = new CommonRvAdapter<DepartmentInfo>(deptInfoList, R.layout.mobile_business_sort_left) {
                @Override
                public void convert(ViewHolderRv holder, DepartmentInfo item, int position) {
                    View itemView = holder.getItemView(R.id.ll);
                    TextView textView = holder.getItemView(R.id.title);
                    if (item.isSelected()) {
                        itemView.setBackgroundResource(R.color.gray_system_bg);
                        textView.setTextColor(getResources().getColor(R.color.blue_main));
                    } else {
                        itemView.setBackgroundResource(R.color.white);
                        textView.setTextColor(getResources().getColor(R.color.gray));
                    }
                    holder.setText(R.id.title, item.getShortName());
                }
            };
            leftAdapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (oldPosition == position) return;
                    DepartmentInfo item = leftAdapter.getItem(position);
                    siteNo = item.getSiteNo();
                    item.setSelected(true);
                    leftAdapter.getItem(oldPosition).setSelected(false);
                    leftAdapter.notifyDataSetChanged();
                    oldPosition = position;
                    getDepartmentDutyList();
                }
            });
            recyclerView_left.setAdapter(leftAdapter);
        } else {
            leftAdapter.setData(deptInfoList);
        }
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == SITE_LIST_REQ) {
                    List<ResultItem> items = item.getItems("DATA");
                    parseData(items);
                } else if (what == DEPARTMENT_DUTY_LIST_REQ) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    parse(result);
                }
            } else {
                DialogUtils.showToast(DepartmentDutyListActivity.this, message);
            }
        }


    };
    private List<String> strs = new ArrayList<>();
    private List<String> ids = new ArrayList<>();

    private void parse(ResultItem result) {
        if (currentPage == 1 && strs.size() > 0) {
            strs.clear();
            ids.clear();
        }
        if (null != result) {
            ResultItem bmze = (ResultItem) result.get("BMZE");
            ResultItem ggfw = (ResultItem) result.get("GGFW");
            ResultItem jgzd = (ResultItem) result.get("JGZD");
            ResultItem zzbj = (ResultItem) result.get("ZZBJ");
            List<ResultItem> data1 = bmze.getItems("DATA");
            List<ResultItem> data2 = ggfw.getItems("DATA");
            List<ResultItem> data3 = jgzd.getItems("DATA");
            List<ResultItem> data4 = zzbj.getItems("DATA");
            if (null != data1) {
                for (ResultItem resultItem : data1) {
                    String title = resultItem.getString("MOSTLYOFFICE");
                    String id = resultItem.getString("BMZZID");
                    ids.add(id);
                    strs.add(title);
                }
            }
            if (null != data2) {
                for (ResultItem resultItem : data2) {
                    String title = resultItem.getString("TITLENAME");
                    String id = resultItem.getString("JGID");
                    ids.add(id);
                    strs.add(title);
                }
            }
            if (null != data3) {
                for (ResultItem resultItem : data3) {
                    String title = resultItem.getString("TITLENAME");
                    String id = resultItem.getString("JGID");
                    ids.add(id);
                    strs.add(title);
                }
            }
            if (null != data4) {
                for (ResultItem resultItem : data4) {
                    String title = resultItem.getString("MANAGEOFFICE");
                    String id = resultItem.getString("ZZBJID");
                    ids.add(id);
                    strs.add(title);
                }
            }
        }
        if (BeanUtils.isEmpty(strs)) {
            tv_no_data.setVisibility(View.VISIBLE);
            recyclerView_right.setVisibility(View.GONE);
        } else {
            recyclerView_right.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        }
        if (rightAdapter == null) {
            rightAdapter = new CommonRvAdapter<String>(strs, R.layout.government_affairs_item) {
                @Override
                public void convert(ViewHolderRv holder, String item, int position) {
                    holder.getItemView(R.id.tv_department).setVisibility(View.GONE);
                    holder.setText(R.id.text, item);
                }
            };
            rightAdapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(DepartmentDutyListActivity.this, DepartmentDutyListDetailActivity.class);
//                intent.putExtra("charge_project", mChargeListInfo.get(position).getChargeProject());
                    intent.putExtra("id", ids.get(position));
                    startActivity(intent);
                }
            });
            recyclerView_right.setAdapter(rightAdapter);
        } else {
            rightAdapter.setData(strs);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                DepartmentDutyListActivity.this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        currentPage ++;
        getDepartmentDutyList();
        refreshlayout.finishLoadmore();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        currentPage = 1;
        getDepartmentDutyList();
        refreshlayout.finishRefresh();
    }
}
