package com.powerrich.office.oa.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.powerrich.office.oa.adapter.BookingExpandableListViewAdapter;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.Items;
import com.powerrich.office.oa.bean.ItemsInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
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
 * @author ch
 * @title 网上预约提醒
 */
public class OnlineBookingActivity extends BaseActivity implements View.OnClickListener, OnRefreshLoadmoreListener, BookingExpandableListViewAdapter.IGroupListener, BookingExpandableListViewAdapter.IChildListener {

    /**
     * 个人和企业办事主题
     */
    private final int GETTAGSLISTBYSORT = 000;
    /**
     * 个人和企业办事信息
     */
    private final int GETITEMBYTAGID = 111;
    /**
     * 个人和企业办事信息， 用于加载更多
     */
    private final int GETITEMBYTAGIDFORLOADMORE = 222;

    private static final int REQUEST_CODE = 100;
    /**
     * 事项类型(1企业办事；2个人办事)
     */
    private String mType = "2";
    private EditText et_query_content;
    private SmartRefreshLayout refresh_layout;
    private ExpandableListView expand_lv;
    private int mPage = 1;
    private BookingExpandableListViewAdapter itemAdapter;
    private TextView tv_no_data;
    private String tag_id;//默认为空
    private List<Items> itemsList = new ArrayList<>();//全局事项集合是为了装上拉加载的数据
    private ListView lv_left;
    private int oldPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getIntent().getStringExtra("type");
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_online_booking;
    }

    private void initView() {
        initTitleBar(R.string.title_activity_online_booking, this, null);
        et_query_content = (EditText) findViewById(R.id.et_query_content);
        findViewById(R.id.tv_search).setVisibility(View.GONE);
        lv_left = (ListView) findViewById(R.id.lv_left);
        refresh_layout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        expand_lv = (ExpandableListView) findViewById(R.id.expand_lv);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        initData();
    }

    private void initData() {
        et_query_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_query_content.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(OnlineBookingActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchItemList();// 搜索
                    return true;
                }
                return false;
            }
        });

        refresh_layout.setEnableRefresh(false);
        refresh_layout.setOnRefreshLoadmoreListener(this);
        loadData();
    }

    private void loadData() {
        getServiceItems("");
    }

    /**
     * 获取事项请求接口
     */
    private void getServiceItems(String tagName) {
        invoke.invoke(OAInterface.getTagsListBySort(mType, tagName), callBack, GETTAGSLISTBYSORT);
    }

    /**
     * 获取事项信息请求
     */
    private void getItemByTagId() {
        invoke.invokeWidthDialog(OAInterface.getItemByTagId(mType, tag_id == null ? "" : tag_id,
                et_query_content.getText().toString().trim(), String.valueOf(mPage)), callBack, GETITEMBYTAGID);
    }

    /**
     * 获取事项信息请求，用于加载更多
     */
    private void getItemByTagIdForLoadMore() {
        invoke.invoke(OAInterface.getItemByTagId(mType, tag_id == null ? "" : tag_id,
                et_query_content.getText().toString().trim(), String.valueOf(mPage)), callBack, GETITEMBYTAGIDFORLOADMORE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == GETTAGSLISTBYSORT) {
                    List<ResultItem> data = item.getItems("DATA");
                    showDepList(data);
                } else if (what == GETITEMBYTAGID) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    if (!BeanUtils.isEmpty(result)) {
                        List<ResultItem> data = result.getItems("DATA");
                        parseItemData(data, GETITEMBYTAGID);
                        tv_no_data.setVisibility(View.GONE);
                        expand_lv.setVisibility(View.VISIBLE);
                    } else {
                        expand_lv.setVisibility(View.GONE);
                        tv_no_data.setVisibility(View.VISIBLE);
                    }
                } else if (what == GETITEMBYTAGIDFORLOADMORE) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    if (!BeanUtils.isEmpty(result)) {
                        List<ResultItem> data = result.getItems("DATA");
                        parseItemData(data, GETITEMBYTAGIDFORLOADMORE);
                    } else {
                        refresh_layout.setEnableLoadmore(false);
                    }
                }
            } else {
                DialogUtils.showToast(OnlineBookingActivity.this, message);
            }
        }

    };

    private void parseItemData(List<ResultItem> data, int what) {
        if (what == GETITEMBYTAGID) {
            if (null != itemsList) {
                itemsList.clear();
            }
        }
        if (!BeanUtils.isEmpty(data)) {
            for (ResultItem resultItem : data) {
                String itemId = resultItem.getString("HID");
                String itemName = resultItem.getString("ITEMNAME");
                String itemCode = resultItem.getString("CODE");
                String itemDepart = resultItem.getString("NORMACCEPTDEPART");
                String siteNo = resultItem.getString("SITENO");
                String itemType = resultItem.getString("SXLX");
                String isAppointment = resultItem.getString("YYBL");//当前事项是否可以预约，0否，1是
                List<ResultItem> sonItems = resultItem.getItems("SON_ITEM");
                Items items = new Items();
                items.setITEMID(itemId);
                items.setITEMNAME(itemName);
                items.setSXBM(itemCode);
                items.setNORMACCEPTDEPART(itemDepart);
                items.setSITENO(siteNo);
                items.setSXLX(itemType);
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
                            sonItem.setSXLX(son.getString("SXLX"));//
                            sonItem.setISAPPOINTMENT(son.getString("YYBL"));//当前事项是否可以预约，0否，1是
                            child.add(sonItem);
                        }
                    }
                    items.setSonItems(child);
                }
                itemsList.add(items);
            }
        }
        if (null == itemAdapter) {
            itemAdapter = new BookingExpandableListViewAdapter(OnlineBookingActivity.this, itemsList);
            itemAdapter.setGuideListener(this);
            itemAdapter.setWorkLister(this);
            itemAdapter.setChildGuideListener(this);
            itemAdapter.setChildWorkListener(this);
            expand_lv.setAdapter(itemAdapter);
        } else {
            itemAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 展示部门选择弹框
     *
     * @param data
     */
    private void showDepList(List<ResultItem> data) {
        ArrayList<ItemsInfo> depList = new ArrayList<>();
        if (!BeanUtils.isEmpty(data)) {
            for (ResultItem item : data) {
                    String tag_id = item.getString("TAG_ID");
                    String tag_name = item.getString("TAG_NAME");
                    String tag_type = item.getString("TAG_TYPE");
                    ItemsInfo itemsInfo = new ItemsInfo();
                    itemsInfo.setTAG_ID(tag_id);
                    itemsInfo.setTAG_NAME(tag_name);
                    itemsInfo.setTAG_TYPE(tag_type);
                depList.add(itemsInfo);
            }
            if (depList.size() > 0) {
                depList.get(0).setSelected(true);
                tag_id = depList.get(0).getTAG_ID();
                getItemByTagId();
            }
        }
        CommonAdapter<ItemsInfo> depAdapter = new CommonAdapter<ItemsInfo>(OnlineBookingActivity.this, depList, R.layout.my_work_gv_item) {
            @Override
            public void convert(ViewHolder holder, ItemsInfo item) {
                holder.setTextView(R.id.text, item.getTAG_NAME());
                View ll_bg = holder.getItemView(R.id.ll_bg);
                if (item.isSelected()) {
                    ll_bg.setBackgroundColor(Color.parseColor("#ffffff"));
                } else {
                    ll_bg.setBackgroundColor(Color.parseColor("#ececec"));
                }
            }
        };
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (oldPosition == position) return;
                et_query_content.setText("");//搜索置空
                CommonAdapter<ItemsInfo> adapter = (CommonAdapter<ItemsInfo>) parent.getAdapter();
                tag_id = adapter.getItem(position).getTAG_ID();
                adapter.getItem(position).setSelected(true);
                adapter.getItem(oldPosition).setSelected(false);
                adapter.notifyDataSetChanged();
                searchItemList();
                oldPosition = position;
            }
        });
        lv_left.setAdapter(depAdapter);
    }

    /**
     * 根据关键字搜索事项
     */
    private void searchItemList() {
        mPage = 1;
        refresh_layout.setEnableLoadmore(true);
        getItemByTagId();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                OnlineBookingActivity.this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPage++;
        getItemByTagIdForLoadMore();
        refresh_layout.finishLoadmore();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        //暂时不用
    }

    @Override
    public void onClickGuide(Items item) {
        goGuide(item, -1);
    }

    @Override
    public void onClickWork(Items item) {
        goWork(item, -1);
    }

    @Override
    public void onClickChildGuide(Items item, int childPosition) {
        goGuide(item, childPosition);
    }

    @Override
    public void onClickChildWork(Items item, int childPosition) {
        goWork(item, childPosition);
    }

    private void goGuide(Items item, int childPosition) {
        Intent intent = new Intent(OnlineBookingActivity.this, WorkGuideNewActivity.class);
        intent.putExtra("item_id", childPosition == -1 ? item.getITEMID() : item.getSonItems().get(childPosition).getITEMID());
        intent.putExtra("item_name", childPosition == -1 ? item.getITEMNAME() : item.getSonItems().get(childPosition).getITEMNAME());
        startActivity(intent);
    }

    private void goWork(Items item, int childPosition) {
        if (LoginUtils.getInstance().isLoginSuccess()) {
            intercept(item, childPosition);
        } else {
            //没有登录则让用户先去登录
            LoginUtils.getInstance().checkNeedLogin(OnlineBookingActivity.this, true, getString(R.string.declare_tips), REQUEST_CODE);
        }
    }

    /**
     * 拦截操作
     * @param item
     * @param childPosition
     */
    private void intercept(Items item, int childPosition) {
        // 判断用户是否实名认证进行办事事项的拦截
        if (VerificationUtils.isAuthentication(OnlineBookingActivity.this)) {
            String itemType = childPosition == -1 ? item.getSXLX() : item.getSonItems().get(childPosition).getSXLX();
            String isOrder = childPosition == -1 ? item.getISAPPOINTMENT() : item.getSonItems().get(childPosition).getISAPPOINTMENT();
            if (!BeanUtils.isEmptyStr(itemType) && !"3".equals(itemType)) {//sxlx 不为空则说明该事项可以办理;不为“3”则说明不是部门事项
                String type = "0".equals(LoginUtils.getInstance().getUserInfo().getUserType()) ? Constants.PERSONAL_WORK_TYPE : Constants.COMPANY_WORK_TYPE;
                if (itemType.contains(type)) {//包含则说明此用户类型可以办理该事项
                    if ("1".equals(isOrder)) {//yybl为“1”则可以预约
                        Intent intent = new Intent(OnlineBookingActivity.this, OnlineBookingHallActivity.class);
                        intent.putExtra("type", mType);
                        intent.putExtra("NORMACCEPTDEPART", childPosition == -1 ? item.getNORMACCEPTDEPART() : item.getSonItems().get(childPosition).getNORMACCEPTDEPART());
                        intent.putExtra("ITEMNAME", childPosition == -1 ? item.getITEMNAME() : item.getSonItems().get(childPosition).getITEMNAME());
                        intent.putExtra("ITEMID", childPosition == -1 ? item.getITEMID() : item.getSonItems().get(childPosition).getITEMID());
                        intent.putExtra("SITENO", childPosition == -1 ? item.getSITENO() : item.getSonItems().get(childPosition).getSITENO());
                        startActivity(intent);
                    } else {//yybl不为“1”则不可以预约
                        DialogUtils.showToast(OnlineBookingActivity.this, "该事项不能预约");
                    }
                } else {//不包含则说明此用户类型不可以办理该事项
                    String userType = Constants.PERSONAL_WORK_TYPE.equals(type) ? "个人" : "企业";
                    DialogUtils.showToast(OnlineBookingActivity.this, "您是" + userType + "用户，请办理" + userType + "事项");
                }
            } else {//sxlx 为空或为“3”则说明该事项不能办理
                DialogUtils.showToast(OnlineBookingActivity.this, "该事项不能预约");
            }
        }
    }
}
