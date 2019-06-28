package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.yt.simpleframe.view.LoadingWebView;

/**
 * 文 件 名：NewsDetailActivity
 * 描   述：新闻政务详情
 * 作   者：Wangzheng
 * 时   间：2017/11/17
 * 版   权：v1.0
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener{

	private LoadingWebView webView;
	private String newsId;
	private TextView tv_title;
	private TextView tv_create_time;
	private TextView tv_source;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		newsId = getIntent().getStringExtra("news_id");
		initView();
		getNewsDetail();
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_news_detail;
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		initTitleBar(R.string.news, this, null);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_create_time = (TextView) findViewById(R.id.tv_create_time);
		tv_source = (TextView) findViewById(R.id.tv_source);
		webView = (LoadingWebView) findViewById(R.id.webView);
		int tesize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,75, this.getResources().getDisplayMetrics());
		webView.setTextZoom(tesize);
	}
	
	/**
	 * 政务详情请求
	 */
	private void getNewsDetail() {
		ApiRequest request = OAInterface.getNewsDetail(newsId);
		invoke.invokeWidthDialog(request, callBack);
	}

	/**
	 * 解析数据
	 * @param result
	 */
	private void showNewsDetail(ResultItem result) {
		if (result == null) {
			return;
		}
		String title = result.getString("TITLE");
		String createTime = result.getString("CREATE_DATE");
		String content = result.getString("NEWS_CONTENT");
		String source = result.getString("SOURCE");
		tv_title.setText(title);
		tv_create_time.setText("时间:" + createTime);
		tv_source.setText("来源:" + source);
		webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
	}

	private IRequestCallBack callBack = new BaseRequestCallBack() {

		@Override
		public void process(HttpResponse response, int what) {
			if(!BeanUtils.isEmpty(response)){
				ResultItem item = response.getResultItem(ResultItem.class);
				String code = item.getString("code");
				String message = item.getString("message");
				if (Constants.SUCCESS_CODE.equals(code)) {
					ResultItem result = (ResultItem) item.get("DATA");
					showNewsDetail(result);
				} else {
					DialogUtils.showToast(NewsDetailActivity.this, message);
				}
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.system_back:
				NewsDetailActivity.this.finish();
				break;
			default:
				break;
		}
	}
}
