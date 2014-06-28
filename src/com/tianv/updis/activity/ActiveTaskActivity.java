package com.tianv.updis.activity;

import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melvin.android.base.common.ui.IMessageDialogListener;
import com.melvin.android.base.common.ui.MessageDialog;
import com.tianv.updis.AppException;
import com.tianv.updis.Constant;
import com.tianv.updis.R;
import com.tianv.updis.model.ActiveTaskModel;
import com.tianv.updis.model.ProjectModel;
import com.tianv.updis.task.ActiveTask;
import com.tianv.updis.task.ReviewActiveTask;
import com.tianv.updis.task.TaskCallBack;

/**
 * Created by lm3515 on 14-1-22.
 */
public class ActiveTaskActivity extends Activity implements OnClickListener, IMessageDialogListener {
	// private TextView mProjectNameTv;
	private int AUDIT_CONFIRM_1 = 10011;
	private int AUDIT_CONFIRM_2 = 10012;
	private int AUDIT_CONFIRM_3 = 10013;
	/**
	 * 页面弹出对话框
	 */
	protected MessageDialog mDialog = null;
	private TextView state;// 下达单状态, 不需要前面的标签
	private TextView partner;// 甲方
	private TextView partnerType;// 甲方类型
	private TextView customerContact;// 甲方联系人
	private TextView guimo;// 规模
	private TextView yaoQiuXingChengWenJian;// 顾客要求形成文件否
	private TextView shiFouTouBiao;// 是否投标
	private TextView touBiaoLeiBie;// 投标类别
	private TextView expressRequirement;
	private TextView yinHanYaoQiu; // 隐含要求
	private TextView diFangFaGui; // 地方法规
	private TextView fuJiaYaoQiu; // 附加要求
	private TextView heTongYiZhi; // 合同一致
	private TextView ziYuanManZu; // 资源满足
	private TextView sheBeiManZu; // 设备满足
	private TextView gongQiManZu; // 工期满足
	private TextView sheJiFeiManZu; // 设计费满足
	private TextView shiFouWaiBao; // 是否外包 0:否; 1:是
	private TextView shiZhengPeiTao; // 市政配套 0:否; 1:是
	private TextView duoFangHeTong; // 多方合同 0:否; 1:是
	private TextView directorReviewer; // 评审人评审时间
	private TextView directorReviewerApplyTime; // 评审时间
	private String showButton; // String 0: 不显示所长审批按钮; 1:显示

	private TextView pingShenFangShi;
	private TextView yinFaCuoShi;
	private TextView renWuYaoQiu;
	private TextView chengJieBuMen;
	private TextView jingYingShiReviewer;
	private TextView jingYingShiReviewApplyTime;
	private TextView projectCategory;
	private TextView guanLiJiBie;
	private TextView projectLead;
	private TextView zhuGuanZongShi;
	private TextView zongShiShiReviewer;
	private TextView zongShiShiReviewApplyTime;
	// private TextView showProjectLeadReviewAndRejectButton;
	private TextView projectLeadReviewer;
	private TextView projectLeadReviewApplyTime;

