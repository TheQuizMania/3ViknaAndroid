package a.b.c.quizmania.Jobs;

import android.os.AsyncTask;

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
        publishProgress(integers[0]);
        while(!this.isCancelled() && endTime > (currTime = System.currentTimeMillis())) {

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

//    @Override
//    protected Integer onPostExecute(Integer... integers) {
//        callback.onPostExecute(integers[0]);
//        callback = null;
//        return null;
//    }


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
