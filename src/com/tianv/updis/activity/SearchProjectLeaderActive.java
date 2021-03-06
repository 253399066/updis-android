package com.tianv.updis.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.melvin.android.base.common.ui.IMessageDialogListener;
import com.tianv.updis.AppException;
import com.tianv.updis.Constant;
import com.tianv.updis.R;
import com.tianv.updis.model.SpinnerData;
import com.tianv.updis.task.ProjectLeaderSearchTask;
import com.tianv.updis.task.TaskCallBack;

public class SearchProjectLeaderActive extends Activity implements OnClickListener, IMessageDialogListener {

	private ListView listView;
	private ImageView buttonClose;
	private AutoCompleteTextView inputSearchQuery;
	private ProgressDialog mProgressDialog;
	private ProjectLeaderSearchTask projectLeaderSearchTask;
	private TextView searchResultView;
	private String projectLeadIds = "";
	private List searchResultList;
	private Button searchOk, searchCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_projet_leader);
		findViewById();
		initView();
	}

	private void findViewById() {
		searchResultView = (TextView) findViewById(R.id.search_sesult_view);
		buttonClose = (ImageView) findViewById(R.id.button_close);
		listView = (ListView) findViewById(R.id.projectLeaderList);
		inputSearchQuery = (AutoCompleteTextView) findViewById(R.id.input_search_query);
		searchOk = (Button) findViewById(R.id.search_ok);
		searchCancel = (Button) findViewById(R.id.search_cancel);

		// buttonSearch.setOnClickListener(this);
		inputSearchQuery.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				//showProgressDialog();
				String keyWord = "";
				try {
					keyWord = URLEncoder.encode(inputSearchQuery.getText().toString(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String urlParam = Constant.INTERFACE_REVIEW_ACTIVETASK + "searchProjectLeader?name=" + keyWord;
				projectLeaderSearchTask = new ProjectLeaderSearchTask(SearchProjectLeaderActive.this, getProjectLeaderSearchTaskResult(), urlParam);
				projectLeaderSearchTask.execute();
			}

		});

		buttonClose.setOnClickListener(this);
		searchOk.setOnClickListener(this);
		searchCancel.setOnClickListener(this);
		listView.setOnItemClickListener(new ClickEvent());

	}

	private class ClickEvent implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
			ListView listView = (ListView) arg0;
			SpinnerData spinerData = (SpinnerData) listView.getItemAtPosition(position);
			String searchResult = searchResultView.getText().toString();
			String projectLeadIdsTemp = projectLeadIds;
			if (!searchResult.equals("")) {
				searchResult += ",";
				projectLeadIdsTemp += ",";
			}
			searchResult += spinerData.getText();
			projectLeadIdsTemp += spinerData.getValue();
			if (searchResultList != null) {
				for (int i = 0; i < searchResultList.size(); i++) {
					SpinnerData temp = (SpinnerData) searchResultList.get(i);
					if (temp.getValue().equals(spinerData.getValue())) {
						searchResultList.remove(i);
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchProjectLeaderActive.this, android.R.layout.simple_expandable_list_item_1, searchResultList);
						listView.setAdapter(adapter);
					}
				}
			}
			projectLeadIds = projectLeadIdsTemp;
			searchResultView.setText(searchResult);
		}
	}

	private void initView() {

		// setContentView(listView);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.button_close:
			inputSearchQuery.setText("");
			break;
		case R.id.search_ok:
			if (!"".equals(searchResultView.getText().toString())) {
				Intent intent = new Intent();
				intent.putExtra("searchProjectLeader", searchResultView.getText().toString());
				intent.putExtra("projectLeadIds", projectLeadIds);
				setResult(RESULT_OK, intent);
			}
			finish();
			break;
		case R.id.search_cancel:
			this.finish();
			break;
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

	@Override
	public void onDialogClickOk(int requestCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDialogClickCancel(int requestCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDialogClickClose(int requestCode) {
		// TODO Auto-generated method stub

	}

	private TaskCallBack<Void, List> getProjectLeaderSearchTaskResult() {
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
			}

			@Override
			public void endTask(List eParam, AppException appException) {
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					mProgressDialog = null;
				}
				if (eParam != null && eParam.size() > 0) {
					searchResultList = eParam;
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchProjectLeaderActive.this, android.R.layout.simple_expandable_list_item_1, eParam);
					listView.setAdapter(adapter);
				}

			}
		};
		return taskCallBask;
	}

}
