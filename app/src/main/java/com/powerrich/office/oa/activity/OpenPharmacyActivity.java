package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.PharmacyListAdapter;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.PharmacyInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * 开药店界面
 * @author Administrator
 *
 */
public class OpenPharmacyActivity extends BaseActivity implements OnClickListener {

	private ListView lv_pharmacy;
	private String itemCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initTitleBar(R.string.title_activity_open_pharmacy, this, null);
		itemCode = getIntent().getStringExtra("item_code");
		initView();
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_open_pharmacy;
	}

	private void initView() {
		lv_pharmacy = (ListView) findViewById(R.id.lv_pharmacy);
		loadData();
	}

	private void loadData() {
		invoke.invokeWidthDialog(OAInterface.getPackServiceByItemCode(itemCode), callBack);
	}

	IRequestCallBack callBack = new BaseRequestCallBack() {

		@Override
		public void process(HttpResponse response, int what) {
			ResultItem item = response.getResultItem(ResultItem.class);
			String message = item.getString("message");
			if (Constants.SUCCESS_CODE.equals(item.get("code"))) {
				List<ResultItem> items = item.getItems("data");
				showList(items);
			}else {
				DialogUtils.showToast(OpenPharmacyActivity.this, message);
			}
		}
	};
	/**
	 * 展示药店列表
	 * @param items
	 */
	private void showList(List<ResultItem> items) {
		if (BeanUtils.isEmpty(items)) {
			return;
		}
		List<PharmacyInfo> pharmacyInfos=new ArrayList<PharmacyInfo>();
		for (ResultItem item : items) {
			String dept_id = item.getString("DEPT_ID");
			String pkdeptname = item.getString("PKDEPTNAME");
			String pkname = item.getString("PKNAME");
			String pxid = item.getString("PXID");
			String sxid = item.getString("SXID");
			PharmacyInfo pharmacyInfo = new PharmacyInfo(dept_id,pkdeptname,pkname,pxid,sxid);
			pharmacyInfos.add(pharmacyInfo);
		}
		lv_pharmacy.setAdapter(new PharmacyListAdapter(OpenPharmacyActivity.this, pharmacyInfos));
	}
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
