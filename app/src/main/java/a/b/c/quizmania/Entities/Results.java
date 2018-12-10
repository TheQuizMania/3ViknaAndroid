package a.b.c.quizmania.Entities;

public class Results {
    private String category;
    private String difficulty;
    private String question;
    private String correct_answer;
    private String[] incorrect_answers;

    public Results() {
    }

    public Results(String category, String difficulty,
                   String question, String correctAnswer,
                   String[] incorrectAnswers) {
        this.category = category;
        this.difficulty = difficulty;
        this.question = question;
        this.correct_answer = correctAnswer;
        this.incorrect_answers = incorrectAnswers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String type) {
        this.difficulty = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correct_answer = correctAnswer;
    }

    public String[] getIncorrectAnswers() {
        return incorrect_answers;
    }

    public void setIncorrectAnswers(String[] incorrectAnswers) {
        this.incorrect_answers = incorrectAnswers;
    }
}
