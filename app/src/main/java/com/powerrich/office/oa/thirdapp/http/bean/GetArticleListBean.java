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

public class GetArticleListBean implements Serializable {



    private int AllArticleCounts;
    private int AllArticlePages;
    private List<TopArticleBean> TopArticle;
    private List<ArticleListBean> ArticleList;

    public int getAllArticleCounts() {
        return AllArticleCounts;
    }

    public void setAllArticleCounts(int AllArticleCounts) {
        this.AllArticleCounts = AllArticleCounts;
    }

    public int getAllArticlePages() {
        return AllArticlePages;
    }

    public void setAllArticlePages(int AllArticlePages) {
        this.AllArticlePages = AllArticlePages;
    }

    public List<TopArticleBean> getTopArticle() {
        return TopArticle;
    }

    public void setTopArticle(List<TopArticleBean> TopArticle) {
        this.TopArticle = TopArticle;
    }

    public List<ArticleListBean> getArticleList() {
        return ArticleList;
    }

    public void setArticleList(List<ArticleListBean> ArticleList) {
        this.ArticleList = ArticleList;
    }

    public static class TopArticleBean {


        private String ParentClassName;
        private int Id;
        private String ImgSrc;
        private String Title;
        private String Detail;
        private String PostDateTime;
        private String Description;
        private int Type;
        private String Url;
        private String Tag;
        private String ImgSrcs;
        private int TypeValue;
        private int MediaType;
        private int CommentCount;
        private int ZambiaCount;
        private int ShareCount;
        private int ReadCount;
        private int IsShowSummary;
        private int IsShowPostDateTime;
        private String LiveStartTime;
        private int LiveStatus;
        private int ParNum;
        private String LiveEndTime;
        private String QKUrl;
        private int IsCollected;
        private int IsPraised;
        private int IsCanComment;
        private int ImageType;
        private int ImageCount;

        public String getParentClassName() {
            return ParentClassName;
        }

