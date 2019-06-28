package com.powerrich.office.oa.activity.things;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.idl.util.NetUtil;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LocationUtils;
import com.powerrich.office.oa.tools.StatusBarUtils;
import com.powerrich.office.oa.tools.TextPinyinUtil;
import com.yt.simpleframe.utils.SharedPreferencesUtils;
import com.yt.simpleframe.view.LoadingWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/15.
 */

public class WebNearActivity extends BaseActivity implements LocationUtils.ILocationListener {

    @BindView(R.id.system_back)
    ImageView systemBack;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.btn_top_right)
    ImageView btnTopRight;
    @BindView(R.id.tv_top_right)
    TextView tvTopRight;
    @BindView(R.id.btn_custom_right)
    ImageView btnCustomRight;
    @BindView(R.id.webView)
    LoadingWebView webView;
    @BindView(R.id.error_view)
    LinearLayout errorView;
    private String type;
    String url, city, lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");

        LocationUtils.requestLocation(this, true, this);

        if(!NetUtil.isConnected(this)){
            webView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
        }else {
            webView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
        }
        setWebSetting();
        systemBack.setVisibility(View.VISIBLE);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
    }

    /**
     * 初始化控件
     */
    @SuppressLint("JavascriptInterface")
    private void initView() {
//        getActivity().startActivity(new Intent()
//                .setClass(getActivity(), WebNearActivity.class)
//                .putExtra("type","1"));
        city = (String) SharedPreferencesUtils.getParam(this, "city", "鹰潭市");
        lat = (String) SharedPreferencesUtils.getParam(this, "lat", "28.27");
        lng = (String) SharedPreferencesUtils.getParam(this, "lng", "117.07");
        if (city.contains("市")) {
            city = city.replace("市", "");
        }
        String pinyingCity = TextPinyinUtil.getInstance().getPinyin(city);
        switch (type) {
            case "0":
                tvTopTitle.setText("景点");
//                url = "https://m.nuomi.com/";
//                url = "http://1807.jxtrip.cc/wx/app/saleProduct?type=2";
                url = "https://uri.amap.com/search?keyword=景点&center=" + lat + "," + lng + "&view=list&src=mypage&coordinate=gaode&callnative=0";
//                url = "http://api.map.baidu.com/place/search?query=景点&location=" + lat + "," + lng + "&radius=1000&output=html";
                break;
            case "1":
               tvTopTitle.setText("美食");
//                url = "https://m.tuniu.com/g1714/specialty-0-0/";
                url = "https://uri.amap.com/search?keyword=美食&center=" + lat + "," + lng + "&view=list&src=mypage&coordinate=wgs84&callnative=0";
//                url = "http://api.map.baidu.com/place/search?query=美食&location=" + lat + "," + lng + "&radius=1000&output=html";
                break;
            case "2":
               tvTopTitle.setText("酒店");
//                url = "http://1807.jxtrip.cc/wx/app/saleProduct?type=3";
                url = "https://uri.amap.com/search?keyword=酒店&center=" + lat + "," + lng + "&view=list&src=mypage&coordinate=gaode&callnative=0";
//                url = "http://api.map.baidu.com/place/search?query=酒店&location=" + lat + "," + lng + "&radius=1000&output=html";
                break;
            case "3":
                tvTopTitle.setText("银行");
//                url = "http://m.weather.com.cn/d/town/index?lat=" + lat + "&lon=" + lng;
                url = "https://uri.amap.com/search?keyword=银行&center=" + lat + "," + lng + "&view=list&src=mypage&coordinate=gaode&callnative=0";
//                url = "http://api.map.baidu.com/place/search?query=银行&location=" + lat + "," + lng + "&radius=1000&output=html";
                break;
            case "4":
               tvTopTitle.setText("公厕");
//                Uri uri= Uri.parse("http://api.map.baidu.com/place/search?query=医院&location="+lat+","+lng+"&radius=1000&output=html&src=西安扁珍|扁珍健康");
//                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
//                startActivity(intent);
                url = "https://uri.amap.com/search?keyword=公厕&center=" + lat + "," + lng + "&view=list&src=mypage&coordinate=gaode&callnative=0";
//                url = "http://api.map.baidu.com/place/search?query=公厕&location=" + lat + "," + lng + "&radius=1000&output=html";
                break;
            case "5":
                tvTopTitle.setText("加油站");
                url = "https://uri.amap.com/search?keyword=加油站&center=" + lat + "," + lng + "&view=list&src=mypage&coordinate=gaode&callnative=0";
//                url = "http://api.map.baidu.com/place/search?query=加油站&location=" + lat + "," + lng + "&radius=1000&output=html";
                break;
            case "6":
                tvTopTitle.setText("医院");
                url = "https://uri.amap.com/search?keyword=医院&center=" + lat + "," + lng + "&view=list&src=mypage&coordinate=gaode&callnative=0";
//                url = "http://api.map.baidu.com/place/search?query=医院&location=" + lat + "," + lng + "&radius=1000&output=html";
                break;
                case "19":
                tvTopTitle.setText("天气");
                url = "http://vlife.weather.com.cn/mweather/101241101.shtml#1";
//                url = "http://weather1.sina.cn/?code=yingtan&vt=4";
                break;
            case "20":
                tvTopTitle.setText("公交站");
                url = "https://uri.amap.com/search?keyword=公交站&center=" + lat + "," + lng + "&view=list&src=mypage&coordinate=gaode&callnative=0";
//                url = "http://api.map.baidu.com/place/search?query=公交站&location=" + lat + "," + lng + "&radius=1000&output=html";
                break;
            case "21"://实时路况
                tvTopTitle.setText("实时路况");
               url =  "https://map.baidu.com/mobile/webapp/index/index/foo=bar/vt=map&traffic=on";
//               url = "http://api.map.baidu.com/marker?location=" + lat + "," + lng + "&radius=1000&output=html";
//                url = "https://ditu.amap.com/city=310000&traffic=1&src=mypage&callnative=0";
//                url = "https://uri.amap.com/center=" + lat + "," + lng + "&view=list&src=mypage&coordinate=gaode&callnative=0";
//                url =  "http://map.baidu.com/?latlng=" + lat + "," + lng + "&autoOpen=true";
                break;
            case "22":
                tvTopTitle.setText("定点医院");
                url = "https://uri.amap.com/search?keyword=定点医院&center=" + lat + "," + lng + "&view=list&src=mypage&coordinate=gaode&callnative=0";
//                url = "http://api.map.baidu.com/place/search?query=定点医院&location=" + lat + "," + lng + "&radius=1000&output=html";
                break;
                //baidumap://map/place/search?query=%E5%8A%A0%E6%B2%B9%E7%AB%99&src=webapp.poi.poilistpg
            default:
                break;
        }
        webView.requestFocusFromTouch();//可弹出输入框
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);
    }

    @OnClick({R.id.system_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            default:
                break;
        }
    }

    public void setWebSetting() {
        webView.addProgressBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroyWebView();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String js = getClearAdDivJs();
            //加载js方法代码
            webView.loadUrl(js);
            //调用js方法
            webView.loadUrl("javascript:hideAd();");
        }
    }

    /**
     * 隐藏html页面部分内容
     *
     * @return
     */
    public String getClearAdDivJs() {
        String js = "javascript:function hideAd() {";
        String[] adDivs = {"common-footer-widget", "common-bottombanner-widget-fis"};
//        String[] adDivs = {"styleguide common-widget-footer -ft-tertiary", "styleguide common-widget-bottom-banner-changeId"};
        for (int i = 0; i < adDivs.length; i++) {
            //通过div的id属性删除div元素
            js += "var adDiv" + i + "= document.getElementById('" + adDivs[i] + "');if(adDiv" + i + " != null)adDiv" + i + ".parentNode.removeChild(adDiv" + i + ");";
            //通过div的class属性隐藏div元素
//            js += "var adDiv" + i + "= document.getElementsByClassName('" + adDivs[i] + "');if(adDiv" + i + " != null)" +
//                    "{var x; for (x = 0; x < adDiv" + i + ".length; x++) {adDiv" + i + "[x].style.display='none';}}";
        }
        js += "}";
        return js;
    }

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSuccessLocation() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });
    }

    @Override
    public void onFailedLocation() {
        DialogUtils.showToast(WebNearActivity.this, "获取当前位置信息失败");
    }
}
