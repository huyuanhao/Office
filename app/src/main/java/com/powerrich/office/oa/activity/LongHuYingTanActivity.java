package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.ServiceListAdapter;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by hasee on 2018/7/20.
 */

public class LongHuYingTanActivity extends BaseActivity {
    @BindView(R.id.system_back)
    ImageView systemBack;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_longhu_yingtan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        tvTopTitle.setText("山水道都，世界铜都");
        systemBack.setVisibility(View.VISIBLE);
    }

    private void initData() {
        List<ItemBean> data = new ArrayList<>();
        data.add(new ItemBean(R.drawable.lishi, "鹰潭-历史", "西周以前鹰潭属扬州之域，春秋为百越之地，战国先属越后属楚。始皇二十六年（前221年），属九江郡。"));
        data.add(new ItemBean(R.drawable.renkou, "鹰潭-人口", "2017年末常住人口116.75万人，比上年末增加0.82万人，其中城镇人口为68.98万人，比上年末增加2.35万人，占总人口比重59.08%，比上年提高1.6个百分点。全年出生人口为1.59万人，出生率为13.67‰，比上年提高0.39个千分点；死亡人口为0.71万人，死亡率为6.1‰，比上年下降0.08个千分点；人口自然增长率为7.57‰，比上年提高0.47个千分点。"));
        data.add(new ItemBean(R.drawable.dili, "鹰潭-面积", "鹰潭市位于江西省东北部，信江中下游。地处北纬27°35ˊ～28°41ˊ、东经116°41ˊ～117°30ˊ，全市总面积3556.7平方千米，占江西省总面积的2.15%。"));
        data.add(new ItemBean(R.drawable.tong, "鹰潭-铜产业", "我市共有铜加工企业130余家。2015年，全市规模以上铜企业实现工业增加值261.3亿元，主营业务收入3100亿元，利税总额106亿元，分别占全市规模工业的73%、91.2%和66%"));
        data.add(new ItemBean(R.drawable.longhushan, "鹰潭-龙虎山", "龙虎山，位于江西省鹰潭市西南20公里处。是中国道教的发祥地，是中国古典名著《水浒传》开篇描绘的名山，被誉为“华夏道都”。它集国家级重点风景名胜区、国家4A级旅游区、国家森林公园、国家地质公园、国家重点文物保护单位、国家重点保护宫观和国家农业旅游示范点等众多品牌于一身，被世人称为“洞天福地、人间仙境”。《水浒传》开篇重笔描绘的龙虎山，位于鹰潭市境内，现为世界自然遗产和世界地质公园双冠景区。龙虎山风景区以其道教祖庭、丹霞地貌和春秋战国大型崖墓群珠壁联合为特色，在世界级风景名胜区中独树一帜。"));
        ServiceListAdapter adapter = new ServiceListAdapter(R.layout.first_list_item, data);
        adapter.openLoadAnimation();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.system_back)
    public void onViewClicked() {
        finish();
    }
}
