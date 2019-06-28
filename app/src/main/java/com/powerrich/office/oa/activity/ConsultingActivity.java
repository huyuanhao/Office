package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;

/**
 * 咨询投诉界面
 * @author Administrator
 *
 */
public class ConsultingActivity extends BaseActivity implements View.OnClickListener{

	/** 我要举报*/
	private LinearLayout ll_declare;
	/** 我要建议*/
	private LinearLayout ll_iwant_suggestion;
	/** 我要咨询*/
	private LinearLayout ll_iwant_consulting;
	/** 领导信箱*/
	private LinearLayout ll_lead_mail;
	/** 我要投诉*/
	private LinearLayout ll_iwant_complaint;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_consulting;
	}

	private void initView() {
		initTitleBar(R.string.home_interaction_exchange, this, null);
		ll_declare = (LinearLayout) findViewById(R.id.ll_declare);
		ll_iwant_suggestion = (LinearLayout) findViewById(R.id.ll_iwant_suggestion);
		ll_iwant_consulting = (LinearLayout) findViewById(R.id.ll_iwant_consulting);
		ll_lead_mail = (LinearLayout) findViewById(R.id.ll_lead_mail);
		ll_iwant_complaint = (LinearLayout) findViewById(R.id.ll_iwant_complaint);
	}
	
	private void initData() {
		ll_declare.setOnClickListener(this);
		ll_iwant_suggestion.setOnClickListener(this);
		ll_iwant_consulting.setOnClickListener(this);
		ll_lead_mail.setOnClickListener(this);
		ll_iwant_complaint.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.system_back://返回
			ConsultingActivity.this.finish();
			break;
		case R.id.ll_declare://我要举报
			DialogUtils.showToast(this, "功能暂未开发");
//			Intent intent1 = new Intent(ConsultingActivity.this, IWantSuggestActivity.class);
//			intent1.putExtra("iwant_type",Constants.REPORT_TYPE);
//			startActivity(intent1);
			break;
		case R.id.ll_iwant_consulting://我要咨询
			Intent intent2= new Intent(ConsultingActivity.this, IWantSuggestActivity.class);
			intent2.putExtra("iwant_type", Constants.CONSULTING_TYPE);
			startActivity(intent2);
			break;
		case R.id.ll_iwant_suggestion://我要建议
			Intent intent3 = new Intent(ConsultingActivity.this, IWantSuggestActivity.class);
			intent3.putExtra("iwant_type", Constants.SUGGEST_TYPE);
			startActivity(intent3);
			break;
		case R.id.ll_lead_mail://领导信箱
			startActivity(new Intent(ConsultingActivity.this, LeaderMailActivity.class));
			break;
		case R.id.ll_iwant_complaint://我要投诉
			Intent intent4 = new Intent(ConsultingActivity.this, IWantSuggestActivity.class);
			intent4.putExtra("iwant_type", Constants.COMPLAIN_TYPE);
			startActivity(intent4);
			break;
		default:
			break;
		}
	}
}
