package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import a.b.c.quizmania.R;

public class QuestionActivity extends AppCompatActivity {

    public static String category;
    public static String difficulty;
    private String uID;

//    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        setAppTheme();
        getInfo();
        setContentView(R.layout.activity_question);
    }
    private void setAppTheme() {
        SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }

    private void getInfo(){
        Intent i = getIntent();
        category = i.getStringExtra("CATEGORY");
        difficulty = i.getStringExtra("DIFFICULTY");
    }
}
