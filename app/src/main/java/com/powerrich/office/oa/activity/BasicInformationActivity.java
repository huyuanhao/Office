package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.AddAddressActivity;
import com.powerrich.office.oa.adapter.AddressAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.DeclareInfo;
import com.powerrich.office.oa.bean.ServiceBranchBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.enums.AddressType;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.StringUtils;
import com.powerrich.office.oa.view.NoScrollListView;
import com.yt.simpleframe.http.bean.entity.AddressInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 文 件 名：BasicInformationActivity
 * 描   述：个人、企业办事基本信息
 * 作   者：Wangzheng
 * 时   间：2018-6-12 10:53:06
 * 版   权：v1.0
 */
public class BasicInformationActivity extends BaseActivity implements View.OnClickListener, AddressAdapter.OnItemClickListener, AddressAdapter.IModifyListener {

    private static final int GET_USER_ITEM_CODE = 0;
    private static final int SAVE_ITEM_INFO_CODE = 1;
    private static final int GET_USER_BASE_INFO_CODE = 2;
    private static final int GET_ADDRESS_CODE = 3;
    private static final int SUBMIT_ITEM_AUDIT_CODE = 4;
    private static final int SELECT_SERVICE_BRANCH_CODE = 10000;
    private ImageView iv_progress_bar;
    private TextView tv_item_name, tv_item_code;
    private LinearLayout ll_personal, ll_enterprise;
    private TextView tv_name, tv_gender, tv_id_number, tv_contact_phone;
    private TextView tv_legal_name, tv_organization_name, tv_social_credit_code, tv_legal_id_number;
    private EditText et_nation, et_phone_number, et_organization_address;
    private EditText et_contact_address, et_mailbox, et_postal_code;
    private RadioGroup rg_agency;
    private RadioButton rb_self, rb_agent;
    private RadioGroup rg_way;
    private LinearLayout ll_branch;
    private TextView tv_select_branch, tv_branch;
    private RadioButton rb_get, rb_mail;
    private NoScrollListView lv_address;
    private TextView tv_add_address;
    private TextView tv_no_data;
    private LinearLayout ll_transact;
    private EditText et_agent_name, et_agent_phone, et_agent_id;
    private Button btn_next;
    private String userId;
    private String type;
    private String itemId, itemName, itemCode;
    private DeclareInfo declareInfo;
    private String position;
    private String companyId;
    private String companyAdress;
    private String dynamicForm, fileData;
    private String proKeyId;
    private String userType;
    private AddressAdapter adapter;
    private List<AddressInfo> addressInfoList = new ArrayList<>();

    private int selected = -1;
    private String branchId;
    private String itemType;
    private int mPosition = 0;
    private String addressId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        itemId = getIntent().getStringExtra("item_id");
        proKeyId = getIntent().getStringExtra("proKeyId");
        dynamicForm = getIntent().getStringExtra("dynamicForm");
        fileData = getIntent().getStringExtra("fileData");
        position = getIntent().getStringExtra("position");
        companyId = getIntent().getStringExtra("companyId");
        companyAdress = getIntent().getStringExtra("companyAdress");
        i("companyId:" + companyId + "-companyAdress:" + companyAdress);
        userType = LoginUtils.getInstance().getUserInfo().getUserType();
        initView();
        initData();
        if ("0".equals(position)) {
            getUserItem(BeanUtils.isEmptyStr(companyId) ? "" : companyId);
        } else {
            if (!BeanUtils.isEmpty(addressInfoList)) {
                addressInfoList.clear();
            }
            getUserBaseInfo();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_basic_information;
    }

