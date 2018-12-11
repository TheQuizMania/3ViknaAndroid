package a.b.c.quizmania.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import a.b.c.quizmania.Entities.User;
import a.b.c.quizmania.R;

public class RegisterActivity extends AppCompatActivity {

    private static FirebaseDatabase INSTANCE = null;
    public static void setInstance(FirebaseDatabase instance){
        INSTANCE = instance;
    }

    // Firebase
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;

    // Views
    private Button signupBtn;
    private EditText unEdit;
    private EditText emailEdit;
    private EditText passwdEdit;
    private EditText passwdConfirmEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Firebase
        db = INSTANCE;
        if(db == null){
            FirebaseDatabase.getInstance();
        }
        mAuth = FirebaseAuth.getInstance();

        // Buttons
        signupBtn = findViewById(R.id.sign_Up);

        // Input
        unEdit = findViewById(R.id.username_signUp);
        emailEdit = findViewById(R.id.email_signUp);
        passwdEdit = findViewById(R.id.passwd_signUp);
        passwdConfirmEdit = findViewById(R.id.passwd_confirm);


        // Click listeners
        signupBtn.setOnClickListener(v -> signUp());
    }

    public void signUp() {

        String userName = unEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String passW = passwdEdit.getText().toString();
        String passWConfirm = passwdConfirmEdit.getText().toString();

        if(userName.trim().length() == 0) {
            unEdit.requestFocus();
            unEdit.setError(getString(R.string.username_needed));
            //regex validates if email is in a correct format.
            //i.e <chars or symbols>@<chars or symbols>.<chars>
        } else if(email.length() == 0){
            emailEdit.requestFocus();
            emailEdit.setError(getString(R.string.email_needed));
        } else if(!email.trim().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")) {
            emailEdit.requestFocus();
            emailEdit.setError(getString(R.string.email_not_valid));
        } else if(passW.trim().length() == 0) {
            passwdEdit.requestFocus();
            passwdEdit.setError(getString(R.string.password_needed));
            //regex validates if password length is > 6 and at least one of each
            // of the following is used: alphabetical letter for each case and a number
        } else if(passW.trim().matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{6,})")){
            passwdEdit.requestFocus();
            passwdEdit.setError("Password invalid");
        } else if(!passWConfirm.equals(passW)) {
            passwdConfirmEdit.requestFocus();
            passwdConfirmEdit.setError(getString(R.string.password_mismatch));
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, passW)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,
                                    getString(R.string.registration_success),
                                    Toast.LENGTH_SHORT).show();
                            //Create user to be inserted
                            final User user = new User();
                            user.setUserName(userName);
                            user.setScores(null);
                            user.setWins(0);
                            user.setLosses(0);
                            //add user to database if not exists
                            addUserIfNotInDb(user);
                            //add username to auth user
                            FirebaseUser gUser = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest changeRequest
                                    = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName)
                                    .build();
                            assert gUser != null;
                            gUser.updateProfile(changeRequest);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this,
                                    getString(R.string.registration_failed),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void addUserIfNotInDb(User user){
        db = FirebaseDatabase.getInstance();
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = db.getReference().child("root").child("Users").child(uId);
        //Listener listens if there is an attempt to change data in the database
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("DATACHANGE", "onDataChange");
                if(!dataSnapshot.exists()){
                    ref.setValue(user);
                    Toast.makeText(RegisterActivity.this, "Added User to database", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisterActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
