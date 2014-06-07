
package com.tianv.updis.adapter;

import android.app.Activity;
import com.melvin.android.base.adapter.UUFragmentPageAdapter;
import com.tianv.updis.Constant;
import com.tianv.updis.fragment.CommonListFragment;
import com.uucun.android.sharedstore.SharedStore;

/**
 * @author Melvin
 * @version V1.0
 * @ClassName: CategoryFragmentAdapter.java
 * @Description: TODO
 * @Date 2013-3-24 下午1:36:30
 */
public class CategoryFragmentAdapter extends UUFragmentPageAdapter {
    public CategoryFragmentAdapter(Activity activity, String[] content,String canViewTender,String isSpecailUser) {
        super(activity, content);
        add(new CommonListFragment(activity, Constant.VIEW_HOME_NOTICE));
        if ("1".equals(canViewTender)) {
        	add(new CommonListFragment(activity, Constant.VIEW_HOME_BIDDING));
        }
        add(new CommonListFragment(activity, Constant.VIEW_HOME_TALK));
        add(new CommonListFragment(activity, Constant.VIEW_HOME_AMATEUR));

        if ("1".equals(isSpecailUser)) {
            add(new CommonListFragment(activity, Constant.VIEW_PROJECT));
        }
       
    }
}
