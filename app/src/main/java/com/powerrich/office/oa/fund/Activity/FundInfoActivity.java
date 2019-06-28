package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.FundInfo;
import com.powerrich.office.oa.bean.FundLoanInfo;
import com.powerrich.office.oa.bean.RepaymentPlanInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.fund.Adapter.FundItemAdapter;
import com.powerrich.office.oa.fund.bean.FundItemBean;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.GsonUtil;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.yt.simpleframe.view.recyclerview.decoration.LineItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.powerrich.office.oa.fund.bean.FundBean.grzh;

/**
 * @author PC
 * @date 2019/04/12 11:26
 */
public class FundInfoActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView mBarBack;
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    FundItemAdapter mAdapter;
    List<FundItemBean> list;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fund_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mBarTitle.setText("公积金账户信息");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new LineItemDecoration(DensityUtil.dp2px(1)));
        list = new ArrayList<>();
        mAdapter = new FundItemAdapter(R.layout.item_rv_fund_item,list);
        mRecyclerView.setAdapter(mAdapter);


        if (null != invoke) {
            //公积金信息查询
            invoke.invokeWidthDialog(OAInterface.getFundInfo("C36060", grzh), callBack, 100);
        }
    }

    @OnClick({R.id.bar_back})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bar_back:
                finish();
                break;
        }
    }

    private void setData(FundInfo info){
        if(info.getDATA()!=null){
            if(info.getDATA().size()>0){
                FundInfo.DATABean bean = info.getDATA().get(0);
                list.add(new FundItemBean("姓名",bean.getXingming()));
                list.add(new FundItemBean("数据截止日期",bean.getJzny()));
                list.add(new FundItemBean("公积金账号",bean.getGrzh()));
                list.add(new FundItemBean("缴存单位",bean.getGrckzhkhyhmc()));
                list.add(new FundItemBean("单位账号",bean.getDwzh()));
                list.add(new FundItemBean("账户状态",bean.getGrzhzt()));
                list.add(new FundItemBean("缴存基数",bean.getGrjcjs()));
                list.add(new FundItemBean("月缴额",bean.getZjjce()));
                list.add(new FundItemBean("余额",bean.getGrzhye()));
                list.add(new FundItemBean("最近缴存日期",bean.getZjjcrq()));
                list.add(new FundItemBean("最近提取额",bean.getZjtqe()));
                list.add(new FundItemBean("最近提取日期",bean.getZjtqrq()));

                mAdapter.notifyDataSetChanged();
            }
        }
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {
        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String message = item.getString("message");
            String code = item.getString("code");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (100 == what) {
                    String s = item.getDataStr();
                    FundInfo fundInfo = GsonUtil.GsonToBean(s,FundInfo.class);
                    setData(fundInfo);
                }
            } else {
                DialogUtils.showToast(FundInfoActivity.this, message);
            }
        }
    };
}
