package com.powerrich.office.oa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.Interaction.OnMultiClickListener;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.MaterialsInfo;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.bean.WorkClBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.VerificationUtils;
import com.powerrich.office.oa.view.VerifyWorkDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * 办事指南最新的界面
 * 2018-09-26
 */
public class WorkGuideNewActivity extends BaseActivity implements View.OnClickListener {


    private RelativeLayout titleLayout;
    private ImageView btnTopRight;
    private TextView tvTopRight;
    private ImageView btnCustomRight;
    private LinearLayout llZxsb;
    private LinearLayout llZxyy;
    private TextView tvFwdx;
    private TextView tvSfsf;
    private LinearLayout llSfbz;
    private TextView sfbz;
    private TextView tvFdbjqx;
    private TextView tvCnbjqx;
    private LinearLayout llBlsj;
    private LinearLayout llBltj;
    private LinearLayout llBlcl;
    private LinearLayout llBllc;
    private LinearLayout llBlyj;

    ImageView systemBack, btn_custom_right_cc;
    //是否是收藏
    private boolean isCollect;
    TextView tvTopTitle, tv_zxsb, tv_work_title, tv_work_unit;


    private final int GET_ITEM_REQ = 0;
    private final int GET_COLLECT_ITEM_REQ = 1;
    private final int CANCEL_COLLECT_ITEM_REQ = 2;
    private final int READER_ITEM_REQ = 3;
    private final int REQUEST_CODE = 111;

    private List<MaterialsInfo> materialsInfoList;
    private WorkClBean workClBean = new WorkClBean();
    ;


    //  -------------------------------------- Intent传入的值--------------------------------------------------------

    private String unitStr;
    private String item_id;
    private String itemName;
    //1法人事项，2个人事项
    private String type;
    // 判断首页点击的是哪个办事  type类型2.个人办事   type类型1.企业办事   0 搜索进来的
    private String typeHome = "0";

    //  -------------------------------------- 解析成员变量--------------------------------------------------------
    private String shortName;
    private String siteno;
    private String yybl;
    //是否可以在线申报
    private String isApp;

    private String itemCode;
    //1：法人2：自然人
    private String sxlx;
    private String transact_condition;
    private String setting_gist;
    private String collect;


    private String transaction_time;
    private String transaction_place;
    private String lat, lng;



    @Override
    protected int provideContentViewId() {
        return R.layout.activity_work_guide_new;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isAutoFlag = false;
        super.onCreate(savedInstanceState);
        titleLayout = findViewById(R.id.title_layout);
        AutoUtils.auto(titleLayout);
        initIntent();
        initView();
        getDetail();
    }

