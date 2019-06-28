package com.powerrich.office.oa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.adapter.SearchRecycleAdapter;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.IntegratedQueryBean;
import com.powerrich.office.oa.bean.SearchBean;
import com.powerrich.office.oa.bean.SearchItem;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.SPUtils;
import com.powerrich.office.oa.tools.ServiceSelectTool;
import com.powerrich.office.oa.tools.VerificationUtils;
import com.powerrich.office.oa.view.DeletableEditText;
import com.powerrich.office.oa.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * CC 描述：搜索
 * 文 件 名：WorkGuideActivity
 * 描   述：办事指南
 * 作   者：Wangzheng
 * 时   间：2018-6-11 14:04:55
 * 版   权：v1.0
 */
public class WorkGuideActivity extends BaseActivity implements View.OnClickListener {

    private DeletableEditText et_query_content;
    private TextView tv_search;
    private LinearLayout ll_num;
    private TextView tv_no_data;
    private ListView listView;
    private RecyclerView rv_search_content;
    private SearchRecycleAdapter searchRecycleAdapter;
    private CommonAdapter<IntegratedQueryBean> adapter;
    private int currentPage = 1;
    private TextView tv_num;
    private NestedScrollView nested_sv;
    private FlowLayout keyword_fl, history_fl;
    private ImageView clear_iv, iv_back;
    private ServiceSelectTool selectTool;

    private List<SearchItem> itemsList = new ArrayList<>();

    public static String[] searchWord = {"就业创业", "职业资格", "规划建设", "出境入境", "公共安全", "医疗卫生", "社会保障", "婚姻登记", "社会救助"};
    private static final int REQUEST_CODE = 111;

