package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.BaseRefreshActivity;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.entity.MessageInfo;
import com.yt.simpleframe.http.bean.xmlentity.MessageListBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.view.recyclerview.viewholder.RecycleviewViewHolder;

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
public class MessageListActivity extends BaseRefreshActivity<MessageInfo> {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的消息");
        showBack();
//        refreshLayout.autoRefresh(0);
//        loadListData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void loadListData() {
        getData();
    }

    private void getData() {
        ApiManager.getApi().getPushMessageList(RequestBodyUtils.getPushMessageList(LoginUtils.getInstance().getUserInfo()
                        .getAuthtoken()
                , kPage + "", kPageSize + ""))
                .compose(RxSchedulers.<MessageListBean>io_main())
                .subscribe(new BaseSubscriber<MessageListBean>() {
                    @Override
                    public void result(MessageListBean listBean) {
                        if(null != listBean && null != listBean.getDATA()) {
                            setListData(listBean.getDATA().getDATA());
                        } else {
                            setListData(null);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        finishLoad();
                    }
                });
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(int viewType) {
        View view = inflateContentView(R.layout.item_message_info);

        return new MessageVH(view);
    }

    @Override
    public void BindViewHolder(RecyclerView.ViewHolder viewHolder, int viewType, int position, Object data) {
        MessageVH holder = (MessageVH) viewHolder;
        MessageInfo info = (MessageInfo) data;

        String content = info.getCONTENT();
        try {
            JSONObject object = new JSONObject(content);
            JSONObject inform = object.getJSONObject("INFORM");
            final String recordid = inform.getString("RECORDID");
            String name = inform.getString("NAME");
            String statusmsg = inform.getString("STATUSMSG");

            holder.mTvContentTxt.setText(statusmsg);

            holder.mTvTime.setText(info.getPUSHTIME());
            if ("1".equals(info.getMSGTYPE())) {
                holder.mTvName.setText("实名认证");
                holder.mTvType.setText("认证");
                holder.mTvType.setBackgroundColor(getResources().getColor(R.color.take));
            } else if ("2".equals(info.getMSGTYPE())) {
                holder.mTvName.setText("项目办理");
                holder.mTvType.setText("办件");
                holder.mTvType.setBackgroundColor(getResources().getColor(R.color.title_bar_color));
            } else if ("3".equals(info.getMSGTYPE())) {
                holder.mTvName.setText("通知公告");
                holder.mTvType.setText("通知");
                holder.mTvType.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if ("1".equals(info.getSTATUS())) {
                holder.mTvContentTxt.setTextColor(getResources().getColor(R.color.gray_split_line_bg_group));
                holder.mTvName.setTextColor(getResources().getColor(R.color.gray_split_line_bg_group));
                holder.mTvTime.setTextColor(getResources().getColor(R.color.gray_split_line_bg_group));
            } else {
                holder.mTvContentTxt.setTextColor(getResources().getColor(R.color.actionsheet_gray));
                holder.mTvTime.setTextColor(getResources().getColor(R.color.actionsheet_gray));
                holder.mTvName.setTextColor(getResources().getColor(R.color.color_333333));
            }

            holder.convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MessageListActivity.this, MessageDetailActivity.class);
                    intent.putExtra("id", recordid);
                    startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class MessageVH extends RecycleviewViewHolder {
        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_content_txt)
        TextView mTvContentTxt;
        public MessageVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
