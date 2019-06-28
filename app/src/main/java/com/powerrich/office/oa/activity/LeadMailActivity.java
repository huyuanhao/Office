package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonViewPagerHelper;
import com.powerrich.office.oa.adapter.DepartmentEVAdapter;
import com.powerrich.office.oa.adapter.WardenMailListAdapter;
import com.powerrich.office.oa.adapter.WardenMailListAdapter.WidgetClick;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.MailBoxInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 领导信箱
 * @author Administrator
 *
 */
public class LeadMailActivity extends BaseActivity implements OnClickListener,WidgetClick {

	public static final String[] ITTILES = new String[]{"区长信箱","部门信箱"};
	private List<CommonViewPagerHelper.PagerModel> mListDatas = new ArrayList<CommonViewPagerHelper.PagerModel>();
	private CommonViewPagerHelper helper;
	private LayoutInflater layoutInflater;
	private LinearLayout layout;
	private View warden_mail,department_mail;
	private ListView lv_warden_mailbox;
	private ExpandableListView ev_department_mailbox;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initTitleBar(R.string.title_activity_lead_mail, this, null);
		initView();
		initViewPagers(savedInstanceState);
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_lead_mail;
	}

	private void initView() {
		layoutInflater = getLayoutInflater();
		layout = (LinearLayout) findViewById(R.id.view_pager);
	}
	
	private void initViewPagers(Bundle savedInstanceState) {
		helper = new CommonViewPagerHelper(LeadMailActivity.this, layout, null);
        helper.onCreate(savedInstanceState);
        List<View> views = new ArrayList<View>();
        warden_mail = layoutInflater.inflate(R.layout.warden_mail, null);
        views.add(warden_mail);
        department_mail = layoutInflater.inflate(R.layout.department_mail, null);
        views.add(department_mail);
        for (int i = 0; i < ITTILES.length; i++) {
			mListDatas.add(helper.new PagerModel(ITTILES[i], views.get(i), null));
		}
        helper.showViews(mListDatas);
        loadView();
	}

	private void loadView() {
		lv_warden_mailbox = (ListView) warden_mail.findViewById(R.id.lv_warden_mailbox);
		lv_warden_mailbox.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
		ev_department_mailbox = (ExpandableListView) department_mail.findViewById(R.id.ev_department_mailbox);
		loadData();
	}

	private void loadData() {
		List<MailBoxInfo> mailBoxInfos=new ArrayList<MailBoxInfo>();
		for (int i = 0; i < 5; i++) {
			mailBoxInfos.add(new MailBoxInfo("庄杨渊", "", "贯彻落实党和国家路线、方针、策略，贯彻落实党和国家路线、方针、策略", 0));
		}
		lv_warden_mailbox.setAdapter(new WardenMailListAdapter(this, mailBoxInfos));
		
		List<String> parent = new ArrayList<String>();
		parent.add("省级部门");
		parent.add("市级部门");

		Map<String, List<String>> map = new HashMap<String, List<String>>();

		List<String> list1 = new ArrayList<String>();
		list1.add("省发改委");
		list1.add("省信访局");
		list1.add("省司法厅");
		map.put("省级部门", list1);

		List<String> list2 = new ArrayList<String>();
		list2.add("市级");
		list2.add("市级信访局");
		list2.add("市建设厅");
		map.put("市级部门", list2);

		ev_department_mailbox.setAdapter(new DepartmentEVAdapter(this,parent,map));

	     //将所有项设置成默认展开
	    int groupCount = ev_department_mailbox.getCount();
		for (int i = 0; i < groupCount; i++) {
			ev_department_mailbox.expandGroup(i);
		}
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

	@Override
	public void click(int position) {
		startActivity(new Intent(LeadMailActivity.this,WriteToMeActivity.class));
	}
}
