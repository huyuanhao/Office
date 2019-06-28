package com.powerrich.office.oa.fund.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.FundInfo;
import com.powerrich.office.oa.bean.FundLoanInfo;
import com.powerrich.office.oa.bean.RepaymentPlanInfo;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.fund.utils.FundUtils;
import com.powerrich.office.oa.fund.bean.FundBean;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.RxRoundProgress;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.ReFundYQHKInfoBean;
import com.yt.simpleframe.http.bean.entity.ReFundYQCXInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.DimensUtils;
import com.yt.simpleframe.utils.SharedPreferencesUtils;
import com.yt.simpleframe.view.BadgeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.powerrich.office.oa.fund.bean.FundBean.dwzh;
import static com.powerrich.office.oa.fund.bean.FundBean.grzh;
import static com.powerrich.office.oa.fund.bean.FundBean.jkhtbh;
import static com.powerrich.office.oa.fund.bean.FundBean.zjbzxbm;

/**
 * @author PC
 * @date 2018/12/19 13:53
 */
public class FundHomeActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.deposite)
    TextView deposite;
    @BindView(R.id.deposite_time)
    TextView depositeTime;
    @BindView(R.id.extract)
    TextView extract;
    @BindView(R.id.extract_time)
    TextView extractTime;
    @BindView(R.id.loan_statu)
    TextView loanStatu;
    @BindView(R.id.loan_amount)
    TextView loanAmount;
    @BindView(R.id.loan_time)
    TextView loanTime;
    @BindView(R.id.gv_manage)
    GridView gvManage;
    @BindView(R.id.gv_query)
    GridView gvQuery;
    @BindView(R.id.roundProgressBar)
    RxRoundProgress roundProgress;
    @BindView(R.id.tv_statu)
    TextView tvStatu;
    private int[] manageIcon = {R.drawable.jj_extract, R.drawable.jj_pay, R.drawable.jj_settle,
            R.drawable.jj_money};
    private String[] manageIconName = {"公积金提取", "提前还本", "提前结清", "签约对冲还贷",};

    private int[] queryIcon = {R.drawable.jj_commission, R.drawable.jj_plan, R.drawable.jj_location,
            R.drawable.jj_attention};
    private String[] queryIconName = {"贷款进度", "贷款还款计划", "贷款还款明细", "贷款逾期明细"};

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fund;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initView();
    }

    @OnClick({R.id.bar_back, R.id.info, R.id.iv_right, R.id.ll_loan, R.id.ll_jc, R.id.ll_tq})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;

            case R.id.info://账户信息
                if(!TextUtils.isEmpty(grzh)) {
                    startActivity(new Intent(FundHomeActivity.this, FundInfoActivity.class));
                }else {
                    ToastUtils.showMessage(FundHomeActivity.this,"暂无此人账户信息");
                }
                break;
            case R.id.iv_right://消息
                startActivity(new Intent(FundHomeActivity.this, FundCalculatorActivity.class));
                break;
            case R.id.ll_loan:
                if(!TextUtils.isEmpty(jkhtbh)) {
                    startActivity(new Intent(FundHomeActivity.this, LoanAccountInfoActivity.class));
                }
                break;
            case R.id.ll_jc:
                startActivity(new Intent(FundHomeActivity.this, FundFunctionActivity.class)
                        .putExtra("type", 1));
                break;
            case R.id.ll_tq:
                startActivity(new Intent(FundHomeActivity.this, FundFunctionActivity.class)
                        .putExtra("type", 2));
                break;
        }
    }

    public void initView() {
        barTitle.setText("公积金");
        ivRight.setImageResource(R.drawable.jj_collect);
        ivRight.setVisibility(View.GONE);

        setMessage();
        setManage();
        setQuery();

        getInfo1();
    }

    public void setMessage() {
        BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(ivRight);
        badgeView.setBadgeMargin(0, 0, 7, 0);
        badgeView.setPadding(5, 3, 5, 3);
        badgeView.setBackground(DimensUtils.dip2px(this, 10), Color.parseColor("#FF2525"));
        badgeView.setText("10");
    }

    public void setManage() {
        gvManage.setAdapter(new CommonAdapter< Map< String, Object > >(this, getData(manageIcon, manageIconName), R.layout.service_gv_item) {

            @Override
            public void convert(ViewHolder holder, Map< String, Object > item) {
                holder.setImageResource(R.id.image, ((int) item.get("image")));
                holder.setTextView(R.id.text, ((String) item.get("text")));
            }
        });
        gvManage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ? > parent, View view, int position, long id) {
                switch (position) {
                    case 0://公积金提取
                        if (null != invoke) {
                            //公积金贷款基本信息查询
                            invoke.invokeWidthDialog(OAInterface.jcrywblyz(), callBack, 90);
                        }
                        break;
                    case 1://提前还本
                        startActivity(new Intent(FundHomeActivity.this,EarlyActivity.class));
                        break;
                    case 2://提前结清
                        startActivity(new Intent(FundHomeActivity.this,EarlySettlementActivity.class));
                        break;
                    case 3://签约对冲还贷
                        startActivity(new Intent(FundHomeActivity.this, QianYueDuiChongHuaiDaiActivity.class));
                        break;
                }
            }
        });
    }

    public void setQuery() {
        gvQuery.setAdapter(new CommonAdapter< Map< String, Object > >(this, getData(queryIcon, queryIconName), R.layout.service_gv_item) {

            @Override
            public void convert(ViewHolder holder, Map< String, Object > item) {
                holder.setImageResource(R.id.image, ((int) item.get("image")));
                holder.setTextView(R.id.text, ((String) item.get("text")));
            }
        });
        gvQuery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ? > parent, View view, int position, long id) {
//                {"贷款进度", "贷款还款计划", "贷款还款明细", "贷款逾期明细"}
                switch (position) {
                    case 0://贷款进度
                       startActivity(new Intent(FundHomeActivity.this, LoanForActivity.class));
//                        startActivity(new Intent(FundHomeActivity.this, FundBusinessActivity.class));
                        break;
                    case 1://贷款还款计划
                        startActivity(new Intent(FundHomeActivity.this, RefundDetailActivity.class).putExtra("type",1));
                        break;
                    case 2://贷款还款明细
//                        startActivity(new Intent(FundHomeActivity.this, FuWuWangDianChaXunActivity.class));
                        break;
                    case 3://贷款逾期明细
//                        startActivity(new Intent(FundHomeActivity.this,NotesActivity.class));
//                        startActivity(new Intent(FundHomeActivity.this, FundFunctionActivity.class));
                        break;
                }
            }
        });
    }

    private void getInfo1() {
        UserInfo userInfo = LoginUtils.getInstance().getUserInfo();
        String xingming = userInfo.getDATA().getGR_REALNAME();
        String zjhm = userInfo.getDATA().getADDR();

        xingming = "曾志英";
        zjhm = "360602196901110026";
        FundBean.xingming = xingming;
        FundBean.zjhm = zjhm;
        if (null != invoke) {
            //公积金贷款基本信息查询
            invoke.invokeWidthDialog(OAInterface.getFundLoanInfo(zjbzxbm, xingming, zjhm), callBack, 101);
        }
    }

    private void getInfo2() {
        if (null != invoke) {
            //公积金信息查询
            invoke.invokeWidthDialog(OAInterface.getFundInfo(zjbzxbm, grzh), callBack, 100);
        }
    }

    private void getInfo3() {
        if (null != invoke) {
            String jsrq = DateUtils.getMonthAgo(DateUtils.parseDate(FundBean.khrq, "yyyy-MM-dd"), 600, "yyyy-MM-dd");
            FundBean.jsrq = jsrq;
            //还款计划查询
            if(!TextUtils.isEmpty(jkhtbh)) {
                invoke.invokeWidthDialog(OAInterface.RepaymentPlan(zjbzxbm, jkhtbh, FundBean.khrq, jsrq), callBack, 102);
            }

            //公积金信息查询
            invoke.invokeWidthDialog(OAInterface.dkzhxxcx("C36060", "20180502030002"), callBack, 103);
            //贷款还款明细查询
//            invoke.invokeWidthDialog(OAInterface.dkhkmxcx("C36060", "20180502030002", "2019-01-01", "2020-03-01"), callBack, 104);
            //逾期未还款明细查询
            getYQWHKInfo();

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
//                    List<ResultItem> data = item.getItems("DATA");
                    String s = item.getDataStr();
                    FundUtils.getInstance().saveFundGjjZhInfo(FundHomeActivity.this,s);
                    FundInfo fundInfo = GsonUtil.GsonToBean(s, FundInfo.class);
                    setData(fundInfo);
                } else if (101 == what) {
                    String s = item.getDataStr();
                    List< FundLoanInfo > loanInfo = GsonUtil.jsonToList(s, FundLoanInfo.class);
                    if (loanInfo.size() > 0) {
                        setLoanData(loanInfo.get(0));
                    }
                } else if (102 == what) {
                    String s = item.getDataStr();
                    FundUtils.getInstance().saveFundHkjhInfo(FundHomeActivity.this,s);
                    RepaymentPlanInfo repaymentPlanInfo = GsonUtil.GsonToBean(s, RepaymentPlanInfo.class);
                    setRepayment(repaymentPlanInfo);
                } else if(103==what){
                    String s = item.getDataStr();
                    FundUtils.getInstance().saveFundInfo(FundHomeActivity.this,s);
                } else if(90 == what){
                    if (null != invoke) {
                        //公积金贷款基本信息查询
                        invoke.invokeWidthDialog(OAInterface.jcrywblyz(), callBack, 91);
                    }
                } else if (91 == what) {
                    startActivity(new Intent(FundHomeActivity.this, FundExtract1Activity.class));
                } else if(104 == what){
//                    startActivity(new Intent(FundHomeActivity.this, FundExtract1Activity.class));
                }
            } else {
                DialogUtils.showToast(FundHomeActivity.this, message);
            }
        }

    };

    public void setData(FundInfo info) {
        if (info.getDATA() == null) {
            return;
        }
        if (info.getDATA().size() < 1) {
            return;
        }
        FundInfo.DATABean bean = info.getDATA().get(0);

        //保存参数
        FundBean.dwzh = bean.getDwzh();
        FundBean.khrq = bean.getKhrq();
        FundBean.grzh = bean.getGrzh();
        FundBean.grzhzt = bean.getGrzhzt();
        FundBean.grzhye = bean.getGrzhye();
        FundBean.dwmc = bean.getDwmc();
        FundBean.sjhm = bean.getSjhm();

        getInfo3();

        amount.setText(bean.getGrzhye() + "元");//账户余额
        id.setText(bean.getGrzh()); //账户id
        tvStatu.setText(bean.getGrzhzt());//账户状态
        deposite.setText(bean.getZjjce());//我的缴存
        depositeTime.setText("最近缴存 " + bean.getZjjcrq());//最近缴存时间
        extract.setText(bean.getZjtqe());//最近提取
        extractTime.setText("最近提取 " + bean.getZjtqrq());//最近提取时间
    }

    private void setLoanData(FundLoanInfo info) {
//        FundBean.grzh = bean.getGrzh();
//        FundBean.grzhzt = "正常";
//        FundBean.grzhye = "18640.29";
//        FundBean.dwmc = bean.getDwmc();
//        FundBean.dwzh = bean.getDwzh();

        if (info.getDkxx().size() > 0) {
            FundLoanInfo.DkxxBean dkxxBean = info.getDkxx().get(0);
            FundBean.jkhtbh = dkxxBean.getJkhtbh();
            FundBean.dkzt = dkxxBean.getDkzt();
            FundBean.dkye = dkxxBean.getDkye();
            FundBean.dkffe = dkxxBean.getDkffe();
            FundBean.jkrgjjzh = dkxxBean.getJkrgjjzh();
            FundBean.grzh = dkxxBean.getJkrgjjzh();

            loanStatu.setText(dkxxBean.getDkzt());//正常还款
            money = Double.parseDouble(dkxxBean.getDkye());
            maxMoney = Double.parseDouble(dkxxBean.getDkffe());

            initRoundProgress();
        }else {
            FundBean.jkhtbh = "";
            FundBean.dkzt = "";
            FundBean.dkye = "";
            FundBean.dkffe = "";
            FundBean.jkrgjjzh = "";
            FundBean.grzh = "";
            loanStatu.setText("无贷款");//正常还款
            money = 0;
            maxMoney = 0;
        }
        if (info.getGjjxx().size() > 0) {
            for (int i = 0; i < info.getGjjxx().size(); i++) {
                if (info.getGjjxx().get(i) != null) {
                    FundLoanInfo.GjjxxBean gjjxxBean = info.getGjjxx().get(i);
//                if(gjjxxBean.getGrzhzt().equals("正常")){
                    FundBean.grzh = gjjxxBean.getGrzh();
                    FundBean.grzhzt = gjjxxBean.getGrzhzt();
                    FundBean.dwzh = gjjxxBean.getDwzh();
                    FundBean.grzhye = gjjxxBean.getGrzhye();
                    FundBean.dwmc = gjjxxBean.getDwmc();
                    FundBean.jgbm = gjjxxBean.getJgbm();
//                }
                }
            }
        }
        if(!TextUtils.isEmpty(FundBean.grzh)) {
            getInfo2();
        }
    }

    private void setRepayment(RepaymentPlanInfo info) {
        if (info.getDATA() == null) {
            return;
        }
        if (info.getDATA().size() < 1) {
            return;
        }
        RepaymentPlanInfo.DATABean bean = info.getDATA().get(0);
        loanAmount.setText(bean.getYhbx() + "元");//本期还款金额
        loanTime.setText(bean.getYhrq());//本期还款时间
    }

    private int progress = 0;
    Thread downLoadThread2;
    double money = 0;
    double maxMoney = 0;

    private void initRoundProgress() {
        roundProgress.setProgress(progress);
        roundProgress.setMax(maxMoney);

        downLoadThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                while (!downLoadThread2.isInterrupted()) {
                while (progress < money) {
                    progress += roundProgress.getMax() * 0.01;
                    if (progress < roundProgress.getMax()) {
                        mHandler.sendEmptyMessage(100);
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler.sendEmptyMessage(101);
//                        while (progress > 0) {
//                            progress -= roundProgress.getMax() * 0.01;
//                            if (progress > 0) {
//                                roundProgress.setProgress(progress);
//                            }
//                            Thread.sleep(8);
//                        }

//                        if (money != 0) {
//                            while (progress < money) {
//                                progress += money * 0.01;
//                                roundProgress.setProgress(progress);
//                                Thread.sleep(10);
//                            }
//                        }

//                        roundProgress.setProgress(money);
            }

//            }
        });
        downLoadThread2.start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                roundProgress.setProgress(progress);
            } else if (msg.what == 101) {
                roundProgress.setProgress(money);
            }
        }
    };

    private int getMax(double currentProgress) {
        if (currentProgress < 100 && currentProgress > 0) {
            return 100;
        } else if (currentProgress >= 100 && currentProgress < 1000) {
            return 1000;
        } else if (currentProgress >= 1000 && currentProgress < 5000) {
            return 5000;
        } else if (currentProgress >= 5000 && currentProgress < 20000) {
            return 20000;
        } else if (currentProgress >= 20000 && currentProgress < 100000) {
            return 100000;
        } else if (currentProgress >= 100000) {
            return stringToInt(currentProgress * 1.1 + "");
        } else {
            return stringToInt(currentProgress + "");
        }
    }

    public static int stringToInt(String str) {
        if (isNullString(str)) {
            return 0;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    /**
     * 判断字符串是否为空 为空即true
     *
     * @param str 字符串
     * @return
     */
    public static boolean isNullString(@Nullable String str) {
        return str == null || str.length() == 0 || "null".equals(str);
    }


    public List< Map< String, Object > > getData(int[] iconIds, String[] iconNames) {
        List< Map< String, Object > > dataList = new ArrayList<>();
        for (int i = 0; i < iconNames.length; i++) {
            Map< String, Object > map = new HashMap<>();
            map.put("image", iconIds[i]);
            map.put("text", iconNames[i]);
            dataList.add(map);
        }
        return dataList;
    }


    /**
     * 8逾期未还款明细查询
     */
    public void getYQWHKInfo() {

        ApiManager.getApi().queryYQWHK(RequestBodyUtils.queryYqwhk(FundBean.zjbzxbm2, FundBean.jkhtbh2))
                .compose(RxSchedulers.<ReFundYQHKInfoBean>io_main())
                .compose(this.<ReFundYQHKInfoBean>loadingDialog())
                .subscribe(new BaseSubscriber<ReFundYQHKInfoBean>() {
                    @Override
                    public void result(ReFundYQHKInfoBean baseBean) {
                        Log.e("jsc", "RefundDetailActivity-result:" + baseBean.getDATA().getDATA().size()+baseBean.toString());
                        if (!BeanUtils.isEmpty(baseBean.getDATA().getDATA())) {
                           ;
                             FundUtils.getInstance().saveFundYqwhkInfo(FundHomeActivity.this, GsonUtil.GsonString(baseBean.getDATA().getDATA()));

//                            if (pageIndex == 1) {
//                                mReFundYQCXInfos.clear();
//                                mReFundYQCXInfos.addAll(baseBean.getDATA().getDATA());
//                            } else {
//                                mReFundYQCXInfos.addAll(baseBean.getDATA().getDATA());
//                            }
//                            mReFundBeanCommonRvAdapter.notifyDataSetChanged();
                        }

                    }


                });
    }
}
