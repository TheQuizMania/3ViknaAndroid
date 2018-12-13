package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;

import java.util.Objects;
import java.util.Random;

import a.b.c.quizmania.Entities.Question;
import a.b.c.quizmania.R;

import static a.b.c.quizmania.Entities.StaticVariables.pendingChallenge;
import static a.b.c.quizmania.Entities.StaticVariables.question;

/**
 * An activity for selecting game settings
 */
public class SelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Question variable that is used in QuestionDisplayFragment
    // Url for the api that will be appended strings
    public String url = "https://opentdb.com/api.php?amount=10";

    private Button playBtn;

    // Strings for dropdown
    private String[] categories = {
            "Random",
            "Film",
            "Science & nature",
            "General Knowledge",
            "Sports",
            "Mythology",
            "Politics",
            "Geography",
            "Video Games",
            "Television",
            "Music"};
    private String[] difficulties = {
            "Easy",
            "Medium",
            "Hard"
    };

    // String that will be given values for the chosen from a specified dropdown
    String selectedCategory;
    String cat;
    String selectedDifficulty;

    String mode = "";

    int challengeId;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //else covers everything, so it doesn't run when you quickplay.
        mAuth = FirebaseAuth.getInstance();
        //Check if mAuth is null, this is so the tests run
        if(mAuth.getCurrentUser() != null){
            setAppTheme();
        }
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Checks whether the extra is equal to 1, if so the user clicked quick play and will be automatically put
        //into a single player game with a random difficulty
        int quickPlayCheck = getIntent().getIntExtra("QUICK_MATCH", -1);
        if(quickPlayCheck == 1){
            setMode();
            runQuickPlay();
        }else {

            setContentView(R.layout.activity_selection);

            setMode();

            challengeId = getIntent().getIntExtra("CHALLENGEID", -1);


            // Initialize the selection strings as empty strings
            selectedCategory = "";
            selectedDifficulty = "easy";

            // Find Views
            // Views
            Spinner categoryDropDown = findViewById(R.id.category_dropdown);
            Spinner diffDropDown = findViewById(R.id.difficulty_dropdown);
            playBtn = findViewById(R.id.sp_play_btn);

            // Listeners
            categoryDropDown.setOnItemSelectedListener(this);
            diffDropDown.setOnItemSelectedListener(this);

            playBtn.setOnClickListener(v -> playGame());

            // Fill in Drop down

            // For category
            ArrayAdapter categoryAA = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
            categoryAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categoryDropDown.setAdapter(categoryAA);

            // For difficulty
            ArrayAdapter diffAA = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, difficulties);
            diffAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            diffDropDown.setAdapter(diffAA);
        }
    }
    private void setMode() {
        mode = getIntent().getStringExtra("MODE");
        //Svo test keyri
        if (mode == null) {
            mode = "";
        }
    }

    private void runQuickPlay() {

        //gets random difficulty
        String difficultyString = getRandomDifficulty();


        //Fetches questions from the api from all categories(random) and a random difficulty
        Ion.with(this)
                .load(url + "&difficulty=" + difficultyString + "&type=multiple")
                .asString()
                .setCallback((e, result) -> {
                    // Converts the result from the api to a Question.class
                    Gson gson = new Gson();
                    question = gson.fromJson(result, Question.class);

                    //if the response code in not 0 the api fetch was not successful
                    //recreates the activity to try again
                    //this can happen when there is not enough question in a particular category/difficulty combination
                    //or if the api fetch is too slow
                    if(question.getResponseCode() != 0){
                        recreate();
                    }else{ //if the response code is 0 , it starts the QuestionActivity with the
                            //correct extras.
                        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                        // Sets the intent Extras
                        setExtrasIntent(intent, "", difficultyString, "");
                        startActivity(intent);
                        finish();
                    }

                });
    }
    private String getRandomDifficulty() {
        //random a number from 1-3 and chooses a difficulty accordingly
        Random rand = new Random();
        int i = rand.nextInt(3) + 1;
        switch (i) {
            case 1:
                return "easy";
            case 2:
                return "medium";
            case 3:
                return "hard";
            default:
                return "easy";
        }
    }

    private void playGame() {
        // Gets the questions on click, and starts the next activity
        getQuestions();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("SELECTION_INTENT", "onItemSelected() called");
        // Checks which drop down is pressed
        switch(parent.getId()){
            case R.id.category_dropdown:
//                Toast.makeText(getApplicationContext(),categories[position] , Toast.LENGTH_LONG).show();
                selectedCategory = getCategory(categories[position]);
                break;
            case R.id.difficulty_dropdown:
//                Toast.makeText(getApplicationContext(), difficulties[position], Toast.LENGTH_SHORT).show();
                selectedDifficulty = difficulties[position].toLowerCase();
                break;
            default:
                break;
        }
    }
    private String getCategory(String category) {
        // If you changed category it will return a string with &category={selected category}
        cat = category;
        Log.d("SELECTION_INTENT", cat);
        switch (category) {
            case "Random":
                cat = "";
                return "";
            case "Film":
                return "11";
            case "Science & nature":
                return "17";
            case "General Knowledge":
                return  "9";
            case "Sports":
                return "21";
            case "Mythology":
                return "20";
            case "Politics":
                return "24";
            case "Geography":
                return "22";
            case "Video Games":
                return "15";
            case "Television":
                return "14";
            case "Music":
                return "12";
            default:
                return "";
        }
    }

    private void getQuestions() {
        // Makes the buttons unclickable
        // Gets the questions from the api
      playBtn.setClickable(false);
      playBtn.setText(getString(R.string.quiz_unavaliable));
      Ion.with(this)
              .load(url + "&category=" + selectedCategory + "&difficulty=" + selectedDifficulty + "&type=multiple")
              .asString()
              .setCallback((e, result) -> {
                  // Converts the result from the api to a Question.class
                  Gson gson = new Gson();
                  question = gson.fromJson(result, Question.class);

                  //If the response code is not 0, something went wrong fetching from the api
                  //Some category/difficulty combinations fail due to lack of questions
                  //this lets the user know and recreates the activity
                  if(question.getResponseCode() != 0){
                      Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
                      recreate();
                  }else{ //if fetching from the api was successful, start the QuestionActivity
                      Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                      setExtrasIntent(intent, cat, selectedDifficulty, mode);
                      //make the button clickable again so when the user returns it isn't disabled
                      playBtn.setClickable(true);
                      playBtn.setText(getString(R.string.quiz_avaliable));
                      startActivity(intent);
                      finish();
                  }
              });
    }
    private void setExtrasIntent(Intent intent, String c, String d, String m) {
        //c = category string,
        //d = difficulty string,
        //m = mode String
        intent.putExtra("CATEGORY", c);
        intent.putExtra("DIFFICULTY", d);
        intent.putExtra("MODE", m);
        if (mode.matches("CHALLENGER")) {
            pendingChallenge.setQuestion(question);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    private void setAppTheme() {
        String uId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        SharedPreferences pref = getSharedPreferences(uId, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        assert str != null;
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }
}
