package com.powerrich.office.oa.thirdapp.http.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/23
 * 版权：
 */

public class GetArticleBean implements Serializable{


    /**
     * Name : 移动贵溪
     * Imageurl : http://cmsv3.aheading.com//UploadFile/8673/2017/10-02/20189a1d-08c9-4027-a6d4-208682d0f76b.jpg
     * Description : 贵溪，位于江西省东北部、信江中游。自唐永泰元年（公元765年）建县，至今已有1200余年历史。
     * IsFollow : false
     * ChildClassifys : [{"Id":31087,"Name":"新闻动态","SortIndex":24884,"Type":17,"ShowStyle":0,"Url":"",
     * "OperationTimeStamp":1529375533},{"Id":31091,"Name":"政务公开","SortIndex":24885,"Type":17,"ShowStyle":0,"Url":"",
     * "OperationTimeStamp":1529375533},{"Id":31089,"Name":"公共服务","SortIndex":24886,"Type":17,"ShowStyle":0,"Url":"",
     * "OperationTimeStamp":1529375533},{"Id":31090,"Name":"招商引资","SortIndex":24887,"Type":17,"ShowStyle":0,"Url":"",
     * "OperationTimeStamp":1529375533},{"Id":31088,"Name":"贵溪概况","SortIndex":24888,"Type":17,"ShowStyle":0,"Url":"",
     * "OperationTimeStamp":1529375533}]
     */

    private String Name;
    private String Imageurl;
    private String Description;
    private boolean IsFollow;
    private List<ChildClassifysBean> ChildClassifys;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String Imageurl) {
        this.Imageurl = Imageurl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public boolean isIsFollow() {
        return IsFollow;
    }

    public void setIsFollow(boolean IsFollow) {
        this.IsFollow = IsFollow;
    }

    public List<ChildClassifysBean> getChildClassifys() {
        return ChildClassifys;
    }

    public void setChildClassifys(List<ChildClassifysBean> ChildClassifys) {
        this.ChildClassifys = ChildClassifys;
    }

    public static class ChildClassifysBean implements Serializable{
        /**
         * Id : 31087
         * Name : 新闻动态
         * SortIndex : 24884
         * Type : 17
         * ShowStyle : 0
         * Url :
         * OperationTimeStamp : 1529375533
         */

        private int Id;
        private String Name;
        private int SortIndex;
        private int Type;
        private int ShowStyle;
        private String Url;
        private int OperationTimeStamp;
        public int nid;


        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getSortIndex() {
            return SortIndex;
        }

        public void setSortIndex(int SortIndex) {
            this.SortIndex = SortIndex;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public int getShowStyle() {
            return ShowStyle;
        }

        public void setShowStyle(int ShowStyle) {
            this.ShowStyle = ShowStyle;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public int getOperationTimeStamp() {
            return OperationTimeStamp;
        }

        public void setOperationTimeStamp(int OperationTimeStamp) {
            this.OperationTimeStamp = OperationTimeStamp;
        }
    }
}
