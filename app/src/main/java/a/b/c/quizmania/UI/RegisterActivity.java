package a.b.c.quizmania.UI;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import a.b.c.quizmania.R;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    Button signupBtn;

    EditText unEdit;
    EditText emailEdit;
    EditText passwdEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Buttons
        signupBtn = (Button)findViewById(R.id.sign_up);

        // Input
        unEdit = (EditText)findViewById(R.id.username_signup);
        emailEdit = (EditText)findViewById(R.id.email_signup);
        passwdEdit = (EditText)findViewById(R.id.passwd_signup);


        // Click listeners
        signupBtn.setOnClickListener(v -> signUp(v));
    }

    private void signUp(View view) {

        String userName = unEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String passwd = passwdEdit.getText().toString();

        if(userName.trim().length() == 0) {
            unEdit.requestFocus();
            unEdit.setError("Username is needed");
        } else if(!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")) {
            emailEdit.requestFocus();
            emailEdit.setError("Email needs to be valid");
        } else if(passwd.trim().length() == 0) {
            passwdEdit.requestFocus();
            passwdEdit.setError("Password is needed");
        } else {
            mAuth.createUserWithEmailAndPassword(email, passwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, getString(R.string.registration_failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }



}