    private String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();

    }


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_work_guide;
    }

    private void initView() {
        initTitleBar(R.string.work_guide, this, null);


        et_query_content = (DeletableEditText) findViewById(R.id.et_query_content);
        tv_search = (TextView) findViewById(R.id.tv_search);
        ll_num = (LinearLayout) findViewById(R.id.ll_num);
        listView = (ListView) findViewById(R.id.listView);
        rv_search_content = (RecyclerView) findViewById(R.id.rv_search_content);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        tv_num = (TextView) findViewById(R.id.tv_num);
        nested_sv = (NestedScrollView) findViewById(R.id.nested_sv);
        keyword_fl = (FlowLayout) findViewById(R.id.keyword_fl);
        history_fl = (FlowLayout) findViewById(R.id.history_fl);
        clear_iv = (ImageView) findViewById(R.id.clear_iv);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rv_search_content.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_search_content.setItemAnimator(new DefaultItemAnimator());
        rv_search_content.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        searchRecycleAdapter = new SearchRecycleAdapter();
        rv_search_content.setAdapter(searchRecycleAdapter);
    }

    /**
     * 查询本地
     *
     * @param content
     */
    private void searchLocal(String content) {

        List<Map<String, Object>> mapList = selectTool.queryData(content);
        Log.i("jsc", "查询个数: " + mapList.size());
        if (mapList.size() > 0) {
            itemsList.add(new SearchItem(1, "服务"));
            SearchItem searchItem1 = new SearchItem(3, "服务");
            List<SearchBean> searchItemList = new ArrayList<>();
            for (int i = 0; i < mapList.size(); i++) {
                SearchBean searchItem = new SearchBean();
                Map<String, Object> objectMap = mapList.get(i);
                String text = ((String) objectMap.get("text"));
                int image = (int) objectMap.get("image");
                Intent intent = (Intent) objectMap.get("intent");
                searchItem.setIntent(intent);
                searchItem.setItemName(text);
                searchItem.setItemImage(image);
                searchItemList.add(searchItem);
            }
            searchItem1.setArrayList(searchItemList);
            itemsList.add(searchItem1);
        }
    }

    /**
     * @param item_data
     * @param app_data
     */
    private void showItemData(List<ResultItem> item_data, List<ResultItem> app_data) {


//        // 在线事项
//        if (!BeanUtils.isEmpty(app_data)) {
//            itemsList.addAll(filtData(app_data, "在线办事"));
//        }
        // 全部事项 （办事指南）
        if (!BeanUtils.isEmpty(item_data)) {
            itemsList.addAll(filtData(item_data, "事项"));
       //     itemsList.addAll(filtData(item_data, "预约办事"));
        }
        searchLocal(content);
        if (itemsList.size() == 0) {
            noDataView();
        }
        searchRecycleAdapter.setData(itemsList);

//
//        if (null == adapter) {
//            adapter = new CommonAdapter<IntegratedQueryBean>(WorkGuideActivity.this, itemsList, R.layout.list_item) {
//                @Override
//                public void convert(ViewHolder holder, IntegratedQueryBean item) {
//                    holder.setTextView(R.id.text, item.getItemName());
//                    holder.setTextView(R.id.depart_txt, item.getShortName());
//                }
//            };
//            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent();
//                    intent.setClass(WorkGuideActivity.this, FamilyRegisterDetailActivity.class);
//                    intent.putExtra("item_id", adapter.getItem(position).getItemId());
//                    intent.putExtra("item_name", adapter.getItem(position).getItemName());
//                    startActivity(intent);
//                }
//            });
//        } else {
//            adapter.setData(itemsList);
//        }
    }

    /**
     * 筛选数据
     *
     * @param resultItemList
     * @param titleName
     * @return
     */
    private List<SearchItem> filtData(List<ResultItem> resultItemList, String titleName) {
        List<SearchItem> searchItems = new ArrayList<>();
        if (!BeanUtils.isEmpty(resultItemList)) {
            searchItems.add(new SearchItem(1, titleName));
            for (int i = 0; i < resultItemList.size(); i++) {
                if (i < 5) {
                    ResultItem item = resultItemList.get(i);
                    SearchBean searchBean = new SearchBean();
                    SearchItem searchItem = new SearchItem(2, item.getString("ITEMNAME"));
                    searchBean.setItemId(item.getString("HID"));
                    searchBean.setItemName(item.getString("ITEMNAME"));
                    searchBean.setShortName(item.getString("SHORT_NAME"));
                    searchBean.setSiteNo(item.getString("SITENO"));
                    searchBean.setNormacceptdepart(item.getString("NORMACCEPTDEPART"));
                    searchBean.setTypeName(titleName);
                    searchBean.setType("0");
                    searchItem.setItem(searchBean);
                    searchItems.add(searchItem);
                    if(i==4){
                        searchItems.add(new SearchItem(4, titleName));
                        break;
                    }
                }
//                else {
//                    searchItems.add(new SearchItem(4, titleName));
//                    break;
//                }
            }

        }
        return searchItems;
    }


    private void initData() {

        selectTool = new ServiceSelectTool(this);
        tv_search.setOnClickListener(this);
        clear_iv.setOnClickListener(this);
        iv_back.setOnClickListener(this);


        et_query_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_query_content.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(WorkGuideActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    content = et_query_content.getText().toString().trim();


                    loadData(content);// 搜索

                    tv_no_data.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    rv_search_content.setVisibility(View.VISIBLE);
                    ll_num.setVisibility(View.GONE);
                    nested_sv.setVisibility(View.GONE);

//                    loadData(content);// 搜索
//                    if (!BeanUtils.isEmptyStr(content)) {
//                        if (BeanUtils.isEmpty(itemsList)) {
//                            tv_no_data.setVisibility(View.VISIBLE);
//                            listView.setVisibility(View.GONE);
//                            ll_num.setVisibility(View.GONE);
//                        } else {
//                            tv_no_data.setVisibility(View.GONE);
//                            listView.setVisibility(View.VISIBLE);
//                            ll_num.setVisibility(View.VISIBLE);
//                        }
//                    }
                    return true;
                }
                return false;
            }
        });
        initKeyword(searchWord);
        initHistory();

        searchRecycleAdapter.setiClickListener(new SearchRecycleAdapter.IClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SearchItem searchItem = (SearchItem) view.getTag();
                SearchBean item = searchItem.getItem();
                switch (searchItem.getType()) {
                    //内容
                    case 2:
                        if (item.getTypeName().equals("在线办事")) {
                            zxIntent(item.getItemId());
                        } else if (item.getTypeName().equals("预约办事")) {
                            Intent intent = new Intent(WorkGuideActivity.this, OnlineBookingHallActivity.class);
                            intent.putExtra("NORMACCEPTDEPART", item.getNormacceptdepart());
                            intent.putExtra("ITEMNAME", item.getItemName());
                            intent.putExtra("ITEMID", item.getItemId());
                            intent.putExtra("SITENO", item.getSiteNo());

                            startActivity(intent);
                        } else {
//                            Intent intent = new Intent();
//                            intent.setClass(WorkGuideActivity.this, FamilyRegisterDetailActivity.class);
//                            intent.putExtra("item_id", item.getItemId());
//                            intent.putExtra("item_name", item.getItemName());
//                            startActivity(intent);
                            Intent intent = new Intent();
                            intent.setClass(WorkGuideActivity.this, WorkGuideNewActivity.class);
                            intent.putExtra("item_id", item.getItemId());
                            intent.putExtra("item_name", item.getItemName());
                            intent.putExtra("UNITSTR", item.getNormacceptdepart());
                            intent.putExtra("type", Constants.PERSONAL_WORK_TYPE);
                            intent.putExtra("typeHome","0");
                            startActivity(intent);
                        }
                        break;

                    //更多
                    case 4:

                        startActivity(new Intent(WorkGuideActivity.this, SearchMoreActivity.class)
                                .putExtra("CONTENT", content)
                                .putExtra("CONTENTTYPE", searchItem.getName())
                                .putExtra("SEARCHBEAN", item)
                        );
                        break;
                }
            }
        });

        et_query_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    initHistory();
                    nested_sv.setVisibility(View.VISIBLE);
                    tv_no_data.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    rv_search_content.setVisibility(View.GONE);
                    ll_num.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 在线跳转
     */
    private void zxIntent(String item_id) {
        if (LoginUtils.getInstance().isLoginSuccess()) {
            // 判断用户是否实名认证进行办事事项的拦截
            if (VerificationUtils.isAuthentication(WorkGuideActivity.this)) {
                String itemType = "0".equals(LoginUtils.getInstance().getUserInfo().getUserType()) ? Constants.PERSONAL_WORK_TYPE : Constants.COMPANY_WORK_TYPE;
                List<UserInfo.CompanyInfo> companyInfoList = LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST();
                gotoDeclareNotice(itemType, companyInfoList, item_id);
            }
        } else {
            // 没有登录则让用户先去登录
            LoginUtils.getInstance().checkNeedLogin(WorkGuideActivity.this, true, getString(R.string.declare_tips), REQUEST_CODE);
        }
    }


    /**
     * 跳转到申报须知界面
     */
    private void gotoDeclareNotice(String itemType, List<UserInfo.CompanyInfo> companyInfoList, String item_id) {
        Intent intent = new Intent(WorkGuideActivity.this, DeclareNoticeActivity.class);
        intent.putExtra("type", Constants.PERSONAL_WORK_TYPE);
        intent.putExtra("item_id", item_id);
        intent.putExtra("position", "0");
        intent.putExtra("companyId", BeanUtils.isEmpty(companyInfoList) ? "" : companyInfoList.get(0).getID());
        startActivity(intent);
    }


