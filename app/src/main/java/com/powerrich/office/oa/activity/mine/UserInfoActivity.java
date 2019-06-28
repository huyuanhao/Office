package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.enums.UserType;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.utils.StringUtil;

import java.util.Date;
import java.util.Map;

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

public class UserInfoActivity extends YTBaseActivity {


    @BindView(R.id.ll_person)
    LinearLayout ll_person;
    @BindView(R.id.tv_p_name)
    TextView tv_p_name;
    @BindView(R.id.tv_p_sex)
    TextView tv_p_sex;
    @BindView(R.id.tv_p_card_id)
    TextView tv_p_card_id;
    @BindView(R.id.tv_p_phone)
    TextView tv_p_phone;
    @BindView(R.id.tv_p_email)
    TextView tv_p_email;
    @BindView(R.id.tv_p_register_time)
    TextView tv_p_register_time;

    @BindView(R.id.ll_company)
    LinearLayout ll_company;
    @BindView(R.id.tv_c_name)
    TextView tv_c_name;
    @BindView(R.id.tv_c_code)
    TextView tv_c_code;
    @BindView(R.id.tv_fr_name)
    TextView tv_fr_name;
    @BindView(R.id.tv_fr_id_card)
    TextView tv_fr_id_card;
    @BindView(R.id.tv_c_address)
    TextView tv_c_address;
    @BindView(R.id.tv_c_register_time)
    TextView tv_c_register_time;
    @BindView(R.id.tv_lxr_name)
    TextView tv_lxr_name;
    @BindView(R.id.tv_lxr_number)
    TextView tv_lxr_number;
    @BindView(R.id.tv_lxr_email)
    TextView tv_lxr_email;



    private UserType type;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (UserType) getIntent().getExtras().get("type");
        setTitle("我的资料");
        if (type == UserType.个人) {
            ll_company.setVisibility(View.GONE);
            ll_person.setVisibility(View.VISIBLE);
        } else if (type == UserType.法人) {
            ll_person.setVisibility(View.GONE);
            ll_company.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserInfo user = LoginUtils.getInstance().getUserInfo();
        if (LoginUtils.getInstance().isLoginSuccess()) {
            initView(user);
        } else {
            goFinishActivity(LoginActivity.class);
        }
    }


    private void initView(UserInfo userInfo) {
        if (userInfo == null) {
            return;
        }
        UserInfo.DATABean info = userInfo.getDATA();
        if (type == UserType.个人) {
            tv_p_name.setText(info.getREALNAME());

            if (!TextUtils.isEmpty(info.getSEX())) {
                tv_p_sex.setText("1".equals(info.getSEX()) ? "女" : "男");
            }else{
                if(!BeanUtils.isEmptyStr(info.getIDCARD())) {//性别为空时根据身份证号判断性别
                    if (info.getIDCARD().length() == 15) {
                        Map<String, Object> map = BeanUtils.getCarInfo15(info.getIDCARD());
                        tv_p_sex.setText(((String) map.get("sex")));
                    } else if (info.getIDCARD().length() == 18) {
                        Map<String, Object> map = BeanUtils.getCarInfo18(info.getIDCARD());
                        tv_p_sex.setText(((String) map.get("sex")));
                    }
                } else {
                    tv_p_sex.setText("");
                }
            }
            tv_p_card_id.setText(BeanUtils.isEmptyStr(info.getIDCARD()) ? "" : StringUtil.replaceIdCard(info.getIDCARD()));
            tv_p_phone.setText(info.getMOBILE_NUM());
            tv_p_email.setText(info.getEMAIL());
            tv_p_register_time.setText(DateUtils.getDateStr(new Date(info.getREGDATE()), null));

        } else if (type == UserType.法人) {
            tv_c_name.setText(info.getCOMPANYNAME());
            String businessLicence = info.getBUSINESSLICENCE();
            if (!BeanUtils.isEmptyStr(businessLicence)) {
                tv_c_code.setText(businessLicence.substring(0, 4) + "**********" + businessLicence.substring(14, businessLicence.length()));
            } else {
                tv_c_code.setText("");
            }
            tv_fr_name.setText(info.getFRDB());
            tv_fr_id_card.setText(BeanUtils.isEmptyStr(info.getFRDB_SFZHM()) ? "" : StringUtil.replaceIdCard(info.getFRDB_SFZHM()));
            tv_c_address.setText(info.getQYTXDZ());
//            tv_c_type.setText("");
            tv_c_register_time.setText(DateUtils.getDateStr(new Date(info.getREGDATE()), null));
            tv_lxr_name.setText(info.getGR_REALNAME());
            tv_lxr_number.setText(info.getGR_REALPHONE());
            tv_lxr_email.setText(info.getGR_EMAIL());
        }


    }

}
