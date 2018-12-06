package a.b.c.quizmania.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import a.b.c.quizmania.R;

public class ProfileActivity extends AppCompatActivity {


    private Switch themeSwitch;
    private String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        setContentView(R.layout.activity_profile);
        themeSwitch = findViewById(R.id.theme_switch);
        if(theme.equals("AppTheme")) {
            themeSwitch.setChecked(false);
        }else{
            themeSwitch.setChecked(true);
        }
        writePreference();

    }
    private void setAppTheme() {
        SharedPreferences pref = getSharedPreferences("MY_PREF", MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
        theme = str;
    }
    private void writePreference() {
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                setTheme(R.style.DarkTheme);
                SharedPreferences pref = getSharedPreferences("MY_PREF", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("THEME_PREF", "DarkTheme");
                editor.apply();
                this.recreate();
            }else{
                setTheme(R.style.AppTheme);
                SharedPreferences pref = getSharedPreferences("MY_PREF", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("THEME_PREF", "AppTheme");
                editor.apply();
                this.recreate();
            }
        });
    }
}
