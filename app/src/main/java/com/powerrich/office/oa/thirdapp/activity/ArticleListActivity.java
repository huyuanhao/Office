package com.powerrich.office.oa.thirdapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.powerrich.office.oa.activity.mine.base.BaseNavPagerActivity;
import com.powerrich.office.oa.thirdapp.fragment.ArticleListFragment;
import com.powerrich.office.oa.thirdapp.http.ThirdApiManager;
import com.powerrich.office.oa.thirdapp.http.ThirdBaseSubscriber;
import com.powerrich.office.oa.thirdapp.http.bean.GetArticleBean;
import com.yt.simpleframe.http.RxSchedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/23
 * 版权：
 */

public class ArticleListActivity extends BaseNavPagerActivity{

    private String[] titles = null;
    private ArrayList<Fragment> listFragment;


    private String title;
    private int nid;
    private int pidx;
    private String typeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nid = getIntent().getExtras().getInt("nid");
        pidx = getIntent().getExtras().getInt("pidx");
        typeid = getIntent().getExtras().getString("typeid");
        title = getIntent().getExtras().getString("title");
        setTitle(title);
        getData();
    }

    @Override
    protected String[] getTitles() {
        return titles;
    }

    @Override
    protected ArrayList<Fragment> fragmentClasses() {
        return listFragment;
    }

    //http://cmswebv3.aheading.com/api/Article/ClassifyDetail?Nid=8673&Pidx=31085&Token=&TypeId=17
//    Nid=8673&Pidx=36453&Token=&TypeId=16
//    http://cmswebv3.aheading.com/api/Article/List?ClassifyIdx=36454&ClassifyType=16&IsFound=0&NewspaperIdx=8673&PageIndex=1&PageSize=15&Type=0

    private void getData(){
        ThirdApiManager.getApi().getArticle(Integer.valueOf(nid),Integer.valueOf(pidx),"",Integer.valueOf(typeid))
                .compose(RxSchedulers.<GetArticleBean>io_main())
                .compose(this.<GetArticleBean>loadingDialog())
                .subscribe(new ThirdBaseSubscriber<GetArticleBean>() {
                    @Override
                    public void result(GetArticleBean getArticleBean) {
                        List<GetArticleBean.ChildClassifysBean> list = getArticleBean.getChildClassifys();
                        if(list.size() == 0){
                            showNodata();
                            return;
                        }

                        titles = new String[list.size()];
                        listFragment = new ArrayList<>(list.size());
                        for (int i = 0; i< list.size();i++){
                            titles[i] = list.get(i).getName();
                            list.get(i).nid = Integer.valueOf(nid);
                            listFragment.add(ArticleListFragment.getInstance(list.get(i)));
                        }
                        setPageAdapter(getTitles());
                    }
                });
    }


}
