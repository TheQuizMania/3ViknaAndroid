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

    public static List<Challenge> challengeList;
    public static List<Challenge> myChallenges;

    // Firebase
    private FirebaseAuth mAuth;

    // Views
    private Button singlePlayerBtn;
    private Button multiPlayerBtn;
    private Button quickMatchBtn;
    private Button settingsBtn;
    private TextView nameBox;

    //
    private String theme;
    private String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        setAppTheme();
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Init challenge list
        challengeList = new ArrayList<>();
        myChallenges = new ArrayList<>();
        fetchChallenges();

        setContentView(R.layout.activity_main_menu);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Finding views
        singlePlayerBtn = findViewById(R.id.single_player_btn);
        multiPlayerBtn = findViewById(R.id.multi_player_btn);
        quickMatchBtn = findViewById(R.id.quick_match_btn);
        settingsBtn = findViewById(R.id.settings_btn);
        nameBox = findViewById(R.id.main_menu_title);

        // Setting Click listeners
        singlePlayerBtn.setOnClickListener(v -> singlePlayer(v));
        multiPlayerBtn.setOnClickListener(v -> multiPlayer(v));
        quickMatchBtn.setOnClickListener(v -> multiPlayer(v));
        settingsBtn.setOnClickListener(v -> goToProfile(v));

        // Displays the User name
        getPlayer();
    }

    private void getPlayer(){
        String userId = mAuth.getCurrentUser().getDisplayName();

        nameBox.setText("Welcome\n" + userId);
    }
    private void multiPlayer(View v) {
        if(v.getId() == R.id.multi_player_btn) {
            Intent intent = new Intent(this, UserListActivity.class);
            startActivity(intent);
        } else {

        }
    }

    private void singlePlayer(View v) {
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
        if(str.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
        theme = str;
    }


    private void fetchChallenges() {
        FirebaseDatabase.getInstance().getReference().child("root")
                .child("challenges")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot challengeInstance: dataSnapshot.getChildren()) {
                            Challenge challenge = challengeInstance.getValue(Challenge.class);
                            challengeList.add(challenge);
                            if(challenge.getChallengee().getEmail().matches(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
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
