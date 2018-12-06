package a.b.c.quizmania.Jobs;

import android.os.AsyncTask;
import android.os.SystemClock;

import static a.b.c.quizmania.Fragments.QuestionDisplayFragment.mutex;

public class BackroundJob extends AsyncTask<Integer, Integer, Integer> {
    private UiCallback<Integer> callback;

    public BackroundJob(UiCallback callback){
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {


        publishProgress(integers[0]);
        SystemClock.sleep(5000);

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
