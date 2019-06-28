package com.powerrich.office.oa.test;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.base.InvokeHelper;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;

import java.util.List;


public class TestInterface extends Activity implements OnClickListener {

	public static final String TAG = "TestInterface";
	private int index = 0;
	private InvokeHelper invoke;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		invoke = new InvokeHelper(this);
		initView();
	}

	private void initView() {
		String[] items = {"getToken","getLoginUser", "getVersion", "getServiceOrItems", "getItem", "iWant", "search", "laws", "getLawById", "saveReg"};
		LinearLayout root = new LinearLayout(this);
		root.setBackgroundColor(Color.BLACK);
		root.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		root.setOrientation(LinearLayout.VERTICAL);
		for (int i = 0; i < items.length; i++) {
			root.addView(createItem(items[i]));
		}
		setContentView(root);
	}

    private View createItem(String content) {
		LinearLayout child = new LinearLayout(this);
		child.setBackgroundColor(Color.WHITE);
		child.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 60));
		child.setPadding(10, 10, 10, 10);
		TextView text = new TextView(this);
		text.setText(content);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		child.setOnClickListener(this);
		child.setId(index++);
		child.addView(text, params);
		return child;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case 0:
//			invoke.invokeWidthDialog(OAInterface.getToken("AE912D644F39F1F51CF70A07DB4F22A4", "E12CF98663A822983118AD92B2FF3AB5"), callBack);
			break;
		case 1:
			invoke.invokeWidthDialog(OAInterface.getUserByIdInfo("13333333333"), loginCallBack);
			break;
		case 2:
			invoke.invokeWidthDialog(OAInterface.getVersion(), callBack);
			break;
		case 3:
			invoke.invokeWidthDialog(OAInterface.getServiceOrItems("1", "1"), callBack);
			break;
		case 4:
			invoke.invokeWidthDialog(OAInterface.getItem("201611181643273408842959420"), callBack);
			break;
		case 5:
			invoke.invokeWidthDialog(OAInterface.iWant("1", null), callBack);
			break;
		case 6:
//			invoke.invokeWidthDialog(OAInterface.search(""), callBack);
			break;
		case 7:
			invoke.invokeWidthDialog(OAInterface.laws("1", "", ""), callBack);
			break;
		case 8:
			invoke.invokeWidthDialog(OAInterface.getLawById("1", "201610311722451000764824080"), callBack);
			break;
		case 9:
			break;
		}
	}
	
	IRequestCallBack loginCallBack = new BaseRequestCallBack() {

		@Override
		public void process(HttpResponse response, int what) {
			ResultItem item = response.getResultItem(ResultItem.class);
			ResultItem data = (ResultItem) item.get("data");
			UserInfo userInfo = new UserInfo();
			String account = data.getString("account");
			Boolean flag = item.getBoolean("success");
			System.out.println(flag);
		}
		
	};
	
	IRequestCallBack callBack = new BaseRequestCallBack() {

		@Override
		public void process(HttpResponse response, int what) {
			ResultItem item = response.getResultItem(ResultItem.class);
			List<ResultItem> items = item.getItems("data");
//			String code = item.getString("code");
//			String message = item.getString("message");
//			List<ResultItem> items = item.getItems("data");
//			boolean success = item.getBoolean("success");
//			System.out.println("items=" + items.size());
		}
		
	};
}
