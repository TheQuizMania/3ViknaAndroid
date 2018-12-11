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

import a.b.c.quizmania.R;

public class ChangeUsernameActivity extends AppCompatActivity {

    private EditText newUsername;
    private EditText confirmNewUsername;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);
        getSupportActionBar().hide();
        setAppTheme();
        user = FirebaseAuth.getInstance().getCurrentUser();

        newUsername = findViewById(R.id.new_username);
        confirmNewUsername = findViewById(R.id.confirm_new_user);
    }

    private void setAppTheme() {
        String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }

    public void submitUsernameChange(View view) {
        if(!usernameIsValid()){
            newUsername.requestFocus();
            newUsername.setError(getString(R.string.username_needed));
        } else if(!usernamesMatch()){
            confirmNewUsername.requestFocus();
            confirmNewUsername.setError(getString(R.string.no_usernames_match));
        } else if(usernameIsSameAsOld()){
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
        Toast.makeText(this, "Changed username successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void changeFirebaseName() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("root").child("Users").child(user.getUid()).child("userName");
        ref.setValue(newUsername.getText().toString());
    }

    private boolean usernameIsValid() {
        return newUsername.length() != 0;
    }

    private boolean usernamesMatch() {
        return newUsername.getText().toString().matches(confirmNewUsername.getText().toString());
    }

    private boolean usernameIsSameAsOld() {
        return user.getDisplayName().equals(newUsername.getText().toString());
    }

    public void cancelUsernameChange(View view) {
        finish();
    }

}
