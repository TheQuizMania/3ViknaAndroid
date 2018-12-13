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
import a.b.c.quizmania.utilities.ChallengeRVAdapter;

import static a.b.c.quizmania.Entities.StaticVariables.currChallenge;
import static a.b.c.quizmania.Entities.StaticVariables.question;
import static a.b.c.quizmania.UI.MainMenuActivity.myChallenges;

/**
 * An activity that shows all challenges for the current user
 */
public class ChallengeListActivity extends AppCompatActivity implements ChallengeRVAdapter.ItemClickListener{

    private ChallengeRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        setContentView(R.layout.activity_challenge_list);
        Objects.requireNonNull(getSupportActionBar()).hide();
        listAllMatches();

    }

    @Override
    public void onItemClick(View view, int position) {
        //When you click a challenge in the list it starts it
        Toast.makeText(this, "Clicked " + position, Toast.LENGTH_SHORT).show();
        currChallenge = adapter.getChallenge(position);
        Intent intent = new Intent(this, QuestionActivity.class);
        if(currChallenge.getCategory().matches("Random")) {
            intent.putExtra("CATEGORY", "");
        } else {
            intent.putExtra("CATEGORY", currChallenge.getCategory());
        }
        intent.putExtra("DIFFICULTY", currChallenge.getDifficulty());
        intent.putExtra("MODE", "CHALLENGEE");
        question = currChallenge.getQuestion();

        startActivity(intent);
        finishAffinity();
    }

    private void listAllMatches() {
        //lists all challenges in the recycler view
        RecyclerView rv = findViewById(R.id.rv_matches);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChallengeRVAdapter(this, myChallenges);
        adapter.setClickListener(this);
        rv.setAdapter(adapter);
    }

    private void setAppTheme() {
        //reads your theme from the shared preferences
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
