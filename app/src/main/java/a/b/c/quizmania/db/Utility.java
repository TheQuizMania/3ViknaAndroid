package a.b.c.quizmania.db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import a.b.c.quizmania.Entities.UserListItem;

public class Utility {

    public static void updateToken(String uID) {
        String userToken = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseDatabase.getInstance().getReference()
                .child("root")
                .child("Users")
                .child(uID)
                .child("Token").setValue(userToken);
    }

    public static void addToUserList(String email, String displayName) {
        UserListItem newUser = new UserListItem(email, displayName);
        FirebaseDatabase.getInstance().getReference().child("root")
                .child("UserList")
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(newUser);
    }

    public static String getUid() {
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            return "";
        }
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
