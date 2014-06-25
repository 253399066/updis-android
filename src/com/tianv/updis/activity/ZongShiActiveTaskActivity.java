package com.tianv.updis.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.melvin.android.base.common.ui.IMessageDialogListener;
import com.melvin.android.base.common.ui.MessageDialog;
import com.tianv.updis.AppException;
import com.tianv.updis.Constant;
import com.tianv.updis.R;
import com.tianv.updis.model.ProjectModel;
import com.tianv.updis.model.SpinnerData;
import com.tianv.updis.model.UIUtilities;
import com.tianv.updis.task.CategoryListTask;
import com.tianv.updis.task.ChiefEngineerListTask;
import com.tianv.updis.task.ProjectTypeDropDownListTask;
import com.tianv.updis.task.ReviewActiveTask;
import com.tianv.updis.task.TaskCallBack;

/**
 * Created by lm3515 on 14-1-22.
 */
public class ZongShiActiveTaskActivity extends Activity implements OnClickListener, IMessageDialogListener {
	// private TextView mProjectNameTv;
	private int AUDIT_CONFIRM_1 = 10011;
	private int AUDIT_CONFIRM_2 = 10012;
	private int AUDIT_CONFIRM_3 = 10013;
	/**
	 * 页面弹出对话框
	 */
	protected MessageDialog mDialog = null;
	
	private String showButton; // String 0: 不显示所长审批按钮; 1:显示

	private RelativeLayout qitaLayout,chiefEngineerLayout1,chiefEngineerLayout2;
	private ProjectTypeDropDownListTask projectTypeTask;
	private ReviewActiveTask reviewActiveTask;
	private ProgressDialog mProgressDialog;
	private Button zongShiRejectButton,zongShiReviewButton;

