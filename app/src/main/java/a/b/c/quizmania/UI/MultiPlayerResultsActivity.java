package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import a.b.c.quizmania.Entities.Challenge;
import a.b.c.quizmania.Entities.QuestionStats;
import a.b.c.quizmania.Entities.Score;
import a.b.c.quizmania.Jobs.MessageSender;
import a.b.c.quizmania.R;
import a.b.c.quizmania.utilities.Utility;

import static a.b.c.quizmania.Entities.StaticVariables.currChallenge;

/**
 * An activity that displays the results of a multiplayer match
 */
public class MultiPlayerResultsActivity extends AppCompatActivity {

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

    MessageSender msgSender;
    Challenge newChallenge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        setContentView(R.layout.activity_multi_player_result);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Finding Views
        categoryTV = findViewById(R.id.mp_results_category);
        difficultyTV = findViewById(R.id.mp_results_difficulty);

        challengerNameTV = findViewById(R.id.mp_results_challenger);
        challengerRightAnswersTV = findViewById(R.id.mp_challenger_results_right_answers);
        challengerAvgTimeTV = findViewById(R.id.mp_challenger_results_average_time);

        challengeeNameTV = findViewById(R.id.mp_results_challengee);
        challengeeRightAnswersTV = findViewById(R.id.mp_challengee_results_right_answers);
        challengeeAvgTimeTV = findViewById(R.id.mp_challengee_results_average_time);

        resultsTV = findViewById(R.id.mp_results_result);

        mainMenuBtn = findViewById(R.id.mp_main_menu_btn);

        if(currChallenge != null && FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(currChallenge.getChallengee().getEmail())){
            msgSender = new MessageSender();
            msgSender.sendResults(currChallenge.getChallenger().getPushToken(), currChallenge.getId());
        }
        currChallenge = null;
        String cID = getIntent().getStringExtra("challengeID");

        FirebaseDatabase.getInstance().getReference().child("root")
            .child("challenges")
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot instance: dataSnapshot.getChildren()) {
                        Challenge ch = instance.getValue(Challenge.class);
                        if(ch.getId().matches(cID)) {
                            currChallenge = ch;
                            // Displays the information
                            displayInfo();
                            // Displays the results of the challenger
                            displayChallenger();
                            // Displays the results of the challengee
                            displayChallengee();
                            // Displays which one is the winner
                            displayWinner();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




//        if(currChallenge != null){
//
//            // Displays the information
//            displayInfo();
//            // Displays the results of the challenger
//            displayChallenger();
//            // Displays the results of the challengee
//            displayChallengee();
//            // Displays which one is the winner
//            displayWinner();
//
//        }
        // Setting listeners
        mainMenuBtn.setOnClickListener(v -> startMainMenu());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currChallenge = null;
        finish();
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

    /***************************************************
     *                                                 *
     *                Display basic info               *
     *                                                 *
     ***************************************************/
    private void displayInfo() {
        // Displays the category
        changeText(categoryTV, ("Category: " + currChallenge.getCategory() + "\n"));
        // Displays the difficulty
        changeText(difficultyTV, ("Difficulty: " + currChallenge.getDifficulty() + "\n"));
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
        changeText(challengerNameTV, "Player name:\n" + currChallenge.getChallenger().getDisplayName() + "\n");
        changeText(challengerRightAnswersTV,
                (String.format(Locale.US, "%s\n%d/10", getString(R.string.answerdCorrect), currChallenge.getChallengerScore().getCorrectAnswers())));
        changeText(challengerAvgTimeTV, String.format("%s\n%s", getString(R.string.avgTimeToAnswer), avgTime));
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
        changeText(challengeeNameTV, "Player name:\n" + currChallenge.getChallengee().getDisplayName() + "\n");
        changeText(challengeeRightAnswersTV,
                (String.format(Locale.US, "%s\n%d/10", getString(R.string.answerdCorrect), currChallenge.getChallengeeScore().getCorrectAnswers())));
        changeText(challengeeAvgTimeTV, String.format("%s\n%s", getString(R.string.avgTimeToAnswer), avgTime));
    }

    /************************************
     *                                  *
     *        Display the Winner        *
     *                                  *
     ************************************/
    private void displayWinner() {
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        Score challengerScore, challengeeScore;
        challengerScore = currChallenge.getChallengerScore();
        challengeeScore = currChallenge.getChallengeeScore();
        int challengerRA, challengeeRA;
        challengerRA = challengerScore.getCorrectAnswers();
        challengeeRA = challengeeScore.getCorrectAnswers();
        if(challengerRA < challengeeRA) {
            changeText(resultsTV, "Winner:\n" + currChallenge.getChallengee()
                    .getDisplayName());
            assert currUser != null;
            Utility.setWinsAndLosses(currUser, isWinner(currUser));
        } else if (challengerRA > challengeeRA) {
            changeText(resultsTV, "Winner:\n" + currChallenge.getChallenger()
                    .getDisplayName());
            assert currUser != null;
            Utility.setWinsAndLosses(currUser, isWinner(currUser));
        } else {
            double challengerAvgTime = getAvg(currChallenge.getChallengerScore()
                    .getQuestionStats());
            double challengeeAvgTime
                    = getAvg(currChallenge
                        .getChallengeeScore()
                        .getQuestionStats());
            if(challengerAvgTime > challengeeAvgTime) {
                changeText(resultsTV, "Winner: " + currChallenge.getChallengee()
                        .getDisplayName());
                assert currUser != null;
                Utility.setWinsAndLosses(currUser, isWinner(currUser));
            } else if (challengerAvgTime > challengeeAvgTime) {
                changeText(resultsTV, "Winner: " + currChallenge.getChallenger()
                        .getDisplayName());
                assert currUser != null;
                Utility.setWinsAndLosses(currUser, isWinner(currUser));
            } else {
                changeText(resultsTV, "Draw");
            }
        }
    }

    private boolean isWinner(FirebaseUser user){
        Score player;
        Score opponent;
        if(Objects.equals(user.getEmail(), currChallenge.getChallenger().getEmail())){
            player = currChallenge.getChallengerScore();
            opponent = currChallenge.getChallengeeScore();
            if(player.getCorrectAnswers() > opponent.getCorrectAnswers()){
                return true;
            } else if(player.getCorrectAnswers() == opponent.getCorrectAnswers()){
                return getAvg(player.getQuestionStats()) > getAvg(opponent.getQuestionStats());
            } else {
                return false;
            }
        } else if(Objects.equals(user.getEmail(), currChallenge.getChallengee().getEmail())){
            player = currChallenge.getChallengeeScore();
            opponent = currChallenge.getChallengerScore();
            if(player.getCorrectAnswers() > opponent.getCorrectAnswers()){
                return true;
            } else if(player.getCorrectAnswers() == opponent.getCorrectAnswers()){
                return getAvg(player.getQuestionStats()) < getAvg(opponent.getQuestionStats());
            } else {
                return false;
            }
        }
        return false;
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
    private void startMainMenu() {
        currChallenge = null;
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finishAffinity();
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
