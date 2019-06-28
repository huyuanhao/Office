package com.powerrich.office.oa.tools;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.ServiceActivity;
import com.powerrich.office.oa.activity.WaterServiceActivity;
import com.powerrich.office.oa.activity.home.InsuranceInquiriesActivity;
import com.powerrich.office.oa.activity.things.PublicWebViewActivity;
import com.powerrich.office.oa.activity.things.WebNearActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceSelectTool {

    // -------------------------------  百事通服务   ----------------------------------------------

    private int[] serviceIcon = {R.drawable.icon_trade_bmfu_tr
    };
    //    R.drawable.icon_trade_bmfu_fv,"公安查询",
    private String[] serviceIconName = {"公交站"};


    private int[] livingIcon = {R.drawable.icon_service_life_1, R.drawable.icon_service_life_2,
            R.drawable.icon_service_life_3, R.drawable.icon_service_life_4,
            R.drawable.icon_service_life_5, R.drawable.icon_service_life_6};
    private String[] livingIconName = {"供水服务", "快递服务", "违章查询", "加油卡充值", "天气", "空气水质查询"};

    private int[] medicalIcon = {R.drawable.icon_service_health_1, R.drawable.icon_service_health_2, R.drawable.icon_service_health_3};
    private String[] medicalIconName = {"医保查询", "定点医院", "预约挂号"};

    private int[] insuranceIcon = {R.drawable.icon_service_home_1, R.drawable.icon_service_home_2, R.drawable.icon_service_home_3};
    private String[] insuranceIconName = {"社保查询", "公积金查询", "社保年检"};

    private int[] travelIcon = {R.drawable.icon_service_walk_1, R.drawable.icon_service_walk_2};
    private String[] travelIconName = {"火车", "机票"};


    private int[] nearbyIcon = {R.drawable.icon_trade_fj_on, R.drawable.icon_trade_fj_tw, R.drawable.icon_trade_fj_tr,
            R.drawable.icon_trade_fj_onfo, R.drawable.icon_trade_fv, R.drawable.icon_trade_fj_six, R.drawable.icon_trade_fj_sev};
    private String[] nearbyIconName = {"景点", "美食", "酒店", "银行", "公厕", "加油站", "医院"};


    Intent[] serviceIntent = new Intent[]{};
    Intent[] livingIntent = new Intent[]{};
    Intent[] medicalIntent = new Intent[]{};
    Intent[] insuranceIntent = new Intent[]{};
    Intent[] traveIntent = new Intent[]{};
    Intent[] nearbyIntent = new Intent[]{};

    //本地数据总集合
    List<Map<String, Object>> mapList = new ArrayList<>();

    private Context mContext;



    private void initIntent() {

        serviceIntent=new Intent[]{canvertIntent(20)};



        livingIntent = new Intent[]{new Intent(mContext, WaterServiceActivity.class)
                , new Intent(mContext, PublicWebViewActivity.class)
                .putExtra("url", "http://m.kuaidi100.com/?uid=&isnight=0&siteid=55&version=3.0.7&platform=2")
                .putExtra("title", "快递服务")
                , new Intent(mContext, PublicWebViewActivity.class)
//                                .putExtra("url", "http://whjg.alipaycs.com/alipay/whjgquery/dzjc")
                .putExtra("url", "http://city.mzywx.com/yingtan/front/car/toillegalquery")
                .putExtra("title", "违章查询"),
                new Intent(mContext, PublicWebViewActivity.class)
                        .putExtra("url", "http://m.sinopecsales.com/webmobile/html/webhome.jsp")
                        .putExtra("title", "加油卡充值")
                , new Intent(mContext, WebNearActivity.class)
                .putExtra("type", "19"),
                new Intent(mContext, PublicWebViewActivity.class)
                        .putExtra("url", "http://zfb.ipe.org.cn/index.aspx?cityId=297")
                        .putExtra("title", "空气水质查询")};

        medicalIntent = new Intent[]{
                        new Intent(mContext, InsuranceInquiriesActivity.class)
                .putExtra("type", 1),
                        canvertIntent(22),
                        new Intent(mContext, PublicWebViewActivity.class)
                .putExtra("url", "https://wy.guahao.com/")
                .putExtra("title", "挂号")};

        traveIntent = new Intent[]{new Intent(mContext, PublicWebViewActivity.class)
//                                .putExtra("url", "http://m.ctrip.com/webapp/train/")
                .putExtra("url", "https://touch.train.qunar.com/?bd_source=qunar")
                .putExtra("title", "火车票"),new Intent(mContext, PublicWebViewActivity.class)
//                                .putExtra("url", "http://m.ctrip.com/webapp/train/")
                .putExtra("url", "https://m.flight.qunar.com/h5/flight/?bd_source=qunar")
                .putExtra("title", "机票")};




        nearbyIntent = new Intent[]{canvertIntent(0), canvertIntent(1), canvertIntent(2), canvertIntent(3), canvertIntent(4)
                , canvertIntent(5), canvertIntent(6)};


    }



    public ServiceSelectTool(Context context) {
        this.mContext = context;
        loadData();
    }


    /**
     * 初始化数据
     */
    private void loadData() {
        if (mapList.size() > 0) {
            mapList.clear();
        }
        initIntent();

        mapList.addAll(getData(serviceIcon, serviceIconName,serviceIntent));
        mapList.addAll(getData(livingIcon, livingIconName, livingIntent));
        mapList.addAll(getData(medicalIcon, medicalIconName,medicalIntent));
        //SearchServiceAdapter 里有判断
        mapList.addAll(getData(insuranceIcon, insuranceIconName));
        mapList.addAll(getData(travelIcon, travelIconName,traveIntent));
        mapList.addAll(getData(nearbyIcon, nearbyIconName, nearbyIntent));

    }

    /**
     * 查询本地数据
     *
     * @param content
     * @return
     */
    public List<Map<String, Object>> queryData(String content) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Log.i("jsc", "queryData: " + mapList.size());
        for (int i = 0; i < mapList.size(); i++) {
            Map<String, Object> objectMap = mapList.get(i);

            String text = ((String) objectMap.get("text"));
        //    Log.i("jsc", "queryData-text: " + text);
            if (text.contains(content)) {
                dataList.add(objectMap);
            }
        }
        return dataList;
    }


    private Intent canvertIntent(int position) {
        Intent intent = new Intent()
                .setClass(mContext, WebNearActivity.class)
                .putExtra("type", position + "");
        return intent;
    }

    private List<Map<String, Object>> getData(int[] iconIds, String[] iconNames) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < iconNames.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", iconIds[i]);
            map.put("text", iconNames[i]);
            //   map.put("intent", iconNames[i]);
            dataList.add(map);
        }
        return dataList;
    }

    private List<Map<String, Object>> getData(int[] iconIds, String[] iconNames, Intent[] intents) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < iconNames.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", iconIds[i]);
            map.put("text", iconNames[i]);
            map.put("intent", intents[i]);
            dataList.add(map);
        }
        return dataList;
    }

}
