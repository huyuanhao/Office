package com.powerrich.office.oa.fragment.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.AdvisoryDetailActivity;
import com.powerrich.office.oa.enums.AdvisoryType;
import com.powerrich.office.oa.fragment.mine.base.BaseRefreshFragment;
import com.powerrich.office.oa.notify.NotifyKey;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.viewholder.AdvisoryViewHolder;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.AdvisoryListBean;
import com.yt.simpleframe.http.bean.entity.AdvisoryInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.notification.NotificationCenter;
import com.yt.simpleframe.notification.NotificationListener;

import java.util.ArrayList;

import butterknife.Unbinder;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public class AdvisoryTypeFragment extends BaseRefreshFragment<AdvisoryInfo> implements NotificationListener {

    Unbinder unbinder;
    private AdvisoryType type;
    private Handler mHandler;
//    private LoadingDialog dialog;

    public static AdvisoryTypeFragment getInstance(AdvisoryType type) {
        AdvisoryTypeFragment fragment = new AdvisoryTypeFragment();
        Bundle b = new Bundle();
        b.putSerializable("type", type);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type = (AdvisoryType) getArguments().getSerializable("type");
        mHandler = new Handler();
        NotificationCenter.defaultCenter.addListener(NotifyKey.SEARCH_KEY, this);
//        dialog = new LoadingDialog(context);
//        dialog.show();
    }

    @Override
    public RecyclerView.ViewHolder createVH(int viewType) {
        View view = inflateContentView(R.layout.item_process_fragment);
        return new AdvisoryViewHolder(unbinder, view, type);
    }

    @Override
    public void bindVH(RecyclerView.ViewHolder viewHolder, int viewType, final int position,final Object data) {
        final AdvisoryViewHolder holder = (AdvisoryViewHolder) viewHolder;
        holder.bindView(data);
        holder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdvisoryDetailActivity.class);
                intent.putExtra("id",((AdvisoryInfo)data).getJL_ID());
                intent.putExtra("type",type.getCode()+"");
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadListData() {
        getDate("");
    }

    public void getDate(final String searchName) {

        getExchageList(searchName);

//        long time = 100;
//        if (type == AdvisoryType.咨询) {
//        } else if (type == AdvisoryType.建议) {
//            time = 400;
//        } else if (type == AdvisoryType.投诉) {
//            time = 800;
//        }
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getExchageList(searchName);
//            }
//        }, time);
    }

    public void getExchageList(final String searchName) {
        ApiManager.getApi().getExchangeList(RequestBodyUtils.getExchangeList(
                LoginUtils.getInstance().getUserInfo().getAuthtoken(), type.getCode() + "", searchName, kPage, kPageSize))
                .compose(RxSchedulers.<AdvisoryListBean>io_main())
                .subscribe(new BaseSubscriber<AdvisoryListBean>() {
                    @Override
                    public void result(AdvisoryListBean listBean) {
                        ArrayList<AdvisoryInfo> data = listBean.getDATA().getDATA();
                        if (!TextUtils.isEmpty(searchName)) {
                            _adapter.clearDatas();
                        }
                        setListData(listBean.getDATA().getDATA());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        finishLoad();
//                        if (dialog != null)
//                            dialog.dismiss();
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NotificationCenter.defaultCenter.removeListener(this);
        if (unbinder != null)
            unbinder.unbind();
    }


    @Override
    public boolean onNotification(Notification notification) {
        if (notification.key.equals(NotifyKey.SEARCH_KEY)) {
            String searchName = (String) notification.object;
            if (!TextUtils.isEmpty(searchName)) {
                getDate(searchName);
//                if (dialog != null)
//                    dialog.show();
            } else {
                refreshLayout.autoRefresh(0);
            }
            return false;
        }
        return false;
    }
}
