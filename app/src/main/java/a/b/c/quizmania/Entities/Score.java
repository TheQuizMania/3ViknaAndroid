package a.b.c.quizmania.Entities;

public class Score {

    private String difficulty;
    private int category;
    private String mode;
    private int totalQuestions;
    private int correctAnswers;

    public Score() {
    }

    public Score(String difficulty, int category, String mode,
                 int totalQuestions, int correctAnswers) {
        this.difficulty = difficulty;
        this.category = category;
        this.mode = mode;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
