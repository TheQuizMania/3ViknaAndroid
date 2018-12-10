package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import a.b.c.quizmania.R;

public class ProfileActivity extends AppCompatActivity {


    private Switch themeSwitch;
    private String theme;
    private String uID;

    private TextView userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        setAppTheme();
        setContentView(R.layout.activity_profile);
        themeSwitch = findViewById(R.id.theme_switch);
        userInfo = findViewById(R.id.user_information);
        checkSwitch();
        writePreference();

        setUserInfo();
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
}
