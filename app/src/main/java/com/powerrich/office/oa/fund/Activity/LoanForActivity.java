package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.LoanBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.view.LoanView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 贷款进度查询
 */
public class LoanForActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llContent;
    public static final int SENDYZM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("jsc", "LoanForActivity-onCreate:");
        initTitleBar("贷款进度查询", this, null);
        initView();
        initData();
        //   llContent.addView(new LoanView(this,4));
//        llContent.addView(new LoanView(this,0));
//        llContent.addView(new LoanView(this,1));
//        llContent.addView(new LoanView(this,2));
//        llContent.addView(new LoanView(this,2));
//        llContent.addView(new LoanView(this,3));

//        LoanView loanView = new LoanView(LoanForActivity.this);
//        loanView.setState(4);
//        llContent.addView(loanView);
//
//        LoanView loanView2 = new LoanView(LoanForActivity.this);
//        loanView2.setState(0);
//        llContent.addView(loanView2);

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_loan_for;
    }


    IRequestCallBack callBack = new BaseRequestCallBack() {
        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            Log.e("jsc", "LoanForActivity-process:" + item.toString());
            String msg = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(item.getString("code"))) {
                ResultItem result = (ResultItem) item.get("DATA");
                List<ResultItem> data = result.getItems("DATA");
//                String s = new Gson().toJson(data);
//                Log.e("jsc", "LoanForActivity-processA:"+s);
//
//                List<LoanBean> loanBeans = new Gson().fromJson(s, new TypeToken<List<LoanBean>>() {
//                }.getType());
                List<LoanBean> loanBeans = canvet(data);

                Log.e("jsc", "LoanForActivity-processB:" + loanBeans.toString());

                for (int i = 0; i < loanBeans.size() + 1; i++) {
                    LoanView loanView = new LoanView(LoanForActivity.this);
                    //最后
                    if (i == loanBeans.size()) {
                        loanView.setState(3);
                        llContent.addView(loanView);
                        break;
                    }
                    LoanBean loanBean = loanBeans.get(i);

                      //第一个
                    if (i == 0) {
                        loanView.setState(4);
                        loanView.setTvSlsjName(loanBean.getBljd());
                        // 为空
                    } else if (TextUtils.isEmpty(loanBean.getBlsj())) {
                        loanView.setState(2);
                        //有内容
                    } else {
                        loanView.setState(0);
                        loanView.setTvBljdValue(loanBean.getBljd());
                        loanView.setTvBlsjValue(loanBean.getBlsj());
                        loanView.setTv_cllr_time(loanBean.getBlsj());
                        loanView.setTvBlresultValue(loanBean.getBz());
                        loanView.setTv_cllr(loanBean.getBljd());
                    }


                    llContent.addView(loanView);


                }
            }
        }
    };


    private List<LoanBean> canvet(List<ResultItem> resultItems) {
        List<LoanBean> loanBeans = new ArrayList<>();
        for (int i = 0; i < resultItems.size(); i++) {
            LoanBean loanBean = new LoanBean();
            ResultItem resultItem = resultItems.get(i);
            loanBean.setBljd(resultItem.getString("bljd"));
            loanBean.setBlsj(resultItem.getString("blsj"));
            loanBean.setBz(resultItem.getString("bz"));
            loanBeans.add(loanBean);
        }
        return loanBeans;
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void initData() {
        //发送短信接口
        invoke.invokeWidthDialog(OAInterface.getloanProgressInformationQuery(""), callBack, SENDYZM);
    }


    private void initView() {
        llContent = (LinearLayout) findViewById(R.id.ll_content);
    }
}
