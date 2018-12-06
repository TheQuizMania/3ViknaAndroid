package a.b.c.quizmania.UI;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import a.b.c.quizmania.Entities.Question;
import a.b.c.quizmania.Fragments.MultipleChoiseFragment;
import a.b.c.quizmania.R;

public class QuestionActivity extends AppCompatActivity {

    private int category;
    private String difficulty;
    private String type;
    private String uID;

//    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        setAppTheme();
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
}
