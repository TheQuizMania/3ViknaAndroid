package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;

import java.util.Objects;
import java.util.Random;

import a.b.c.quizmania.Entities.Question;
import a.b.c.quizmania.R;

import static a.b.c.quizmania.Entities.StaticVariables.pendingChallenge;
import static a.b.c.quizmania.Entities.StaticVariables.question;

public class SelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Question variable that is used in QuestionDisplayFragment
    // Url for the api that will be appended strings
    public String url = "https://opentdb.com/api.php?amount=10";

    private static FirebaseDatabase INSTANCE = null;
//    public static void setInstance(FirebaseDatabase instance){
//        INSTANCE = instance;
//    }

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
    private String uId;
    String mode = "";
    int challengeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int quickPlayCheck = getIntent().getIntExtra("QUICK_MATCH", -1);
        if(quickPlayCheck == 1){
            setMode();
            runQuickPlay();
        }else {
            //else covers everything, so it doesn't run when you quickplay.
            setAppTheme();
            setContentView(R.layout.activity_selection);
            Objects.requireNonNull(getSupportActionBar()).hide();

            FirebaseDatabase db = INSTANCE;
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            uId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

            setMode();

            challengeId = getIntent().getIntExtra("CHALLENGEID", -1);


            // Initialize the selection strings as empty strings
            selectedCategory = "";
            selectedDifficulty = "&difficulty=easy";

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

        String difficultyString = getRandomDifficulty();


        Ion.with(this)
                .load(url + difficultyString + "&type=multiple")
                .asString()
                .setCallback((e, result) -> {
                    // Converts the result from the api to a Question.class
                    Gson gson = new Gson();
                    question = gson.fromJson(result, Question.class);

                    Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                    setExtrasIntent(intent, "", difficultyString, "");
                    startActivity(intent);
                    finish();
                });
    }
    private String getRandomDifficulty() {
        Random rand = new Random();
        int i = rand.nextInt(3) + 1;
        switch (i) {
            case 1:
                return "&difficulty=easy";
            case 2:
                return "&difficulty=medium";
            case 3:
                return "difficulty=hard";
            default:
                return "&difficulty=easy";
        }
    }

    private void playGame() {
        // Gets the questions on click, and starts the next activity
        getQuestions();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Checks which drop down is pressed
        switch(parent.getId()){
            case R.id.category_dropdown:
//                Toast.makeText(getApplicationContext(),categories[position] , Toast.LENGTH_LONG).show();
                selectedCategory = getCategory(categories[position]);
                break;
            case R.id.difficulty_dropdown:
//                Toast.makeText(getApplicationContext(), difficulties[position], Toast.LENGTH_SHORT).show();
                selectedDifficulty = "&difficulty=" + difficulties[position].toLowerCase();
                break;
            default:
                break;
        }
    }
    private String getCategory(String category) {
        // If you changed category it will return a string with &category={selected category}
        String retVal = "&category=";
        cat = retVal + category;
        switch (category) {
            case "Random":
                cat = "";
                return "";
            case "Film":
                return retVal + "11";
            case "Science & nature":
                return retVal + "17";
            case "General Knowledge":
                return retVal + "9";
            case "Sports":
                return retVal + "21";
            case "Mythology":
                return retVal + "20";
            case "Politics":
                return retVal + "24";
            case "Geography":
                return retVal + "22";
            case "Video Games":
                return retVal + "15";
            case "Television":
                return retVal + "14";
            case "Music":
                return retVal + "12";
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
              .load(url + selectedCategory + selectedDifficulty + "&type=multiple")
              .asString()
              .setCallback((e, result) -> {
                  // Converts the result from the api to a Question.class
                  Gson gson = new Gson();
                  question = gson.fromJson(result, Question.class);

                  Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                  setExtrasIntent(intent, cat, selectedDifficulty, mode);
                  playBtn.setClickable(true);
                  playBtn.setText(getString(R.string.quiz_avaliable));
                  startActivity(intent);
                  finish();
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
