package a.b.c.quizmania.UI;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import a.b.c.quizmania.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public final ActivityTestRule<LoginActivity> activityTestRule
            = new ActivityTestRule<>(LoginActivity.class);

    private ViewInteraction email;
    private ViewInteraction passW;
    private ViewInteraction subBtn;
    private LoginActivity activity;

    @Before
    public void setUp() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
        subBtn = onView(withId(R.id.sign_in));
        email = onView(withId(R.id.email_sign_in));
        passW = onView(withId(R.id.password_sign_in));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void noInputTest(){
        subBtn.perform(click());
        email.check(matches(hasFocus()));
        email.check(matches(hasErrorText(activity.getString(R.string.email_not_valid))));

    }
}