package a.b.c.quizmania.Jobs;

/**
 * Callback for the background job
 * @param <Integer>
 */
public interface UiCallback<Integer> {
    void onPreExecute();
    void onProgressUpdate(Integer... values);
    void onPostExecute(Integer integer);
    void onCancelled();
}
