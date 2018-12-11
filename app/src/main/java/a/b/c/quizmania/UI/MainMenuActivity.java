package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import a.b.c.quizmania.Entities.Challenge;
import a.b.c.quizmania.R;

public class MainMenuActivity extends AppCompatActivity {

    public static List<Challenge> myChallenges;

    // Firebase
    private FirebaseAuth mAuth;

    private TextView nameBox;
    private String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        setAppTheme();
        setContentView(R.layout.activity_main_menu);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Init challenge list
        myChallenges = new ArrayList<>();
        fetchChallenges();

        setContentView(R.layout.activity_main_menu);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Finding views
        // Views
        Button singlePlayerBtn = findViewById(R.id.single_player_btn);
        Button multiPlayerBtn = findViewById(R.id.multi_player_btn);
        Button quickMatchBtn = findViewById(R.id.quick_match_btn);
        Button settingsBtn = findViewById(R.id.settings_btn);
        nameBox = findViewById(R.id.main_menu_title);
        Button matchesBtn = findViewById(R.id.matches_btn);

        getPlayer();

        // Setting Click listeners
        singlePlayerBtn.setOnClickListener(v -> singlePlayer());
        multiPlayerBtn.setOnClickListener(v -> multiPlayer(v));
        quickMatchBtn.setOnClickListener(v -> quickMatch(v));
        settingsBtn.setOnClickListener(v -> goToProfile(v));
        matchesBtn.setOnClickListener(v -> startMatchActivity());

        // Displays the User name
        getPlayer();
    }

    private void quickMatch(View v) {
        Intent intent = new Intent(this, SelectionActivity.class);
        intent.putExtra("QUICK_MATCH", 1);
        startActivity(intent);
    }

    private void startMatchActivity() {
        Intent intent = new Intent(this, ChallengeListActivity.class);
        startActivity(intent);
    }

    private void getPlayer(){
        String user = Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName();

        nameBox.setText(String.format("Welcome\n%s", user));
    }
    private void multiPlayer(View v) {
        if(v.getId() == R.id.multi_player_btn) {
            Intent intent = new Intent(this, UserListActivity.class);
            startActivity(intent);
        }
    }

    private void singlePlayer() {
        Intent intent = new Intent(this, SelectionActivity.class);
        intent.putExtra("MODE", "single");
        startActivity(intent);
    }


    public void goToProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
    private void setAppTheme() {
        SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        assert str != null;
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
        //
    }

    /************************************
     *                                  *
     *    Fetching data from the api    *
     *                                  *
     ************************************/
    private void fetchChallenges() {
        FirebaseDatabase.getInstance().getReference().child("root")
                .child("challenges")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        myChallenges.clear();
                        for(DataSnapshot challengeInstance: dataSnapshot.getChildren()) {
                            Challenge challenge = challengeInstance.getValue(Challenge.class);
                            assert challenge != null;
                            challenge.setId(challengeInstance.getKey());
                            if(challenge.getChallengee().getEmail()
                                    .matches(Objects.requireNonNull(Objects
                                            .requireNonNull(FirebaseAuth.getInstance()
                                            .getCurrentUser())
                                            .getEmail())) && challenge.isActive()) {
                                myChallenges.add(challenge);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
