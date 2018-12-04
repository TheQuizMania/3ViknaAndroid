package a.b.c.quizmania.Entities;

public class Question {
    private int responseCode;
    private Results[] results;

    public Question(int responseCode, Results[] results) {
        this.responseCode = responseCode;
        this.results = results;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }
}
