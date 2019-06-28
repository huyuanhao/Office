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

public class ArticleTopicBean implements Serializable{


    /**
     * SubjectArticle : {"Id":1886127,"Title":"书记市长活动专题","IndexArticleId":1886127,"BigImg":"","Description":""}
     * ArticleList : [{"ParentClassName":"","Id":2877773,
     * "ImgSrc":"/UploadFile/8673/2018/03-26/136a8b50-92f3-4aa3-af54-9cfd3741f4db7.jpg","Title":"梅峰督查经开区、贵塘公路沿线环境综合整治工作",
     * "Detail":"","PostDateTime":"2018-03-26T09:36:40.1961883","Description":"梅峰督查经开区、贵塘公路沿线环境综合整治工作","Type":4,
     * "Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/2877773?TypeValue=17&MediaType=4&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2018%2f03-26%2f136a8b50-92f3-4aa3-af54-9cfd3741f4db7.jpg&Title=%e6%a2%85
     * %e5%b3%b0%e7%9d%a3%e6%9f%a5%e7%bb%8f%e5%bc%80%e5%8c%ba%e3%80%81%e8%b4%b5%e5%a1%98%e5%85%ac%e8%b7%af%e6%b2%bf%e7%ba%bf%e7
     * %8e%af%e5%a2%83%e7%bb%bc%e5%90%88%e6%95%b4%e6%b2%bb...&Description=%e6%a2%85%e5%b3%b0%e7%9d%a3%e6%9f%a5%e7%bb%8f%e5%bc
     * %80%e5%8c%ba%e3%80%81%e8%b4%b5%e5%a1%98%e5%85%ac%e8%b7%af%e6%b2%bf%e7%ba%bf%e7%8e%af%e5%a2%83%e7%bb%bc%e5%90%88%e6%95%b4
     * %e6%b2%bb%e5%b7%a5%e4%bd%9c&PostDateTime=2018%2f3%2f26+9%3a36%3a40&ReadCount=80&CommentCount=0&ZambiaCount=3&ShareCount
     * =0&NewsPaperGroupId=8673","Tag":"","ImgSrcs":"/UploadFile/8673/2018/03-26/136a8b50-92f3-4aa3-af54-9cfd3741f4db7.jpg",
     * "TypeValue":17,"MediaType":4,"CommentCount":0,"ZambiaCount":3,"ShareCount":0,"ReadCount":80,"IsShowSummary":0,
     * "IsShowPostDateTime":1,"LiveStartTime":"2018-03-26T09:36:40.1961883","LiveStatus":3,"ParNum":80,
     * "LiveEndTime":"2018-03-26T09:36:40.1961883","QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,
     * "ImageCount":0},{"ParentClassName":"","Id":2307576,
     * "ImgSrc":"/UploadFile/8673/2017/03-29/a2fe5bfd-6e83-4909-ab95-f3eda03f2d646.jpg","Title":"全市市场和质量（食品药品）监管工作会议召开
     * 曹淑敏于秀明提出明确要求  ","Detail":"","PostDateTime":"2017-03-29T09:48:31","Description":"","Type":1,
     * "Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/2307576?TypeValue=4&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2017%2f03-29%2fa2fe5bfd-6e83-4909-ab95-f3eda03f2d646.jpg&Title=%e5%85%a8
     * %e5%b8%82%e5%b8%82%e5%9c%ba%e5%92%8c%e8%b4%a8%e9%87%8f%ef%bc%88%e9%a3%9f%e5%93%81%e8%8d%af%e5%93%81%ef%bc%89%e7%9b%91%e7
     * %ae%a1%e5%b7%a5%e4%bd%9c%e4%bc%9a%e8%ae%ae%e5%8f%ac...&Description=&PostDateTime=2017%2f3%2f29+9%3a48%3a31&ReadCount=201
     * &CommentCount=0&ZambiaCount=0&ShareCount=3&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2017/03-29/a2fe5bfd-6e83-4909-ab95-f3eda03f2d646.jpg","TypeValue":4,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":0,"ShareCount":3,"ReadCount":201,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2017-03-29T09:48:31.0514782","LiveStatus":3,"ParNum":201,"LiveEndTime":"2017-03-29T09:48:31.0514782",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":2307550,"ImgSrc":"/UploadFile/8673/2017/03-29/8f2f1601-1ed2-41f6-81aa-db37a7b618761.jpg","Title":"全市环境保护工作暨工业园区
     * 环境整治动员部署会议召开 曹淑敏于秀明提出明确要求","Detail":"","PostDateTime":"2017-03-29T09:42:49","Description":"","Type":1,
     * "Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/2307550?TypeValue=4&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2017%2f03-29%2f8f2f1601-1ed2-41f6-81aa-db37a7b618761.jpg&Title=%e5%85%a8
     * %e5%b8%82%e7%8e%af%e5%a2%83%e4%bf%9d%e6%8a%a4%e5%b7%a5%e4%bd%9c%e6%9a%a8%e5%b7%a5%e4%b8%9a%e5%9b%ad%e5%8c%ba+%e7%8e%af
     * %e5%a2%83%e6%95%b4%e6%b2%bb%e5%8a%a8%e5%91%98...&Description=&PostDateTime=2017%2f3%2f29+9%3a42%3a49&ReadCount=91
     * &CommentCount=0&ZambiaCount=0&ShareCount=3&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2017/03-29/8f2f1601-1ed2-41f6-81aa-db37a7b618761.jpg","TypeValue":4,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":0,"ShareCount":3,"ReadCount":91,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2017-03-29T09:42:49.5804784","LiveStatus":3,"ParNum":91,"LiveEndTime":"2017-03-29T09:42:49.5804784",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1978476,"ImgSrc":"/UploadFile/8673/2016/08-30/68a8d860-4777-4107-ad9c-fe1dc0201370b.jpg",
     * "Title":"总结过去五年发展成就，引领未来五年发展道路。关注梅峰书记党代","Detail":"","PostDateTime":"2016-08-30T15:39:39.0547419","Description":"",
     * "Type":1,"Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/1978476?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2016%2f08-30%2f68a8d860-4777-4107-ad9c-fe1dc0201370b
     * .jpg&Title=%e6%80%bb%e7%bb%93%e8%bf%87%e5%8e%bb%e4%ba%94%e5%b9%b4%e5%8f%91%e5%b1%95%e6%88%90%e5%b0%b1%ef%bc%8c%e5%bc%95
     * %e9%a2%86%e6%9c%aa%e6%9d%a5%e4%ba%94%e5%b9%b4%e5%8f%91%e5%b1%95%e9%81%93...&Description=&PostDateTime=2016%2f8%2f30+15
     * %3a39%3a39&ReadCount=58&CommentCount=0&ZambiaCount=1&ShareCount=0&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/08-30/68a8d860-4777-4107-ad9c-fe1dc0201370b.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":1,"ShareCount":0,"ReadCount":58,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-08-30T15:39:39.0547419","LiveStatus":3,"ParNum":58,"LiveEndTime":"2016-08-30T15:39:39.0547419",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1978471,"ImgSrc":"/UploadFile/8673/2016/08-30/f432a8ee-debf-4c4b-8069-2e97d69d4ca7c.jpg",
     * "Title":"中共贵溪市第六次代表大会今日胜利闭幕！选举产生新一届中共贵溪","Detail":"","PostDateTime":"2016-08-30T15:36:56.4868564","Description":"",
     * "Type":1,"Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/1978471?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2016%2f08-30%2ff432a8ee-debf-4c4b-8069-2e97d69d4ca7c
     * .jpg&Title=%e4%b8%ad%e5%85%b1%e8%b4%b5%e6%ba%aa%e5%b8%82%e7%ac%ac%e5%85%ad%e6%ac%a1%e4%bb%a3%e8%a1%a8%e5%a4%a7%e4%bc%9a
     * %e4%bb%8a%e6%97%a5%e8%83%9c%e5%88%a9%e9%97%ad%e5%b9%95%ef%bc%81%e9%80%89...&Description=&PostDateTime=2016%2f8%2f30+15
     * %3a36%3a56&ReadCount=260&CommentCount=0&ZambiaCount=3&ShareCount=1&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/08-30/f432a8ee-debf-4c4b-8069-2e97d69d4ca7c.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":3,"ShareCount":1,"ReadCount":260,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-08-30T15:36:56.4868564","LiveStatus":3,"ParNum":260,"LiveEndTime":"2016-08-30T15:36:56.4868564",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1976094,"ImgSrc":"/UploadFile/8673/2016/08-29/568b476e-47b9-446e-aada-15ab8aaf22a1f.jpg",
     * "Title":"这个产业园真是牛，梅书记现场亲自调度","Detail":"","PostDateTime":"2016-08-29T10:35:30.6142663","Description":"","Type":1,
     * "Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/1976094?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2016%2f08-29%2f568b476e-47b9-446e-aada-15ab8aaf22a1f
     * .jpg&Title=%e8%bf%99%e4%b8%aa%e4%ba%a7%e4%b8%9a%e5%9b%ad%e7%9c%9f%e6%98%af%e7%89%9b%ef%bc%8c%e6%a2%85%e4%b9%a6%e8%ae%b0
     * %e7%8e%b0%e5%9c%ba%e4%ba%b2%e8%87%aa%e8%b0%83%e5%ba%a6&Description=&PostDateTime=2016%2f8%2f29+10%3a35%3a30&ReadCount
     * =215&CommentCount=0&ZambiaCount=2&ShareCount=0&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/08-29/568b476e-47b9-446e-aada-15ab8aaf22a1f.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":2,"ShareCount":0,"ReadCount":215,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-08-29T10:35:30.6142663","LiveStatus":3,"ParNum":215,"LiveEndTime":"2016-08-29T10:35:30.6142663",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1976077,"ImgSrc":"/UploadFile/8673/2016/08-29/3a42595f-2349-4a63-b282-eb526035da353.jpg",
     * "Title":"响应省委书记号召，今天省市主流媒体齐聚贵溪","Detail":"","PostDateTime":"2016-08-29T10:31:45.1048696","Description":"","Type":1,
     * "Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/1976077?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2016%2f08-29%2f3a42595f-2349-4a63-b282-eb526035da353.jpg&Title=%e5%93%8d
     * %e5%ba%94%e7%9c%81%e5%a7%94%e4%b9%a6%e8%ae%b0%e5%8f%b7%e5%8f%ac%ef%bc%8c%e4%bb%8a%e5%a4%a9%e7%9c%81%e5%b8%82%e4%b8%bb%e6
     * %b5%81%e5%aa%92%e4%bd%93%e9%bd%90%e8%81%9a%e8%b4%b5...&Description=&PostDateTime=2016%2f8%2f29+10%3a31%3a45&ReadCount=39
     * &CommentCount=0&ZambiaCount=1&ShareCount=0&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/08-29/3a42595f-2349-4a63-b282-eb526035da353.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":1,"ShareCount":0,"ReadCount":39,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-08-29T10:31:45.1048696","LiveStatus":3,"ParNum":39,"LiveEndTime":"2016-08-29T10:31:45.1048696",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1976055,"ImgSrc":"/UploadFile/8673/2016/08-29/9527185d-f207-4c19-8daf-8111d272f6ea0.jpg",
     * "Title":"中共贵溪市第六次代表大会于8月26日至8月29日召开！","Detail":"","PostDateTime":"2016-08-29T10:27:47.0530509","Description":"",
     * "Type":1,"Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/1976055?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2016%2f08-29%2f9527185d-f207-4c19-8daf-8111d272f6ea0.jpg&Title=%e4%b8%ad
     * %e5%85%b1%e8%b4%b5%e6%ba%aa%e5%b8%82%e7%ac%ac%e5%85%ad%e6%ac%a1%e4%bb%a3%e8%a1%a8%e5%a4%a7%e4%bc%9a%e4%ba%8e8%e6%9c%8826
     * %e6%97%a5%e8%87%b38...&Description=&PostDateTime=2016%2f8%2f29+10%3a27%3a47&ReadCount=81&CommentCount=0&ZambiaCount=1
     * &ShareCount=0&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/08-29/9527185d-f207-4c19-8daf-8111d272f6ea0.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":1,"ShareCount":0,"ReadCount":81,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-08-29T10:27:47.0530509","LiveStatus":3,"ParNum":81,"LiveEndTime":"2016-08-29T10:27:47.0530509",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1976034,"ImgSrc":"/UploadFile/8673/2016/08-29/6a5e889c-906d-4289-a9d1-c83c1632c15ca.jpg",
     * "Title":"周末烈日下，贵溪这群干部调研在路上","Detail":"","PostDateTime":"2016-08-29T10:23:51.0926359","Description":"","Type":1,
     * "Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/1976034?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2016%2f08-29%2f6a5e889c-906d-4289-a9d1-c83c1632c15ca
     * .jpg&Title=%e5%91%a8%e6%9c%ab%e7%83%88%e6%97%a5%e4%b8%8b%ef%bc%8c%e8%b4%b5%e6%ba%aa%e8%bf%99%e7%be%a4%e5%b9%b2%e9%83%a8
     * %e8%b0%83%e7%a0%94%e5%9c%a8%e8%b7%af%e4%b8%8a&Description=&PostDateTime=2016%2f8%2f29+10%3a23%3a51&ReadCount=93
     * &CommentCount=0&ZambiaCount=2&ShareCount=0&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/08-29/6a5e889c-906d-4289-a9d1-c83c1632c15ca.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":2,"ShareCount":0,"ReadCount":93,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-08-29T10:23:51.0926359","LiveStatus":3,"ParNum":93,"LiveEndTime":"2016-08-29T10:23:51.0926359",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1954548,"ImgSrc":"/UploadFile/8673/2016/08-15/e4f424a0-8755-4f94-a8fd-817fb6a2cd524.jpg",
     * "Title":"党政主要领导情牵程永林，一起去医院看看他的康复情况","Detail":"","PostDateTime":"2016-08-15T16:53:43.1130742","Description":"","Type":1,
     * "Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/1954548?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2016%2f08-15%2fe4f424a0-8755-4f94-a8fd-817fb6a2cd524.jpg&Title=%e5%85%9a
     * %e6%94%bf%e4%b8%bb%e8%a6%81%e9%a2%86%e5%af%bc%e6%83%85%e7%89%b5%e7%a8%8b%e6%b0%b8%e6%9e%97%ef%bc%8c%e4%b8%80%e8%b5%b7%e5
     * %8e%bb%e5%8c%bb%e9%99%a2%e7%9c%8b%e7%9c%8b%e4%bb%96...&Description=&PostDateTime=2016%2f8%2f15+16%3a53%3a43&ReadCount=21
     * &CommentCount=0&ZambiaCount=0&ShareCount=0&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/08-15/e4f424a0-8755-4f94-a8fd-817fb6a2cd524.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":0,"ShareCount":0,"ReadCount":21,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-08-15T16:53:43.1130742","LiveStatus":3,"ParNum":21,"LiveEndTime":"2016-08-15T16:53:43.1130742",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1954544,"ImgSrc":"/UploadFile/8673/2016/08-15/68a54602-311d-4dd1-8f44-96e7bc10951c3.jpg",
     * "Title":"市委书记上任第三天便南北座谈，看贵溪当前哪项工作如此重要","Detail":"","PostDateTime":"2016-08-15T16:49:44.1830545","Description":"",
     * "Type":1,"Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/1954544?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2016%2f08-15%2f68a54602-311d-4dd1-8f44-96e7bc10951c3.jpg&Title=%e5%b8%82
     * %e5%a7%94%e4%b9%a6%e8%ae%b0%e4%b8%8a%e4%bb%bb%e7%ac%ac%e4%b8%89%e5%a4%a9%e4%be%bf%e5%8d%97%e5%8c%97%e5%ba%a7%e8%b0%88%ef
     * %bc%8c%e7%9c%8b%e8%b4%b5%e6%ba%aa%e5%bd%93%e5%89%8d...&Description=&PostDateTime=2016%2f8%2f15+16%3a49%3a44&ReadCount
     * =136&CommentCount=0&ZambiaCount=1&ShareCount=0&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/08-15/68a54602-311d-4dd1-8f44-96e7bc10951c3.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":1,"ShareCount":0,"ReadCount":136,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-08-15T16:49:44.1830545","LiveStatus":3,"ParNum":136,"LiveEndTime":"2016-08-15T16:49:44.1830545",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1931607,"ImgSrc":"/UploadFile/8673/2016/08-03/c83ccf66-855a-4ae0-acbd-fff6a21c41593.jpg","Title":"贵溪市主要领导昨日调整到位",
     * "Detail":"","PostDateTime":"2016-08-03T09:28:09","Description":"","Type":1,"Url":"https://cmsuiv3.aheading
     * .com/Article/ArticleRead/1931607?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f%2fcmsv3.aheading
     * .com%2f%2fUploadFile%2f8673%2f2016%2f08-03%2fc83ccf66-855a-4ae0-acbd-fff6a21c41593.jpg&Title=%e8%b4%b5%e6%ba%aa%e5%b8%82
     * %e4%b8%bb%e8%a6%81%e9%a2%86%e5%af%bc%e6%98%a8%e6%97%a5%e8%b0%83%e6%95%b4%e5%88%b0%e4%bd%8d&Description=&PostDateTime
     * =2016%2f8%2f3+9%3a28%3a09&ReadCount=85&CommentCount=0&ZambiaCount=2&ShareCount=1&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/08-03/c83ccf66-855a-4ae0-acbd-fff6a21c41593.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":2,"ShareCount":1,"ReadCount":85,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-08-03T09:28:09.7872513","LiveStatus":3,"ParNum":85,"LiveEndTime":"2016-08-03T09:28:09.7872513",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1931569,"ImgSrc":"/UploadFile/8673/2016/08-03/57c8e9a6-96d8-4d75-acf1-8748f20701525.jpg",
     * "Title":"一段难舍的情怀，张福庆离任答贵溪人民情","Detail":"","PostDateTime":"2016-08-03T09:22:29.8314542","Description":"","Type":1,
     * "Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/1931569?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2016%2f08-03%2f57c8e9a6-96d8-4d75-acf1-8748f20701525.jpg&Title=%e4%b8%80
     * %e6%ae%b5%e9%9a%be%e8%88%8d%e7%9a%84%e6%83%85%e6%80%80%ef%bc%8c%e5%bc%a0%e7%a6%8f%e5%ba%86%e7%a6%bb%e4%bb%bb%e7%ad%94%e8
     * %b4%b5%e6%ba%aa%e4%ba%ba%e6%b0%91%e6%83%85&Description=&PostDateTime=2016%2f8%2f3+9%3a22%3a29&ReadCount=42&CommentCount
     * =0&ZambiaCount=0&ShareCount=0&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/08-03/57c8e9a6-96d8-4d75-acf1-8748f20701525.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":0,"ShareCount":0,"ReadCount":42,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-08-03T09:22:29.8314542","LiveStatus":3,"ParNum":42,"LiveEndTime":"2016-08-03T09:22:29.8314542",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1923306,"ImgSrc":"/UploadFile/8673/2016/07-28/88ca9c73-fae1-45c0-a790-24929a1912966.jpg",
     * "Title":"公告，公告，航空发动机技术实验室将落户贵溪啦！","Detail":"","PostDateTime":"2016-07-28T17:03:12.9998508","Description":"","Type":1,
     * "Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/1923306?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2016%2f07-28%2f88ca9c73-fae1-45c0-a790-24929a1912966.jpg&Title=%e5%85%ac
     * %e5%91%8a%ef%bc%8c%e5%85%ac%e5%91%8a%ef%bc%8c%e8%88%aa%e7%a9%ba%e5%8f%91%e5%8a%a8%e6%9c%ba%e6%8a%80%e6%9c%af%e5%ae%9e%e9
     * %aa%8c%e5%ae%a4%e5%b0%86%e8%90%bd%e6%88%b7%e8%b4%b5...&Description=&PostDateTime=2016%2f7%2f28+17%3a03%3a12&ReadCount=26
     * &CommentCount=0&ZambiaCount=0&ShareCount=0&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/07-28/88ca9c73-fae1-45c0-a790-24929a1912966.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":0,"ShareCount":0,"ReadCount":26,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-07-28T17:03:12.9998508","LiveStatus":3,"ParNum":26,"LiveEndTime":"2016-07-28T17:03:12.9998508",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0},{"ParentClassName":"",
     * "Id":1886503,"ImgSrc":"/UploadFile/8673/2016/07-07/ddff2b92-65fc-4f39-aded-ec4a0b6a7c8ed.jpg","Title":"庆祝建党95周年
     * 贵溪一批党员和基层党组织受表彰","Detail":"","PostDateTime":"2016-07-07T13:14:23","Description":"","Type":1,
     * "Url":"https://cmsuiv3.aheading.com/Article/ArticleRead/1886503?TypeValue=17&MediaType=1&ImgSrc=http%3a%2f
     * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2016%2f07-07%2fddff2b92-65fc-4f39-aded-ec4a0b6a7c8ed
     * .jpg&Title=%e5%ba%86%e7%a5%9d%e5%bb%ba%e5%85%9a95%e5%91%a8%e5%b9%b4+%e8%b4%b5%e6%ba%aa%e4%b8%80%e6%89%b9%e5%85%9a%e5%91
     * %98%e5%92%8c%e5%9f%ba%e5%b1%82%e5%85%9a%e7%bb%84...&Description=&PostDateTime=2016%2f7%2f7+13%3a14%3a23&ReadCount=29
     * &CommentCount=0&ZambiaCount=0&ShareCount=0&NewsPaperGroupId=8673","Tag":"",
     * "ImgSrcs":"/UploadFile/8673/2016/07-07/ddff2b92-65fc-4f39-aded-ec4a0b6a7c8ed.jpg","TypeValue":17,"MediaType":1,
     * "CommentCount":0,"ZambiaCount":0,"ShareCount":0,"ReadCount":29,"IsShowSummary":0,"IsShowPostDateTime":1,
     * "LiveStartTime":"2016-07-07T13:14:23.9219533","LiveStatus":3,"ParNum":29,"LiveEndTime":"2016-07-07T13:14:23.9219533",
     * "QKUrl":"","IsCollected":0,"IsPraised":0,"IsCanComment":0,"ImageType":1,"ImageCount":0}]
     */

