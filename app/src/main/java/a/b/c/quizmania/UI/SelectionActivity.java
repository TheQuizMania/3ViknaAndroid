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
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import a.b.c.quizmania.Entities.Question;
import a.b.c.quizmania.R;

import static a.b.c.quizmania.Entities.StaticVariables.pendingChallenge;
import static a.b.c.quizmania.Entities.StaticVariables.question;

public class SelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Question variable that is used in QuestionDisplayFragment
    // Url for the api that will be appended strings
    public String url = "https://opentdb.com/api.php?amount=10";

    private static FirebaseDatabase INSTANCE = null;
    public static void setInstance(FirebaseDatabase instance){
        INSTANCE = instance;
    }

    // Views
    private Spinner categoryDropDown;
    private Spinner diffDropDown;
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
    String selectedDifficulty;
    private String uId;
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    String mode;
    int challengeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Þetta if/else statement er svo testin keyri
        db = INSTANCE;
        if(db == null){
            FirebaseDatabase.getInstance();
            uId = "";
        }else{
            mAuth = FirebaseAuth.getInstance();
            uId = mAuth.getCurrentUser().getUid();
        }
        setAppTheme();
        setContentView(R.layout.activity_selection);
        getSupportActionBar().hide();

        mode = getIntent().getStringExtra("MODE");
        challengeId = getIntent().getIntExtra("CHALLENGEID", -1);


        // Initialize the selection strings as empty strings
        selectedCategory = "";
        selectedDifficulty = "&difficulty=easy";

        // Find Views
        categoryDropDown = findViewById(R.id.category_dropdown);
        diffDropDown = findViewById(R.id.difficulty_dropdown);
        playBtn = findViewById(R.id.sp_play_btn);

        // Listeners
        categoryDropDown.setOnItemSelectedListener(this);
        diffDropDown.setOnItemSelectedListener(this);

        playBtn.setOnClickListener(v -> playGame(v));

        // Fill in Drop down

        // For category
        ArrayAdapter categoryAA = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categoryAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryDropDown.setAdapter(categoryAA);

        // For difficulty
        ArrayAdapter diffAA = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, difficulties);
        diffAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diffDropDown.setAdapter(diffAA);
    }

    private void playGame(View v) {
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
        switch (category) {
            case "Random":
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
              .setCallback(new FutureCallback<String>() {
                  @Override
                  public void onCompleted(Exception e, String result) {
                      // Converts the result from the api to a Question.class
                      Gson gson = new Gson();
                      question = gson.fromJson(result, Question.class);
                      // Makes the play button clickable again
                      //playBtn.setOnClickListener(v -> playGame(v));
                      //playBtn.setText(getString(R.string.quiz_avaliable));
                      Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                      intent.putExtra("CATEGORY", selectedCategory);
                      intent.putExtra("DIFFICULTY", selectedDifficulty);
                      intent.putExtra("MODE", mode);
                      if (mode.matches("CHALLENGER")) {
                          pendingChallenge.setQuestion(question);
                      }

                      playBtn.setClickable(true);
                      playBtn.setText(getString(R.string.quiz_avaliable));
                      startActivity(intent);
                      finish();
                  }
              });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    private void setAppTheme() {
        SharedPreferences pref = getSharedPreferences(uId, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }
}
