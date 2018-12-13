package a.b.c.quizmania.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import a.b.c.quizmania.R;

public class ChangeUsernameActivity extends AppCompatActivity {

    private EditText newUsername;
    private EditText confirmNewUsername;

    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            setAppTheme();
        }
        setContentView(R.layout.activity_change_username);
        Objects.requireNonNull(getSupportActionBar()).hide();
        user = FirebaseAuth.getInstance().getCurrentUser();

        newUsername = findViewById(R.id.new_username);
        confirmNewUsername = findViewById(R.id.confirm_new_user);
    }

    public void submitUsernameChange(View view) {
        if(!usernameIsValid()){ //username can't be empty or spaces
            newUsername.requestFocus();
            newUsername.setError(getString(R.string.username_needed));
        } else if(!usernamesMatch()){ //username and confirm username have to match
            confirmNewUsername.requestFocus();
            confirmNewUsername.setError(getString(R.string.no_usernames_match));
        } else if(usernameIsSameAsOld()){ //new username can't be the same as the existing one
            newUsername.requestFocus();
            newUsername.setError(getString(R.string.username_same_as_old));
        } else {
            changeUsername();
        }
    }

    private void changeUsername() {
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(newUsername.getText().toString())
                .build();
        user.updateProfile(profileChangeRequest);
        changeFirebaseName();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //if change was successful, inform the user and finish() the activity, returning the user
        //to the next activity on the stack(profile)
        Toast.makeText(this, "Changed username successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void changeFirebaseName() {
        //updates the firebasedatabase with the new username
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("root").child("Users").child(user.getUid()).child("userName");
        ref.setValue(newUsername.getText().toString());
    }

    private boolean usernameIsValid() {
        //username can't be empty or spaces only
        return newUsername.getText().toString().trim().length() != 0;
    }

    private boolean usernamesMatch() {
        //username and confirm username have to match
        return newUsername.getText().toString().matches(confirmNewUsername.getText().toString());
    }

    private boolean usernameIsSameAsOld() {
        //new username can't be the same as the existing one
        return Objects.equals(user.getDisplayName(), newUsername.getText().toString());
    }

    public void cancelUsernameChange(View view) {
        finish();
    }

    private void setAppTheme() {
        String uID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        assert str != null;
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }
}
