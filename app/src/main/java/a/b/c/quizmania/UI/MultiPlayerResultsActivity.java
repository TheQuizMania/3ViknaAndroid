package a.b.c.quizmania.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        displayInfo();
        displayChallenger();
        displayChallengee();
        displayWinner();

        // Setting listeners
        mainMenuBtn.setOnClickListener(v -> startMainMenu(v));
    }

    private void displayInfo() {
        changeText(categoryTV, (categoryTV.getText() + " " + currChallenge.getCategory()));
        changeText(difficultyTV, (difficultyTV.getText() + " " + currChallenge.getDifficulty()));
    }

    private void displayChallenger() {
        double avgTime = getAvg(currChallenge.getChallengerScore().getQuestionStats());
        changeText(challengerNameTV, currChallenge.getChallenger().getDisplayName());
        changeText(challengerRightAnswersTV, (challengerRightAnswersTV.getText() + " " + currChallenge.getChallengerScore().getCorrectAnswers()));
        changeText(challengerAvgTimeTV, (challengerAvgTimeTV.getText() + " " + avgTime));
    }

    private void displayChallengee() {
        double avgTime = getAvg(currChallenge.getChallengeeScore().getQuestionStats());
        changeText(challengeeNameTV, currChallenge.getChallengee().getDisplayName());
        changeText(challengeeRightAnswersTV, (challengeeRightAnswersTV.getText() + " " + currChallenge.getChallengeeScore().getCorrectAnswers()));
        changeText(challengeeAvgTimeTV, (challengeeAvgTimeTV.getText() + " " + avgTime));
    }

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

    private void changeText(View v, String newText) {
        ((TextView)findViewById(v.getId())).setText(newText);
    }

    private void startMainMenu(View v) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }

    private double getAvg(List<QuestionStats> stats) {
        int total = 0;
        for (QuestionStats stat: stats) {
            total += stat.getTimeToAnswer();
        }

        return Math.round((((double)total / 10.0)/ 1000.0) * 100.0) / 100.0;
    }
}
