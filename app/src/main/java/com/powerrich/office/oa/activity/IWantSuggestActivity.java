package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.IWantBean;
import com.powerrich.office.oa.bean.WorkInfo;
import com.powerrich.office.oa.bean.XmlInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.DialogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 我要建议、我要咨询、我要投诉、我要举报界面
 *
 * @author Administrator
 */
public class IWantSuggestActivity extends BaseActivity implements OnClickListener {
    /**
     * 获取部门列表
     */
    private static int GETSITELIST = 000;
    private static int GET_SHOW_DATA = 0001;
    /**
     * 咨询，建议，投诉
     */
    private static int IWANT = 111;
    private Spinner spinner_dep;
    private EditText et_dep;
    private TextView tv_top_right;
    /**
     * 姓名，电话，手机，电子邮箱，邮政编码，地址，标题，内容
     */
    private EditText et_name, et_tel, et_phone, et_email, et_postal_code, et_address, et_title, et_content;
    private XmlInfo info;
    private String iwant_type;
    private String dep_name;
    private String dep_id;
    /**
     * 单位名称
     */
    private TextView tv_dep_name;
    private RadioGroup rg_gender;
    private RadioButton rb_no, rb_yes;
    private CheckBox cb_short_msg;
    private CheckBox cb_mail;
    private boolean isShowDetail;

    private String id;
    private LinearLayout ll_cb;
    private LinearLayout ll_rg;
    private LinearLayout ll_postal_code;
    private LinearLayout ll_tel;
    private LinearLayout ll_email;
    private LinearLayout ll_reverter;
    private LinearLayout ll_reverter_content;
    private EditText et_reverter;
    private EditText et_reverter_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwant_type = getIntent().getStringExtra("iwant_type");
        dep_id = getIntent().getStringExtra("dep_id");
        dep_name = getIntent().getStringExtra("dep_name");
        id = getIntent().getStringExtra("id");

        isShowDetail = getIntent().getBooleanExtra("isShowDetail", false);

        initView();
        if (isShowDetail) {
            getShowData();
        } else {
            initData();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_iwant_suggest;
    }

    private void showData(IWantBean iWantBean) {
        setUIUnClickable();
        et_name.setText(iWantBean.getDATA().getMYNAME());
//        et_tel.setText(iWantBean.getDATA().getPHONENUMBER());
        et_phone.setText(iWantBean.getDATA().getMOBILE());
//        cb_short_msg.setChecked(iWantBean.getDATA().getISNOTE_NOTIFY().equals("1") ? false : true);
//        cb_mail.setChecked(iWantBean.getDATA().getISMAIL_NOTIFY().equals("1") ? false : true);
//        et_email.setText(iWantBean.getDATA().getEMAIL_ADDRESS());
//        et_postal_code.setText(iWantBean.getDATA().getPOSTNUMBER());
        et_address.setText(iWantBean.getDATA().getADDRESS());
        et_title.setText(iWantBean.getDATA().getTITLE());
        et_content.setText(iWantBean.getDATA().getQUESTION());
        et_dep.setText(iWantBean.getDATA().getQUSTIONTYPE());
        et_reverter.setText(iWantBean.getDATA().getREVERTER());
        et_reverter_content.setText(iWantBean.getDATA().getREVERTCONTENT());
//        if (iWantBean.getDATA().getISOPEN().equals("0")) {
//            rb_yes.setChecked(true);
//            rb_no.setChecked(false);
//        } else {
//            rb_yes.setChecked(false);
//            rb_no.setChecked(true);
//        }

    }

    private void setUIUnClickable() {
        et_name.setClickable(false);
        et_name.setFocusable(false);

        et_tel.setClickable(false);
        et_tel.setFocusable(false);
        et_phone.setClickable(false);
        et_phone.setFocusable(false);

        cb_short_msg.setClickable(false);
        cb_short_msg.setFocusable(false);
        cb_mail.setClickable(false);
        cb_mail.setFocusable(false);
        et_email.setClickable(false);
        et_email.setFocusable(false);
        et_postal_code.setClickable(false);
        et_postal_code.setFocusable(false);
        et_address.setClickable(false);
        et_address.setFocusable(false);
        et_title.setClickable(false);
        et_title.setFocusable(false);
        et_content.setClickable(false);
        et_content.setFocusable(false);
        spinner_dep.setClickable(false);
        spinner_dep.setFocusable(false);
        et_dep.setClickable(false);
        et_dep.setFocusable(false);
        rb_no.setClickable(false);
        rb_no.setFocusable(false);
        rb_yes.setClickable(false);
        rb_yes.setFocusable(false);

        et_reverter.setClickable(false);
        et_reverter.setFocusable(false);

        et_reverter_content.setClickable(false);
        et_reverter_content.setFocusable(false);
    }

