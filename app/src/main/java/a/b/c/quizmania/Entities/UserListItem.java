package a.b.c.quizmania.Entities;

public class UserListItem {

    private String email;
    private String displayName;


    public UserListItem(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
    }
    public UserListItem() {}

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }
}
