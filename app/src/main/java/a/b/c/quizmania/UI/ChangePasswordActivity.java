package a.b.c.quizmania.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import a.b.c.quizmania.R;

/**
 * An activity for non-google users to change their password
 */
public class ChangePasswordActivity extends AppCompatActivity {
    //Views
    private EditText newPass;
    private EditText confirmNewPass;
    //Current user
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the app theme
        setAppTheme();
        setContentView(R.layout.activity_change_password);
        //hides the action bar on top
        Objects.requireNonNull(getSupportActionBar()).hide();
        //finds views in XML
        newPass = findViewById(R.id.new_pass);
        confirmNewPass = findViewById(R.id.confirm_new_pass);
        //initializes current user
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void submitPassChange(View view) {
        //checks if the password is valid
        if(!passwordIsValid()){
            newPass.requestFocus();
            newPass.setError("Invalid password");
        } else if(!passwordsMatch()){
            confirmNewPass.requestFocus();
            confirmNewPass.setError(getString(R.string.no_usernames_match));
        } else if(passwordIsSameAsOld()){
            newPass.requestFocus();
            newPass.setError(getString(R.string.username_same_as_old));
        } else {
            changePassword();
        }
    }

    private void changePassword() {
        //Changes the password
            user.updatePassword(newPass.getText().toString())
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(ChangePasswordActivity.this,
                                    "Successfully changed password", Toast.LENGTH_SHORT).show();
                        }
                    });
            finish();
    }

    private boolean passwordIsValid() {
        //password can't be empty string or be whitespace only
        if(newPass.getText().toString().trim().length() == 0){
            return false;
            //password needs to include lower and uppercase letters and a number, can't include whitespace
        }else
            return newPass.getText().toString().trim().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$");
    }

    private boolean passwordsMatch() {
        //password has to match the confirm password editText
        return newPass.getText().toString().matches(confirmNewPass.getText().toString());
    }

    private boolean passwordIsSameAsOld() {
        //new password can't match the existing one
        return Objects.equals(user.getDisplayName(), newPass.getText().toString());
    }

    //if the user presses the cancel button finish the view
    public void cancelPassChange(View view) {
        finish();
    }

    private void setAppTheme() {
        //sets app theme stored in shared preferences
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
