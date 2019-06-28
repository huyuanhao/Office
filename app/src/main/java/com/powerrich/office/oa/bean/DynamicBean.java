package com.powerrich.office.oa.bean;

import com.powerrich.office.oa.tools.BeanUtils;

import java.util.ArrayList;

/**
 * 文 件 名：DynamicBean
 * 描   述：网上办事-保存动态表单的信息实体类
 * 作   者：Wangzheng
 * 时   间：2017/12/1
 * 版   权：v1.0
 */
public class DynamicBean {
    private String itemId;
    private String proKeyId;
    private String table;
    private ArrayList<ParamMap> params = new ArrayList<>();

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProKeyId() {
        return proKeyId;
    }

    public void setProKeyId(String proKeyId) {
        this.proKeyId = proKeyId;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void addParam(ParamMap param) {
        if (null != param) {
            params.add(param);
        }
    }

    public void removeParam(ParamMap param) {
        if (null != param) {
            params.remove(param);
        }
    }

    public String getParam() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"" + "itemid" + "\"" + ":" + "\"" + getItemId() + "\",");
        buffer.append("\"" + "prokeyid" + "\"" + ":" + "\"" + getProKeyId() + "\",");
        buffer.append("\"" + "table" + "\"" + ":" + "\"" + getTable() + "\",");
        buffer.append("\"" + "field" + "\"" +  ":");
        buffer.append("[");
        if (!BeanUtils.isEmpty(params)) {
            boolean flag = false;
            for (ParamMap map : params) {
                if (flag) {
                    buffer.append(",");
                }
                flag = true;
                buffer.append("{").append(map.getParam()).append("}");
            }
        }
        buffer.append("]");
        buffer.append("}");
        return buffer.toString();
    }

    public static class ParamMap {
        public String key;
        public String value;

        public ParamMap(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getParam() {
            return "\"" + key + "\"" + ":" + "\"" + value + "\"";
        }
    }
}