        public void setParentClassName(String ParentClassName) {
            this.ParentClassName = ParentClassName;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getImgSrc() {
            return ImgSrc;
        }

        public void setImgSrc(String ImgSrc) {
            this.ImgSrc = ImgSrc;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getDetail() {
            return Detail;
        }

        public void setDetail(String Detail) {
            this.Detail = Detail;
        }

        public String getPostDateTime() {
            return PostDateTime;
        }

        public void setPostDateTime(String PostDateTime) {
            this.PostDateTime = PostDateTime;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public String getTag() {
            return Tag;
        }

        public void setTag(String Tag) {
            this.Tag = Tag;
        }

        public String getImgSrcs() {
            return ImgSrcs;
        }

        public void setImgSrcs(String ImgSrcs) {
            this.ImgSrcs = ImgSrcs;
        }

        public int getTypeValue() {
            return TypeValue;
        }

        public void setTypeValue(int TypeValue) {
            this.TypeValue = TypeValue;
        }

        public int getMediaType() {
            return MediaType;
        }

        public void setMediaType(int MediaType) {
            this.MediaType = MediaType;
        }

        public int getCommentCount() {
            return CommentCount;
        }

        public void setCommentCount(int CommentCount) {
            this.CommentCount = CommentCount;
        }

        public int getZambiaCount() {
            return ZambiaCount;
        }

        public void setZambiaCount(int ZambiaCount) {
            this.ZambiaCount = ZambiaCount;
        }

        public int getShareCount() {
            return ShareCount;
        }

        public void setShareCount(int ShareCount) {
            this.ShareCount = ShareCount;
        }

        public int getReadCount() {
            return ReadCount;
        }

        public void setReadCount(int ReadCount) {
            this.ReadCount = ReadCount;
        }

        public int getIsShowSummary() {
            return IsShowSummary;
        }

        public void setIsShowSummary(int IsShowSummary) {
            this.IsShowSummary = IsShowSummary;
        }

        public int getIsShowPostDateTime() {
            return IsShowPostDateTime;
        }

        public void setIsShowPostDateTime(int IsShowPostDateTime) {
            this.IsShowPostDateTime = IsShowPostDateTime;
        }

        public String getLiveStartTime() {
            return LiveStartTime;
        }

        public void setLiveStartTime(String LiveStartTime) {
            this.LiveStartTime = LiveStartTime;
        }

        public int getLiveStatus() {
            return LiveStatus;
        }

        public void setLiveStatus(int LiveStatus) {
            this.LiveStatus = LiveStatus;
        }

        public int getParNum() {
            return ParNum;
        }

        public void setParNum(int ParNum) {
            this.ParNum = ParNum;
        }

        public String getLiveEndTime() {
            return LiveEndTime;
        }

        public void setLiveEndTime(String LiveEndTime) {
            this.LiveEndTime = LiveEndTime;
        }

        public String getQKUrl() {
            return QKUrl;
        }

        public void setQKUrl(String QKUrl) {
            this.QKUrl = QKUrl;
        }

        public int getIsCollected() {
            return IsCollected;
        }

        public void setIsCollected(int IsCollected) {
            this.IsCollected = IsCollected;
        }

        public int getIsPraised() {
            return IsPraised;
        }

        public void setIsPraised(int IsPraised) {
            this.IsPraised = IsPraised;
        }

        public int getIsCanComment() {
            return IsCanComment;
        }

        public void setIsCanComment(int IsCanComment) {
            this.IsCanComment = IsCanComment;
        }

        public int getImageType() {
            return ImageType;
        }

        public void setImageType(int ImageType) {
            this.ImageType = ImageType;
        }

        public int getImageCount() {
            return ImageCount;
        }

        public void setImageCount(int ImageCount) {
            this.ImageCount = ImageCount;
        }
    }

    public static class ArticleListBean {

        private String ParentClassName;
        private int Id;
        private String ImgSrc;
        private String Title;
        private String Detail;
        private String PostDateTime;
        private String Description;
        private int Type;
        private String Url;
        private String Tag;
        private String ImgSrcs;
        private int TypeValue;
        private int MediaType;
        private int CommentCount;
        private int ZambiaCount;
        private int ShareCount;
        private int ReadCount;
        private int IsShowSummary;
        private int IsShowPostDateTime;
        private String LiveStartTime;
        private int LiveStatus;
        private int ParNum;
        private String LiveEndTime;
        private String QKUrl;
        private int IsCollected;
        private int IsPraised;
        private int IsCanComment;
        private int ImageType;
        private int ImageCount;

        public String getParentClassName() {
            return ParentClassName;
        }

        public void setParentClassName(String ParentClassName) {
            this.ParentClassName = ParentClassName;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getImgSrc() {
            return ImgSrc;
        }

        public void setImgSrc(String ImgSrc) {
            this.ImgSrc = ImgSrc;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getDetail() {
            return Detail;
        }

        public void setDetail(String Detail) {
            this.Detail = Detail;
        }

        public String getPostDateTime() {
            return PostDateTime;
        }

        public void setPostDateTime(String PostDateTime) {
            this.PostDateTime = PostDateTime;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public String getTag() {
            return Tag;
        }

        public void setTag(String Tag) {
            this.Tag = Tag;
        }

        public String getImgSrcs() {
            return ImgSrcs;
        }

        public void setImgSrcs(String ImgSrcs) {
            this.ImgSrcs = ImgSrcs;
        }

        public int getTypeValue() {
            return TypeValue;
        }

        public void setTypeValue(int TypeValue) {
            this.TypeValue = TypeValue;
        }

        public int getMediaType() {
            return MediaType;
        }

        public void setMediaType(int MediaType) {
            this.MediaType = MediaType;
        }

        public int getCommentCount() {
            return CommentCount;
        }

        public void setCommentCount(int CommentCount) {
            this.CommentCount = CommentCount;
        }

        public int getZambiaCount() {
            return ZambiaCount;
        }

        public void setZambiaCount(int ZambiaCount) {
            this.ZambiaCount = ZambiaCount;
        }

        public int getShareCount() {
            return ShareCount;
        }

        public void setShareCount(int ShareCount) {
            this.ShareCount = ShareCount;
        }

        public int getReadCount() {
            return ReadCount;
        }

        public void setReadCount(int ReadCount) {
            this.ReadCount = ReadCount;
        }

        public int getIsShowSummary() {
            return IsShowSummary;
        }

        public void setIsShowSummary(int IsShowSummary) {
            this.IsShowSummary = IsShowSummary;
        }

        public int getIsShowPostDateTime() {
            return IsShowPostDateTime;
        }

        public void setIsShowPostDateTime(int IsShowPostDateTime) {
            this.IsShowPostDateTime = IsShowPostDateTime;
        }

        public String getLiveStartTime() {
            return LiveStartTime;
        }

        public void setLiveStartTime(String LiveStartTime) {
            this.LiveStartTime = LiveStartTime;
        }

        public int getLiveStatus() {
            return LiveStatus;
        }

        public void setLiveStatus(int LiveStatus) {
            this.LiveStatus = LiveStatus;
        }

        public int getParNum() {
            return ParNum;
        }

        public void setParNum(int ParNum) {
            this.ParNum = ParNum;
        }

        public String getLiveEndTime() {
            return LiveEndTime;
        }

        public void setLiveEndTime(String LiveEndTime) {
            this.LiveEndTime = LiveEndTime;
        }

        public String getQKUrl() {
            return QKUrl;
        }

        public void setQKUrl(String QKUrl) {
            this.QKUrl = QKUrl;
        }

        public int getIsCollected() {
            return IsCollected;
        }

        public void setIsCollected(int IsCollected) {
            this.IsCollected = IsCollected;
        }

        public int getIsPraised() {
            return IsPraised;
        }

        public void setIsPraised(int IsPraised) {
            this.IsPraised = IsPraised;
        }

        public int getIsCanComment() {
            return IsCanComment;
        }

        public void setIsCanComment(int IsCanComment) {
            this.IsCanComment = IsCanComment;
        }

        public int getImageType() {
            return ImageType;
        }

        public void setImageType(int ImageType) {
            this.ImageType = ImageType;
        }

        public int getImageCount() {
            return ImageCount;
        }

        public void setImageCount(int ImageCount) {
            this.ImageCount = ImageCount;
        }
    }
}
