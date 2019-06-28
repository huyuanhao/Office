package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.enums.AddressType;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.PickUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.MyDialog;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.entity.AddressInfo;
import com.yt.simpleframe.http.bean.xmlentity.XmlAddressInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.KeyboardUtils;
import com.yt.simpleframe.utils.StringUtil;

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

public class AddAddressActivity extends YTBaseActivity {


    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_phone_number)
    EditText metPhoneNumber;
    @BindView(R.id.tv_select_city)
    TextView mTvSelectCity;
    @BindView(R.id.et_adrress)
    EditText mEtAdrress;
    @BindView(R.id.tv_modify)
    TextView mTvModify;
    @BindView(R.id.tv_del)
    TextView mTvDel;
    @BindView(R.id.et_zipcode)
    EditText mEtZipcode;
    @BindView(R.id.rb_yes)
    RadioButton mRbYes;
    @BindView(R.id.rb_no)
    RadioButton mRbNo;
    @BindView(R.id.et_tel)
    EditText mEtTel;
    @BindView(R.id.et_company_name)
    EditText mEtCompanyName;

    private AddressType type;
    private AddressInfo info;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_add_address);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showBack();
        type = (AddressType) getIntent().getExtras().get("type");
        if (type == AddressType.添加) {
            mTvDel.setVisibility(View.GONE);
            setTitle("添加地址");
            UserInfo.DATABean user = LoginUtils.getInstance().getUserInfo().getDATA();
            if (!BeanUtils.isEmpty(user)) {
                String userDuty = user.getUSERDUTY();
                if ("0".equals(userDuty)) {//个人
                    mEtName.setText(user.getREALNAME());
                    metPhoneNumber.setText(user.getMOBILE_NUM());
                } else if ("1".equals(userDuty)) {//企业
                    mEtName.setText(user.getFRDB());
                    metPhoneNumber.setText(user.getGR_REALPHONE());
                }
            }
            mTvSelectCity.setText("江西省 鹰潭市");

        } else if (type == AddressType.修改) {
            setTitle("修改地址");
            info = (AddressInfo) getIntent().getExtras().get("data");
            mEtName.setText(info.getSJRXM());
            mEtAdrress.setText(info.getADDRESS());
            metPhoneNumber.setText(info.getHANDSET());
            mTvSelectCity.setText(info.getPROV() +" "+ info.getCITY());
            mEtZipcode.setText(info.getYZBM());
            mEtTel.setText(info.getTEL_NO());
            mEtCompanyName.setText(info.getCOMPANY_NAME());


            if ("0".equals(info.getISDEFAULT())) {
                mRbNo.setChecked(true);
                mRbYes.setChecked(false);
            } else {
                mRbNo.setChecked(false);
                mRbYes.setChecked(true);
            }
            mTvDel.setVisibility(View.VISIBLE);
        }

    }


    private boolean checkData(String name, String phone, String selectCity, String address, String zipCode) {
        if (TextUtils.isEmpty(name)) {
            DialogUtils.showToast(this, "名称不能为空！");
            return false;
        }
        if (TextUtils.isEmpty(name) || !StringUtil.isMobileNO(phone)) {
            DialogUtils.showToast(this, "手机号码输入错误！");
            return false;
        }

        if (TextUtils.isEmpty(selectCity)) {
            DialogUtils.showToast(this, "城市选择错误！");
            return false;
        }
        if (TextUtils.isEmpty(selectCity)) {
            DialogUtils.showToast(this, "具体地址填写错误！");
            return false;
        }
        if (!StringUtil.checkZipCode(zipCode)) {
            DialogUtils.showToast(this, "邮编地址输入不正确！");
            return false;
        }
        if (!mRbNo.isChecked() && !mRbYes.isChecked()) {
            ToastUtils.showMessage(this, "请选择默认地址");
            return false;
        }
        return true;
    }

    public void modifyAddress() {
        String name = mEtName.getText().toString();
        String phone = metPhoneNumber.getText().toString();
        String selectCity = mTvSelectCity.getText().toString().trim();
        String address = mEtAdrress.getText().toString();
        String zipCode = mEtZipcode.getText().toString();
        String tel = mEtTel.getText().toString();
        String companyName = mEtCompanyName.getText().toString();

        String addressId = "";
        if (type == AddressType.修改) {
            addressId = info.getADDRESSID();
        }
        if (!checkData(name, phone, selectCity, address, zipCode)) {
            return;
        }
        String isDefault = mRbYes.isChecked() ? "1" : "0";
        /*info.setADDRESSID(addressId);
        info.setSJRXM(name);
        info.setHANDSET(phone);
        info.setADDRESS(address);
        info.setCOMPANY_NAME(companyName);
        info.setISDEFAULT(isDefault);
        info.setYZBM(zipCode);
        info.setDefault(mRbYes.isChecked());*/
        String prov = "";
        String city = "";
        if(!StringUtil.isEmpty(selectCity)){
            String[] provStrs = selectCity.split(" ");
            if(provStrs.length >= 2){
                prov = provStrs[0];
                city =  provStrs[1];
            }
        }


        XmlAddressInfo xmlAddressInfo = new XmlAddressInfo(address, companyName, phone, isDefault, name, tel, zipCode, prov, city);
        ApiManager.getApi().exeNormal(RequestBodyUtils.modifyAddress(LoginUtils.getInstance().getUserInfo()
                .getAuthtoken(), addressId, xmlAddressInfo))
                .compose(this.<BaseBean>loadingDialog())
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean baseBean) {
                        Intent intent = new Intent();
//                        intent.putExtra("addressInfo", info);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
    }

    public void delAddress() {
        String userId = LoginUtils.getInstance().getUserInfo().getDATA().getUSERID();
        ApiManager.getApi().exeNormal(RequestBodyUtils.delAddress(LoginUtils.getInstance().getUserInfo()
                .getAuthtoken(), userId, info.getADDRESSID()))
                .compose(this.<BaseBean>loadingDialog())
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean baseBean) {
                        Intent intent = new Intent();
//                        intent.putExtra("addressInfo", info);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
    }


    @OnClick({R.id.tv_select_city, R.id.tv_modify, R.id.tv_del, R.id.rb_yes, R.id.rb_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_city:
                KeyboardUtils.hideSoftInput(this);
                PickUtils.showCityPickerView(this, mTvSelectCity.getText().toString(), new PickUtils.StringCallback() {
                    @Override
                    public void getString(String city) {
                        mTvSelectCity.setText(city);
                    }
                });
                break;
            case R.id.tv_modify:
                modifyAddress();
                break;
            case R.id.tv_del:
                MyDialog.showDialog(this, "提示", "确定删除吗！", new MyDialog.InterfaceClick() {
                    @Override
                    public void click() {
                        delAddress();
                    }
                });
                break;
            case R.id.rb_yes:
                break;
            case R.id.rb_no:
                break;
        }
    }

}
