package com.powerrich.office.oa.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.notify.NotifyKey;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.MessageBean;
import com.yt.simpleframe.http.bean.entity.MessageInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.notification.NotificationCenter;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11/011
 * 版权：
 */
public class MessageDetailActivity extends YTBaseActivity {

    @BindView(R.id.tv_content_txt)
    TextView mTvContentTxt;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("消息详情");
        showBack();
        id = getIntent().getExtras().getString("id");
        getDate();
    }

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void getDate() {
        //调用了查看通知接口，就发送消息更新未读记录
        NotificationCenter.defaultCenter.postNotification(NotifyKey.UPDATE_MESSAGE_KEY);
        ApiManager.getApi().getInformById(RequestBodyUtils.getMessageInfo(id))
                .compose(RxSchedulers.<MessageBean>io_main())
                .subscribe(new BaseSubscriber<MessageBean>() {
                    @Override
                    public void result(MessageBean listBean) {
                        if (null == listBean || null == listBean.getDATA()) return;
                        MessageInfo data = listBean.getDATA();
                        String content = data.getCONTENT();
                        try {
                            JSONObject object = new JSONObject(content);
                            JSONObject inform = object.getJSONObject("INFORM");
                            String statusmsg = inform.getString("STATUSMSG");
                            mTvContentTxt.setText(statusmsg);
                            mTvTime.setText(data.getPUSHTIME());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

}
