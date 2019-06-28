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
import com.powerrich.office.oa.activity.DoThingDetailActivity;
import com.powerrich.office.oa.activity.mine.YtSearchActivity;
import com.powerrich.office.oa.enums.SearchFragmentType;
import com.powerrich.office.oa.fragment.mine.base.BaseRefreshFragment;
import com.powerrich.office.oa.notify.NotifyKey;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.viewholder.ProcessViewHolder;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.ProcessListBean;
import com.yt.simpleframe.http.bean.entity.ProcessInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.notification.NotificationCenter;
import com.yt.simpleframe.notification.NotificationListener;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.Unbinder;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public class FragmentTypeFragment extends BaseRefreshFragment<ProcessInfo> implements NotificationListener {

    Unbinder unbinder;
    private SearchFragmentType type;
    boolean longboo = false;
    YtSearchActivity mActivity;
//    private LoadingDialog dialog;

    public static FragmentTypeFragment getInstance(SearchFragmentType type) {
        FragmentTypeFragment fragment = new FragmentTypeFragment();
        Bundle b = new Bundle();
        b.putSerializable("type", type);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        refreshLayout.autoRefresh(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (YtSearchActivity) context;
        type = (SearchFragmentType) getArguments().getSerializable("type");
        if (type == SearchFragmentType.暂存) {
            NotificationCenter.defaultCenter.addListener(NotifyKey.SELECT_ALL_KEY, this);
            NotificationCenter.defaultCenter.addListener(NotifyKey.UNSELECT_ALL_KEY, this);
            NotificationCenter.defaultCenter.addListener(NotifyKey.CANCEL_KEY, this);
            NotificationCenter.defaultCenter.addListener(NotifyKey.DELETE_KEY, this);
        }
        NotificationCenter.defaultCenter.addListener(NotifyKey.SEARCH_KEY, this);
        mHandler = new Handler();
//        dialog = new LoadingDialog(mActivity);
//        dialog.show();
    }

    @Override
    public RecyclerView.ViewHolder createVH(int viewType) {
        View view = inflateContentView(R.layout.item_process_fragment);
        return new ProcessViewHolder(unbinder, view, type);
    }

    @Override
    public void bindVH(RecyclerView.ViewHolder viewHolder, int viewType, final int position, final Object data) {
        final ProcessViewHolder holder = (ProcessViewHolder) viewHolder;
        holder.bindView(data);
        if (longboo) {
            holder.mCbProcess.setVisibility(View.VISIBLE);
        } else {
            holder.mCbProcess.setVisibility(View.GONE);
        }
        if (SearchFragmentType.暂存 == type) {
            holder.convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    NotificationCenter.defaultCenter.postNotification(NotifyKey.PROCESS_LONG_CLICK_KEY);
                    longboo = true;
                    _adapter.notifyDataSetChanged();
                    return false;
                }
            });


            holder.mCbProcess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean b = holder.mCbProcess.isChecked();
                    ArrayList<ProcessInfo> list = (ArrayList<ProcessInfo>) _adapter.getData();
                    ProcessInfo info = list.get(position);
                    info.checkBoo = b;
                    list.set(position, info);
                    NotificationCenter.defaultCenter.postNotification(NotifyKey.SELECT_KEY, list);
                }
            });

        }

        final ProcessInfo info = (ProcessInfo) data;

        if(type == SearchFragmentType.已完结){
            if ("8".equals(info.getPROCESS_STATE())) {
                holder.mTvPocessGo.setText("不予受理");
                holder.mTvPocessGo.setTextColor(getResources().getColor(R.color.red));
                holder.mTvPocessGo.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_corners_5_dp));
            }else{
                holder.mTvPocessGo.setVisibility(View.GONE);
            }
        }


        if (type == SearchFragmentType.已办理) {
            //待审核
            if ("1".equals(info.getPROCESS_STATE())) {
                holder.mTvPocessGo.setText("待审核");
                holder.mTvPocessGo.setTextColor(getResources().getColor(R.color.blue_main));
                holder.mTvPocessGo.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_corners_5_dp));
                //已受理
            } else if ("2".equals(info.getPROCESS_STATE())) {
                holder.mTvPocessGo.setText("已受理");
                holder.mTvPocessGo.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_corners_5_dp));
                holder.mTvPocessGo.setTextColor(getResources().getColor(R.color.green));
                //待补交件
            } else if ("5".equals(info.getPROCESS_STATE())) {
                holder.mTvPocessGo.setText("待补交件");
                holder.mTvPocessGo.setTextColor(getResources().getColor(R.color.red));
                holder.mTvPocessGo.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_corners_5_dp));
                //预审退回
            } else if ("7".equals(info.getPROCESS_STATE())) {
                holder.mTvPocessGo.setText("预审退回");
                holder.mTvPocessGo.setTextColor(getResources().getColor(R.color.red));
                holder.mTvPocessGo.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_corners_5_dp));
            } else {
                holder.mTvPocessGo.setVisibility(View.GONE);
            }
        }


        holder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //QueryHandingDetailActivity  proKeyId
                //zan_cun  暂存 true , 其他事false
                if(!longboo){
                    click(info);
                }else{
                    ArrayList<ProcessInfo> list = (ArrayList<ProcessInfo>) _adapter.getData();
                    ProcessInfo info = list.get(position);
                    info.checkBoo = !info.checkBoo;
                    list.set(position, info);
                    NotificationCenter.defaultCenter.postNotification(NotifyKey.SELECT_KEY, list);
                    _adapter.notifyDataSetChanged();
                }
            }
        });
