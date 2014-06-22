package com.tianv.updis.task;

import android.content.Context;

import com.melvin.android.base.task.BaseTask;
import com.tianv.updis.AppException;
import com.tianv.updis.model.ActiveTaskModel;
import com.tianv.updis.network.CollectResource;
import com.uucun.android.logger.Logger;
import com.uucun.android.utils.io.IOUtils;

/**
 * Created by lm3515 on 14-1-22.
 */
public class ActiveTask extends BaseTask<Void, Void, ActiveTaskModel> {

	private String taskId;
	public ActiveTask(Context context, TaskCallBack<Void, ActiveTaskModel> taskCallBack,String taskId) {
        super(taskCallBack, context);
        this.taskId = taskId;
    }

    public void onPreExecute() {
        super.onPreExecute();
    }

    public ActiveTaskModel doInBackground(Void... params) {
        super.doInBackground(params);
        ActiveTaskModel atm = null;
        try {
        	atm = CollectResource.getInstance(context).fetchActiveTaskData(this.taskId);
        } catch (AppException e) {
            appException = e;
            String s = IOUtils.exception2String(e);
            Logger.w("fetchActiveTaskData", "" + s);
        }
        return atm;
    }

    
    public void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void onCancelled() {
        super.onCancelled();
    }

    public void onPostExecute(ActiveTaskModel result) {
        super.onPostExecute(result);
    }
}
