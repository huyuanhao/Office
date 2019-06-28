package com.powerrich.office.oa.fund.bean;

/**
 * @author PC
 * @date 2019/04/12 11:56
 */
public class FundItemBean {
   private String title;
   private String info;

   public FundItemBean(String title,String info){
       this.title = title;
       this.info = info;
   }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
