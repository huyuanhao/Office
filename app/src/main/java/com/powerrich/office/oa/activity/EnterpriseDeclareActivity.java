package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.AddressInfo;
import com.powerrich.office.oa.bean.DeclareInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：EnterpriseDeclareActivity
 * 描   述：企业办事在线申报界面
 * 作   者：Wangzheng
 * 时   间：2017/11/27
 * 版   权：v1.0
 */
public class EnterpriseDeclareActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_USER_ITEM_CODE = 0;
    private static final int SAVE_ITEM_INFO_CODE = 1;
    private static final int SUBMIT_ITEM_AUDIT_REQ = 2;
    private static final int GET_USER_BASE_INFO_REQ = 3;
    private Button btn_next;
    private DeclareInfo declareInfo;
    private String item_id;
    private String itemName;
    private TextView tv_project_name, tv_enterprise_name, tv_legal_representative, tv_credit_code;
    private EditText et_contact_name, et_mobile_phone, et_id_number, et_postal_address;
    private TextView tv_express_ways;
    private String userId;
    private String itemId;
    private String itemCode;
    private String expressId;
    private String address;
    private String dynamicForm;
    private String fileData;
    private Spinner spinner_address;
    private LinearLayout ll_communication_address, ll_post_address;
    private TextView tv_add_address;
    private List<AddressInfo> addressInfoList = new ArrayList<>();
    private String proKeyId;
    private boolean flag = false;
    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userType = LoginUtils.getInstance().getUserInfo().getUserType();
        String sxlx = getIntent().getStringExtra("sxlx");
        if (null != LoginUtils.getInstance().getUserInfo().getDATA()) {
            if ("0".equals(LoginUtils.getInstance().getUserInfo().getDATA().getSFSMRZ())) {
                if (!"1".equals(LoginUtils.getInstance().getUserInfo().getDATA().getAUDIT_STATE())) {
                    DialogUtils.createAuthDialog(EnterpriseDeclareActivity.this,Integer.parseInt(LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY()),null);
                    return;
                }
            }
        }
        if (!BeanUtils.isEmptyStr(userType)) {
            if (!BeanUtils.isEmptyStr(sxlx) && ("1".equals(userType) && !sxlx.contains("1"))) {
                DialogUtils.showToast(EnterpriseDeclareActivity.this, "您是企业用户，当前事项属于个人办理事项！");
                finish();
                return;
            }
            if (userType.equals("0")) {
                DialogUtils.showToast(EnterpriseDeclareActivity.this, "您是个人用户，请办理个人事项！");
                finish();
                return;
            }
        }
        position = getIntent().getStringExtra("position");
        proKeyId = getIntent().getStringExtra("proKeyId");
        item_id = getIntent().getStringExtra("item_id");
        dynamicForm = getIntent().getStringExtra("dynamicForm");
        fileData = getIntent().getStringExtra("fileData");
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_enterprise_declare;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!flag) {
            if (!BeanUtils.isEmpty(addressInfoList)) {
                addressInfoList.clear();
            }
            if (LoginUtils.getInstance().isLoginSuccess()) {
                if ("1".equals(position) || "2".equals(position)) {
                    getUserBaseInfo();
                } else {
                    getUserItem();
                }
            }

        }
    }

    private void initView() {
        initTitleBar(R.string.enterprise_declare, this, null);
        tv_project_name = (TextView) findViewById(R.id.tv_project_name);
        tv_enterprise_name = (TextView) findViewById(R.id.tv_enterprise_name);
        tv_legal_representative = (TextView) findViewById(R.id.tv_legal_representative);
        tv_credit_code = (TextView) findViewById(R.id.tv_credit_code);
        et_contact_name = (EditText) findViewById(R.id.et_contact_name);
        et_mobile_phone = (EditText) findViewById(R.id.et_mobile_phone);
        et_id_number = (EditText) findViewById(R.id.et_id_number);
        et_postal_address = (EditText) findViewById(R.id.et_postal_address);
        tv_express_ways = (TextView) findViewById(R.id.tv_express_ways);
        btn_next = (Button) findViewById(R.id.btn_next);
        spinner_address = (Spinner) findViewById(R.id.spinner_address);
        ll_communication_address = (LinearLayout) findViewById(R.id.ll_communication_address);
        ll_post_address = (LinearLayout) findViewById(R.id.ll_post_address);
        tv_add_address = (TextView) findViewById(R.id.tv_add_address);
        tv_add_address.setOnClickListener(this);
    }

    private void initData() {
        tv_express_ways.setText(BeanUtils.getExpressWays(this, (String) tv_express_ways.getTag()));
        tv_express_ways.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    /**
     * 查询当前办理人的【基本信息】请求
     */
    private void getUserItem() {
        ApiRequest request = OAInterface.getUserOrItem(item_id, "");
        invoke.invokeWidthDialog(request, callBack, GET_USER_ITEM_CODE);
    }

    /**
     * 暂存件-根据业务id获取用户基本信息请求
     */
    private void getUserBaseInfo() {
        ApiRequest request = OAInterface.getUserBaseInfo(proKeyId);
        invoke.invokeWidthDialog(request, callBack, GET_USER_BASE_INFO_REQ);
    }

    /**
     * 保存事项申报基本信息请求
     */
    private void saveItemInfo(DeclareInfo declareInfo) {
        ApiRequest request = OAInterface.saveItemInfo(declareInfo);
        invoke.invokeWidthDialog(request, callBack, SAVE_ITEM_INFO_CODE);
    }

    /**
     * 网上办事-事项申报提交审核请求
     */
    private void submitItemAudit(String proKeyId) {
        ApiRequest request = OAInterface.submitItemAudit(itemId, proKeyId);
        invoke.invokeWidthDialog(request, callBack, SUBMIT_ITEM_AUDIT_REQ);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == GET_USER_ITEM_CODE) {
                    ResultItem result = (ResultItem) item.get("INFO_DATA");
                    dynamicForm = item.getString("DYNAMICFORM");
                    fileData = item.getString("FILEDATA");
                    if ("0".equals(dynamicForm) &&  "0".equals(fileData)) {
                        btn_next.setText(R.string.submit);
                    }
                    parseData(result);
                    List<ResultItem> data = item.getItems("ADR_DATA");
                    showAddressData(data);
                } else if (what == SAVE_ITEM_INFO_CODE) {
                    flag = true;
                    ResultItem result = (ResultItem) item.get("DATA");
                    proKeyId = result.getString("PROKEY");
                    if ("1".equals(dynamicForm)) {
                        Intent intent = new Intent(EnterpriseDeclareActivity.this, BaseInformationActivity.class);
                        intent.putExtra("itemId", itemId);
                        intent.putExtra("itemName", itemName);
                        intent.putExtra("proKeyId", proKeyId);
                        intent.putExtra("fileData", fileData);
                        startActivity(intent);
                    } else if ("1".equals(fileData)) {
                        Intent intent = new Intent(EnterpriseDeclareActivity.this, RelativeMaterialsActivity.class);
                        intent.putExtra("itemId", itemId);
                        intent.putExtra("itemName", itemName);
                        intent.putExtra("proKeyId", proKeyId);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                    // 既没有动态表单又没有材料附件就直接调事项提交审核请求接口
                    if ("0".equals(dynamicForm) &&  "0".equals(fileData)) {
                        submitItemAudit(proKeyId);
                    }
                } else if (what == GET_USER_BASE_INFO_REQ) {
                    List<ResultItem> addressData = item.getItems("ADDRDATA");
                    showAddressData(addressData);
                    ResultItem result = (ResultItem) item.get("BASEDATA");
                    parseBaseData(result);
                } else if (what == SUBMIT_ITEM_AUDIT_REQ) {
                    ResultItem data = (ResultItem) item.get("DATA");
                    String trackId = data.getString("TRACKID");
                    if ("0".equals(dynamicForm) && "0".equals(fileData)) {
                        Intent intent = new Intent(EnterpriseDeclareActivity.this, MaterialSendActivity.class);
                        intent.putExtra("itemName", itemName);
                        intent.putExtra("proKeyId", proKeyId);
                        intent.putExtra("trackId", trackId);
                        startActivity(intent);
                    }
                }
            } else {
                DialogUtils.showToast(EnterpriseDeclareActivity.this, message);
            }
        }

    };

    private void parseData(ResultItem result) {
        if (result == null) {
            return;
        }
        userId = result.getString("USERID");
        itemId = result.getString("ITEMID");
        itemCode = result.getString("ITEMSXBM");
        itemName = result.getString("ITEMNAME");
        String enterpriseName = result.getString("COMPANYNAME");
        String legalRepresentative = result.getString("FRDB");
        String creditCode = result.getString("BUSINESSLICENCE");
        String contactName = result.getString("LINKMAN");
        String mobilePhone = result.getString("MOBILE_NUM");
        String idNumber = result.getString("IDCARD");
        String address = result.getString("QYTXDZ");
        tv_project_name.setText(itemName);
        tv_enterprise_name.setText(enterpriseName);
        tv_legal_representative.setText(legalRepresentative);
        tv_credit_code.setText(creditCode);
        et_contact_name.setText(contactName);
        et_mobile_phone.setText(mobilePhone);
        et_id_number.setText(idNumber);
        et_postal_address.setText(address);
    }

    private void parseBaseData(ResultItem result) {
        if (result == null) {
            return;
        }
        userId = result.getString("USERID");
        itemId = result.getString("ITEMID");
        itemCode = result.getString("ITEMSXBM");
        itemName = result.getString("REGISTER_REMARK");
        String enterpriseName = result.getString("NAME");
        String legalRepresentative = result.getString("ENTERPRISE_ARTIFICIAL_PERSON");
        String creditCode = result.getString("ZJHM");
        String contactName = result.getString("PERSON_NAME");
        String mobilePhone = result.getString("PERSON_PHONE");
        String idNumber = result.getString("PERSON_ID");
        String address = result.getString("PERSON_ADDRESS");
        String getAway = result.getString("GETAWAY");
        if ("2".equals(getAway)) {
            tv_express_ways.setText(BeanUtils.getExpressWays(this, String.valueOf(Integer.parseInt(getAway) - 2)));
            tv_express_ways.setTag(String.valueOf(Integer.parseInt(getAway) - 2));
        } else {
            tv_express_ways.setText(BeanUtils.isNullOrEmpty(getAway) ? BeanUtils.getExpressWays(this, "0") : BeanUtils.getExpressWays(this, getAway));
            tv_express_ways.setTag(BeanUtils.isNullOrEmpty(getAway) ? "0" : getAway);
        }
        tv_project_name.setText(itemName);
        tv_enterprise_name.setText(enterpriseName);
        tv_legal_representative.setText(legalRepresentative);
        tv_credit_code.setText(creditCode);
        et_contact_name.setText(contactName);
        et_mobile_phone.setText(mobilePhone);
        et_id_number.setText(idNumber);
        et_postal_address.setText(address);
        if ("1".equals(tv_express_ways.getTag().toString())) {
            ll_communication_address.setVisibility(View.GONE);
            ll_post_address.setVisibility(View.VISIBLE);
        } else {
            ll_post_address.setVisibility(View.GONE);
        }
        spinner_address.setVisibility(View.VISIBLE);
        tv_add_address.setVisibility(View.GONE);
    }

    private void showAddressData(List<ResultItem> data) {
        if (BeanUtils.isEmpty(data)) {
            ll_post_address.setVisibility(View.GONE);
            spinner_address.setVisibility(View.GONE);
            tv_add_address.setVisibility(View.VISIBLE);
            return;
        }
        for (ResultItem resultItem : data) {
            AddressInfo addressInfo = new AddressInfo();
            addressInfo.setAddress(resultItem.getString("ADDRESS"));
            addressInfo.setCompanyName(resultItem.getString("COMPANY_NAME"));
            addressInfo.setHandSet(resultItem.getString("HANDSET"));
            addressInfo.setId(resultItem.getString("ID"));
            addressInfo.setIsDefault(resultItem.getString("ISDEFAULT"));
            addressInfo.setAddressee(resultItem.getString("SJRXM"));
            addressInfo.setTelNo(resultItem.getString("TEL_NO"));
            addressInfo.setUserId(resultItem.getString("USERID"));
            addressInfo.setPostalCode(resultItem.getString("YZBM"));
            addressInfoList.add(addressInfo);
        }
        if (BeanUtils.isEmpty(addressInfoList)) {
            ll_post_address.setVisibility(View.GONE);
            spinner_address.setVisibility(View.GONE);
            tv_add_address.setVisibility(View.VISIBLE);
        } else {
            if ("0".equals(tv_express_ways.getTag().toString())) {
                ll_post_address.setVisibility(View.GONE);
            } else {
                ll_post_address.setVisibility(View.VISIBLE);
            }
            spinner_address.setVisibility(View.VISIBLE);
            tv_add_address.setVisibility(View.GONE);
        }
        CommonAdapter<AddressInfo> depAdapter = new CommonAdapter<AddressInfo>(this, addressInfoList, R.layout.spinner_dep_items) {
            @Override
            public void convert(ViewHolder holder, AddressInfo item) {
                holder.setTextView(R.id.name, item.getAddress());
            }
        };
        spinner_address.setAdapter(depAdapter);

        spinner_address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expressId = addressInfoList.get(position).getId();
                address = addressInfoList.get(position).getAddress();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 校验信息
     * @return
     */
    private boolean validate() {
        if (BeanUtils.isEmptyStr(declareInfo.getUserName())) {
            DialogUtils.showToast(EnterpriseDeclareActivity.this, "联系人不能为空");
            return false;
        }
        if (BeanUtils.isEmptyStr(declareInfo.getPhone())) {
            DialogUtils.showToast(EnterpriseDeclareActivity.this, "手机号码不能为空");
            return false;
        } else if (!BeanUtils.isMobileNO(declareInfo.getPhone())) {
            DialogUtils.showToast(EnterpriseDeclareActivity.this, "手机号格式不正确");
            return false;
        }
        if (BeanUtils.isEmptyStr(declareInfo.getIdCard())) {
            DialogUtils.showToast(EnterpriseDeclareActivity.this, "身份证号码不能为空");
            return false;
        } /*else if (!BeanUtils.validCard(declareInfo.getIdCard())) {
            DialogUtils.showToast(EnterpriseDeclareActivity.this, "身份证号格式不正确");
            return false;
        }*/
        if (BeanUtils.isEmptyStr(declareInfo.getAddress())) {
            DialogUtils.showToast(EnterpriseDeclareActivity.this, "通讯地址不能为空");
            return false;
        }
        return true;
    }

    private void setDeclareInfo() {
        if (null == declareInfo) {
            declareInfo = new DeclareInfo();
        }
        declareInfo.setUserId(userId);
        declareInfo.setItemId(itemId);
        declareInfo.setItemCode(itemCode);
        declareInfo.setItemName(tv_project_name.getText().toString());
        declareInfo.setEnterpriseName(tv_enterprise_name.getText().toString());
        declareInfo.setEnterprisePerson(tv_legal_representative.getText().toString());
        declareInfo.setBusinessLicence(tv_credit_code.getText().toString());
        declareInfo.setUserName(et_contact_name.getText().toString().trim());
        declareInfo.setPhone(et_mobile_phone.getText().toString().trim());
        declareInfo.setIdCard(et_id_number.getText().toString().trim());
        declareInfo.setIsExpress("1".equals(tv_express_ways.getTag().toString()) ? "1" : "2");
        declareInfo.setExpressId("0".equals(tv_express_ways.getTag().toString()) ? "" : expressId);
        declareInfo.setAddress("0".equals(tv_express_ways.getTag().toString()) ? et_postal_address.getText().toString().trim() : address);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                EnterpriseDeclareActivity.this.finish();
                break;
            case R.id.tv_express_ways:
                BeanUtils.showExpressWays(this, tv_express_ways, ll_post_address, ll_communication_address);
                break;
            case R.id.btn_next:
                setDeclareInfo();
                if (!validate()) {
                    return;
                }
                saveItemInfo(declareInfo);
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(this, AddressDetailActivity.class);
                intent.putExtra("type", AddressDetailActivity.TYPE_ADD);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
