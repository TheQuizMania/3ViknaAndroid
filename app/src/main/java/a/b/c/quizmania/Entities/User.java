package a.b.c.quizmania.Entities;

public class User {
    private String userName;
    private Score[] scores;
    private int wins;
    private int losses;

    public User() {
    }

    public User(String userName, Score[] scores, int wins, int losses) {
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

    public Score[] getScores() {
        return scores;
    }

    public void setScores(Score[] scores) {
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