    private SubjectArticleBean SubjectArticle;
    private List<ArticleListBean> ArticleList;

    public SubjectArticleBean getSubjectArticle() {
        return SubjectArticle;
    }

    public void setSubjectArticle(SubjectArticleBean SubjectArticle) {
        this.SubjectArticle = SubjectArticle;
    }

    public List<ArticleListBean> getArticleList() {
        return ArticleList;
    }

    public void setArticleList(List<ArticleListBean> ArticleList) {
        this.ArticleList = ArticleList;
    }

    public static class SubjectArticleBean {
        /**
         * Id : 1886127
         * Title : 书记市长活动专题
         * IndexArticleId : 1886127
         * BigImg :
         * Description :
         */

        private int Id;
        private String Title;
        private int IndexArticleId;
        private String BigImg;
        private String Description;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public int getIndexArticleId() {
            return IndexArticleId;
        }

        public void setIndexArticleId(int IndexArticleId) {
            this.IndexArticleId = IndexArticleId;
        }

        public String getBigImg() {
            return BigImg;
        }

        public void setBigImg(String BigImg) {
            this.BigImg = BigImg;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }
    }

    public static class ArticleListBean {
        /**
         * ParentClassName :
         * Id : 2877773
         * ImgSrc : /UploadFile/8673/2018/03-26/136a8b50-92f3-4aa3-af54-9cfd3741f4db7.jpg
         * Title : 梅峰督查经开区、贵塘公路沿线环境综合整治工作
         * Detail :
         * PostDateTime : 2018-03-26T09:36:40.1961883
         * Description : 梅峰督查经开区、贵塘公路沿线环境综合整治工作
         * Type : 4
         * Url : https://cmsuiv3.aheading.com/Article/ArticleRead/2877773?TypeValue=17&MediaType=4&ImgSrc=http%3a%2f
         * %2fcmsv3.aheading.com%2f%2fUploadFile%2f8673%2f2018%2f03-26%2f136a8b50-92f3-4aa3-af54-9cfd3741f4db7.jpg&Title=%e6%a2
         * %85%e5%b3%b0%e7%9d%a3%e6%9f%a5%e7%bb%8f%e5%bc%80%e5%8c%ba%e3%80%81%e8%b4%b5%e5%a1%98%e5%85%ac%e8%b7%af%e6%b2%bf%e7
         * %ba%bf%e7%8e%af%e5%a2%83%e7%bb%bc%e5%90%88%e6%95%b4%e6%b2%bb...&Description=%e6%a2%85%e5%b3%b0%e7%9d%a3%e6%9f%a5%e7
         * %bb%8f%e5%bc%80%e5%8c%ba%e3%80%81%e8%b4%b5%e5%a1%98%e5%85%ac%e8%b7%af%e6%b2%bf%e7%ba%bf%e7%8e%af%e5%a2%83%e7%bb%bc
         * %e5%90%88%e6%95%b4%e6%b2%bb%e5%b7%a5%e4%bd%9c&PostDateTime=2018%2f3%2f26+9%3a36%3a40&ReadCount=80&CommentCount=0
         * &ZambiaCount=3&ShareCount=0&NewsPaperGroupId=8673
         * Tag :
         * ImgSrcs : /UploadFile/8673/2018/03-26/136a8b50-92f3-4aa3-af54-9cfd3741f4db7.jpg
         * TypeValue : 17
         * MediaType : 4
         * CommentCount : 0
         * ZambiaCount : 3
         * ShareCount : 0
         * ReadCount : 80
         * IsShowSummary : 0
         * IsShowPostDateTime : 1
         * LiveStartTime : 2018-03-26T09:36:40.1961883
         * LiveStatus : 3
         * ParNum : 80
         * LiveEndTime : 2018-03-26T09:36:40.1961883
         * QKUrl :
         * IsCollected : 0
         * IsPraised : 0
         * IsCanComment : 0
         * ImageType : 1
         * ImageCount : 0
         */

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
