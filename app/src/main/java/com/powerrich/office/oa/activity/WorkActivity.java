package com.powerrich.office.oa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.ItemsAdapter;
import com.powerrich.office.oa.adapter.WorkExpandableListViewAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.Items;
import com.powerrich.office.oa.bean.ItemsInfo;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.VerificationUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：WorkGuideActivity
 * 描   述：个人、企业办事
 * 作   者：Wangzheng
 * 时   间：2018-6-11 14:04:55
 * 版   权：v1.0
 */
public class WorkActivity extends BaseActivity implements View.OnClickListener, OnRefreshLoadmoreListener, WorkExpandableListViewAdapter.IGroupListener, WorkExpandableListViewAdapter.IChildListener {

    private static final int GET_SERVICE_ITEMS_REQ = 0;
    private static final int GET_ITEM_REQ = 1;
    private static final int SITE_LIST_REQ = 2;
    private static final int GET_SITE_ITEM_LIST_REQ = 3;
    private static final int REQUEST_CODE = 100;
    private TextView tv_theme, tv_department;
    private EditText et_query_content;
    private TextView tv_search;
    private ListView lv_left;
    private int currentPage = 1;
    private List<ItemsInfo> itemsInfoList = new ArrayList<>();
    private ItemsAdapter adapter;
    private List<Items> itemsList = new ArrayList<>();
    private String tagId;
    private String type;
    private SmartRefreshLayout refresh_layout;
    private ExpandableListView expand_lv;
    private TextView tv_no_data;
    private WorkExpandableListViewAdapter workAdapter;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        initView();
        initData();
        refreshLayout();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_work;
    }

    private void initView() {
        if (type.equals(Constants.PERSONAL_WORK_TYPE)) {
            initTitleBar(R.string.personal_work, this, null);
        } else {
            initTitleBar(R.string.enterprise_work, this, null);
        }
        getServiceItems(type, "");
        tv_theme = (TextView) findViewById(R.id.tv_theme);
        tv_department = (TextView) findViewById(R.id.tv_department);
        et_query_content = (EditText) findViewById(R.id.et_query_content);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setVisibility(View.GONE);
        lv_left = (ListView) findViewById(R.id.lv_left);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);

        refresh_layout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        expand_lv = (ExpandableListView) findViewById(R.id.expand_lv);
    }

    private void refreshLayout() {
        tv_theme.setOnClickListener(this);
        tv_department.setOnClickListener(this);
        refresh_layout.setEnableRefresh(false);
        refresh_layout.setOnRefreshLoadmoreListener(this);
    }

    private void initData() {
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tagId = itemsInfoList.get(position).getTAG_ID();
                if (adapter != null) {
                    adapter.setSelectedPosition(position);
                    adapter.notifyDataSetChanged();
                }
                if (!BeanUtils.isEmpty(itemsList)) {
                    itemsList.clear();
                }
                refresh_layout.setEnableLoadmore(true);
                et_query_content.setText("");
                currentPage = 1;
                getItem(type, tagId, "");
                tv_no_data.setVisibility(View.VISIBLE);
                if (workAdapter != null) {
                    workAdapter.notifyDataSetChanged();
                }

            }
        });

        et_query_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_query_content.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(WorkActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    String content = et_query_content.getText().toString().trim();
                    if (BeanUtils.isNullOrEmpty(content)) {
                        DialogUtils.showToast(WorkActivity.this, "请输入搜索内容");
                        return false;
                    }
                    if (!BeanUtils.isEmpty(itemsList)) {
                        itemsList.clear();
                    }
                    currentPage = 1;
                    getItem(type, tagId, content);
                    tv_no_data.setVisibility(View.VISIBLE);
                    if (workAdapter != null) {
                        workAdapter.notifyDataSetChanged();
                    }
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 获取事项请求接口
     */
    private void getServiceItems(String type, String tagName) {
        ApiRequest request = OAInterface.getTagsListBySort(type, tagName);
        invoke.invokeWidthDialog(request, callBack, GET_SERVICE_ITEMS_REQ);
    }

    /**
     * 获取部门列表请求
     */
    private void getSiteList() {
        ApiRequest request = OAInterface.getSiteList();
        invoke.invokeWidthDialog(request, callBack, SITE_LIST_REQ);
    }

    private void loadData(int index) {
        if (!BeanUtils.isEmpty(itemsInfoList)) {
            itemsInfoList.clear();
        }
        if (index == 0) {
            getServiceItems(type, "");
        } else {
            getSiteList();
        }
    }

    /**
     * 获取事项信息请求
     */
    private void getItem(String type, String tagId, String itemName) {
        ApiRequest request = OAInterface.getItemByTagId(type, tagId, itemName, String.valueOf(currentPage));
        invoke.invokeWidthDialog(request, callBack, GET_ITEM_REQ);
    }

    /**
     * 获取部门列表事项信息请求
     */
    private void getSiteAndItemList(String siteName) {
        ApiRequest request = OAInterface.getSiteAndItemList(siteName);
        invoke.invokeWidthDialog(request, callBack, GET_SITE_ITEM_LIST_REQ);
    }


    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == GET_SERVICE_ITEMS_REQ) {
                    List<ResultItem> data = item.getItems("DATA");
                    parseData(data);
                    tagId = itemsInfoList.get(adapter.getSelectedPosition()).getTAG_ID();
                    /*if (!BeanUtils.isEmpty(itemsList)) {
                        itemsList.clear();
                    }*/
                    getItem(type, tagId, "");
                } else if (what == GET_ITEM_REQ) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    if (result != null) {
                        List<ResultItem> data = result.getItems("DATA");
                        parseItemData(data);
                        tv_no_data.setVisibility(View.GONE);
                    } else {
                        refresh_layout.setEnableLoadmore(false);
                    }
                } /*else if (what == SITE_LIST_REQ) {
                    List<ResultItem> data = item.getItems("DATA");
                    parseData(data);
                }*/
            } else {
                DialogUtils.showToast(WorkActivity.this, message);
            }
        }

    };


    private void parseData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            return;
        }
        if (index == 0) {
            ItemsInfo info = new ItemsInfo();
            info.setTAG_ID("");
            info.setTAG_NAME("全部");
            itemsInfoList.add(info);
            for (ResultItem item : items) {
                String tag_id = item.getString("TAG_ID");
                String tag_name = item.getString("TAG_NAME");
                String tag_type = item.getString("TAG_TYPE");
                ItemsInfo itemsInfo = new ItemsInfo();
                itemsInfo.setTAG_ID(tag_id);
                itemsInfo.setTAG_NAME(tag_name);
                itemsInfo.setTAG_TYPE(tag_type);
                itemsInfoList.add(itemsInfo);
            }
        } else {
            for (ResultItem item : items) {
                String tag_id = item.getString("SITE_NO");
                String tag_name = item.getString("SHORT_NAME");
                ItemsInfo itemsInfo = new ItemsInfo();
                itemsInfo.setTAG_ID(tag_id);
                itemsInfo.setTAG_NAME(tag_name);
                itemsInfoList.add(itemsInfo);
            }
        }
        if (adapter == null) {
            adapter = new ItemsAdapter(WorkActivity.this, itemsInfoList);
            lv_left.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void parseItemData(List<ResultItem> data) {
        if (BeanUtils.isEmpty(data)) {
            return;
        }
        for (ResultItem resultItem : data) {
            String itemId = resultItem.getString("HID");
            String itemName = resultItem.getString("ITEMNAME");
            String itemCode = resultItem.getString("CODE");
            String itemDepart = resultItem.getString("NORMACCEPTDEPART");
            String siteNo = resultItem.getString("SITENO");
            String itemType = resultItem.getString("SXLX");
            String isApp = resultItem.getString("ISZXBL");
            String isAppointment = resultItem.getString("YYBL");//当前事项是否可以预约，0否，1是
            List<ResultItem> sonItems = resultItem.getItems("SON_ITEM");
            Items items = new Items();
            items.setITEMID(itemId);
            items.setITEMNAME(itemName);
            items.setSXBM(itemCode);
            items.setNORMACCEPTDEPART(itemDepart);
            items.setSITENO(siteNo);
            items.setSXLX(itemType);
            items.setISAPP(isApp);
            items.setISAPPOINTMENT(isAppointment);
            List<Items.SonItem> child = new ArrayList<>();
            if (!BeanUtils.isEmpty(sonItems)) {
                for (ResultItem son : sonItems) {
                    Items.SonItem sonItem = items.new SonItem();
                    if (siteNo.equals(son.getString("SITENO"))) {
                        sonItem.setITEMID(son.getString("HID"));
                        sonItem.setITEMNAME(son.getString("ITEMNAME"));
                        sonItem.setSXBM(son.getString("CODE"));
                        sonItem.setNORMACCEPTDEPART(son.getString("NORMACCEPTDEPART"));
                        sonItem.setSITENO(son.getString("SITENO"));
                        sonItem.setSXLX(son.getString("SXLX"));
                        sonItem.setISAPP(son.getString("ISZXBL"));
                        sonItem.setISAPPOINTMENT(resultItem.getString("YYBL"));//当前事项是否可以预约，0否，1是
                        child.add(sonItem);
                    }
                }
                items.setSonItems(child);
            }
            itemsList.add(items);
        }
        if (null == workAdapter) {
            workAdapter = new WorkExpandableListViewAdapter(WorkActivity.this, itemsList);
            workAdapter.setGuideListener(this);
            workAdapter.setWorkLister(this);
            workAdapter.setChildGuideListener(this);
            workAdapter.setChildWorkListener(this);
            expand_lv.setAdapter(workAdapter);
        } else {
            workAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refresh_layout.finishLoadmore();
        currentPage++;
        getItem(type, tagId, "");
    }

    /**
     * 个人、企业事项办理跳转到办事指南界面
     *
     * @param item
     * @param childPosition
     */
    private void gotoGuide(Items item, int childPosition) {
        Intent intent = new Intent(WorkActivity.this, WorkGuideNewActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("item_id", childPosition == -1 ? item.getITEMID() : item.getSonItems().get(childPosition).getITEMID());
        intent.putExtra("item_name", childPosition == -1 ? item.getITEMNAME() : item.getSonItems().get(childPosition).getITEMNAME());
        startActivity(intent);
    }

    /**
     * 个人、企业事项办理跳转到申报须知界面
     *
     * @param item
     * @param childPosition
     */
    private void gotoWork(Items item, int childPosition) {
        if (LoginUtils.getInstance().isLoginSuccess()) {
            intercept(item, childPosition);
        } else {
            //没有登录则让用户先去登录
            LoginUtils.getInstance().checkNeedLogin(WorkActivity.this, true, getString(R.string.declare_tips), REQUEST_CODE);
        }
    }

    /**
     * 拦截操作
     * @param item
     * @param childPosition
     */
    private void intercept(Items item, int childPosition) {
        // 判断用户是否实名认证进行办事事项的拦截
        if (VerificationUtils.isAuthentication(WorkActivity.this)) {
            String itemType = childPosition == -1 ? item.getSXLX() : item.getSonItems().get(childPosition).getSXLX();
            String isApp = childPosition == -1 ? item.getISAPP() : item.getSonItems().get(childPosition).getISAPP();
            if (!BeanUtils.isEmptyStr(itemType)) {
                String type = "0".equals(LoginUtils.getInstance().getUserInfo().getUserType()) ? Constants.PERSONAL_WORK_TYPE : Constants.COMPANY_WORK_TYPE;
                if (itemType.contains(type)) {
                    //包含则说明此用户类型可以办理该事项
                    if (isApp.contains("2")) {
                        List<UserInfo.CompanyInfo> companyInfoList = LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST();
                        if (Constants.PERSONAL_WORK_TYPE.equals(this.type)) {
                            gotoActivity(DeclareNoticeActivity.class, item, childPosition);
                        } else {
                            if ("1".equals(LoginUtils.getInstance().getUserInfo().getUserType())) {
                                if (!BeanUtils.isEmpty(companyInfoList)) {
                                    if (companyInfoList.size() == 1) {
                                        gotoDeclareNotice(item, childPosition, companyInfoList);
                                    } else {
                                        gotoActivity(EnterpriseInformationActivity.class, item, childPosition);
                                    }
                                } else {
                                    gotoActivity(DeclareNoticeActivity.class, item, childPosition);
                                }
                            } else {
                                DialogUtils.showToast(WorkActivity.this, "您是个人用户，请办理个人事项");
                            }
                        }
                    } else {
                        DialogUtils.showToast(WorkActivity.this, "该事项只能在网上办理或窗口办理");
                    }
                } else {//不包含则说明此用户类型不可以办理该事项
                    String userType = Constants.PERSONAL_WORK_TYPE.equals(type) ? "个人" : "企业";
                    DialogUtils.showToast(WorkActivity.this, "您是" + userType + "用户，请办理" + userType + "事项");
                }
            } else {
                DialogUtils.showToast(WorkActivity.this, "该事项不能办理");
            }
        }
    }

    /**
     * 跳转到申报须知界面
     * @param item
     * @param childPosition
     */
    private void gotoDeclareNotice(Items item, int childPosition, List<UserInfo.CompanyInfo> companyInfoList) {
        Intent intent = new Intent(WorkActivity.this, DeclareNoticeActivity.class);
        intent.putExtra("type", this.type);
        intent.putExtra("item_id", childPosition == -1 ? item.getITEMID() : item.getSonItems().get(childPosition).getITEMID());
        intent.putExtra("position", "0");
        intent.putExtra("companyId", BeanUtils.isEmpty(companyInfoList) ? "" : companyInfoList.get(0).getID());
        startActivity(intent);
    }

    /**
     * 跳转不同界面传参
     * @param c
     * @param item
     * @param childPosition
     */
    private void gotoActivity(Class c, Items item, int childPosition) {
        Intent intent = new Intent(WorkActivity.this, c);
        intent.putExtra("type", this.type);
        intent.putExtra("item_id", childPosition == -1 ? item.getITEMID() : item.getSonItems().get(childPosition).getITEMID());
        intent.putExtra("position", "0");
        startActivity(intent);
    }

    @Override
    public void onClickGuide(Items item) {
        gotoGuide(item, -1);
    }

    @Override
    public void onClickWork(Items item) {
        gotoWork(item, -1);
    }

    @Override
    public void onClickChildGuide(Items item, int childPosition) {
        gotoGuide(item, childPosition);
    }

    @Override
    public void onClickChildWork(Items item, int childPosition) {
        gotoWork(item, childPosition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                WorkActivity.this.finish();
                break;
            /*case R.id.tv_theme:
                tv_theme.setBackgroundResource(R.drawable.select_tab_checked_left);
                tv_theme.setTextColor(ContextCompat.getColor(WorkActivity.this, R.color.white));
                tv_department.setBackground(null);
                tv_department.setTextColor(Color.GRAY);
                index = 0;
                loadData(index);
                break;
            case R.id.tv_department:
                tv_department.setBackgroundResource(R.drawable.select_tab_checked_right);
                tv_department.setTextColor(ContextCompat.getColor(WorkActivity.this, R.color.white));
                tv_theme.setBackground(null);
                tv_theme.setTextColor(Color.GRAY);
                index = 1;
                loadData(index);
                break;*/
        }
    }

}
