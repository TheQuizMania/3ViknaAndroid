package a.b.c.quizmania.Entities;

import java.util.Arrays;
import java.util.List;

public class Score {

    private String difficulty;
    private String category;
    private String mode;
    private int correctAnswers;
    private QuestionStats[] questionStats;

    public Score() {
    }

    public Score(String difficulty, String category, String mode) {
        this.difficulty = difficulty;
        this.category = category;
        this.mode = mode;
    }

    public Score(String difficulty, String category, String mode, int correctAnswers,
                 QuestionStats[] questionStats) {
        this.difficulty = difficulty;
        this.category = category;
        this.mode = mode;
        this.correctAnswers = correctAnswers;
        this.questionStats = questionStats;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public QuestionStats[] getQuestionStats() {
        return questionStats;
    }

    public void setQuestionStats(QuestionStats[] questionStats) {
        this.questionStats = questionStats;
    }

    public void appendQuestionStats(QuestionStats newQuestion){
        List<QuestionStats> d  = Arrays.asList(this.questionStats);
        d.add(newQuestion);
        this.questionStats = (QuestionStats[]) d.toArray();
    }
}
