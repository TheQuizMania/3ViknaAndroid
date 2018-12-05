package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import a.b.c.quizmania.R;

public class LoginActivity extends AppCompatActivity {

    final int RESULT_CODE = 9001;

    // Firebase
    FirebaseAuth mAuth;
    GoogleSignInClient googleClient;

    // Views
    Button signInBtn;
    SignInButton googleSigninBtn;
    TextView registerBtn;
    EditText emailEdit;
    EditText passwdEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleClient = GoogleSignIn.getClient(this, gso);

        // Inputs
        emailEdit = (EditText)findViewById(R.id.email_signin);
        passwdEdit = (EditText)findViewById(R.id.passwd_signin); 

        // Finding Clickables
        signInBtn = (Button)findViewById(R.id.sign_in);
        googleSigninBtn = (SignInButton) findViewById(R.id.google_signin);
        registerBtn = (TextView)findViewById(R.id.register_me);

        // Click listeners
        signInBtn.setOnClickListener(v -> signIn(v));
        googleSigninBtn.setOnClickListener(v -> signInGoogle(v));
        registerBtn.setOnClickListener(v -> registerMe(v));


    }


    /*
    Sign in
    Runs when sign in button is clicked,
    should sign in the user
     */
    private void signIn(View view) {
        String email = emailEdit.getText().toString();
        String passwd = passwdEdit.getText().toString();

        if(!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")) {
            // If email is invalid
            emailEdit.requestFocus();
            emailEdit.setError("Email needs to be valid");
        } else if (passwd.trim().length() == 0) {
            // If password is empty
            passwdEdit.requestFocus();
            passwdEdit.setError("Password is needed");
        } else {
            //Event listener that tries to sign in the user
            mAuth.signInWithEmailAndPassword(emailEdit.getText().toString(), passwdEdit.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                // Sign in was successful
                                Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                                startMainMenu();
                            } else {
                                // Sign in failed
                                Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void signInGoogle(View view) {
        Intent signInIntent = googleClient.getSignInIntent();
        startActivityForResult(signInIntent, RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Sign in Google was successful
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(this, getString(R.string.google_signin_success), Toast.LENGTH_SHORT).show();
                FirebaseGoogleSignup(account);
            }catch (ApiException e) {
                // Sign in Google failed
                e.printStackTrace();
                Toast.makeText(this, getString(R.string.google_signin_fail), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void registerMe(View view) {
        // Starting Register activity
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void startMainMenu() {
        // Starting Main menu activity
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }


    private void FirebaseGoogleSignup(GoogleSignInAccount acct) {
        Log.d("GOOGLE_LOGIN", "FirebaseAuthWithGoogle: " + acct.getIdToken());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            startMainMenu();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.google_signin_fail, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
