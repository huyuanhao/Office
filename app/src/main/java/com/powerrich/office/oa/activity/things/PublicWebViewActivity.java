package com.powerrich.office.oa.activity.things;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LocationUtils;
import com.yt.simpleframe.utils.SharedPreferencesUtils;
import com.yt.simpleframe.view.LoadingWebView;

/**
 * @author MingPeng
 * @date 2018/6/15
 */

public class PublicWebViewActivity extends BaseActivity implements LocationUtils.ILocationListener {

    private MyWebViewClient webViewClient;
    private String url;
    private LoadingWebView mWebView;
    private String lat;
    private String lng;
    private String transaction_place;
    private String endCity = "鹰潭市";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_public_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");

        mWebView = ((LoadingWebView) findViewById(R.id.webView));
        initTitleBar(title, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWebView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                    mWebView.goBack();
                } else {//当webview处于第一页面时,直接退出程序
                    finish();
                }
            }
        }, null);

//        if(!NetUtil.isConnected(this)){
//            mWebView.setVisibility(View.GONE);
//        }
        mWebView.addProgressBar();

        boolean locationPermission = intent.getBooleanExtra("LocationPermission", false);
        boolean isRoute = intent.getBooleanExtra("isRoute", false);
        if (locationPermission) {//只需要请求定位权限
            LocationUtils.requestLocation(this, false, this);
        }
        if (isRoute) {
            lat = intent.getStringExtra("lat");
            lng = intent.getStringExtra("lng");
            transaction_place = intent.getStringExtra("transaction_place");
            LocationUtils.requestLocation(this, true, this);
            return;
        }

        initWebView();
    }

    private void initWebView() {
        webViewClient = new MyWebViewClient();
        mWebView.setWebViewClient(webViewClient);
        mWebView.loadUrl(url);
    }

    /**
     * 通过div的id属性删除div元素
     *
     * @return
     */
    public String getClearAdDivJsById() {
        String js = "javascript:function hideAd() {";
        String[] adIdDivs = {"air-footer", "e_b_div_1629", "e_b_div_1630", "e_b_div_1631", "e_b_div_1632", "e_b_div_1633", "e_b_div_1684", "e_b_div_1685", "e_b_div_1686"};
        for (int i = 0; i < adIdDivs.length; i++) {
            //通过div的id属性删除div元素
            js += "var adDiv" + i + "= document.getElementById('" + adIdDivs[i] + "');if(adDiv" + i + " != null)adDiv" + i + ".parentNode.removeChild(adDiv" + i + ");";
        }
        js += "}";
        return js;
    }

    /**
     * 通过div的class属性删除div元素
     *
     * @return
     */
    public String getClearAdDivJsByClass() {
        String js = "javascript:function hideAd() {";
//        String[] adClassDivs = { "head", "pic_container", "pic_row", "item", "topad", "container", "container", "container", "container", "xuan", "box-all", "xwtt", "ppyc", "tqtt", "fish", "zbdq", "fnav"};
//        String[] adClassDivs = { "icon -back-arrow", "btn -flat needsclick back-btn -col-auto"};
        String[] adClassDivs = { "icon -back-arrow"};
        for (int i = 0; i < adClassDivs.length; i++) {
            //通过div的class属性隐藏div元素
            js += "var adDiv" + i + "= document.getElementsByClassName('" + adClassDivs[i] + "');if(adDiv" + i + " != null)" +
                    "{var x; for (x = 0; x < adDiv" + i + ".length; x++) {adDiv" + i + "[x].style.display='none';}}";
        }
        js += "}";
        return js;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            if(mWebView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            String jsByClass = getClearAdDivJsByClass();
            view.loadUrl(jsByClass); //加载js方法代码
            view.loadUrl("javascript:hideAd();"); //调用js方法
            view.loadUrl(getClearAdDivJsById());
            view.loadUrl("javascript:hideAd();");

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (mWebView.isAcceptedScheme(url)) {
                return false;
            }
            try {
                // 以下固定写法,表示跳转到第三方应用
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } catch (Exception e) {
                // 防止没有安装的情况
                e.printStackTrace();
            }
            return true;
        }
    }

    @Override
    public void onSuccessLocation() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!BeanUtils.isEmptyStr(lat)) {
                    String curLat = (String) SharedPreferencesUtils.getParam(PublicWebViewActivity.this, "lat", "");
                    String curLng = (String) SharedPreferencesUtils.getParam(PublicWebViewActivity.this, "lng", "");
                    String curCity = (String) SharedPreferencesUtils.getParam(PublicWebViewActivity.this, "city", "");
                    url = "http://api.map.baidu.com/direction?origin=latlng:" + curLat + "," + curLng + "|name:我的位置" +
                            "&destination=latlng:" + lat + "," + lng + "|name:" + transaction_place + "&mode=driving&origin_region="
                            + curCity + "&destination_region=" + endCity + "&output=html&src=webapp.baidu.openAPIdemo";
                }
                initWebView();
            }
        });
    }

    @Override
    public void onFailedLocation() {
        DialogUtils.showToast(PublicWebViewActivity.this, "获取当前位置信息失败");
    }
}
