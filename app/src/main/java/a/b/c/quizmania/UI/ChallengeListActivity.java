package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import a.b.c.quizmania.R;
import a.b.c.quizmania.db.ChallengeRVAdapter;

import static a.b.c.quizmania.Entities.StaticVariables.currChallenge;
import static a.b.c.quizmania.UI.MainMenuActivity.myChallenges;
import static a.b.c.quizmania.Entities.StaticVariables.question;


public class ChallengeListActivity extends AppCompatActivity implements ChallengeRVAdapter.ItemClickListener{

    private ChallengeRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        setContentView(R.layout.activity_match_liist);
        Objects.requireNonNull(getSupportActionBar()).hide();
        listAllMatches();

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Clicked " + position, Toast.LENGTH_SHORT).show();
        currChallenge = adapter.getChallenge(position);
        Intent intent = new Intent(this, QuestionActivity.class);
        if(currChallenge.getCategory().matches("Random")) {
            intent.putExtra("CATEGORY", "");
        } else {
            intent.putExtra("CATEGORY", "&category=" + currChallenge.getCategory());
        }
        intent.putExtra("DIFFICULTY", "&difficulty=" + currChallenge.getDifficulty());
        intent.putExtra("MODE", "CHALLENGEE");
        question = currChallenge.getQuestion();
        startActivity(intent);
        finish();
    }

    private void listAllMatches() {
        RecyclerView rv = findViewById(R.id.rv_matches);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChallengeRVAdapter(this, myChallenges);
        adapter.setClickListener(this);
        rv.setAdapter(adapter);
    }

    private void setAppTheme() {
        String uID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        assert str != null;
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }
}
