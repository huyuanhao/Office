package com.powerrich.office.oa.tools;

import android.content.Context;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.powerrich.office.oa.bean.CitysBean;
import com.powerrich.office.oa.bean.EthnicBean;
import com.powerrich.office.oa.bean.EthnicInfo;
import com.powerrich.office.oa.bean.JsonBean;
import com.powerrich.office.oa.bean.LossInfo;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/22
 * 版权：
 */

public class PickUtils {

    public static EthnicBean getEthnic(Context context) {
        EthnicBean bean = new EthnicBean();
        try {
            InputStream is = context.getClass().getClassLoader().getResourceAsStream("assets/" + "group.json");//android studio
            BufferedReader bufr = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufr.readLine()) != null) {
                builder.append(line);
            }
            is.close();
            bufr.close();
            bean = GsonUtil.GsonToBean(builder.toString(), EthnicBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static String getNation(Context context, String type) {
        String nation = "族";
        EthnicBean bean = PickUtils.getEthnic(context);
        List<EthnicInfo> data = bean.getData();
        for (int i = 0; i < data.size(); i++) {
            if (Integer.parseInt(type) == data.get(i).getId()) {
                nation = data.get(i).getName();
            }
        }
        return nation;
    }


    public static CitysBean getCityList(Context context) {
        ArrayList<JsonBean> item1 = new ArrayList<>();
        ArrayList<ArrayList<String>> item2 = new ArrayList<>();
//        ArrayList<ArrayList<ArrayList<String>>> item3 = new ArrayList<>();
        CitysBean citys = new CitysBean();
        try {
            InputStream is = context.getClass().getClassLoader().getResourceAsStream("assets/" + "CityJson.json");//android studio
            BufferedReader bufr = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufr.readLine()) != null) {
                builder.append(line);
            }
            is.close();
            bufr.close();
            ArrayList<JsonBean> jsonBean = parseData(builder.toString());//用Gson 转成实体
            item1 = jsonBean;
            for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
                ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
//                ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                    String CityName = jsonBean.get(i).getCityList().get(c).getName();
                    CityList.add(CityName);//添加城市
//                    ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
//                    if (jsonBean.get(i).getCityList().get(c).getArea() == null
//                            || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
//                        City_AreaList.add("");
//                    } else {
//                        City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
//                    }
//                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                }
                item2.add(CityList);
//                item3.add(Province_AreaList);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        citys.setItem1(item1);
        citys.setItem2(item2);
//        citys.setItem3(item3);
        return citys;
    }


    public static ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    public interface StringCallback {
        void getString(String name);
    }

    public interface CallBackData<T>{
        void getData(T t);
    }

    //弹出城市选择器
    public static void showCityPickerView(Context context, final StringCallback callback) {
        final CitysBean bean = getCityList(context);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String city = bean.getItem1().get(options1).getPickerViewText()
                        + " " + bean.getItem2().get(options1).get(option2)
                        + " " + bean.getItem3().get(options1).get(option2).get(options3);
                callback.getString(city);
            }
        }).build();
        pvOptions.setPicker(bean.getItem1(), bean.getItem2(), bean.getItem3());
        pvOptions.setPicker(bean.getItem1(), bean.getItem2(), bean.getItem3());
//        pvOptions.setSelectOptions(13,5,3);
        pvOptions.show();
    }

    public static void showCityPickerView(Context context, String dataStr, final StringCallback callback) {
        final CitysBean bean = getCityList(context);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String city = bean.getItem1().get(options1).getPickerViewText()
                        + " " + bean.getItem2().get(options1).get(option2);
//                        + " " + bean.getItem3().get(options1).get(option2).get(options3);
                callback.getString(city);
            }
        }).build();
        pvOptions.setPicker(bean.getItem1(), bean.getItem2(), bean.getItem3());
        dataStr = dataStr.trim();
        String[] dataStrs = dataStr.split(" ");
        int a = 0, b = 0;
        if (dataStrs.length >= 2) {
            ArrayList<JsonBean> bean1 = bean.getItem1();
            for (int i = 0; i < bean1.size(); i++) {
                if (dataStrs[0].contains(bean1.get(i).getName())) {
                    a = i;
                    break;
                }
            }
            ArrayList<ArrayList<String>> bean2 = bean.getItem2();
            for (int i = 0; i < bean2.size(); i++) {
                for (int j = 0; j < bean2.get(i).size(); j++) {
                    if (dataStrs[1].contains(bean2.get(i).get(j))) {
                        b = j;
                        break;
                    }
                }
            }
//            ArrayList<ArrayList<ArrayList<String>>> bean3 = bean.getItem3();
//            for (int i = 0; i < bean3.size(); i++) {
//                for (int j = 0; j < bean3.get(i).size(); j++) {
//                    for (int k = 0; k < bean3.get(i).get(j).size(); k++) {
//                        if (bean3.get(i).get(j).get(k).equals(dataStrs[2])) {
//                            c = k;
//                        }
//                    }
//                }
//            }
            pvOptions.setSelectOptions(a, b);
        }
