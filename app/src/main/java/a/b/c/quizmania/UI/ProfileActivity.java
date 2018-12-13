package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import a.b.c.quizmania.R;

/**
 * An activity that displays the current user's profile
 */
public class ProfileActivity extends AppCompatActivity {


    private Switch themeSwitch;
    private String theme;
    private String uID;

    //private SharedPreferences sp;

    

    // Firebase
    GoogleSignInClient mGoogleSignInClient;

    private TextView userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Gets uId to use for shared preferences and sets the app theme
        uID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        setAppTheme();
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();


        themeSwitch = findViewById(R.id.theme_switch);
        userInfo = findViewById(R.id.user_information);

        Button changePassBtn = findViewById(R.id.change_password);
        //Sets the switch button to the correct position
        checkSwitch();

        writePreference();

        setUserInfo();

        // Setting google sign in client
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Finding views
        // Views
        Button signOutBtn = findViewById(R.id.sign_out_profile);

        // Setting click listeners
        signOutBtn.setOnClickListener(v -> signOut());

        if(GoogleSignIn.getLastSignedInAccount(this) == null){
            changePassBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        //onResume recreates the activity if the user has changed his username to display the new one
        super.onResume();
        String[] userInfoBox = userInfo.getText().toString().split("\n\n");
        String username = userInfoBox[0];
        if(!username.equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName())){
            recreate();
        }
    }

    private void signOut() {
        if(GoogleSignIn.getLastSignedInAccount(this) != null){
            mGoogleSignInClient.signOut();
            FirebaseAuth.getInstance().signOut();
        } else {
            FirebaseAuth.getInstance().signOut();
        }
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        sp.edit().putBoolean("logged",false).apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void setAppTheme() {
        //fetches shared preferences with uId key
        SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        assert str != null;
        if(str.equals("AppTheme")) {
            //sets app theme to light mode
            setTheme(R.style.AppTheme);
        }else{
            //sets app theme to dark mode
            setTheme(R.style.DarkTheme);
        }
        //sets the theme variable to use in checkSwitch()
        theme = str;
    }

    public void goToStats(View view){
        startActivity(new Intent(this, StatisticsActivity.class));
    }

    private void setUserInfo() {
        //gets the users info and sets the TextView to display them.
        String userName;
        String email;
        String phoneNumber;

        userName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        phoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        if(phoneNumber == null){
            phoneNumber = "";
        }

        userInfo.setText(String.format("%s\n\n%s\n\n%s", userName, email, phoneNumber));
    }
    private void writePreference() {
        //if themeSwitch is clicked, changes the theme and recreates the activity to apply changes
        //also writes into shared preferences to remember the users settings.
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                setTheme(R.style.DarkTheme);
                SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("THEME_PREF", "DarkTheme");
                editor.apply();
                this.recreate();
            }else{
                setTheme(R.style.AppTheme);
                SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("THEME_PREF", "AppTheme");
                editor.apply();
                this.recreate();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Creates a new MainMenuActivity to change the light/dark mode
        super.onBackPressed();
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
    private void checkSwitch() {
        //Fall sem athugar hvort userinn er med stillt a Dark/light mode
        //setur switchin i samræmi við það án þess að triggera onClickið
        if(theme.equals("AppTheme")) {
            themeSwitch.setChecked(false);
        }else{
            themeSwitch.setChecked(true);
        }
    }

    public void changeUserName(View view) {
        startActivity(new Intent(this, ChangeUsernameActivity.class));
    }

    public void changePassWord(View view) {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }
}
