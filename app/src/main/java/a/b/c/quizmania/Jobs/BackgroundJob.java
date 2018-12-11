package a.b.c.quizmania.Jobs;

import android.os.AsyncTask;
import android.os.SystemClock;

public class BackgroundJob extends AsyncTask<Integer, Integer, Integer> {
    // To call functions in Question fragment
    private UiCallback<Integer> callback;

    public BackgroundJob(UiCallback callback){
        this.callback = callback;
    }

    // Runs while the thread is alive
    @Override
    protected Integer doInBackground(Integer... integers) {
        long startTime = System.currentTimeMillis();
        long endTime = (integers[0] == 0) ? startTime + 1000 * 20 : startTime + 1000 * 21;
        long currTime = 0;
        while(!this.isCancelled() && endTime > (currTime = System.currentTimeMillis())) {
            SystemClock.sleep(2);
            publishProgress((int)endTime - (int)currTime);
        }
        long timeToAnswer = currTime - startTime;
        return integers[0];
    }


    // Runs when the thread is initiated
    @Override
    protected void onPreExecute() {
        callback.onPreExecute();
    }

    // Runs when publishProgress(Integer) is called
    @Override
    protected void onProgressUpdate(Integer... values) {
        callback.onProgressUpdate(values);
    }


    // Runs when the thread stops
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
