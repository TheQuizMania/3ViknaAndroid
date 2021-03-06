package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import a.b.c.quizmania.Entities.QuestionStats;
import a.b.c.quizmania.Entities.Score;
import a.b.c.quizmania.Entities.StaticVariables;
import a.b.c.quizmania.R;

/**
 * An activity that show the results of a single player game
 */
public class SinglePlayerResultsActivity extends AppCompatActivity {

    private TextView category;
    private TextView difficulty;
    private TextView rightAnswers;
    private TextView averageTime;

    private Score resultScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the app theme
        setAppTheme();
        setContentView(R.layout.activity_results);
        //Hides the actionbar on top
        Objects.requireNonNull(getSupportActionBar()).hide();

        Intent ret = getIntent();
        //If user is playing multiplayer, hides the play_again button
        if(ret.getStringExtra("MODE").matches("MULTI")) {
            findViewById(R.id.sp_result_play_again).setVisibility(Button.GONE);
        }
        initVariables();
        populateViews();
    }

    private void setAppTheme() {
        String uID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        assert str != null;
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
//        startActivity(new Intent(this, MainMenuActivity.class));
    }

    private void initVariables(){
        //initializing variables
        resultScore = StaticVariables.getCurrScore();
        category = findViewById(R.id.results_category);
        difficulty = findViewById(R.id.results_difficulty);
        rightAnswers = findViewById(R.id.results_right_answers);
        averageTime = findViewById(R.id.results_average_time);

    }

    private void populateViews(){
        //setting TextView texts
        category.setText(String.format("Category: %s", getCategoryName()));
        difficulty.setText(String.format("Difficulty: %s", resultScore.getDifficulty()));
        rightAnswers.setText(String.format(Locale.US, "%d %s", resultScore.getCorrectAnswers(), "out of 10 questions correct"));
        averageTime.setText(String.format(Locale.US, "%s %s %s", "Average time to answer:", calculateAverageTime(), "seconds"));
    }

    private String calculateAverageTime() {
        //Gets all the questions and sums up the time to answer, returns the avg time spent on a question
        List<QuestionStats> questionList = resultScore.getQuestionStats();
        double totalTimeToAnswer = 0;
        for(QuestionStats q : questionList) {
            totalTimeToAnswer += (double) q.getTimeToAnswer();
        }
        double avg = totalTimeToAnswer / 10.0;
        DecimalFormat df = new DecimalFormat("##.##");
        avg /= 1000.0;
        return df.format(avg);
    }

    public void goToMainMenu(View view){
        //Button to go back to main menu
        finish();
    }

    public void goToSelection(View view){
        //button to go to SelectionActivity
        startActivity(new Intent(this, SelectionActivity.class));
        finish();
    }

    private String getCategoryName(){
        //Fetched category name
        return resultScore.getCategory();
    }

}