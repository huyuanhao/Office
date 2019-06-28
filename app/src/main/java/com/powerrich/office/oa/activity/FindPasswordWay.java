package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;

/**
 * 文件名：FindPosswordWay
 * 描述：密码找回方式
 * 作者：白煜
 * 时间：2018/1/18 0018
 * 版权：
 */

public class FindPasswordWay extends BaseActivity implements View.OnClickListener{

    private RadioGroup rg;
    private RadioButton rb_phone;
    private RadioButton rb_id_card;
    private Button bt_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_find_password_way;
    }

    private void initView() {
        initTitleBar(R.string.find_password, this, null);
        rb_phone = (RadioButton)findViewById(R.id.rb_phone);
        rb_id_card = (RadioButton)findViewById(R.id.rb_id_card);

        bt_next = (Button)findViewById(R.id.bt_next);
        bt_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.bt_next:
                Intent intent = null;
                if(rb_id_card.isChecked()){
                    intent = new Intent(this,FindPasswordUseIdCard.class);
                }else if(rb_phone.isChecked()){
                    intent = new Intent(this,FindPasswordUsePhone.class);
                }
                startActivity(intent);
                break;
        }
    }
}
