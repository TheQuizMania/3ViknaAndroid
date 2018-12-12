package a.b.c.quizmania.db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import a.b.c.quizmania.Entities.Challenge;
import a.b.c.quizmania.Entities.Score;
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
	public static void addScore(Score score) {
		String uId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
		FirebaseDatabase.getInstance().getReference().child("root")
				.child("Users")
				.child(uId)
				.child("scores")
				.push()
				.setValue(score);
	}

	public static void addChallenge(Challenge challenge) {
		FirebaseDatabase.getInstance().getReference().child("root")
				.child("challenges")
				.push()
				.setValue(challenge);
	}

	public static void updateChallenge(Challenge challenge) {
		FirebaseDatabase.getInstance().getReference().child("root")
				.child("challenges")
				.child(challenge.getId())
				.setValue(challenge);
	}


}