//        else if (dataStrs.length >= 2) {
//            a = 0;
//            ArrayList<ArrayList<String>> bean2 = bean.getItem2();
//            for (int i = 0; i < bean2.size(); i++) {
//                for (int j = 0; j < bean2.get(i).size(); j++) {
//                    if (bean2.get(i).get(j).equals(dataStrs[0])) {
//                        if (dataStrs[0].equals("北京")) {
//                            a = 0;
//                        } else if (dataStrs[0].equals("上海")) {
//                            a = 8;
//                        } else if (dataStrs[0].equals("重庆")) {
//                            a = 21;
//                        } else if (dataStrs[0].equals("台湾")) {
//                            a = 31;
//                        } else if (dataStrs[0].equals("天津")) {
//                            a = 1;
//                        } else if (dataStrs[0].equals("澳门")) {
//                            a = 32;
//                        } else if (dataStrs[0].equals("香港")) {
//                            a = 32;
//                        }
//                        b = j;
//                        break;
//                    }
//                }
//            }
//            ArrayList<ArrayList<ArrayList<String>>> bean3 = bean.getItem3();
//            for (int i = 0; i < bean3.size(); i++) {
//                for (int j = 0; j < bean3.get(i).size(); j++) {
//                    for (int k = 0; k < bean3.get(i).get(j).size(); k++) {
//                        if (bean3.get(i).get(j).get(k).equals(dataStrs[1])) {
//                            c = k;
//                        }
//                    }
//                }
//            }
//            pvOptions.setSelectOptions(a, b, c);
//        } else {
//        }
        pvOptions.show();
    }


    //弹出名族选择器
    public static void showEthnicPickerView(Context context, final StringCallback callback) {
        final List<EthnicInfo> bean = getEthnic(context).getData();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String ethnic = bean.get(options1).getName();
                callback.getString(ethnic);
            }
        }).build();
        pvOptions.setPicker(getEthnic(context).getData());
        pvOptions.show();
    }

    //弹出民族选择器
    public static void showGroupView(Context context,EthnicInfo info, final CallBackData callback) {
        final List<EthnicInfo> bean = getEthnic(context).getData();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
//                String ethnic = bean.get(options1).getName();
//                int code = bean.get(options1).getId();
                callback.getData( bean.get(options1));
            }
        }).build();
        pvOptions.setPicker(getEthnic(context).getData());
        if(info != null){
            pvOptions.setSelectOptions(info.getId() - 1);
        }
        pvOptions.show();
    }


    //弹出证件类型选择器
    public static void showCardIdPickerView(Context context, final StringCallback callback) {
        String[] strs = new String[]{"身份证", "驾驶证", "社保证", "行驶证"};
        final List<String> bean = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            bean.add(strs[i]);
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String ethnic = bean.get(options1);
                callback.getString(ethnic);
            }
        }).build();
        pvOptions.setPicker(bean);
        pvOptions.show();
    }


    //弹出挂失类型
    public static void showCocialLossType(Context context,LossInfo info,final CallBackData callback) {

        LossInfo info1 = new LossInfo(21,"临时挂失");
        LossInfo info2 = new LossInfo(22,"正式挂失");
        LossInfo info3 = new LossInfo(24,"临时挂失解挂");

        final List<LossInfo> bean = new ArrayList<>();
        bean.add(info1);
        bean.add(info2);
        bean.add(info3);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                callback.getData(bean.get(options1));
            }
        }).build();
        pvOptions.setPicker(bean);
        if(info != null){
            if(info.getId() == 21){
                pvOptions.setSelectOptions(0);
            }else if(info.getId() == 22){
                pvOptions.setSelectOptions(1);
            }else if(info.getId() == 24){
                pvOptions.setSelectOptions(2);
            }
        }
        pvOptions.show();
    }

}
