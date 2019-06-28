package com.powerrich.office.oa.thirdapp.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebSettings;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.thirdapp.http.ThirdApiManager;
import com.powerrich.office.oa.thirdapp.http.ThirdBaseSubscriber;
import com.powerrich.office.oa.thirdapp.http.bean.ArticleInfoBean;
import com.powerrich.office.oa.view.LoadingX5WebView;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.utils.StringUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/25
 * 版权：
 */

public class ArticleInfoActivity extends YTBaseActivity {


    @BindView(R.id.lwv_web)
    LoadingX5WebView mLwvWeb;

    private int nid;
    private int id;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            url = getIntent().getExtras().getString("url");
        }catch (Exception e){

        }
        if (StringUtil.isEmpty(url)) {
            id = getIntent().getExtras().getInt("id");
            nid = getIntent().getExtras().getInt("nid");
            getData();
        } else {
            initWebView(url);
        }
        setTitle("详情");


    }


    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_article_info);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    //    http://cmswebv3.aheading.com/api/Article/GetArticle/?Id=3063862&NewsPaperGroupIdx=8673&Token=
    private void getData() {
        ThirdApiManager.getApi().getArticleInfo(id, nid, "")
                .compose(RxSchedulers.<ArticleInfoBean>io_main())
                .compose(this.<ArticleInfoBean>loadingDialog())
                .subscribe(new ThirdBaseSubscriber<ArticleInfoBean>() {
                    @Override
                    public void result(ArticleInfoBean bean) {
                        initView(bean);
                    }
                });
    }

    @SuppressLint("ResourceType")
    private void initView(ArticleInfoBean bean) {
        mLwvWeb.addProgressBar();
        if (!StringUtil.isEmpty(bean.getUrl())) {
            initWebView(bean.getUrl());
        } else {
            mLwvWeb.setZoom(false);
            mLwvWeb.loadDataWithBaseURL("",  getNewContent(bean.getDetail()), "text/html", "utf-8", null);
            int tesize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100, this.getResources().getDisplayMetrics());
            mLwvWeb.setTextZoom(tesize);
            mLwvWeb.setZoom(false);
        }

    }


    private void initWebView(String url) {
        mLwvWeb.loadUrl(url);

        mLwvWeb.getSettings().setBlockNetworkImage(false);//解决图片不显示
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLwvWeb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 修改ua使得web端正确判断
        String ua = mLwvWeb.getSettings().getUserAgentString();
        mLwvWeb.getSettings().setUserAgentString(ua + "; Aheading ImageSwitcher VideoCanFullScreen");
//        webSettings.setUserAgentString("Mozilla/5.0 (Linux; Android 7.0; Redmi Note 4X Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MBrowser/6.2 TBS/044112 Mobile Safari/537.36
//          qianchen Aheading ImageSwitcher VideoCanFullScreen SetPageShare openpageshare");
    }

    public static String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }
}