	private RelativeLayout greaterThan_3_1, greaterThan_3_2, greaterThan_4_1, greaterThan_4_2, greaterThan_4_3, greaterThan_4_4, greaterThan_4_5, greaterThan_4_6, greaterThan_5_1,
			greaterThan_5_2, greaterThan_5_3, greaterThan_5_4, greaterThan_5_5, greaterThan_5_6, greaterThan_6_1, greaterThan_6_2;//, projectBeginButtonLayout;
	private LinearLayout rejectButtonLayout,zongShiReviewButtonLayout;
	private ActiveTask activeTask;
	private ReviewActiveTask reviewActiveTask;
	private ProgressDialog mProgressDialog;
	private Button suozhangAudit, rejectButton, projectBeginButton,zongShiReviewButton,rejectZongShiReviewButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_info);
		findViewById();
		initView();
	}
	@Override
    protected void onResume() {
        super.onResume();
        initView();
    }
    /*public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }*/
	private void initView() {
		// String taskId =
		// (String)getIntent().getSerializableExtra(Constant.EXTRA_ACTIVE_TASK);
		showProgressDialog();
		ProjectModel pm = (ProjectModel) getIntent().getSerializableExtra(Constant.EXTRA_PROJECTMODEL);
		if (pm != null) {
			activeTask = new ActiveTask(ActiveTaskActivity.this, getResourceListTask(), pm.getActiveTaskId());
			activeTask.execute();
		}
		
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
					reviewActiveTask = new ReviewActiveTask(ActiveTaskActivity.this, getProjectRejectActiveTaskResult(), urlParam);
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
			case R.id.suozhangAudit:
				mDialog.showConfirm(AUDIT_CONFIRM_1, getString(R.string.suozhangAudit), getString(R.string.audit_confirm), this);
				break;
			case R.id.rejectButton:
				showEditTextInfo(AUDIT_CONFIRM_2, ActiveTaskActivity.this, getString(R.string.rejectButton), this);
				break;
			case R.id.projectBeginButton:
				mDialog.showConfirm(AUDIT_CONFIRM_3, getString(R.string.projectBeginButton), getString(R.string.projectBegin_confirm), this);
				break;
			case R.id.zongShiReviewButton:
				Intent intent = new Intent(ActiveTaskActivity.this, ZongShiActiveTaskActivity.class);
				ProjectModel pm = (ProjectModel) getIntent().getSerializableExtra(Constant.EXTRA_PROJECTMODEL);
				intent.putExtra(Constant.EXTRA_PROJECTMODEL, pm);
                intent.putExtra("projectLead", projectLead.getText());
				startActivityForResult(intent, 11);
				break;
			case R.id.rejectZongShiReviewButton:
				
				showProgressDialog();
				pm = (ProjectModel) getIntent().getSerializableExtra(Constant.EXTRA_PROJECTMODEL);
				String urlParam = Constant.INTERFACE_REVIEW_ACTIVETASK + 	"zongShiRejectActiveTask?id=" + pm.getActiveTaskId() ;
				//UIUtilities.showToast(ZongShiActiveTaskActivity.this,urlParam);
				reviewActiveTask = new ReviewActiveTask(ActiveTaskActivity.this, getProjectBeginActiveTaskResult("打回申请单"), urlParam);
				reviewActiveTask.execute();
				break;
		}
		
	}
	
	private TaskCallBack<Void, String> getProjectBeginActiveTaskResult(final String type) {
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
				MessageDialog mDialog = new MessageDialog(ActiveTaskActivity.this);
				if ("1".equals(eParam)) {
					initView();
					mDialog.showInfo(type, "提交成功");
				} else {
					mDialog.showInfo(type, "提交失败");
				}
			}
		};
		return taskCallBask;
	}
	@Override
	public void onDialogClickOk(int requestCode) {
		// TODO Auto-generated method stub
		if (requestCode == AUDIT_CONFIRM_1) {// 所长审核
			showProgressDialog();
			ProjectModel pm = (ProjectModel) getIntent().getSerializableExtra(Constant.EXTRA_PROJECTMODEL);
			String urlParam = Constant.INTERFACE_REVIEW_ACTIVETASK + "reviewActiveTask?id=" + pm.getActiveTaskId();
			reviewActiveTask = new ReviewActiveTask(ActiveTaskActivity.this, getReviewActiveTaskResult(), urlParam);
			reviewActiveTask.execute();
		} else if (requestCode == AUDIT_CONFIRM_3) {// 项目启动
			showProgressDialog();
			ProjectModel pm = (ProjectModel) getIntent().getSerializableExtra(Constant.EXTRA_PROJECTMODEL);
			String urlParam = Constant.INTERFACE_REVIEW_ACTIVETASK + "projectLeadReviewActiveTask?id=" + pm.getActiveTaskId();
			reviewActiveTask = new ReviewActiveTask(ActiveTaskActivity.this, getProjectBeginActiveTaskResult(), urlParam);
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
				MessageDialog mDialog = new MessageDialog(ActiveTaskActivity.this);
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
				MessageDialog mDialog = new MessageDialog(ActiveTaskActivity.this);
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
				MessageDialog mDialog = new MessageDialog(ActiveTaskActivity.this);
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

	private TaskCallBack<Void, ActiveTaskModel> getResourceListTask() {
		TaskCallBack<Void, ActiveTaskModel> taskCallBask = new TaskCallBack<Void, ActiveTaskModel>() {
			@Override
			public void beforeDoingTask() {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
			}

			@Override
			public void doingTask() {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
			}

			@Override
			public void onCancel() {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
			}

			@Override
			public void doingProgress(Void... fParam) {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
			}

			@Override
			public void endTask(ActiveTaskModel eParam, AppException appException) {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
				if (eParam != null) {
					ProjectModel pm = (ProjectModel) getIntent().getSerializableExtra(Constant.EXTRA_PROJECTMODEL);
					// mProjectNameTv.setText(getFilterString(pm.getProjectName()));
					state.setText(eParam.getState());
					partner.setText(eParam.getPartner());
					partnerType.setText(eParam.getPartnerType());
					customerContact.setText(eParam.getCustomerContact());
					guimo.setText(getNotBlank(eParam.getGuimo()));
					yaoQiuXingChengWenJian.setText(getNotBlank(eParam.getYaoQiuXingChengWenJian()));
					shiFouTouBiao.setText(getNotBlankBoolean(eParam.getShiFouTouBiao()));
					touBiaoLeiBie.setText(getNotBlank(eParam.getTouBiaoLeiBie()));
					expressRequirement.setText(getNotBlank(eParam.getExpressRequirement()));
					yinHanYaoQiu.setText(getNotBlank(eParam.getYinHanYaoQiu()));
					diFangFaGui.setText(getNotBlank(eParam.getDiFangFaGui()));
					fuJiaYaoQiu.setText(getNotBlank(eParam.getFuJiaYaoQiu()));
					heTongYiZhi.setText(getNotBlank(eParam.getHeTongYiZhi()));
					ziYuanManZu.setText(getNotBlank(eParam.getZiYuanManZu()));
					sheBeiManZu.setText(getNotBlank(eParam.getSheBeiManZu()));
					gongQiManZu.setText(getNotBlank(eParam.getGongQiManZu()));
					sheJiFeiManZu.setText(getNotBlank(eParam.getSheJiFeiManZu()));
					shiFouWaiBao.setText(getNotBlankBoolean(eParam.getShiFouWaiBao()));// 是或否
					shiZhengPeiTao.setText(getNotBlankBoolean(eParam.getShiZhengPeiTao()));// 是
																							// 否
					duoFangHeTong.setText(getNotBlankBoolean(eParam.getDuoFangHeTong()));// 是
																							// 否

					if ("1".equals(eParam.getShowButton())) {
						suozhangAudit.setVisibility(View.VISIBLE);
					}
					else{
						suozhangAudit.setVisibility(View.GONE);
					}
					if ("1".equals(eParam.getShowProjectLeadReviewAndRejectButton())) {
						rejectButtonLayout.setVisibility(View.VISIBLE);
						//projectBeginButtonLayout.setVisibility(View.GONE);
					}
					else{
						rejectButtonLayout.setVisibility(View.GONE);
					}

					// ------------------------ >=3
					if (Integer.parseInt(eParam.getStateId()) >= 3) {
						greaterThan_3_1.setVisibility(View.VISIBLE);
						greaterThan_3_2.setVisibility(View.VISIBLE);
						directorReviewer.setText(getNotBlank(eParam.getDirectorReviewer()));
						directorReviewerApplyTime.setText(getNotBlank(eParam.getDirectorReviewerApplyTime()));

					}
					
					// ------------------------ >=4
					if (Integer.parseInt(eParam.getStateId()) >= 4) {
						greaterThan_4_1.setVisibility(View.VISIBLE);
						greaterThan_4_2.setVisibility(View.VISIBLE);
						greaterThan_4_3.setVisibility(View.VISIBLE);
						greaterThan_4_4.setVisibility(View.VISIBLE);
						greaterThan_4_5.setVisibility(View.VISIBLE);
						greaterThan_4_6.setVisibility(View.VISIBLE);
						pingShenFangShi.setText(getNotBlank(eParam.getPingShenFangShi()));
						yinFaCuoShi.setText(getNotBlank(eParam.getYinFaCuoShi()));
						renWuYaoQiu.setText(getNotBlank(eParam.getRenWuYaoQiu()));
						chengJieBuMen.setText(getNotBlank(eParam.getChengJieBuMen()));
						jingYingShiReviewer.setText(getNotBlank(eParam.getJingYingShiReviewer()));
						jingYingShiReviewApplyTime.setText(getNotBlank(eParam.getJingYingShiReviewApplyTime()));

					}

					// ------------------------ >=5
					if (Integer.parseInt(eParam.getStateId()) >= 5) {
						greaterThan_5_1.setVisibility(View.VISIBLE);
						greaterThan_5_2.setVisibility(View.VISIBLE);
						greaterThan_5_3.setVisibility(View.VISIBLE);
						greaterThan_5_4.setVisibility(View.VISIBLE);
						greaterThan_5_5.setVisibility(View.VISIBLE);
						greaterThan_5_6.setVisibility(View.VISIBLE);
						projectCategory.setText(getNotBlank(eParam.getProjectCategory()));
						guanLiJiBie.setText(getNotBlank(eParam.getGuanLiJiBie()));
						projectLead.setText(getNotBlank(eParam.getProjectLead()));
						zhuGuanZongShi.setText(getNotBlank(eParam.getZhuGuanZongShi()));
						zongShiShiReviewer.setText(getNotBlank(eParam.getZongShiShiReviewer()));
						zongShiShiReviewApplyTime.setText(getNotBlank(eParam.getZongShiShiReviewApplyTime()));

					}
					try {
						projectLead.setText(getNotBlank(eParam.getProjectLead()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// ------------------------ >=6
					if (Integer.parseInt(eParam.getStateId()) >= 6) {
						greaterThan_6_1.setVisibility(View.VISIBLE);
						greaterThan_6_2.setVisibility(View.VISIBLE);
						projectLeadReviewer.setText(getNotBlank(eParam.getProjectLeadReviewer()));
						projectLeadReviewApplyTime.setText(getNotBlank(eParam.getProjectLeadReviewApplyTime()));

					}
					if("1".equals(eParam.getShowZongShiReviewButton())){
						zongShiReviewButtonLayout.setVisibility(View.VISIBLE);
					}
					else{
						zongShiReviewButtonLayout.setVisibility(View.GONE);
					}
					// showButton; // String 0: 不显示所长审批按钮; 1:显示
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
		// To change body of created methods use File | Settings | File
		// Templates.
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
		// mProjectNameTv = (TextView) findViewById(R.id.project_name);

		state = (TextView) findViewById(R.id.state);
		partner = (TextView) findViewById(R.id.partner);
		partnerType = (TextView) findViewById(R.id.partnerType);// 甲方类型
		customerContact = (TextView) findViewById(R.id.customerContact);// 甲方联系人
		guimo = (TextView) findViewById(R.id.guimo);// 规模
		yaoQiuXingChengWenJian = (TextView) findViewById(R.id.yaoQiuXingChengWenJian);// 顾客要求形成文件否
		shiFouTouBiao = (TextView) findViewById(R.id.shiFouTouBiao);// 是否投标
		touBiaoLeiBie = (TextView) findViewById(R.id.touBiaoLeiBie);// 投标类别
		expressRequirement = (TextView) findViewById(R.id.expressRequirement);
		yinHanYaoQiu = (TextView) findViewById(R.id.yinHanYaoQiu); // 隐含要求
		diFangFaGui = (TextView) findViewById(R.id.diFangFaGui); // 地方法规
		fuJiaYaoQiu = (TextView) findViewById(R.id.fuJiaYaoQiu); // 附加要求
		heTongYiZhi = (TextView) findViewById(R.id.heTongYiZhi); // 合同一致
		ziYuanManZu = (TextView) findViewById(R.id.ziYuanManZu); // 资源满足
		sheBeiManZu = (TextView) findViewById(R.id.sheBeiManZu); // 设备满足
		gongQiManZu = (TextView) findViewById(R.id.gongQiManZu); // 工期满足
		sheJiFeiManZu = (TextView) findViewById(R.id.sheJiFeiManZu); // 设计费满足
		shiFouWaiBao = (TextView) findViewById(R.id.shiFouWaiBao); // 是否外包 0:否
		shiZhengPeiTao = (TextView) findViewById(R.id.shiZhengPeiTao); // 市政配套
																		// 0:否
		duoFangHeTong = (TextView) findViewById(R.id.duoFangHeTong); // 多方合同 0:否

		suozhangAudit = (Button) findViewById(R.id.suozhangAudit);
		rejectButton = (Button) findViewById(R.id.rejectButton);
		projectBeginButton = (Button) findViewById(R.id.projectBeginButton);
		if (mDialog == null) {
			mDialog = new MessageDialog(this);
		}

		directorReviewer = (TextView) findViewById(R.id.directorReviewer); // 评审人评审时间
		directorReviewerApplyTime = (TextView) findViewById(R.id.directorReviewerApplyTime); // 评审时间

		pingShenFangShi = (TextView) findViewById(R.id.pingShenFangShi);
		yinFaCuoShi = (TextView) findViewById(R.id.yinFaCuoShi);
		renWuYaoQiu = (TextView) findViewById(R.id.renWuYaoQiu);
		chengJieBuMen = (TextView) findViewById(R.id.chengJieBuMen);
		jingYingShiReviewer = (TextView) findViewById(R.id.jingYingShiReviewer);
		jingYingShiReviewApplyTime = (TextView) findViewById(R.id.jingYingShiReviewApplyTime);

		projectCategory = (TextView) findViewById(R.id.projectCategory);
		guanLiJiBie = (TextView) findViewById(R.id.guanLiJiBie);
		projectLead = (TextView) findViewById(R.id.projectLead);
		zhuGuanZongShi = (TextView) findViewById(R.id.zhuGuanZongShi);
		zongShiShiReviewer = (TextView) findViewById(R.id.zongShiShiReviewer);
		zongShiShiReviewApplyTime = (TextView) findViewById(R.id.zongShiShiReviewApplyTime);

		projectLeadReviewer = (TextView) findViewById(R.id.projectLeadReviewer);
		projectLeadReviewApplyTime = (TextView) findViewById(R.id.projectLeadReviewApplyTime);

		greaterThan_3_1 = (RelativeLayout) findViewById(R.id.greaterThan_3_1);
		greaterThan_3_2 = (RelativeLayout) findViewById(R.id.greaterThan_3_2);
		greaterThan_4_1 = (RelativeLayout) findViewById(R.id.greaterThan_4_1);
		greaterThan_4_2 = (RelativeLayout) findViewById(R.id.greaterThan_4_2);
		greaterThan_4_3 = (RelativeLayout) findViewById(R.id.greaterThan_4_3);
		greaterThan_4_4 = (RelativeLayout) findViewById(R.id.greaterThan_4_4);
		greaterThan_4_5 = (RelativeLayout) findViewById(R.id.greaterThan_4_5);
		greaterThan_4_6 = (RelativeLayout) findViewById(R.id.greaterThan_4_6);

		greaterThan_5_1 = (RelativeLayout) findViewById(R.id.greaterThan_5_1);
		greaterThan_5_2 = (RelativeLayout) findViewById(R.id.greaterThan_5_2);
		greaterThan_5_3 = (RelativeLayout) findViewById(R.id.greaterThan_5_3);
		greaterThan_5_4 = (RelativeLayout) findViewById(R.id.greaterThan_5_4);
		greaterThan_5_5 = (RelativeLayout) findViewById(R.id.greaterThan_5_5);
		greaterThan_5_6 = (RelativeLayout) findViewById(R.id.greaterThan_5_6);

		greaterThan_6_1 = (RelativeLayout) findViewById(R.id.greaterThan_6_1);
		greaterThan_6_2 = (RelativeLayout) findViewById(R.id.greaterThan_6_2);

		rejectButtonLayout = (LinearLayout) findViewById(R.id.rejectButtonLayout);
		//projectBeginButtonLayout = (RelativeLayout) findViewById(R.id.projectBeginButtonLayout);
		zongShiReviewButtonLayout = (LinearLayout) findViewById(R.id.zongShiReviewButtonLayout);
		
		zongShiReviewButton = (Button) findViewById(R.id.zongShiReviewButton);//总师室审批
		rejectZongShiReviewButton = (Button) findViewById(R.id.rejectZongShiReviewButton);
		suozhangAudit.setOnClickListener(this);
		rejectButton.setOnClickListener(this);
		projectBeginButton.setOnClickListener(this);
		zongShiReviewButton.setOnClickListener(this);
		rejectZongShiReviewButton.setOnClickListener(this);
	}

}
