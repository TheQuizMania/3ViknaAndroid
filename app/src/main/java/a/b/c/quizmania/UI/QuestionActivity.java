package a.b.c.quizmania.UI;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import a.b.c.quizmania.Entities.Question;
import a.b.c.quizmania.Fragments.MultipleChoiseFragment;
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
