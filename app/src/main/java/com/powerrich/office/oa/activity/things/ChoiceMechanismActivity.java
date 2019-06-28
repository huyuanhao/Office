package com.powerrich.office.oa.activity.things;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.MechanismSelectAdapter;
import com.powerrich.office.oa.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择缴费机构
 * huyuanhao
 */
public class ChoiceMechanismActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.listView)
    ListView listView;
    String title = "";
    String type = "0";
    MechanismSelectAdapter adapter;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_oil;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra("type");
        switch (type){
            case "0":
                title = "水费";
                break;
            case "1":
                title = "电费";
                break;
            case "2":
                title = "燃气费";
                break;
            case "3":
                title = "加油";
                break;
            case "4":
                title = "电视费";
                break;
            case "5":
                title = "宽带费";
                break;
        }
        barTitle.setText(title);

        setAdatper();
    }

    public void setAdatper(){
        final List<String> list =new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(title + "机构" + i);
        }
        adapter = new MechanismSelectAdapter(this,list,R.layout.item_text_mechanism);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(ChoiceMechanismActivity.this,PowerRateActivity.class)
                        .putExtra("type",type)
                .putExtra("name",list.get(i)));
            }
        });
    }

    @OnClick({R.id.bar_back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
        }
    }
}
