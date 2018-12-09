package a.b.c.quizmania.Entities;

import java.util.List;

public class Score {

    private String difficulty;
    private String category;
    private int correctAnswers;
    private List<QuestionStats> questionStats;

    public Score() {
    }

    public Score(String difficulty, String category) {
        this.difficulty = difficulty;
        this.category = category;
    }

    public Score(String difficulty, String category, int correctAnswers,
                 List<QuestionStats> questionStats) {
        this.difficulty = difficulty;
        this.category = category;
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

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<QuestionStats> getQuestionStats() {
        return questionStats;
    }

    public void setQuestionStats(List<QuestionStats> questionStats) {
        this.questionStats = questionStats;
    }
}
