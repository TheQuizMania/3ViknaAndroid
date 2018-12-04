package a.b.c.quizmania.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Buttons
        signupBtn = (Button)findViewById(R.id.sign_up);

        // Input
        unEdit = (EditText)findViewById(R.id.username_signup);
        emailEdit = (EditText)findViewById(R.id.email_signup);
        passwdEdit = (EditText)findViewById(R.id.passwd_signup);

    }
}
