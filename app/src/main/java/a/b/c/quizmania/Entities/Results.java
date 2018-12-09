package a.b.c.quizmania.Entities;

import java.util.List;

public class Results {
    private String category;
    private String type;
    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;

    public Results() {
    }

    public Results(String category, String type,
                   String question, String correctAnswer,
                   List<String> incorrectAnswers) {
        this.category = category;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<String> getIncorrectAnswers() {
        return incorrect_answers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrect_answers = incorrectAnswers;
    }
}
