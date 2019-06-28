package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.BaseRefreshActivity;
import com.powerrich.office.oa.enums.CertificateType;
import com.powerrich.office.oa.tools.BeanUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11
 * 版权：
 */

public class CertificateListActivity extends BaseRefreshActivity<CertificateInfo> {


    public CertificateType type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (CertificateType) getIntent().getExtras().get("type");
        if (type == CertificateType.查看) {
            setTitle("电子证照");
        } else if (type == CertificateType.选择) {
            setTitle("历史证照", "确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<CertificateInfo> list = (ArrayList<CertificateInfo>) _adapter.getData();
                    ArrayList<CertificateInfo> sendList = new ArrayList<>();
                    if (!BeanUtils.isEmpty(list)) {
                        for (CertificateInfo info : list) {
                            if (info.checkBoo) {
                                sendList.add(info);
                            }
                        }
                        //这里处理将sendList数据返回
                        if (BeanUtils.isEmpty(sendList)) {
                            DialogUtils.showToast(CertificateListActivity.this, "请选择电子证照！");
                            return;
                        }
                    }
                    Intent intent = new Intent();
                    intent.putExtra("sendList", sendList);
                    setResult(RESULT_OK, intent);
                    CertificateListActivity.this.finish();
                }


            });
        }

        onRefresh();
    }


    private void getData() {
        ApiManager.getApi().getCertificateList(RequestBodyUtils.getCertificateList(LoginUtils.getInstance().getUserInfo()
                .getAuthtoken(), "", kPage + "", +kPageSize + ""))
                .compose(RxSchedulers.<CertificateListBean>io_main())
                .subscribe(new BaseSubscriber<CertificateListBean>(refreshLayout) {
                    @Override
                    public void result(CertificateListBean baseBean) {
                        if (baseBean.getDATA() != null) {
                            setListData(baseBean.getDATA().getDATA());
                        } else {
                            setListData(null);
                        }
                    }
                });
    }

    @Override
    public void loadListData() {
        getData();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(int viewType) {
        View view = inflateContentView(R.layout.item_certificate);
        return new CardVH(view);
    }

    @Override
    public void BindViewHolder(RecyclerView.ViewHolder viewHolder,final int position, final int viewType, Object data) {
        final CardVH vh = (CardVH) viewHolder;
        final CertificateInfo info = (CertificateInfo) data;
        vh.mTvName.setText(info.getZZLYS());
        vh.mTvCardType.setText(info.getCOMP_FILE_NAME());
        vh.mTvTime.setText("有效期至:" + info.getENDTIME());

        //如果>4位 小于8位  前四位数字后面全部*    如果>8位   前后4位保留，中间全部*
//        String id = info.getID();
//        if(id.length() < 4){
//            id = "****";
//        }else if(id.length() >= 4 && id.length() <=8){
//            id = id.substring(4)+"****";
//        }else if(id.length() > 8){
//            id = id.replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "*");
//        }
        String id = info.getCARD_NO();
        if (id.length() > 0) {
            if (id.length() < 4) {
                id = "****";
            } else if (id.length() >= 4 && id.length() <= 8) {
                id = id.substring(4) + "****";
            } else if (id.length() > 8) {
                id = id.replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "*");
            }
        }
        vh.mTvNumId.setText(id);


        if(type == CertificateType.选择){
            vh.mCbCertificate.setChecked(info.checkBoo);
            vh.mCbCertificate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean b = vh.mCbCertificate.isChecked();
                    ArrayList<CertificateInfo> list = (ArrayList<CertificateInfo>) _adapter.getData();
                    CertificateInfo info = list.get(position);
                    info.checkBoo = b;
                    list.set(position, info);
                }
            });
        }else if(type == CertificateType.查看){
            vh.mCbCertificate.setVisibility(View.GONE);
        }
        vh.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == CertificateType.查看) {
                    Intent intent = new Intent(v.getContext(), CertificateInfoActivity.class);
                    intent.putExtra("data", info);
                    startActivity(intent);
                }else if(type == CertificateType.选择){
                    ArrayList<CertificateInfo> list = (ArrayList<CertificateInfo>) _adapter.getData();
                    CertificateInfo info = list.get(position);
                    info.checkBoo = !info.checkBoo;
                    list.set(position, info);
                    _adapter.notifyDataSetChanged();
                }
            }
        });



//        String filePath = info.getCARD_FILE();
//        final String hdfsFileName = info.getHDFSFILENAME();
//        String fileName = info.getFILENAME();
//        String url = "http://218.87.176.156:80/platform/DownFileServlet?" + "type=1" + "&DOWNPATH=" + filePath +
//                "&HDFSFILENAME=" + hdfsFileName + "&FILENAME=" + "bb.jpg";
//        ImageLoad.setUrl(this, url, vh.mIvImg, R.drawable.pic_mine_head);

    }

    class CardVH extends RecycleviewViewHolder {

        @BindView(R.id.iv_img)
        ImageView mIvImg;
        @BindView(R.id.tv_card_type)
        TextView mTvCardType;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_num_id)
        TextView mTvNumId;
        @BindView(R.id.cb_certificate)
        CheckBox mCbCertificate;

        public CardVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
