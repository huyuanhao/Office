package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.LeaderEmailInfoBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

/**
 * 文件名：LeaderMailInfoActivity
 */

public class LeaderMailInfoActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_write_man;
    private EditText et_write_time;
    private EditText et_title;
    private EditText et_write_content;
    private EditText et_department_name;
    private EditText et_leader_name;
    private EditText et_is_revert;
    private EditText et_revert_time;
    private EditText et_revert_content;
    private String mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mid = getIntent().getStringExtra("mid");
        initView();
        loadData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_leader_meail_info;
    }

    private void loadData() {
        if (null != invoke)
            invoke.invokeWidthDialog(OAInterface.getLeadermailById(mid), callBack);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                String jsonStr = item.getJsonStr();
                Gson gson = new Gson();
                LeaderEmailInfoBean leaderEmailInfoBean = gson.fromJson(jsonStr, LeaderEmailInfoBean.class);
                showData(leaderEmailInfoBean);
            } else {
                DialogUtils.showToast(LeaderMailInfoActivity.this, message);
            }
        }

    };

    private void showData(LeaderEmailInfoBean info) {
        et_write_man.setText(info.getDATA().getSEND_USER());
        et_write_time.setText(info.getDATA().getSEND_TIME());
        et_title.setText(info.getDATA().getTITLE());
        et_write_content.setText(Html.fromHtml(info.getDATA().getCONTENT()));
        et_department_name.setText(info.getDATA().getSITE_NAME());
        et_leader_name.setText(info.getDATA().getLEADER_NAME());
        et_write_man.setText(info.getDATA().getSEND_USER());
        String is_revert = info.getDATA().getIS_REVERT().equals("1") ? "已回复" : "未回复";
        et_is_revert.setText(is_revert);
        et_revert_time.setText(info.getDATA().getREVERT_TIME());
        et_revert_content.setText(info.getDATA().getREVERT_CONTENT());
    }

    private void initView() {
        initTitleBar(R.string.consulting_lead_mail, this, null);
        et_write_man = (EditText) findViewById(R.id.et_write_man);
        et_write_time = (EditText) findViewById(R.id.et_write_time);
        et_title = (EditText) findViewById(R.id.et_title);
        et_write_content = (EditText) findViewById(R.id.et_write_content);
        et_department_name = (EditText) findViewById(R.id.et_department_name);
        et_leader_name = (EditText) findViewById(R.id.et_leader_name);
        et_is_revert = (EditText) findViewById(R.id.et_is_revert);
        et_revert_time = (EditText) findViewById(R.id.et_revert_time);
        et_revert_content = (EditText) findViewById(R.id.et_revert_content);
        setUIUnClickable();
    }

    private void setUIUnClickable() {
        et_write_man.setClickable(false);
        et_write_man.setFocusable(false);
        et_write_time.setClickable(false);
        et_write_time.setFocusable(false);
        et_title.setClickable(false);
        et_title.setFocusable(false);
        et_write_content.setClickable(false);
        et_write_content.setFocusable(false);
        et_department_name.setClickable(false);
        et_department_name.setFocusable(false);
        et_leader_name.setClickable(false);
        et_leader_name.setFocusable(false);
        et_is_revert.setClickable(false);
        et_is_revert.setFocusable(false);
        et_revert_time.setClickable(false);
        et_revert_time.setFocusable(false);
        et_revert_content.setClickable(false);
        et_revert_content.setFocusable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                LeaderMailInfoActivity.this.finish();
                break;

            default:
                break;
        }
    }
}
