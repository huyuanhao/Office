package com.powerrich.office.oa.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.tools.PickUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11
 * 版权：
 */

public class PerfectInfomationActivity extends YTBaseActivity {


    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.rb_man)
    RadioButton mRbMan;
    @BindView(R.id.rb_women)
    RadioButton mRbWomen;
    @BindView(R.id.tv_group)
    TextView mTvGroup;
    @BindView(R.id.tv_card_type)
    TextView mTvCardType;
    @BindView(R.id.et_card_number)
    EditText mEtCardNumber;
    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @BindView(R.id.tv_select_city)
    TextView mTvSelectCity;
    @BindView(R.id.et_adrress)
    EditText mEtAdrress;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.tv_modify)
    TextView mTvModify;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_prefect_infomation);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("完善个人信息");
        showBack();
    }


    @OnClick({R.id.rb_man, R.id.rb_women, R.id.tv_group, R.id.tv_card_type, R.id.tv_select_city, R.id.tv_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_man:
                break;
            case R.id.rb_women:
                break;
            case R.id.tv_group:
                PickUtils.showEthnicPickerView(this.mContext, new PickUtils.StringCallback() {
                    @Override
                    public void getString(String city) {
                        mTvGroup.setText(city);
                    }
                });
                break;
            case R.id.tv_card_type:
                PickUtils.showCardIdPickerView(this, new PickUtils.StringCallback() {
                    @Override
                    public void getString(String city) {
                        mTvCardType.setText(city);
                    }
                });
                break;
            case R.id.tv_select_city:
                PickUtils.showCityPickerView(this, new PickUtils.StringCallback() {
                    @Override
                    public void getString(String city) {
                        mTvSelectCity.setText(city);
                    }
                });
                break;
            case R.id.tv_modify:
                //提交个人信息
                String name = mEtName.getText().toString();
                String cardtype = mTvCardType.getText().toString();
                String cardNumber = mEtCardNumber.getText().toString();
//                String birTime = mtime
                break;
        }
    }

}
