package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import a.b.c.quizmania.R;

public class SelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Views
    private Spinner categoryDropDown;
    private Spinner diffDropDown;
    private Spinner typeDropDown;
    private Button playBtn;

    // Strings for dropdown
    private String[] categories = {
            "Random",
            "Entertainment Random",
            "Science Random",
            "General Knowledge",
            "Sports",
            "History & Mythology",
            "Politics",
            "Geography",
            "Video Games",
            "Television & Film",
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
    int[] ids = {0, 0, 0};
    private String uId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        setAppTheme();
        setContentView(R.layout.activity_selection);
        getSupportActionBar().hide();


        // Find Views
        categoryDropDown = (Spinner)findViewById(R.id.category_dropdown);
        diffDropDown = (Spinner)findViewById(R.id.difficulty_dropdown);
        typeDropDown = (Spinner)findViewById(R.id.type_dropdown);
        playBtn = (Button)findViewById(R.id.sp_play_btn);

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
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("CATEGORY", ids[0]);
        intent.putExtra("DIFFICULTY", ids[1]);
        intent.putExtra("TYPE", ids[2]);

        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.category_dropdown:
                Toast.makeText(getApplicationContext(),categories[position] , Toast.LENGTH_LONG).show();
                ids[0] = position;
                break;
            case R.id.difficulty_dropdown:
                Toast.makeText(getApplicationContext(), difficulties[position], Toast.LENGTH_SHORT).show();
                ids[1] = position;
                break;
            case R.id.type_dropdown:
                Toast.makeText(getApplicationContext(), types[position], Toast.LENGTH_SHORT).show();
                ids[2] = position;
                break;
            default:
                break;
        }

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