    private void initView() {
        initTitleBar(R.string.basic_information, this, null);
        iv_progress_bar = (ImageView) findViewById(R.id.iv_progress_bar);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        tv_item_code = (TextView) findViewById(R.id.tv_item_code);
        ll_personal = (LinearLayout) findViewById(R.id.ll_personal);
        ll_enterprise = (LinearLayout) findViewById(R.id.ll_enterprise);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        et_nation = (EditText) findViewById(R.id.et_nation);
        tv_id_number = (TextView) findViewById(R.id.tv_id_number);
        tv_contact_phone = (TextView) findViewById(R.id.tv_contact_phone);
        et_contact_address = (EditText) findViewById(R.id.et_contact_address);
        tv_legal_name = (TextView) findViewById(R.id.tv_legal_name);
        tv_organization_name = (TextView) findViewById(R.id.tv_organization_name);
        tv_social_credit_code = (TextView) findViewById(R.id.tv_social_credit_code);
        tv_legal_id_number = (TextView) findViewById(R.id.tv_legal_id_number);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_organization_address = (EditText) findViewById(R.id.et_organization_address);
        et_mailbox = (EditText) findViewById(R.id.et_mailbox);
        et_postal_code = (EditText) findViewById(R.id.et_postal_code);
        rg_agency = (RadioGroup) findViewById(R.id.rg_agency);
        rb_self = (RadioButton) findViewById(R.id.rb_self);
        rb_agent = (RadioButton) findViewById(R.id.rb_agent);
        rg_agency.setTag(rb_self.getTag().toString());
        rg_way = (RadioGroup) findViewById(R.id.rg_way);
        rb_get = (RadioButton) findViewById(R.id.rb_get);
        rb_mail = (RadioButton) findViewById(R.id.rb_mail);
        rg_way.setTag(rb_get.getTag().toString());
        ll_branch = (LinearLayout) findViewById(R.id.ll_branch);
        tv_select_branch = (TextView) findViewById(R.id.tv_select_branch);
        tv_branch = (TextView) findViewById(R.id.tv_branch);
        lv_address = (NoScrollListView) findViewById(R.id.lv_address);
        tv_add_address = (TextView) findViewById(R.id.tv_add_address);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        ll_transact = (LinearLayout) findViewById(R.id.ll_transact);
        et_agent_name = (EditText) findViewById(R.id.et_agent_name);
        et_agent_phone = (EditText) findViewById(R.id.et_agent_phone);
        et_agent_id = (EditText) findViewById(R.id.et_agent_id);
        btn_next = (Button) findViewById(R.id.btn_next);
        if (Constants.PERSONAL_WORK_TYPE.equals(type)) {
            ll_personal.setVisibility(View.VISIBLE);
            ll_enterprise.setVisibility(View.GONE);
        } else {
            ll_personal.setVisibility(View.GONE);
            ll_enterprise.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        tv_gender.setText(BeanUtils.getGender(BasicInformationActivity.this, tv_gender.getTag().toString()));
        tv_gender.setOnClickListener(this);
        tv_select_branch.setOnClickListener(this);
        tv_add_address.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        rg_agency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == rb_self.getId()) {
                    rg_agency.setTag(rb_self.getTag().toString());
                    ll_transact.setVisibility(View.GONE);
                } else if (checkedId == rb_agent.getId()) {
                    rg_agency.setTag(rb_agent.getTag().toString());
                    ll_transact.setVisibility(View.VISIBLE);
                }
            }
        });
        rg_way.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == rb_get.getId()) {
                    rg_way.setTag(rb_get.getTag().toString());
                    tv_add_address.setVisibility(View.GONE);
                    lv_address.setVisibility(View.GONE);
                    tv_no_data.setVisibility(View.GONE);
                    ll_branch.setVisibility(View.VISIBLE);
                } else if (checkedId == rb_mail.getId()) {
                    rg_way.setTag(rb_mail.getTag().toString());
                    tv_add_address.setVisibility(View.VISIBLE);
                    lv_address.setVisibility(View.VISIBLE);
                    ll_branch.setVisibility(View.GONE);
                    if (BeanUtils.isEmpty(addressInfoList)) {
                        tv_no_data.setVisibility(View.VISIBLE);
                    } else {
                        tv_no_data.setVisibility(View.GONE);
                    }
                }
            }
        });
    }


    /**
     * 查询当前办理人的【基本信息】请求
     */
    private void getUserItem(String companyId) {
        ApiRequest request = OAInterface.getUserOrItem(itemId, companyId);
        invoke.invokeWidthDialog(request, callBack, GET_USER_ITEM_CODE);
    }

    /**
     * 暂存件-根据业务id获取用户基本信息请求
     */
    private void getUserBaseInfo() {
        ApiRequest request = OAInterface.getUserBaseInfo(proKeyId);
        invoke.invokeWidthDialog(request, callBack, GET_USER_BASE_INFO_CODE);
    }

    /**
     * 获取地址信息请求
     */
    private void getAddress() {
        ApiRequest request = OAInterface.getAddressManager();
        invoke.invoke(request, callBack, GET_ADDRESS_CODE);
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
        invoke.invokeWidthDialog(request, callBack, SUBMIT_ITEM_AUDIT_CODE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

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
                    if ("0".equals(dynamicForm)) {
                        iv_progress_bar.setImageResource(R.drawable.per_circle_two_2);
                    } else {
                        iv_progress_bar.setImageResource(R.drawable.per_circle_two);
                    }
                    parseData(result);
                } else if (what == SAVE_ITEM_INFO_CODE) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    proKeyId = result.getString("PROKEY");
                    if ("1".equals(dynamicForm)) {
                        Intent intent = new Intent(BasicInformationActivity.this, BaseInformationActivity.class);
                        intent.putExtra("itemId", itemId);
                        intent.putExtra("itemName", itemName);
                        intent.putExtra("itemCode", itemCode);
                        intent.putExtra("proKeyId", proKeyId);
                        intent.putExtra("dynamicForm", dynamicForm);
                        intent.putExtra("fileData", fileData);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    } else if ("1".equals(fileData)) {
                        Intent intent = new Intent(BasicInformationActivity.this, DeclareMaterialsActivity.class);
                        intent.putExtra("itemId", itemId);
                        intent.putExtra("itemName", itemName);
                        intent.putExtra("itemCode", itemCode);
                        intent.putExtra("proKeyId", proKeyId);
                        intent.putExtra("dynamicForm", dynamicForm);
                        intent.putExtra("fileData", fileData);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                    // 既没有动态表单又没有材料附件就直接调事项提交审核请求接口
                    if ("0".equals(dynamicForm) && "0".equals(fileData) || (BeanUtils.isNullOrEmpty(dynamicForm) && BeanUtils.isNullOrEmpty(fileData))) {
                        submitItemAudit(proKeyId);
                    }
                } else if (what == GET_USER_BASE_INFO_CODE) {
                    ResultItem result = (ResultItem) item.get("BASEDATA");
                    parseBaseData(result);
                    ResultItem data = (ResultItem) item.get("USERINFO");
                    parseUserInfo(data);
                    ResultItem branchData = (ResultItem) item.get("ZQWD");
                    parseBranchData(branchData);
                    ResultItem chooseData = (ResultItem) item.get("CHOOSEDDATA");
                    addressId = chooseData.getString("ADDRESS_ID");
//                    List<ResultItem> items = item.getItems("ADDRDATA");
//                    showAddressData(items);
                } else if (what == GET_ADDRESS_CODE) {
                    List<ResultItem> items = item.getItems("DATA");
                    showAddressData(items);
                } else if (what == SUBMIT_ITEM_AUDIT_CODE) {
                    ResultItem data = (ResultItem) item.get("DATA");
                    String trackId = data.getString("TRACKID");
                    if ("0".equals(dynamicForm) && "0".equals(fileData) || (BeanUtils.isNullOrEmpty(dynamicForm) && BeanUtils.isNullOrEmpty(fileData))) {
                        Intent intent = new Intent(BasicInformationActivity.this, DeclareNotifyActivity.class);
                        intent.putExtra("itemName", itemName);
                        intent.putExtra("itemCode", itemCode);
                        intent.putExtra("proKeyId", proKeyId);
                        intent.putExtra("trackId", trackId);
                        startActivity(intent);
                    }
                }
            } else {
                DialogUtils.showToast(BasicInformationActivity.this, message);
            }
        }

    };

    private void parseData(ResultItem result) {
        if (result == null) {
            return;
        }
        userId = result.getString("USERID");
        itemId = result.getString("ITEMID");
        itemName = result.getString("ITEMNAME");
        itemCode = result.getString("SXBM");
        String name = result.getString("REALNAME");
        String gender = result.getString("SEX");
        String nation = result.getString("MZ");
        String address = result.getString("QYTXDZ");
        String personAddress = result.getString("UNITADD");
        String idNumber = result.getString("IDCARD");
        String phone = result.getString("MOBILE_NUM");
        String legalName = result.getString("FRDB");
        String organizationName = result.getString("COMPANYNAME");
        String socialCreditCode = result.getString("BUSINESSLICENCE");
        tv_item_name.setText(itemName);
        tv_item_code.setText(itemCode);
        i(this + "type:" + type);
        if (Constants.PERSONAL_WORK_TYPE.equals(type)) {
            if ("0".equals(userType)) {
                tv_name.setText(name);
                et_contact_address.setText(personAddress);
            } else {
                tv_name.setText(legalName);
                et_contact_address.setText(address);
            }
            tv_gender.setText(BeanUtils.getGender(BasicInformationActivity.this, BeanUtils.isEmptyStr(gender) ? StringUtils.getGenderByIdCard(idNumber) : gender));
            tv_gender.setTag(BeanUtils.isEmptyStr(gender) ? StringUtils.getGenderByIdCard(idNumber) : gender);
            et_nation.setText(nation);
            tv_id_number.setText(idNumber);
            tv_contact_phone.setText(phone);
        } else {
            tv_legal_name.setText(BeanUtils.isEmptyStr(companyId) ? legalName : result.getString("BLR_FRDB"));
            tv_organization_name.setText(BeanUtils.isEmptyStr(companyId) ? organizationName : result.getString("BLR_COMPANYNAME"));
            tv_social_credit_code.setText(BeanUtils.isEmptyStr(companyId) ? socialCreditCode : result.getString("BLR_BUSINESSLICENCE"));
            et_phone_number.setText(BeanUtils.isEmptyStr(companyId) ? phone : result.getString("BLR_FR_PHONE_NUM"));
            tv_legal_id_number.setText(BeanUtils.isEmptyStr(companyId) ? idNumber : result.getString("BLR_FRDB_SFZHM"));
              et_organization_address.setText(address);
//            et_organization_address.setText(companyAdress);
        }
        if (!BeanUtils.isEmpty(addressInfoList)) {
            addressInfoList.clear();
        }
        getAddress();
    }

    private void parseBaseData(ResultItem result) {
        if (result == null) {
            return;
        }
        userId = result.getString("USERID");
        itemId = result.getString("ITEMID");
        itemCode = result.getString("ITEMSXBM");
        itemName = result.getString("REGISTER_REMARK");
        String name = result.getString("NAME");
        String phone = result.getString("DECLAREUSERPHONE");
        String personId = result.getString("PERSON_ID");
        String personName = result.getString("PERSON_NAME");
        String personPhone = result.getString("PERSON_PHONE");
        String idNumber = result.getString("ZJHM");
        String getAway = result.getString("GETAWAY").equals("1") ? rb_mail.getTag().toString() : rb_get.getTag().toString();
        String legalName = result.getString("ENTERPRISE_ARTIFICIAL_PERSON");
        String address = result.getString("PERSON_ADDRESS");
        String email = result.getString("PERSON_EMAIL");
        String post = result.getString("PERSON_POST");
        String agent = result.getString("TRANSACTORTYPE").equals("2") ? rb_agent.getTag().toString() : rb_self.getTag().toString();
        itemType = result.getString("USER_TYPE");
        String nation = result.getString("PERSON_MZ");
        tv_item_name.setText(itemName);
        tv_item_code.setText(itemCode);
        if (Constants.PERSONAL_WORK_TYPE.equals(type)) {
            tv_name.setText(name);
//            tv_id_number.setText(idNumber);
//            tv_contact_phone.setText(phone);
            et_contact_address.setText(address);
        } else {
//            tv_legal_name.setText(legalName);
            tv_organization_name.setText(name);
//            tv_social_credit_code.setText(idNumber);
//            et_phone_number.setText(personPhone);
//            tv_legal_id_number.setText(personId);
            et_organization_address.setText(address);
        }
        et_mailbox.setText(email);
        et_postal_code.setText(post);
        et_nation.setText(nation);
        /*if ("1".equals(agent)) {
            rb_agent.setChecked(true);
            et_agent_name.setText(personName);
            et_agent_phone.setText(personPhone);
            et_agent_id.setText(personId);
        } else {
            rb_self.setChecked(true);
        }
        rg_agency.setTag(agent);*/
        if ("1".equals(getAway)) {
            rb_mail.setChecked(true);
        } else {
            rb_get.setChecked(true);
        }
        rg_way.setTag(getAway);
        if ("0".equals(dynamicForm)) {
            iv_progress_bar.setImageResource(R.drawable.per_circle_two_2);
        } else {
            iv_progress_bar.setImageResource(R.drawable.per_circle_two);
        }
        if (!BeanUtils.isEmpty(addressInfoList)) {
            addressInfoList.clear();
        }
        getAddress();
    }

    /**
     * 解析网点json数据回显
     * @param branchData
     */
    private void parseBranchData(ResultItem branchData) {
        if (branchData == null) {
            return;
        }
        branchId = branchData.getString("ID");
        String name = branchData.getString("NAME");
        String windowName = branchData.getString("SXWINDOW_NAME");
        if (!BeanUtils.isEmptyStr(name) && !BeanUtils.isEmptyStr(windowName)) {
            tv_branch.setText("网点名称：" + name + "，窗口地址：" + windowName);
        }
    }

    private void parseUserInfo(ResultItem data) {
        if (data == null) {
            return;
        }
        String name = data.getString("REALNAME");
        String idNumber = data.getString("IDCARD");
        String gender = data.getString("SEX");
        String nation = data.getString("MZ");
        String legalName = data.getString("FRDB");
        String organizationName = data.getString("COMPANYNAME");
        String socialCreditCode = data.getString("BUSINESSLICENCE");
        String legalIdNumber = data.getString("FRDB_SFZHM");
        String phone = data.getString("MOBILE_NUM");
        if (Constants.PERSONAL_WORK_TYPE.equals(type)) {
//            tv_name.setText(name);
            tv_id_number.setText(idNumber);
            tv_contact_phone.setText(phone);
            tv_gender.setText(BeanUtils.getGender(BasicInformationActivity.this, StringUtils.getGenderByIdCard(idNumber)));
            tv_gender.setTag(StringUtils.getGenderByIdCard(idNumber));
        } else {
            tv_legal_name.setText(legalName);
//            tv_organization_name.setText(organizationName);
            tv_social_credit_code.setText(socialCreditCode);
            tv_legal_id_number.setText(legalIdNumber);
            et_phone_number.setText(phone);
        }

    }

    private void showAddressData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            if ("0".equals(rg_way.getTag().toString())) {
                tv_no_data.setVisibility(View.GONE);
            } else {
                tv_no_data.setVisibility(View.VISIBLE);
            }
            lv_address.setVisibility(View.GONE);
            return;
        }
        tv_no_data.setVisibility(View.GONE);
        for (ResultItem resultItem : items) {
            AddressInfo addressInfo = new AddressInfo();
            addressInfo.setADDRESS(resultItem.getString("ADDRESS"));
//            addressInfo.setADDRESSID("0".equals(position) ? resultItem.getString("ADDRESSID") : resultItem.getString("ID"));
            addressInfo.setADDRESSID(resultItem.getString("ADDRESSID"));
            addressInfo.setCITY(resultItem.getString("CITY"));
            addressInfo.setCOMPANY_NAME(resultItem.getString("COMPANY_NAME"));
            addressInfo.setHANDSET(resultItem.getString("HANDSET"));
            if (BeanUtils.isEmptyStr(addressId)) {
                addressInfo.setDefault(resultItem.getString("ISDEFAULT").equals("1"));
            } else {
                if (addressId.equals(resultItem.getString("ADDRESSID"))) {
                    addressInfo.setDefault(true);
                } else {
                    addressInfo.setDefault(false);
                }
            }
            addressInfo.setISDEFAULT(resultItem.getString("ISDEFAULT"));
            addressInfo.setPROV(resultItem.getString("PROV"));
            addressInfo.setSJRXM(resultItem.getString("SJRXM"));
            addressInfo.setTEL_NO(resultItem.getString("TEL_NO"));
            addressInfo.setUSERID(resultItem.getString("USERID"));
            addressInfo.setYZBM(resultItem.getString("YZBM"));
            addressInfoList.add(addressInfo);
        }
        if (adapter == null) {
            adapter = new AddressAdapter(this, addressInfoList);
            lv_address.setAdapter(adapter);
            adapter.setOnClickListener(this);
            adapter.setOnItemClickListener(this);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 修改地址
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        selected = position;
        adapter.setSelected(selected);
    }

    public void onClick(int position) {
        mPosition = position;
        Intent intent = new Intent(BasicInformationActivity.this, AddAddressActivity.class);
        intent.putExtra("type", AddressType.修改);
        intent.putExtra("data", addressInfoList.get(position));
        startActivityForResult(intent, 100);
    }


    /**
     * 校验信息
     *
     * @return
     */
    private boolean validate() {
        if (Constants.PERSONAL_WORK_TYPE.equals(type)) {
            if (BeanUtils.isEmptyStr(declareInfo.getAddress())) {
                DialogUtils.showToast(BasicInformationActivity.this, "联系地址不能为空");
                return false;
            }
        } else {
            if (BeanUtils.isEmptyStr(declareInfo.getPhone())) {
                DialogUtils.showToast(BasicInformationActivity.this, "手机号不能为空");
                return false;
            } else if (!BeanUtils.isMobileNO(declareInfo.getPhone())) {
                DialogUtils.showToast(BasicInformationActivity.this, "手机号码格式不正确");
                return false;
            }
        }
        if (!BeanUtils.isEmptyStr(declareInfo.getMailbox()) && !BeanUtils.isEmail(declareInfo.getMailbox())) {
            DialogUtils.showToast(BasicInformationActivity.this, "电子邮箱格式不正确");
            return false;
        }
        if (!BeanUtils.isEmptyStr(declareInfo.getPostalCode()) && !BeanUtils.isPostCode(declareInfo.getPostalCode())) {
            DialogUtils.showToast(BasicInformationActivity.this, "邮政编码格式不正确");
            return false;
        }
        /*if ("1".equals(rg_agency.getTag().toString())) {
            if (BeanUtils.isEmptyStr(declareInfo.getAgentName())) {
                DialogUtils.showToast(BasicInformationActivity.this, "代理人姓名不能为空");
                return false;
            }
            if (BeanUtils.isEmptyStr(declareInfo.getAgentPhone())) {
                DialogUtils.showToast(BasicInformationActivity.this, "代理人电话不能为空");
                return false;
            } else if (!BeanUtils.isMobileNO(declareInfo.getAgentPhone())) {
                DialogUtils.showToast(BasicInformationActivity.this, "代理人电话格式不正确");
                return false;
            }
            if (BeanUtils.isEmptyStr(declareInfo.getAgentId())) {
                DialogUtils.showToast(BasicInformationActivity.this, "代理人证件号码不能为空");
                return false;
            } else if (!BeanUtils.validCard(declareInfo.getAgentId())) {
                DialogUtils.showToast(BasicInformationActivity.this, "代理人证件号码不正确");
                return false;
            }
        }*/
        if ("0".equals(rg_way.getTag().toString())) {
            if (BeanUtils.isEmptyStr(branchId)) {
                DialogUtils.showToast(BasicInformationActivity.this, "请选择办理结果自取网点！");
                return false;
            }
        } else if ("1".equals(rg_way.getTag().toString())) {
            if (BeanUtils.isEmpty(addressInfoList)) {
                DialogUtils.showToast(BasicInformationActivity.this, "邮寄地址不能为空");
                return false;
            }
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
        declareInfo.setItemName(tv_item_name.getText().toString());
        declareInfo.setUserType(type);
        if (Constants.PERSONAL_WORK_TYPE.equals(type)) {
            declareInfo.setName(tv_name.getText().toString());
            declareInfo.setGender(tv_gender.getTag().toString());
            declareInfo.setNation(et_nation.getText().toString().trim());
            declareInfo.setIdNumber(tv_id_number.getText().toString());
            declareInfo.setPhone(tv_contact_phone.getText().toString());
            declareInfo.setAddress(et_contact_address.getText().toString().trim());
        } else {
            declareInfo.setName(tv_legal_name.getText().toString());
            declareInfo.setEnterpriseName(tv_organization_name.getText().toString());
            declareInfo.setBusinessLicence(tv_social_credit_code.getText().toString());
            declareInfo.setPhone(et_phone_number.getText().toString().trim());
            declareInfo.setIdNumber(tv_legal_id_number.getText().toString());
            declareInfo.setAddress(et_organization_address.getText().toString().trim());
        }

        declareInfo.setMailbox(et_mailbox.getText().toString().trim());
        declareInfo.setPostalCode(et_postal_code.getText().toString().trim());
        declareInfo.setTransactorType("");
        declareInfo.setAgentName("");
        declareInfo.setAgentPhone("");
        declareInfo.setAgentId("");
        /*declareInfo.setTransactorType("0".equals(rg_agency.getTag().toString()) ? "1" : "2");
        if ("0".equals(rg_agency.getTag().toString())) {
            declareInfo.setAgentName("");
            declareInfo.setAgentPhone("");
            declareInfo.setAgentId("");
        } else {
            declareInfo.setAgentName(et_agent_name.getText().toString().trim());
            declareInfo.setAgentPhone(et_agent_phone.getText().toString().trim());
            declareInfo.setAgentId(et_agent_id.getText().toString().trim());
        }*/

        declareInfo.setIsExpress("1".equals(rg_way.getTag().toString()) ? "1" : "2");
        if (!BeanUtils.isEmpty(addressInfoList)) {
            declareInfo.setExpressId("0".equals(rg_way.getTag().toString()) ? "" : addressInfoList.get(adapter.getSelected()).getADDRESSID());
        }
        declareInfo.setProKeyId("0".equals(position) ? "" : proKeyId);
        declareInfo.setBranchId("0".equals(rg_way.getTag().toString()) ? branchId : "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SERVICE_BRANCH_CODE && data != null) {
                List<ServiceBranchBean> branchBeanList = (List<ServiceBranchBean>) data.getSerializableExtra("branchBeanList");
                for (ServiceBranchBean bean : branchBeanList) {
                    branchId = bean.getID();
                    String name = bean.getNAME();
                    String windowName = bean.getSXWINDOW_NAME();
                    tv_branch.setText("网点名称：" + name + "，窗口地址：" + windowName);
                }
            } else if (requestCode == 100 || requestCode == 101 && data != null) {
                /*AddressInfo addressInfo = (AddressInfo) data.getSerializableExtra("addressInfo");
                addressInfoList.get(mPosition).setADDRESS(addressInfo.getADDRESS());
                addressInfoList.get(mPosition).setADDRESSID(addressInfo.getADDRESSID());
                addressInfoList.get(mPosition).setCITY(addressInfo.getCITY());
                addressInfoList.get(mPosition).setCOMPANY_NAME(addressInfo.getCOMPANY_NAME());
                addressInfoList.get(mPosition).setHANDSET(addressInfo.getHANDSET());
                addressInfoList.get(mPosition).setISDEFAULT(addressInfo.getISDEFAULT());
                addressInfoList.get(mPosition).setDefault(addressInfo.isDefault());
                addressInfoList.get(mPosition).setPROV(addressInfo.getPROV());
                addressInfoList.get(mPosition).setSJRXM(addressInfo.getSJRXM());
                addressInfoList.get(mPosition).setTEL_NO(addressInfo.getTEL_NO());
                addressInfoList.get(mPosition).setUSERID(addressInfo.getUSERID());
                if ("1".equals(addressInfo.getISDEFAULT())) {
                    addressInfoList.get(adapter.getSelected()).setDefault(false);
                }
                addressInfoList.get(mPosition).setYZBM(addressInfo.getYZBM());
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }*/
                if (!BeanUtils.isEmpty(addressInfoList)) {
                    addressInfoList.clear();
                }
                getAddress();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                BasicInformationActivity.this.finish();
                break;
            case R.id.tv_gender:
//                BeanUtils.showGender(BasicInformationActivity.this, tv_gender);
                break;
            case R.id.tv_select_branch:
                // 选择服务网点
                startActivityForResult(new Intent(BasicInformationActivity.this, SelectServiceBranchActivity.class), SELECT_SERVICE_BRANCH_CODE);
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(BasicInformationActivity.this, AddAddressActivity.class);
                intent.putExtra("type", AddressType.添加);
                startActivityForResult(intent, 101);
                break;
            case R.id.btn_next:
                setDeclareInfo();
                if (!validate()) {
                    return;
                }
                saveItemInfo(declareInfo);
                break;
        }
    }

}
