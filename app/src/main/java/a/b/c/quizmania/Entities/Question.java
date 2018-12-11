package a.b.c.quizmania.Entities;

import java.util.List;

public class Question {
    private int response_code;
    private List<Results> results;

    public Question() {
    }

    public Question(int responseCode, List<Results> results) {
        this.response_code = responseCode;
        this.results = results;
    }

    public int getResponseCode() {
        return response_code;
    }

    public void setResponseCode(int responseCode) {
        this.response_code = responseCode;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
