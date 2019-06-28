package com.powerrich.office.oa.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.MyDialog;

/**
 * 文件名：FindPasswordUseIdCard
 * 描述：用身份证号找回密码
 * 作者：白煜
 * 时间：2018/1/18 0018
 * 版权：
 */

public class FindPasswordUseIdCard extends BaseActivity implements View.OnClickListener{

    private Button bt_next;
    private EditText et_id_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_find_password_use_id_card;
    }

    private void initView() {
        initTitleBar(R.string.find_password, this, null);
        et_id_card = (EditText)findViewById(R.id.et_id_card);
        bt_next = (Button)findViewById(R.id.bt_next);
        bt_next.setOnClickListener(this);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                    ResultItem data = (ResultItem) item.get("DATA");
                    String userName = data.getString("USERNAME", "");
                    if (BeanUtils.isEmptyStr(userName)) {//用户不存在
                        showCloseDialog();
                    } else {//用户存在，进入下一步
                        toNextStep(userName);
                    }
            } else {
                DialogUtils.showToast(FindPasswordUseIdCard.this, message);
            }
        }

    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;

            case R.id.bt_next:
                if (BeanUtils.isIdCard(et_id_card.getText().toString())) {
                    //通过身份证号获取用户信息
                    invoke.invoke(OAInterface.getUserByIdInfo(et_id_card.getText().toString()), callBack);
                } else {
                    DialogUtils.showToast(FindPasswordUseIdCard.this, "身份证格式不正确");
                }
                break;
        }
    }
    private void toNextStep(String userName) {
        Intent intent = new Intent(this, ReSetPasswordActivity.class);
        intent.putExtra("userName",userName);
        startActivity(intent);
    }

    /**
     *用户不存在弹框
     */
    private void showCloseDialog(){
        MyDialog builder = new MyDialog(FindPasswordUseIdCard.this).builder();
        builder
                .setTitle("提示信息")
                .setMessage("该身份证号没有注册")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
