package a.b.c.quizmania.Jobs;

import android.os.AsyncTask;
import android.os.SystemClock;

public class BackroundJob extends AsyncTask<Integer, Integer, Integer> {
    // To call functions in Question fragment
    private UiCallback<Integer> callback;

    public BackroundJob(UiCallback callback){
        this.callback = callback;
    }

    // Runs while the thread is alive
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


    // Runs when the thead is initiated
    @Override
    protected void onPreExecute() {
        callback.onPreExecute();
    }

    // Runs when publishProgress(Integer) is called
    @Override
    protected void onProgressUpdate(Integer... values) {
        callback.onProgressUpdate(values);
    }


    // Runs when the thread stoppes
    @Override
    protected void onPostExecute(Integer integer) {
        callback.onPostExecute(integer);
        callback = null;
    }

    // Runs when the thread is cancelled
    @Override
    protected void onCancelled() {
        callback.onCancelled();
        callback = null;
    }
}
