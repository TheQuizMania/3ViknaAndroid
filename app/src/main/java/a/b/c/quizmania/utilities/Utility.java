package a.b.c.quizmania.utilities;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import a.b.c.quizmania.Entities.Challenge;
import a.b.c.quizmania.Entities.Score;
import a.b.c.quizmania.Entities.UserListItem;

/**
 * Utility is used for the communication to Firebase Database
 *
 */
public class Utility {

	private static DatabaseReference ref;

    /**
     * setWinsAndLosses()
     * Takes in the user and a boolean variable that either increments the wins or losses
     *
     * @param user FirebaseUser
     * @param isWinner Boolean
     */
	public static void setWinsAndLosses(FirebaseUser user, boolean isWinner){
		ref = FirebaseDatabase.getInstance().getReference()
				.child("root")
				.child("Users")
				.child(user.getUid());
		if(isWinner){
			ref = ref.child("wins");
		} else {
			ref = ref.child("losses");
		}
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()){
					int retVal = dataSnapshot.getValue(Integer.class);
					ref.setValue(retVal + 1);
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				Log.d("ON_CANCELED", "onCanceled called with error " + databaseError.getMessage());
			}
		});
	}

	/**
     * Class for storing the users to the user list,
     * It holds all the users that have logged in and can be challenged
     *
     * @param email String
     * @param displayName String
     */
    public static void addToUserList(String email, String displayName) {
        UserListItem newUser = new UserListItem(email, displayName);
        FirebaseDatabase.getInstance().getReference().child("root")
                .child("UserList")
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(newUser);
    }
    /**
     * Called when a new score is to be stored
     *
     * @param score Score
     */
	public static void addScore(Score score) {
		String uId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
		FirebaseDatabase.getInstance().getReference().child("root")
				.child("Users")
				.child(uId)
				.child("scores")
				.push()
				.setValue(score);
	}

    /**
     * Called when a new challenge is to be initialized
     *
     * @param challenge Challenge
     */
	public static void addChallenge(Challenge challenge) {
		challenge.setId(FirebaseDatabase.getInstance().getReference().push().getKey());
		FirebaseDatabase.getInstance().getReference().child("root")
				.child("challenges")
				.child(challenge.getId())
				.setValue(challenge);
	}

    /**
     * Called when the challenge has been finished
     *
     * @param challenge Challenge
     */
	public static void updateChallenge(Challenge challenge) {
		FirebaseDatabase.getInstance().getReference().child("root")
				.child("challenges")
				.child(challenge.getId())
				.setValue(challenge);
	}

    /**
     * Takes in a user with its token and updates the push token of the user
     *
     * @param uID String
     * @param newToken String
     */
	public static void updatePushToken(String uID, String newToken) {
		FirebaseDatabase.getInstance().getReference().child("root")
				.child("UserList")
				.child(uID)
				.child("pushToken")
				.setValue(newToken);
	}

}
