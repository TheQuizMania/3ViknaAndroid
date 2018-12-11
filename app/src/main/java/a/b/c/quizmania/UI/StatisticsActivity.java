package a.b.c.quizmania.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import a.b.c.quizmania.Entities.Categories;
import a.b.c.quizmania.Entities.Score;
import a.b.c.quizmania.Entities.User;
import a.b.c.quizmania.R;

public class StatisticsActivity extends AppCompatActivity {

    //variables
    //List that stores all statistics for current user
    private List<Score> scores;

    //Views
    private TextView totalRight;
    private TextView totalWrong;
    private TextView totalRatio;

    private TextView mPWins;
    private TextView mPLosses;
    private TextView winLossRatio;

    private TextView easyRight;
    private TextView easyWrong;
    private TextView easyRatio;

    private TextView mediumRight;
    private TextView mediumWrong;
    private TextView mediumRatio;

    private TextView hardRight;
    private TextView hardWrong;
    private TextView hardRatio;

    private TextView favoriteCategory;

    //counters
    int winCount;
    int lossCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        setContentView(R.layout.activity_statistics);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initVariables();
        readData();
    }

    private void setAppTheme() {
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

    private void initVariables() {
        scores = new ArrayList<>();

        totalRight = findViewById(R.id.total_right);
        totalWrong = findViewById(R.id.total_wrong);
        totalRatio = findViewById(R.id.total_ratio);

        mPWins = findViewById(R.id.multi_player_wins);
        mPLosses = findViewById(R.id.multi_player_losses);
        winLossRatio = findViewById(R.id.multi_player_ratio);

        easyRight = findViewById(R.id.easy_right);
        easyWrong = findViewById(R.id.easy_wrong);
        easyRatio = findViewById(R.id.easy_ratio);

        mediumRight = findViewById(R.id.medium_right);
        mediumWrong = findViewById(R.id.medium_wrong);
        mediumRatio = findViewById(R.id.medium_ratio);

        hardRight = findViewById(R.id.hard_right);
        hardWrong = findViewById(R.id.hard_wrong);
        hardRatio = findViewById(R.id.hard_ratio);

        favoriteCategory = findViewById(R.id.favorite_category);

        winCount = 0;
        lossCount = 0;
    }

    private void populateViews() {
        totalRight.setText(String.format(Locale.US, "Right: %d", getTotalRightAnswers()));
        totalWrong.setText(String.format(Locale.US, "Wrong: %d", getTotalWrongAnswers()));
        totalRatio.setText(String.format(Locale.US, "Ratio: %s", getTotalRatio()));

        mPWins.setText(String.format(Locale.US, "Wins: %d", getTotalMPWins()));
        mPLosses.setText(String.format(Locale.US, "Losses: %d", getTotalMPLosses()));
        winLossRatio.setText(String.format(Locale.US, "Ratio: %s", getWinLossRatio()));

        easyRight.setText(String.format(Locale.US, "Right: %d", getRightAnswersByDifficulty("easy")));
        easyWrong.setText(String.format(Locale.US, "Wrong: %d", getWrongAnswersByDifficulty("easy")));
        easyRatio.setText(String.format(Locale.US, "Ratio: %s", getRatioByDifficulty("easy")));

        mediumRight.setText(String.format(Locale.US, "Right: %d", getRightAnswersByDifficulty("medium")));
        mediumWrong.setText(String.format(Locale.US, "Wrong: %d", getWrongAnswersByDifficulty("medium")));
        mediumRatio.setText(String.format(Locale.US, "Ratio: %s", getRatioByDifficulty("medium")));

        hardRight.setText(String.format(Locale.US, "Right: %d", getRightAnswersByDifficulty("hard")));
        hardWrong.setText(String.format(Locale.US, "Wrong: %d", getWrongAnswersByDifficulty("hard")));
        hardRatio.setText(String.format(Locale.US, "Ratio: %s", getRatioByDifficulty("hard")));

        favoriteCategory.setText(getFavoriteCategory());
    }

    private int getTotalRightAnswers(){
        int count = 0;
        for(Score s : scores){
            count += s.getCorrectAnswers();
        }
        return count;
    }

    private int getTotalWrongAnswers(){
        int count = 0;
        for(Score s : scores){
            count += s.getQuestionStats().size() - s.getCorrectAnswers();
        }
        return count;
    }

    private String getTotalRatio(){
        if(getTotalWrongAnswers() == 0){
            return "inf";
        }
        Double ratio = (double) getTotalRightAnswers() / (double) getTotalWrongAnswers();
        ratio = Math.round(ratio * 100.0) / 100.0;
        return ratio.toString();
    }

    private int getTotalMPWins(){
        String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref = ref.child("root").child("Users").child(user);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    winCount = user.getWins();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return winCount;
    }

    private int getTotalMPLosses(){
        String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref = ref.child("root").child("Users").child(user);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    lossCount = user.getLosses();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return lossCount;
    }

    private String getWinLossRatio(){
        if(getTotalMPLosses() == 0){
            return "inf";
        }
        Double ratio = (double) getTotalMPWins() / (double) getTotalMPLosses();
        ratio = Math.round(ratio * 100.0) / 100.0;
        return ratio.toString();
    }

    private int getRightAnswersByDifficulty(String difficulty){
        int counter = 0;
        for(Score s : scores){
            if(s.getDifficulty().toLowerCase().equals(difficulty.toLowerCase())){
                counter += s.getCorrectAnswers();
            }
        }
        return counter;
    }

    private int getWrongAnswersByDifficulty(String difficulty){
        int counter = 0;
        for(Score s : scores){
            if(s.getDifficulty().toLowerCase().equals(difficulty.toLowerCase())){
                counter += s.getQuestionStats().size() - s.getCorrectAnswers();
            }
        }
        return counter;
    }

    private String getRatioByDifficulty(String difficulty) {
        if(getWrongAnswersByDifficulty(difficulty) == 0){
            return "inf";
        }
        Double ratio = (double) getRightAnswersByDifficulty(difficulty)
                / (double) getWrongAnswersByDifficulty(difficulty);
        ratio = Math.round(ratio * 100.0) / 100.0;
        return ratio.toString();
    }

    private String getFavoriteCategory(){
        List<String> categories = new Categories().getCategories();
        int[] counts = new int[categories.size()];
        for(Score s : scores){
            counts[categories.indexOf(s.getCategory())]++;
        }
        int largest = 0;
        for(int i = 0; i < counts.length; i++){
            if(counts[i] > counts[largest]){
                largest = i;
            }
        }
        return categories.get(largest);
    }

    public void readData(){
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        String uId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference ref = db.getReference()
                .child("root")
                .child("Users")
                .child(uId)
                .child("scores");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot s : dataSnapshot.getChildren()){
                        scores.add(s.getValue(Score.class));
                    }
                    populateViews();
                    Log.d("FIREBASE_LOG", "" + scores.size());
                } else {
                    Log.d("FIREBASE_LOG", "datasnapshot not exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FIREBASE_LOG", "Read failed");
            }
        });
    }
}