    private void initView() {
        tv_dep_name = (TextView) findViewById(R.id.tv_dep_name);
        //根据iwant_type判断显示内容
        if (Constants.LETTER_TYPE.equals(iwant_type)) {
            initTitleBar(R.string.mayoral_mailbox, this, null);
            tv_dep_name.setText(R.string.declare_department);
        } else if (Constants.CONSULTING_TYPE.equals(iwant_type)) {
            initTitleBar(R.string.title_activity_iwant_consulting, this, null);
            tv_dep_name.setText(R.string.consulting_department);
        } else if (Constants.SUGGEST_TYPE.equals(iwant_type)) {
            initTitleBar(R.string.title_activity_iwant_suggest, this, null);
            tv_dep_name.setText(R.string.suggest_department);
        } else if (Constants.COMPLAIN_TYPE.equals(iwant_type)) {
            initTitleBar(R.string.title_activity_iwant_complaint, this, null);
            tv_dep_name.setText(R.string.complain_department);
        }
        spinner_dep = (Spinner) findViewById(R.id.spinner_dep);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_top_right.setVisibility(View.VISIBLE);
        tv_top_right.setText(R.string.save);
        tv_top_right.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_tel = (EditText) findViewById(R.id.et_tel);
        et_phone = (EditText) findViewById(R.id.et_phone);
        cb_short_msg = (CheckBox) findViewById(R.id.cb_short_msg);
        cb_mail = (CheckBox) findViewById(R.id.cb_mail);
        et_email = (EditText) findViewById(R.id.et_email);
        et_postal_code = (EditText) findViewById(R.id.et_postal_code);
        et_address = (EditText) findViewById(R.id.et_address);
        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        spinner_dep = (Spinner) findViewById(R.id.spinner_dep);
        et_dep = (EditText) findViewById(R.id.et_dep);
        rg_gender = (RadioGroup) findViewById(R.id.gender);
        rb_no = (RadioButton) findViewById(R.id.cb_no);
        rb_yes = (RadioButton) findViewById(R.id.cb_yes);
        rb_no.setChecked(true);
        rg_gender.setTag(rb_no.getTag().toString());

        et_reverter = (EditText)findViewById(R.id.et_reverter);
        et_reverter_content = (EditText)findViewById(R.id.et_reverter_content);

        ll_cb = (LinearLayout)findViewById(R.id.ll_cb);
        ll_rg = (LinearLayout)findViewById(R.id.ll_rg);
        ll_tel = (LinearLayout)findViewById(R.id.ll_tel);
        ll_email = (LinearLayout)findViewById(R.id.ll_email);
        ll_postal_code = (LinearLayout)findViewById(R.id.ll_postal_code);
        ll_reverter = (LinearLayout)findViewById(R.id.ll_reverter);
        ll_reverter_content = (LinearLayout)findViewById(R.id.ll_reverter_content);

        if(isShowDetail){
            ll_cb.setVisibility(View.GONE);
            ll_rg.setVisibility(View.GONE);
            ll_tel.setVisibility(View.GONE);
            ll_email.setVisibility(View.GONE);
            ll_postal_code.setVisibility(View.GONE);
            ll_reverter.setVisibility(View.VISIBLE);
            ll_reverter_content.setVisibility(View.VISIBLE);
        }

        if (isShowDetail) {
            tv_top_right.setVisibility(View.GONE);
            et_dep.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        if (BeanUtils.isEmptyStr(dep_id)) {
            getServiceOrItems();
            spinner_dep.setVisibility(View.VISIBLE);
        } else {
            et_dep.setVisibility(View.VISIBLE);
            et_dep.setText(dep_name);
        }
        cb_mail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_email.setHint(getResources().getString(R.string.online_declare_hint_must));
                } else {
                    et_email.setHint("");
                }
            }
        });
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rb_no.getId()) {
                    rg_gender.setTag(rb_no.getTag().toString());
                } else if (checkedId == rb_yes.getId()) {
                    rg_gender.setTag(rb_yes.getTag().toString());
                }

            }
        });
    }

    /**
     * 获取部门列表
     */
    private void getServiceOrItems() {
        if (null != invoke)
            invoke.invokeWidthDialog(OAInterface.getSiteAndItemList(""), callBack, GETSITELIST);
    }

    /**
     * 咨询，建议，投诉接口
     */
    private void iWantSuggestion(XmlInfo xmlInfo) {
        ApiRequest request = OAInterface.iWant(iwant_type, xmlInfo);
        if (null != invoke)
            invoke.invokeWidthDialog(request, callBack, IWANT);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (IWANT == what) {
                    DialogUtils.showToast(IWantSuggestActivity.this, message);
                    IWantSuggestActivity.this.finish();
                } else if (GETSITELIST == what) {
                    List<ResultItem> data = item.getItems("DATA");
                    showDepartmentList(data);
                } else if (GET_SHOW_DATA == what) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    IWantBean iWantBean = gson.fromJson(jsonStr, IWantBean.class);
                    showData(iWantBean);
                }
            } else {
                DialogUtils.showToast(IWantSuggestActivity.this, message);
            }
        }

    };

    /**
     * 展示部门列表
     *
     * @param items
     */
    protected void showDepartmentList(List<ResultItem> items) {
        final List<WorkInfo> departments = new ArrayList<WorkInfo>();
        List<String> departNames = new ArrayList<String>();
        for (ResultItem resultItem : items) {
            String siteName = resultItem.getString("SITE_NAME");
            String siteNo = resultItem.getString("SITE_NO");
            WorkInfo workInfo = new WorkInfo();
            workInfo.setSite_name(siteName);
            workInfo.setSite_no(siteNo);
            departNames.add(siteName);
            departments.add(workInfo);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_dep.setAdapter(arrayAdapter);
        spinner_dep.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                dep_name = departments.get(position).getSite_name();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean setReturnMsg(String msg) {
        DialogUtils.showToast(IWantSuggestActivity.this, msg);
        return false;
    }

    private boolean validate() {
        if (BeanUtils.isEmptyStr(info.getUserName())) {
            return setReturnMsg(getResources().getString(R.string.name_can_not_be_empty));
        } else if (!BeanUtils.isMobileNO(info.getPhone())) {
            return setReturnMsg(getResources().getString(R.string.phone_can_not_be_empty));
        } else if (info.isMail() && !BeanUtils.isEmail(info.getEmail())) {
            return setReturnMsg(getResources().getString(R.string.Email_can_not_be_empty));
        } else if (!BeanUtils.isEmptyStr(info.getPostNumber()) && !BeanUtils.isPostCode(info.getPostNumber())) {
            return setReturnMsg(getResources().getString(R.string.post_code_can_not_be_empty));
        } else if (BeanUtils.isEmptyStr(info.getTitle())) {
            return setReturnMsg(getResources().getString(R.string.title_can_not_be_empty));
        } else if (BeanUtils.isEmptyStr(info.getContent())) {
            return setReturnMsg(getResources().getString(R.string.content_can_not_be_empty));
        }
        return true;
    }

    /**
     * 设置信息
     */
    private void setSuggestionInfo() {
        if (null == info) {
            info = new XmlInfo();
        }
        String name = et_name.getText().toString();
        String tel = et_tel.getText().toString();
        String phone = et_phone.getText().toString();
        String email = et_email.getText().toString();
        String postalCode = et_postal_code.getText().toString();
        String address = et_address.getText().toString();
        String title = et_title.getText().toString();
        String content = et_content.getText().toString();

        info.setLoginTime(DateUtils.getDateStr(new Date()));
        info.setUserName(name);
        info.setPhoneNumber(tel);
        info.setPhone(phone);
        info.setNote(cb_short_msg.isChecked());
        info.setMail(cb_mail.isChecked());
        info.setEmail(email);
        info.setPostNumber(postalCode);
        info.setAddress(address);
        info.setTitle(title);
//        info.setLocal("");
//		for (int i = 0; i < rg_gender.getChildCount(); i++) {
//			RadioButton rb = (RadioButton) rg_gender.getChildAt(i);
//			if (rb.isChecked()) {
//				info.setIsOpen(rb.getText().toString());
//			}
//		}
        info.setIsOpen(rg_gender.getTag().toString());
        info.setContent(content);
        info.setDept(dep_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.tv_top_right:
                setSuggestionInfo();
                if (!validate()) {
                    return;
                }
                iWantSuggestion(info);
                break;

            default:
                break;
        }
    }

    public void getShowData() {
        if (null != invoke)
            invoke.invokeWidthDialog(OAInterface.getIWantInfo(iwant_type, id), callBack, GET_SHOW_DATA);
    }
}
