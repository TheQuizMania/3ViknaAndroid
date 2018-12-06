package a.b.c.quizmania.Jobs;

public interface UiCallback {
    void onPreExecute();
    void onProgressUpdate(Integer... values);
    void onPostExecute();
    void onCancelled();
}
