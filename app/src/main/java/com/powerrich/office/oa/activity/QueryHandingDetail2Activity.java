package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.DoThingBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;

/**
 * 文件名：QueryHandingDetail2Activity
 * 描述：
 * 作者：白煜
 * 时间：2017/12/7 0007
 * 版权：
 */
public class QueryHandingDetail2Activity extends BaseActivity implements View.OnClickListener {

    private static final int CODE_GET_DETAIL = 663;
    private EditText et_bj_linkman_id_num;
    private EditText et_bj_linkman_id_type;
    private EditText et_bj_apply_person_name;
    private EditText et_bj_apply_time;
    private EditText et_bj_event_name;
    private EditText et_bj_event_num;
    private EditText et_bj_link_address;
    private EditText et_bj_linkman_mobile_phone;
    private EditText et_bj_object_name;
    private EditText et_bj_object_num;

    private String proKeyId;
    private boolean zan_cun;
    private TextView tv_see_form;
    private TextView tv_see_file;
    private TextView tv_continue;
    private TextView tv_redo;
    private View v_continue;
    private View v_redo;
    private DoThingBean mDoThingBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proKeyId = getIntent().getStringExtra("proKeyId");
        zan_cun = getIntent().getBooleanExtra("zan_cun", false);
        initView();
        loadData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_query_handing_detail_2;
    }

    private void loadData() {
        ApiRequest request = OAInterface.doFileDetail(proKeyId);
        invoke.invokeWidthDialog(request, callBack, CODE_GET_DETAIL);
    }

    private void initView() {
        initTitleBar(R.string.do_thing_detail, this, null);
        et_bj_linkman_id_num = (EditText) findViewById(R.id.et_bj_linkman_id_num);
        et_bj_linkman_id_type = (EditText) findViewById(R.id.et_bj_linkman_id_type);
        et_bj_apply_person_name = (EditText) findViewById(R.id.et_bj_apply_person_name);
        et_bj_apply_time = (EditText) findViewById(R.id.et_bj_apply_time);
        et_bj_event_name = (EditText) findViewById(R.id.et_bj_event_name);
        et_bj_event_num = (EditText) findViewById(R.id.et_bj_event_num);
        et_bj_link_address = (EditText) findViewById(R.id.et_bj_link_address);
        et_bj_linkman_mobile_phone = (EditText) findViewById(R.id.et_bj_linkman_mobile_phone);
        et_bj_object_name = (EditText) findViewById(R.id.et_bj_object_name);
        et_bj_object_num = (EditText) findViewById(R.id.et_bj_object_num);
        tv_see_form = (TextView) findViewById(R.id.tv_see_form);
        tv_see_file = (TextView) findViewById(R.id.tv_see_file);
        tv_continue = (TextView) findViewById(R.id.tv_continue);
        tv_redo = (TextView) findViewById(R.id.tv_redo);
        v_continue = findViewById(R.id.v_continue);
        v_redo = findViewById(R.id.v_redo);
        if (!zan_cun) {
            tv_continue.setVisibility(View.GONE);
            tv_redo.setVisibility(View.GONE);
            v_continue.setVisibility(View.GONE);
            v_redo.setVisibility(View.GONE);
        }

        tv_continue.setOnClickListener(this);
        tv_redo.setOnClickListener(this);
        tv_see_form.setOnClickListener(this);
        tv_see_file.setOnClickListener(this);
        setEnabled();
    }

    private void setEnabled() {
        et_bj_linkman_id_num.setEnabled(false);
        et_bj_linkman_id_num.setFocusable(false);
        et_bj_linkman_id_type.setEnabled(false);
        et_bj_linkman_id_type.setFocusable(false);
        et_bj_apply_person_name.setEnabled(false);
        et_bj_apply_person_name.setSingleLine(false);
        et_bj_apply_person_name.setFocusable(false);
        et_bj_apply_time.setEnabled(false);
        et_bj_apply_time.setFocusable(false);
        et_bj_event_name.setEnabled(false);
        et_bj_event_name.setSingleLine(false);
        et_bj_event_name.setFocusable(false);
        et_bj_event_num.setEnabled(false);
        et_bj_event_num.setFocusable(false);
        et_bj_link_address.setEnabled(false);
        et_bj_link_address.setFocusable(false);
        et_bj_linkman_mobile_phone.setEnabled(false);
        et_bj_linkman_mobile_phone.setFocusable(false);
        et_bj_object_name.setEnabled(false);
        et_bj_object_name.setSingleLine(false);
        et_bj_object_name.setFocusable(false);
        et_bj_object_num.setEnabled(false);
        et_bj_object_num.setFocusable(false);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (CODE_GET_DETAIL == what) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    mDoThingBean = gson.fromJson(jsonStr, DoThingBean.class);
                    showData(mDoThingBean);
                }
            } else {

                DialogUtils.showToast(QueryHandingDetail2Activity.this, message);
            }
        }

    };

    private void showData(DoThingBean doThingBean) {
        et_bj_linkman_id_num.setText(doThingBean.getDATA().getPERSON_ID());
        if ("0".equals(doThingBean.getDATA().getPERSON_TYPE())) {
            et_bj_linkman_id_type.setText(getString(R.string.id_card));
        }
        et_bj_apply_person_name.setText(doThingBean.getDATA().getNAME());
        if (doThingBean.getDATA().getSQSJ().contains(".")) {
            String[] split = doThingBean.getDATA().getSQSJ().split("\\.");
            et_bj_apply_time.setText(split[0]);
        } else {
            et_bj_apply_time.setText(doThingBean.getDATA().getSQSJ());
        }
        et_bj_event_name.setText(doThingBean.getDATA().getSXMC());
        et_bj_event_num.setText(doThingBean.getDATA().getSXBM());
        et_bj_link_address.setText(doThingBean.getDATA().getPERSON_ADDRESS());
        et_bj_linkman_mobile_phone.setText(doThingBean.getDATA().getPERSON_PHONE());
        et_bj_object_name.setText(doThingBean.getDATA().getREGISTER_REMARK());
        et_bj_object_num.setText(doThingBean.getDATA().getTRACKINGNUMBER());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                QueryHandingDetail2Activity.this.finish();
                break;
            case R.id.tv_see_file:

                break;
            case R.id.tv_see_form:

                break;
            case R.id.tv_continue:
                String userType = LoginUtils.getInstance().getUserInfo().getUserType();
                if ("1".equals(mDoThingBean.getDATA().getPOSITION()) || "2".equals(mDoThingBean.getDATA().getPOSITION())) {
                    if ("1".equals(userType)) {
                        Intent intent = new Intent(QueryHandingDetail2Activity.this, EnterpriseDeclareActivity.class);
                        intent.putExtra("itemId", mDoThingBean.getDATA().getSXID());
                        intent.putExtra("itemName", mDoThingBean.getDATA().getREGISTER_REMARK());
                        intent.putExtra("proKeyId", mDoThingBean.getDATA().getPROKEYID());
                        intent.putExtra("dynamicForm", mDoThingBean.getDATA().getDYNAMICFORM());
                        intent.putExtra("fileData", mDoThingBean.getDATA().getFILEDATA());
                        intent.putExtra("position", mDoThingBean.getDATA().getPOSITION());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(QueryHandingDetail2Activity.this, OnlineDeclareActivity.class);
                        intent.putExtra("itemId", mDoThingBean.getDATA().getSXID());
                        intent.putExtra("itemName", mDoThingBean.getDATA().getREGISTER_REMARK());
                        intent.putExtra("proKeyId", mDoThingBean.getDATA().getPROKEYID());
                        intent.putExtra("dynamicForm", mDoThingBean.getDATA().getDYNAMICFORM());
                        intent.putExtra("fileData", mDoThingBean.getDATA().getFILEDATA());
                        intent.putExtra("position", mDoThingBean.getDATA().getPOSITION());
                        startActivity(intent);
                    }
                } else if ("3".equals(mDoThingBean.getDATA().getPOSITION())) {
                    Intent intent = new Intent(QueryHandingDetail2Activity.this, BaseInformationActivity.class);
                    intent.putExtra("itemId", mDoThingBean.getDATA().getSXID());
                    intent.putExtra("itemName", mDoThingBean.getDATA().getREGISTER_REMARK());
                    intent.putExtra("proKeyId", mDoThingBean.getDATA().getPROKEYID());
                    intent.putExtra("dynamicForm", mDoThingBean.getDATA().getDYNAMICFORM());
                    intent.putExtra("fileData", mDoThingBean.getDATA().getFILEDATA());
                    intent.putExtra("position", mDoThingBean.getDATA().getPOSITION());
                    startActivity(intent);
                } else if ("4".equals(mDoThingBean.getDATA().getPOSITION())) {
                    Intent intent = new Intent(QueryHandingDetail2Activity.this, RelativeMaterialsActivity.class);
                    intent.putExtra("itemId", mDoThingBean.getDATA().getSXID());
                    intent.putExtra("itemName", mDoThingBean.getDATA().getREGISTER_REMARK());
                    intent.putExtra("proKeyId", mDoThingBean.getDATA().getPROKEYID());
                    intent.putExtra("position", mDoThingBean.getDATA().getPOSITION());
                    startActivity(intent);
                } else {
                    DialogUtils.showToast(QueryHandingDetail2Activity.this,"该办件不能继续办理");
                }
                break;
            case R.id.tv_redo:

                break;
        }
    }
}
