package a.b.c.quizmania.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Finding views
        signOutBtn = findViewById(R.id.sign_out);

        // Setting Click listeners
        signOutBtn.setOnClickListener(v -> signOut(v));



        ((TextView)findViewById(R.id.test_show)).setText(mAuth.getCurrentUser().getDisplayName());
    }

    // Sign Out account
    private void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
