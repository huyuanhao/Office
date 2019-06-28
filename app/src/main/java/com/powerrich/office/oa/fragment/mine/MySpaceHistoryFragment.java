package com.powerrich.office.oa.fragment.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.SIMeID.SIMeIDApplication;
import com.powerrich.office.oa.activity.Interaction.OnMultiClickListener;
import com.powerrich.office.oa.activity.MySpaceActivity;
import com.powerrich.office.oa.activity.mine.CertificateImageActivity;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.base.InvokeHelper;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.fragment.mine.base.BaseRefreshFragment;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.entity.CertificateInfo;
import com.yt.simpleframe.http.bean.xmlentity.CertificateListBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.view.recyclerview.viewholder.RecycleviewViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 历史附件与所有证件 公用的Framgnet
 *
 * @author Administrator
 * @date 2018/10/18 16:25
 */
public class MySpaceHistoryFragment extends BaseRefreshFragment<CertificateInfo> {

    private MySpaceActivity mActivity;
    private static final int GET_ALL_FILE_LIST_BY_USER_CODE = 0;
    public int type;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MySpaceActivity) {
            this.mActivity = (MySpaceActivity) context;
        }
        type = getArguments().getInt("type");
        invoke = new InvokeHelper(mActivity);
    }

    public static MySpaceHistoryFragment getInstance(int type) {
        MySpaceHistoryFragment fragment = new MySpaceHistoryFragment();
        Bundle b = new Bundle();
        b.putInt("type", type);
        fragment.setArguments(b);
        return fragment;
    }

    public void autoRefresh(String searName){
        this.searchName = searName;
        refreshLayout.autoRefresh();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        autoRefresh("");
//        refreshLayout.autoRefresh(0);
    }


    @Override
    public RecyclerView.ViewHolder createVH(int viewType) {
        View view = inflateContentView(R.layout.space_item);
        return new SpaceHistoryHolder(view);
    }

    @Override
    public void bindVH(RecyclerView.ViewHolder viewHolder, int viewType, int position, Object object) {
        final SpaceHistoryHolder holder = (SpaceHistoryHolder) viewHolder;
        holder.bindView(object);
         final CertificateInfo certificateInfo= (CertificateInfo) object;

        viewHolder.itemView.setOnClickListener(new OnMultiClickListener() {
            @Override
            protected void onMultiClick(View v) {
//                        String url = "http://218.87.176.156:80/platform/DownFileServlet?" + "type=1" + "&DOWNPATH=" + certificateInfo.getCARD_FILE() +
//                "&HDFSFILENAME=" + certificateInfo.getHDFSFILENAME() + "&FILENAME=" + certificateInfo.getFILENAME();
//                Log.i("jsc", "onMultiClick: "+url);

                String filePath = "";
                String hdfsfilename = "";
                String fileName = "";

                if(type==1){
                    filePath =certificateInfo.getPATH();
                    hdfsfilename =certificateInfo.getHDFSFILENAME();
                    fileName =certificateInfo.getNAME();
                }else{
                    filePath =certificateInfo.getCARD_FILE();
                    hdfsfilename =certificateInfo.getHDFSFILENAME();
                    fileName =certificateInfo.getFILENAME();
                }




                if(fileName.contains(".")){
                    Intent intent = new Intent(getActivity(), CertificateImageActivity.class);
                    intent.putExtra("CARD_FILE", filePath);
                    intent.putExtra("HDFSFILENAME", hdfsfilename);
                    intent.putExtra("FILENAME", fileName);
                    startActivity(intent);
                }else{
                    String url = "http://218.87.176.156:80/platform/DownFileServlet?type=1&DOWNPATH=" + filePath +
                            "&HDFSFILENAME=" + hdfsfilename + "&FILENAME=" + fileName;
                    Log.i("jsc", "onMultiClick: "+url);
                    DialogUtils.showToast(getActivity(), "文件格式有误。");
                }


            }
        });

    }

    @Override
    public void loadListData() {
        Log.i("jsc", "loadListData: "+type);
        if (type == 1) {
            getDate();
        } else {
            getAllData();
        }



    }

    private InvokeHelper invoke;
    private String searchName;
    /**
     * 历史附件
     *
     */
    public void getDate() {

        //   getProcessList(searchName);

//        ApiRequest request = OAInterface.getAllFileListByUser(searchName, String.valueOf(kPage));
//        invoke.invokeWidthDialog(request, callBack, GET_ALL_FILE_LIST_BY_USER_CODE);

        ApiManager.getApi().getCertificateList(RequestBodyUtils.getHistoryCertificateList(LoginUtils.getInstance().getUserInfo()
                .getAuthtoken(), searchName, kPage + "", +kPageSize + ""))
                .compose(RxSchedulers.<CertificateListBean>io_main())
                .subscribe(new BaseSubscriber<CertificateListBean>(refreshLayout) {
                    @Override
                    public void result(CertificateListBean baseBean) {
                        if (baseBean.getDATA() != null&&baseBean.getDATA().getDATA()!=null) {
                            Log.i("jsc", "result: ");
                            setListData(baseBean.getDATA().getDATA());
                        } else {
                            _adapter.getData().clear();
                            Log.i("jsc", "result:null ");
                            setListData(null);
                        }
                    }
                });


    }

    /**
     * 所有证照
     *
     * @param searchName
     */
    public void getAlllicenseData(final String searchName) {


        ApiRequest request = OAInterface.getAllFileListByUser("", String.valueOf(kPage));
        invoke.invokeWidthDialog(request, callBack, GET_ALL_FILE_LIST_BY_USER_CODE);

    }

    /**
     * 获取当前用户所有的的电子证照信息
     */
    private void getAllData() {
        ApiManager.getApi().getCertificateList(RequestBodyUtils.getCertificateList(LoginUtils.getInstance().getUserInfo()
                .getAuthtoken(), searchName, kPage + "", +kPageSize + ""))
                .compose(RxSchedulers.<CertificateListBean>io_main())
                .subscribe(new BaseSubscriber<CertificateListBean>(refreshLayout) {
                    @Override
                    public void result(CertificateListBean baseBean) {
                        if (baseBean.getDATA() != null&&baseBean.getDATA().getDATA()!=null) {
                            setListData(baseBean.getDATA().getDATA());
                        } else {
                            _adapter.getData().clear();
                            setListData(null);
                        }
                    }
                });
    }


    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {

            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh();
            } else {
                refreshLayout.finishLoadmore();
            }

            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == GET_ALL_FILE_LIST_BY_USER_CODE) {
                    ArrayList<CertificateInfo> materialsDepotBeanList = SIMeIDApplication.getmGson().fromJson(item.getDataDataStr(), new TypeToken<List<CertificateInfo>>() {
                    }.getType());
                    Log.i("jsc", "process: " + materialsDepotBeanList.toString());


                    setListData(materialsDepotBeanList);


                    //   ResultItem result = (ResultItem) item.get("DATA");
//                    if (currentPage == 1 && !BeanUtils.isEmpty(beanList)) {
//                        beanList.clear();
//                        currentRows = 0;
//                    }
//                    currentRows += result.getInt("CURRENTROWS");
//                    if (currentRows == result.getInt("ROWS")) {
//                        refresh_layout.setLoadmoreFinished(true);
//                    } else {
//                        refresh_layout.setLoadmoreFinished(false);
//                    }
//                    List<ResultItem> items = result.getItems("DATA");
//                    parseData(items);
                }
            } else {
                DialogUtils.showToast(getActivity(), message);
            }
        }
    };


    class SpaceHistoryHolder extends RecycleviewViewHolder {
        @BindView(R.id.tv_zjmc_value)
        TextView tvZjmcValue;
        @BindView(R.id.tv_hjmc_value)
        TextView tvHjmcValue;
        @BindView(R.id.tv_scsj_value)
        TextView tvScsjValue;

        @BindView(R.id.tv_zjmc_name)
        TextView tvZjmcName;
        @BindView(R.id.tv_scsj_name)
        TextView tvScsjName;

        private CertificateInfo certificateInfo;

        SpaceHistoryHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(Object object) {
            certificateInfo = (CertificateInfo) object;
            if (type == 1) {
                tvZjmcValue.setText(certificateInfo.getNAME());
                tvHjmcValue.setText(certificateInfo.getCOMP_FILE_NAME());
                tvScsjValue.setText(certificateInfo.getCREATETIME());
            } else {
                tvZjmcName.setText("证照名称：");
                tvScsjName.setText("证照等级：");
                tvZjmcValue.setText(certificateInfo.getCARD_NAME());
                tvHjmcValue.setText(certificateInfo.getCOMP_FILE_NAME());
                tvScsjValue.setText(canvertZZDJ(certificateInfo.getZZDJ()));
            }

        }

        private String canvertZZDJ(String content){
            String str = "- -";
            switch (content){
                case "A":
                    str="本系统颁发";
                    break;
                case "B":
                    str="本系统上传经过审核";
                    break;
                case "C":
                    str="本系统上传未审核";
                    break;
            }
            return str;

        }

    }



}
