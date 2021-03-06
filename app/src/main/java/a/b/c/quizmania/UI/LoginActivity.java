package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import a.b.c.quizmania.Entities.User;
import a.b.c.quizmania.R;
import a.b.c.quizmania.utilities.Utility;

/**
 * An activity for logging users in
 */
public class LoginActivity extends AppCompatActivity {
    //instance for testing
    private static SharedPreferences INSTANCE = null;
    public static void setSharedPreferencesInstance(SharedPreferences pref){
        INSTANCE = pref;
    }

    final int RESULT_CODE = 9001;
    private SharedPreferences sp;

    // Firebase
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleClient;
    private GoogleSignInAccount account;

    // EditTexts
    private EditText emailEdit;
    private EditText passwdEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //for tests
        if(INSTANCE == null){
            sp = getSharedPreferences("login",MODE_PRIVATE);

            if(sp.getBoolean("logged",false)){
                startMainMenu();
            }

        }else {
            sp = INSTANCE;
        }


        //initializes variables
        initVariables();
    }

    private void initVariables(){
        // Firebase
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleClient = GoogleSignIn.getClient(this, gso);

        // Inputs
        emailEdit = findViewById(R.id.email_sign_in);
        passwdEdit = findViewById(R.id.password_sign_in);

        // Finding Clickables
        // Views
        Button signInBtn = findViewById(R.id.sign_in);
        SignInButton googleSigninBtn = findViewById(R.id.google_signin);
        TextView registerBtn = findViewById(R.id.register_me);

        // Click listeners
        signInBtn.setOnClickListener(v -> signIn());
        googleSigninBtn.setOnClickListener(v -> signInGoogle());
        registerBtn.setOnClickListener(v -> registerMe());
    }

    /**
     Sign in
     Runs when sign in button is clicked,
     should sign in the user
     **/

    private void signIn() {
        String email = emailEdit.getText().toString();
        String passW = passwdEdit.getText().toString();
        //regex looks for any number of characters, then a @ and any number of characters.
        //Then it looks for a dot and some alphabetical letters.
        if(!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")) {
            // If email is invalid
            emailEdit.requestFocus();
            emailEdit.setError(getString(R.string.email_not_valid));
        } else if (passW.trim().length() == 0) {
            // If password is empty
            passwdEdit.requestFocus();
            passwdEdit.setError(getString(R.string.password_needed));
        } else {
            //Event listener that tries to sign in the user
            mAuth.signInWithEmailAndPassword(emailEdit.getText().toString(),
                    passwdEdit.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if(task.isSuccessful()) {
                            // Sign in was successful
                            addUserInfoToAuth();
                            Toast.makeText(LoginActivity.this,
                                    getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                            Utility.addToUserList(Objects.requireNonNull(FirebaseAuth
                                            .getInstance().getCurrentUser()).getEmail(),
                                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            startMainMenu();
                            sp.edit().putBoolean("logged",true).apply();
                        } else {
                            // Sign in failed
                            Toast.makeText(LoginActivity.this,
                                    getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void addUserInfoToAuth(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = db.getReference();
        String uId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference ref = rootRef.child("root").child("Users").child(uId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    String userN = user.getUserName();
                    FirebaseUser gUser = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest changeRequest
                            = new UserProfileChangeRequest.Builder()
                            .setDisplayName(userN)
                            .build();
                    assert gUser != null;
                    gUser.updateProfile(changeRequest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void startMainMenu() {
        // Starting Main menu activity
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void FirebaseGoogleSignup(GoogleSignInAccount acct) {
        Log.d("GOOGLE_LOGIN", "FirebaseAuthWithGoogle: " + acct.getIdToken());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()) {
                        //Create user to be inserted
                        final User user = new User();
                        user.setUserName(account.getDisplayName());
                        user.setScores(null);
                        user.setWins(0);
                        user.setLosses(0);
                        Utility.addToUserList(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail(), mAuth.getCurrentUser().getDisplayName());
                        //on sign in inserts new user into database if not exists
                        addUserIfNotInDb(user);
                        startMainMenu();
                        sp.edit().putBoolean("logged",true).apply();

                    } else {
                        Toast.makeText(LoginActivity.this,
                                R.string.google_signin_fail, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addUserIfNotInDb(User user){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String uId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference ref = db.getReference("root//Users//" + uId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    //insert user if they don't exist in database.
                    ref.setValue(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void signInGoogle() {
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
                account = task.getResult(ApiException.class);
                Toast.makeText(this, getString(R.string.google_signin_success),
                        Toast.LENGTH_SHORT).show();
                FirebaseGoogleSignup(account);
            }catch (ApiException e) {
                // Sign in Google failed
                e.printStackTrace();
                Toast.makeText(this, getString(R.string.google_signin_fail),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void registerMe() {
        // Starting Register activity
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

}
