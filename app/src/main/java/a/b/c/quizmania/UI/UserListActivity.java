package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import a.b.c.quizmania.Entities.Challenge;
import a.b.c.quizmania.Entities.UserListItem;
import a.b.c.quizmania.R;
import a.b.c.quizmania.db.UsersRVAdapter;

import static a.b.c.quizmania.Entities.StaticVariables.pendingChallenge;

public class UserListActivity extends AppCompatActivity implements UsersRVAdapter.ItemClickListener {

    private List<UserListItem> userList;
    private UserListItem currUser;
    UsersRVAdapter adapter;

    public void setUserList(List<UserListItem> users) {
        this.userList = users;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        setContentView(R.layout.activity_user_list);
        Objects.requireNonNull(getSupportActionBar()).hide();

        if( userList == null ) { userList = new ArrayList<>(); }

        fetchUserList();
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

    private void updateList() {
        RecyclerView rv = findViewById(R.id.rv_user_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsersRVAdapter(this, userList);
        adapter.setClickListener(this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You challenged " + adapter.getDisplayName(position), Toast.LENGTH_SHORT).show();
        pendingChallenge = new Challenge(currUser, adapter.getUser(position), true);
        Intent intent = new Intent(this, SelectionActivity.class);
        intent.putExtra("MODE", "CHALLENGER");
        startActivity(intent);
    }

    private void fetchUserList() {
        FirebaseDatabase.getInstance().getReference().child("root")
                .child("UserList")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot instance: dataSnapshot.getChildren()) {
                            UserListItem item = instance.getValue(UserListItem.class);
                            assert item != null;
                            if(!item.getEmail().matches(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()))) {
                                userList.add(item);
                            } else {
                                currUser = item;
                            }
                        }

                        updateList();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


}
