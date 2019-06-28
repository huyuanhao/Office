package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;

/**
 * @author dir_wang
 * @title 个人中心
 */
public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_phone;
    private TextView tv_idcard;
    private TextView tv_is_real_name;
    private TextView tv_user_name;
    private TextView tv_email;
    private TextView tv_postcode;
    private TextView tv_address;
    private RelativeLayout rl_login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
//		getPersonalCenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_personal_center;
    }

    private void initView() {
        initTitleBar(R.string.personal_center, this, null);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_is_real_name = (TextView) findViewById(R.id.tv_is_real_name);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_postcode = (TextView) findViewById(R.id.tv_postcode);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_idcard = (TextView) findViewById(R.id.tv_idcard);
        rl_login_password = (RelativeLayout) findViewById(R.id.rl_login_password);

        rl_login_password.setOnClickListener(this);
        showData();
    }

    private void showData() {
        if (LoginUtils.getInstance().isLoginSuccess()) {
            UserInfo.DATABean data = LoginUtils.getInstance().getUserInfo().getDATA();
            tv_phone.setText(data.getMOBILE_NUM());
            if ("0".equals(data.getSFSMRZ())) {
                tv_is_real_name.setText(getString(R.string.no_real_name));
            } else if ("1".equals(data.getSFSMRZ())) {
                tv_is_real_name.setText(getString(R.string.yes_real_name));
            }
            tv_user_name.setText(data.getUSERNAME());
            tv_email.setText(data.getEMAIL());
//			tv_postcode.setText(data.get);
            tv_address.setText(data.getIDCARD_ADDRESS());
            tv_idcard.setText(data.getIDCARD());
        }
    }

    private void initData() {

    }


    /**
     * 个人中心请求
     */
    private void getPersonalCenter() {
        ApiRequest request = OAInterface.laws("", "", "");
        invoke.invokeWidthDialog(request, callBack);
    }

    /**
     * 获取个人中心数据解析
     */
    private void showPersonalCenter(ResultItem result) {

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
                    showPersonalCenter(result);
                } else {
                    DialogUtils.showToast(PersonalCenterActivity.this, message);
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                PersonalCenterActivity.this.finish();
                break;
            case R.id.rl_login_password:
                if (LoginUtils.getInstance().isLoginSuccess()) {
                    Intent intent = new Intent(this, FindPasswordUsePhone.class);
                    intent.putExtra("xiugaimima", true);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }


}
