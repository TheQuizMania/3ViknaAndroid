package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import a.b.c.quizmania.Entities.User;
import a.b.c.quizmania.R;

public class MainMenuActivity extends AppCompatActivity {

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

        setContentView(R.layout.activity_main_menu);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Finding views
        singlePlayerBtn = findViewById(R.id.single_player_btn);
        multiPlayerBtn = findViewById(R.id.multi_player_btn);
        quickMatchBtn = findViewById(R.id.quick_match_btn);
        settingsBtn = findViewById(R.id.settings_btn);
        nameBox = findViewById(R.id.main_menu_title);

        getPlayer();

        // Setting Click listeners
        singlePlayerBtn.setOnClickListener(v -> singlePlayer(v));
        multiPlayerBtn.setOnClickListener(v -> multiPlayer(v));
        quickMatchBtn.setOnClickListener(v -> multiPlayer(v));
        settingsBtn.setOnClickListener(v -> goToProfile(v));
    }

    private void getPlayer(){
        String userId = mAuth.getCurrentUser().getDisplayName();

        nameBox.setText("Welcome\n" + userId);
    }
    private void multiPlayer(View v) {
        if(v.getId() == R.id.multi_player_btn) {

        } else {

        }
    }

    private void singlePlayer(View v) {
        Intent intent = new Intent(this, SelectionActivity.class);
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
}
