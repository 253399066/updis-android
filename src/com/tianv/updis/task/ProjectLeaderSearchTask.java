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
public class ProjectLeaderSearchTask extends BaseTask<Void, Void, List> {

	private String urlParam;
	public ProjectLeaderSearchTask(Context context, TaskCallBack<Void, List> taskCallBack,String urlParam) {
        super(taskCallBack, context);
        this.urlParam = urlParam;
    }

    public void onPreExecute() {
        super.onPreExecute();
    }

    public List doInBackground(Void... params) {
        super.doInBackground(params);
        List result = null;
        try {
        	result = CollectResource.getInstance(context).fetchProjectLeaderListData(this.urlParam);
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

    public void onPostExecute(List result) {
        super.onPostExecute(result);
    }
}
