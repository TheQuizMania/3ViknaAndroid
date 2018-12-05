package a.b.c.quizmania.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import a.b.c.quizmania.R;

public class MainMenuActivity extends AppCompatActivity {

    // Views
    Button signOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();

        // Finding views
        signOutBtn = findViewById(R.id.sign_out);

        // Setting Click listeners
        signOutBtn.setOnClickListener(v -> signOut(v));
    }

    // Sign Out account
    private void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
