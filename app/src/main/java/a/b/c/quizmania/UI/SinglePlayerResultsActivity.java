package a.b.c.quizmania.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import a.b.c.quizmania.Entities.CurrentScore;
import a.b.c.quizmania.Entities.QuestionStats;
import a.b.c.quizmania.Entities.Score;
import a.b.c.quizmania.R;

public class SinglePlayerResultsActivity extends AppCompatActivity {

    private TextView category;
    private TextView difficulty;
    private TextView rightAnswers;
    private TextView averageTime;

    private Score resultScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        initVariables();
        populateViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainMenuActivity.class));
    }

    private void initVariables(){
        resultScore = CurrentScore.getCurrScore();
        category = findViewById(R.id.results_category);
        difficulty = findViewById(R.id.results_difficulty);
        rightAnswers = findViewById(R.id.results_right_answers);
        averageTime = findViewById(R.id.results_average_time);
    }

    private void populateViews(){
        category.setText(resultScore.getCategory());
        difficulty.setText(resultScore.getDifficulty());
        rightAnswers.setText(String.format(Locale.US, "%d", resultScore.getCorrectAnswers()));
        averageTime.setText(String.format("%s seconds", String.format(Locale.US, "%d", calculateAverageTime())));
    }

    private double calculateAverageTime() {
        List<QuestionStats> questionList = resultScore.getQuestionStats();
        double totalTimeToAnswer = 0;
        for(QuestionStats q : questionList) {
            totalTimeToAnswer += (double) q.getTimeToAnswer();
        }
        return (totalTimeToAnswer / 10) / 100;
    }

    public void goToMainMenu(View view){
        startActivity(new Intent(this, MainMenuActivity.class));
    }

    public void goToSelection(View view){
        startActivity(new Intent(this, SelectionActivity.class));
    }

}