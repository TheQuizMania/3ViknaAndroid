package a.b.c.quizmania.Jobs;

import android.os.AsyncTask;
import android.os.SystemClock;

public class BackroundJob extends AsyncTask<Integer, Integer, Integer> {
    private UiCallback<Integer> callback;

    public BackroundJob(UiCallback callback){
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 1000 * 20;
        long currTime;
        while(!this.isCancelled() && endTime > (currTime = System.currentTimeMillis())) {
            SystemClock.sleep(500);
            publishProgress(integers[0]);
        }

        return integers[0];
    }


    @Override
    protected void onPreExecute() {
        callback.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        callback.onProgressUpdate(values);
    }


    @Override
    protected void onPostExecute(Integer integer) {
        callback.onPostExecute(integer);
        callback = null;
    }

    @Override
    protected void onCancelled() {
        callback.onCancelled();
        callback = null;
    }
}
