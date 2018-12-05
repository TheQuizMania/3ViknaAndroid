package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import a.b.c.quizmania.R;

public class MainMenuActivity extends AppCompatActivity {

    // Firebase
    FirebaseAuth mAuth;

    // Views
    Button signOutBtn;
    Button singlePlayerBtn;
    Button multiPlayerBtn;
    Button quickmatchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Finding views
        signOutBtn = (Button)findViewById(R.id.sign_out);
        singlePlayerBtn =  (Button)findViewById(R.id.singleplayer_btn);
        multiPlayerBtn = (Button)findViewById(R.id.multiplayer_btn);
        quickmatchBtn = (Button)findViewById(R.id.quickmatch_btn);

        // Setting Click listeners
        signOutBtn.setOnClickListener(v -> signOut(v));
        singlePlayerBtn.setOnClickListener(v -> singlePlayer(v));
        multiPlayerBtn.setOnClickListener(v -> multiPlayer(v));
        quickmatchBtn.setOnClickListener(v -> multiPlayer(v));



        ((TextView)findViewById(R.id.test_show)).setText(mAuth.getCurrentUser().getDisplayName());
    }

    private void multiPlayer(View v) {
        if(v.getId() == R.id.multiplayer_btn) {

        } else {

        }
    }

    private void singlePlayer(View v) {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }

    // Sign Out account
    private void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }


}
