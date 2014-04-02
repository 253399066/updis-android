
package com.tianv.updis.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.melvin.android.base.activity.BaseFragmentActivity;
import com.melvin.android.base.common.ui.PageIndicator;
import com.tianv.updis.Constant;
import com.tianv.updis.R;
import com.tianv.updis.adapter.CategoryFragmentAdapter;
import com.uucun.android.sharedstore.SharedStore;

/**
 * @author Melvin
 * @version V1.0
 * @ClassName: CategoryMessageActivity.java
 * @Description: TODO
 * @Date 2013-3-24 下午1:20:48
 */
public class CategoryMessageActivity extends BaseFragmentActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.common_viewpage_layout);
        //String title[] = getResources().getStringArray(R.array.category_tab_text);
        SharedStore sharedStore = new SharedStore(this, null);
        String canViewTender = sharedStore.getString(Constant.UPDIS_STORE_KEY_CANVIEWTENDER, "");
        ArrayList titleArray = new ArrayList();
        
        titleArray.add("通知");
        if ("1".equals(canViewTender)) {
        	titleArray.add("招投标信息");
        }
        titleArray.add("畅所欲言");
        titleArray.add("业余生活");
        String isSpecailUser = sharedStore.getString(Constant.UPDIS_STORE_KEY_ISSPECIALUSER, "0");
        if ("1".equals(isSpecailUser)) {
        	titleArray.add("在谈项目");
        }
        String title[] = new String[titleArray.size()];
        
        for(int i =0; i< titleArray.size(); i++){
        	title[i] = new String();
        	title[i] = (String)titleArray.get(i);
        }
        mAdapter = new CategoryFragmentAdapter(this, title);
        mIndicator = (PageIndicator) findViewById(R.id.indicator);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mPager);
        initPageChange();
    }
    public static String[] remove(String[] array, int index) {  
        if (array == null || array.length == 0) {  
            throw new IllegalArgumentException();  
        }  
        if (index > array.length || index <= 0) {  
            throw new IllegalArgumentException();  
        }  
        String[] dest = new String[array.length - 1];  
        System.arraycopy(array, 0, dest, 0, index - 1);  
        System.arraycopy(array, index, dest, index - 1, array.length - index);  
        return dest;  
    }  
    @Override
    protected void onNewIntent(Intent intent) {
    }
}