//        if (type == SearchFragmentType.已完结) {
//            holder.mTvPocessGo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mActivity, EvaluationActivity.class);
//                    if (info.isSFPJ()) {
//                        //查看评价
//                        intent.putExtra("type", EvaluationType.查看评价);
//                    } else {
//                        //去评价
//                        intent.putExtra("type", EvaluationType.去评价);
//                    }
//                    intent.putExtra("id", info.getPROKEYID());
//                    startActivity(intent);
//                }
//            });
//
//
//        }
//        else if (type == SearchFragmentType.已办理) {
//            holder.mTvProcessGui.setVisibility(View.VISIBLE);
//            holder.mTvProcessGui.setText("查看进度");
//
//            holder.mTvProcessGui.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mActivity, DoThingQueryActivity.class);
//                    intent.putExtra("processCode", info.getTRACKINGNUMBER());
//                    startActivity(intent);
//                }
//            });
//
//
//            if ("1".equals(info.getEMS_MAILNUM())) {
//                holder.mTvPocessGo.setText("已下单");
//                holder.mTvPocessGo.setBackground(getResources().getDrawable(R.drawable.gray2_corners2_dp));
//                holder.mTvPocessGo.setEnabled(false);
//            } else {
//                holder.mTvPocessGo.setText("快递下单");
//                holder.mTvPocessGo.setBackground(getResources().getDrawable(R.drawable.blue2_corners_icon_dp));
//                holder.mTvPocessGo.setEnabled(true);
//                holder.mTvPocessGo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mActivity, MaterialSendActivity.class);
//                        intent.putExtra("itemName", info.getITEMNAME());
//                        intent.putExtra("proKeyId", info.getPROKEYID());
//                        intent.putExtra("trackId", info.getPROKEYID());
//                        startActivity(intent);
//                    }
//                });
//            }
//
//        }
    }

    public void click(ProcessInfo info) {
//        Intent intent = new Intent(mActivity, QueryHandingDetailActivity.class);
        Intent intent = new Intent(mActivity, DoThingDetailActivity.class);
        intent.putExtra("itemId", info.getSXID());
        intent.putExtra("proKeyId", info.getPROKEYID());
        intent.putExtra("isEvaluate", info.isSFPJ());
        if (type == SearchFragmentType.暂存) {
            intent.putExtra("zan_cun", true);
        } else {
            intent.putExtra("zan_cun", false);
        }
        startActivity(intent);
    }

    @Override
    public void loadListData() {
        getDate("");
    }

    private Handler mHandler;

    public void getDate(final String searchName) {

        getProcessList(searchName);



    }

    public void getProcessList(final String searchName) {
        ApiManager.getApi().getProcessList(RequestBodyUtils.queryProcesslist(
                LoginUtils.getInstance().getUserInfo().getAuthtoken(), type.getCode() + "", searchName, kPage, kPageSize))
                .compose(RxSchedulers.<ProcessListBean>io_main())
                .subscribe(new BaseSubscriber<ProcessListBean>() {
                    @Override
                    public void result(ProcessListBean listBean) {
//                        ArrayList<ProcessInfo> data = listBean.getDATA().getDATA();
                        if(kPage == 1){
                            hideDeleBt();
                        }
                        ArrayList<ProcessInfo> data = listBean.getDATA();
                        if (data != null) {
                            if (!TextUtils.isEmpty(searchName)) {
                                _adapter.clearDatas();
                            }
                            setListData(data);
                        } else {
                            setListData(new ArrayList<ProcessInfo>());
                        }

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
        boolean b = false;
        if (notification.key.equals(NotifyKey.SELECT_ALL_KEY)) {
            b = true;
        } else if (notification.key.equals(NotifyKey.UNSELECT_ALL_KEY)) {
            b = false;
        } else if (notification.key.equals(NotifyKey.SEARCH_KEY)) {
            String searchName = (String) notification.object;
            _adapter.getData().clear();
            if (!TextUtils.isEmpty(searchName)) {
                getDate(searchName);
//                if (dialog != null)
//                    dialog.show();
            } else {
                refreshLayout.autoRefresh(0);
            }

            return false;
        } else if (notification.key.equals(NotifyKey.CANCEL_KEY)) {
            longboo = false;
            _adapter.notifyDataSetChanged();
            return false;
        } else if (notification.key.equals(NotifyKey.DELETE_KEY)) {
            ArrayList<ProcessInfo> list = (ArrayList<ProcessInfo>) _adapter.getData();
            JSONArray ja = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                ProcessInfo info = list.get(i);
                if (info.checkBoo) {
                    ja.put(info.getPROKEYID());
                }
            }
            dele(ja.toString());
            longboo = false;
            NotificationCenter.defaultCenter.postNotification(NotifyKey.HIDE_KEY);

            return false;
        }
        ArrayList<ProcessInfo> list = null;
        list = (ArrayList<ProcessInfo>) _adapter.getData();
        if (SearchFragmentType.暂存 == type) {
            for (int i = 0; i < list.size(); i++) {
                ProcessInfo info = list.get(i);
                info.checkBoo = b;
                list.set(i, info);
            }
            _adapter.notifyDataSetChanged();
            NotificationCenter.defaultCenter.postNotification(NotifyKey.SELECT_KEY, list);


        }
        return false;
    }

    void hideDeleBt(){
        longboo = false;
        NotificationCenter.defaultCenter.postNotification(NotifyKey.HIDE_KEY);
    }


    //批量删除办件
    public void dele(String jsonArray) {
        ApiManager.getApi().exeNormal(RequestBodyUtils.deleteProcess(LoginUtils.getInstance().getUserInfo()
                .getAuthtoken(), jsonArray))
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean baseBean) {
                        refreshLayout.autoRefresh(0);
                    }
                });
    }
}
