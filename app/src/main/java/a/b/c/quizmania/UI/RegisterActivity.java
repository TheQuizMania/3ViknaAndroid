package a.b.c.quizmania.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import a.b.c.quizmania.R;

public class RegisterActivity extends AppCompatActivity {

    // Firebase
    FirebaseAuth mAuth;

    // Views
    Button signupBtn;
    EditText unEdit;
    EditText emailEdit;
    EditText passwdEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Firebase
        mAuth = FirebaseAuth.getInstance();


        // Buttons
        signupBtn = findViewById(R.id.sign_up);

        // Input
        unEdit = findViewById(R.id.username_signup);
        emailEdit = findViewById(R.id.email_signup);
        passwdEdit = findViewById(R.id.passwd_signup);


        // Click listeners
        signupBtn.setOnClickListener(v -> signUp());
    }

    private void signUp() {

        String userName = unEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String passwd = passwdEdit.getText().toString();

        if(userName.trim().length() == 0) {
            unEdit.requestFocus();
            unEdit.setError("Username is needed");
        } else if(!email.trim().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")) {
            emailEdit.requestFocus();
            emailEdit.setError("Email needs to be valid");
        } else if(passwd.trim().length() == 0) {
            passwdEdit.requestFocus();
            passwdEdit.setError("Password is needed");
        } else if(passwd.trim().matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{6,})")){
            passwdEdit.requestFocus();
            passwdEdit.setError("Password invalid");
        } else {
            mAuth.createUserWithEmailAndPassword(email, passwd)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference ref = db.getReference("root//Users//id//userName");
                            ref.setValue(userName);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, getString(R.string.registration_failed), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
