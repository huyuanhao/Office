package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;

/***
 * 文件名：ConServiceActivity
 * 描述：便民服务加载网页的界面
 * 作者：chenhao
 * 时间：2017/11/30
 * 版权：v1.0
 */
public class ConServiceActivity extends BaseActivity implements OnClickListener{
    private WebView webView;
    private ProgressBar pb;
    private WebSettings mWebSettings;
    private RelativeLayout rl_error;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        initTitleBar(title, this, null);
        webView = (WebView) findViewById(R.id.webview);
        pb =(ProgressBar) findViewById(R.id.progressBar);
        rl_error = (RelativeLayout) findViewById(R.id.rl_error);
        //加载需要显示的网页
        webView.loadUrl(url);
        setUpView();

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_con_service;
    }

    private void setUpView() {
        mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);    //允许加载javascript
        mWebSettings.setAppCacheEnabled(true);// 设置缓存
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 设置缓存模式,一共有四种模式
        mWebSettings.setSupportZoom(true);          //允许缩放
        mWebSettings.setBuiltInZoomControls(true);  //原网页基础上缩放
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setUseWideViewPort(true);      //任意比例缩放
        mWebSettings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(webClient);  //设置Web视图
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if(newProgress==100){
                    pb.setVisibility(View.GONE);//加载完网页进度条消失
                }else{
                    pb.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pb.setProgress(newProgress);//设置进度值
                }

            }
        });
    }
    /***
     * 设置Web视图的方法
     */
    WebViewClient webClient = new WebViewClient(){//处理网页加载失败时
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            ShowErrorPage();
        }
        public void onPageFinished(WebView view, String url) {//处理网页加载成功时
            if (!checkNetworkState()){
                ShowErrorPage();
                return;
            }
            rl_error.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }
    };
    /***
     * 显示加载失败时自定义的网页
     */
    protected void ShowErrorPage() {
        webView.setVisibility(View.GONE);
        rl_error.setVisibility(View.VISIBLE);
        rl_error.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if(webView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                webView.goBack();
                return true;
            }
            else {//当webview处于第一页面时,直接退出程序
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
        }
    }
}
