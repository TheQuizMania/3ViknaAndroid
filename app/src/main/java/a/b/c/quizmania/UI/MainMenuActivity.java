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
import a.b.c.quizmania.Jobs.MessageSender;
import a.b.c.quizmania.R;

public class MainMenuActivity extends AppCompatActivity {

    public static List<Challenge> myChallenges;

    private MessageSender msgSender;

    // Firebase
    private FirebaseAuth mAuth;

    private TextView nameBox;
    private String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gets the users Id to use for shared preferences
        uID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        //Calls function to set the apps theme to what is stored in the shared preference
        setAppTheme();
        setContentView(R.layout.activity_main_menu);
        //hides the action bar on top
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Init challenge list
        myChallenges = new ArrayList<>();
        fetchChallenges();


        //setContentView(R.layout.activity_main_menu);
        //Objects.requireNonNull(getSupportActionBar()).hide();

        // Finding views
        // Views
        Button singlePlayerBtn = findViewById(R.id.single_player_btn);
        Button multiPlayerBtn = findViewById(R.id.multi_player_btn);
        Button quickMatchBtn = findViewById(R.id.quick_match_btn);
        Button profileBtn = findViewById(R.id.profile_btn);
        nameBox = findViewById(R.id.main_menu_title);
        Button matchesBtn = findViewById(R.id.matches_btn);

        // Setting Click listeners
        singlePlayerBtn.setOnClickListener(v -> singlePlayer());
        multiPlayerBtn.setOnClickListener(v -> multiPlayer(v));
        quickMatchBtn.setOnClickListener(v -> quickMatch());
        profileBtn.setOnClickListener(v -> goToProfile(v));
        matchesBtn.setOnClickListener(v -> startMatchActivity());


        // Displays the User name
        getPlayer();

        msgSender = new MessageSender();
        msgSender.onNewToken();
    }
    private void setNotificationCircle() {
        Button matchesBtn = findViewById(R.id.matches_btn);
            switch (myChallenges.size()){
            case 0:
                break;
            case 1:
                matchesBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.round_lens_red1_18dp, 0);
                break;
            case 2:
                matchesBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.round_lens_red2_18dp, 0);
                break;
            case 3:
                matchesBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.round_lens_red3_18dp, 0);
                break;
            case 4:
                matchesBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.round_lens_red4_18dp, 0);
                break;
            case 5:
                matchesBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.round_lens_red4_18dp, 0);
                break;
            case 6:
                matchesBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.round_lens_red6_18dp, 0);
                break;
            case 7:
                matchesBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.round_lens_red7_18dp, 0);
                break;
            case 8:
                matchesBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.round_lens_red8_18dp, 0);
                break;
            case 9:
                matchesBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.round_lens_red9_18dp, 0);
                break;
            default:
                matchesBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.round_lens_red9plus_18dp, 0);
                break;
            }
    }

    private void quickMatch() {
        //Starts the SelectionActivity with onClick an extra, extra makes sure to select
        //a random difficulty and puts the users straight into a game (Singleplayer)
        Intent intent = new Intent(this, SelectionActivity.class);
        intent.putExtra("QUICK_MATCH", 1);
        startActivity(intent);
    }

    private void startMatchActivity() {
        //Starts the ChallengerListActivity onClick
        Intent intent = new Intent(this, ChallengeListActivity.class);
        startActivity(intent);
    }

    private void getPlayer(){
        //fetches the logged in user and displays his username in a TextView
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
        //Takes the user to the SelectionActivity for a singleplayer game
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
        //fetches shared preferences with uID key
        SharedPreferences pref = getSharedPreferences(uID, MODE_PRIVATE);
        String str = pref.getString("THEME_PREF", "AppTheme");
        assert str != null;
        if(str.equals("AppTheme")) {
            //Sets activity to light mode
            setTheme(R.style.AppTheme);
        }else{
            //Sets activity to dark mode
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
                        setNotificationCircle();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
