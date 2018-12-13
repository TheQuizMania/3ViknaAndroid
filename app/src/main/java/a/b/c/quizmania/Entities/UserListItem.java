package a.b.c.quizmania.Entities;

/**
 * Entity that keeps track of user info for multiplayer
 */
public class UserListItem {

    private String email;
    private String displayName;
    private String pushToken;


    public UserListItem(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
        this.pushToken = null;
    }

    public UserListItem() {}

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPushToken(String pushToken){
        this.pushToken = pushToken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPushToken() {
        return pushToken;
    }
}
