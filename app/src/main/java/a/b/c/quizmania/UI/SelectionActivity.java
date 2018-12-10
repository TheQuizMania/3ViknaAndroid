package a.b.c.quizmania.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import a.b.c.quizmania.Entities.Question;

import a.b.c.quizmania.R;

public class SelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Question variable that is used in QuestionDisplayFragment
    public static Question question;
    // Url for the api that will be appended strings
    public String url = "https://opentdb.com/api.php?amount=10";

    private static FirebaseDatabase INSTANCE = null;
    public static void setInstance(FirebaseDatabase instance){
        INSTANCE = instance;
    }

    // Views
    private Spinner categoryDropDown;
    private Spinner diffDropDown;
    private Spinner typeDropDown;
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
    private String[] types = {
            "Both",
            "Multiple choice",
            "True or False",
    };

    // String that will be given values for the chosen from a specified dropdown
    String selectedCategory;
    String selectedDifficulty;
    String selectedType;
    private String uId;
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;


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

        // Initialize the selection strings as empty strings
        selectedCategory = "";
        selectedDifficulty = "&difficulty=easy";
        selectedType = "&type=multiple";

        // Find Views
        categoryDropDown = findViewById(R.id.category_dropdown);
        diffDropDown = findViewById(R.id.difficulty_dropdown);
        typeDropDown = findViewById(R.id.type_dropdown);
        playBtn = findViewById(R.id.sp_play_btn);

        // Listeners
        categoryDropDown.setOnItemSelectedListener(this);
        diffDropDown.setOnItemSelectedListener(this);
        typeDropDown.setOnItemSelectedListener(this);

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

        // For type
        ArrayAdapter typeAA = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types);
        typeAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeDropDown.setAdapter(typeAA);



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
            case R.id.type_dropdown:
//                Toast.makeText(getApplicationContext(), types[position], Toast.LENGTH_SHORT).show();
                selectedType = getType(types[position]);
                break;
            default:
                break;
        }
    }

    private String getType(String type) {
        // If you changed type it will return a string with &type={selected type}
        String retVal = "&type=";
        switch (type) {
            case "Multiple choice":
                return retVal + "multiple";
            case "Both":
                return retVal + "multiple";
            case "True or False":
                return retVal + "multiple";
            default:
                return retVal + "multiple";
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
                .load(url + selectedCategory + selectedDifficulty + selectedType)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        // Converts the result from the api to a Question.class
                        Gson gson = new Gson();
                        question = gson.fromJson(result, Question.class);

                        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                        intent.putExtra("CATEGORY", selectedCategory);
                        intent.putExtra("DIFFICULTY", selectedDifficulty);
                        intent.putExtra("TYPE", selectedType);

                        // Makes the play button clickable again
                        playBtn.setClickable(true);
                        playBtn.setText(getString(R.string.quiz_avaliable));

                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
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
