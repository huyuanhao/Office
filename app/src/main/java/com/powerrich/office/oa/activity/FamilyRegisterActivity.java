package com.powerrich.office.oa.activity;

import java.util.ArrayList;
import java.util.List;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.FamilyRegisterListAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.FamilyRegisterInfo;
import com.powerrich.office.oa.bean.WorkInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
/**
 * 户籍界面
 * @author Administrator
 *
 */
public class FamilyRegisterActivity extends BaseActivity implements View.OnClickListener{

	
	private ListView lv_family_register;
	private List<FamilyRegisterInfo> infos = new ArrayList<FamilyRegisterInfo>();
	private String tag_id,tag_name;
	private List<WorkInfo> workInfos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tag_id = getIntent().getStringExtra("tag_id");
		tag_name = getIntent().getStringExtra("tag_name");
		initView();
		initData();
		getItem();
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_family_register;
	}

	private void initView() {
		initTitleBar(tag_name, this, null);
		lv_family_register = (ListView) findViewById(R.id.lv_family_register);
	}
	
	
	private void initData() {
		lv_family_register.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(FamilyRegisterActivity.this, WorkGuideNewActivity.class);
				intent.putExtra("item_id", infos.get(position).getItemId());
				intent.putExtra("tag_id", workInfos.get(position).getTag_id());
				intent.putExtra("tag_name", tag_name);
				startActivity(intent);
			}
		});
	}
	
	
	/**
	 * 户籍列表请求
	 */
	private void getItem() {
		ApiRequest request = OAInterface.getServiceOrItems(Constants.PERSONAL_WORK_TYPE, tag_id);
        if (null != invoke)
            invoke.invokeWidthDialog(request, callBack);
	}
	
	/**
	 * 展示列表
	 * @param result
	 */
	private void showItem(ResultItem result) {
		if (BeanUtils.isEmpty(result)) {
			return;
		}
		workInfos = new ArrayList<WorkInfo>();
		List<ResultItem> lists = result.getItems("1");
		if (!BeanUtils.isEmpty(lists)) {
			for (ResultItem resultItem : lists) {
				WorkInfo workInfo = new WorkInfo();
				String tag_id = resultItem.getString("TAG_ID");
				workInfo.setTag_id(tag_id);
				workInfos.add(workInfo);
			}
		}
		List<ResultItem> items = result.getItems("2");
		if (!BeanUtils.isEmpty(items)) {
			for (ResultItem resultItem : items) {
				FamilyRegisterInfo info = new FamilyRegisterInfo();
				String itemId = resultItem.getString("SXJBXXID");
				String itemName = resultItem.getString("ITEMNAME");
				String createTime = resultItem.getString("CREATETIME");
				info.setItemId(itemId);
				info.setTitle(itemName);
				info.setTime(createTime);
				infos.add(info);
			}
		}
		lv_family_register.setAdapter(new FamilyRegisterListAdapter(this, infos));
	}
	
	IRequestCallBack callBack = new BaseRequestCallBack() {

		@Override
		public void process(HttpResponse response, int what) {
			ResultItem item = response.getResultItem(ResultItem.class);
			String code = item.getString("code");
			String message = item.getString("message");
			if (Constants.SUCCESS_CODE.equals(code)) {
				ResultItem result = (ResultItem) item.get("DATA");
				showItem(result);
			} else {
				DialogUtils.showToast(FamilyRegisterActivity.this, message);
			}
		}
		
	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.system_back:
			FamilyRegisterActivity.this.finish();
			break;

		default:
			break;
		}
	}
}
