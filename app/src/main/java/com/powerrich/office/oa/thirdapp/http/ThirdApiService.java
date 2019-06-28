package com.powerrich.office.oa.thirdapp.http;


import com.powerrich.office.oa.thirdapp.http.bean.ArticleInfoBean;
import com.powerrich.office.oa.thirdapp.http.bean.ArticleTopicBean;
import com.powerrich.office.oa.thirdapp.http.bean.GetArticleBean;
import com.powerrich.office.oa.thirdapp.http.bean.GetArticleListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/5/21 0021
 * 版权：
 */

public interface ThirdApiService {


    @GET("api/Article/List")
    Observable<GetArticleListBean> getArticleList(@Query("ClassifyIdx") int ClassifyIdx,
                                                  @Query("ClassifyType") int ClassifyType,
                                                  @Query("IsFound") int IsFound,
                                                  @Query("NewspaperIdx") int NewspaperIdx,
                                                  @Query("PageIndex") int PageIndex,
                                                  @Query("PageSize") int PageSize,
                                                  @Query("Type") int Type);

    //    http://cmswebv3.aheading.com/api/Article/ClassifyDetail?Nid=8673&Pidx=31085&Token=&TypeId=17
    //点击进去的titlebar
    @GET("api/Article/ClassifyDetail")
    Observable<GetArticleBean> getArticle(@Query("Nid") int Nid,
                                          @Query("Pidx") int Pidx,
                                          @Query("Token") String Token,
                                          @Query("TypeId") int TypeId);

    //http://cmswebv3.aheading.com/api/Article/SubjectList?NewspaperIdx=8673&PageIndex=1&PageSize=15&SubjectId=1886127
    @GET("api/Article/SubjectList")
    Observable<ArticleTopicBean> getArticleTopic(@Query("NewspaperIdx") int NewspaperIdx,
                                                 @Query("PageIndex") int PageIndex,
                                                 @Query("PageSize") int PageSize,
                                                 @Query("SubjectId") int SubjectId);
//    http://cmswebv3.aheading.com/api/Article/GetArticle/?Id=3063862&NewsPaperGroupIdx=8673&Token=

    @GET("api/Article/GetArticle")
    Observable<ArticleInfoBean> getArticleInfo(@Query("Id") int Id,
                                               @Query("NewsPaperGroupIdx") int NewsPaperGroupIdx,
                                               @Query("Token") String token);

}
