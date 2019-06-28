package com.powerrich.office.oa.bean;

/**
 * Created by ch on 2016/6/14.
 */
public class NewsInfo {

	private String id;
    private int icon;
    private String newsTitle;
    private String newsContent;
    private String date;
    private String newsType;
    private String imgUrl;

    public NewsInfo() {
	}

    public NewsInfo(String id, int icon, String newsTitle, String date, String type) {
        this.icon = icon;
        this.newsTitle = newsTitle;
        this.date = date;
        this.newsType = type;
        this.id = id;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
