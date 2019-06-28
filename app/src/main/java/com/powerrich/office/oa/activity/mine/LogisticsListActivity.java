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
import com.yt.simpleframe.http.bean.LogisticsListBean;
import com.yt.simpleframe.http.bean.entity.LogisticsInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.view.recyclerview.viewholder.RecycleviewViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/12/012
 * 版权：
 */
public class LogisticsListActivity extends BaseRefreshActivity<LogisticsInfo> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("物流记录");
        showBack();
        getDate("");
//        refreshLayout.autoRefresh(0);
    }


    public void getDate(final String searchName) {
        ApiManager.getApi().getExpressList(RequestBodyUtils.getExpressList(
                LoginUtils.getInstance().getUserInfo().getAuthtoken(), "", searchName, kPage, kPageSize))
                .compose(RxSchedulers.<LogisticsListBean>io_main())
                .subscribe(new BaseSubscriber<LogisticsListBean>(refreshLayout) {
                    @Override
                    public void result(LogisticsListBean listBean) {
                        ArrayList<LogisticsInfo> data = listBean.getDATA().getDATA();
                        if (listBean.getDATA() != null) {
                            setListData(listBean.getDATA().getDATA());
                        }

                    }
                });
    }

    @Override
    public void loadListData() {
        getDate("");
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(int viewType) {
        View view = inflateContentView(R.layout.item_mine_logistics);
        return new LogisticsVH(view);
    }


    @Override
    public void BindViewHolder(RecyclerView.ViewHolder viewHolder, int viewType, int position, Object data) {
        LogisticsVH vh = (LogisticsVH) viewHolder;
        final LogisticsInfo info = (LogisticsInfo) data;
        vh.mTvSendLeft.setText(info.getSEND_NAME());
        vh.mTvSendRight.setText(info.getCONSIGNEE_NAME());
        vh.mTvSendNumber.setText("运单号：" + info.getORDER_NUM());
        vh.mTvTime.setText(info.getEMIT_TIME());
        String status = info.getORDER_STATUS();
        //0已下单，1物流中，2已签收
        String statusStr = "";
        int color = R.color.black;
        if ("0".equals(status)) {
            statusStr = "已下单";
            color = R.color.blue_main;
        } else if ("1".equals(status)) {
            statusStr = "物流中";
            color = R.color.app_button_color;
        } else if ("2".equals(status)) {
            statusStr = "已签收";
            color = R.color.main_select_color;
        }
        vh.mTvStyle.setText(statusStr);
        vh.mTvStyle.setTextColor(getResources().getColor(color));

        vh.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LogisticsDetailActivity.class);
                intent.putExtra("id",info.getORDER_NUM());
                startActivity(intent);
            }
        });

    }

    class LogisticsVH extends RecycleviewViewHolder {
        @BindView(R.id.tv_send_left)
        TextView mTvSendLeft;
        @BindView(R.id.tv_send_right)
        TextView mTvSendRight;
        @BindView(R.id.tv_send_number)
        TextView mTvSendNumber;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_style)
        TextView mTvStyle;

        public LogisticsVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