    private void initView() {

        btn_custom_right_cc = generateFindViewById(R.id.btn_custom_right_cc);
        btn_custom_right_cc.setVisibility(View.VISIBLE);

        btn_custom_right_cc.setOnClickListener(new OnMultiClickListener() {
            @Override
            protected void onMultiClick(View v) {
                if (LoginUtils.getInstance().isLoginSuccess()) {
                    if (VerificationUtils.isAuthentication(WorkGuideNewActivity.this)) {
                        //已收藏
                        if (isCollect) {
                            cancelCollectionItem();
                        } else {
                            getCollectItem();
                        }
                    }
                } else {
                    // 没有登录则让用户先去登录
                    LoginUtils.getInstance().checkNeedLogin(WorkGuideNewActivity.this, true, getString(R.string.declare_tips), REQUEST_CODE);
                }

            }
        });


        tv_work_title = (TextView) findViewById(R.id.tv_work_title);
        tv_work_unit = (TextView) findViewById(R.id.tv_work_unit);

        tv_work_unit.setText(unitStr);
        tv_work_title.setText(itemName);

        tvTopTitle = (TextView) findViewById(R.id.tv_top_title);
        tvTopTitle.setText("办事指南");
        tv_zxsb = (TextView) findViewById(R.id.tv_zxsb);

        systemBack = (ImageView) findViewById(R.id.system_back);
        systemBack.setVisibility(View.VISIBLE);
        systemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnTopRight = (ImageView) findViewById(R.id.btn_top_right);
        tvTopRight = (TextView) findViewById(R.id.tv_top_right);
        btnCustomRight = (ImageView) findViewById(R.id.btn_custom_right);
        llZxsb = (LinearLayout) findViewById(R.id.ll_zxsb);
        llZxyy = (LinearLayout) findViewById(R.id.ll_zxyy);
        tvFwdx = (TextView) findViewById(R.id.tv_fwdx);
        tvSfsf = (TextView) findViewById(R.id.tv_sfsf);
        llSfbz = (LinearLayout) findViewById(R.id.ll_sfbz);

        llZxsb.setOnClickListener(this);
        llZxyy.setOnClickListener(this);
        llSfbz.setOnClickListener(this);

        sfbz = (TextView) findViewById(R.id.sfbz);
        tvFdbjqx = (TextView) findViewById(R.id.tv_fdbjqx);
        tvCnbjqx = (TextView) findViewById(R.id.tv_cnbjqx);
        llBlsj = (LinearLayout) findViewById(R.id.ll_blsj);
        llBltj = (LinearLayout) findViewById(R.id.ll_bltj);
        llBlcl = (LinearLayout) findViewById(R.id.ll_blcl);
        llBllc = (LinearLayout) findViewById(R.id.ll_bllc);
        llBlyj = (LinearLayout) findViewById(R.id.ll_blyj);

        llBlsj.setOnClickListener(this);
        llBltj.setOnClickListener(this);
        llBlcl.setOnClickListener(this);
        llBllc.setOnClickListener(this);
        llBlyj.setOnClickListener(this);
    }

    private void initIntent() {
        unitStr = getIntent().getStringExtra("UNITSTR");//事项单位
        item_id = getIntent().getStringExtra("item_id");//事项id
        itemName = getIntent().getStringExtra("item_name");//事项名称


        type = getIntent().getStringExtra("type");
        typeHome = getIntent().getStringExtra("typeHome");
        if(TextUtils.isEmpty(typeHome)){
            typeHome = "0";
        }

        i(this + "type:" + type+"-typeHome:"+typeHome);
    }

    /**
     * 列表详情请求
     */
    private void getDetail() {
        ApiRequest request = OAInterface.getItem(item_id);
        invoke.invokeWidthDialog(request, callBack, GET_ITEM_REQ);
    }

    /**
     * 获取事项浏览接口
     */
    private void readerItem() {
        ApiRequest request = OAInterface.readerItem(item_id, type);
        invoke.invoke(request, callBack, READER_ITEM_REQ);
    }

    /**
     * 事项取消收藏请求
     */
    private void cancelCollectionItem() {
        ApiRequest request = OAInterface.cancelCollectionItem(itemCode);
        invoke.invoke(request, callBack, CANCEL_COLLECT_ITEM_REQ);
    }


