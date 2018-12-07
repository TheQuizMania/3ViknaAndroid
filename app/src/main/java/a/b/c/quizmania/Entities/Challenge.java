package a.b.c.quizmania.Entities;

public class Challenge {
    private UserListItem challenger;
    private UserListItem challengee;
    private int id;

    public Challenge() {}

    public Challenge(UserListItem challenger, UserListItem challengee, int id) {
        this.challenger = challenger;
        this.challengee = challengee;
        this.id = id;
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
