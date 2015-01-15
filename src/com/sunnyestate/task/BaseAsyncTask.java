package com.sunnyestate.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.Toast;

import com.sunnyestate.enums.RetError;
import com.sunnyestate.utils.ToastUtil;
import com.sunnyestate.utils.Utils;

public abstract class BaseAsyncTask<Params, Progress, Result> extends
		AsyncTask<Params, Progress, RetError> {
	private AbstractTaskPostCallBack<RetError> mCallBack;

	public void setmCallBack(AbstractTaskPostCallBack<RetError> mCallBack) {
		this.mCallBack = mCallBack;
	}

	@SuppressLint("NewApi")
	public void executeParallel(Params... params) {
		if (!Utils.isNetworkAvailable()) {
			onPostExecute(RetError.NETWORK_ERROR);
			return;
		}
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
			super.execute(params);
		} else {
			super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		}
	}

	@Override
	protected void onPostExecute(RetError result) {
		if (result instanceof RetError) {
			if (result != RetError.NONE) {
				ToastUtil.showToast(RetError.toText((RetError) result),
						Toast.LENGTH_SHORT);
			}
		}
		if (mCallBack != null) {
			mCallBack.taskFinish(result);
		}
	}
}
