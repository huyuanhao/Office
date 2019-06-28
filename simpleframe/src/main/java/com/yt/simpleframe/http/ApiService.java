package com.yt.simpleframe.http;


import com.yt.simpleframe.http.bean.AddressListBean;
import com.yt.simpleframe.http.bean.AdvisoryInfoBean;
import com.yt.simpleframe.http.bean.AdvisoryListBean;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.CollectionListBean;
import com.yt.simpleframe.http.bean.EvaluationBean;
import com.yt.simpleframe.http.bean.LoginBean;
import com.yt.simpleframe.http.bean.LogisticsInfoBean;
import com.yt.simpleframe.http.bean.LogisticsListBean;
import com.yt.simpleframe.http.bean.MessageBean;
import com.yt.simpleframe.http.bean.NewImageBeanListBean;
import com.yt.simpleframe.http.bean.ProcessListCommonBean;
import com.yt.simpleframe.http.bean.ProcessMaterialsDepotBeanListBean;
import com.yt.simpleframe.http.bean.ProcessListBean;
import com.yt.simpleframe.http.bean.ReFundHKJHInfoBean;
import com.yt.simpleframe.http.bean.ReFundInfoBean;
import com.yt.simpleframe.http.bean.ReFundYQHKInfoBean;
import com.yt.simpleframe.http.bean.ReservationListBean;
import com.yt.simpleframe.http.bean.SocialInfoBean;
import com.yt.simpleframe.http.bean.UserInfoBean;
import com.yt.simpleframe.http.bean.ValiCodeBean;
import com.yt.simpleframe.http.bean.VolunteerImgsBean;
import com.yt.simpleframe.http.bean.VolunteerInfoBean;
import com.yt.simpleframe.http.bean.VolunteerListBean;
import com.yt.simpleframe.http.bean.WaterInfoBean;
import com.yt.simpleframe.http.bean.entity.CertificateInfo;
import com.yt.simpleframe.http.bean.entity.NewsInfo;
import com.yt.simpleframe.http.bean.entity.ReFundHKJHInfo;
import com.yt.simpleframe.http.bean.entity.ReFundYQCXInfo;
import com.yt.simpleframe.http.bean.xmlentity.CertificateListBean;
import com.yt.simpleframe.http.bean.xmlentity.CertificateListNewBean;
import com.yt.simpleframe.http.bean.xmlentity.MessageListBean;
import com.yt.simpleframe.http.bean.xmlentity.NewsInfoBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/5/21 0021
 * 版权：
 */

public interface ApiService {

    //登录
//    @Headers({ "Content-Type: text/xml;charset=UTF-8"})

    @POST("services/XzsposServices")
    Observable<LoginBean> login(@Body RequestBody body);

    @POST("services/XzsposServices")
    Observable<NewImageBeanListBean> getHomeNewsImg(@Body RequestBody body);

    //返回基类BaseBean 全部走这个方法
    @POST("services/XzsposServices")
    Observable<BaseBean> exeNormal(@Body RequestBody body);

    @POST("services/XzsposServices")
    Observable<ProcessListBean> getProcessList(@Body RequestBody body);

    @POST("services/XzsposServices")
    Observable<ProcessMaterialsDepotBeanListBean> getHistoryProcessList(@Body RequestBody body);


    /**社保基本信息*/
    @POST("services/XzsposServices")
    Observable<SocialInfoBean> SocialInfo(@Body RequestBody body);

    /**供水服务*/
    @POST("services/XzsposServices")
    Observable<WaterInfoBean> WaterInfo(@Body RequestBody body);

    /**咨询列表*/
    @POST("services/XzsposServices")
    Observable<AdvisoryListBean> getExchangeList(@Body RequestBody body);

    /**咨询详情*/
    @POST("services/XzsposServices")
    Observable<AdvisoryInfoBean> getExchangeInfo(@Body RequestBody body);

    /**收藏事项列表*/
    @POST("services/XzsposServices")
    Observable<CollectionListBean> collectItemList(@Body RequestBody body);

