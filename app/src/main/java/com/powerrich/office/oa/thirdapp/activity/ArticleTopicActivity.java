package com.powerrich.office.oa.thirdapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.BaseRefreshActivity;
import com.powerrich.office.oa.thirdapp.http.ThirdApiManager;
import com.powerrich.office.oa.thirdapp.http.ThirdBaseSubscriber;
import com.powerrich.office.oa.thirdapp.http.bean.ArticleTopicBean;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.ImageLoad;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.utils.StringUtil;
import com.yt.simpleframe.view.recyclerview.viewholder.RecycleviewViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/25
 * 版权：
 */

public class ArticleTopicActivity extends BaseRefreshActivity {


    private int nid;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nid = getIntent().getExtras().getInt("nid");
        id = getIntent().getExtras().getInt("id");
        onRefresh();
    }


    @Override
    public void loadListData() {
        getData();
    }

    //http://cmswebv3.aheading.com/api/Article/SubjectList?NewspaperIdx=8673&PageIndex=1&PageSize=15&SubjectId=1886127
    private void getData() {
        ThirdApiManager.getApi().getArticleTopic(nid,kPage,kPageSize,id)
                .compose(RxSchedulers.<ArticleTopicBean>io_main())
                .compose(this.<ArticleTopicBean>loadingDialog())
                .subscribe(new ThirdBaseSubscriber<ArticleTopicBean>() {
                    @Override
                    public void result(ArticleTopicBean bean) {
                        setTitle(bean.getSubjectArticle().getTitle());
                        setListData(bean.getArticleList());
                    }
                });
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(int viewType) {
        View view = inflateContentView(R.layout.item_article_dp);
        return new ArticleTopVH(view);
    }

    @Override
    public void BindViewHolder(RecyclerView.ViewHolder viewHolder, int viewType, int position, Object data) {
        final ArticleTopicBean.ArticleListBean info = (ArticleTopicBean.ArticleListBean) data;
        ArticleTopVH vh = (ArticleTopVH) viewHolder;
        if(StringUtil.isEmpty(info.getImgSrc())){
            vh.mIvImg.setVisibility(View.GONE);
        }else{
            vh.mIvImg.setVisibility(View.VISIBLE);
            ImageLoad.setUrl(this,ThirdApiManager.ULR+info.getImgSrc(),vh.mIvImg);
        }
        vh.mTvTitle.setText(info.getTitle());
        vh.mTvTime.setText(DateUtils.UtcToStr(info.getLiveStartTime()));
        vh.mTvArticle.setVisibility(View.GONE);
        vh.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ArticleInfoActivity.class);
                intent.putExtra("nid",nid);
                intent.putExtra("id",info.getId());
                startActivity(intent);
            }
        });
    }

    class ArticleTopVH extends RecycleviewViewHolder {
        @BindView(R.id.iv_img)
        ImageView mIvImg;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_article)
        TextView mTvArticle;

        public ArticleTopVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
