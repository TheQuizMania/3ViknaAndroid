package a.b.c.quizmania.Jobs;

public interface UiCallback<Integer> {
    void onPreExecute();
    void onProgressUpdate(Integer... values);
    void onPostExecute(Integer integer);
    void onCancelled();
}
