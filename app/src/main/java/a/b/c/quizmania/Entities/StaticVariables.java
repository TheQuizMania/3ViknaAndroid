package a.b.c.quizmania.Entities;


/**
 * Class for storing commonly used static variables
 */
public class StaticVariables {
    private static Score currScore;
    public static Challenge pendingChallenge;
    public static Challenge currChallenge;
    public static Question question;

    public StaticVariables() {
    }

    public static Score getCurrScore() {
        return currScore;
    }

    public static void setCurrScore(Score currScore) {
        StaticVariables.currScore = currScore;
    }
}
