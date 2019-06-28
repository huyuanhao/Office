package com.powerrich.office.oa.fund.view.entity;

/**
 * 提前还款  初始实体
 * @author AlienChao
 * @date 2019/04/19 11:35
 */
public class Prepayment {
    private String title;
    private String value;
    // 0默认标题  1 小标题
    private int type;
    //是否允许点击
    private boolean isClick;


    public Prepayment setClick(boolean click) {
        isClick = click;
        return this;
    }

    public Prepayment setTitle(String title) {
        this.title = title;
        return this;
    }

    public Prepayment setType(int type) {
        this.type = type;
        return this;
    }

    public Prepayment setValue(String value) {
        this.value = value;
        return this;
    }


    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    public boolean isClick() {
        return isClick;
    }
}
