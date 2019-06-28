package com.powerrich.office.oa.thirdapp.http;


import com.yt.simpleframe.http.exception.ExceptionHandle;
import com.yt.simpleframe.notification.NotificationCenter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by fanliang on 18/1/3.
 */

public abstract class ThirdBaseSubscriber<T> implements Observer<T> {

    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }


    @Override
    public void onComplete() {

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
        result(t);
//        if("0".equals(t.getCode())){
//            result(t);
//        }else if("-403".equals(t.getCode())){
//            NotificationCenter.defaultCenter.postNotification("error_code","-403");
//        }else{
//            NotificationCenter.defaultCenter.postNotification("error_code",t.getMessage());
//        }

    }

    public abstract void result(T t);
//    public abstract void completed();

}
