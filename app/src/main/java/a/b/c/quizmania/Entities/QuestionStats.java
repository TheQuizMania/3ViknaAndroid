package a.b.c.quizmania.Entities;

public class QuestionStats {

    private int timeToAnswer;
    private String statsQuestion;
    private String questionType;
    private String rightAnswer;
    private String[] wrongAnswers;
    private boolean wasCorrect;

    public QuestionStats() {
    }

    public QuestionStats(int timeToAnswer, String question, String questionType,
                         String rightAnswer, String[] wrongAnswers, boolean wasCorrect) {
        this.timeToAnswer = timeToAnswer;
        this.statsQuestion = question;
        this.questionType = questionType;
        this.rightAnswer = rightAnswer;
        this.wrongAnswers = wrongAnswers;
        this.wasCorrect = wasCorrect;
    }

    public int getTimeToAnswer() {
        return timeToAnswer;
    }

    public void setTimeToAnswer(int timeToAnswer) {
        this.timeToAnswer = timeToAnswer;
    }

    public String getStatsQuestion() {
        return statsQuestion;
    }

    public void setStatsQuestion(String statsQuestion) {
        this.statsQuestion = statsQuestion;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String[] getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(String[] wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public boolean isWasCorrect() {
        return wasCorrect;
    }

    public void setWasCorrect(boolean wasCorrect) {
        this.wasCorrect = wasCorrect;
    }
}
