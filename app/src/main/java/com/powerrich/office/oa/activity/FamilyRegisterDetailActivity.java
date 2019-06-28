package com.powerrich.office.oa.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.things.PublicWebViewActivity;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.base.InvokeHelper;
import com.powerrich.office.oa.bean.MaterialsInfo;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.fragment.HJTab1Fragment;
import com.powerrich.office.oa.fragment.HJTab2Fragment;
import com.powerrich.office.oa.fragment.HJTab3Fragment;
import com.powerrich.office.oa.fragment.HJTab4Fragment;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.CoodinateCovertor;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.StatusBarUtils;
import com.powerrich.office.oa.tools.StringUtils;
import com.powerrich.office.oa.tools.VerificationUtils;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 户籍列表详情界面
 *
 * @author Administrator
 */
public class  FamilyRegisterDetailActivity extends FragmentActivity implements OnClickListener {
    private static final int GET_ITEM_REQ = 0;
    private static final int GET_COLLECT_ITEM_REQ = 1;
    private static final int CANCEL_COLLECT_ITEM_REQ = 2;
    private static final int READER_ITEM_REQ = 3;
    private static final int REQUEST_CODE = 111;
    /**头部相关控件*/
    public ImageView backBtn, rightBtn, customBtn;
    /**头部标题*/
    public TextView titleTxt;
    /**受理条件*/
    private TextView tv_sltj;
    /**办理材料*/
    private TextView tv_sxcl;
    /**收费情况*/
    private TextView tv_sfyj;
    /**设定依据*/
    private TextView tv_flyj;
    /**View线：受理条件，办理材料，收费情况，设定依据*/
    private View view1, view2, view3, view4;
    /**Fragment：受理条件，办理材料，收费情况，设定依据*/
    private Fragment tab1, tab2, tab3, tab4;
    /**当前Fragment*/
    private Fragment currentFragment;
    private String type;
    private String item_id;
    private String itemName;
    private InvokeHelper invokeHelper;
    /**项目名称*/
    private TextView tv_item_name;
    /**办理机关*/
    private TextView tv_actualize_department;
    /**办理时限*/
    private TextView tv_transact_time_limit;
    /**收费标准*/
    private TextView tv_fee_scale;
    /**到办事窗口最少次数*/
    private TextView tv_degree;
    /**咨询电话*/
    private TextView tv_refer_phone;
    /**监督投诉电话*/
    private TextView tv_monitor_phone;
    /**办理时间、地点*/
    private TextView tv_transact_place;
    /**在线申报、咨询、收藏*/
    private LinearLayout ll_online_booking, ll_online_declare, ll_route, ll_collect;
    private TextView tv_online_booking;
    private TextView tv_online_declare;
    private TextView tv_collect;
    private String transact_condition;
    private String setting_gist;
    private List<MaterialsInfo> materialsInfoList;
    private String itemCode;
    private String sxlx;
    private String isApp;

