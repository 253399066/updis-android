package com.tianv.updis.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
	private ImageButton buttonSearch;
	private ImageView buttonClose;
	private AutoCompleteTextView inputSearchQuery;
	private ProgressDialog mProgressDialog;
	private ProjectLeaderSearchTask projectLeaderSearchTask;
	private TextView searchResultView;
	private List searchResultList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_projet_leader);
		findViewById();
		initView();
	}

	private void findViewById() {
		searchResultView  = (TextView) findViewById(R.id.search_sesult_view);
		buttonSearch  = (ImageButton) findViewById(R.id.button_search);
		buttonClose  = (ImageView) findViewById(R.id.button_close);
		listView  = (ListView) findViewById(R.id.projectLeaderList);
		inputSearchQuery = (AutoCompleteTextView) findViewById(R.id.input_search_query);
		
		buttonSearch.setOnClickListener(this);
		buttonClose.setOnClickListener(this);
		
		listView.setOnItemClickListener(new ClickEvent());
			
	}
	private class ClickEvent implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
			ListView listView = (ListView)arg0;
			SpinnerData spinerData = (SpinnerData) listView.getItemAtPosition(position); 
			String searchResult = searchResultView.getText().toString();
			if(!searchResult.equals("")){
				searchResult += ",";
			}
			searchResult += spinerData.getText();
			if(searchResultList != null){
				for(int i = 0; i < searchResultList.size(); i++){
					SpinnerData temp = (SpinnerData)searchResultList.get(i);
					if(temp.getValue().equals(spinerData.getValue())){
						searchResultList.remove(i);
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchProjectLeaderActive.this, android.R.layout.simple_expandable_list_item_1, searchResultList);
						listView.setAdapter(adapter);
					}
				}
			}
			searchResultView.setText(searchResult);
		}
	}
	private void initView() {

		// setContentView(listView);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.button_search:
			showProgressDialog();
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
			break;
		case R.id.button_close:
			inputSearchQuery.setText("");
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
