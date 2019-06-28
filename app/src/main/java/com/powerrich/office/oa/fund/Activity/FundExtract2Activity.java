package com.powerrich.office.oa.fund.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.fund.Adapter.BankSelectAdapter;
import com.powerrich.office.oa.fund.bean.FundBean;
import com.powerrich.office.oa.fund.bean.FundLhhBean;
import com.powerrich.office.oa.fund.bean.FundTqyhxx;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.ToastUtils;
import com.yt.simpleframe.view.recyclerview.decoration.LineItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author PC
 * @date 2019/04/16 11:25
 */
public class FundExtract2Activity extends BaseActivity {
    @BindView(R.id.tv_bank_number)
    TextView mTvBankNumber;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_query)
    TextView mTvQuery;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.bar_back)
    ImageView mBarBack;
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.iv_right)
    ImageView mIvRight;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fund_extract2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mBarTitle.setText("退休提取");
        initPopwindow();
    }

    String number,skyhmc;
    @OnClick({R.id.bar_back,R.id.tv_add,R.id.tv_back,R.id.tv_query})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_query:
                skyhmc = mEtCode.getText().toString();
                if(TextUtils.isEmpty(skyhmc)){
                    ToastUtils.showMessage(FundExtract2Activity.this,"请输入银行名称");
                    return;
                }

                if (null != invoke) {
                    //获取联行号
                    invoke.invokeWidthDialog(OAInterface.jcrkhdj(skyhmc), callBack, 99);
                }
                break;
            case R.id.tv_add:
                if (null != invoke) {
                     number = mTvBankNumber.getText().toString();
                     skyhmc = mEtCode.getText().toString();

                    if(TextUtils.isEmpty(number)){
                        ToastUtils.showMessage(FundExtract2Activity.this,"请输入银行卡号");
                        return;
                    }
                    if(TextUtils.isEmpty(skyhmc)){
                        ToastUtils.showMessage(FundExtract2Activity.this,"请输入银行名称");
                        return;
                    }
//                    String lhh = "105";
//                    //添加银行卡
                    invoke.invokeWidthDialog(OAInterface.jcrxxbg(number,skyhmc,lhh), callBack, 100);
                }
                break;
            case R.id.bar_back:
            case R.id.tv_back:
                finish();
                break;
        }
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {
        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String message = item.getString("message");
            String code = item.getString("code");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if(what == 99){
                    String data = item.getDataStr();
                    FundLhhBean lhhBean = GsonUtil.GsonToBean(data,FundLhhBean.class);
                    if(lhhBean.getData()!=null){
                        if(lhhBean.getData().size()>0){
                            list.addAll(lhhBean.getData());
                            if(pop!=null) {
                                pop.showAsDropDown(mEtCode);
                            }
                        }else {
                            ToastUtils.showMessage(FundExtract2Activity.this,"输入正确的银行名称");
                        }
                    }else {
                        ToastUtils.showMessage(FundExtract2Activity.this,"输入正确的银行名称");
                    }
                }
                else if(what == 100){
                    String data = item.getDataStr();
                    FundTqyhxx fundTqyhxx = GsonUtil.GsonToBean(data,FundTqyhxx.class);
                    if(fundTqyhxx.getData()!= null) {
                        if(fundTqyhxx.getData().size()>0) {
                            if(fundTqyhxx.getData().get(0).getRet().equals("0")){
                                Intent intent = new Intent();
                                intent.putExtra("yhzh",number);
                                intent.putExtra("skyhmc",skyhmc);
                                intent.putExtra("lhh",lhh);
                                setResult(100,intent);
                                FundExtract2Activity.this.finish();
                                ToastUtils.showMessage(FundExtract2Activity.this,fundTqyhxx.getData().get(0).getMsg());
                            }else {
                                ToastUtils.showMessage(FundExtract2Activity.this,"添加银行卡失败");
                            }
                        }else {
                            ToastUtils.showMessage(FundExtract2Activity.this,"添加银行卡失败");
                        }
                    }else {
                        ToastUtils.showMessage(FundExtract2Activity.this,"添加银行卡失败");
                    }
                }
            }
        }
    };


    CustomPopWindow pop;
    List< FundLhhBean.DataBean > list;
    BankSelectAdapter mAdapter;
    String lhh;
    public void initPopwindow() {
        RecyclerView reView = (RecyclerView) LayoutInflater.from(this).inflate(R.layout.recyclerview,null);
        pop = new  CustomPopWindow.PopupWindowBuilder(this).setView(reView)
                .create();
        list = new ArrayList<>();
        mAdapter = new BankSelectAdapter(R.layout.item_text_mechanism, list);
        reView.setLayoutManager(new LinearLayoutManager(this));
        reView.addItemDecoration(new LineItemDecoration(1));
        reView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(pop!=null) {
                    pop.dissmiss();
                }
                lhh = mAdapter.getData().get(position).getGrckzhkhyhdm();
                mEtCode.setText(mAdapter.getData().get(position).getGrckzhkhyhmc());
            }
        });
    }
}
