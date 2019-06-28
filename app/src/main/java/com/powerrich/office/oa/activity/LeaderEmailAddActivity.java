package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.WorkInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：LeaderEmailAddActivity
 */

public class LeaderEmailAddActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_title;
    private EditText et_content;
    private EditText et_dep;
    private Spinner spinner_dep;
    private String dep_name;
    private static final int GETSITELIST = 111;
    private static final int SAVE_DATA = 112;
    private TextView tv_top_right;
    private String dep_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_leader_email_add;
    }

    private void initData() {
        getServiceOrItems();
        spinner_dep.setVisibility(View.VISIBLE);
    }

    private void initView() {
        initTitleBar(R.string.consulting_lead_mail, this, null);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_top_right.setVisibility(View.VISIBLE);
        tv_top_right.setText(R.string.save);
        tv_top_right.setOnClickListener(this);
        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        et_dep = (EditText) findViewById(R.id.et_dep);
        spinner_dep = (Spinner) findViewById(R.id.spinner_dep);
    }

    /**
     * 获取部门列表
     */
    private void getServiceOrItems() {
        if (null != invoke)
            invoke.invokeWidthDialog(OAInterface.getSiteAndItemList(""), callBack, GETSITELIST);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (SAVE_DATA == what) {
                    DialogUtils.showToast(LeaderEmailAddActivity.this, message);
                    LeaderEmailAddActivity.this.finish();
                } else if (GETSITELIST == what) {
                    List<ResultItem> data = item.getItems("DATA");
                    showDepartmentList(data);
                }
            } else {
                DialogUtils.showToast(LeaderEmailAddActivity.this, message);
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
        spinner_dep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                dep_name = departments.get(position).getSite_name();
                dep_id = departments.get(position).getSite_no();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                LeaderEmailAddActivity.this.finish();
                break;
            case R.id.tv_top_right:
                //保存
                if (checkData()) {
                    saveData();
                }
                break;
            default:
                break;
        }
    }

    private void saveData() {
        if (null != invoke)
            invoke.invokeWidthDialog(OAInterface.saveLeadermail(
                    et_title.getText().toString().trim(),
                    et_content.getText().toString().trim(),
                    dep_name,
                    dep_id
            ), callBack, SAVE_DATA);
    }


    private boolean setReturnMsg(String msg) {
        DialogUtils.showToast(LeaderEmailAddActivity.this, msg);
        return false;
    }

    private boolean checkData() {
        if (BeanUtils.isEmptyStr(et_title.getText().toString().trim())) {
            return setReturnMsg(getResources().getString(R.string.title_can_not_be_empty));
        } else if (BeanUtils.isEmptyStr(et_content.getText().toString().trim())) {
            return setReturnMsg(getResources().getString(R.string.content_can_not_be_empty));
        }
        return true;
    }
}
