package com.yt.simpleframe.http;


import android.util.Log;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.exception.ExceptionHandle;
import com.yt.simpleframe.notification.NotificationCenter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by fanliang on 18/1/3.
 */

public abstract class BaseSubscriber<T extends BaseBean> implements Observer<T> {

    private Disposable disposable;
    private RefreshLayout refreshLayout;
    private boolean haveToast = true;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }


    public BaseSubscriber(RefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }
    public BaseSubscriber() {
    }

    public BaseSubscriber(boolean haveToast) {
        this.haveToast = haveToast;
    }

    @Override
    public void onComplete() {
        if(refreshLayout != null){
            refreshLayout.finishLoadmore();
            refreshLayout.finishRefresh();
        }
    }

    @Override
    public void onError(Throwable e) {
        ExceptionHandle.ResponeThrowable throwable = ExceptionHandle.handleException(e);
        e.printStackTrace();
        NotificationCenter.defaultCenter.postNotification("exception",throwable);
        onComplete();
    }

    @Override
    public void onNext(T t) {
        Log.i("RefundDetailActivity:", "onNext: "+t.getCode()+"-message:"+t.getMessage());
        if("0".equals(t.getCode()) || "-4".equals(t.getCode())){
         result(t);
        }else if(!haveToast){
            return;
        }else if("-403".equals(t.getCode())){
            NotificationCenter.defaultCenter.postNotification("error_code","-403");
        }else if("-99".equals(t.getCode())){
            NotificationCenter.defaultCenter.postNotification("error_code","-99");
        }else{
                NotificationCenter.defaultCenter.postNotification("error_code",t.getMessage());
        }

    }

    public abstract void result(T t);
//    public abstract void completed();

}
