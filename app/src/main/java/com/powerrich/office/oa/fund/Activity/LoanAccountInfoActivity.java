package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.FundInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.fund.Adapter.FundItemAdapter;
import com.powerrich.office.oa.fund.bean.BaseFund;
import com.powerrich.office.oa.fund.bean.FundItemBean;
import com.powerrich.office.oa.fund.bean.LoanAccountInfo;
import com.powerrich.office.oa.fund.utils.FundUtils;
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

import static com.powerrich.office.oa.fund.bean.FundBean.jkhtbh;

/**
 * @author PC
 * @date 2019/04/12 11:26
 */
public class LoanAccountInfoActivity extends BaseActivity {
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
        mBarTitle.setText("贷款账户信息");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new LineItemDecoration(DensityUtil.dp2px(1)));
        list = new ArrayList<>();
        mAdapter = new FundItemAdapter(R.layout.item_rv_fund_item,list);
        mRecyclerView.setAdapter(mAdapter);


        if (null != invoke) {
            //公积金信息查询
            invoke.invokeWidthDialog(OAInterface.dkzhxxcx("C36060", jkhtbh), callBack, 100);
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

    private void setData(BaseFund<LoanAccountInfo> info){
        if(info!=null){
                list.add(new FundItemBean("贷款账号",info.getDATA().get(0).getJkrgjjzh()));
                list.add(new FundItemBean("借款合同编号",info.getDATA().get(0).getJkhtbh()));
                list.add(new FundItemBean("贷款金额","¥" +info.getDATA().get(0).getDkffe()));
                list.add(new FundItemBean("放贷时间",info.getDATA().get(0).getDkffrq()));
                list.add(new FundItemBean("约定还款日","每月" + info.getDATA().get(0).getYdhkr() + "号"));
                list.add(new FundItemBean("贷款期数",info.getDATA().get(0).getDkqs()));
                list.add(new FundItemBean("应结清日期",info.getDATA().get(0).getYjqrq()));
                list.add(new FundItemBean("还款方式",info.getDATA().get(0).getDkhkfs()));
                list.add(new FundItemBean("贷款状态",info.getDATA().get(0).getDkzt()));
                list.add(new FundItemBean("银行还款账号",info.getDATA().get(0).getHkzh()));
                list.add(new FundItemBean("贷款余额","¥" +info.getDATA().get(0).getDkye()));
                list.add(new FundItemBean("已还本金","¥" +info.getDATA().get(0).getHsbjze()));
            list.add(new FundItemBean("已还利息","¥" +info.getDATA().get(0).getHslxze()));
            list.add(new FundItemBean("已还期数",info.getDATA().get(0).getShqs()));
            list.add(new FundItemBean("当前逾期次数",info.getDATA().get(0).getDqyqcs()));
            list.add(new FundItemBean("当前逾期本金",info.getDATA().get(0).getDqyqbj()));
            list.add(new FundItemBean("当前逾期利息",info.getDATA().get(0).getDqyqlx()));
            list.add(new FundItemBean("罚息总额",info.getDATA().get(0).getFxze()));
                mAdapter.notifyDataSetChanged();
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
                    FundUtils.getInstance().saveFundInfo(LoanAccountInfoActivity.this,s);
                    BaseFund<LoanAccountInfo> fundInfo  = new Gson().fromJson(s, new TypeToken<BaseFund<LoanAccountInfo>>() {
                    }.getType());

                    if(fundInfo.getDATA()!=null){
                        if(fundInfo.getDATA().size()>0){
                            setData(fundInfo);
                        }
                    }
                }
            } else {
                DialogUtils.showToast(LoanAccountInfoActivity.this, message);
            }
        }
    };
}
