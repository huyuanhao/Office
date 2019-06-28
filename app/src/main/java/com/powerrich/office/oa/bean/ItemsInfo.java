package com.powerrich.office.oa.bean;

/**
 * 文 件 名：ItemsInfo
 * 描   述：事项实体类  部门
 * 作   者：Wangzheng
 * 时   间：2017/11/21
 * 版   权：v1.0
 */
public class ItemsInfo {
    private String IMG;
    private String TAG_DESCRIBE;
    private String TAG_ID;
    private String TAG_LEVEL;
    private String TAG_LEVEL_ABOUT;
    private String TAG_NAME;
    private String TAG_ORDER;
    private String TAG_TYPE;
    private String TAG_VISIT_COUNT;
    private String SXLX;
    private boolean isSelected;

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getTAG_DESCRIBE() {
        return TAG_DESCRIBE;
    }

    public void setTAG_DESCRIBE(String TAG_DESCRIBE) {
        this.TAG_DESCRIBE = TAG_DESCRIBE;
    }

    public String getTAG_ID() {
        return TAG_ID;
    }

    public void setTAG_ID(String TAG_ID) {
        this.TAG_ID = TAG_ID;
    }

    public String getTAG_LEVEL() {
        return TAG_LEVEL;
    }

    public void setTAG_LEVEL(String TAG_LEVEL) {
        this.TAG_LEVEL = TAG_LEVEL;
    }

    public String getTAG_LEVEL_ABOUT() {
        return TAG_LEVEL_ABOUT;
    }

    public void setTAG_LEVEL_ABOUT(String TAG_LEVEL_ABOUT) {
        this.TAG_LEVEL_ABOUT = TAG_LEVEL_ABOUT;
    }

    public String getTAG_NAME() {
        return TAG_NAME;
    }

    public void setTAG_NAME(String TAG_NAME) {
        this.TAG_NAME = TAG_NAME;
    }

    public String getTAG_ORDER() {
        return TAG_ORDER;
    }

    public void setTAG_ORDER(String TAG_ORDER) {
        this.TAG_ORDER = TAG_ORDER;
    }

    public String getTAG_TYPE() {
        return TAG_TYPE;
    }

    public void setTAG_TYPE(String TAG_TYPE) {
        this.TAG_TYPE = TAG_TYPE;
    }

    public String getTAG_VISIT_COUNT() {
        return TAG_VISIT_COUNT;
    }

    public void setTAG_VISIT_COUNT(String TAG_VISIT_COUNT) {
        this.TAG_VISIT_COUNT = TAG_VISIT_COUNT;
    }

    public String getSXLX() {
        return SXLX;
    }

    public void setSXLX(String SXLX) {
        this.SXLX = SXLX;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
