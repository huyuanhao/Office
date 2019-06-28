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
import com.powerrich.office.oa.bean.MobileBusinessLeftBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：MarketAdmittanceListActivity
 * 描   述：阳光政务市场准入清单界面
 * 作   者：Wangzheng
 * 时   间：2018/1/23
 * 版   权：v1.0
 */
public class MarketAdmittanceListActivity extends BaseActivity implements View.OnClickListener {

    private CommonRvAdapter<MobileBusinessLeftBean> leftAdapter;
    private CommonRvAdapter<MobileBusinessLeftBean> rightAdapter;
    private RecyclerView recyclerView_left, recyclerView_right;
    private TextView tv_no_data;
    private int oldPosition = 0;
    private String leftTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_market_admittance_list;
    }

    private void initView() {
        initTitleBar(R.string.market_admittance_list, this, null);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        recyclerView_left = (RecyclerView) findViewById(R.id.recyclerView_left);
        recyclerView_right = (RecyclerView) findViewById(R.id.recyclerView_right);
        initData();
    }

    private void initData() {
        recyclerView_left.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_left.setHasFixedSize(true);
        recyclerView_right.setLayoutManager(new LinearLayoutManager(this));
        load();
    }

    /**
     * 获取市场准入清单-查询类别和领域请求
     */
    private void getApproveCategory() {
        ApiRequest request = OAInterface.getApproveCategory();
        invoke.invoke(request, callBack);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                ResultItem data = (ResultItem) item.get("DATA");
                List<String> category = (List<String>) data.get("CATEGORY");
                parseCategory(category);
                List<String> territory = (List<String>) data.get("TERRITORY");
                parseTerritory(territory);
            } else {
                DialogUtils.showToast(MarketAdmittanceListActivity.this, message);
            }
        }
    };

    /**
     * 获取市场准入清单-查询类别数据解析
     * @param category
     */
    private void parseCategory(List<String> category) {
        ArrayList<MobileBusinessLeftBean> rightTitles = new ArrayList<>();
        if (!BeanUtils.isEmpty(category)) {
            for (String s : category) {
                MobileBusinessLeftBean rightBean = new MobileBusinessLeftBean();
                rightBean.setTitle(s.replace("\n", ""));
                rightTitles.add(rightBean);
            }

        }
        if (rightAdapter == null) {
            rightAdapter = new CommonRvAdapter<MobileBusinessLeftBean>(rightTitles, R.layout.mobile_business_sort_left) {
                @Override
                public void convert(ViewHolderRv holder, MobileBusinessLeftBean item, int position) {
                    holder.setText(R.id.title, item.getTitle());
                }
            };
            rightAdapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(MarketAdmittanceListActivity.this, MarketAdmittanceListDetailActivity.class);
                    intent.putExtra("category", rightAdapter.getItem(position).getTitle());
                    intent.putExtra("territory", leftTitle);
                    startActivity(intent);
                }
            });
            recyclerView_right.setAdapter(rightAdapter);
        } else {
            rightAdapter.setData(rightTitles);
        }

    }
    /**
     * 获取市场准入清单-查询领域数据解析
     * @param territory
     */
    private void parseTerritory(List<String> territory) {
        ArrayList<MobileBusinessLeftBean> leftTitles = new ArrayList<>();
        if (!BeanUtils.isEmpty(territory)) {
            for (String s : territory) {
                MobileBusinessLeftBean leftBean = new MobileBusinessLeftBean();
                leftBean.setTitle(s);
                leftTitles.add(leftBean);
            }

        }
        if (leftTitles.size() != 0) {//初始化第一条分类
            leftTitles.get(0).setSelected(true);
            leftTitle = leftTitles.get(0).getTitle();
        }
        if (leftAdapter == null) {
            leftAdapter = new CommonRvAdapter<MobileBusinessLeftBean>(leftTitles, R.layout.mobile_business_sort_left) {
                @Override
                public void convert(ViewHolderRv holder, MobileBusinessLeftBean item, int position) {
                    View itemView = holder.getItemView(R.id.ll);
                    TextView textView = holder.getItemView(R.id.title);
                    if (item.isSelected()) {
                        itemView.setBackgroundResource(R.color.gray_system_bg);
                        textView.setTextColor(getResources().getColor(R.color.blue_main));
                    } else {
                        itemView.setBackgroundResource(R.color.white);
                        textView.setTextColor(getResources().getColor(R.color.gray));
                    }
                    holder.setText(R.id.title, item.getTitle());
                }
            };
            leftAdapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (oldPosition == position) return;
                    MobileBusinessLeftBean item = leftAdapter.getItem(position);
                    leftTitle = item.getTitle();
                    item.setSelected(true);
                    leftAdapter.getItem(oldPosition).setSelected(false);
                    leftAdapter.notifyDataSetChanged();
                    oldPosition = position;
                }
            });
            recyclerView_left.setAdapter(leftAdapter);
        } else {
            leftAdapter.setData(leftTitles);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                MarketAdmittanceListActivity.this.finish();
                break;
            default:
                break;
        }
    }

    private void load() {
        oldPosition = 0;
        getApproveCategory();
    }

}