    /**预约列表*/
    @POST("services/XzsposServices")
    Observable<ReservationListBean> getMyAppointmentList(@Body RequestBody body);

    /**获取验证码*/
    @POST("services/XzsposServices")
    Observable<ValiCodeBean> getPhoneValiCode(@Body RequestBody body);

    /**获取物流列表*/
    @POST("services/XzsposServices")
    Observable<LogisticsListBean> getExpressList(@Body RequestBody body);

    /**获取物流列表*/
    @POST("services/XzsposServices")
    Observable<LogisticsListBean> getNewTitle(@Body RequestBody body);


    @POST("services/XzsposServices")
    Observable<NewsInfoBean> getNewsList(@Body RequestBody body);

    @POST("services/XzsposServices")
    Observable<NewsInfoBean> getHomeNewsImgs(@Body RequestBody body);

    @POST("services/XzsposServices")
    Observable<NewsInfo> getNewsDetails(@Body RequestBody body);

    @POST("services/XzsposServices")
    Observable<NewsInfoBean> getNews(@HeaderMap Map<String,String> headerMap, @Body String body);

    @POST("services/XzsposServices")
    Observable<AddressListBean> getAddressManager(@Body RequestBody body);

    @POST("services/XzsposServices")
    Observable<LoginBean> register(@Body RequestBody body);

    @POST("services/XzsposServices")
    Observable<LoginBean> comparisonIdentityInfo(@Body RequestBody body);

    @Multipart
    @POST("UploadItemFileServlet")
    Observable<BaseBean> uploadImg(@Path("type") int type);

    /**保存用户信息*/
    @POST("services/XzsposServices")
    Observable<BaseBean> saveInfo(@Body RequestBody body);


    /**根据手机号查找user*/
    @POST("services/XzsposServices")
    Observable<UserInfoBean> queryUser(@Body RequestBody body);

    /**查找消息推送列表*/
    @POST("services/XzsposServices")
    Observable<MessageListBean> getPushMessageList(@Body RequestBody body);

    /**查找消息*/
    @POST("services/XzsposServices")
    Observable<MessageBean> getInformById(@Body RequestBody body);

    /**证件列表*/
    @POST("services/XzsposServices")
    Observable<CertificateListBean> getCertificateList(@Body RequestBody body);

    /**历史证件列表*/
    @POST("services/XzsposServices")
    Observable<ProcessListCommonBean<CertificateInfo>> getHistoryCertificateList(@Body RequestBody body);

    /**证件列表 新的接口*/
    @POST("services/XzsposServices")
    Observable<CertificateListNewBean> getCertificateNewList(@Body RequestBody body);



    /**查看评价*/
    @POST("services/XzsposServices")
    Observable<EvaluationBean> getEvaluateInfo(@Body RequestBody body);

    /**获取自愿者资讯列表*/
    @POST("services/XzsposServices")
    Observable<VolunteerListBean> getVolunteerList(@Body RequestBody body);

    /**获取自愿者资讯列表*/
    @POST("services/XzsposServices")
    Observable<VolunteerInfoBean> getVolunteerInfo(@Body RequestBody body);

    /**获取自愿者资讯列表*/
    @POST("services/XzsposServices")
    Observable<VolunteerImgsBean> getVolunteerImgs(@Body RequestBody body);


    /**获取自愿者资讯列表*/
    @POST("services/XzsposServices")
    Observable<LogisticsInfoBean> getLogisticsInfo(@Body RequestBody body);

    /**还款明细user*/
    @POST("services/XzsposServices")
    Observable<ReFundInfoBean> queryDkhkmxcx(@Body RequestBody body);


    /**还款计划*/
    @POST("services/XzsposServices")
    Observable<ReFundHKJHInfoBean> queryHKJH(@Body RequestBody body);


    /**逾期未还款*/
    @POST("services/XzsposServices")
    Observable<ReFundYQHKInfoBean> queryYQWHK(@Body RequestBody body);





}
