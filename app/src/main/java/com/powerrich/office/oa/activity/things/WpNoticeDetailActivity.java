package com.powerrich.office.oa.activity.things;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 停水停电通知
 */
public class WpNoticeDetailActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_range)
    TextView tvRange;
    @BindView(R.id.tv_line)
    TextView tvLine;
    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_wp_notice_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.bar_back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
        }
    }
    private void initView() {
        barTitle.setText("停水停电通知");
        tvTitle.setText("停水停电通知i");
    }
}
