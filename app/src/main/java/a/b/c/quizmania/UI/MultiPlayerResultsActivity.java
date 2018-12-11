package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import a.b.c.quizmania.Entities.Challenge;
import a.b.c.quizmania.Entities.QuestionStats;
import a.b.c.quizmania.Entities.Score;
import a.b.c.quizmania.R;

import static a.b.c.quizmania.Entities.StaticVariables.currChallenge;

public class MultiPlayerResultsActivity extends AppCompatActivity {

    private Challenge currentChallenge;

    // Views
    TextView categoryTV;
    TextView difficultyTV;

    TextView challengerNameTV;
    TextView challengerRightAnswersTV;
    TextView challengerAvgTimeTV;

    TextView challengeeNameTV;
    TextView challengeeRightAnswersTV;
    TextView challengeeAvgTimeTV;

    TextView resultsTV;

    Button mainMenuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_result);
        getSupportActionBar().hide();
        setAppTheme();

        currentChallenge = currChallenge;

        // Finding Views
        categoryTV = (TextView)findViewById(R.id.mp_results_category);
        difficultyTV = (TextView)findViewById(R.id.mp_results_difficulty);

        challengerNameTV = (TextView)findViewById(R.id.mp_results_challenger);
        challengerRightAnswersTV = (TextView)findViewById(R.id.mp_challenger_results_right_answers);
        challengerAvgTimeTV = (TextView)findViewById(R.id.mp_challenger_results_average_time);

        challengeeNameTV = (TextView)findViewById(R.id.mp_results_challengee);
        challengeeRightAnswersTV = (TextView)findViewById(R.id.mp_challengee_results_right_answers);
        challengeeAvgTimeTV = (TextView)findViewById(R.id.mp_challengee_results_average_time);

        resultsTV = (TextView)findViewById(R.id.mp_results_result);

        mainMenuBtn = (Button)findViewById(R.id.mp_main_menu_btn);

        // Displays the information
        displayInfo();
        // Displays the results of the challenger
        displayChallenger();
        // Displays the results of the challengee
        displayChallengee();
        // Displays which one is the winner
        displayWinner();

        // Setting listeners
        mainMenuBtn.setOnClickListener(v -> startMainMenu(v));
    }

    private void setAppTheme() {
        String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }

    /***************************************************
     *                                                 *
     *                Display basic info               *
     *                                                 *
     ***************************************************/
    private void displayInfo() {
        // Displays the category
        changeText(categoryTV, (categoryTV.getText() + " " + currChallenge.getCategory()));
        // Displays the difficulty
        changeText(difficultyTV, (difficultyTV.getText() + " " + currChallenge.getDifficulty()));
    }

    /***************************************************
     *                                                 *
     *              Display the Challenger             *
     *                                                 *
     ***************************************************/
    private void displayChallenger() {
        // Gets the average time to answer a question
        double avgTime = getAvg(currChallenge.getChallengerScore().getQuestionStats());
        // Displays the name, number of questions answered right and average time
        changeText(challengerNameTV, currChallenge.getChallenger().getDisplayName());
        changeText(challengerRightAnswersTV, (challengerRightAnswersTV.getText() + " " + currChallenge.getChallengerScore().getCorrectAnswers()));
        changeText(challengerAvgTimeTV, (challengerAvgTimeTV.getText() + " " + avgTime));
    }

    /***************************************************
     *                                                 *
     *              Display the Challengee             *
     *                                                 *
     ***************************************************/
    private void displayChallengee() {
        // Gets the average time to answer a question
        double avgTime = getAvg(currChallenge.getChallengeeScore().getQuestionStats());
        // Displays the name, number of questions answered right and average time
        changeText(challengeeNameTV, currChallenge.getChallengee().getDisplayName());
        changeText(challengeeRightAnswersTV, (challengeeRightAnswersTV.getText() + " " + currChallenge.getChallengeeScore().getCorrectAnswers()));
        changeText(challengeeAvgTimeTV, (challengeeAvgTimeTV.getText() + " " + avgTime));
    }

    /************************************
     *                                  *
     *        Display the Winner        *
     *                                  *
     ************************************/
    private void displayWinner() {
        Score challengerScore, challengeeScore;
        challengerScore = currChallenge.getChallengerScore();
        challengeeScore = currChallenge.getChallengeeScore();
        int challengerRA, challengeeRA;
        challengerRA = challengerScore.getCorrectAnswers();
        challengeeRA = challengeeScore.getCorrectAnswers();
        if(challengerRA < challengeeRA) {
            changeText(resultsTV, "Winner: " + currChallenge.getChallengee().getDisplayName());
        } else if (challengerRA > challengeeRA) {
            changeText(resultsTV, "Winner: " + currChallenge.getChallenger().getDisplayName());
        } else {
            double challengerAvgTime = getAvg(currChallenge.getChallengerScore().getQuestionStats());
            double challengeeAvgTime = getAvg(currChallenge.getChallengeeScore().getQuestionStats());
            if(challengerAvgTime < challengeeAvgTime) {
                changeText(resultsTV, "Winner: " + currChallenge.getChallengee().getDisplayName());
            } else if (challengerAvgTime > challengeeAvgTime) {
                changeText(resultsTV, "Winner: " + currChallenge.getChallenger().getDisplayName());
            } else {
                changeText(resultsTV, "Tie");
            }
        }
    }

    /***************************************************
     *                                                 *
     *   Change the text of the view to the new text   *
     *                                                 *
     ***************************************************/
    private void changeText(View v, String newText) {
        // Finds the view and sets the text
        ((TextView)findViewById(v.getId())).setText(newText);
    }

    /********************************************
     *                                          *
     *      Starts the main menu activity       *
     *                                          *
     ********************************************/
    private void startMainMenu(View v) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }


    /********************************************************
     *                                                      *
     *         Takes in a list of question stats            *
     *  and returns the average time to answer a question   *
     *                                                      *
     ********************************************************/
    private double getAvg(List<QuestionStats> stats) {
        int total = 0;
        // appends the time to answer for each question to total
        for (QuestionStats stat: stats) {
            total += stat.getTimeToAnswer();
        }
        // Returns the total time in seconds and rounds it to two decimal places
        return Math.round((((double)total / 10.0)/ 1000.0) * 100.0) / 100.0;
    }
}
