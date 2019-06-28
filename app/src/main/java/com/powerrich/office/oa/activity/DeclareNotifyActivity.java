package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;

/**
 * 文 件 名：DeclareNotifyActivity
 * 描   述：个人、企业办事申报告知
 * 作   者：Wangzheng
 * 时   间：2018-6-13 11:16:33
 * 版   权：v1.0
 */
public class DeclareNotifyActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_progress_bar;
    private TextView tv_item_name, tv_item_code;
    private TextView tv_declare_content;
    private TextView tv_complete;

    private String itemName, itemCode;
    private String proKeyId;
    private String dynamicForm;
    private String trackId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemName = getIntent().getStringExtra("itemName");
        itemCode = getIntent().getStringExtra("itemCode");
        proKeyId = getIntent().getStringExtra("proKeyId");
        dynamicForm = getIntent().getStringExtra("dynamicForm");
        trackId = getIntent().getStringExtra("trackId");
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_declare_notify;
    }

    private void initView() {
        initTitleBar(R.string.declare_notify, null, this);
        iv_progress_bar = (ImageView) findViewById(R.id.iv_progress_bar);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        tv_item_code = (TextView) findViewById(R.id.tv_item_code);
        tv_declare_content = (TextView) findViewById(R.id.tv_declare_content);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
    }

    private void initData() {
        tv_item_name.setText(itemName);
        tv_item_code.setText(itemCode);
        tv_declare_content.setText(Html.fromHtml("您好，您申报的<font color='#ec5302'>【" + itemName + "】</font>办件已经申报成功。办件申报号为：【" + trackId + "】您可在五个工作日后，在APP“个人办事”— “办件查询”或“用户中心”中查看预审结果。"));
        tv_complete.setOnClickListener(this);
        if ("0".equals(dynamicForm)) {
            iv_progress_bar.setImageResource(R.drawable.per_circle_five_5);
        } else {
            iv_progress_bar.setImageResource(R.drawable.per_circle_five);
        }
    }

    /**
     * 转跳到主界面
     */
    private void toMain() {
        Intent intent = new Intent(DeclareNotifyActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        toMain();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                DeclareNotifyActivity.this.finish();
                break;
            case R.id.btn_top_right:
                toMain();
                break;
            case R.id.tv_complete:
                toMain();
                break;
        }
    }
}
