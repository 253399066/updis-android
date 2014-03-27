package com.tianv.updis.task;

import android.content.Context;

import com.melvin.android.base.task.BaseTask;
import com.tianv.updis.AppException;
import com.tianv.updis.network.CollectResource;
import com.uucun.android.logger.Logger;
import com.uucun.android.utils.io.IOUtils;

/**
 * Created by lm3515 on 14-1-22.
 */
public class ReviewActiveTask extends BaseTask<Void, Void, String> {

	private String taskId;
	public ReviewActiveTask(Context context, TaskCallBack<Void, String> taskCallBack,String taskId) {
        super(taskCallBack, context);
        this.taskId = taskId;
    }

    public void onPreExecute() {
        super.onPreExecute();
    }

    public String doInBackground(Void... params) {
        super.doInBackground(params);
        String result = null;
        try {
        	result = CollectResource.getInstance(context).reviewActiveTask(this.taskId);
        } catch (AppException e) {
            appException = e;
            String s = IOUtils.exception2String(e);
            Logger.w("fetchActiveTaskData", "" + s);
        }
        return result;
    }

    public void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void onCancelled() {
        super.onCancelled();
    }

    public void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
