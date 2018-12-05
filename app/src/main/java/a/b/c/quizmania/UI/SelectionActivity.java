package a.b.c.quizmania.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import a.b.c.quizmania.R;

public class SelectionActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        getSupportActionBar().hide();


    }
}
