package com.powerrich.office.oa.fund.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：签约对冲还贷
 * 作者：梁帆
 * 时间：2019/3/1
 * 版权：
 */
public class QianYueDuiChongHuaiDaiActivity extends BaseActivity {


    @BindView(R.id.bar_back)
    ImageView mBarBack;
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.ok)
    Button mOk;
    @BindView(R.id.cancel)
    Button mCancel;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_qianyue_duichong_huandai;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mBarTitle.setText("签约对冲还贷");
    }


    @OnClick({R.id.bar_back, R.id.ok, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.ok:
                break;
            case R.id.cancel:
                break;
        }
    }
}
