package com.powerrich.office.oa.bean;

/**
 * 文 件 名：MobileBusinessRightBean
 * 描    述：
 * 作    者：chenhao
 * 时    间：2018/7/27
 * 版    权：v1.0
 */
public class MobileBusinessRightBean {


    /**
     * Detail : 驻营商会
     * Id : 30477
     * ImageFile : http://cmsv3.aheading.com//UploadFile/8673/2017/10-18/b3692c9a-80b8-4c4b-aec8-3ef2987342ab.jpg
     * IsFollow : false
     * IsUrl : false
     * NewPidx : 437
     * SortIndex : 24218
     * Url :
     */

    private String Detail;
    private int Id;
    private String ImageFile;
    private boolean IsFollow;
    private boolean IsUrl;
    private int NewPidx;
    private int SortIndex;
    private String Url;

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String Detail) {
        this.Detail = Detail;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getImageFile() {
        return ImageFile;
    }

    public void setImageFile(String ImageFile) {
        this.ImageFile = ImageFile;
    }

    public boolean isIsFollow() {
        return IsFollow;
    }

    public void setIsFollow(boolean IsFollow) {
        this.IsFollow = IsFollow;
    }

    public boolean isIsUrl() {
        return IsUrl;
    }

    public void setIsUrl(boolean IsUrl) {
        this.IsUrl = IsUrl;
    }

    public int getNewPidx() {
        return NewPidx;
    }

    public void setNewPidx(int NewPidx) {
        this.NewPidx = NewPidx;
    }

    public int getSortIndex() {
        return SortIndex;
    }

    public void setSortIndex(int SortIndex) {
        this.SortIndex = SortIndex;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }
}
