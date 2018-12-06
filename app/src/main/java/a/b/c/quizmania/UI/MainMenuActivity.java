package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
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
    FirebaseAuth mAuth;

    // Views
    Button singlePlayerBtn;
    Button multiPlayerBtn;
    Button quickMatchBtn;
    Button settingsBtn;
    TextView nameBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Firebase
        mAuth = FirebaseAuth.getInstance();


        getPlayer();
        try {
            Thread.sleep(1300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
    }
}
