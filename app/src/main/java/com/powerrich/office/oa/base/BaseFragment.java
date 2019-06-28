package com.powerrich.office.oa.base;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.PermissionPageUtils;
import com.powerrich.office.oa.view.MyDialog;

public abstract class BaseFragment extends Fragment implements FragmentInterface, OnTouchListener {

	protected String errorMessage;

	protected InvokeHelper mInvokeHelper;
    /**
     * 模拟后退键
     */
    protected void back() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate();
    }

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInvokeHelper = new InvokeHelper(getContext());
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(provideContentViewId(), container, false);
		AutoUtils.auto(rootView);
		return rootView;
	}

	protected abstract int provideContentViewId();

	/* (non-Javadoc)
             * @see android.support.v4.app.Fragment#onViewCreated(android.view.View, android.os.Bundle)
             */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.setOnTouchListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    /* (non-Javadoc)
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 拦截触摸事件，防止传递到下一层的View
        return true;
    }

    public void dispatchCommand(int id, Bundle args) {
        if (getActivity() instanceof FragmentCallback) {
            FragmentCallback callback = (FragmentCallback) getActivity();
            callback.onFragmentCallback(this, id, args);
        } else {
            throw new IllegalStateException("The host activity does not implement FragmentCallback.");
        }
    }

    public void refreshViews() {

    }

    public boolean commitData() {
        return false;
    }
    
    /**
	 * 提示是否退出对话框
	 */
	protected void showCheckDialog() {
		MyDialog builder = new MyDialog(getActivity()).builder();
		builder.setTitle(R.string.app_name);
		builder.setMessage(R.string.alert_dialog_exit_title);
		builder.setPositiveButton(R.string.system_dialog_confirm,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(getActivity(), LoginActivity.class);
						//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						//getActivity().finish();
					}
				});
		builder.show();
	}
	
	/**
	 * 检查认证
	 */
	@SuppressWarnings("deprecation")
	public boolean checkResult(ResultItem item) {
		try {
			if (!BeanUtils.isEmpty(item)) {
				String code = item.getString("code");
				if (!Constants.SUCCESS_CODE.equals(code)) {
					errorMessage = item.getString("message");
					// 令牌失效
					if (Constants.LOGIN_FAIL_CODE.equals(code)) {
						showCheckDialog();
					} else {
						Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
					}
					return false;
				}
			} else {
				errorMessage = getString(R.string.system_request_error_message);
				Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
				return false;
			}
		} catch (Exception e) {

		}
		return true;
	}

	private Dialog permissionDialog;
	/**
	 * 提示开启权限
	 */
	public void showTipDialog(String message) {
		if(this.getContext() == null){
			Log.e("BaseFragment","context is null");
			return;
		}
		if (permissionDialog == null) {
			permissionDialog = new AlertDialog.Builder(this.getContext())
					.setTitle("权限通知")
					.setMessage(message)
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					})
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							PermissionPageUtils.getInstance(BaseFragment.this.getContext()).jumpPermissionPage();
						}
					})
					.create();
		}
		if (permissionDialog.isShowing()) return;
		permissionDialog.show();
	}
}
