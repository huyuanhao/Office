package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.TestData;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.tools.BeanUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/9.
 */

public class MyQueryActivity extends BaseActivity implements View.OnClickListener {

    private ListView lv_queryhanding;
    private ArrayList<TestData> list = new ArrayList<>();
    private ArrayList<TestData> searchList = new ArrayList<>();
    private CommonAdapter mAdapter;
    private EditText searchEdt;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            searchList.addAll(list);
            updateList();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_queryhanding;
    }

    private void initView() {
        initTitleBar("事项查询", this, null);
        lv_queryhanding = (ListView) findViewById(R.id.lv_queryhanding);
        searchEdt = (EditText) findViewById(R.id.et_query_content);
        (findViewById(R.id.tv_search)).setOnClickListener(this);
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                parseData(0);
                parseData(1);
                parseData(2);
            }
        }.start();
        lv_queryhanding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyQueryActivity.this, WorkGuideNewActivity.class);
                intent.putExtra("tag_name", searchList.get(position).title);
                startActivity(intent);
            }
        });
    }

    private void updateList() {
        if (null == mAdapter) {
            mAdapter = new CommonAdapter<TestData>(this, searchList, R.layout.list_item) {

                @Override
                public void convert(ViewHolder holder, TestData item) {
                    holder.setTextView(R.id.text, item.content);
                    if (!BeanUtils.isEmpty(item.department)) {
                        holder.setTextView(R.id.depart_txt, item.department);
                    } else {
                        holder.setTextView(R.id.depart_txt, item.title);
                    }
                }
            };
            lv_queryhanding.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.tv_search:
                filterData();
            default:
                break;
        }
    }

    private void filterData() {
        String searchContent = searchEdt.getText().toString().trim();
        if (BeanUtils.isEmpty(searchContent)) {
            mHandler.sendEmptyMessage(0);
            return;
        }
        searchList.clear();
        for (TestData data : list) {
            if (data.title.contains(searchContent)
                    || data.content.contains(searchContent)
                    || data.department.contains(searchContent)) {
                searchList.add(data);
            }
        }
        updateList();
    }

    private void parseData(int type) {
        boolean isDepart = false;
        String string = null;
        if (type == 0) {
//            string = getString(R.string.personal_data);
        } else if (1 == type) {
//            string = getString(R.string.company_data);
        } else {
            isDepart = true;
//            string = getString(R.string.department_data);
        }
        String[] types = string.split("@");
        if (!BeanUtils.isEmpty(types)) {
            for (int i = 0; i < types.length; i++) {
                String[] contents = types[i].split(":");
                if (null != contents) {
                    if (contents.length > 1) {
                        String[] messages = contents[1].split("\\s+");
                        if (null != messages && messages.length > 0) {
                            if (isDepart) {
                                for (int j = 0; j < messages.length; j++) {
                                    TestData item = new TestData();
                                    item.title = contents[0];
                                    item.content = messages[j];
                                    item.department = item.title;
                                    list.add(item);
                                }
                            } else {
                                int count = messages.length / 2;
                                for (int j = 0; j < count; j++) {
                                    TestData item = new TestData();
                                    item.title = contents[0];
                                    item.content = messages[j * 2];
                                    item.department = messages[j * 2 + 1];
                                    list.add(item);
                                }
                            }
                        }
                    }
//                    else {
//                        TestData item = new TestData();
//                        item.title = contents[0];
//                        list.add(item);
//                    }
                }
            }
        }
        mHandler.sendEmptyMessage(0);
    }

}
