package a.b.c.quizmania.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import a.b.c.quizmania.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText newPass;
    private EditText confirmNewPass;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().hide();

        newPass = findViewById(R.id.new_pass);
        confirmNewPass = findViewById(R.id.confirm_new_pass);

        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void submitPassChange(View view) {
        if(!passwordIsValid()){
            newPass.requestFocus();
            newPass.setError(getString(R.string.username_needed));
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
        return newPass.length() != 0;
    }

    private boolean passwordsMatch() {
        return newPass.getText().toString().matches(confirmNewPass.getText().toString());
    }

    private boolean passwordIsSameAsOld() {
        return user.getDisplayName().equals(newPass.getText().toString());
    }

    public void cancelPassChange(View view) {
        finish();
    }
}
