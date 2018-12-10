package a.b.c.quizmania.Entities;

import java.util.List;

public class Question {
    private int responseCode;
    private List<Results> results;

    public Question() {
    }

    public Question(int responseCode, List<Results> results) {
        this.responseCode = responseCode;
        this.results = results;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
