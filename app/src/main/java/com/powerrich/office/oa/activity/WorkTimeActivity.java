package com.powerrich.office.oa.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.things.PublicWebViewActivity;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.MaterialsInfo;
import com.powerrich.office.oa.fragment.HJTab2Fragment;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.CoodinateCovertor;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.ImageLoad;
import com.yt.simpleframe.view.PinchImageView;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WorkTimeActivity extends BaseActivity {

    private ImageView systemBack;
    private LinearLayout ll_content;
    private TextView tvTopTitle, tv_child_title;
    private View tv_zwsj;
    private PinchImageView mIvFile;
    private TextView tvLocal;
    private TextView tv_local_adress;
    private TextView tvTime;
    private TextView tvMap;
    private TextView tvCall;
    private String time;
    private String local;
    private String transaction_place;
    private String lat, lng;
    //1 .办理时间 2 办理条件 3 办理材料 4 办理流程 5 办理依据
    private int type;
    private Dialog mapDialog;
    private Fragment tab2;
    private List<MaterialsInfo> materialsInfoList;
    private String titleStr;
    private String yjStr;


    String path;
    String hdpath;
    String fileName;
    private RelativeLayout mTitleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isAutoFlag = false;
        super.onCreate(savedInstanceState);
        mTitleLayout = findViewById(R.id.title_layout);
        AutoUtils.auto(mTitleLayout);
        initIntent();
        initView();
        initEvent();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_work_time;
    }

    private void initIntent() {
        Intent intent = getIntent();
        //必传入
        titleStr = intent.getStringExtra("TITLE");
        type = intent.getIntExtra("Type", 0);
        //时间
        time = intent.getStringExtra("TIME");
        local = intent.getStringExtra("LOCAL");
        lat = intent.getStringExtra("LAT");
        lng = intent.getStringExtra("LNG");

        //依据
        yjStr = intent.getStringExtra("YJ");
        transaction_place = intent.getStringExtra("TRANSACTION_PLACE");
        String materialsInfo = intent.getStringExtra("MaterialsInfo");
        //流程
        path = intent.getStringExtra("PATH");
        hdpath = intent.getStringExtra("HDPATH");
        fileName = intent.getStringExtra("FILENAME");

        if (!TextUtils.isEmpty(materialsInfo)) {
            materialsInfoList = new Gson().fromJson(materialsInfo, new TypeToken<List<MaterialsInfo>>() {
            }.getType());
        }

    }

    private void initEvent() {


    }

    private void initView() {

        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        tvTopTitle = (TextView) findViewById(R.id.tv_top_title);
        tv_child_title = (TextView) findViewById(R.id.tv_child_title);
        tv_zwsj = findViewById(R.id.tv_zwsj);
        tv_zwsj.setVisibility(View.GONE);
        tv_child_title.setText(titleStr);

        tvTopTitle.setText("办事指南");
        systemBack = (ImageView) findViewById(R.id.system_back);
        systemBack.setVisibility(View.VISIBLE);
        systemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //办理时间
        if (type == 1) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.work_time_layout, null);
            mView = inflate;
            tvLocal = generateFindViewById(R.id.tv_local);
            tv_local_adress = generateFindViewById(R.id.tv_local_adress);
            tvTime = generateFindViewById(R.id.tv_time);
            tvMap = generateFindViewById(R.id.tv_map);
            tvCall = generateFindViewById(R.id.tv_call);
            tvLocal.setText(local);
            tvTime.setText(time);

            //如果不是经纬度 就取 lat
            if (BeanUtils.isEmptyStr(lat) || BeanUtils.isEmptyStr(lng)) {
                tvMap.setVisibility(View.GONE);
                if (TextUtils.isEmpty(lat)) {
                    tv_local_adress.setText("暂无位置信息");
                } else {
                    tv_local_adress.setText(local);
                }

            } else {
                // 精度维度 都不为空
                tv_local_adress.setVisibility(View.GONE);
                tvMap.setVisibility(View.VISIBLE);
            }


            tvMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (BeanUtils.isEmptyStr(lat) || BeanUtils.isEmptyStr(lng)) {
                        DialogUtils.showToast(WorkTimeActivity.this, "暂未获取办事地点信息");
                        return;
                    }
                    showSelectMapDialog();
                }
            });

        }
        // 办理条件
        else if (type == 2) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.work_yj_layout, null);
            mView = inflate;
        }
        // 办理材料
        else if (type == 3) {
            if (materialsInfoList == null || materialsInfoList.size() == 0) {
                tv_zwsj.setVisibility(View.VISIBLE);
            } else {
                if (tab2 == null) {
                    tab2 = new HJTab2Fragment();
                    setData(tab2, materialsInfoList);
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), tab2);
            }

        }
        // 办理流程
        else if (type == 4) {
            if (BeanUtils.isEmptyStr(fileName)) {
                tv_zwsj.setVisibility(View.VISIBLE);
                return;
            }
            final View inflate = LayoutInflater.from(this).inflate(R.layout.work_lc_layout, ll_content, false);
            mView = inflate;
            mIvFile = generateFindViewById(R.id.iv_file);
            String url = "http://218.87.176.156:80/platform/DownFileServlet?type=1&DOWNPATH=" + path +
                    "&HDFSFILENAME=" + hdpath + "&FILENAME=" + fileName;
            i("url:" + url);
            //是否为图片格式
            if (fileName.contains(".png") || fileName.contains(".jpg") || fileName.contains(".jpeg") || fileName.contains(".webps")) {
                //监听加载错误
                inflate.setVisibility(View.VISIBLE);
                ImageLoad.setUrlImgCc(this, url, mIvFile, new ImageLoad.InterfaceImageListener() {
                    @Override
                    public void onError() {
                        inflate.setVisibility(View.GONE);
                        tv_zwsj.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                inflate.setVisibility(View.GONE);
                tv_zwsj.setVisibility(View.VISIBLE);
            }

        }
        // 设定依据
        else if (type == 5) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.work_yj_layout, null);
            mView = inflate;
            TextView tvYj = generateFindViewById(R.id.tv_yj);
            tvYj.setText(yjStr);
            if (TextUtils.isEmpty(yjStr)) {
                tv_zwsj.setVisibility(View.VISIBLE);
                inflate.setVisibility(View.GONE);
                yjStr = "暂无数据";
            }
        }

        if (mView != null) {
            ll_content.removeAllViews();
            ll_content.addView(mView);
        }


    }

    private View mView;

    public <T extends View> T generateFindViewById(int id) {
        return (T) mView.findViewById(id);
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
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
//        if (tab2 == null || tab2 == fragment)
//            return;
        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.add(R.id.ll_content, fragment).commit();
        } else {
            transaction.show(fragment).commit();
        }

    }


    //  -------------------------------------- 以下为地图代码 --------------------------------------------------------

    private void showSelectMapDialog() {
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_map_select, null);
        List<PackageInfo> installedMap = setInstalledMap(this, root);
        if (installedMap.size() == 0) {//当本地没有安装地图应用时跳转百度web地图
            gotoBaiduWebMap();
            return;
        } else if (installedMap.size() == 1) {//当本地安装了一个地图应用时跳转到该地图应用
            openMap(installedMap.get(0).packageName);
            return;
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


    private String gdPackageName = "com.autonavi.minimap";
    private String bdPackageName = "com.baidu.BaiduMap";
    private String txPackageName = "com.tencent.map";

    /**
     * 检查手机上是否安装了指定的软件
     */
    private List<PackageInfo> setInstalledMap(Context context, View root) {
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
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                if (packName.equals(gdPackageName) || packName.contains(bdPackageName) || packName.contains(txPackageName)) {
                    mapPackages.add(packageInfos.get(i));
                }
            }
        }
        if (mapPackages.size() >= 2) {
            map1.setVisibility(View.VISIBLE);
            map2.setVisibility(View.VISIBLE);
            map1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMap(mapPackages.get(0).packageName);
                }
            });
            map2.setOnClickListener(new View.OnClickListener() {
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
            map3.setOnClickListener(new View.OnClickListener() {
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

    private void gotoBaiduWebMap() {
//            String url = "http://api.map.baidu.com/direction?origin=latlng:" + curLat + "," + curLng + "|name:我的位置" +
//                    "&destination=latlng:" + lat + "," + lng + "|name:" + transaction_place + "&mode=driving&origin_region="
//                    + curCity +"&destination_region=" + endCity +"&output=html&src=webapp.baidu.openAPIdemo";
        startActivity(new Intent(this, PublicWebViewActivity.class)
                .putExtra("isRoute", true)
                .putExtra("lat", lat)
                .putExtra("lng", lng)
                .putExtra("transaction_place", transaction_place)
                .putExtra("title", "路线"));
    }

    /**
     * 打开地图
     *
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
        CoodinateCovertor.LngLat lngLat_bd = new CoodinateCovertor.LngLat(Double.valueOf(lng), Double.valueOf(lat));
        CoodinateCovertor.LngLat lngLat = CoodinateCovertor.bd_decrypt(lngLat_bd);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri = Uri.parse("amapuri://route/plan/?did=BGVIS2&dlat=" + lngLat.getLantitude() + "&dlon=" + lngLat.getLongitude() + "&dname=" + transaction_place + "&dev=0&t=0");
        intent.setData(uri);
        startActivity(intent);
    }

    /**
     * 打开百度地图
     */
    private void openBaiduMap() {
        try {
            Intent intent = Intent.getIntent("intent://map/direction?" +
                    "destination=latlng:" + lat + "," + lng + "|name:" + transaction_place +        //终点
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
        CoodinateCovertor.LngLat lngLat_bd = new CoodinateCovertor.LngLat(Double.valueOf(lng), Double.valueOf(lat));
        CoodinateCovertor.LngLat lngLat = CoodinateCovertor.bd_decrypt(lngLat_bd);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri = Uri.parse("qqmap://map/routeplan?type=drive&to=" + transaction_place + "&tocoord="
                + lngLat.getLantitude() + "," + lngLat.getLongitude());
        intent.setData(uri);
        startActivity(intent);
    }


}

