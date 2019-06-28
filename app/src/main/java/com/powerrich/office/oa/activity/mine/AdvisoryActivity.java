package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.Interaction.IwantActivity;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.enums.AdvisoryType;
import com.powerrich.office.oa.fragment.mine.AdvisoryFragment;
import com.powerrich.office.oa.notify.NotifyKey;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.tools.VerificationUtils;
import com.yt.simpleframe.notification.NotificationCenter;
import com.yt.simpleframe.utils.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/6/006
 * 版权：
 */
public class AdvisoryActivity extends YTBaseActivity {

    @BindView(R.id.et_search_txt)
    EditText mEtSearchTxt;
    @BindView(R.id.bt_search)
    Button mBtSearch;
    @BindView(R.id.flt_process_content)
    FrameLayout mFltProcessContent;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    private int mPosition = 0;
    private AdvisoryType type;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_yt_advisory);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (AdvisoryType) getIntent().getSerializableExtra("type");
        if(type == AdvisoryType.咨询){
            setTitle("我的咨询");
        }else if(type == AdvisoryType.投诉){
            setTitle("我的投诉");
        }else if(type == AdvisoryType.建议){
            setTitle("我的建议");
        }
        AdvisoryFragment fragment = AdvisoryFragment.getInstance(type);
        replaceFragment(R.id.flt_process_content, fragment);
        showBack();
    }


    public void setBottomText(int position){
        mPosition = position;
        if(position == 0){
            mTvAdd.setText("+新增咨询");
            setTitle("我的咨询");
        }else if(position == 1){
            mTvAdd.setText("+新增投诉");
            setTitle("我的投诉");
        }else if(position == 2){
            mTvAdd.setText("+新增建议");
            setTitle("我的建议");
        }
    }



    @OnClick({R.id.bt_search, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                String searchText = mEtSearchTxt.getText().toString();
                if (!TextUtils.isEmpty(searchText)){
                    NotificationCenter.defaultCenter.postNotification(NotifyKey.SEARCH_KEY, searchText);
                }else{
                    ToastUtils.showMessage(this,"请输入搜索内容");
                }
                KeyboardUtils.hideSoftInput(this);
                break;
            case R.id.tv_add:
                if(VerificationUtils.isAuthentication(this)){
                    Intent intent = new Intent(this,IwantActivity.class);
                    intent.putExtra("iwant_type", (mPosition+1)+"");
                    startActivity(intent);
                }
                break;
        }
    }

}
