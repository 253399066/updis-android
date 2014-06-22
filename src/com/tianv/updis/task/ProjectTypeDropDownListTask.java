package com.tianv.updis.task;

import java.util.List;

import android.content.Context;

import com.melvin.android.base.task.BaseTask;
import com.tianv.updis.AppException;
import com.tianv.updis.network.CollectResource;
import com.uucun.android.logger.Logger;
import com.uucun.android.utils.io.IOUtils;

/**
 * Created by lm3515 on 14-1-22.
 */
public class ProjectTypeDropDownListTask extends BaseTask<Void, Void, List> {

	private String taskId;
	public ProjectTypeDropDownListTask(Context context, TaskCallBack<Void, List> taskCallBack,String taskId) {
        super(taskCallBack, context);
        this.taskId = taskId;
    }

    public void onPreExecute() {
        super.onPreExecute();
    }

    public List doInBackground(Void... params) {
        super.doInBackground(params);
        
        try {
        	return CollectResource.getInstance(context).fetchProjectTypeListData();
        } catch (AppException e) {
            appException = e;
            String s = IOUtils.exception2String(e);
            Logger.w("fetchActiveTaskData", "" + s);
        }
        return null;
    }

    
    public void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void onCancelled() {
        super.onCancelled();
    }

    public void onPostExecute(List result) {
        super.onPostExecute(result);
    }
}