    /**
     * 事项收藏请求
     */
    private void getCollectItem() {
        ApiRequest request = OAInterface.collectItem(itemCode);
        invoke.invoke(request, callBack, GET_COLLECT_ITEM_REQ);
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

                        List<ResultItem> items = item.getItems("FILE_DATA");
                        showMaterial(items);
                        ResultItem flow_chart = (ResultItem) item.get("FLOW_CHART");
                        showFlow(flow_chart);

                        ResultItem bsznResult = (ResultItem) item.get("BSZN_DATA");
                        bsznDetail(bsznResult);
                        ResultItem result = (ResultItem) item.get("ITEM_DATA");
                        showItemData(result);

                        ResultItem resultBs = (ResultItem) item.get("BSZN_DATA");
                        showDetail(resultBs);



                        if (LoginUtils.getInstance().isLoginSuccess()) {
                            readerItem();
                        }
                    } else if (what == GET_COLLECT_ITEM_REQ) {
                        isCollect = true;
                        collect(R.drawable.icon_guide_collect_selected);
                    } else if (what == CANCEL_COLLECT_ITEM_REQ) {
                        isCollect = false;
                        collect(R.drawable.icon_guide_collect);
                    }

                } else {
                    DialogUtils.showToast(WorkGuideNewActivity.this, message);
                }
            }
        }

        @Override
        public void onReturnError(HttpResponse response, ResultItem error, int what) {
            super.onReturnError(response, error, what);
            if (what == GET_COLLECT_ITEM_REQ || what == CANCEL_COLLECT_ITEM_REQ) {
                // isCollecting = false;
            }
        }

        @Override
        public void onNetError(int what) {
            super.onNetError(what);
            if (what == GET_COLLECT_ITEM_REQ || what == CANCEL_COLLECT_ITEM_REQ) {
                //isCollecting = false;
            }
        }
    };


    private void showFlow(ResultItem items) {


        workClBean.setFILENAME(items.getString("FILENAME"));
        workClBean.setFJPATH(items.getString("FJPATH"));
        workClBean.setFJZSNAME(items.getString("FJZSNAME"));
        workClBean.setID(items.getString("ID"));
    }

    protected void showMaterial(List<ResultItem> items) {
        materialsInfoList = new ArrayList<>();
        if (BeanUtils.isEmpty(items)) {
            return;
        }
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

    /**
     * 收藏事项
     */
    private void collect(int icon) {
        btn_custom_right_cc.setImageResource(icon);
    }


    private void bsznDetail(ResultItem resultItem) {
        collect = resultItem.getString("SCID");
        if (TextUtils.isEmpty(unitStr)) {
            tv_work_unit.setText(resultItem.getString("NORMACCEPTDEPART"));

        }

        transact_condition = resultItem.getString("TRANSACT_CONDITION");
        setting_gist = resultItem.getString("SDYJ");
        i("依据：" + setting_gist);

        transaction_time = resultItem.getString("BLSJ");
        transaction_place = resultItem.getString("BLWINDOW");
        //  获取坐标信息
        String coordinate = resultItem.getString("COORDINATE");
        i("坐标：" + coordinate);
        if (!BeanUtils.isEmptyStr(coordinate) && coordinate.contains(",")) {
            String[] strings = coordinate.split(",");
            String lngStr = strings[0];
            String latStr = strings[1];
         //   if (BeanUtils.checkItude(lngStr, latStr)) {//检验是否为经纬度坐标
                lng = lngStr;
                lat = latStr;
//            } else {
//                lat = coordinate;
//            }
        }

        isCollect = BeanUtils.isNullOrEmpty(collect) ? false : true;
        btn_custom_right_cc.setImageResource(BeanUtils.isNullOrEmpty(collect) ? R.drawable.icon_guide_collect : R.drawable.icon_guide_collect_selected);

    }


    private void showItemData(ResultItem resultItem){
        isApp = resultItem.getString("ISZXBL");
        // 是App
        if (isApp.contains("2")) {
            llZxsb.setBackgroundResource(R.drawable.work_shape_button);
            tv_zxsb.setTextColor(getResources().getColor(R.color.white));
        }
        //0:窗口办理，1：网上办理
        else {
            llZxsb.setBackgroundResource(R.drawable.work_shape_button_gray);
            tv_zxsb.setTextColor(getResources().getColor(R.color.text_gray_color));
        }
    }

    /**
     * 获取列表详情数据解析
     */
    private void showDetail(ResultItem resultItem) {
        //  -------------------------------------- 解析服务器的json--------------------------------------------------------
        String transact_time_limit = resultItem.getString("NORMTIMELIMIT");

        Log.i("jsc", "showDetail: "+transact_time_limit);

        String cnbjsxStr = resultItem.getString("CNBJSX");

        String fee_scale = resultItem.getString("SFJE");
        String is_fee = resultItem.getString("SFSF"); // 0：否1：是
        String degree = resultItem.getString("DEGREE");
        String refer_phone = resultItem.getString("ZXMKZXDH");
        String monitor_phone = resultItem.getString("JDTSDH");



        collect = resultItem.getString("SCID");

        isCollect = BeanUtils.isNullOrEmpty(collect) ? false : true;
        btn_custom_right_cc.setImageResource(BeanUtils.isNullOrEmpty(collect) ? R.drawable.icon_guide_collect : R.drawable.icon_guide_collect_selected);

        shortName = resultItem.getString("NORMACCEPTDEPART");
        siteno = resultItem.getString("NORMACCEPTDEPARTID");
        yybl = resultItem.getString("YYBL");

        itemCode = resultItem.getString("SXBM");
        //办事对象
        sxlx = resultItem.getString("BSDX");



        //  -------------------------------------- 根据解析操作UI--------------------------------------------------------
        // type = sxlx;

        // * 是否收费 否
        if (is_fee.equals("1")) {
            tvSfsf.setText("是");
            llSfbz.setVisibility(View.VISIBLE);
            i("收费金额：" + fee_scale);
            sfbz.setText(fee_scale);
        } else {
            llSfbz.setVisibility(View.GONE);
            tvSfsf.setText("否");
        }
        // * 事项类型
        if (sxlx.equals("1")) {//事项类型：1企业事项，2个人事项
            tvFwdx.setText("企业");
            if (BeanUtils.isEmptyStr(type)) {
                type = "1";     //从预约列表进来的type为空，需重新定义
            }
        } else if (sxlx.contains(",")) {
            tvFwdx.setText("个人和企业");
            if (BeanUtils.isEmptyStr(type)) {
                String userType = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();//（0个人，1企业，2管理员）
                type = "1".equals(userType) ? "1" : "2";//从预约列表进来的type为空，需重新定义
            }
        } else if (sxlx.equals("2")) {
            tvFwdx.setText("个人");
            if (BeanUtils.isEmptyStr(type)) {
                type = "2";     //从预约列表进来的type为空，需重新定义
            }
        } else {//sxlx为空时
            tvFwdx.setText("无");
        }

        // * 法定办结期限
        tvFdbjqx.setText((TextUtils.isEmpty(transact_time_limit) ? "0" : transact_time_limit) + "工作日");
        // * 承诺办结期限
        tvCnbjqx.setText((TextUtils.isEmpty(cnbjsxStr) ? "0" : cnbjsxStr) + "工作日");




        // * 承诺办结期限
        // tvCnbjqx.setText(transact_time_limit);
    }


    /**
     * 1.在线申请  2.在线预约
     * @param typeIntent
     */
    private void zxsbIntent(int typeIntent) {
        i("用户类型：" + LoginUtils.getInstance().getUserInfo().getUserType()+"-type:"+type);
        //   String itemType = "0".equals(LoginUtils.getInstance().getUserInfo().getUserType()) ? Constants.PERSONAL_WORK_TYPE : Constants.COMPANY_WORK_TYPE;
//        List<UserInfo.CompanyInfo> companyInfoList = LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST();
//        gotoDeclareNotice(type, companyInfoList, item_id);
        if (typeIntent == 1) {
            List<UserInfo.CompanyInfo> companyInfoList = LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST();
            gotoDeclareNotice(type, companyInfoList, item_id);
         //   gotoDeclareNotice( Constants.COMPANY_WORK_TYPE,LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST(),item_id);
        } else {
            goZxSubscrib(type);
        }



    }

    /**
     * 在线跳转
     * type 1 在线申请 2在线预约
     */
    private void zxIntent(String item_id, int typeIntent) {
        if (LoginUtils.getInstance().isLoginSuccess()) {
            // 判断用户是否实名认证进行办事事项的拦截
            if (VerificationUtils.isAuthentication(this)) {


                //判断是企业还是个人
                String userType = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                i("用户类型：" + userType + "-服务对象:" + sxlx);

                //如果首页是从个人进来的
                if (typeHome.equals(Constants.PERSONAL_WORK_TYPE)) {
                    //当前用户为个人
                    if(userType.equals("0")){
                        zxsbIntent(typeIntent);
                    }else{
                        DialogUtils.showToast(WorkGuideNewActivity.this, "您是法人，请办理企业事项");
                        return;
                    }
                    //如果首页是从法人进来的
                } else if (typeHome.equals(Constants.COMPANY_WORK_TYPE)) {
                    //当前用户为个人
                    if(userType.equals("0")){
                        DialogUtils.showToast(WorkGuideNewActivity.this, "您是个人用户，请办理个人事项");
                        return;
                    }else{
                       // gotoActivity(EnterpriseInformationActivity.class, Constants.COMPANY_WORK_TYPE, String.valueOf(typeIntent));
                     //   gotoDeclareNotice( Constants.COMPANY_WORK_TYPE,LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST(),item_id);
                        zxsbIntent(typeIntent);
                    }
                }

//                //当前用户为法人 且 服务对象是否即为个人也是企业
//                else if (userType.equals("1") && !BeanUtils.isEmptyStr(sxlx) && sxlx.contains(",")) {
//                    showCustDialog(typeIntent);
//                }

                //  当前用户为个人 且 服务对象是企业
                else if (userType.equals("0") && !BeanUtils.isEmptyStr(sxlx) && sxlx.equals("1")) {
                    DialogUtils.showToast(this, "您是个人用户，请办理个人事项");
                    //  当前用户为法人 且 服务对象是个人
                }else if(userType.equals("1") && !BeanUtils.isEmptyStr(sxlx) && sxlx.equals("2")){
                    DialogUtils.showToast(this, "您是法人，请办理企业事项");
                } else {
                    zxsbIntent(typeIntent);
                }

            }

        } else {
            // 没有登录则让用户先去登录
            LoginUtils.getInstance().checkNeedLogin(this, true, getString(R.string.declare_tips), REQUEST_CODE);
        }

    }

    /**
     * 选择个人还是企业
     * @param typeIntent  1 在线申请 2在线预约
     */
    private void showCustDialog(final int typeIntent) {
        final VerifyWorkDialog dialog = new VerifyWorkDialog(this);
        if (dialog.isShowing()) {
            return;
        }
        if(typeIntent==1){
            dialog.setTitle("请选择申报方式");
        }else{
            dialog.setTitle("请选择预约方式");
        }

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(final DialogInterface d, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                        finish();
                    }
                }
                return false;
            }
        });
        dialog.setCancelable(false);
        dialog.show();

        dialog.setClickListener(new VerifyWorkDialog.ClickListenerInterface() {
            @Override
            public void cancel() {
                dialog.dismiss();
            }

            //企业
            @Override
            public void verifyNomal() {
                dialog.dismiss();
//                if(typeIntent==1){   }else{
//                    goZxSubscrib(Constants.COMPANY_WORK_TYPE);
//                }
              //  gotoActivity(EnterpriseInformationActivity.class, Constants.COMPANY_WORK_TYPE, String.valueOf(typeIntent));
                gotoDeclareNotice( Constants.COMPANY_WORK_TYPE,LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST(),item_id);

            }

            //个人
            @Override
            public void verifyEid() {
                dialog.dismiss();
                /**
                 * 在线申报
                 */
                if (typeIntent == 1) {
                    List<UserInfo.CompanyInfo> companyInfoList = LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST();
                    gotoDeclareNotice(Constants.PERSONAL_WORK_TYPE, companyInfoList, item_id);
                } else {
                    goZxSubscrib(Constants.PERSONAL_WORK_TYPE);
                }

            }
        });
    }


    /**
     * 跳转不同界面传参
     */
    private void gotoActivity(Class c, String itemType, String intentType) {
        Intent intent = new Intent(this, c);
        intent.putExtra("type", itemType);
        intent.putExtra("intentType", intentType);
        intent.putExtra("item_id", item_id);
        intent.putExtra("position", "0");
        startActivity(intent);
        finish();
    }


    /**
     * 在线预约
     */
    private void goZxSubscrib(String type) {
//        if (BeanUtils.isEmptyStr(type)) {//避免type为空跳转到下个界面报错
//            DialogUtils.showToast(this, "该事项暂不支持在线预约");
//            return;
//        }
        Intent intent = new Intent(this, OnlineBookingHallActivity.class);
        intent.putExtra("NORMACCEPTDEPART", shortName);
        intent.putExtra("ITEMNAME", itemName);
        intent.putExtra("ITEMID", item_id);
        intent.putExtra("SITENO", siteno);
        intent.putExtra("type", type);
        //  intent.putExtra("SITENO", item.getSiteNo());
        startActivity(intent);
    }

    /**
     * 跳转到申报须知界面
     */
    private void gotoDeclareNotice(String itemType, List<UserInfo.CompanyInfo> companyInfoList, String item_id) {
        i("申报的type" + itemType);
//        if (BeanUtils.isEmptyStr(type)) {//避免type为空跳转到下个界面报错
//            DialogUtils.showToast(this, "该事项暂不支持在线办理");
//            return;
//        }
        Intent intent = new Intent(this, DeclareNoticeActivity.class);
        intent.putExtra("type", itemType);
        intent.putExtra("item_id", item_id);
        intent.putExtra("position", "0");
        intent.putExtra("companyId", BeanUtils.isEmpty(companyInfoList) ? "" : companyInfoList.get(0).getID());
        intent.putExtra("companyId", BeanUtils.isEmpty(companyInfoList) ? "" : companyInfoList.get(0).getCOMPANYNAME());
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //在线申报
            case R.id.ll_zxsb:

                // 是否登录
                if (LoginUtils.getInstance().isLoginSuccess()) {

                    // 是App
                    if (!BeanUtils.isEmptyStr(isApp) && isApp.contains("2")) {
                        zxIntent(item_id, 1);
                    }
                    //0:窗口办理，1：网上办理
                    else {
                        Toast.makeText(this, "该事项暂不支持在线办理", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 没有登录则让用户先去登录
                    LoginUtils.getInstance().checkNeedLogin(this, true, getString(R.string.declare_tips), REQUEST_CODE);
                }


                break;
            //在线预约
            case R.id.ll_zxyy:
                zxIntent(item_id, 2);

                break;
            //办理时间
            case R.id.ll_blsj:

                Intent intent1 = new Intent(this, WorkTimeActivity.class);
                Log.i("jsc", "onClick: "+transaction_time+"-transaction_place:"+transaction_place);
                intent1.putExtra("TIME", transaction_time);
                intent1.putExtra("LOCAL", transaction_place);
                intent1.putExtra("LAT", lat);
                intent1.putExtra("LNG", lng);
                intent1.putExtra("Type", 1);
                intent1.putExtra("TITLE", "办理时间和地点");
                intent1.putExtra("MaterialsInfo", BeanUtils.isEmpty(materialsInfoList) ? "" : materialsInfoList.toString());
                startActivity(intent1);
                break;
            //办理条件
            case R.id.ll_bltj:
                i("办理条件:" + transact_condition);
                Intent intent2 = new Intent(this, WorkTimeActivity.class);
                intent2.putExtra("Type", 5);
                intent2.putExtra("TITLE", "办理条件");
                intent2.putExtra("YJ", transact_condition);
                startActivity(intent2);
                break;
            //办理材料
            case R.id.ll_blcl:

                Intent intent3 = new Intent(this, WorkTimeActivity.class);
                intent3.putExtra("Type", 3);
                intent3.putExtra("TITLE", "办理材料");
                intent3.putExtra("MaterialsInfo", BeanUtils.isEmpty(materialsInfoList) ? "" : materialsInfoList.toString());
                startActivity(intent3);

                break;
            //办理流程
            case R.id.ll_bllc:

                Intent intent4 = new Intent(this, WorkTimeActivity.class);
                intent4.putExtra("Type", 4);
                intent4.putExtra("TITLE", "办理流程");
                intent4.putExtra("PATH", workClBean.getFJPATH());
                intent4.putExtra("HDPATH", workClBean.getFJZSNAME());
                intent4.putExtra("FILENAME", workClBean.getFILENAME());

                startActivity(intent4);
                break;
            //办理依据
            case R.id.ll_blyj:

                Intent intent5 = new Intent(this, WorkTimeActivity.class);
                intent5.putExtra("Type", 5);
                intent5.putExtra("YJ", setting_gist);
                intent5.putExtra("TITLE", "办理依据");
                startActivity(intent5);
                break;
        }
    }
}
