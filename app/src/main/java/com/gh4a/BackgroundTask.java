package com.gh4a;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;

public abstract class BackgroundTask<T> extends AsyncTask<Void, Void, T> {
    private final Context mContext;
    private Exception mException;

    public BackgroundTask(Context context) {
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    public void schedule() {
        AsyncTaskCompat.executeParallel(this);
    }

    @Override
    protected T doInBackground(Void... params) {
        try {
            return run();
        } catch (Exception e) {
            e.printStackTrace();
            mException = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
        if (mException != null) {
            onError(mException);
        } else {
            onSuccess(result);
        }
    }

    protected abstract T run() throws Exception;

    protected abstract void onSuccess(T result);

    protected void onError(Exception e) { }
}
