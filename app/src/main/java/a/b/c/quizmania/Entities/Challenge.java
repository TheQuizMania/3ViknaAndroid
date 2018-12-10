package a.b.c.quizmania.Entities;

public class Challenge {
    private UserListItem challenger;
    private UserListItem challengee;
    private Score challengerScore;
    private Score challengeeScore;
    private int id;
    private String category;
    private String difficulty;
    private boolean isActive;


    Question question;

    public Challenge() {}

    public Challenge(UserListItem challenger, UserListItem challengee, int id, boolean isActive) {
        this.challenger = challenger;
        this.challengee = challengee;
        this.id = id;
        this.isActive = isActive;
        challengerScore = null;
        challengeeScore = null;
    }

    public UserListItem getChallenger() {
        return challenger;
    }

    public UserListItem getChallengee() {
        return challengee;
    }

    public int getId() {
        return id;
    }

    public Question getQuestion() { return question; }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isActive() {
        return isActive;
    }

    public Score getChallengerScore() {
        return challengerScore;
    }

    public Score getChallengeeScore() {
        return challengeeScore;
    }

    public void setChallengerScore(Score challengerScore) {
        this.challengerScore = challengerScore;
    }

    public void setChallengeeScore(Score challengeeScore) {
        this.challengeeScore = challengeeScore;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setQuestion(Question question) { this.question = question; }

    public void setChallenger(UserListItem challenger) {
        this.challenger = challenger;
    }

    public void setChallengee(UserListItem challengee) {
        this.challengee = challengee;
    }

    public void setId(int id) {
        this.id = id;
    }
}
