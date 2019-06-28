package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

/**
 * 文 件 名：PolicyStatuteDetailActivity
 * 描   述：阳光政务政策法规具体政策内容界面
 * 作   者：Wangzheng
 * 时   间：2017/12/8
 * 版   权：v1.0
 */
public class PolicyStatuteDetailActivity extends BaseActivity implements View.OnClickListener {

    private WebView webView;
    private TextView tv_title;
    private TextView tv_create_time;
    private String type;
    private String policyStatuteId;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        policyStatuteId = getIntent().getStringExtra("policy_statute_id");
        title = getIntent().getStringExtra("title");
        initView();
        getPolicyStatuteDetail();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_news_detail;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        initTitleBar(title, this, null);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_create_time = (TextView) findViewById(R.id.tv_create_time);
        webView = (WebView) findViewById(R.id.webView);
    }

    /**
     * 根据政策法规id查询具体政策内容请求
     */
    private void getPolicyStatuteDetail() {
        ApiRequest request = OAInterface.getPolicyStatuteDetail(type, policyStatuteId);
        invoke.invokeWidthDialog(request, callBack);
    }

    /**
     * 解析数据
     *
     * @param result
     */
    private void parseData(ResultItem result) {
        if (result == null) {
            return;
        }
        String title = result.getString("TITLE");
        String createTime = result.getString("CREATTIME");
        String content = result.getString("CONTENT");
        tv_title.setText(title);
        tv_create_time.setText(createTime);
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            if (!BeanUtils.isEmpty(response)) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String code = item.getString("code");
                String message = item.getString("message");
                if (Constants.SUCCESS_CODE.equals(code)) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    parseData(result);
                } else {
                    DialogUtils.showToast(PolicyStatuteDetailActivity.this, message);
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                PolicyStatuteDetailActivity.this.finish();
                break;
            default:
                break;
        }
    }
}
