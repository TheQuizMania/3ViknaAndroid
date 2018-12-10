package a.b.c.quizmania.Entities;

public class CurrentScore {
    private static Score currScore;

    public CurrentScore() {
    }

    public static Score getCurrScore() {
        return currScore;
    }

    public static void setCurrScore(Score currScore) {
        CurrentScore.currScore = currScore;
    }
}
