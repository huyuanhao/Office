package com.powerrich.office.oa.activity.things;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebSettings;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.VolunteerInfoBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.view.LoadingWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/30
 * 版权：
 */

public class VolunteerInfoActivity extends YTBaseActivity {


    @BindView(R.id.lwv)
    LoadingWebView mLwv;
    private String id;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_volunteer_info);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().getString("id");
        setTitle("");
        getData();
    }

    private void getData() {
        ApiManager.getApi().getVolunteerInfo(RequestBodyUtils.queryVolunteerInfo(id))
                .compose(RxSchedulers.<VolunteerInfoBean>io_main())
                .subscribe(new BaseSubscriber<VolunteerInfoBean>() {
                    @Override
                    public void result(VolunteerInfoBean bean) {
                        if (null != bean && null != bean.getDATA()) {
                            String strs = bean.getDATA().getNEWS_CONTENT();
                            strs = strs.replaceAll("&lt;", "<").replaceAll("&amp;nbsp;","&nbsp&nbsp");
                            strs = strs.replaceAll("<img src","<div align=\"center\"><img src");
                            strs = strs.replaceAll("\"max-width:100%;\">","\"max-width:100%;\"></div>");
                            setTitle(bean.getDATA().getTITLE());
                            WebSettings mWebSettings = mLwv.getSettings();
                            mWebSettings.setUseWideViewPort(true);
                            mWebSettings.setLoadWithOverviewMode(true);
                            strs = "<h3 align=\"center\">"+bean.getDATA().getTITLE()+"</h3>"+"<div align='right'> <font size=2 color='#999999'>"+"来源:"+
                                    bean.getDATA().getSOURCE()+" 时间："+bean.getDATA().getCREATE_DATE()
                                    +"</font></div>"+strs;
                            mLwv.loadDataWithBaseURL("", strs, "text/html", "utf-8", null);
                            int tesize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100, VolunteerInfoActivity.this.getResources().getDisplayMetrics());
                            mLwv.setTextZoom(tesize);
                            mLwv.setZoom(false);
                        }
                    }
                });
    }

}
