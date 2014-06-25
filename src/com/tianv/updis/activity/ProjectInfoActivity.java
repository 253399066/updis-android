package com.tianv.updis.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tianv.updis.Constant;
import com.tianv.updis.R;
import com.tianv.updis.model.ProjectModel;
import com.tianv.updis.model.UIUtilities;

/**
 * Created by lm3515 on 14-1-22.
 */
public class ProjectInfoActivity extends Activity {
	private TextView mProjectNameTv;
	private TextView mProjectIdTv;
	private TextView mProjectNumTv;
	private TextView mPartyNameTv;
	private TextView mDesignDepartmentTv;
	private TextView mProjectLeadersTv;
	private TextView mProjectScaleTv;
	private ProjectModel pm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_info);
		findViewById();
		initView();
	}

	private void initView() {
		pm = (ProjectModel) getIntent().getSerializableExtra(Constant.EXTRA_PROJECTMODEL);
		if (pm != null) {
			mProjectIdTv.setText(getFilterString(pm.getProjectId()));
			mProjectNameTv.setText(getFilterString(pm.getProjectName()));
			mProjectNumTv.setText(getFilterString(pm.getProjectNumber()));
			mDesignDepartmentTv.setText(getFilterString(pm.getDesignDepartment()));
			mProjectScaleTv.setText(getFilterString(pm.getProjectScale()));
			mPartyNameTv.setText(getFilterString(pm.getPartyAName()));
			if (!TextUtils.isEmpty(pm.getProjectLeaders())) {
				mProjectLeadersTv.setText(pm.getProjectLeaders());
			} else {
				mProjectLeadersTv.setText("-");
			}
		}
	}

	public String getFilterString(String str) {
		if (TextUtils.isEmpty(str)) {
			return "-";
		} else {
			return str;
		}
	}

	private void findViewById() {
		mProjectNameTv = (TextView) findViewById(R.id.project_name);
		mProjectIdTv = (TextView) findViewById(R.id.project_id);
		mProjectNumTv = (TextView) findViewById(R.id.project_num);
		mPartyNameTv = (TextView) findViewById(R.id.party_name);
		mDesignDepartmentTv = (TextView) findViewById(R.id.designDepartment);
		mProjectLeadersTv = (TextView) findViewById(R.id.projectLeaders);
		mProjectScaleTv = (TextView) findViewById(R.id.projectScale);
	}

	
	public void btn_shake(View v) {
		String activeTaskId = pm.getActiveTaskId();
		String imported = pm.getImported();
		if("false".equals(imported) && activeTaskId!= null && !activeTaskId.equals("") ){
			startActivityForResult(new Intent(ProjectInfoActivity.this, ActiveTaskActivity.class).putExtra(
					Constant.EXTRA_PROJECTMODEL, pm), 11);
		}
		else{
			UIUtilities.showCustomToast(getApplicationContext(),
                    R.string.no_active_task);
		}
	}
	
}
