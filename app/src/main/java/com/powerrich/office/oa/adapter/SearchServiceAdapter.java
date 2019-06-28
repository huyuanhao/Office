package com.powerrich.office.oa.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.AccumulationFundActivity;
import com.powerrich.office.oa.activity.InsuranceReceiveActivity;
import com.powerrich.office.oa.activity.home.service.SocialInquiryActivity;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.SearchBean;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.PermissionPageUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.tools.VerificationUtils;
import com.powerrich.office.oa.view.MyDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.functions.Consumer;

public class SearchServiceAdapter extends RecyclerView.Adapter<SearchServiceAdapter.ServiceViewHolder> {
    private List<SearchBean> arrayList;
    private Context context;

    @Override
    public ServiceViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_service_item, parent, false);
        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        final SearchBean searchBean = arrayList.get(position);
        holder.tv_service.setText(searchBean.getItemName());

        holder.iv_picture.setImageResource(searchBean.getItemImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBean.getItemName().equals("社保查询")){
                    if (VerificationUtils.all(context)) {
                        String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                        if ("1".equals(type)) {
                            ToastUtils.showMessage(context, "亲，该功能需要个人账号才能使用哦！");
                            return;
                        } else {
                            context.startActivity(new Intent(context, SocialInquiryActivity.class));
                        }

                    }
                }else if(searchBean.getItemName().equals("公积金查询")){
                    if (VerificationUtils.all(context)) {
                        String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                        if ("1".equals(type)) {
                            ToastUtils.showMessage(context, "亲，该功能需要个人账号才能使用哦！");
                            return;
                        } else {
                            context.startActivity(new Intent(context, AccumulationFundActivity.class));
                        }
                    }
                }else if(searchBean.getItemName().equals("社保年检")){
                    if (VerificationUtils.all(context)) {
                        doPermissionRW("存储", new BaseActivity.PermissionCallBack() {
                            @Override
                            public void accept() {
                                String type = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
                                if ("1".equals(type)) {
                                    ToastUtils.showMessage(context, "亲，该功能需要个人账号才能使用哦！");
                                    return;
                                } else {
                                    context.startActivity(new Intent(context, InsuranceReceiveActivity.class));
                                }
                            }
                        });
                    }

                }else if(searchBean.getItemName().equals("")){

                }else{
                    context.startActivity(searchBean.getIntent());
                }


            }
        });

    }



    public void setData( List<SearchBean> arrayList){
          this.arrayList = arrayList;
          notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return arrayList!=null?arrayList.size():0;
    }


    class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView tv_service;
        ImageView iv_picture;


        public ServiceViewHolder(View itemView) {
            super(itemView);

            tv_service = (TextView) itemView.findViewById(R.id.tv_service);
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
        }

    }

    private MyDialog dialog;
    private void doPermissionRW(final String msg, final BaseActivity.PermissionCallBack callBack) {
        RxPermissions rxPermissions = new RxPermissions((Activity) context);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            callBack.accept();
                        } else {
                            showTipDialog("没有 " + msg + " 权限，请打开相关权限");
                        }
                    }
                });
    }

    /**
     * 提示开启权限
     */
    public void showTipDialog(String message) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new MyDialog(context).builder()
                .setTitle("权限通知")
                .setMessage(message)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        PermissionPageUtils.getInstance(context).jumpPermissionPage();
                    }
                });
        dialog.show();
    }
}
