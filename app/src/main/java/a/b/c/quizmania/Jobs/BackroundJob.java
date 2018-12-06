package a.b.c.quizmania.Jobs;

import android.os.AsyncTask;
import android.os.SystemClock;

public class BackroundJob extends AsyncTask<Integer, Integer, Void> {
    private UiCallback callback;

    public BackroundJob(UiCallback callback){
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Integer... integers) {

        publishProgress(integers[0]);
        SystemClock.sleep(1500);
        return null;
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
    protected void onPostExecute(Void aVoid) {
        callback.onPostExecute();
    }

    @Override
    protected void onCancelled() {
        callback.onCancelled();
    }
}
