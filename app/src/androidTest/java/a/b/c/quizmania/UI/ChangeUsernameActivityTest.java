package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import a.b.c.quizmania.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
@RunWith(AndroidJUnit4.class)
public class ChangeUsernameActivityTest {

    @Rule
    public ActivityTestRule<ChangeUsernameActivity> mActivityTestRule
            = new ActivityTestRule<>(ChangeUsernameActivity.class,
            false, false);

    private ChangeUsernameActivity mActivity = null;

    @Mock private FirebaseAuth mockFirebaseAuth;

    @Mock private FirebaseUser mockFirebaseUser;

    @Mock private FirebaseUser mUser;
    private ViewInteraction userName;
    private ViewInteraction userNameConfirm;
    private ViewInteraction submitBtn;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mActivityTestRule.launchActivity(new Intent());
        mActivity = mActivityTestRule.getActivity();

        when(mockFirebaseAuth.getCurrentUser()).thenReturn(mUser);
        when(mUser.getDisplayName()).thenReturn("Balduroli");

        userName = onView(withId(R.id.new_username));
        userNameConfirm = onView(withId(R.id.confirm_new_user));
        submitBtn = onView(withId(R.id.submitBtnChangeUserName));
    }

    @Test
    public void checkWhiteSpaceUsername() {
        userName.perform(typeText(" "), closeSoftKeyboard());
        userNameConfirm.perform(typeText(" "), closeSoftKeyboard());
        submitBtn.perform(click());
        userName.check(matches(hasFocus()));
        userName.check(matches(hasErrorText(mActivity.getString(R.string.username_needed))));
    }
    @Test
    public void checkUserNameMatch() {
        userName.perform(typeText("Ballioli"), closeSoftKeyboard());
        userNameConfirm.perform(typeText("Balboa"), closeSoftKeyboard());
        submitBtn.perform(click());
        userNameConfirm.check(matches(hasFocus()));
        userNameConfirm.check(matches(hasErrorText(mActivity.getString(R.string.no_usernames_match))));
    }

    @After
    public void tearDown() throws Exception {
    }
}