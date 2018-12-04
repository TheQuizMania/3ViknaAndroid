package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import a.b.c.quizmania.R;

public class LoginActivity extends AppCompatActivity {

    Button signInBtn;
    TextView registerBtn;
    
    EditText emailEdit;
    EditText passwdEdit;
    
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Inputs
        emailEdit = (EditText)findViewById(R.id.email_signin);
        passwdEdit = (EditText)findViewById(R.id.passwd_signin); 

        // Finding Clickables
        signInBtn = (Button)findViewById(R.id.sign_in);
        registerBtn = (TextView)findViewById(R.id.register_me);

        // Click listeners
        signInBtn.setOnClickListener(v -> signIn(v));
        registerBtn.setOnClickListener(v -> registerMe(v));


    }


    /*
    Sign in
    Runs when sign in button is clicked,
    should sign in the user
     */
    private void signIn(View view) {
        //Event listener that tries to sign in the user
        mAuth.signInWithEmailAndPassword(emailEdit.getText().toString(), passwdEdit.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            // Sign in was successful
                            Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();

                        } else {
                            // Sign in failed
                            Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void registerMe(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void startMainMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

}
