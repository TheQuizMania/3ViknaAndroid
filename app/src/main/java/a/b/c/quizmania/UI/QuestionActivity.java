package a.b.c.quizmania.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import a.b.c.quizmania.R;

public class QuestionActivity extends AppCompatActivity {

    private int category;
    private String difficulty;
    private String type;

//    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }
}
