package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：注意事项
 * 作者：梁帆
 * 时间：2019/3/1
 * 版权：
 */
public class NotesActivity extends BaseActivity {


    @BindView(R.id.bar_back)
    ImageView mBarBack;
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fund_notes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mBarTitle.setText("注意事项");
    }


    @OnClick(R.id.bar_back)
    public void onViewClicked() {
        finish();
    }
}
