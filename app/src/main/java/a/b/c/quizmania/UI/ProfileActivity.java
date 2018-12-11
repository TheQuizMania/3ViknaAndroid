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

import a.b.c.quizmania.R;

public class ProfileActivity extends AppCompatActivity {


    private Switch themeSwitch;
    private String theme;
    private String uID;

    

    // Firebase
    GoogleSignInClient mGoogleSignInClient;

    // Views
    private Button signOutBtn;
    private Button changePassBtn;
    private TextView userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        setAppTheme();
        setContentView(R.layout.activity_profile);
        themeSwitch = findViewById(R.id.theme_switch);
        userInfo = findViewById(R.id.user_information);
        changePassBtn = findViewById(R.id.change_password);
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
        signOutBtn = (Button)findViewById(R.id.sign_out_profile);

        // Setting click listeners
        signOutBtn.setOnClickListener(v -> signOut(v));

        if(GoogleSignIn.getLastSignedInAccount(this) == null){
            changePassBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] userInfoBox = userInfo.getText().toString().split("\n\n");
        String username = userInfoBox[0];
        if(!username.equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())){
            recreate();
        }
    }

    private void signOut(View v) {
        if(GoogleSignIn.getLastSignedInAccount(this) != null){
            mGoogleSignInClient.signOut();
            FirebaseAuth.getInstance().signOut();
        } else {
            FirebaseAuth.getInstance().signOut();
        }
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void setAppTheme() {
        SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
        theme = str;
    }

    public void goToStats(View view){
        startActivity(new Intent(this, StatisticsActivity.class));
    }

    private void setUserInfo() {
        String userName;
        String email;
        String phoneNumber;

        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        phoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        if(phoneNumber == null){
            phoneNumber = "";
        }

        userInfo.setText(userName + "\n\n" + email + "\n\n" + phoneNumber);
    }
    private void writePreference() {
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
