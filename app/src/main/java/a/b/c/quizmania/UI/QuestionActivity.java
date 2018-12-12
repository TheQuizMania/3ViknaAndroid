package a.b.c.quizmania.UI;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import a.b.c.quizmania.Fragments.QuestionDisplayFragment;
import a.b.c.quizmania.R;

public class QuestionActivity extends AppCompatActivity {

    public static String category;
    public static String difficulty;
    private String uID;
    private String mode;
    private int challengeId;
//    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        getInfo();
        setAppTheme();
        setContentView(R.layout.activity_question);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    /*********************************
     *                               *
     *      Sets the app theme       *
     *                               *
     *********************************/
    private void setAppTheme() {
        SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        assert str != null;
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }

    /***********************************
     *                                 *
     *  Gets the data from the intent  *
     *                                 *
     ***********************************/
    private void getInfo(){
        Intent i = getIntent();
        category = i.getStringExtra("CATEGORY");
        difficulty = i.getStringExtra("DIFFICULTY");
        mode = i.getStringExtra("MODE");
        challengeId = i.getIntExtra("CHALLENGEID", -1);
    }

    @Override
    public void onBackPressed() {
        //when back button is pressed during a game, cancel and stops all fragment/background threads
        QuestionDisplayFragment fragment = (QuestionDisplayFragment)getSupportFragmentManager().findFragmentById(R.id.question_fragment);
        fragment.stopFragment();

        finish();
    }
}