	private Spinner projectType,category,projectManageLevel;
	private CategoryListTask categoryListTask;
	private ChiefEngineerListTask chiefEngineerListTask;
	private EditText qitaEdit;
	private TextView projectManager;
	private TextView chiefEngineerIds;
	private List selectedTemp = new ArrayList();
	private List chiefEngineerList= new ArrayList();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_info_zongshi);
		findViewById();
		initView();
	}

	private void initView() {
		
		// String taskId =
		// (String)getIntent().getSerializableExtra(Constant.EXTRA_ACTIVE_TASK);
		showProgressDialog();
		
		projectTypeTask = new ProjectTypeDropDownListTask(ZongShiActiveTaskActivity.this, getProjectTypeListTask(),null);
		projectTypeTask.execute();
		
		projectType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){ 


			@Override 
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) { 
				String value = ((SpinnerData)projectType.getSelectedItem()).getValue();
				categoryListTask = new CategoryListTask(ZongShiActiveTaskActivity.this, getCategoryListTask(),value);
				categoryListTask.execute();

			} 


			@Override 
			public void onNothingSelected(AdapterView<?> arg0) { 
			// TODO Auto-generated method stub 
			} 
		}); 
		
		projectManageLevel.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){ 
			@Override 
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) { 
				String value = ((SpinnerData)projectManageLevel.getSelectedItem()).getText();
				if("院级".equals(value)){
					chiefEngineerLayout1.setVisibility(View.VISIBLE);
					chiefEngineerLayout2.setVisibility(View.VISIBLE);
				}
				else{
					chiefEngineerLayout1.setVisibility(View.GONE);
					chiefEngineerLayout2.setVisibility(View.GONE);
				}

			} 


			@Override 
			public void onNothingSelected(AdapterView<?> arg0) { 
			// TODO Auto-generated method stub 
			} 
		}); 
		
		zongShiRejectButton.setOnClickListener(this);
		zongShiReviewButton.setOnClickListener(this);
		
		chiefEngineerIds.setOnClickListener(this);
	}

	public void showEditTextInfo(int requestCode, Context context, String title, IMessageDialogListener listener) {

		Builder builder = mDialog.createDialogBuilder(title, null);
		final LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.dialog_edittext, null);
		builder.setView(textEntryView);
		if (listener != null) {
			builder.setPositiveButton(mDialog.sCaptionOk, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					EditText reject_comment = (EditText) textEntryView.findViewById(R.id.reject_comment);
					showProgressDialog();
					ProjectModel pm = (ProjectModel) getIntent().getSerializableExtra(Constant.EXTRA_PROJECTMODEL);
					String comment =  URLEncoder.encode(reject_comment.getText().toString());
					String urlParam = Constant.INTERFACE_REVIEW_ACTIVETASK + "projectLeadRejectActiveTask?id=" + pm.getActiveTaskId() + "&comment="
							+ comment;
					reviewActiveTask = new ReviewActiveTask(ZongShiActiveTaskActivity.this, getProjectRejectActiveTaskResult(), urlParam);
					reviewActiveTask.execute();
				}
			});
			builder.setNegativeButton(mDialog.sCaptionCancel, new DialogOnClickListener(requestCode, 2, listener));
		} else {
			builder.setPositiveButton(mDialog.sCaptionOk, null);
			builder.setPositiveButton(mDialog.sCaptionCancel, null);
		}

		builder.create().show();
	}

	private class DialogOnClickListener implements DialogInterface.OnClickListener {

		private int requestCode;

		private int clickid = 0;

		private IMessageDialogListener listener;

		public DialogOnClickListener(int requestCode, int clickid, IMessageDialogListener listener) {
			this.requestCode = requestCode;
			this.clickid = clickid;
			this.listener = listener;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			if (this.listener != null) {
				switch (this.clickid) {
				case 0:
					this.listener.onDialogClickClose(this.requestCode);
					break;
				case 1:
					this.listener.onDialogClickOk(this.requestCode);
					break;
				case 2:
					this.listener.onDialogClickCancel(this.requestCode);
					break;
				}
			}

		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.zongShiRejectButton:
				UIUtilities.showCustomToast(this, R.string.updis_network_error_tip);
				break;
			case R.id.zongShiReviewButton:
				UIUtilities.showCustomToast(this, R.string.updis_network_error_tip);
				break;
			case R.id.chiefEngineerIds:
				chiefEngineerListTask = new ChiefEngineerListTask(ZongShiActiveTaskActivity.this, getChiefEngineerList());
				chiefEngineerListTask.execute();
				break;
		}
		
	}

	@Override
	public void onDialogClickOk(int requestCode) {
		// TODO Auto-generated method stub
		if (requestCode == AUDIT_CONFIRM_1) {// 所长审核
			showProgressDialog();
			ProjectModel pm = (ProjectModel) getIntent().getSerializableExtra(Constant.EXTRA_PROJECTMODEL);
			String urlParam = Constant.INTERFACE_REVIEW_ACTIVETASK + "reviewActiveTask?id=" + pm.getActiveTaskId();
			reviewActiveTask = new ReviewActiveTask(ZongShiActiveTaskActivity.this, getReviewActiveTaskResult(), urlParam);
			reviewActiveTask.execute();
		} else if (requestCode == AUDIT_CONFIRM_3) {// 项目启动
			showProgressDialog();
			ProjectModel pm = (ProjectModel) getIntent().getSerializableExtra(Constant.EXTRA_PROJECTMODEL);
			String urlParam = Constant.INTERFACE_REVIEW_ACTIVETASK + "projectLeadReviewActiveTask?id=" + pm.getActiveTaskId();
			reviewActiveTask = new ReviewActiveTask(ZongShiActiveTaskActivity.this, getProjectBeginActiveTaskResult(), urlParam);
			reviewActiveTask.execute();
		}
	}

	@Override
	public void onDialogClickCancel(int requestCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDialogClickClose(int requestCode) {
		// TODO Auto-generated method stub

	}

	private TaskCallBack<Void, String> getReviewActiveTaskResult() {
		TaskCallBack<Void, String> taskCallBask = new TaskCallBack<Void, String>() {
			@Override
			public void beforeDoingTask() {

			}

			@Override
			public void doingTask() {

			}

			@Override
			public void onCancel() {

			}

			@Override
			public void doingProgress(Void... fParam) {
			}

			@Override
			public void endTask(String eParam, AppException appException) {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
				MessageDialog mDialog = new MessageDialog(ZongShiActiveTaskActivity.this);
				if ("1".equals(eParam)) {
					initView();
					mDialog.showInfo("所长审核", "提交成功");
				} else {
					mDialog.showInfo("所长审核", "提交失败");
				}
			}
		};
		return taskCallBask;
	}

	private TaskCallBack<Void, String> getProjectRejectActiveTaskResult() {
		TaskCallBack<Void, String> taskCallBask = new TaskCallBack<Void, String>() {
			@Override
			public void beforeDoingTask() {

			}

			@Override
			public void doingTask() {

			}

			@Override
			public void onCancel() {

			}

			@Override
			public void doingProgress(Void... fParam) {
			}

			@Override
			public void endTask(String eParam, AppException appException) {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
				MessageDialog mDialog = new MessageDialog(ZongShiActiveTaskActivity.this);
				if ("1".equals(eParam)) {
					initView();
					mDialog.showInfo("打回", "提交成功");
				} else {
					mDialog.showInfo("打回", "提交失败");
				}
			}
		};
		return taskCallBask;
	}

	private TaskCallBack<Void, String> getProjectBeginActiveTaskResult() {
		TaskCallBack<Void, String> taskCallBask = new TaskCallBack<Void, String>() {
			@Override
			public void beforeDoingTask() {

			}

			@Override
			public void doingTask() {

			}

			@Override
			public void onCancel() {

			}

			@Override
			public void doingProgress(Void... fParam) {
			}

			@Override
			public void endTask(String eParam, AppException appException) {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
				MessageDialog mDialog = new MessageDialog(ZongShiActiveTaskActivity.this);
				if ("1".equals(eParam)) {
					initView();
					mDialog.showInfo("项目启动", "提交成功");
				} else {
					mDialog.showInfo("项目启动", "提交失败");
				}
			}
		};
		return taskCallBask;
	}

	private TaskCallBack<Void, List> getProjectTypeListTask() {
		TaskCallBack<Void, List> taskCallBask = new TaskCallBack<Void, List>() {
			@Override
			public void beforeDoingTask() {

			}

			@Override
			public void doingTask() {

			}

			@Override
			public void onCancel() {

			}

			@Override
			public void doingProgress(Void... fParam) {
				if (fParam != null) {

				}
			}

			@Override
			public void endTask(List projectTypeList, AppException appException) {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
				if (projectTypeList != null) {
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(ZongShiActiveTaskActivity.this,
			                android.R.layout.simple_spinner_item, projectTypeList);
					
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					projectType.setAdapter(adapter);
					if(projectTypeList.size() >0){
						SpinnerData sd = (SpinnerData)projectTypeList.get(0);
						categoryListTask = new CategoryListTask(ZongShiActiveTaskActivity.this, getCategoryListTask(),sd.getValue());
						categoryListTask.execute();
					}
					
				}
			}
		};
		return taskCallBask;
	}

	private TaskCallBack<Void, List> getCategoryListTask() {
		TaskCallBack<Void, List> taskCallBask = new TaskCallBack<Void, List>() {
			@Override
			public void beforeDoingTask() {

			}

			@Override
			public void doingTask() {

			}

			@Override
			public void onCancel() {

			}

			@Override
			public void doingProgress(Void... fParam) {
				if (fParam != null) {

				}
			}

			@Override
			public void endTask(List eParam, AppException appException) {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
				if (eParam != null) {
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(ZongShiActiveTaskActivity.this,
			                android.R.layout.simple_spinner_item, eParam);
					
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					category.setAdapter(adapter);
					if(eParam.size() >0){
						SpinnerData sd = (SpinnerData)eParam.get(0);
						if("其它".equals(sd.getText())){
							qitaLayout.setVisibility(View.VISIBLE);
						}
						else{
							qitaLayout.setVisibility(View.GONE);
						}
					}
				}
			}
		};
		return taskCallBask;
	}
	
	private TaskCallBack<Void, List> getChiefEngineerList() {
		TaskCallBack<Void, List> taskCallBask = new TaskCallBack<Void, List>() {
			@Override
			public void beforeDoingTask() {

			}

			@Override
			public void doingTask() {

			}

			@Override
			public void onCancel() {

			}

			@Override
			public void doingProgress(Void... fParam) {
				if (fParam != null) {

				}
			}

			@Override
			public void endTask(List eParam, AppException appException) {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
				if (eParam != null) {
					 Dialog dialog = onCreateDialog(eParam);
					 dialog.show();
				}
			}
		};
		return taskCallBask;
	}
	public String getNotBlank(String value) {
		if (value == null || value.trim().equals("")) {
			return "-";
		} else {
			return value;
		}
	}

	public String getNotBlankBoolean(String value) {
		if (value == null || value.trim().equals("")) {
			return "-";
		} else {
			if ("1".equals(value)) {
				return "是";
			} else if ("0".equals(value)) {
				return "否";
			} else {
				return value;
			}
		}
	}

	private void showProgressDialog() {

		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setProgressStyle(0);
		mProgressDialog.setMessage("正在加载数据,请稍候...");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	public String getFilterString(String str) {
		if (TextUtils.isEmpty(str)) {
			return "-";
		} else {
			return str;
		}
	}

	private void findViewById() {
		zongShiReviewButton = (Button) findViewById(R.id.zongShiReviewButton);
		zongShiRejectButton = (Button) findViewById(R.id.zongShiRejectButton);
		projectType = (Spinner) findViewById(R.id.projectType);
		category =  (Spinner) findViewById(R.id.category);
		qitaLayout =  (RelativeLayout) findViewById(R.id.qitaLayout);
		chiefEngineerLayout1 = (RelativeLayout) findViewById(R.id.chiefEngineerLayout1);
		chiefEngineerLayout2 = (RelativeLayout) findViewById(R.id.chiefEngineerLayout2);
		qitaEdit = (EditText)findViewById(R.id.qitaEdit);
		qitaEdit.clearFocus();
		projectManageLevel = (Spinner) findViewById(R.id.projectManageLevel);
		
		List list = new ArrayList();
		list.add(new SpinnerData("LH200307240002","所级"));
		list.add(new SpinnerData("LH200307240001","院级"));
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ZongShiActiveTaskActivity.this,
                android.R.layout.simple_spinner_item, list);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		projectManageLevel.setAdapter(adapter);
		
		projectManager = (TextView) findViewById(R.id.projectManager);
		String projectLead = getIntent().getStringExtra("projectLead");
		projectManager.setText(projectLead);
		
		
		chiefEngineerIds = (TextView)findViewById(R.id.chiefEngineerIds);
	}

	protected Dialog onCreateDialog( final List list) { // 重写onCreateDialog方法
		Dialog dialog = null;
		Builder b = new AlertDialog.Builder(this); // 创建Builder对象
		// b.setIcon(R.drawable.header); // 设置图标
		// b.setTitle(R.string.title); // 设置标题
		final boolean selected[] = new boolean[list.size()];
		final String[] checkboxText = new String[list.size()];
		final String[] checkboxValue = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			SpinnerData sd = (SpinnerData) list.get(i);
			checkboxText[i] = sd.getText();
			checkboxValue[i] = sd.getValue();
			selected[i] = false;
		}
		b.setMultiChoiceItems(checkboxText, selected, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				selected[which] = isChecked; // 设置选中标志位
				chiefEngineerList = new ArrayList();
				selectedTemp = new ArrayList();
				for (int i = 0; i < selected.length; i++) {
					if (selected[i]) { // 如果该选项被选中
						selectedTemp.add(checkboxValue[i]);
						chiefEngineerList.add(checkboxText[i]);
					}
				}

			}
		});
		b.setPositiveButton( // 添加按钮
				"确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String chiefEngineer = "";
						for(int i = 0; i < chiefEngineerList.size();i++){
							if(!"".equals(chiefEngineer)){
								chiefEngineer += ",";
							}
							chiefEngineer += (String)chiefEngineerList.get(i);
						}
						chiefEngineerIds.setText(chiefEngineer);
					}
				});
		
		dialog = b.create(); // 生成Dialog方法

		return dialog; // 返回Dialog方法
	}

}
