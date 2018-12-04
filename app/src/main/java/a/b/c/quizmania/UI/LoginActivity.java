package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import a.b.c.quizmania.R;

public class LoginActivity extends AppCompatActivity {

    Button signInBtn;
    TextView registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        signInBtn = (Button)findViewById(R.id.sign_in);
        registerBtn = (TextView)findViewById(R.id.register_me);

        signInBtn.setOnClickListener(v -> signIn(v));
        registerBtn.setOnClickListener(v -> registerMe(v));

    }

    private void signIn(View view) {
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }

    private void registerMe(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);

    }

}
