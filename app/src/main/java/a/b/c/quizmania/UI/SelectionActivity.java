package a.b.c.quizmania.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import a.b.c.quizmania.R;

public class SelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner categoryDropDown;

    String[] categories = {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        getSupportActionBar().hide();


        // Find Views
        categoryDropDown = (Spinner)findViewById(R.id.category_dropdown);

        // Listeners
        categoryDropDown.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoryDropDown.setAdapter(aa);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),categories[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
