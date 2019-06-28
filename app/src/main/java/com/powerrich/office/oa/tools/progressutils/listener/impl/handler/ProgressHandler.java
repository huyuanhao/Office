package com.powerrich.office.oa.tools.progressutils.listener.impl.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.powerrich.office.oa.tools.progressutils.listener.impl.UIProgressListener;
import com.powerrich.office.oa.tools.progressutils.listener.impl.model.ProgressModel;

import java.lang.ref.WeakReference;


/**
 */
public abstract class ProgressHandler extends Handler {
    public static final int UPDATE = 0x01;
    public static final int START = 0x02;
    public static final int FINISH = 0x03;
    //弱引用
    private final WeakReference<UIProgressListener> mUIProgressListenerWeakReference;

    public ProgressHandler(UIProgressListener uiProgressListener) {
        super(Looper.getMainLooper());
        mUIProgressListenerWeakReference = new WeakReference<>(uiProgressListener);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case UPDATE: {
                UIProgressListener uiProgressListener = mUIProgressListenerWeakReference.get();
                if (uiProgressListener != null) {
                    //获得进度实体类
                    ProgressModel progressModel = (ProgressModel) msg.obj;
                    //回调抽象方法
                    progress(uiProgressListener, progressModel.getCurrentBytes(), progressModel.getContentLength(), progressModel.isDone());
                }
                break;
            }
            case START: {
                UIProgressListener uiProgressListener = mUIProgressListenerWeakReference.get();
                if (uiProgressListener != null) {
                    //获得进度实体类
                    ProgressModel progressModel = (ProgressModel) msg.obj;
                    //回调抽象方法
                    start(uiProgressListener, progressModel.getCurrentBytes(), progressModel.getContentLength(), progressModel.isDone());

                }
                break;
            }
            case FINISH: {
                UIProgressListener uiProgressListener = mUIProgressListenerWeakReference.get();
                if (uiProgressListener != null) {
                    //获得进度实体类
                    ProgressModel progressModel = (ProgressModel) msg.obj;
                    //回调抽象方法
                    finish(uiProgressListener, progressModel.getCurrentBytes(), progressModel.getContentLength(), progressModel.isDone());
                }
                break;
            }
            default:
                super.handleMessage(msg);
                break;
        }
    }

    public abstract void start(UIProgressListener uiProgressListener,long currentBytes, long contentLength, boolean done);
    public abstract void progress(UIProgressListener uiProgressListener,long currentBytes, long contentLength, boolean done);
    public abstract void finish(UIProgressListener uiProgressListener,long currentBytes, long contentLength, boolean done);
}