//    /**
//     * 跳转不同界面传参
//     */
//    private void gotoActivity(Class c, String itemType) {
//        Intent intent = new Intent(WorkGuideActivity.this, c);
//        intent.putExtra("type", itemType);
//        intent.putExtra("item_id", item_id);
//        intent.putExtra("position", "0");
//        startActivity(intent);
//    }


    private void initKeyword(final String[] keyword) {
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        keyword_fl.removeAllViews();
        for (int i = 0; i < keyword.length; i++) {
            final int j = i;
            //添加分类块
            View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
            TextView keyWordTv = (TextView) paramItemView.findViewById(R.id.tv_content);
            keyWordTv.setText(keyword[j]);
            keyWordTv.setBackgroundResource(R.drawable.whitebg_strokegrey_radius3);
            keyword_fl.addView(paramItemView, layoutParams);

            keyWordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_query_content.setText(keyword[j]);
                    loadData(et_query_content.getText().toString().trim());
                }
            });
        }
    }

    private void initHistory() {
        final String[] data = SPUtils.getInstance(this).getHistoryList();
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        history_fl.removeAllViews();
        for (int i = 0; i < data.length; i++) {
            if (BeanUtils.isEmptyStr(data[i])) {
                return;
            }
            final int j = i;
            //添加分类块
            View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
            TextView keyWordTv = (TextView) paramItemView.findViewById(R.id.tv_content);
            keyWordTv.setText(data[j]);
            keyWordTv.setBackgroundResource(R.drawable.whitebg_strokegrey_radius3);
            history_fl.addView(paramItemView, layoutParams);

            keyWordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_query_content.setText(data[j]);
                    loadData(et_query_content.getText().toString().trim());
                }
            });
        }
    }

    private void loadData(String searchText) {
        content = searchText;
        if (BeanUtils.isEmptyStr(searchText)) {
            DialogUtils.showToast(WorkGuideActivity.this, "请输入搜索内容");
            return;
        }
        if (!BeanUtils.isEmpty(itemsList)) {
            itemsList.clear();
        }
        invoke.invokeWidthDialog(OAInterface.searchContent(searchText), callBack);
        SPUtils.getInstance(WorkGuideActivity.this).save(searchText);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");

            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                //（全部）事项列表
                List<ResultItem> item_data = item.getItems("ITEM_DATA");
                //（在线）App办理的事项
                List<ResultItem> app_data = item.getItems("APP_ITEM");

                try {
                    Log.i("jsc", "process: " + item_data.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Log.i("jsc", "-app_data:" + app_data.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (BeanUtils.isEmpty(item_data) && BeanUtils.isEmpty(app_data)) {

                    showItemData(null, null);
                } else {
//                    tv_no_data.setVisibility(View.GONE);
//                    listView.setVisibility(View.VISIBLE);
//                    ll_num.setVisibility(View.VISIBLE);
//                    tv_num.setText(Html.fromHtml("共搜索到<font color='#ea7805'>" + item_data.size() + "</font>条数据"));
                    tv_no_data.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    rv_search_content.setVisibility(View.VISIBLE);
                    ll_num.setVisibility(View.GONE);
                    nested_sv.setVisibility(View.GONE);
                    showItemData(item_data, app_data);
                }
                //<  服务器不返回数据时的操作。>

                nested_sv.setVisibility(View.GONE);
            } else {
                DialogUtils.showToast(WorkGuideActivity.this, message);
            }
        }

    };

    /**
     * 没有数据的界面
     */
    private void noDataView() {
        tv_no_data.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        rv_search_content.setVisibility(View.GONE);
        ll_num.setVisibility(View.GONE);
    }


//    private void showItemData(List<ResultItem> item_data) {
//        if (!BeanUtils.isEmpty(item_data)) {
//            for (ResultItem item : item_data) {
//                IntegratedQueryBean queryBean = new IntegratedQueryBean();
//                queryBean.setItemId(item.getString("HID"));
//                queryBean.setItemName(item.getString("ITEMNAME"));
//                queryBean.setShortName(item.getString("SHORT_NAME"));
//                queryBean.setSiteNo(item.getString("SITENO"));
//                queryBean.setType("0");
//                itemsList.add(queryBean);
//            }
//        }
//        if (null == adapter) {
//            adapter = new CommonAdapter<IntegratedQueryBean>(WorkGuideActivity.this, itemsList, R.layout.list_item) {
//                @Override
//                public void convert(ViewHolder holder, IntegratedQueryBean item) {
//                    holder.setTextView(R.id.text, item.getItemName());
//                    holder.setTextView(R.id.depart_txt, item.getShortName());
//                }
//            };
//            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent();
//                    intent.setClass(WorkGuideActivity.this, FamilyRegisterDetailActivity.class);
//                    intent.putExtra("item_id", adapter.getItem(position).getItemId());
//                    intent.putExtra("item_name", adapter.getItem(position).getItemName());
//                    startActivity(intent);
//                }
//            });
//        } else {
//            adapter.setData(itemsList);
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                WorkGuideActivity.this.finish();
                break;
            case R.id.system_back:
                WorkGuideActivity.this.finish();
                break;
            case R.id.tv_search:
                loadData(et_query_content.getText().toString().trim());
                break;
            case R.id.clear_iv:
                SPUtils.getInstance(WorkGuideActivity.this).cleanHistory();
                DialogUtils.showToast(WorkGuideActivity.this, "已清除历史记录！");
                initHistory();
                break;
        }
    }
}
