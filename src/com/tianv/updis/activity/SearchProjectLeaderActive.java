package com.tianv.updis.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.melvin.android.base.common.ui.IMessageDialogListener;
import com.tianv.updis.R;

public class SearchProjectLeaderActive extends Activity implements OnClickListener, IMessageDialogListener {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_projet_leader);
		findViewById();
		initView();
	}

	private void findViewById() {

	}

	private void initView() {
		List<String> data = new ArrayList<String>();
		data.add("测试数据1");
		data.add("测试数据2");
		data.add("测试数据3");
		data.add("测试数据4");
		data.add("测试数据6");
		data.add("测试数据7");
		data.add("测试数据8");
		data.add("测试数据9");
		data.add("测试数据10");
		data.add("测试数据11");
		data.add("测试数据12");
		listView  = (ListView) findViewById(R.id.projectLeaderList);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data));
		//setContentView(listView);
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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
