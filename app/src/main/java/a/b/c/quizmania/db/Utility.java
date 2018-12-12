package a.b.c.quizmania.db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import a.b.c.quizmania.Entities.UserListItem;

public class Utility {

    /*********************************************************************
     *                                                                   *
     *  Class for storing the users to the user list,                    *
     *  It holds all the users that have logged in and can be challenged *
     *                                                                   *
     * @param email                                                      *
     * @param displayName                                                *
     *********************************************************************/
    public static void addToUserList(String email, String displayName) {
        UserListItem newUser = new UserListItem(email, displayName);
        FirebaseDatabase.getInstance().getReference().child("root")
                .child("UserList")
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(newUser);
    }
}
