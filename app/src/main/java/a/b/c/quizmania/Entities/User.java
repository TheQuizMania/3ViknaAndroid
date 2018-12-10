package a.b.c.quizmania.Entities;

import java.util.List;

public class User {
    private String userName;
    private List<Score> scores;
    private int wins;
    private int losses;

    public User() {
    }

    public User(String userName, List<Score> scores, int wins, int losses) {
        this.userName = userName;
        this.scores = scores;
        this.wins = wins;
        this.losses = losses;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}
