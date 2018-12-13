package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.LinearLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import a.b.c.quizmania.Entities.Challenge;
import a.b.c.quizmania.Entities.UserListItem;
import a.b.c.quizmania.utilities.UsersRVAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class UserListActivityTest {

    private UsersRVAdapter adapter;
    private UsersRVAdapter.ViewHolder holder;
    private View mockView;
    private LinearLayout layout;

    @Rule
    public final ActivityTestRule<UserListActivity> mActivity
            = new ActivityTestRule<>(
                    UserListActivity.class, true, false);

    @Before
    public void setVariables() {

        List<UserListItem> users = new ArrayList<>();
        users.add(new UserListItem("email1@email.com","Coco"));
        users.add(new UserListItem("email2@email.com","Lola"));
        users.add(new UserListItem("email3@email.com","Misty"));
        users.add(new UserListItem("emall4@email.com","Violet"));
        users.add(new UserListItem("email5@email.com","Roxy"));
        users.add(new UserListItem("email6@email.com","Jinx"));

        mockView = mock(View.class);
        layout = mock(LinearLayout.class);
        adapter = new UsersRVAdapter();
        UserListActivity.setUserList(users);
        UserListActivity.setAdapter(adapter);
        mActivity.launchActivity(new Intent());
    }

    @After
    public void resetVariables() {
        UserListActivity.setUserList(null);
        UserListActivity.setAdapter(null);
    }

    @Test
    public void checkListSizeTest() {
        List<UserListItem> users = mActivity.getActivity().getUsers();
        assertEquals(6, users.size());
    }

    @Test
    public void initChallengeTest() {
        // Initialize variables
        List<UserListItem> users = mActivity.getActivity().getUsers();
        Challenge challenge = mActivity.getActivity().initChallenge(users.get(0), users.get(1));

        // Run tests
        assertEquals("Coco", challenge.getChallenger().getDisplayName());
        assertEquals("Lola", challenge.getChallengee().getDisplayName());
        assertTrue(challenge.isActive());
    }

//    @Test
//    public void setItemAndClickIt() {
//        List<UserListItem> users = mActivity.getActivity().getUsers();
//
//        adapter.setmUsers(users);
//
//    }

}