package a.b.c.quizmania.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import a.b.c.quizmania.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ProfileActivityTest {

    @Rule
    public final ActivityTestRule<ProfileActivity> activityTestRule
            = new ActivityTestRule<>(ProfileActivity.class);

    private ProfileActivity activity;
    private ViewInteraction switchBtn;

    @Mock
    private FirebaseAuth fbAuth;

    @Before
    public void setUp() {
        activity = activityTestRule.getActivity();
        switchBtn = onView(withId(R.id.theme_switch));
        SharedPreferences mPref = mock(SharedPreferences.class);
        when(mPref.getString("THEME_PREF", "AppTheme")).thenReturn("MOCKED_VALUE");
        activityTestRule.launchActivity(new Intent());
    }

    @After
    public void tearDown() {

    }

    @Test
    public void sharePreferenceTest() {
        switchBtn.perform(click());
    }

}
