package a.b.c.quizmania.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import a.b.c.quizmania.Entities.Categories;
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
        category.setText(String.format("Category: %s", getCategoryName()));
        difficulty.setText(String.format("Difficulty: %s", resultScore.getDifficulty()));
        rightAnswers.setText(String.format(Locale.US, "%d %s", resultScore.getCorrectAnswers(), "out of 10 right"));
        averageTime.setText(String.format(Locale.US, "%s %s %s", "Average time to answer:", calculateAverageTime(), "seconds"));
    }

    private String calculateAverageTime() {
        List<QuestionStats> questionList = resultScore.getQuestionStats();
        double totalTimeToAnswer = 0;
        for(QuestionStats q : questionList) {
            totalTimeToAnswer += (double) q.getTimeToAnswer();
        }
        double avg = totalTimeToAnswer / 10;
        DecimalFormat df = new DecimalFormat("##.##");
        avg /= 1000;
        return df.format(avg);
    }

    public void goToMainMenu(View view){
        startActivity(new Intent(this, MainMenuActivity.class));
    }

    public void goToSelection(View view){
        startActivity(new Intent(this, SelectionActivity.class));
    }

    private String getCategoryName(){
        List<String> categories = new Categories().getCategories();
        List<String> catNumbers = new Categories().getCatNumbers();

        if(resultScore.getCategory().equals("Random")){
            return resultScore.getCategory();
        } else {
            return categories.get(catNumbers.indexOf(resultScore.getCategory()));
        }

    }

}