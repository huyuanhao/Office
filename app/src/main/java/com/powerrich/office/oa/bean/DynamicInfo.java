package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * 文 件 名：DynamicInfo
 * 描   述：动态信息实体类
 * 作   者：Wangzheng
 * 时   间：2017/11/28
 * 版   权：v1.0
 */
public class DynamicInfo {
    private String defaultValue;
    private String fieldCName;
    private String fieldDesc;
    private String fieldEName;
    private String fieldLength;
    private String fieldSort;
    private String fieldType;
    private String isNull;
    private String source;
    private String styleId;
    private String tableEName;
    private List<SourceInfo> sourceInfoList;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFieldCName() {
        return fieldCName;
    }

    public void setFieldCName(String fieldCName) {
        this.fieldCName = fieldCName;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public String getFieldEName() {
        return fieldEName;
    }

    public void setFieldEName(String fieldEName) {
        this.fieldEName = fieldEName;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getFieldSort() {
        return fieldSort;
    }

    public void setFieldSort(String fieldSort) {
        this.fieldSort = fieldSort;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getTableEName() {
        return tableEName;
    }

    public void setTableEName(String tableEName) {
        this.tableEName = tableEName;
    }

    public boolean isNull() {
        return !"1".equals(isNull);
    }

    public List<SourceInfo> getSourceInfoList() {
        return sourceInfoList;
    }

    public void setSourceInfoList(List<SourceInfo> sourceInfoList) {
        this.sourceInfoList = sourceInfoList;
    }

    public static class SourceInfo {
        private String id;
        private String val;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }
    }
}