    private String shortName;
    private String siteno;
    private String yybl;
    private ScrollView sv_detail;
    private boolean isAppointed;
    private boolean isCollecting;
    private Dialog mapDialog;
    private String transaction_place;
    private String lat,lng;
    private String endCity = "鹰潭市";
    private TextView route;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_register_deail);
        AutoUtils.auto(this);
        StatusBarUtils.initWindows(this, R.color.blue_main);
        invokeHelper = new InvokeHelper(this);
        type = getIntent().getStringExtra("type");//事项id
        String userType = LoginUtils.getInstance().getUserInfo().getUserType();
        if (!BeanUtils.isEmptyStr(userType)) {
            if ("0".equals(LoginUtils.getInstance().getUserInfo().getUserType())) {
                if (BeanUtils.isEmptyStr(type)) {
                    type = Constants.PERSONAL_WORK_TYPE;
                }
            } else {
                if (BeanUtils.isEmptyStr(type)) {
                    type = Constants.COMPANY_WORK_TYPE;
                }
            }
        }
        item_id = getIntent().getStringExtra("item_id");//事项id
        itemName = getIntent().getStringExtra("item_name");//事项名称
        isAppointed = getIntent().getBooleanExtra("isAppointed", false);//从预约详情传过来的标记
        initView();
        getDetail();
    }

    private void initView() {
        initTitleBar(itemName + "详情", this, null);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        tv_actualize_department = (TextView) findViewById(R.id.tv_actualize_department);
        tv_transact_time_limit = (TextView) findViewById(R.id.tv_transact_time_limit);
        tv_fee_scale = (TextView) findViewById(R.id.tv_fee_scale);
        tv_degree = (TextView) findViewById(R.id.tv_degree);
        tv_refer_phone = (TextView) findViewById(R.id.tv_refer_phone);
        tv_monitor_phone = (TextView) findViewById(R.id.tv_monitor_phone);
        tv_transact_place = (TextView) findViewById(R.id.tv_transact_place);
        route = (TextView) findViewById(R.id.route);
        sv_detail = (ScrollView) findViewById(R.id.sv_detail);
        sv_detail.smoothScrollTo(0, 0);
        tv_sltj = (TextView) findViewById(R.id.tv_sltj);
        tv_sltj.setOnClickListener(this);
        tv_sxcl = (TextView) findViewById(R.id.tv_sxcl);
        tv_sxcl.setOnClickListener(this);
        tv_sfyj = (TextView) findViewById(R.id.tv_sfyj);
        tv_sfyj.setOnClickListener(this);
        tv_flyj = (TextView) findViewById(R.id.tv_flyj);
        tv_flyj.setOnClickListener(this);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        ll_online_booking = (LinearLayout) findViewById(R.id.ll_online_booking);
        ll_online_booking.setOnClickListener(this);
        ll_online_declare = (LinearLayout) findViewById(R.id.ll_online_declare);
        tv_online_booking = (TextView) findViewById(R.id.tv_online_booking);
        tv_online_declare = (TextView) findViewById(R.id.tv_online_declare);
        ll_online_declare.setOnClickListener(this);
        ll_route = (LinearLayout) findViewById(R.id.ll_route);
        ll_route.setOnClickListener(this);
        ll_collect = (LinearLayout) findViewById(R.id.ll_collect);
        ll_collect.setOnClickListener(this);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
    }

    /**
     * 初始化title
     */
    public void initTitleBar(String titleRes, OnClickListener backListener, OnClickListener rightListener) {
        backBtn = (ImageView) findViewById(R.id.system_back);
        rightBtn = (ImageView) findViewById(R.id.btn_top_right);
        customBtn = (ImageView) findViewById(R.id.btn_custom_right);
        titleTxt = (TextView) findViewById(R.id.tv_top_title);
        if (!StringUtils.isNullOrEmpty(titleRes)) {
            titleTxt.setText(titleRes);
        }
        if (null != backListener) {
            backBtn.setOnClickListener(backListener);
            backBtn.setVisibility(View.VISIBLE);
        }
        if (null != rightListener) {
            rightBtn.setOnClickListener(rightListener);
            rightBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (tab1 == null) {
            tab1 = new HJTab1Fragment();
            setData(tab1, transact_condition);
        }
        if (!tab1.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction().add(R.id.content_layout, tab1).commit();
            // 记录当前Fragment
            currentFragment = tab1;
        }
    }

    private void setData(Fragment tab, Object value) {
        Bundle bundle = new Bundle();
        if (value instanceof String) {
            bundle.putString("str", (String) value);
        } else if (value instanceof Collection) {
            bundle.putSerializable("material", (Serializable) value);
        }
        tab.setArguments(bundle);
    }

    /**
     * 列表详情请求
     */
    private void getDetail() {
        ApiRequest request = OAInterface.getItem(item_id);
        invokeHelper.invokeWidthDialog(request, callBack, GET_ITEM_REQ);
    }

    /**
     * 获取事项浏览接口
     */
    private void readerItem() {
        ApiRequest request = OAInterface.readerItem(item_id, type);
        invokeHelper.invoke(request, callBack, READER_ITEM_REQ);
    }

    /**
     * 事项收藏请求
     */
    private void getCollectItem() {
        ApiRequest request = OAInterface.collectItem(itemCode);
        invokeHelper.invoke(request, callBack, GET_COLLECT_ITEM_REQ);
    }

    /**
     * 事项取消收藏请求
     */
    private void cancelCollectionItem() {
        ApiRequest request = OAInterface.cancelCollectionItem(itemCode);
        invokeHelper.invoke(request, callBack, CANCEL_COLLECT_ITEM_REQ);
    }

    /**
     * 获取列表详情数据解析
     */
    private void showDetail(ResultItem resultItem) {
        String transact_time_limit = resultItem.getString("NORMTIMELIMIT");
        String fee_scale = resultItem.getString("SFJE");
        String is_fee = resultItem.getString("SFSF");
        String degree = resultItem.getString("DEGREE");
        String refer_phone = resultItem.getString("ZXMKZXDH");
        String monitor_phone = resultItem.getString("JDTSDH");
        String transaction_time = resultItem.getString("BLSJ");
        transaction_place = resultItem.getString("BLWINDOW");
        String collect = resultItem.getString("SCID");
        shortName = resultItem.getString("NORMACCEPTDEPART");
        siteno = resultItem.getString("NORMACCEPTDEPARTID");
        yybl = resultItem.getString("YYBL");
        isApp = resultItem.getString("ISZXBL");
        itemCode = resultItem.getString("SXBM");
        sxlx = resultItem.getString("SXLX");
        transact_condition = resultItem.getString("TRANSACT_CONDITION");
        setting_gist = resultItem.getString("SDYJ");
        //获取坐标信息
        String coordinate = resultItem.getString("COORDINATE");
        if (!BeanUtils.isEmptyStr(coordinate) && coordinate.contains(",")) {
            String[] strings = coordinate.split(",");
            String lngStr = strings[0];
            String latStr = strings[1];
            if (BeanUtils.checkItude(lngStr, latStr)) {//检验是否为经纬度坐标
                lng = lngStr;
                lat = latStr;
            }
        }
        tv_item_name.setText(itemName);
        tv_actualize_department.setText(shortName);
        tv_transact_time_limit.setText(transact_time_limit);
        tv_fee_scale.setText(is_fee.equals("1") ? fee_scale : "不收费");
        tv_degree.setText(degree);
        tv_refer_phone.setText(refer_phone);
        tv_monitor_phone.setText(monitor_phone);
        tv_transact_place.setText(transaction_time + " 、" + transaction_place);
        tv_collect.setText(BeanUtils.isNullOrEmpty(collect) ? "未收藏" : "已收藏");
        if (BeanUtils.isNullOrEmpty(collect)) {
            collect(tv_collect.getText().toString(), R.drawable.icon_guide_collect);
        } else {
            collect(tv_collect.getText().toString(), R.drawable.icon_guide_collect_selected);
        }
        if (isApp.contains("2")) {
            setBackgroundResource(tv_online_declare, R.drawable.icon_guide_declare);
            tv_online_declare.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
        if ("1".equals(yybl) && !isAppointed) {
            //yybl为1则可以预约
            setBackgroundResource(tv_online_booking, R.drawable.icon_guide_order);
            tv_online_booking.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
        if (!BeanUtils.isEmptyStr(lat) && !BeanUtils.isEmptyStr(lng)) {
            setBackgroundResource(route, R.drawable.icon_guide_route);
            route.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
        initTab();
    }

    private void setBackgroundResource(TextView textView, int icon) {
        Drawable drawable = ContextCompat.getDrawable(FamilyRegisterDetailActivity.this, icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 收藏事项
     */
    private void collect(String collect, int icon) {
        tv_collect.setText(collect);
        setBackgroundResource(tv_collect, icon);
    }

    protected void showMaterial(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            return;
        }
        materialsInfoList = new ArrayList<>();
        for (ResultItem resultItem : items) {
            String materialNecessity = resultItem.getString("BYX");
//			String materialId = resultItem.getString("CL_ID");
            String materialDescribe = resultItem.getString("CLDESCRIBE");
            String materialFormat = resultItem.getString("CLFORMAT");
            String materialCopies = resultItem.getString("CLFS");
            String materialName = resultItem.getString("CLNAME");
            String materialSize = resultItem.getString("CLSIZE");
            String materialForm = resultItem.getString("CLXS");
            String materialType = resultItem.getString("TYPE");
            MaterialsInfo materialsInfo = new MaterialsInfo();
            materialsInfo.setMaterialNecessity(materialNecessity);
//			materialsInfo.setMaterialId(materialId);
            materialsInfo.setMaterialDescribe(materialDescribe);
            materialsInfo.setMaterialFormat(materialFormat);
            materialsInfo.setMaterialName(materialName);
            materialsInfo.setMaterialCopies(materialCopies);
            materialsInfo.setMaterialSize(materialSize);
            materialsInfo.setMaterialForm(materialForm);
            materialsInfo.setMaterialType(materialType);
            materialsInfoList.add(materialsInfo);
        }
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            if (!BeanUtils.isEmpty(response)) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String code = item.getString("code");
                String message = item.getString("message");
                if (Constants.SUCCESS_CODE.equals(code)) {
                    if (what == GET_ITEM_REQ) {
                        ResultItem result = (ResultItem) item.get("ITEM_DATA");
                        showDetail(result);
                        List<ResultItem> items = item.getItems("FILE_DATA");
                        showMaterial(items);
                        if (LoginUtils.getInstance().isLoginSuccess()) {
                            readerItem();
                        }
                    } else if (what == GET_COLLECT_ITEM_REQ) {
                        isCollecting = false;
                        collect("已收藏", R.drawable.icon_guide_collect_selected);
                    } else if (what == CANCEL_COLLECT_ITEM_REQ) {
                        isCollecting = false;
                        collect("未收藏", R.drawable.icon_guide_collect);
                    }

                } else {
                    DialogUtils.showToast(FamilyRegisterDetailActivity.this, message);
                }
            }
        }

        @Override
        public void onReturnError(HttpResponse response, ResultItem error, int what) {
            super.onReturnError(response, error, what);
            if (what == GET_COLLECT_ITEM_REQ || what == CANCEL_COLLECT_ITEM_REQ) {
                isCollecting = false;
            }
        }

        @Override
        public void onNetError(int what) {
            super.onNetError(what);
            if (what == GET_COLLECT_ITEM_REQ || what == CANCEL_COLLECT_ITEM_REQ) {
                isCollecting = false;
            }
        }
    };

    private void gotoBookingHall() {
        Intent intent = new Intent(FamilyRegisterDetailActivity.this, OnlineBookingHallActivity.class);
        intent.putExtra("NORMACCEPTDEPART", shortName);
        intent.putExtra("ITEMNAME", itemName);
        intent.putExtra("ITEMID", item_id);
        intent.putExtra("SITENO", siteno);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.system_back: // 返回
                finish();
                break;
            case R.id.tv_sltj:
                clickTab1Layout();
                sv_detail.smoothScrollBy(0, 0);
                break;
            case R.id.tv_sxcl:
                clickTab2Layout();
                sv_detail.smoothScrollBy(0, 0);
                break;
            case R.id.tv_sfyj:
                clickTab3Layout();
                sv_detail.smoothScrollBy(0, 0);
                break;
            case R.id.tv_flyj:
                clickTab4Layout();
                sv_detail.smoothScrollBy(0, 0);
                break;
            case R.id.ll_online_booking:// 在线预约
                if (isAppointed) { //如果从预约详情界面就不让再次预约
                    DialogUtils.showToast(FamilyRegisterDetailActivity.this, "该事项已预约");
                    return;
                }
                if (LoginUtils.getInstance().isLoginSuccess()) {
                    // 判断用户是否实名认证进行办事事项的拦截
                    if (VerificationUtils.isAuthentication(FamilyRegisterDetailActivity.this)) {
                        if (!BeanUtils.isEmptyStr(sxlx) && !"3".equals(sxlx)) {//sxlx 不为空则说明该事项可以办理;不为“3”则说明不是部门事项
                            String type = "0".equals(LoginUtils.getInstance().getUserInfo().getUserType()) ? Constants.PERSONAL_WORK_TYPE : Constants.COMPANY_WORK_TYPE;
                            if (sxlx.contains(type)) {//包含则说明此用户类型可以办理该事项
                                if ("1".equals(yybl)) {//yybl为“1”则可以预约
                                    gotoBookingHall();
                                } else {//yybl不为“1”则不可以预约
                                    DialogUtils.showToast(FamilyRegisterDetailActivity.this, "该事项不能预约");
                                }
                            } else {//不包含则说明此用户类型不可以办理该事项
                                String userType = Constants.PERSONAL_WORK_TYPE.equals(type) ? "个人" : "企业";
                                DialogUtils.showToast(FamilyRegisterDetailActivity.this, "您是" + userType + "用户，请办理" + userType + "事项");
                            }
                        } else {//sxlx 为空或为“3”则说明该事项不能办理
                            DialogUtils.showToast(FamilyRegisterDetailActivity.this, "该事项不能预约");
                        }
                    }
                } else {
                    // 没有登录则让用户先去登录
                    LoginUtils.getInstance().checkNeedLogin(FamilyRegisterDetailActivity.this, true, getString(R.string.declare_tips), REQUEST_CODE);
                }
                break;
                //在线申办
            case R.id.ll_online_declare:
                if (LoginUtils.getInstance().isLoginSuccess()) {
                    // 判断用户是否实名认证进行办事事项的拦截
                    if (VerificationUtils.isAuthentication(FamilyRegisterDetailActivity.this)) {
                        if (!BeanUtils.isEmptyStr(sxlx)) {
                            String itemType = "0".equals(LoginUtils.getInstance().getUserInfo().getUserType()) ? Constants.PERSONAL_WORK_TYPE : Constants.COMPANY_WORK_TYPE;
                            if (BeanUtils.isEmptyStr(type)) {
                                type = itemType;
                            }
                            if (sxlx.contains(itemType)) {
                                //包含则说明此用户类型可以办理该事项
                                if (isApp.contains("2")) {
                                    List<UserInfo.CompanyInfo> companyInfoList = LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST();
                                    if (Constants.PERSONAL_WORK_TYPE.equals(type)) {
                                        gotoActivity(DeclareNoticeActivity.class, type);
                                    } else {
                                        if (!BeanUtils.isEmpty(companyInfoList)) {
                                            if ("1".equals(LoginUtils.getInstance().getUserInfo().getUserType())) {
                                                if (companyInfoList.size() == 1) {
                                                    gotoDeclareNotice(type, companyInfoList);
                                                } else {
                                                    gotoActivity(EnterpriseInformationActivity.class, type);
                                                }
                                            } else {
                                                DialogUtils.showToast(FamilyRegisterDetailActivity.this, "您是个人用户，请办理个人事项");
                                            }
                                        } else {
                                            gotoActivity(DeclareNoticeActivity.class, type);
                                        }
                                    }
                                } else {
                                    DialogUtils.showToast(FamilyRegisterDetailActivity.this, "该事项只能在网上办理或窗口办理");
                                }
                            } else {//不包含则说明此用户类型不可以办理该事项
                                String userType = "2".equals(itemType) ? "个人" : "企业";
                                DialogUtils.showToast(FamilyRegisterDetailActivity.this, "您是" + userType + "用户，请办理" + userType + "事项");
                            }
                        } else {
                            DialogUtils.showToast(FamilyRegisterDetailActivity.this, "该事项不能办理");
                        }
                    }
                } else {
                    // 没有登录则让用户先去登录
                    LoginUtils.getInstance().checkNeedLogin(FamilyRegisterDetailActivity.this, true, getString(R.string.declare_tips), REQUEST_CODE);
                }
                break;
            case R.id.ll_route://路线
                if (BeanUtils.isEmptyStr(lat) || BeanUtils.isEmptyStr(lng)) {
                    DialogUtils.showToast(this, "暂未获取办事地点信息");
                    return;
                }
                showSelectMapDialog();
                break;
            case R.id.ll_collect:
                if (LoginUtils.getInstance().isLoginSuccess()) {
                    if (VerificationUtils.isAuthentication(FamilyRegisterDetailActivity.this)) {
                        if (isCollecting) {
                            DialogUtils.showToast(FamilyRegisterDetailActivity.this, "处理中...请稍后！");
                        } else {
                            isCollecting = true;
                            if (tv_collect.getText().toString().equals("已收藏")) {
                                cancelCollectionItem();
                            } else {
                                getCollectItem();
                            }
                        }
                    }
                } else {
                    // 没有登录则让用户先去登录
                    LoginUtils.getInstance().checkNeedLogin(FamilyRegisterDetailActivity.this, true, getString(R.string.declare_tips), REQUEST_CODE);
                }
                break;
            default:
                break;
        }
    }

    private void showSelectMapDialog() {
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_map_select, null);
        List<PackageInfo> installedMap = setInstalledMap(this, root);
        if (installedMap.size() == 0) {//当本地没有安装地图应用时跳转百度web地图
            gotoBaiduWebMap();
            return;
        } else if (installedMap.size() == 1) {//当本地安装了一个地图应用时跳转到该地图应用
            openMap(installedMap.get(0).packageName);
            return ;
        }
        if (mapDialog == null) {
            mapDialog = new Dialog(this, R.style.my_dialog);
            mapDialog.setContentView(root);
            Window dialogWindow = mapDialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
            WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            lp.x = 0; // 新位置X坐标
            lp.y = -20; // 新位置Y坐标
            lp.width = getResources().getDisplayMetrics().widthPixels; // 宽度
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
            root.measure(0, 0);
            lp.alpha = 9f; // 透明度
            dialogWindow.setAttributes(lp);
            mapDialog.setCanceledOnTouchOutside(true);
            mapDialog.show();
        } else {
            if (mapDialog.isShowing()) {
                mapDialog.dismiss();
            } else {
                mapDialog.setContentView(root);//重新设置布局
                mapDialog.show();
            }
        }
    }

    private void gotoBaiduWebMap() {
//            String url = "http://api.map.baidu.com/direction?origin=latlng:" + curLat + "," + curLng + "|name:我的位置" +
//                    "&destination=latlng:" + lat + "," + lng + "|name:" + transaction_place + "&mode=driving&origin_region="
//                    + curCity +"&destination_region=" + endCity +"&output=html&src=webapp.baidu.openAPIdemo";
        startActivity(new Intent(FamilyRegisterDetailActivity.this, PublicWebViewActivity.class)
                .putExtra("isRoute", true)
                .putExtra("lat", lat)
                .putExtra("lng", lng)
                .putExtra("transaction_place", transaction_place)
                .putExtra("title", "路线"));
    }

    private String gdPackageName = "com.autonavi.minimap";
    private String bdPackageName = "com.baidu.BaiduMap";
    private String txPackageName = "com.tencent.map";
    /**
     * 检查手机上是否安装了指定的软件
     */
    private List<PackageInfo> setInstalledMap(Context context, View root){
        ImageView icon1 = (ImageView) root.findViewById(R.id.icon1);
        ImageView icon2 = (ImageView) root.findViewById(R.id.icon2);
        ImageView icon3 = (ImageView) root.findViewById(R.id.icon3);
        TextView app_name1 = (TextView) root.findViewById(R.id.app_name1);
        TextView app_name2 = (TextView) root.findViewById(R.id.app_name2);
        TextView app_name3 = (TextView) root.findViewById(R.id.app_name3);
        View map1 = root.findViewById(R.id.map1);
        View map2 = root.findViewById(R.id.map2);
        View map3 = root.findViewById(R.id.map3);
        //获取packagemanager
        PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        final List<PackageInfo> mapPackages = new ArrayList<>();
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                if (packName.equals(gdPackageName) || packName.contains(bdPackageName) || packName.contains(txPackageName)) {
                    mapPackages.add(packageInfos.get(i));
                }
            }
        }
        if (mapPackages.size() >= 2) {
            map1.setVisibility(View.VISIBLE);
            map2.setVisibility(View.VISIBLE);
            map1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMap(mapPackages.get(0).packageName);
                }
            });
            map2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMap(mapPackages.get(1).packageName);
                }
            });
            icon1.setImageDrawable(mapPackages.get(0).applicationInfo.loadIcon(packageManager));
            app_name1.setText(mapPackages.get(0).applicationInfo.loadLabel(packageManager).toString());
            icon2.setImageDrawable(mapPackages.get(1).applicationInfo.loadIcon(packageManager));
            app_name2.setText(mapPackages.get(1).applicationInfo.loadLabel(packageManager).toString());
        }
        if (mapPackages.size() == 3) {
            map3.setVisibility(View.VISIBLE);
            map3.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMap(mapPackages.get(2).packageName);
                }
            });
            icon3.setImageDrawable(mapPackages.get(2).applicationInfo.loadIcon(packageManager));
            app_name3.setText(mapPackages.get(2).applicationInfo.loadLabel(packageManager).toString());
        }
        return mapPackages;
    }

    /**
     * 打开地图
     * @param packageName
     */
    private void openMap(String packageName) {
        if (packageName.equals(gdPackageName)) {
            openGaodeMap();
        } else if (packageName.equals(bdPackageName)) {
            openBaiduMap();
        } else if (packageName.equals(txPackageName)) {
            openTencentMap();
        }
    }
    /**
     * 打开高德地图
     */
    private void openGaodeMap() {
        CoodinateCovertor.LngLat lngLat_bd = new CoodinateCovertor.LngLat(Double.valueOf(lng),Double.valueOf(lat));
        CoodinateCovertor.LngLat lngLat = CoodinateCovertor.bd_decrypt(lngLat_bd);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            Uri uri = Uri.parse("amapuri://route/plan/?did=BGVIS2&dlat="+ lngLat.getLantitude() +"&dlon=" + lngLat.getLongitude() +"&dname=" + transaction_place + "&dev=0&t=0");
            intent.setData(uri);
            startActivity(intent);
    }

    /**
     * 打开百度地图
     */
    private void openBaiduMap() {
        try {
            Intent intent = Intent.getIntent("intent://map/direction?" +
                    "destination=latlng:" + lat + "," + lng + "|name:"  + transaction_place +        //终点
                    "&mode=driving&" +          //导航路线方式
                    "&src=appname#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            Log.e("intent", e.getMessage());
        }
    }

    /**
     * 打开腾讯地图
     */
    private void openTencentMap() {
        CoodinateCovertor.LngLat lngLat_bd = new CoodinateCovertor.LngLat(Double.valueOf(lng),Double.valueOf(lat));
        CoodinateCovertor.LngLat lngLat = CoodinateCovertor.bd_decrypt(lngLat_bd);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri = Uri.parse("qqmap://map/routeplan?type=drive&to=" + transaction_place + "&tocoord="
                + lngLat.getLantitude() + "," + lngLat.getLongitude());
        intent.setData(uri);
        startActivity(intent);
    }
    /**
     * 跳转到申报须知界面
     */
    private void gotoDeclareNotice(String itemType, List<UserInfo.CompanyInfo> companyInfoList) {
        Intent intent = new Intent(FamilyRegisterDetailActivity.this, DeclareNoticeActivity.class);
        intent.putExtra("type", itemType);
        intent.putExtra("item_id", item_id);
        intent.putExtra("position", "0");
        intent.putExtra("companyId", BeanUtils.isEmpty(companyInfoList) ? "" : companyInfoList.get(0).getID());
        startActivity(intent);
    }

    /**
     * 跳转不同界面传参
     */
    private void gotoActivity(Class c, String itemType) {
        Intent intent = new Intent(FamilyRegisterDetailActivity.this, c);
        intent.putExtra("type", itemType);
        intent.putExtra("item_id", item_id);
        intent.putExtra("position", "0");
        startActivity(intent);
    }

    /**
     * 点击第一个tab
     */
    @SuppressLint("NewApi")
    private void clickTab1Layout() {
        if (tab1 == null) {
            tab1 = new HJTab1Fragment();
            setData(tab1, transact_condition);
        }
        tv_sltj.setTextColor(Color.parseColor("#333333"));
        tv_sxcl.setTextColor(Color.parseColor("#666666"));
        tv_sfyj.setTextColor(Color.parseColor("#666666"));
        tv_flyj.setTextColor(Color.parseColor("#666666"));
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), tab1);
    }

    /**
     * 点击第二个tab
     */
    @SuppressLint("NewApi")
    private void clickTab2Layout() {
        if (tab2 == null) {
            tab2 = new HJTab2Fragment();
            setData(tab2, materialsInfoList);
        }
        tv_sltj.setTextColor(Color.parseColor("#666666"));
        tv_sxcl.setTextColor(Color.parseColor("#333333"));
        tv_sfyj.setTextColor(Color.parseColor("#666666"));
        tv_flyj.setTextColor(Color.parseColor("#666666"));
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.VISIBLE);
        view3.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), tab2);
    }

    /**
     * 点击第三个tab
     */
    @SuppressLint("NewApi")
    private void clickTab3Layout() {
        if (tab3 == null) {
            tab3 = new HJTab3Fragment();
            setData(tab3, tv_fee_scale.getText().toString());
        }

        tv_sltj.setTextColor(Color.parseColor("#666666"));
        tv_sxcl.setTextColor(Color.parseColor("#666666"));
        tv_sfyj.setTextColor(Color.parseColor("#333333"));
        tv_flyj.setTextColor(Color.parseColor("#666666"));
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.VISIBLE);
        view4.setVisibility(View.GONE);
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), tab3);
    }

    @SuppressLint("NewApi")
    private void clickTab4Layout() {
        if (tab4 == null) {
            tab4 = new HJTab4Fragment();
            setData(tab4, setting_gist);
        }
        tv_sltj.setTextColor(Color.parseColor("#666666"));
        tv_sxcl.setTextColor(Color.parseColor("#666666"));
        tv_sfyj.setTextColor(Color.parseColor("#666666"));
        tv_flyj.setTextColor(Color.parseColor("#333333"));
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
        view4.setVisibility(View.VISIBLE);
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), tab4);
    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == null || currentFragment == fragment)
            return;
        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_CODE) {
            readerItem();
        }
    }
}
