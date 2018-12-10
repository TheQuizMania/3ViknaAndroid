package a.b.c.quizmania.UI;

import android.content.Intent;
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

import a.b.c.quizmania.Entities.Challenge;
import a.b.c.quizmania.Entities.UserListItem;
import a.b.c.quizmania.R;
import a.b.c.quizmania.db.UsersRVAdapter;

import static a.b.c.quizmania.UI.MainMenuActivity.challengeList;

public class UserListActivity extends AppCompatActivity implements UsersRVAdapter.ItemClickListener {

    public static Challenge pendingChallenge;
    private List<UserListItem> userList;
    private UserListItem currUser;
    UsersRVAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        
        userList = new ArrayList<>();

        fetchUserList();
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
        pendingChallenge = new Challenge(currUser, adapter.getUser(position), challengeList.size(), true);
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
                            if(!item.getEmail().matches(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
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
