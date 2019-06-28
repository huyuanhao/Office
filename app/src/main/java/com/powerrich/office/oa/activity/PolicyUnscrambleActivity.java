package com.powerrich.office.oa.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.PolicyUnscrambleAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.PolicyStatute;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

/**
 * @title  政策解读
 * @author dir_wang
 *
 */
public class PolicyUnscrambleActivity extends BaseActivity implements View.OnClickListener{
	private ListView lv_policy_unscramble;
	private TextView tv_no_data;
	private PolicyUnscrambleAdapter adapter;
	private List<PolicyStatute> policyUnscrambleLists = new ArrayList<PolicyStatute>();
	/** 政策法规类型 */
	private String type;
	/** 政策法规数据id */
	private String policyStatuteId;
	/** 政策法规标题 */
	private String title;
	private TextView tv_top_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getIntent().getStringExtra("type");
		policyStatuteId = getIntent().getStringExtra("policy_statute_id");
		title = getIntent().getStringExtra("title");
		initView();
		initData();
		getPolicyUnsramble();
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_policy_unscramble;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!BeanUtils.isEmpty(policyUnscrambleLists)) {
			policyUnscrambleLists.clear();
		}
//		getPolicyUnsramble();
//		showPolicyUnsrambleList(null);
	}
	
	private void initView() {
		initTitleBar(title, this, null);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_title.setText(title);
		lv_policy_unscramble = (ListView) findViewById(R.id.lv_policy_unscramble);
		tv_no_data = (TextView) findViewById(R.id.tv_no_data);
	}
	
	private void initData() {
		lv_policy_unscramble.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(PolicyUnscrambleActivity.this, NewsDetailActivity.class);
				String policyId = policyUnscrambleLists.get(position).getId();
				System.out.println("policId=" + policyId);
				intent.putExtra("type", type);
				intent.putExtra("id", BeanUtils.isEmpty(policyUnscrambleLists) ? "" : policyUnscrambleLists.get(position).getId());
				startActivity(intent);
			}
		});
	}
	
	/**
	 * 根据政策法规id查询具体政策内容请求
	 */
	private void getPolicyUnsramble() {
		ApiRequest request = OAInterface.getPolicyStatuteDetail(type, policyStatuteId);
		invoke.invokeWidthDialog(request, callBack);
	}
	
	/**
	 * 获取政策解读列表数据解析
	 */
	private void showPolicyUnsrambleList(ResultItem result) {
//		List<ResultItem> items = result.getItems("DATA");
//		if (!BeanUtils.isEmpty(items)) {
//			for (ResultItem resultItem : items) {
//				PolicyStatute policyStatute = new PolicyStatute();
//				String id = resultItem.getString("ID");
//				String title = resultItem.getString("TITLE");
//				String createTime = resultItem.getString("CREATTIME");
//				policyStatute.setId(id);
//				policyStatute.setTitle(title);
//				policyStatute.setCreateTime(createTime);
//				policyUnscrambleLists.add(policyStatute);
//			}
//		} else {
//			tv_no_data.setVisibility(View.VISIBLE);
//		}
		String[] titles = {"鹰潭市关于调整全省最低工资标准的通知", "鹰潭市关于进一步提升工业设计发展水平的意见"};
		String[] times = {"[2017-10-10]", "[2017-10-03]"};
		for (int i = 0; i < titles.length; i++) {
			PolicyStatute policyStatute = new PolicyStatute();
			policyStatute.setId(i + "");
			policyStatute.setTitle(titles[i]);
			policyStatute.setCreateTime(times[i]);
			policyUnscrambleLists.add(policyStatute);
		}

		if (!BeanUtils.isEmpty(policyUnscrambleLists)) {
			if (adapter == null) {
				adapter = new PolicyUnscrambleAdapter(PolicyUnscrambleActivity.this, policyUnscrambleLists);
				lv_policy_unscramble.setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
		} else {
			tv_no_data.setVisibility(View.VISIBLE);
		}
	}
	
	private IRequestCallBack callBack = new BaseRequestCallBack() {

		@Override
		public void process(HttpResponse response, int what) {
			if(!BeanUtils.isEmpty(response)){
				ResultItem item = response.getResultItem(ResultItem.class);
				String code = item.getString("code");
				String message = item.getString("message");
				if (Constants.SUCCESS_CODE.equals(code)) {
//					ResultItem result = (ResultItem) item.get("DATA");
//					showPolicyUnsrambleList(result);
				} else {
					DialogUtils.showToast(PolicyUnscrambleActivity.this, message);
				}
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.system_back:
			PolicyUnscrambleActivity.this.finish();
			break;

		default:
			break;
		}
	}
}
