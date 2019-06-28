package com.powerrich.office.oa.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.QueryTabInfoAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.QueryInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
/**
 * 表单详情界面
 * @author Administrator
 *
 */
public class QueryTabInfoActivity extends BaseActivity implements OnClickListener {

	private ListView lv_query_tab_info;
	private String prokeyId;
	private String formUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initTitleBar(R.string.title_activity_query_tab_info, this, null);
		prokeyId = getIntent().getStringExtra("prokeyId");
		formUrl = getIntent().getStringExtra("formUrl");
		initView();
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_query_tab_info;
	}

	private void initView() {
		lv_query_tab_info = (ListView) findViewById(R.id.lv_query_tab_info);
		queryTabInfo();
	}
	/**
	 * 办件详情下表单详情请求
	 */
	private void queryTabInfo() {
		ApiRequest request = OAInterface.queryTabInfo(prokeyId, formUrl);
		invoke.invokeWidthDialog(request, callBack);
	}
	
	/**
	 * 获取办件详情数据解析
	 */
	private void showDoFileDetail(List<ResultItem> result) {
		if (result == null) {
			return;
		}
		List<QueryInfo> queryInfos = new ArrayList<QueryInfo>();
		for (ResultItem resultItem : result) {
			Map<String, Object> items = resultItem.getItems();
			List<String> keys = new ArrayList<String>();
			for (String key : items.keySet()) {
				keys.add(key);
			}
			for (int i = 0; i < keys.size(); i++) {
				QueryInfo queryInfo = new QueryInfo();
				queryInfo.setItemName(keys.get(i));
				queryInfo.setContent(resultItem.getString(keys.get(i)));
				queryInfos.add(queryInfo);
			}
		}
		
		lv_query_tab_info.setAdapter(new QueryTabInfoAdapter(QueryTabInfoActivity.this, queryInfos));
	}
	
	IRequestCallBack callBack = new BaseRequestCallBack() {

		@Override
		public void process(HttpResponse response, int what) {
			ResultItem item = response.getResultItem(ResultItem.class);
			String code = item.getString("code");
			String message = item.getString("message");
			if (Constants.SUCCESS_CODE.equals(code)) {
				List<ResultItem> result = item.getItems("DATA");
				showDoFileDetail(result);
			} else {
				DialogUtils.showToast(QueryTabInfoActivity.this, message);
			}
		}
		
	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.system_back:
			finish();
			break;

		default:
			break;
		}
	}
}
